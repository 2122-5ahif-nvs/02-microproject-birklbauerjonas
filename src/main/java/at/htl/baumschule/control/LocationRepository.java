package at.htl.baumschule.control;

import at.htl.baumschule.entity.Location;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

@ApplicationScoped
public class LocationRepository implements PanacheRepository<Location> {

    @Inject
    EntityManager em;

    @Transactional
    public String addLocation(JsonValue locationJson) {

        assert locationJson != null;

        var locationToAdd = parseJsonObject(locationJson.asJsonObject());

        persist(locationToAdd);

        return String.format("Successfully added ´location with street %s",
                locationToAdd.getStreet());
    }

    @Transactional
    public String addLocations(JsonValue locations) {
        var locationArray = locations.asJsonArray();
        IntStream
                .range(0, locationArray.size())
                .mapToObj(locationArray::getJsonObject)
                .forEach(this::addLocation);
        return "Successfully added all locations";
    }

    @Transactional
    public String deleteLocation(long id) {
        try {
            deleteById(id);
            return String.format("Successfully removed location with id %d", id);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Location with id " + id + " does not exist");
        }
    }

    @Transactional
    public String updateLocation(JsonValue locationJson) {
        var location = parseJsonObject(locationJson.asJsonObject());

        try {
            var locationToUpdate = getLocation(locationJson.asJsonObject().getInt("id"));

            locationToUpdate.setStreet(location.getStreet());
            locationToUpdate.setZipCode(location.getZipCode());
            locationToUpdate.setCity(location.getCity());
            locationToUpdate.setRow(location.getRow());
            locationToUpdate.setColumn(location.getColumn());

            return String.format("Successfully updated location with id %d", locationToUpdate.getId());
        } catch(NullPointerException e) {
            long id = locationJson.asJsonObject().getInt("id");
            throw new NullPointerException("Location with id " + id + " does not exist");
        }
    }

    @Transactional
    public Location getLocation(long id) {
        try {
            return findById(id);
        } catch (NoResultException e) {
            throw new NoResultException("Location with id " + id + " does not exist");
        }
    }

    @Transactional
    public List<Location> getLocations() {
        return list("");
    }

    @Transactional
    public void clear() {
        deleteAll();

        em.createNativeQuery("alter table location alter column location_id restart with 1").executeUpdate();
    }

    private Location parseJsonObject(JsonObject location) {
        try {
            return new Location(
                    location.getString("street"),
                    location.getString("zip-code"),
                    location.getString("city"),
                    location.getInt("row"),
                    location.getInt("column")
            );
        } catch (NullPointerException e) {
            throw new NullPointerException("An error has occurred trying to parse the json object");
        }
    }

}
