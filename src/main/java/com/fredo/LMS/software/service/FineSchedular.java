package com.fredo.LMS.software.service;

import com.fredo.LMS.software.service.impl.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class FineSchedular {

    @Autowired
    private FineService fineService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleFineCalculation(){
        fineService.calculateFineDaily();
    }
}
