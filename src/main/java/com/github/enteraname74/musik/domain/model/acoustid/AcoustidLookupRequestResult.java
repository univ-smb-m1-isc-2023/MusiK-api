package com.github.enteraname74.musik.domain.model.acoustid;

import java.util.List;

/**
 * Represent the result of a lookup request on the Acoustid Api.
 */
public class AcoustidLookupRequestResult {
    private List<AcoustidMatch> results;
    private String status;

    public AcoustidLookupRequestResult(List<AcoustidMatch> results, String status) {
        this.results = results;
        this.status = status;
    }

    public AcoustidLookupRequestResult() {
    }

    public List<AcoustidMatch> getResults() {
        return results;
    }

    public void setResults(List<AcoustidMatch> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
