package at.htl.baumschule.boundary;

import at.htl.baumschule.control.LocationRepository;
import at.htl.baumschule.entity.Location;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.JsonValue;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("locations")
@Tag(name = "Locations")
@RolesAllowed(value = {"user", "admin"})
public class LocationService {

    @Inject
    LocationRepository locationRepository;

    @POST
    @Path("add-location")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addLocation(JsonValue location) {
        return Response
                .ok(locationRepository.addLocation(location))
                .build();
    }

    @POST
    @Path("add-locations")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addLocations(JsonValue locations) {
        return Response
                .ok(locationRepository.addLocations(locations))
                .build();
    }

    @DELETE
    @Path("delete-location/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteLocation(@PathParam("id") long id) {
        return Response
                .ok(locationRepository.deleteLocation(id))
                .build();
    }

    @PUT
    @Path("update-location")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLocation(JsonValue location) {
        return Response
                .ok(locationRepository.updateLocation(location))
                .build();
    }

    @GET
    @Path("get-location/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Location getLocation(@PathParam("id") long id) {
        return locationRepository.getLocation(id);
    }

    @GET
    @Path("get-locations")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Location> getLocations() {
        return locationRepository.getLocations();
    }

    @POST
    @Path("clear")
    @Consumes()
    @Produces()
    @RolesAllowed("admin")
    public void clear() {
        locationRepository.clear();
    }
}
