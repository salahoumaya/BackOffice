package com.userPI.usersmanagementsystem.entity.PFE;

import com.userPI.usersmanagementsystem.entity.SujetPfe;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CommentPFE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SujetPfe sujetPfe;

    @ManyToOne
    private OurUsers sender;

    @Lob
    private String content;

    @Lob
    private byte[] file;

    private String fileName;

    private LocalDateTime timestamp;
}
