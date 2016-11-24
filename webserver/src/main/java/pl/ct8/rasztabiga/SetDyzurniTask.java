package pl.ct8.rasztabiga;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.util.Date;

public class SetDyzurniTask implements Job {

    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        App.setDuzyrni();
        System.out.println("Set dyzurni: at " + new Date());
    }
}
