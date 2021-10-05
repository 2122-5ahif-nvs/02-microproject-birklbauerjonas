package at.htl.baumschule.entitytests;

import at.htl.baumschule.entity.Location;
import at.htl.baumschule.entity.Plant;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class LocationTests {

    @Test
    void toString_IsEqual_True() {
        Location location = new Location("Herrenstraße", "4020", "Linz", 0, 1);

        List<Plant> plants = new ArrayList<>();
        plants.add(new Plant("Rose", 2.50, true));

        location.setPlants(plants);

        assertThat(location.toString())
                .isEqualTo("Location{street='Herrenstraße', zipCode='4020', city='Linz', row=0, column=1, plants=[Plant{name='Rose', price=2.5, readyForSale=true, locations=null}]}");
    }
}
