package infrastructure.resources;

import application.ApplicationService;
import application.dto.Customer;
import application.dto.ProfilePhoto;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;
import java.util.NoSuchElementException;

@Path("customers")
public class CustomerResource {
    private final ApplicationService service;

    public CustomerResource(ApplicationService applicationService) {
        this.service = applicationService;
    }

    @GET
    public List<Customer> searchCustomers() {
        return service.searchCustomers();
    }

    @GET
    @Path("/{id}")
    public Customer getCustomer(@PathParam("id") String id){
        try {
            return service.getCustomer(id);
        } catch (NoSuchElementException e) {
            throw new NotFoundException();
        }
    }

    @POST
    @Path("/{id}")
    @ResponseStatus(RestResponse.StatusCode.ACCEPTED)
    public void persistProfilePhoto(@PathParam("id") String id,
                                    @RestForm("photo") FileUpload file){
        service.persistProfilePhoto(id, ProfilePhoto.create(file));
    }
}
