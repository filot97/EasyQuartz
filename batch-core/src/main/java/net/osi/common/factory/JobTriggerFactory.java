package net.osi.common.factory;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Configuration;

import net.osi.common.Constants;
import net.osi.job.common.JobBean;
import net.osi.job.common.JobTrigger;

@Configuration
public class JobTriggerFactory {
	public List<JobTrigger> createJobTrigger(final List<JobBean> jobs)
			throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final List<JobTrigger> jobTriggers = new ArrayList<>();
		for (JobBean job : jobs) {			
			JobTrigger jobTrigger = createJobTrigger(job);
			jobTriggers.add(jobTrigger);
		}
		
		return jobTriggers;
	}

	private JobTrigger createJobTrigger(final JobBean job) throws ClassNotFoundException {
		final JobDetail jobDetail = createJobDetail(job);
		final Trigger trigger = createTrigger(job, jobDetail);

		return new JobTrigger(jobDetail, trigger);
	}

	@SuppressWarnings("unchecked")
	private JobDetail createJobDetail(final JobBean job) throws ClassNotFoundException {
		Class<?> clazz = job.getJobClass();
		String jobName = job.getJobName();
		String jobGroup = job.getJobGroup();
		String description = job.getDescription();		

		return JobBuilder.newJob((Class<? extends Job>) clazz)
						 .setJobData(createJobDataMap(job.getJobDataAsMap()))
						 .storeDurably()
						 .withDescription(description)
						 .withIdentity(jobName, jobGroup)
						 .build();
	}

	private JobDataMap createJobDataMap(final Map<String, String> jobDataAsMap) {
		JobDataMap jobDataMap = new JobDataMap();

		jobDataMap.put(Constants.PPROPERTY, jobDataAsMap);

		return jobDataMap;
	}

	private Trigger createTrigger(final JobBean job, final JobDetail jobDetail) {
		String jobName = job.getJobName();
		String jobGroup = job.getJobGroup();
		String cronExpression = job.getCronExpression();		

		return TriggerBuilder.newTrigger()
							 .forJob(jobDetail)
							 .withSchedule(cronSchedule(cronExpression))
							 .withIdentity(jobName, jobGroup)
							 .build();
	}
}
