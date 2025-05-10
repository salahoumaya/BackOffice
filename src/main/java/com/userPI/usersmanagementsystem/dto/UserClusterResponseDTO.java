package com.userPI.usersmanagementsystem.dto;

import lombok.*;

import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserClusterResponseDTO {
    private Integer cluster;
    private String label;
    private String commentaire;

}
