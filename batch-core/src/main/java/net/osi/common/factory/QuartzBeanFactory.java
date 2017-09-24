package net.osi.common.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class QuartzBeanFactory {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	@Qualifier("properties")
	private Properties quartzProperties;	

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();

		jobFactory.setApplicationContext(applicationContext);

		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactory(JobFactory jobFactory) throws Exception {
		SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();

		factoryBean.setJobFactory(jobFactory);
		factoryBean.setQuartzProperties(quartzProperties);
		factoryBean.setOverwriteExistingJobs(true);
		factoryBean.setAutoStartup(true);
		factoryBean.setWaitForJobsToCompleteOnShutdown(true);
		factoryBean.setDataSource(dataSource);
		factoryBean.setTransactionManager(transactionManager);
		factoryBean.setSchedulerName(quartzProperties.getProperty("org.quartz.scheduler.schedulerName"));		

		return factoryBean;
	}

	@Bean
	public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException,
			ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		scheduler.clear();

		return scheduler;
	}
}
