package at.htl.baumschule.control;

import at.htl.baumschule.entity.Invoice;
import at.htl.baumschule.entity.InvoiceItem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

@ApplicationScoped
public class InvoiceRepository implements PanacheRepository<Invoice> {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    PlantRepository plantRepository;

    @Inject
    Logger logger;

    @Transactional
    public String addInvoice(JsonValue invoiceJson) {

        assert invoiceJson != null;

        var invoiceToAdd = parseJsonObject(invoiceJson.asJsonObject());

        var customerId = invoiceJson.asJsonObject().getInt("customer-id");

        invoiceToAdd.setCustomer(customerRepository.getCustomer(customerId));

        persist(invoiceToAdd);

        try{
            var invoiceItems = invoiceJson.asJsonObject().getJsonArray("invoice-items");
            if(invoiceItems != null) {
                addInvoiceItems(invoiceItems, invoiceToAdd);
            }
        }catch (NullPointerException e) {
            System.out.println("No invoice items found to add");
        }

        return String.format("Successfully added invoice with a total price of %f for customer %s",
                invoiceToAdd.getTotalPrice(),
                invoiceToAdd.getCustomer().getName());
    }

    @Transactional
    public String addInvoices(JsonValue invoices) {
        var invoiceArray = invoices.asJsonArray();
        IntStream
                .range(0, invoiceArray.size())
                .mapToObj(invoiceArray::getJsonObject)
                .forEach(this::addInvoice);
        return "Successfully added all invoices";
    }

    @Transactional
    public String deleteInvoice(long id) {
        try {
            deleteById(id);
            return String.format("Successfully removed invoice with id %d", id);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Invoice with id " + id + " does not exist");
        }
    }

    @Transactional
    public String updateInvoice(JsonValue invoiceJson) {
        var invoice = parseJsonObject(invoiceJson.asJsonObject());

        try {
            var invoiceToUpdate = getInvoice(invoiceJson.asJsonObject().getInt("id"));

            var customerId = invoiceJson.asJsonObject().getInt("customer-id");

            invoiceToUpdate.setCustomer(customerRepository.getCustomer(customerId));
            invoiceToUpdate.setTotalPrice(invoice.getTotalPrice());
            invoiceToUpdate.setDateOfPurchase(invoice.getDate());
            try {
                addInvoiceItems(invoiceJson.asJsonObject().getJsonArray("invoice-items"), invoiceToUpdate);
            } catch(NullPointerException exception) {
                System.out.println("No invoice items found to update");
            }
            return String.format("Successfully updated invoice with id %d", invoiceToUpdate.getId());
        } catch(NullPointerException e) {
            long id = invoiceJson.asJsonObject().getInt("id");
            throw new NullPointerException("Invoice with id " + id + " does not exist");
        }
    }

    @Transactional
    public Invoice getInvoice(long id) {
        try {
            return findById(id);
        } catch (NoResultException e) {
            throw new NoResultException("Invoice with id " + id + " does not exist");
        }
    }

    @Transactional
    public List<Invoice> getInvoices() {
        return list("");
    }

    @Transactional
    private void addInvoiceItems(JsonArray invoiceItems, Invoice invoice) {
        assert invoiceItems != null;

        try {
            for(var item : invoiceItems) {
                var obj = item.asJsonObject();
                var plant = this.plantRepository.getPlant(obj.getInt("plant-id"));
                if(plant.isReadyForSale()) {
                    var invoiceItem = new InvoiceItem(plant, invoice, obj.getInt("quantity"));
                    getEntityManager().persist(invoiceItem);
                    invoice.setTotalPrice(invoice.getTotalPrice() + (plant.getPrice() * invoiceItem.getQuantity()));
                }
                else {
                    logger.info("Plant with id " + plant.getId() + " is not ready for sale and therefore was not added to invoice");
                }
            }
        } catch(NullPointerException e) {
            throw new NullPointerException("An error has occurred trying to parse the json object");
        }
    }

    @Transactional
    public Object getTotalRevenue() {
        try {
            return getEntityManager().createNamedQuery("Invoice.getTotalRevenue").setMaxResults(1).getSingleResult();
        } catch(NoResultException e) {
            throw new NoResultException("No revenue found");
        }
    }

    @Transactional
    public List<InvoiceItem> getInvoiceItemsByInvoiceId(Long id) {
        return getEntityManager()
                .createNamedQuery("InvoiceItem.findByInvoiceId", InvoiceItem.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Transactional
    public List<InvoiceItem> getInvoiceItems() {
        return getEntityManager().createNamedQuery("InvoiceItem.getAll", InvoiceItem.class).getResultList();
    }

    @Transactional
    public void clear() {
        getEntityManager().createNativeQuery("truncate customer cascade").executeUpdate();
        //getEntityManager().createNativeQuery("truncate plant cascade").executeUpdate();
        getEntityManager().createNativeQuery("truncate invoice_item cascade").executeUpdate();

        deleteAll();

        getEntityManager().createNativeQuery("alter table invoice alter column invoice_id restart with 1").executeUpdate();
    }

    private Invoice parseJsonObject(JsonObject invoice) {
        try {
            return new Invoice(
                    parseLocalDate(invoice.getString("date-of-purchase"))
            );
        } catch (NullPointerException e) {
            throw new NullPointerException("An error has occurred trying to parse the json object");
        }
    }

    private LocalDate parseLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

}
