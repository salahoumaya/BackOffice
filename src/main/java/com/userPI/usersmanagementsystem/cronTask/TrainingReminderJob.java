package com.userPI.usersmanagementsystem.cronTask;

import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.entity.Planning;
import com.userPI.usersmanagementsystem.entity.Training;
import com.userPI.usersmanagementsystem.repository.PlanningRepository;
import com.userPI.usersmanagementsystem.service.TwilioService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TrainingReminderJob {

    private final PlanningRepository planningRepository;
    private final TwilioService twilioService;

    public TrainingReminderJob(PlanningRepository planningRepository, TwilioService twilioService) {
        this.planningRepository = planningRepository;
        this.twilioService = twilioService;
    }

 //   @Scheduled(cron = "0 0 8 * * ?")
    //@Scheduled(fixedRate = 60000)
    @Transactional
    public void sendTrainingReminders() {
        Date tomorrow = getTomorrowDate();

        List<Planning> plannings = planningRepository.findByStartDate(tomorrow);
        for (Planning planning : plannings) {
            Training training = planning.getTraining();

            for (OurUsers user : training.getUsers()) {
                String message = "Hello " + user.getName() +
                        ", Reminder: Your training '" + training.getTitle() + "' starts tomorrow at " +
                        formatDate(planning.getStartDate()) + ". Don't miss it!";
                twilioService.sendSms(user.getNumTel(), message);
            }
        }
    }

    private Date getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
