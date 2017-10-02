package net.osi.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

import net.osi.console.view.MainView;

@SuppressWarnings("serial")
@SpringUI(path = "/console")
@Title("Quartz Console")
@Theme("batch-theme")
public class ConsoleUI extends UI {
	
	@Autowired
	private ApplicationContext applicationContext;
	
    @Override
    protected void init(VaadinRequest request) {    	
    	setContent(applicationContext.getBean(MainView.class));
    }
    
}