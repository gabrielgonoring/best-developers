package com.gabriel.gonoring.borges.bdb.controller;

import com.gabriel.gonoring.borges.bdb.dto.developer.DeveloperSummaryDTO;
import com.gabriel.gonoring.borges.bdb.service.developer.DeveloperGitHubService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    private DeveloperGitHubService developerService;

    @Autowired
    public DeveloperController(DeveloperGitHubService developerService) {
        this.developerService = developerService;
    }

    @GetMapping
    @ApiOperation("Return a list of developers")
    protected List<DeveloperSummaryDTO> searchUsersResponse(Integer page, Integer size){
        return developerService.getDevelopers(page, size);
    }
}
