package at.htl.baumschule.entity;

import at.htl.baumschule.adapter.LocalDateAdapter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity(name = "invoice")
@NamedQuery(
        name = "Invoice.getAll",
        query = "select i from invoice i"
)
@NamedQuery(
        name = "Invoice.getTotalRevenue",
        query = "select sum(i.totalPrice) from invoice i"
)
@XmlRootElement
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    @JsonbProperty("id")
    private Long id;
    @Column(name = "i_total_price")
    @JsonbProperty("total-price")
    private double totalPrice;
    @Column(name = "i_date_of_purchase")
    @JsonbProperty("date-of-purchase")
    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class)
    private LocalDate dateOfPurchase;
    @ManyToOne
    @PrimaryKeyJoinColumn
    private Customer customer;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "plant", fetch = FetchType.EAGER)
    private List<InvoiceItem> invoiceItems;

    public Invoice() {

    }

    public Invoice(LocalDate dateOfPurchase) {
        setDateOfPurchase(dateOfPurchase);
    }

    public Long getId() {
        return id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDate() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public JsonObject toJsonObject(int customerId) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("customer-id", customerId);
        builder.add("date-of-purchase", this.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        return builder.build();
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "dateOfPurchase=" + dateOfPurchase +
                ", customer=" + customer +
                '}';
    }
}
