package at.htl.baumschule.entitytests;

import at.htl.baumschule.entity.Customer;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class CustomerTests {

    @Test
    void toString_IsEqual_True() {
        Customer customer = new Customer("Jonas Birklbauer", "Herrenstraße", "4020", "Linz", "06501235467");

        assertThat(customer.toString())
                .isEqualTo("Customer{name='Jonas Birklbauer', street='Herrenstraße', zipCode='4020', city='Linz', phoneNumber='06501235467'}");
    }

}
