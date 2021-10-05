package at.htl.baumschule.entity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity(name = "location")
@NamedQuery(
        name = "Location.getAll",
        query = "select l from location l"
)
@XmlRootElement
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;
    @Column(name = "l_street")
    private String street;
    @Column(name = "l_zip_code")
    private String zipCode;
    @Column(name = "l_city")
    private String city;
    @Column(name = "l_row_number")
    private int row;
    @Column(name = "l_column_number")
    private int column;
    @ManyToMany
    @PrimaryKeyJoinColumn
    @Transient
    private List<Plant> plants;

    public Location(String street, String zipCode, String city, int row, int column) {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.row = row;
        this.column = column;
    }

    public Location() {}

    public Long getId() {
        return id;
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    public JsonObject toJsonObject() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("street", this.getStreet());
        builder.add("zip-code", this.getZipCode());
        builder.add("city", this.getCity());
        builder.add("row", this.getRow());
        builder.add("column", this.getColumn());

        return builder.build();
    }

    @Override
    public String toString() {
        return "Location{" +
                "street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", row=" + row +
                ", column=" + column +
                ", plants=" + plants +
                '}';
    }
}
