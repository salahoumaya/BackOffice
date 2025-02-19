package com.phegondev.usersmanagementsystem.config;

import com.phegondev.usersmanagementsystem.repository.RevokedTokenRepo;
import com.phegondev.usersmanagementsystem.service.JWTUtils;
import com.phegondev.usersmanagementsystem.service.OurUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    @Autowired
    private RevokedTokenRepo revokedTokenRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("🚨 Aucun token trouvé ou format incorrect !");
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7); // Récupérer uniquement le token sans "Bearer "

        // Vérifier si le token est révoqué
        if (revokedTokenRepo.findByToken(jwtToken).isPresent()) {
            System.out.println("🚨 Token révoqué détecté : " + jwtToken);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token has been revoked. Please log in again.");
            return;
        }

        userEmail = jwtUtils.extractUsername(jwtToken);
        System.out.println("✅ Utilisateur détecté : " + userEmail);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = ourUserDetailsService.loadUserByUsername(userEmail);

            // Vérifier si le token est toujours valide
            if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                System.out.println("✅ Token valide pour " + userEmail);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                System.out.println("🚨 Token invalide !");
            }
        }

        filterChain.doFilter(request, response);
    }

}
