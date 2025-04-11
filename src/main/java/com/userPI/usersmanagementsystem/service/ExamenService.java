package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.entity.*;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.entity.user.UserRole;
import com.userPI.usersmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamenService {
    @Autowired
    private ExamenRepository examenRepository;
    @Autowired
    private TrainingRepository formationRepository;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private CourseRepository courseRepository;
    public List<OurUsers> getAllUsers() {
        return usersRepo.findAll().stream()
                .filter(user -> UserRole.USER.equals(user.getRole())) // Assuming Role is an enum
                .collect(Collectors.toList());
    }


    // ✅ Voir tous les examens (ETUDIANT, ADMIN, MODERATOR)
    public List<Examen> getAllExamens() {
        return examenRepository.findAll();
    }
    public List<Examen> getAllExamensbyFormation(Integer ifformation) {
        Course formation = courseRepository.findById(ifformation).get();

        return examenRepository.findAllByCourse(formation);
    }

    // ✅ Ajouter un examen (ADMIN, MODERATOR)
    public Examen addExamen(Examen examen,Integer formation) {

        Course formation1=courseRepository.findById(formation).get();
        examen.setFormation(formation1);
        return examenRepository.save(examen);
    }

    // ✅ Modifier un examen (ADMIN, MODERATOR)
    public Examen updateExamen(Long id, Examen examenDetails) {
        return examenRepository.findById(id).map(examen -> {
            examen.setTitre(examenDetails.getTitre());
            examen.setDate(examenDetails.getDate());
            examen.setDuree(examenDetails.getDuree());
            examen.setExamenT(examenDetails.getExamenT());
            examen.setSession(examenDetails.getSession());
            return examenRepository.save(examen);
        }).orElseThrow(() -> new RuntimeException("Examen non trouvé"));
    }

    // ✅ Supprimer un examen (ADMIN) avec un message de confirmation
    public String deleteExamen(Long id) {
        if (examenRepository.existsById(id)) {
            examenRepository.deleteById(id);
            return "Examen avec ID " + id + " supprimé avec succès.";
        } else {
            throw new RuntimeException("Examen avec ID " + id + " non trouvé.");
        }
    }


    public List<ExamenParticipant> getParticipantsByExamen(Long examenId) {
        Examen examen = examenRepository.findById(examenId)
                .orElseThrow(() -> new RuntimeException("Examen non trouvé"));

        return examen.getParticipants();
    }
    @Autowired
    private ExamenParticipantRepository examenParticipantRepository;

    public String assignUserToExamen(Long examenId, Integer userId) {
        Optional<Examen> examenOpt = examenRepository.findById(examenId);
        Optional<OurUsers> userOpt = usersRepo.findById(userId);

        if (examenOpt.isPresent() && userOpt.isPresent()) {
            Examen examen = examenOpt.get();
            OurUsers user = userOpt.get();

            ExamenParticipant participant = new ExamenParticipant();
            participant.setExamen(examen);
            participant.setUser(user);
            participant.setNote(0.0);
            participant.setMoyenne(0.0);

            examenParticipantRepository.save(participant);
            return "Utilisateur affecté à l'examen avec succès.";
        } else {
            return "Examen ou utilisateur non trouvé.";
        }
    }
    public String updateUserToExamen(Long id, Double note) {
        ExamenParticipant participant = examenParticipantRepository.findById(id).get();

        if (participant != null) {
            participant.setNote(note);

            examenParticipantRepository.save(participant);
            return "Note affecté à l'examen avec succès.";
        } else {
            return "Note ou utilisateur non trouvé.";
        }
    }
//    public List<ExamenParticipant> getMoyenne(Long idformation,Integer userId){
//        OurUsers ourUsers = usersRepo.findById(userId).get();
//       return  examenParticipantRepository.findByUser(ourUsers);
//    }
public Double getMoyenne(Long idFormation, Integer userId) {
    OurUsers user = usersRepo.findById(userId).orElse(null);
    if (user == null) return null;

    // Étape 1 : Récupérer tous les examens liés à cette formation
    List<Examen> examens = examenRepository.findByCourseId(idFormation);

    if (examens.isEmpty()) return 0.0;

    // Étape 2 : Pour chaque examen, récupérer les participations de cet utilisateur
    List<ExamenParticipant> participations = examenParticipantRepository.findByUserAndExamenIn(user, examens);

    if (participations.isEmpty()) return 0.0;

    // Étape 3 : Calculer la moyenne
    double total = 0.0;
    int count = 0;
    for (ExamenParticipant ep : participations) {
        if (ep.getMoyenne() != null) {
            total += ep.getMoyenne();
            count++;
        }
    }

    return count > 0 ? total / count : 0.0;
}

    public String calculerEtEnregistrerMoyenneParUtilisateur(Integer formationId) {
        Course formation = courseRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Course non trouvée"));

        List<Examen> examenList = examenRepository.findAllByCourse(formation);

        List<ExamenParticipant> allParticipants = examenParticipantRepository.findAll()
                .stream()
                .filter(p -> examenList.contains(p.getExamen()))
                .collect(Collectors.toList());

        if (allParticipants.isEmpty()) {
            return "Aucune note trouvée pour cette formation.";
        }

        Map<Integer, List<Double>> notesParUtilisateur = new HashMap<>();

        for (ExamenParticipant participant : allParticipants) {
            if (participant.getNote() != null) {
                Integer userId = participant.getUser().getId();
                notesParUtilisateur
                        .computeIfAbsent(userId, k -> new ArrayList<>())
                        .add(participant.getNote());
            }
        }

        for (Map.Entry<Integer, List<Double>> entry : notesParUtilisateur.entrySet()) {
            Integer userId = entry.getKey();
            List<Double> notes = entry.getValue();
            double moyenne = notes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

            for (ExamenParticipant participant : allParticipants) {
                if (participant.getUser().getId().equals(userId)) {
                    participant.setMoyenne(moyenne);
                    examenParticipantRepository.save(participant);
                }
            }
        }

        return "Moyennes calculées et enregistrées avec succès.";
    }


//    public Map<Integer, Double> calculerMoyenneParUtilisateur(Long formationId) {
//        Formation formation = formationRepository.findById(formationId)
//                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
//
//        List<Examen> examenList = examenRepository.findAllByFormation(formation);
//
//        List<ExamenParticipant> allParticipants = examenParticipantRepository.findAll()
//                .stream()
//                .filter(p -> examenList.contains(p.getExamen()))
//                .collect(Collectors.toList());
//        if (allParticipants.isEmpty()) {
//            throw new RuntimeException("Aucune note trouvée pour cette formation.");
//        }
//        Map<Integer, List<Double>> notesParUtilisateur = new HashMap<>();
//
//        for (ExamenParticipant participant : allParticipants) {
//            if (participant.getNote() != null) {
//                Integer userId = participant.getUser().getId();
//                notesParUtilisateur
//                        .computeIfAbsent(userId, k -> new ArrayList<>())
//                        .add(participant.getNote());
//            }
//        }
//        Map<Integer, Double> moyennesParUtilisateur = new HashMap<>();
//
//        for (Map.Entry<Integer, List<Double>> entry : notesParUtilisateur.entrySet()) {
//            Integer userId = entry.getKey();
//            List<Double> notes = entry.getValue();
//            double moyenne = notes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
//            moyennesParUtilisateur.put(userId, moyenne);
//        }
//        return moyennesParUtilisateur;
//    }



    public List<ExamenParticipant> getallexamen(Integer id) {
        OurUsers ourUsers = usersRepo.findById(id).get();

        return ourUsers.getExamens();
    }







}
