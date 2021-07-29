package com.gabriel.gonoring.borges.bdb.client;

import com.gabriel.gonoring.borges.bdb.client.exception.GitHubForbiddenException;
import com.gabriel.gonoring.borges.bdb.client.model.SearchUsersResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import reactor.core.publisher.Mono;

import java.net.URI;


@Component
public class SearchGitHubClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchGitHubClient.class);

    private WebClient webClientGitHub;

    @Autowired
    public SearchGitHubClient(WebClient webClientGitHub) {
        this.webClientGitHub = webClientGitHub;
    }

    public SearchUsersResponse getBestDevelopers(Integer page, Integer perPage){

        try {
            LOGGER.info("Search developers in github");
            return webClientGitHub.get()
                    .uri(uriBuilder -> createUriToSearchUsers(uriBuilder, page, perPage))
                    .retrieve()
                    .bodyToMono(SearchUsersResponse.class)
                    .block();
        } catch (WebClientResponseException we) {
            LOGGER.warn("Github blocked the request. Caused by: {}", we.getResponseBodyAsString(), we);
            throw new GitHubForbiddenException("GitHub blocked your request, wait a moment and try again");
        }
    }

    private URI createUriToSearchUsers(UriBuilder uriBuilder,Integer page, Integer perPage){
        return uriBuilder.path("/search/users")
                .queryParam("q","type:user")
                .queryParam("sort","followers")
                .queryParam("per_page", perPage)
                .queryParam("page", page)
                .build();
    }

    private static Mono<ClientResponse> logBody(ClientResponse response) {
        if (response.statusCode() != null && (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError())) {
            return response.bodyToMono(String.class)
                    .flatMap(body -> {
                        System.out.println("Body is "+ body);
                        return Mono.error(new RuntimeException(body));
                    });
        } else {
            return Mono.just(response);
        }
    }
}
