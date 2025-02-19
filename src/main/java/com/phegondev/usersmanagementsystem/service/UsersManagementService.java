package com.phegondev.usersmanagementsystem.service;

import com.phegondev.usersmanagementsystem.config.JWTAuthFilter;
import com.phegondev.usersmanagementsystem.dto.ReqRes;
import com.phegondev.usersmanagementsystem.entity.OurUsers;
import com.phegondev.usersmanagementsystem.entity.RevokedToken;
import com.phegondev.usersmanagementsystem.entity.UserRole;
import com.phegondev.usersmanagementsystem.entity.UserStatus;
import com.phegondev.usersmanagementsystem.repository.RevokedTokenRepo;
import com.phegondev.usersmanagementsystem.repository.UsersRepo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import com.phegondev.usersmanagementsystem.dto.ReqRes;




import java.util.*;

import io.jsonwebtoken.Claims;
@Service
public class UsersManagementService {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender; // Pour envoyer l'email


    @Autowired
    private RevokedTokenRepo revokedTokenRepo; // ✅ Injection du repository
    private Map<String, String> resetTokens = new HashMap<>();


    public ReqRes register(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();
        try {
            OurUsers ourUser = new OurUsers();
            ourUser.setEmail(registrationRequest.getEmail());
            ourUser.setCity(registrationRequest.getCity());
            ourUser.setName(registrationRequest.getName());
            ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUser.setImage(registrationRequest.getImage());
            ourUser.setNumTel(registrationRequest.getNumTel());  // ✅ Vérifie cette ligne
            ourUser.setCIN(registrationRequest.getCIN());

            if (registrationRequest.getRole() == null) {
                ourUser.setRole(UserRole.USER);
            } else {
                ourUser.setRole(registrationRequest.getRole());
            }

            if (ourUser.getRole() == UserRole.MODERATOR) {
                ourUser.setStatus(UserStatus.PENDING);
            } else {
                ourUser.setStatus(UserStatus.APPROVED);
            }

            OurUsers ourUsersResult = usersRepo.save(ourUser);
            if (ourUsersResult.getId() > 0) {
                resp.setOurUsers(ourUsersResult);
                resp.setMessage("User Registered Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }





    public ReqRes login(ReqRes loginRequest){
        ReqRes response = new ReqRes();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();

            // 🔹 Vérifier si le compte est activé
            if (user.getStatus() == UserStatus.PENDING) {
                response.setStatusCode(403);
                response.setMessage("Your account is pending approval.");
                return response;
            } else if (user.getStatus() == UserStatus.REJECTED) {
                response.setStatusCode(403);
                response.setMessage("Your account has been rejected.");
                return response;
            }

            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");

        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public ReqRes approveModerator(Integer userId, boolean approve) {
        ReqRes response = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                OurUsers moderator = userOptional.get();

                // Vérifier si c'est bien un MODERATOR
                if (moderator.getRole() != UserRole.MODERATOR) {
                    response.setStatusCode(400);
                    response.setMessage("User is not a moderator.");
                    return response;
                }

                // Modifier son statut
                moderator.setStatus(approve ? UserStatus.APPROVED : UserStatus.REJECTED);
                usersRepo.save(moderator);

                response.setStatusCode(200);
                response.setMessage("Moderator " + (approve ? "approved" : "rejected") + " successfully.");
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found.");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while updating status: " + e.getMessage());
        }
        return response;
    }






    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            OurUsers users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }

//pour admin
    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<OurUsers> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setOurUsersList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }
    public ReqRes getAllModerators() {
        ReqRes response = new ReqRes();
        try {
            List<OurUsers> moderators = usersRepo.findByRole(UserRole.MODERATOR);
            if (!moderators.isEmpty()) {
                response.setOurUsersList(moderators);
                response.setStatusCode(200);
                response.setMessage("List of all moderators retrieved successfully.");
            } else {
                response.setStatusCode(404);
                response.setMessage("No moderators found.");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }


    //pour admin
    public ReqRes getUsersById(Integer id) {
        ReqRes reqRes = new ReqRes();
        try {
            OurUsers usersById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setOurUsers(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes deleteUser(Integer userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes updateUser(Integer userId, OurUsers updatedUser) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                OurUsers existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setCity(updatedUser.getCity());
                existingUser.setRole(updatedUser.getRole());
                existingUser.setImage(updatedUser.getImage());
                existingUser.setNumTel(updatedUser.getNumTel());
                existingUser.setCIN(updatedUser.getCIN());

                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                OurUsers savedUser = usersRepo.save(existingUser);
                reqRes.setOurUsers(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }



    public ReqRes getMyInfo(String email){
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setOurUsers(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }

    public ReqRes logout(String token) {
        ReqRes response = new ReqRes();
        try {
            if (token == null || token.isBlank()) {
                response.setStatusCode(400);
                response.setMessage("Token is missing");
                return response;
            }

            // Vérifier si le token est déjà révoqué
            if (revokedTokenRepo.findByToken(token).isPresent()) {
                response.setStatusCode(400);
                response.setMessage("Token already revoked");
                return response;
            }

            // Extraire la date d'expiration du token
            Date expirationDate = jwtUtils.extractClaims(token, Claims::getExpiration);

            // Sauvegarder le token révoqué
            RevokedToken revokedToken = new RevokedToken();
            revokedToken.setToken(token);
            revokedToken.setExpirationDate(expirationDate);
            revokedTokenRepo.save(revokedToken);

            response.setStatusCode(200);
            response.setMessage("Logged out successfully. Token revoked.");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during logout: " + e.getMessage());
        }
        return response;
    }

    public ReqRes forgetPassword(String email) {
        ReqRes response = new ReqRes();

        Optional<OurUsers> userOptional = usersRepo.findByEmail(email);
        if (userOptional.isEmpty()) {
            response.setStatusCode(404);
            response.setMessage("User not found");
            return response;
        }

        // 🔹 Générer un token unique
        String resetToken = UUID.randomUUID().toString();
        resetTokens.put(resetToken, email);

        // 🔹 Envoyer un email de réinitialisation
        String resetLink = "http://localhost:8077/auth/reset-password?token=" + resetToken;
        sendResetEmail(email, resetLink);

        response.setStatusCode(200);
        response.setMessage("Reset link sent to your email.");
        return response;
    }

    private void sendResetEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Click the link to reset your password: " + resetLink);
        mailSender.send(message);
    }

    public ReqRes resetPassword(String token, String newPassword) {
        ReqRes response = new ReqRes();

        String email = resetTokens.get(token);
        if (email == null) {
            response.setStatusCode(400);
            response.setMessage("Invalid or expired token");
            return response;
        }

        OurUsers user = usersRepo.findByEmail(email).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepo.save(user);

        resetTokens.remove(token);

        response.setStatusCode(200);
        response.setMessage("Password updated successfully.");
        return response;
    }

}
