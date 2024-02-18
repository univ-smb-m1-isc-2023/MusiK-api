package com.github.enteraname74.musik.domain.handlerimpl.musicfingerprintreaderimpl;

import com.github.enteraname74.musik.domain.handler.MusicFingerprintReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

@Qualifier("FpCalc")
@Component
public class FpCalcMusicFingerprintReaderImpl implements MusicFingerprintReader {
    @Override
    public Optional<String> getFingerPrintFromMusic(String musicPath) {
        try {
            System.out.println("Will use FPCALC");
            String[] command = {"fpcalc", "-"};

            byte[] bytes = Files.readAllBytes(Paths.get(musicPath));

            System.out.println("Got bytes as :\n"+ Arrays.toString(bytes));
            Process process = Runtime.getRuntime().exec(command);
            System.out.println("Process is running...");

            try (OutputStream outputStream = process.getOutputStream()) {
                outputStream.write(bytes);
            } catch (Exception e) {
                System.out.println("EXCEPTION OCCURRED DURING OUTPUT FILE!!");
                System.out.println(e.getLocalizedMessage());
                return Optional.empty();
            }

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
