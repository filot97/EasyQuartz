package net.osi;

import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuartzApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(QuartzApplication.class);
	
	@Autowired
	private Scheduler scheduler;
	
	public static void main(String[] args) {
		SpringApplication.run(QuartzApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.debug("## > CommandLineRunner Implementation...");

		for (String arg : args)
			LOG.info(arg);

		scheduler.start();
	}	
}
