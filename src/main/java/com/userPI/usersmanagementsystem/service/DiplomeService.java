package com.userPI.usersmanagementsystem.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.userPI.usersmanagementsystem.entity.Diplome;
import com.userPI.usersmanagementsystem.entity.Training;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.DiplomeRepository;
import com.userPI.usersmanagementsystem.repository.ExamenRepository;
import com.userPI.usersmanagementsystem.repository.TrainingRepository;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class DiplomeService {
    @Autowired
    private DiplomeRepository diplomeRepository;
    @Autowired
    private ExamenRepository examenRepository;
    @Autowired
    private TrainingRepository formationRepository;
    @Autowired
    private UsersRepo usersRepo;
    // ✅ Créer un diplôme
    public Diplome createDiplome(Diplome diplome, Integer idformation, Integer iduser) {
        Training formation = formationRepository.findById(idformation)
                .orElseThrow(() -> new RuntimeException("Formation not found"));

        OurUsers user = usersRepo.findById(iduser)
                .orElseThrow(() -> new RuntimeException("User not found"));

        diplome.setFormation(formation);
        diplome.setUser(user);
        System.out.println(diplome +""+ diplome.getDateObtention());
        return diplomeRepository.save(diplome);
    }


    // ✅ Récupérer tous les diplômes
    public List<Diplome> getAllDiplomes() {
        return diplomeRepository.findAll();
    }

    // ✅ Récupérer un diplôme par son ID
    public Optional<Diplome> getDiplomeById(Long id) {
        return diplomeRepository.findById(id);
    }

    // ✅ Supprimer un diplôme
    public void deleteDiplome(Long id) {
        diplomeRepository.deleteById(id);
    }

    public Diplome updateDiplome(Long id, Diplome updatedDiplome) {
        return diplomeRepository.findById(id).map(diplome -> {
            diplome.setDateObtention(updatedDiplome.getDateObtention());
            diplome.setFormation(updatedDiplome.getFormation());
            return diplomeRepository.save(diplome);
        }).orElseThrow(() -> new RuntimeException("Diplôme avec ID " + id + " non trouvé"));
    }


    public void generateQRCode(String text, String filePath) throws Exception {
        int width = 300;
        int height = 300;
        String format = "png";

        // Générer le QR Code
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);

        // Sauvegarder l'image QR Code dans un fichier
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);

        System.out.println("📌 QR Code généré : " + filePath);
    }


    public String generateDiploma(Diplome diplome, OurUsers user) throws Exception {
        // 📂 Vérifier et créer le dossier "diplomes" s'il n'existe pas
        String directoryPath = "diplomes";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 📄 Définition des chemins de fichiers
        String pdfFilePath = directoryPath + "/diplome_" + diplome.getId() + ".pdf";
        String nom = "diplome_" + diplome.getId() + ".pdf";
        String qrCodeFilePath = directoryPath + "/qrcode_" + diplome.getId() + ".png";
        String logoPath = "logo.png"; // 🖼️ Assurez-vous d'avoir un logo ici

        // 📌 Générer le QR Code
        String qrCodeText = "Diplôme ID: " + diplome.getId() + ", Étudiant: " + user.getName();
        generateQRCode(qrCodeText, qrCodeFilePath);

        // 📄 Création du fichier PDF
        PdfWriter writer = new PdfWriter(pdfFilePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // 🖼️ Ajout du logo universitaire
        try {
            ImageData logoData = ImageDataFactory.create(logoPath);
            Image logo = new Image(logoData).scaleToFit(100, 100);
            logo.setFixedPosition(50, 750); // Positionner en haut à gauche
            document.add(logo);
        } catch (Exception e) {
            System.err.println("⚠️ Logo non trouvé, génération sans logo.");
        }

        // 🏫 Titre du diplôme (avec couleurs et taille ajustée)
        document.add(new Paragraph("Université Esprit")
                .setFontSize(22)
                .setBold()
                .setFontColor(new DeviceRgb(0, 0, 255)) // Bleu
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("DIPLÔME OFFICIEL")
                .setFontSize(20)
                .setBold()
                .setFontColor(new DeviceRgb(0, 0, 0)) // Noir
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new LineSeparator(new SolidLine())); // 📏 Ligne de séparation

        document.add(new Paragraph("\n"));

        // 📜 Corps du diplôme
        document.add(new Paragraph("Nous certifions que :")
                .setFontSize(14)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph(user.getName())
                .setFontSize(18)
                .setBold()
                .setFontColor(new DeviceRgb(0, 0, 0)) // Noir
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("a réussi la formation : " + diplome.getFormation().getTitle())
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Date d'obtention : " + diplome.getDateObtention())
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\n\n"));

        // 📸 Insérer le QR Code dans le PDF (ajusté à une taille correcte)
        ImageData qrImageData = ImageDataFactory.create(qrCodeFilePath);
        Image qrImage = new Image(qrImageData).scaleToFit(100, 100);
        qrImage.setFixedPosition(450, 100);
        document.add(qrImage);

        // ✍️ Signatures et cadre
        document.add(new LineSeparator(new SolidLine())); // 📏 Ligne de séparation

        // Signataire 1
        document.add(new Paragraph("\nDirecteur de l'Université")
                .setFontSize(12)
                .setBold()
                .setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("_________________________")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT));

        // Signataire 2
        document.add(new Paragraph("\nResponsable de Formation")
                .setFontSize(12)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("_________________________")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.RIGHT));

        // ✅ Fermer le document
        document.close();

        // 📝 Log de succès
        System.out.println("📄 Diplôme généré avec succès : " + pdfFilePath);
        diplome.setPath(nom);
        diplomeRepository.save(diplome);
        return pdfFilePath;
    }


}
