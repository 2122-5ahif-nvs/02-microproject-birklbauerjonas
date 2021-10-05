package at.htl.baumschule.controltests;

import at.htl.baumschule.control.LocationRepository;
import at.htl.baumschule.entity.Location;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class LocationRepositoryTests {

    @Inject
    LocationRepository locationRepository;

    @Test
    void addLocation_GetLocations_ListHasSizeOne() {
        locationRepository.clear();

        Location location = new Location("Herrenstraße", "4020", "Linz", 0, 1);

        locationRepository.addLocation(location.toJsonObject());

        assertThat(locationRepository)
                .isNotNull();

        assertThat(locationRepository.getLocations())
                .hasSize(1);
    }
}
