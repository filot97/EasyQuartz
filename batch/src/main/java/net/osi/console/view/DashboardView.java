package net.osi.console.view;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView(name = DashboardView.VIEW_NAME)
public class DashboardView extends VerticalLayout implements View {	
	
	private static final long serialVersionUID = 3701988002798393077L;
	
	public static final String VIEW_NAME = "dashboard";

    @PostConstruct
    void init() {
        addComponent(new Label("This is a dashboard view"));
    }

    @Override
    public void enter(ViewChangeEvent event) {
    	Notification.show(VIEW_NAME);
    }

}
