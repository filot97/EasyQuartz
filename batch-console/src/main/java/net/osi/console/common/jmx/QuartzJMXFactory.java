package net.osi.console.common.jmx;

import java.net.MalformedURLException;

import javax.management.MalformedObjectNameException;

import org.quartz.core.jmx.QuartzSchedulerMBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.access.MBeanProxyFactoryBean;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;

@Configuration
public class QuartzJMXFactory {
	
	@Value("${org.quartz.scheduler.jmx.host}")
	private String rmiHost;
	
	@Value("${org.quartz.scheduler.jmx.port}")
	private Integer rmiPort;
	
	@Value("${org.quartz.scheduler.jmx.objectName}")
	private String objectName;
	
	@Bean
	public MBeanServerConnectionFactoryBean serverConnectionFactory() throws MalformedURLException {
		MBeanServerConnectionFactoryBean factory = new MBeanServerConnectionFactoryBean();
		
		factory.setServiceUrl("service:jmx:rmi://" + rmiHost + "/jndi/rmi://localhost:" + rmiPort + "/jmxrmi");
		
		return factory;
	}
	
	@Bean
	public MBeanProxyFactoryBean proxyFactory(MBeanServerConnectionFactoryBean factory) throws MalformedObjectNameException {
		MBeanProxyFactoryBean proxy = new MBeanProxyFactoryBean();
		
		proxy.setObjectName(objectName);
		proxy.setProxyInterface(QuartzSchedulerMBean.class);
		proxy.setServer(factory.getObject());
		
		return proxy;
	}
}
