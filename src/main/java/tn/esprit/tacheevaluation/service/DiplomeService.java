package tn.esprit.tacheevaluation.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tacheevaluation.entity.Diplome;
import tn.esprit.tacheevaluation.entity.Formation;
import tn.esprit.tacheevaluation.entity.OurUsers;
import tn.esprit.tacheevaluation.repository.DiplomeRepository;
import com.itextpdf.layout.Document;
import java.io.File;
import java.io.FileNotFoundException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import tn.esprit.tacheevaluation.repository.ExamenRepository;
import tn.esprit.tacheevaluation.repository.FormationRepository;
import tn.esprit.tacheevaluation.repository.UsersRepo;

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
    private FormationRepository formationRepository;
    @Autowired
    private UsersRepo usersRepo;
    // ✅ Créer un diplôme
    public Diplome createDiplome(Diplome diplome,Long idformation,Integer iduser) {
        Formation formation = formationRepository.findById(idformation)
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
        String qrCodeFilePath = directoryPath + "/qrcode_" + diplome.getId() + ".png";

        // 📌 Générer le QR Code (contient l'ID du diplôme et le nom de l'étudiant)
        String qrCodeText = "Diplôme ID: " + diplome.getId() + ", Étudiant: " + user.getName();
        generateQRCode(qrCodeText, qrCodeFilePath);

        // 📄 Création du fichier PDF
        PdfWriter writer = new PdfWriter(pdfFilePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // 🏫 Informations du diplôme
        document.add(new Paragraph("Université XYZ"));
        document.add(new Paragraph("DIPLÔME OFFICIEL"));
        document.add(new Paragraph("Certifie que " + user.getName() + " a obtenu un diplôme."));
        document.add(new Paragraph("Formation : " + diplome.getFormation().getTitre()));
        document.add(new Paragraph("Date d'obtention : " + diplome.getDateObtention()));

        // 📸 Insérer le QR Code dans le PDF
        ImageData qrImageData = ImageDataFactory.create(qrCodeFilePath);
        Image qrImage = new Image(qrImageData);
        document.add(qrImage);

        // ✅ Fermer le document
        document.close();

        System.out.println("📄 Diplôme généré avec succès : " + pdfFilePath);

        return pdfFilePath;
    }


}
