package net.osi.console.common.jmx;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;

import org.quartz.core.jmx.JobDataMapSupport;
import org.quartz.impl.JobDetailImpl;

public class QuartzDataConverter {

	public static final JobDetailImpl toJobDetail(CompositeData data) {
		JobDetailImpl jobDetail = new JobDetailImpl();		
		
		jobDetail.setName((String)data.get("name"));
		jobDetail.setGroup((String)data.get("group"));
		jobDetail.setDescription((String)data.get("description"));			
		jobDetail.setDurability((Boolean)data.get("durability"));
		jobDetail.setRequestsRecovery((Boolean)data.get("shouldRecover"));			
		jobDetail.setJobDataMap(JobDataMapSupport.newJobDataMap((TabularData) data.get("jobDataMap")));	
		
		return jobDetail;
	}
}
