package net.osi.view;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView(name = StatusView.VIEW_NAME)
public class StatusView extends VerticalLayout implements View {
	
	public static final String VIEW_NAME = "status";

    @PostConstruct
    void init() {
        addComponent(new Label("This is the status view"));
        addComponent(new Button("Status"));
    }
    
	@Override
    public void enter(ViewChangeEvent event) {
		Notification.show(VIEW_NAME);
    }

}
