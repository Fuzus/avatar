package application.dto;

;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.UUID;

public record ProfilePhoto(FileUpload file) {

    public static ProfilePhoto create(FileUpload file) {
        return new ProfilePhoto(file);
    }

    public domain.models.ProfilePhoto toDomain() {
        return new domain.models.ProfilePhoto(
                UUID.randomUUID().toString(),
                file.uploadedFile().toAbsolutePath().toString(),
                null
        );
    }
}
