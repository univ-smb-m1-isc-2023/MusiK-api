package com.github.enteraname74.musik.domain.handlerimpl;

import com.github.enteraname74.musik.domain.handler.MusicFilePersistenceManager;
import com.github.enteraname74.musik.domain.utils.FileUtils;
import com.github.enteraname74.musik.domain.utils.IdGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

/**
 * Implementation of the MusicFilePersistenceManager.
 */
@Component
public class MusicFilePersistenceManagerImpl implements MusicFilePersistenceManager {
    private final String MUSIC_FOLDER = "/app/musics";

    @Override
    public Optional<String> saveFile(MultipartFile file) {
        if (file.isEmpty()) return Optional.empty();

        String originalFileName = file.getOriginalFilename();
        Optional<String> fileExtension = FileUtils.getFileExtension(originalFileName);

        if (fileExtension.isEmpty() || !FileUtils.isMusicFile(originalFileName)) return Optional.empty();

        String fileName = IdGenerator.generateRandomId()+"."+fileExtension;

        try (InputStream inputStream = file.getInputStream()) {
            Path directory = Path.of(MUSIC_FOLDER);
            Path destination = directory.resolve(fileName);

            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);

            return Optional.of(fileName);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
