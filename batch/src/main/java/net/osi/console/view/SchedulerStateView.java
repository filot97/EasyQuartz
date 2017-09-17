package net.osi.console.view;

import javax.annotation.PostConstruct;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

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
@SpringView(name = SchedulerStateView.VIEW_NAME)
public class SchedulerStateView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "status";
	
	@Autowired
	private Scheduler scheduler;
	
	@PostConstruct
	void init() {
		addComponent(new Label("This is the status view"));
		addComponent(new Button("Status"));
	}

	@Override
	public void enter(ViewChangeEvent event) {		
		try {
			Notification.show(scheduler.getMetaData().toString());
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
