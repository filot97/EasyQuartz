package net.osi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.core.jmx.JobDetailSupport;
import org.quartz.core.jmx.QuartzSchedulerMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jmx.access.MBeanProxyFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;

import net.osi.console.common.quartz.DataConverter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestQuartzJMX {
	
	@Autowired
	private MBeanProxyFactoryBean quartzProxy;
	
	@Test
	public void isStarted() throws Exception {
		QuartzSchedulerMBean quartz = (QuartzSchedulerMBean) quartzProxy.getObject();
		
		TabularData jobs = quartz.getAllJobDetails();		
		
		for (CompositeData job : (Collection<CompositeData>) jobs.values()) {
			//String jobClass = (String) job.get("jobClass");			
			
			//JobDetailSupport.newJobDetail(job);
			JobDetail jobDetail = DataConverter.toJobDetail(job);
			
			for (String key : jobDetail.getJobDataMap().keySet()) {
				System.out.println(key + ":" + jobDetail.getJobDataMap().getString(key));
			}
			
			/*
			TabularData data = (TabularData) job.get("jobDataMap");
			
			for (CompositeData test : (Collection<CompositeData>) data.values()) {
				System.out.println("=========");
				System.out.println(test.values());
			}
			*/
			
		}
		assertThat(quartz.isStarted()).isEqualTo(true);
	}
}