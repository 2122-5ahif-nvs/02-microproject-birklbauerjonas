package at.htl.baumschule.controltests;

import at.htl.baumschule.control.CustomerRepository;
import at.htl.baumschule.control.InvoiceRepository;
import at.htl.baumschule.entity.Customer;
import at.htl.baumschule.entity.Invoice;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class InvoiceRepositoryTest {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    InvoiceRepository invoiceRepository;

    @Test
    void addInvoice_GetInvoices_ListHasSizeOne() {
        customerRepository.clear();

        invoiceRepository.clear();

        Customer customer = new Customer("Jonas Birklbauer", "Herrenstraße", "4020", "Linz", "06501235467");

        customerRepository.addCustomer(customer.toJsonObject());

        Invoice invoice = new Invoice(LocalDate.parse("22.10.2020", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        var customerId = 1;

        invoiceRepository.addInvoice(invoice.toJsonObject(customerId));

        assertThat(customerRepository)
                .isNotNull();

        assertThat(invoiceRepository)
                .isNotNull();

        assertThat(invoiceRepository.getInvoices())
                .hasSize(1);
    }
}
