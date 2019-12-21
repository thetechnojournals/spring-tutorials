package com.ttj.app.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    private Job importWordsJob;

    @GetMapping("/importWords")
    public void runJob(){
        try {
            JobParametersBuilder builder = new JobParametersBuilder();
            builder.addString("startDate", LocalDateTime.now().toString());

            jobLauncher.run(importWordsJob, builder.toJobParameters());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
