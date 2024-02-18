package com.github.enteraname74.musik.domain.handlerimpl.musicfingerprintreaderimpl;

import com.github.enteraname74.musik.domain.handler.MusicFingerprintReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * Implementation of the MusicFingerprintReader using a python script to generate the fingerprint.
 */
@Qualifier("Python")
@Component
public class PythonMusicFingerprintReaderImpl implements MusicFingerprintReader {
    @Override
    public Optional<String> getFingerPrintFromMusic(String musicPath) {
        try {
            System.out.println("Will use PYTHON");
            Process process = Runtime.getRuntime().exec(new String[]{"python3", "./app/fingerprint.py", musicPath});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("Retrieved result is: ");
            while((line = in.readLine()) != null){
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // Successfully calculated fingerprint
                System.out.println("Got fingerprint!");
            } else {
                // Handle error
                System.err.println("Error calculating fingerprint");
            }
        } catch (Exception exception) {
            System.out.println("EXCEPTION OCCURRED!!");
            System.out.println(exception.getLocalizedMessage());
            return Optional.empty();
        }

        return Optional.empty();
    }
}
