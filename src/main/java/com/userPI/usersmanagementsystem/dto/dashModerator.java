package com.userPI.usersmanagementsystem.dto;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class dashModerator {
        private int totalTrainings;
        private int totalCourses;
        private int totalStudents;
        private List<myStudentsDto> myStudents;
}
