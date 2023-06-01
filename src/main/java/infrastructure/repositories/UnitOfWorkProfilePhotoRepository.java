package infrastructure.repositories;

import domain.models.ProfilePhoto;
import domain.repositories.ProfilePhotoRepository;
import infrastructure.rest.StableDiffusionService;
import jakarta.enterprise.context.RequestScoped;
import org.jboss.logging.Logger;

import java.util.Map;

@RequestScoped
public class UnitOfWorkProfilePhotoRepository implements ProfilePhotoRepository {

    private final HibernateProfilePhotoRepository persistenceRepository;
    private final S3ProfilePhotoStorageRepository storageRepository;

    private final StableDiffusionService stableDiffusionService;
    private Map<String, ProfilePhoto> entities;

    public UnitOfWorkProfilePhotoRepository(HibernateProfilePhotoRepository persistenceRepository, S3ProfilePhotoStorageRepository storageRepository,
                                            StableDiffusionService StableDiffusionService) {
        this.persistenceRepository = persistenceRepository;
        this.storageRepository = storageRepository;
        this.stableDiffusionService = StableDiffusionService;
        this.entities = Map.of();
    }

    @Override
    public void registerEntities(Map<String, ProfilePhoto> entities) {
        this.entities = entities;
    }

    @Override
    public void commit() {
        entities.forEach((customerId, profilePhoto) -> {
            try{
                persistenceRepository.save(customerId, profilePhoto);

                var generatedImage = stableDiffusionService.generate(profilePhoto).await().indefinitely();

                var originalS3 = storageRepository.store(customerId, profilePhoto).await().indefinitely();
                var generatedS3 = storageRepository.store(customerId, profilePhoto, generatedImage).await().indefinitely();
                
                var updated = new ProfilePhoto(profilePhoto.id(), originalS3, generatedS3);

                persistenceRepository.save(customerId, updated);
            }catch (Exception e) {
                Logger.getLogger(getClass()).error(e);
            }

        });
    }

    @Override
    public void rollback() {

    }
}
