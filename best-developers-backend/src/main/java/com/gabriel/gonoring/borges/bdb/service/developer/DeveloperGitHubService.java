package com.gabriel.gonoring.borges.bdb.service.developer;

import com.gabriel.gonoring.borges.bdb.client.SearchGitHubClient;
import com.gabriel.gonoring.borges.bdb.client.model.SearchUsersResponse;
import com.gabriel.gonoring.borges.bdb.client.model.UserGitHubSummary;
import com.gabriel.gonoring.borges.bdb.dto.developer.DeveloperSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloperGitHubService {

    private SearchGitHubClient searchGitHubClient;

    @Autowired
    public DeveloperGitHubService(SearchGitHubClient searchGitHubClient) {
        this.searchGitHubClient = searchGitHubClient;
    }

    public List<DeveloperSummaryDTO> getDevelopers(Integer page, Integer size){

        SearchUsersResponse searchUsersResponse = searchGitHubClient.getBestDevelopers(page, size);

        return searchUsersResponse.getItems().stream().map(this::convertToDeveloperSummaryDTO).collect(Collectors.toList());
    }

    private DeveloperSummaryDTO convertToDeveloperSummaryDTO(UserGitHubSummary userGitHubSummary){
        DeveloperSummaryDTO developerSummaryDTO = new DeveloperSummaryDTO();

        developerSummaryDTO.setDeveloper(userGitHubSummary.getLogin());
        developerSummaryDTO.setDeveloperWebSiteUrl(userGitHubSummary.getHtmlUrl());
        developerSummaryDTO.setDeveloperAvatarUrl(userGitHubSummary.getAvatarUrl());

        return developerSummaryDTO;
    }
}
