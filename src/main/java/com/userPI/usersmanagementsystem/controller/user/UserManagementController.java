package com.userPI.usersmanagementsystem.controller.user;

import com.userPI.usersmanagementsystem.dto.ReqRes;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;


import com.userPI.usersmanagementsystem.entity.user.UserFeedback;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import com.userPI.usersmanagementsystem.service.user.UserFeedbackService;

import com.userPI.usersmanagementsystem.service.user.UsersManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserManagementController {
    @Autowired
    private UsersManagementService usersManagementService;

    @Autowired
    UsersRepo usersRepo;
    @Autowired
    UserFeedbackService userFeedbackService;




    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@Valid @RequestBody ReqRes reg) {
        return ResponseEntity.ok(usersManagementService.register(reg));
    }


    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers(){
        return ResponseEntity.ok(usersManagementService.getAllUsers());

    }
    @GetMapping("/admin/get-all-moderators")
    public ResponseEntity<ReqRes> getAllModerators() {
        return ResponseEntity.ok(usersManagementService.getAllModerators());
    }


    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<ReqRes> getUSerByID(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));

    }

    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId, @RequestBody OurUsers reqres) {
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }


    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes response = usersManagementService.getMyInfo(email);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<ReqRes> deleteUSer(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }

    @PutMapping("/admin/approve-moderator/{userId}")
    public ResponseEntity<ReqRes> approveModerator(@PathVariable Integer userId, @RequestParam boolean approve) {
        return ResponseEntity.ok(usersManagementService.approveModerator(userId, approve));
    }



    @PostMapping("/user/feedback")
    public ResponseEntity<UserFeedback> addUserFeedback(@RequestParam Integer trainingId, @RequestParam Integer rating,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        OurUsers user = usersRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        // Ajoute user.getUser().getId() comme premier paramètre
        UserFeedback feedback = userFeedbackService.addUserFeedback(user.getId(), trainingId, rating);

        return ResponseEntity.ok(feedback);
    }






}
