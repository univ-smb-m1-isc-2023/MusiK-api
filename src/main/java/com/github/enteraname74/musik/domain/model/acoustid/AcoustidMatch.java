package com.github.enteraname74.musik.domain.model.acoustid;

import java.util.List;

/**
 * Represent a match from a request on the Acoustid Api.
 * It holds information about a potential match and the percentage of the certainty of the match.
 */
public class AcoustidMatch {
    private String id;

    private List<AcoustidRecording> recordings;

    private float score;

    public AcoustidMatch(String id, List<AcoustidRecording> recordings, float score) {
        this.id = id;
        this.recordings = recordings;
        this.score = score;
    }

    public AcoustidMatch() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AcoustidRecording> getRecordings() {
        return recordings;
    }

    public void setRecordings(List<AcoustidRecording> recordings) {
        this.recordings = recordings;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
