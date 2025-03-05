package tn.esprit.tacheevaluation.repository;





import tn.esprit.tacheevaluation.entity.Diplome;
import tn.esprit.tacheevaluation.entity.Examen;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface DiplomeExamenInterfaceService {


    ByteArrayInputStream genererDiplomePDF(Long etudiantId, Long examenId);


    Double calculerMoyenneGenerale(Long etudiantId, Long formationId);


    List<Examen> obtenirHistoriqueExamens(Long etudiantId);
    List<Diplome> obtenirHistoriqueDiplomes(Long etudiantId);


    void enregistrerAudit(String action, Long utilisateurId, String details);


    ByteArrayInputStream exporterResultatsExcel(Long formationId);


    ByteArrayInputStream genererDiplomeAvecQRCode(Long etudiantId, Long examenId);


    void envoyerRappelExamen(Long etudiantId, Long examenId);
}
