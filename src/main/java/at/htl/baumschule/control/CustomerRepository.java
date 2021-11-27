package at.htl.baumschule.control;

import at.htl.baumschule.entity.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    @Inject
    EntityManager em;

    @Transactional
    public String addCustomer(JsonValue customerJson) {

        assert customerJson != null;

        Customer customerToAdd = parseJsonObject(customerJson.asJsonObject());

        List<Customer> customersFound = list("c_name = ?1 and c_phone_number = ?2",
                customerToAdd.getName(),
                customerToAdd.getPhoneNumber());

        if (customersFound.size() > 0) {
            return String.format("Customer with name %s and phone number %s already exists",
                    customerToAdd.getName(),
                    customerToAdd.getPhoneNumber());
        }

        persist(customerToAdd);

        return String.format("Successfully added customer with name %s and phone number %s",
                customerToAdd.getName(),
                customerToAdd.getPhoneNumber());
    }

    @Transactional
    public String addCustomers(JsonValue customers) {
        JsonArray customerArray = customers.asJsonArray();
        IntStream
                .range(0, customerArray.size())
                .mapToObj(customerArray::getJsonObject)
                .forEach(this::addCustomer);
        return "Successfully added all customers";
    }

    @Transactional
    public String deleteCustomer(long id) {
        try {
            deleteById(id);
            return String.format("Successfully removed customer with id %d", id);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Customer with id " + id + " does not exist");
        }
    }

    @Transactional
    public String updateCustomer(JsonValue customerJson) {
        Customer customer = parseJsonObject(customerJson.asJsonObject());

        try {
            Customer customerToUpdate = getCustomer(customerJson.asJsonObject().getInt("id"));

            customerToUpdate.setName(customer.getName());
            customerToUpdate.setStreet(customer.getStreet());
            customerToUpdate.setZipCode(customer.getZipCode());
            customerToUpdate.setCity(customer.getCity());
            customerToUpdate.setPhoneNumber(customer.getPhoneNumber());

            return String.format("Successfully updated customer with id %d", customerToUpdate.getId());
        } catch(NullPointerException e) {
            long id = customerJson.asJsonObject().getInt("id");
            throw new NullPointerException("Customer with id " + id + " does not exist");
        }
    }

    @Transactional
    public Customer getCustomer(long id) {
        try {
            return findById(id);
        } catch (NoResultException e) {
            throw new NoResultException("Customer with id " + id + " does not exist");
        }
    }

    @Transactional
    public List<Customer> getCustomers() {
        return list("");
    }

    @Transactional
    public void clear() {
        getEntityManager().createNativeQuery("truncate invoice cascade").executeUpdate();

        deleteAll();

        getEntityManager().createNativeQuery("alter table customer alter column customer_id restart with 1").executeUpdate();
    }

    private Customer parseJsonObject(JsonObject customer) {
        try {
            return new Customer(
                    customer.getString("name"),
                    customer.getString("street"),
                    customer.getString("zip-code"),
                    customer.getString("city"),
                    customer.getString("phone-number")
            );
        } catch (NullPointerException e) {
            throw new NullPointerException("An error has occurred trying to parse the json object");
        }
    }
}
