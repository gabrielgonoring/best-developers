package com.gabriel.gonoring.borges.bdb.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchUsersResponse {

    @JsonProperty("total_count")
    private Long totalCount;

    @JsonProperty("incomplete_results")
    private Boolean incompleteResults;

    @JsonProperty("items")
    private List<UserGitHubSummary> items;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<UserGitHubSummary> getItems() {
        return items;
    }

    public void setItems(List<UserGitHubSummary> items) {
        this.items = items;
    }
}
