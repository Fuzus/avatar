package application;

import application.dto.Customer;
import application.dto.ProfilePhoto;
import domain.services.CustomerReadService;
import domain.services.ProfilePhotoCreateService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ApplicationService {
    private final CustomerReadService customerReadService;
    private final ProfilePhotoCreateService profilePhotoCreateService;

    public ApplicationService(CustomerReadService customerReadService,
                              ProfilePhotoCreateService profilePhotoCreateService) {
        this.customerReadService = customerReadService;
        this.profilePhotoCreateService = profilePhotoCreateService;
    }

    public Customer getCustomer(String customerId){
        return Customer.fromDomain(customerReadService.findById(customerId));
    }

    public List<Customer> searchCustomers(){
        return customerReadService.find().stream().map(Customer::fromDomain).toList();
    }

    public void persistProfilePhoto(String cutomerId, ProfilePhoto dto){
        profilePhotoCreateService.save(cutomerId, dto.toDomain());
    }

}
