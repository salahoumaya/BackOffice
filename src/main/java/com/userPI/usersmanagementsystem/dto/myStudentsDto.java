package com.userPI.usersmanagementsystem.dto;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class myStudentsDto {
    private String username;
    private String trainingTitle;
    private LocalDateTime dateSubscription;
}
