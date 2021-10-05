package at.htl.baumschule.control;

import at.htl.baumschule.entity.InvoiceItem;
import at.htl.baumschule.entity.Location;
import at.htl.baumschule.entity.Plant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@ApplicationScoped
public class PlantRepository implements PanacheRepository<Plant> {

    @Inject
    EntityManager em;

    @Inject
    LocationRepository locationRepository;

    @Transactional
    public String addPlant(JsonValue plantJson) {

        assert plantJson != null;

        var plantToAdd = parseJsonObject(plantJson.asJsonObject());

        persist(plantToAdd);

        try {
            var locations = plantJson.asJsonObject().getJsonArray("locations");
            if (locations != null) {
                addLocations(locations, plantToAdd);
            }
        } catch (NullPointerException e) {
            System.out.println("No locations found to add");
        }

        return String.format("Successfully added plant with name %s and price %f",
                plantToAdd.getName(),
                plantToAdd.getPrice());
    }

    @Transactional
    public String addPlants(JsonValue plants) {
        var plantArray = plants.asJsonArray();
        IntStream
                .range(0, plantArray.size())
                .mapToObj(plantArray::getJsonObject)
                .forEach(this::addPlant);
        return "Successfully added all plants";
    }

    @Transactional
    public String deletePlant(long id) {
        try {
            deleteById(id);
            return String.format("Successfully removed plant with id %d", id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Plant with id " + id + " does not exist");
        }
    }

    @Transactional
    public String updatePlant(JsonValue plantJson) {
        var plant = parseJsonObject(plantJson.asJsonObject());

        try {
            var plantToUpdate = getPlant(plantJson.asJsonObject().getInt("id"));

            plantToUpdate.setName(plantToUpdate.getName());
            plantToUpdate.setPrice(plantToUpdate.getPrice());
            plantToUpdate.setReadyForSale(plant.isReadyForSale());
            try {
                addLocations(plantJson.asJsonObject().getJsonArray("locations"), plantToUpdate);
            } catch (NullPointerException exception) {
                System.out.println("No locations found to update");
            }

            return String.format("Successfully updated plant with id %d", plantToUpdate.getId());
        } catch (NullPointerException e) {
            long id = plantJson.asJsonObject().getInt("id");
            throw new NullPointerException("Plant with id " + id + " does not exist");
        }
    }

    @Transactional
    public InvoiceItem getMostSoldPlant() {
        try {
            return getEntityManager().createNamedQuery("InvoiceItem.findBestSellingPlant", InvoiceItem.class)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new NoResultException("No item was found");
        }
    }

    @Transactional
    public Object getPlantWithHighestRevenue() {
        try {
            return getEntityManager().createNamedQuery("InvoiceItem.findPlantWithBiggestRevenue")
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new NoResultException("No plant found");
        }
    }

    @Transactional
    public Plant getPlant(long id) {
        try {
            return findById(id);
        } catch (NoResultException e) {
            throw new NoResultException("Plant with id " + id + " does not exist");
        }
    }

    @Transactional
    public List<Plant> getPlants() {
        return list("");
    }

    @Transactional
    private void addLocations(JsonArray locations, Plant plant) {
        assert locations != null;

        try {
            var locationsToAdd = new ArrayList<Location>();
            for (var location : locations) {
                locationsToAdd.add(locationRepository.find("id", Long.valueOf(String.valueOf(location))).singleResult());
            }
            plant.setLocations(locationsToAdd);
        } catch (NullPointerException | IllegalArgumentException ie) {
            if (ie.getClass().getSimpleName().equals("NullPointerException")) {
                System.out.println("No locations found");
            } else {
                System.out.println("An error occurred");
            }
        }
    }

    @Transactional
    public void clear() {
        getEntityManager().createNativeQuery("truncate location cascade").executeUpdate();
        getEntityManager().createNativeQuery("truncate invoice_item cascade").executeUpdate();

        deleteAll();

        getEntityManager().createNativeQuery("alter table plant alter column plant_id restart with 1").executeUpdate();
    }

    private Plant parseJsonObject(JsonObject plant) {
        try {
            return new Plant(
                    plant.getString("name"),
                    Double.parseDouble(String.valueOf((JsonNumber) plant.get("price"))),
                    plant.getBoolean("ready-for-sale")
            );
        } catch (NullPointerException e) {
            throw new NullPointerException("An error has occurred trying to parse the json object");
        }
    }
}
