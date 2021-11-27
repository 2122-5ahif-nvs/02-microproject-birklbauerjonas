package at.htl.baumschule.entity;

import javax.persistence.*;

@Entity(name = "invoice_item")
@NamedQuery(
        name = "InvoiceItem.getAll",
        query = "select i from invoice_item i"
)
@NamedQuery(
        name = "InvoiceItem.findByInvoiceId",
        query = "select i from invoice_item i where i.invoice.id = :id"
)
@NamedQuery(
        name = "InvoiceItem.findBestSellingPlant",
        query = "select i from invoice_item i group by i.id, i.plant.id order by sum(i.quantity) desc"
)
@NamedQuery(
        name = "InvoiceItem.findPlantWithBiggestTurnover",
        query = "select i from invoice_item i"
)
@NamedQuery(
        name = "InvoiceItem.findPlantWithBiggestRevenue",
        query = "select i.plant.name as plantName, (i.quantity * i.plant.price) as totalRevenue from invoice_item i group by i.id, i.plant.name, i.plant.price order by i.plant.price*i.quantity DESC"
)
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    @Column(name = "it_quantity")
    private int quantity;

    public InvoiceItem(Plant plant, Invoice invoice, int quantity) {
        this.plant = plant;
        this.invoice = invoice;
        this.quantity = quantity;
    }

    public InvoiceItem() {}

    public Long getId() {
        return id;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "plant=" + plant +
                ", invoice=" + invoice +
                ", quantity=" + quantity +
                '}';
    }
}
