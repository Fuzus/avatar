package domain.services;

import domain.models.ProfilePhoto;
import domain.repositories.ProfilePhotoPersistenceRepository;
import infrastructure.repositories.UnitOfWorkProfilePhotoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class ProfilePhotoCreateService {
    private final UnitOfWorkProfilePhotoRepository repository;

    public ProfilePhotoCreateService(UnitOfWorkProfilePhotoRepository photoRepository) {
        this.repository = photoRepository;
    }

    public void save(String customerId, ProfilePhoto profilePhoto){
        this.repository.registerEntities(Map.of(customerId, profilePhoto));
        this.repository.commit();
    }
}
