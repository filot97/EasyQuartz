package net.osi.console.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.Navigator;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;

import net.osi.console.design.MainLayoutDesign;

@SuppressWarnings("serial")
@UIScope
public class MainVew extends MainLayoutDesign {
	
	private Navigator navigator;
	
	@Autowired
    private SpringViewProvider viewProvider;	
	
	@PostConstruct
    protected void init() {        
		navigator = new Navigator(this.getUI(), content);
		
    	navigator.addProvider(viewProvider);    	
		navigator.addView(DashboardView.VIEW_NAME, new DashboardView());
		navigator.addView(SchedulerStateView.VIEW_NAME, new SchedulerStateView());		
		navigator.setErrorView(ErrorView.class);		
		navigator.navigateTo(DashboardView.VIEW_NAME);
		
		schedulerState.addClickListener(event -> navigator.navigateTo(SchedulerStateView.VIEW_NAME));
    }
}
