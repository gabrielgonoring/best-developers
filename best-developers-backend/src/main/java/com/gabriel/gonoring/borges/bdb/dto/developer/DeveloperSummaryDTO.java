package com.gabriel.gonoring.borges.bdb.dto.developer;

public class DeveloperSummaryDTO {
    private String developer;
    private String developerWebSiteUrl;
    private String developerIconUrl;

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getDeveloperWebSiteUrl() {
        return developerWebSiteUrl;
    }

    public void setDeveloperWebSiteUrl(String developerWebSiteUrl) {
        this.developerWebSiteUrl = developerWebSiteUrl;
    }

    public String getDeveloperIconUrl() {
        return developerIconUrl;
    }

    public void setDeveloperAvatarUrl(String developerIconUrl) {
        this.developerIconUrl = developerIconUrl;
    }
}
