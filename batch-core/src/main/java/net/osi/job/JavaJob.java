package net.osi.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.osi.service.ServiceExecution;

@Service
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class JavaJob implements Job {
	private static final Logger LOG = LoggerFactory.getLogger(JavaJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {		
		final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		
		if (LOG.isDebugEnabled())
			LOG.debug(jobDataMap.toString());

		String targetObject = jobDataMap.getString("targetObject");
		
		if (LOG.isDebugEnabled())
			LOG.debug("targetObject = " + targetObject);

		try {
			Class<?> clazz = Class.forName(targetObject);
			
			ServiceExecution service =(ServiceExecution) clazz.newInstance();
			service.execute(jobDataMap);
		} catch (ClassNotFoundException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InstantiationException e) {
			LOG.error(e.getMessage(), e);
		}
	}

}
