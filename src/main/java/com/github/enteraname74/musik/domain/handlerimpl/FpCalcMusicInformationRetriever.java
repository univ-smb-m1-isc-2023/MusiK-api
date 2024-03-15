package com.github.enteraname74.musik.domain.handlerimpl;

import com.github.enteraname74.musik.domain.handler.MusicFileMetadataManager;
import com.github.enteraname74.musik.domain.handler.MusicInformationRetriever;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.model.MusicMetadata;
import com.github.enteraname74.musik.domain.utils.AcoustidApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Optional;

@Qualifier("FpCalc")
@Component
public class FpCalcMusicInformationRetriever implements MusicInformationRetriever {
    private final MusicFileMetadataManager metadataManager;

    @Autowired
    public FpCalcMusicInformationRetriever(MusicFileMetadataManager metadataManager) {
        this.metadataManager = metadataManager;
    }

    @Override
    public Music getInformationAboutMusicFile(File musicFile, String musicFileId) {
        Optional<FpCalcResult> result = getFingerPrintFromMusic(musicFile.getAbsolutePath());
        if (result.isEmpty()) return Music.emptyMusicInformation(musicFileId);

        FpCalcResult foundResult = result.get();

        // We need the initial metadata of the file.
        MusicMetadata initialMetadata = metadataManager.getMetadataOfFile(musicFile);

        AcoustidApiClient client = new AcoustidApiClient(initialMetadata);

        return client.getMusicInformation(
                foundResult.fingerprint(),
                foundResult.duration(),
                musicFileId
        );
    }

    /**
     * Tries to retrieve the fingerprint of a music file.
     *
     * @param musicPath the path of the music used to retrieve the fingerprint.
     * @return the fingerprint of the file or nothing.
     */
    private Optional<FpCalcResult> getFingerPrintFromMusic(String musicPath) {
        try {
            String[] command = {"fpcalc", musicPath};

            Process process = Runtime.getRuntime().exec(command);
            System.out.println("Process is running...");

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            String fingerprint = "";
            String duration = "";
            while((line = in.readLine()) != null){
                if (isDurationField(line)) duration = getDuration(line);
                else if (isFingerprintField(line)) fingerprint = getFingerprint(line);
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // Successfully calculated fingerprint
                return Optional.of(new FpCalcResult(
                        duration,
                        fingerprint
                ));
            }
        } catch (Exception exception) {
            System.out.println("EXCEPTION OCCURRED!!");
            System.out.println(exception.getLocalizedMessage());
        }

        return Optional.empty();
    }

    /**
     * Check if a line correspond to the duration field.
     *
     * @param line the line to check.
     * @return true if the line correspond to the duration field, false if not.
     */
    private boolean isDurationField(String line) {
        try {
            return line.split("=")[0].equals("DURATION");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the value of the duration field.
     *
     * @param line the line holding the duration value.
     * @return the duration value.
     */
    private String getDuration(String line) {
        try {
            return line.split("=")[1];
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Check if a line correspond to the fingerprint field.
     *
     * @param line the line to check.
     * @return true if the line correspond to the fingerprint field, false if not.
     */
    private boolean isFingerprintField(String line) {
        try {
            return line.split("=")[0].equals("FINGERPRINT");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the value of the fingerprint field.
     *
     * @param line the line holding the fingerprint value.
     * @return the duration value.
     */
    private String getFingerprint(String line) {
        try {
            return line.split("=")[1];
        } catch (Exception e) {
            return "";
        }
    }
}

/**
 * Used to analyze the result of a call to the fpcalc command line program.
 */
record FpCalcResult(String duration, String fingerprint) {
}
