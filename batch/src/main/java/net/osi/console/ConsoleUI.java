package net.osi.console;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import net.osi.console.design.MainLayoutDesign;
import net.osi.console.view.DashboardView;
import net.osi.console.view.MainVew;
import net.osi.console.view.SchedulerStateView;

@SuppressWarnings("serial")
@SpringUI(path = "/console")
@Title("Quartz Console")
@Theme("batch-theme")
public class ConsoleUI extends UI {
	
	private MainVew mainView;	
	
    @Override
    protected void init(VaadinRequest request) {    	
    	mainView = new MainVew();
    	
    	setContent(mainView);
    }
    
}