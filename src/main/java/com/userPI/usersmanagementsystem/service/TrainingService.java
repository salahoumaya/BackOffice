package com.userPI.usersmanagementsystem.service;

//import com.userPI.usersmanagementsystem.dto.dashModerator;
//import com.userPI.usersmanagementsystem.dto.myStudentsDto;
import com.userPI.usersmanagementsystem.entity.Course;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.entity.Training;
import com.userPI.usersmanagementsystem.repository.CourseRepository;
import com.userPI.usersmanagementsystem.repository.TrainingRepository;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainingService implements ITrainingService {
    private final UsersRepo usersRepo;
    TrainingRepository trainingRepository;
    private CourseRepository courseRepository;
    private final UsersRepo userRepository;


    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public Training addTraining(Training training) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OurUsers trainer = null;

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            trainer = userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            if (training.getUsers() == null) {
                training.setUsers(new ArrayList<>());
            }
            if (training.getSubscriptionDates() == null) {
                training.setSubscriptionDates(new HashMap<>());
            }
        } else {
            throw new RuntimeException("Unauthorized request");
        }
        training.setTrainer(trainer);

        return trainingRepository.save(training);
    }
    @Override
    public Training addTrainingWithCourses(Training training) {
        if (training.getCourses() != null) {
            for (Course course : training.getCourses()) {

                course.setTraining(training);
            }
        }
        return trainingRepository.save(training);
    }

    public void deleteTrainingWithCourses(int id) {
        if (trainingRepository.existsById(id)) {
            trainingRepository.deleteById(id);
        } else {
            throw new RuntimeException("Training not found!");
        }
    }

    public Training updateTrainingWithCourses(int id, Training newTrainingData) {
        return trainingRepository.findById(id).map(training -> {

            training.setTitle(newTrainingData.getTitle());
            training.setLevel(newTrainingData.getLevel());
            training.setDescription(newTrainingData.getDescription());
            training.setTypeTraning(newTrainingData.getTypeTraning());

            training.getCourses().clear();
            if (training.getUsers() == null) {
                training.setUsers(new ArrayList<>()); // Ensure users list is initialized
            }
            if (training.getSubscriptionDates() == null) {
                training.setSubscriptionDates(new HashMap<>()); // Ensure subscriptionDates is initialized
            }

            return trainingRepository.save(training);
        }).orElseThrow(() -> new RuntimeException("Training not found!"));
    }

    @Override
    public Training addUserToTraining(int trainingId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            throw new RuntimeException("Unauthorized request");
        }
        OurUsers user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return trainingRepository.findById(trainingId).map(training -> {
            if (training.getUsers() == null) {
                training.setUsers(new ArrayList<>());
            }

            if (!training.getUsers().contains(user)) {
                training.getSubscriptionDates().put(user.getId(), LocalDateTime.now());
            } else {
                throw new RuntimeException("User is already subscribed to this training");
            }

            return trainingRepository.save(training);
        }).orElseThrow(() -> new RuntimeException("Training not found"));
    }



    public Training getTrainingById(int id) {
        return trainingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training not found!"));
    }

    @Override
    public List<Training> getTrainingsForAuthenticatedTrainer() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            throw new RuntimeException("Unauthorized request");
        }

        OurUsers trainer = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return trainingRepository.findByTrainer(trainer);
    }

    @Override
    public List<Training> getTrainingsForAuthenticatedStudent() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            throw new RuntimeException("Unauthorized request");
        }

        OurUsers student = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return trainingRepository.findForStudent(student.getId());
    }

//    @Override
//    public dashModerator getDashboard() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username;
//
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails) principal).getUsername();
//        } else {
//            throw new RuntimeException("Unauthorized request");
//        }
//
//        OurUsers trainer = userRepository.findByEmail(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        dashModerator myDashboard = new dashModerator();
//        List<Training> myTrainings = trainingRepository.findByTrainer(trainer);
//        myDashboard.setTotalTrainings(myTrainings.size());
//        int totalCourses =0;
//        int totalStudents =0;
//        List<myStudentsDto> myStudentsDtos = new ArrayList<>();
//
//        for (int i = 0; i < trainingRepository.findByTrainer(trainer).size(); i++) {
//            totalCourses+=myTrainings.get(i).getCourses().size();
//            totalStudents+=myTrainings.get(i).getUsers().size();
//            for (int j = 0; j < myTrainings.get(i).getUsers().size(); j++) {
//                LocalDateTime subscriptionDate = myTrainings.get(i).getSubscriptionDates().get(myTrainings.get(i).getUsers().get(j).getId());
//                myStudentsDto student = new myStudentsDto(myTrainings.get(i).getUsers().get(j).getName(),myTrainings.get(i).getTitle(),subscriptionDate);
//                myStudentsDtos.add(student);
//            }
//        }
//        myDashboard.setTotalCourses(totalCourses);
//        myDashboard.setTotalStudents(totalStudents);
//        myDashboard.setMyStudents(myStudentsDtos);
//        return myDashboard;
//    }


}
