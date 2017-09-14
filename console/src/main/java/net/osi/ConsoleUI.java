package net.osi;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import net.osi.view.DashboardView;
import net.osi.view.StatusView;

@SpringUI(path = "/quartz/console")
@Title("Quartz Console")
public class ConsoleUI extends UI {	
	
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout mainArea = new VerticalLayout();
	private VerticalLayout headerArea = new VerticalLayout();
	private HorizontalLayout bodyArea = new HorizontalLayout();
	private VerticalLayout menuArea = new VerticalLayout();
	private VerticalLayout contentArea = new VerticalLayout();
	
	@Autowired
    private SpringViewProvider viewProvider;
	
	private Navigator navigator;
	
    @Override
    protected void init(VaadinRequest request) {
    	headerArea.addComponent(buildHeader());    	
    	headerArea.addStyleName(ValoTheme.MENU_TITLE);
    	headerArea.setMargin(false);

    	menuArea.addComponent(buildMenuHeader());
    	menuArea.addComponent(addMenuItem("Scheduler State"));
    	menuArea.addComponent(addMenuItem("Triggers"));
    	menuArea.addComponent(addMenuItem("Cron Triggers"));
    	menuArea.addComponent(addMenuItem("Job Details"));
    	//menuArea.addStyleName(ValoTheme.MENU_TITLE);
    	menuArea.setSizeFull();
    	
    	buildContentArea();
    	
    	contentArea.addStyleName(ValoTheme.MENU_TITLE);
    	contentArea.setSizeFull();
    	
    	bodyArea.addComponent(menuArea);
    	bodyArea.addComponent(contentArea);    	
    	bodyArea.setExpandRatio(menuArea, 1);
    	bodyArea.setExpandRatio(contentArea, 3);
    	bodyArea.setSizeFull();   	
    	
    	mainArea.addComponent(headerArea);
    	mainArea.addComponent(bodyArea);
    	mainArea.setExpandRatio(bodyArea, 1);
    	mainArea.setSizeFull();    	
    	
    	setContent(mainArea);
    }
    
    private Label buildHeader() {
    	final Label header = new Label("Quartz Console");    	
    	
    	header.addStyleName(ValoTheme.LABEL_H1);
    	header.addStyleName(ValoTheme.LABEL_BOLD);
    	
    	return header;    	
    }
    
    private Label buildMenuHeader() {
    	final Label menu = new Label("Management");
    	
    	menu.addStyleName(ValoTheme.LABEL_H2);
    	menu.addStyleName(ValoTheme.LABEL_BOLD);
    	
    	return menu;    	
    }
    
    private Button addMenuItem(String name) {
    	final Button button = new Button(name);
    	
    	//button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
    	
    	button.addClickListener(event -> {    		
    		navigator.navigateTo(DashboardView.VIEW_NAME);
    	});
    	
    	return button;
    }
    
    private void buildContentArea() {
    	navigator = new Navigator(this, contentArea);
		
    	navigator.addProvider(viewProvider);
    	
		navigator.addView(DashboardView.VIEW_NAME, new DashboardView());
		navigator.addView(StatusView.VIEW_NAME, new StatusView());		
		//navigator.setErrorView(ErrorView.class);
		
		navigator.navigateTo(DashboardView.VIEW_NAME);				
    }
    
    
    /*
    private HorizontalLayout buildMiddle() {
    	final HorizontalLayout layout = new HorizontalLayout();
    	
    	layout.addComponent(buildMenu());
    	layout.addComponent(buildView());
    	
    	return layout;
    }
    */
    
    /*
    private CssLayout buildMenu() {    	
		final CssLayout layout = new CssLayout();		
		final MenuBar menu = new MenuBar();			
		
		MenuItem console = menu.addItem("Quartz Console", null, null);
		
		console.addItem("Dashboard", item -> {
										navigator.navigateTo(DashboardView.VIEW_NAME);
			});
		
		console.addItem("Status", item -> {
										navigator.navigateTo(StatusView.VIEW_NAME);
			});
        
		layout.addComponent(menu);		
		
		return layout;
	}
    
    private CssLayout buildView() {
		final CssLayout viewArea = new CssLayout();
		
		viewArea.setSizeFull();    	
    	
		navigator = new Navigator(this, viewArea);		
		
		navigator.addView(DefaultView.VIEW_NAME, new DefaultView());
		navigator.addView(DashboardView.VIEW_NAME, new DashboardView());
		navigator.addView(StatusView.VIEW_NAME, new StatusView());
		navigator.setErrorView(ErrorView.class);
		
		navigator.navigateTo(DefaultView.VIEW_NAME);
		
		return viewArea;
	}
	*/
}