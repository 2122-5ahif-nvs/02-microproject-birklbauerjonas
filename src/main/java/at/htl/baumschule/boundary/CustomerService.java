package at.htl.baumschule.boundary;

import at.htl.baumschule.control.CustomerRepository;
import at.htl.baumschule.entity.Customer;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.inject.Inject;
import javax.json.JsonValue;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("api/customers")
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @Operation(
            summary = "Saves a customer",
            description = "Calls the method addCustomer, this method parses the json object and saves a customer entity to the database"
    )
    @POST
    @Path("add-customer")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCustomer(JsonValue customer) {
        return Response
                .ok(customerRepository.addCustomer(customer))
                .build();
    }

    @POST
    @Path("add-customers")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCustomers(JsonValue customers) {
        return Response
                .ok(customerRepository.addCustomers(customers))
                .build();
    }

    @DELETE
    @Path("delete-customer/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCustomer(@PathParam("id") long id) {
        return Response
                .ok(customerRepository.deleteCustomer(id))
                .build();
    }

    @PUT
    @Path("update-customer")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(JsonValue customer) {
        return Response
                .ok(customerRepository.updateCustomer(customer))
                .build();
    }

    @GET
    @Path("get-customer/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Customer getCustomer(@PathParam("id") long id) {
        return customerRepository.getCustomer(id);
    }

    @GET
    @Path("get-customers")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Customer> getCustomers() {
        return customerRepository.getCustomers();
    }

    @POST
    @Path("clear")
    @Consumes()
    @Produces()
    public void clear() {
        customerRepository.clear();
    }
}
