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
<<<<<<< HEAD
=======
        private List<trainingUsersDto> trainingsNumber;
>>>>>>> 4628167b5c86a7a17fb119b4d98fa98300eb8319
}
