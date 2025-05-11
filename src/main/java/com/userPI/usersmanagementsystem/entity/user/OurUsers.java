package com.userPI.usersmanagementsystem.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userPI.usersmanagementsystem.entity.ExamenParticipant;
import com.userPI.usersmanagementsystem.entity.Planning;
import com.userPI.usersmanagementsystem.entity.SujetPfe;
import com.userPI.usersmanagementsystem.entity.Training;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "ourusers")
@Data
public class OurUsers implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String name;
    private String password;
    private String city;
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    private String numTel;
    private Long CIN;
    @Enumerated(EnumType.STRING)
    private UserRole role;


    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.PENDING; // Par défaut, un MODERATOR est "PENDING"
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private List<Training> formations;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ExamenParticipant> examens;


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (role == UserRole.ADMIN) return true;
        return status == UserStatus.APPROVED;
    }
    @ManyToMany(mappedBy = "demandeurs")
    @JsonIgnore
    private List<SujetPfe> sujetsDemandes;

}
