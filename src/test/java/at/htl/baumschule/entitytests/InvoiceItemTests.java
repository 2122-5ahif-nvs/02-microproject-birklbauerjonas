package at.htl.baumschule.entitytests;

import at.htl.baumschule.entity.Customer;
import at.htl.baumschule.entity.Invoice;
import at.htl.baumschule.entity.InvoiceItem;
import at.htl.baumschule.entity.Plant;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class InvoiceItemTests {

    @Test
    void toString_IsEqual_True() {
        Customer customer = new Customer("Jonas Birklbauer", "Herrenstraße", "4020", "Linz", "06501235467");
        Invoice invoice = new Invoice(LocalDate.parse("22.10.2020", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        invoice.setCustomer(customer);

        Plant plant = new Plant("Rose", 2.50, true);

        InvoiceItem item = new InvoiceItem(plant, invoice, 2);

        assertThat(item.toString())
                .isEqualTo("InvoiceItem{plant=Plant{name='Rose', price=2.5, readyForSale=true, locations=null}, invoice=Invoice{dateOfPurchase=2020-10-22, customer=Customer{name='Jonas Birklbauer', street='Herrenstraße', zipCode='4020', city='Linz', phoneNumber='06501235467'}}, quantity=2}");

    }
}
