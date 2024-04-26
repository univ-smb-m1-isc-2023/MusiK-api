package com.github.enteraname74.musik.domain.utils;

import com.github.enteraname74.musik.domain.model.MusicMetadata;
import com.github.enteraname74.musik.domain.model.acoustid.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class AcoustidResultAnalyzerTests
{
    @Test
    public void givenGoodRequestResult_whenRetrievingMetadata_thenShouldReturnCorrectMetadata() {
        MusicMetadata initialMetadata = new MusicMetadata("test", "test", "test");
        AcoustidLookupRequestResult requestResult = new AcoustidLookupRequestResult(
                List.of(new AcoustidMatch(
                        "id",
                        List.of(new AcoustidRecording(
                                List.of(new AcoustidArtist("1", "test")),
                                0,
                                "id",
                                List.of(new AcoustidReleaseGroup(
                                        List.of(new AcoustidArtist("1", "test")),
                                        "id",
                                        "test",
                                        "Album"
                                )),
                                "test"
                        )),
                        0.8f
                )),
                ""
        );

        AcoustidResultAnalyzer resultAnalyzer = new AcoustidResultAnalyzer(
                requestResult,
                initialMetadata
        );

        MusicMetadata foundMetadata = resultAnalyzer.getMusicMetadataFromRequest();
        MusicMetadata supposedReturn = new MusicMetadata(
                "test",
                "test",
                "test"
        );

        Assert.isTrue(supposedReturn.equals(foundMetadata), "The found metadata is not corresponding");
    }
}
