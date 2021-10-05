package at.htl.baumschule.entitytests;

import at.htl.baumschule.entity.Location;
import at.htl.baumschule.entity.Plant;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class PlantTests {

    @Test
    void toString_IsEqual_True() {
        Plant plant = new Plant("Rose", 2.50, true);

        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Herrenstraße", "4020", "Linz", 0, 1));

        plant.setLocations(locations);

        assertThat(plant.toString())
                .isEqualTo("Plant{name='Rose', price=2.5, readyForSale=true, locations=[Location{street='Herrenstraße', zipCode='4020', city='Linz', row=0, column=1, plants=null}]}");
    }

}
