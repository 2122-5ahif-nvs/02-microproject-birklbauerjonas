package at.htl.baumschule.entity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity(name = "plant")
@NamedQuery(
        name = "Plant.getAll",
        query = "select p from plant p"
)
@XmlRootElement
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Long id;
    @Column(name = "p_name")
    private String name;
    @Column(name = "p_price")
    private double price;
    @Column(name = "p_ready_for_sale")
    private boolean readyForSale;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "plant", fetch = FetchType.EAGER)
    @Transient
    private List<InvoiceItem> invoiceItems;
    @ManyToMany(cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    @Transient
    private List<Location> locations;

    public Plant(String name, double price, boolean readyForSale) {
        this.name = name;
        this.price = price;
        this.readyForSale = readyForSale;
    }

    public Plant() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isReadyForSale() {
        return readyForSale;
    }

    public void setReadyForSale(boolean readyForSale) {
        this.readyForSale = readyForSale;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public JsonObject toJsonObject() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("name", this.getName());
        builder.add("price", this.getPrice());
        builder.add("ready-for-sale", this.isReadyForSale());

        return builder.build();
    }

    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", readyForSale=" + readyForSale +
                ", locations=" + locations +
                '}';
    }
}
