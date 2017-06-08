package org.bibalex.eol.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Date;

/**
 * Created by sara.mustafa on 4/10/17.
 */

@SpringBootApplication
public class SchedulerAPI {

    public static void main(String [] args){
        SpringApplication.run(SchedulerAPI.class, args);
    }

}
