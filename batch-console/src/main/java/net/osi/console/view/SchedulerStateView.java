package net.osi.console.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import net.osi.console.design.SchedulerStateViewDesign;

@SuppressWarnings("serial")
@UIScope
@SpringView(name = SchedulerStateView.VIEW_NAME)
public class SchedulerStateView extends SchedulerStateViewDesign implements View {

	public static final String VIEW_NAME = "schedulerState";	
		
	@PostConstruct
	void init() {		
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {		
		
	}
}
