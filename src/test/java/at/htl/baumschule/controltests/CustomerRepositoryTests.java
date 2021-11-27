package at.htl.baumschule.controltests;

import at.htl.baumschule.control.CustomerRepository;
import at.htl.baumschule.entity.Customer;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class CustomerRepositoryTests {

    @Inject
    CustomerRepository customerRepository;

    @Test
    void addCustomer_GetCustomers_ListHasSizeOne() {
        customerRepository.clear();

        Customer customer = new Customer("Jonas Birklbauer", "Herrenstraße", "4020", "Linz", "06501235467");

        customerRepository.addCustomer(customer.toJsonObject());

        assertThat(customerRepository)
                .isNotNull();

        assertThat(customerRepository.getCustomers())
                .hasSize(1);
    }

}
