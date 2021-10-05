package at.htl.baumschule.boundary;

import at.htl.baumschule.control.InvoiceRepository;
import at.htl.baumschule.entity.Invoice;
import at.htl.baumschule.entity.InvoiceItem;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.inject.Inject;
import javax.json.JsonValue;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("api/invoices")
public class InvoiceService {

    @Inject
    InvoiceRepository invoiceRepository;

    @POST
    @Path("add-invoice")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addInvoice(JsonValue invoice) {
        return Response
                .ok(invoiceRepository.addInvoice(invoice))
                .build();
    }

    @POST
    @Path("add-invoices")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addInvoices(JsonValue invoices) {
        return Response
                .ok(invoiceRepository.addInvoices(invoices))
                .build();
    }

    @DELETE
    @Path("delete-invoice/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteInvoice(@PathParam("id") long id) {
        return Response
                .ok(invoiceRepository.deleteInvoice(id))
                .build();
    }

    @PUT
    @Path("update-invoice")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateInvoice(JsonValue invoice) {
        return Response
                .ok(invoiceRepository.updateInvoice(invoice))
                .build();
    }

    @Operation(
            summary = "Gets the total revenue",
            description = "This method returns the total revenue of all the invoices via the association table invoice items"
    )
    @GET
    @Path("get-total-revenue")
    @Produces(MediaType.TEXT_PLAIN)
    public Object getTotalRevenue() {
        return invoiceRepository.getTotalRevenue();
    }

    @GET
    @Path("get-invoice/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Invoice getInvoice(@PathParam("id") long id) {
        return invoiceRepository.getInvoice(id);
    }

    @GET
    @Path("get-invoices")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Invoice> getInvoices() {
        return invoiceRepository.getInvoices();
    }

    @GET
    @Path("get-invoice-items/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<InvoiceItem> getInvoiceItemsByInvoiceId(@PathParam("id") Long id) {
        return invoiceRepository.getInvoiceItemsByInvoiceId(id);
    }

    @GET
    @Path("get-invoice-items")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<InvoiceItem> getInvoiceItems() {
        return invoiceRepository.getInvoiceItems();
    }

    @POST
    @Path("clear")
    @Consumes()
    @Produces()
    public void clear() {
        invoiceRepository.clear();
    }

}
