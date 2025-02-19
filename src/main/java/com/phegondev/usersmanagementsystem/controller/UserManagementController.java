package com.phegondev.usersmanagementsystem.controller;

import com.phegondev.usersmanagementsystem.dto.ReqRes;
import com.phegondev.usersmanagementsystem.entity.OurUsers;
import com.phegondev.usersmanagementsystem.service.UsersManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserManagementController {
    @Autowired
    private UsersManagementService usersManagementService;

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




    @PostMapping("/auth/logout")
    public ResponseEntity<ReqRes> logout(@RequestHeader("Authorization") String authHeader) {
        // Vérifier si le token est bien présent dans le header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(new ReqRes(400, "Missing or invalid Authorization header"));
        }

        // Extraire le token
        String token = authHeader.substring(7);

        // Appeler la méthode de service pour invalider le token
        return ResponseEntity.ok(usersManagementService.logout(token));
    }
    @PostMapping("/auth/forget-password")
    public ResponseEntity<ReqRes> forgetPassword(@RequestBody ReqRes request) {
        return ResponseEntity.ok(usersManagementService.forgetPassword(request.getEmail()));
    }
    @PostMapping("/auth/reset-password")
    public ResponseEntity<ReqRes> resetPassword(@RequestParam String token, @RequestBody ReqRes request) {
        return ResponseEntity.ok(usersManagementService.resetPassword(token, request.getPassword()));
    }





}
