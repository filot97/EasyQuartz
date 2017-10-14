package net.osi.console.common.quartz;

import org.quartz.JobDataMap;
import org.quartz.impl.JobDetailImpl;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
public class JobDetailEx extends JobDetailImpl {
	
	private String jobClassName;
	
	public JobDetailEx() {
		super();
	}
	
	public String jobDataMapToString() {
		JobDataMap jobDataMap = getJobDataMap();
		
		StringBuilder stringBuilder = new StringBuilder();
		
		for (String key : jobDataMap.getKeys()) {
			String value = jobDataMap.getString(key);
			
			stringBuilder.append(key);
			stringBuilder.append("=");
			stringBuilder.append(value);
			stringBuilder.append("\n");
		}
		
		return stringBuilder.toString();
	}
}
