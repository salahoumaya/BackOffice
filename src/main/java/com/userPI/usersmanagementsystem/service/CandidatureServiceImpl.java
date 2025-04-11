package com.userPI.usersmanagementsystem.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.userPI.usersmanagementsystem.entity.Candidature;
import com.userPI.usersmanagementsystem.repository.CandidatureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;



@Service
@AllArgsConstructor
@Slf4j
public class CandidatureServiceImpl implements ICandidatureService {

    private final CandidatureRepository candidatureRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public List<Candidature> retrieveAllCandidatures() {
        List<Candidature> listC = candidatureRepository.findAll();
        log.info("Nombre total des candidats : " + listC.size());
        return listC;
    }

    @Override
    public Candidature retrieveCandidature(Long candidatureId) {
        return candidatureRepository.findById(candidatureId).orElse(null);
    }

    @Override
    public Candidature addCandidature(Candidature c) {
        // Sauvegarde de la candidature
        Candidature savedCandidature = candidatureRepository.save(c);

        // Vérification de nbr_exp avant d'envoyer l'email
        if (c.getEmail() != null && !c.getEmail().isEmpty() && c.getNbr_exp() > 3) {
            sendConfirmationEmail(c.getEmail()); // Envoi à l'email du candidat
        }

        return savedCandidature;
    }


    @Override
    public void sendConfirmationEmail(String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email); // Utilisation de l'email dynamique
            message.setSubject("Entretien Programmé");
            message.setText("Bonjour, \n\nNous vous informons qu'un entretien est programmé pour vous. \n\nCordialement,\nL'équipe de recrutement.");
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }


    @Override
    public void removeCandidature(Long candidatureId) {
        candidatureRepository.deleteById(candidatureId);
    }

    @Override
    public Candidature modifyCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    @Override
    public byte[] generateCandidaturePdf() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(20, 20, 20, 20);

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);

        Table headerTable = new Table(new float[]{1, 2});
        headerTable.setWidth(UnitValue.createPercentValue(100));

        String logoPath = "src/main/resources/static/logo.png";
        try {
            Image logo = new Image(ImageDataFactory.create(logoPath));
            logo.setWidth(60);
            headerTable.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER));
        } catch (Exception e) {
            headerTable.addCell(new Cell().add(new Paragraph(" ")).setBorder(Border.NO_BORDER));
        }

        headerTable.addCell(new Cell()
                .add(new Paragraph("Date : " + formattedDate)
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setFontSize(10))
                .setBorder(Border.NO_BORDER));

        document.add(headerTable);
        document.add(new Paragraph("Liste des Candidatures")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(15));

        Table table = new Table(new float[]{2, 3, 3, 4, 2, 3, 3});
        table.setWidth(UnitValue.createPercentValue(100));

        String[] headers = {"ID", "Nom", "Prénom", "Email", "Expérience", "Spécialité", "Statut"};
        for (String header : headers) {
            table.addHeaderCell(new Cell()
                    .add(new Paragraph(header).setBold().setFontColor(ColorConstants.WHITE))
                    .setBackgroundColor(new DeviceRgb(0, 102, 204))
                    .setTextAlignment(TextAlignment.CENTER));
        }

        List<Candidature> candidatures = candidatureRepository.findAll();
        boolean alternateColor = false;

        for (Candidature candidature : candidatures) {
            DeviceRgb rowColor = alternateColor ? new DeviceRgb(230, 240, 255) : new DeviceRgb(255, 255, 255);
            alternateColor = !alternateColor;

            table.addCell(new Cell().add(new Paragraph(String.valueOf(candidature.getCandidatId()))).setBackgroundColor(rowColor));
            table.addCell(new Cell().add(new Paragraph(candidature.getNom())).setBackgroundColor(rowColor));
            table.addCell(new Cell().add(new Paragraph(candidature.getPrenom())).setBackgroundColor(rowColor));
            table.addCell(new Cell().add(new Paragraph(candidature.getEmail())).setBackgroundColor(rowColor));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(candidature.getNbr_exp()))).setBackgroundColor(rowColor));
            table.addCell(new Cell().add(new Paragraph(candidature.getSpecialite())).setBackgroundColor(rowColor));
            table.addCell(new Cell().add(new Paragraph(candidature.getStatut().toString())).setBackgroundColor(rowColor));
        }
        document.add(table);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}

