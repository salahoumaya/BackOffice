package com.userPI.usersmanagementsystem.service.Reclamation;


import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ReclamationRuleEngine {
    public String determineStatusFromRules(String type, String message) {
        String content = message.toLowerCase();

        List<String> eventRejected = Arrays.asList("annulé", "reporté", "pas organisé", "retard", "absence");
        List<String> eventInProgress = Arrays.asList("organisation", "lieu", "horaire", "logistique", "salle");

        List<String> trainingRejected = Arrays.asList("inutile", "temps perdu", "pas utile", "trop basique", "ennuyeux");
        List<String> trainingInProgress = Arrays.asList("formateur", "contenu", "qualité", "programme", "rythme", "explication");

        List<String> sujetRejected = Arrays.asList("sujet refusé", "non valide", "non accepté", "hors thème");
        List<String> sujetInProgress = Arrays.asList("encadrant absent", "soutenance", "rapport", "correction", "validation");

        switch (type.toUpperCase()) {
            case "EVENT":
                if (containsAny(content, eventRejected)) return "REJECTED";
                if (containsAny(content, eventInProgress)) return "IN_PROGRESS";
                break;

            case "TRAINING":
                if (containsAny(content, trainingRejected)) return "REJECTED";
                if (containsAny(content, trainingInProgress)) return "IN_PROGRESS";
                break;

            case "SUJET_PFE":
                if (containsAny(content, sujetRejected)) return "REJECTED";
                if (containsAny(content, sujetInProgress)) return "IN_PROGRESS";
                break;
        }

        return "OPEN";
    }

    private boolean containsAny(String content, List<String> keywords) {
        return keywords.stream().anyMatch(content::contains);
    }
}

