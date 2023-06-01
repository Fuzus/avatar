package infrastructure.repositories;

import domain.models.ProfilePhoto;
import domain.repositories.ProfilePhotoPersistenceRepository;
import infrastructure.entities.CustomerProfilePhotos;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class HibernateProfilePhotoRepository implements ProfilePhotoPersistenceRepository {

    private final EntityManager entityManager;

    public HibernateProfilePhotoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(String customerId, ProfilePhoto profilePhoto) {
        entityManager.merge(CustomerProfilePhotos.fromDomain(customerId, profilePhoto));
    }
}
