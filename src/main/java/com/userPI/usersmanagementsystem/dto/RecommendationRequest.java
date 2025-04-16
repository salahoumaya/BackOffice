package com.userPI.usersmanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecommendationRequest {
    private Float quiz_score;
    private Integer user_id;

}
