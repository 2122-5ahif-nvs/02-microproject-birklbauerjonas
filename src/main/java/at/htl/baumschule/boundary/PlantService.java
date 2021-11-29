package at.htl.baumschule.boundary;

import at.htl.baumschule.control.PlantRepository;
import at.htl.baumschule.entity.InvoiceItem;
import at.htl.baumschule.entity.Plant;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.JsonValue;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("plants")
@Tag(name = "Plants")
@RolesAllowed(value = {"user", "admin"})
public class PlantService {

    @Inject
    PlantRepository plantRepository;

    @POST
    @Path("add-plant")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlant(JsonValue plant) {
        return Response
                .ok(plantRepository.addPlant(plant))
                .build();
    }

    @POST
    @Path("add-plants")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlants(JsonValue plants) {
        return Response
                .ok(plantRepository.addPlants(plants))
                .build();
    }

    @DELETE
    @Path("delete-plant/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePlant(@PathParam("id") long id) {
        return Response
                .ok(plantRepository.deletePlant(id))
                .build();
    }

    @PUT
    @Path("update-plant")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlant(JsonValue plant) {
        return Response
                .ok(plantRepository.updatePlant(plant))
                .build();
    }

    @Operation(
            summary = "Gets most sold plant",
            description = "Gets the most sold plan via the association table invoice_items. Invoice items uses a named query to get the most sold product"
    )
    @GET
    @Path("get-most-sold-plant")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public InvoiceItem getMostSoldPlant() {
        return plantRepository.getMostSoldPlant();
    }

    @GET
    @Path("get-plant-with-highest-revenue")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Object getPlantWithHighestRevenue() {
        return plantRepository.getPlantWithHighestRevenue();
    }

    @GET
    @Path("get-plant/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Plant getPlant(@PathParam("id") long id) {
        return plantRepository.getPlant(id);
    }

    @GET
    @Path("get-plants")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Plant> getPlants() {
        return plantRepository.getPlants();
    }

    @POST
    @Path("clear")
    @Consumes()
    @Produces()
    @RolesAllowed("admin")
    public void clear() {
        plantRepository.clear();
    }
}
