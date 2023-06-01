package application.dto;

import domain.models.ProfilePhoto;

import java.util.List;
import java.util.stream.Collectors;

public record Customer(String customerId, List<String> photos) {
    public static Customer fromDomain(domain.models.Customer domain) {
        return new Customer(domain.id(),
                            domain.profilePhotos().stream()
                                    .map(ProfilePhoto::generatedPhoto)
                                    .toList());
    }
}
