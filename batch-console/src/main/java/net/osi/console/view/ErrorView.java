package net.osi.console.view;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@UIScope
@SpringView(name = ErrorView.VIEW_NAME)
@EqualsAndHashCode
public class ErrorView extends VerticalLayout implements View {
	
	public static final String VIEW_NAME = "error";
	
	private Label explanation;
	
	@PostConstruct
    void init() {
        Label label = new Label("URL NOT FOUND");
        label.addStyleName(ValoTheme.LABEL_H1);
        
        addComponent(label);
        addComponent(explanation = new Label());
        
        Image image = new Image(null, new ThemeResource("image/vaadin-icon.jpg"));
        
        addComponent(image);
        
        setComponentAlignment(image,  Alignment.MIDDLE_CENTER);
    }
    
	@Override
    public void enter(ViewChangeEvent event) {
		explanation.setValue(String.format("You tried to navigate to view '%s' that does not exists.",
											event.getViewName()
										   )
							 );
    }
}
