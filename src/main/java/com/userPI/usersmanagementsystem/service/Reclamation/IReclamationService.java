package com.userPI.usersmanagementsystem.service.Reclamation;

import com.userPI.usersmanagementsystem.entity.Event;
import com.userPI.usersmanagementsystem.entity.Reclamation;
import com.userPI.usersmanagementsystem.entity.SujetPfe;
import com.userPI.usersmanagementsystem.entity.Training;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;

import java.util.List;

public interface IReclamationService {
 public Reclamation createReclamation(OurUsers user, String type, Long eventId, Integer trainingId, Integer sujetpfeId, String title, String description);
   public Reclamation getReclamationById(Long id);
   public List<Reclamation> getAllReclamations();

    public List<Reclamation> getReclamationsByUserId(Integer userId);

   public Reclamation updateStatusAndResponse(Long id, String status, String responseMessage);

    public List<Reclamation> getUnreadReclamationsByUserId(Integer userId);







}
