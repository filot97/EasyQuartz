package net.osi.job.common;

import org.quartz.JobDetail;
import org.quartz.Trigger;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobTrigger {	

	private JobDetail jobDetail;
    private Trigger trigger;    
}
