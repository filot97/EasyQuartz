package net.osi;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import net.osi.common.factory.JobTriggerFactory;
import net.osi.job.common.JobBean;
import net.osi.job.common.JobTrigger;

@SpringBootApplication
@ImportResource("classpath:config/context-jobs.xml")
public class QuartzApplication implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(QuartzApplication.class);
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	@Autowired
	private JobTriggerFactory jobTriggerFactory;	
	
	@Resource(name="jobs")	
	private List<JobBean> jobs;
	
	public static void main(String[] args) {
		SpringApplication.run(QuartzApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.debug("## > CommandLineRunner Implementation...");

		for (String arg : args)
			LOG.info(arg);
		
		this.startScheduler();
	}
	
	private void startScheduler() throws SchedulerException, ClassNotFoundException,
										 IllegalAccessException, IllegalArgumentException,
										 InvocationTargetException {
		Scheduler scheduler = this.schedulerFactoryBean.getScheduler();

		scheduler.clear();		

		List<JobTrigger> jobTriggers = this.jobTriggerFactory.createJobTrigger(jobs);

		for (JobTrigger jobTrigger : jobTriggers)
			scheduler.scheduleJob(jobTrigger.getJobDetail(), jobTrigger.getTrigger());		

		scheduler.start();
	}
}
