package com.github.enteraname74.musik.domain.handlerimpl;

import com.github.enteraname74.musik.domain.handler.MusicFilePersistenceManager;
import com.github.enteraname74.musik.domain.utils.FileUtils;
import com.github.enteraname74.musik.domain.utils.IdGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of the MusicFilePersistenceManager.
 */
@Component
public class MusicFilePersistenceManagerImpl implements MusicFilePersistenceManager {
    private final String MUSIC_FOLDER = "/app/musics";

    /**
     * Retrieve the id of a music file from its name.
     *
     * @param fileName the name of the music file.
     * @return the id of the music file.
     */
    private String getIdFromFileName(String fileName) {
        return fileName.replaceFirst("[.][^.]+$", "");
    }

    /**
     * Retrieve all music files name.
     *
     * @return a list containing all music files name.
     */
    private List<String> getAllMusicFilesName() {
        try {
            return Stream.of(Objects.requireNonNull(new File(MUSIC_FOLDER).listFiles()))
                    .filter(file -> !file.isDirectory())
                    .map(File::getName)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<String> saveFile(MultipartFile file) {
        System.out.println("Is file empty ? "+ file.isEmpty());
        if (file.isEmpty()) return Optional.empty();

        String originalFileName = file.getOriginalFilename();
        System.out.println("File name: "+originalFileName);
        Optional<String> fileExtension = FileUtils.getFileExtension(originalFileName);

        System.out.println("Is file extension empty ?" + fileExtension.isEmpty());
        System.out.println("Is noy music file ?" + !FileUtils.isMusicFile(file));

        if (fileExtension.isEmpty() || !FileUtils.isMusicFile(file)) return Optional.empty();
        String fileId = IdGenerator.generateRandomId();
        String fileName = fileId + "." + fileExtension.get();

        try (InputStream inputStream = file.getInputStream()) {
            Path directory = Path.of(MUSIC_FOLDER);
            Path destination = directory.resolve(fileName);

            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);

            return Optional.of(fileId);
        } catch (Exception e) {
            System.out.println("EXCEPTION WILL WRITING UPLOADED FILE: "+e.getLocalizedMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<File> getById(String id) {
        List<String> allFiles = getAllMusicFilesName();

        for (String fileName : allFiles) {
            String fileId = getIdFromFileName(fileName);
            if (fileId.equals(id)) {
                File musicFile = new File(MUSIC_FOLDER+"/"+fileName);
                return Optional.of(musicFile);
            }
        }

        return Optional.empty();
    }

    @Override
    public Boolean deleteFile(String id) {
        Optional<File> fileToDelete = getById(id);

        return fileToDelete.map(File::delete).orElse(false);
    }
}
