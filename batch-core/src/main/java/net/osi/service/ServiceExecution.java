package net.osi.service;

import org.quartz.JobDataMap;

public interface ServiceExecution {
	
	public void execute(JobDataMap jobDataAsMap);
}
