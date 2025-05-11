package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.dto.UserClusterViewDTO;
import com.userPI.usersmanagementsystem.service.AdminClusteringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/clustering")
@PreAuthorize("hasRole('ADMIN')")
public class AdminClusteringController {
    private final AdminClusteringService clusteringService;

    public AdminClusteringController(AdminClusteringService clusteringService) {
        this.clusteringService = clusteringService;
    }

    @GetMapping("/users")
    public List<UserClusterViewDTO> getClusters() {
        return clusteringService.getAllUserClusters();
    }
}