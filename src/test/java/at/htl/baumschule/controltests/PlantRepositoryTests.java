package at.htl.baumschule.controltests;

import at.htl.baumschule.control.PlantRepository;
import at.htl.baumschule.entity.Plant;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class PlantRepositoryTests {

    @Inject
    PlantRepository plantRepository;

    @Test
    void addPlant_GetPlants_ListHasSizeOne() {
        plantRepository.clear();

        Plant plant = new Plant("Rose", 2.50, true);

        plantRepository.addPlant(plant.toJsonObject());

        assertThat(plantRepository)
                .isNotNull();

        assertThat(plantRepository.getPlants())
                .hasSize(1);

    }
}
