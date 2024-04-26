package com.github.enteraname74.musik.domain.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class FileUtilsTests {

    @Test
    public void givenCorrectFileName_whenRetrievingFileExtension_thenShouldRetrieveExtension() {
        Optional<String> extension = FileUtils.getFileExtension("test.m4a");

        Assert.isTrue(extension.isPresent(), "A file extension should have been found");
        Assert.isTrue(extension.get().equals("m4a"), "The found extension is not correct");
    }

    @Test
    public void givenWrongFileName_whenRetrievingFileExtension_thenShouldRetrieveNothing() {
        Optional<String> extension = FileUtils.getFileExtension("test");
        Assert.isTrue(extension.isEmpty(), "No extension should been found");
    }

    @Test
    public void givenGoodFileName_whenRetrievingFileContentType_thenShouldRetrieveCorrespondingContentType() {
        String contentType = FileUtils.getContentType("test.mp3");

        Assert.isTrue(contentType.equals("audio/mpeg"), "The found content-type is not correct");
    }

    @Test
    public void givenWrongFileName_whenRetrievingFileContentType_thenShouldRetrieveDefaultContentType() {
        String contentType = FileUtils.getContentType("test");

        Assert.isTrue(contentType.equals("application/octet-stream"), "The found content-type is not correct");
    }
}
