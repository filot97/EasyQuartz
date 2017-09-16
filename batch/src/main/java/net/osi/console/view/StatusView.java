package net.osi.console.view;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@UIScope
@SpringView(name = StatusView.VIEW_NAME)
public class StatusView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "status";
	
	@Autowired
	@Qualifier("properties")
	private Properties quartzProperties;
	
	@PostConstruct
	void init() {
		addComponent(new Label("This is the status view"));
		addComponent(new Button("Status"));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show(VIEW_NAME);
		
		StdSchedulerFactory sf = new StdSchedulerFactory();

		try {
			sf.initialize(quartzProperties);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scheduler scheduler = null;
		try {
			scheduler = sf.getScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		System.out.println("====================");
		
		try {
			for(String group: scheduler.getJobGroupNames()) {
				      System.out.println("Found job identified by: " + group);
			    }
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
