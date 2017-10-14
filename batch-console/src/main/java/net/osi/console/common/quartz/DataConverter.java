package net.osi.console.common.quartz;

import java.util.Collection;
import java.util.List;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;

import org.quartz.JobDataMap;
import org.quartz.core.jmx.JobDataMapSupport;

import com.google.common.collect.Lists;

public class DataConverter {
	
	private DataConverter() {
		
	}
	
	public static final JobDetailEx toJobDetail(final CompositeData data) {
		final JobDetailEx jobDetail = new JobDetailEx();		
		
		jobDetail.setName((String)data.get("name"));
		jobDetail.setGroup((String)data.get("group"));
		jobDetail.setJobClassName((String)data.get("jobClass"));
		jobDetail.setDescription((String)data.get("description"));			
		jobDetail.setDurability((Boolean)data.get("durability"));
		jobDetail.setRequestsRecovery((Boolean)data.get("shouldRecover"));			
		jobDetail.setJobDataMap(JobDataMapSupport.newJobDataMap((TabularData) data.get("jobDataMap")));		
		
		return jobDetail;
	}
	
	public static final List<JobDetailEx> toJobDetails(final TabularData data) {		
    	final List<JobDetailEx> jobDetails = Lists.newArrayList();
		
		for (CompositeData each : (Collection<CompositeData>) data.values())
			jobDetails.add(DataConverter.toJobDetail(each));
		
		return jobDetails;
	}
	
	public static final List<KeyValue> toKeyValues(final JobDataMap jobDataMap) {
		final List<KeyValue> result = Lists.newArrayList();
		
		for (String key : jobDataMap.keySet()) {
			KeyValue data = new KeyValue(key, (String)jobDataMap.get(key));
			
			result.add(data);
		}
		
		return result;
	}
}
