package at.htl.baumschule.entity;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity(name = "customer")
@NamedQuery(
        name = "Customer.getAll",
        query = "select c from customer c"
)
@NamedQuery(
        name = "Customer.findByNameAndPhoneNumber",
        query = "select c from customer c where c.name = :name and c.phoneNumber = :phoneNumber"
)
@XmlRootElement
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    @JsonbProperty("id")
    private Long id;
    @JsonbProperty("name")
    @Column(name = "c_name")
    private String name;
    @JsonbProperty("street")
    @Column(name = "c_street")
    private String street;
    @JsonbProperty("zip-code")
    @Column(name = "c_zip_code")
    private String zipCode;
    @JsonbProperty("city")
    @Column(name = "c_city")
    private String city;
    @JsonbProperty("phone-number")
    @Column(name = "c_phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "customer")
    @PrimaryKeyJoinColumn
    @Transient
    private List<Invoice> invoices;

    public Customer(String name, String street, String zipCode, String city, String phoneNumber) {
        setName(name);
        setStreet(street);
        setZipCode(zipCode);
        setCity(city);
        setPhoneNumber(phoneNumber);
    }

    public Customer() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public JsonObject toJsonObject() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("name", this.getName());
        builder.add("street", this.getStreet());
        builder.add("zip-code", this.getZipCode());
        builder.add("city", this.getCity());
        builder.add("phone-number", this.getPhoneNumber());

        return builder.build();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
