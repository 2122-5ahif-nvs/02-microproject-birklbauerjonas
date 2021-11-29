package at.htl.baumschule.boundary;

import at.htl.baumschule.control.CustomerRepository;
import at.htl.baumschule.entity.Customer;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.JsonValue;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("customers")
@Tag(name = "Customer")
@RolesAllowed(value = {"user", "admin"})
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

    @Operation(
            summary = "Saves a list of customers",
            description = "Calls the method addCustomers, this method parses the json array and saves the customer entities to the database"
    )
    @POST
    @Path("add-customers")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCustomers(JsonValue customers) {
        return Response
                .ok(customerRepository.addCustomers(customers))
                .build();
    }

    @Operation(
            summary = "Removes a customer",
            description = "Calls the method deleteCustomer, this methods searches a customer with the given id, if found it gets removed from the database"
    )
    @DELETE
    @Path("delete-customer/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCustomer(@PathParam("id") long id) {
        return Response
                .ok(customerRepository.deleteCustomer(id))
                .build();
    }

    @Operation(
            summary = "Updats a customer",
            description = "Calls the method updateCustomer, this method parses the json object and searches a customer with the given id, if found the customer gets updated"
    )
    @PUT
    @Path("update-customer")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(JsonValue customer) {
        return Response
                .ok(customerRepository.updateCustomer(customer))
                .build();
    }

    @Operation(
            summary = "Gets a customer",
            description = "Calls the method getCustomer, this method searches a customer with the given id, if found it gets returned"
    )
    @GET
    @Path("get-customer/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Customer getCustomer(@PathParam("id") long id) {
        return customerRepository.getCustomer(id);
    }

    @Operation(
            summary = "Gets all customer",
            description = "Calls the method getCustomers, this method returns a list of all customer"
    )
    @GET
    @Path("get-customers")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Customer> getCustomers() {
        return customerRepository.getCustomers();
    }

    @Operation(
            summary = "Removes all customers",
            description = "Calls the method clear, this method removes every customer"
    )
    @POST
    @Path("clear")
    @Consumes()
    @Produces()
    @RolesAllowed("admin")
    public void clear() {
        customerRepository.clear();
    }
}
