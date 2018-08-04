package org.uluee.web.component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class PreferencePage extends VerticalLayout implements View{

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public PreferencePage() {
		createContents();
	}

	private void createContents() {
		VerticalLayout mylocationLayout = createMyLocationLayout();
		VerticalLayout userLayout = createUserLayout();
		VerticalLayout partnershipLayout = createPartnershipLayout();
		
		TabSheet t = new TabSheet();
		t.addTab(mylocationLayout, "My location");
		t.addTab(userLayout, "User");
		t.addTab(partnershipLayout, "Parthership");
		t.setSizeFull();
		addComponent(t);
		
	}

	private VerticalLayout createPartnershipLayout() {
		VerticalLayout content = new VerticalLayout();
		
		return content;
	}

	private VerticalLayout createUserLayout() {
		VerticalLayout content = new VerticalLayout();
		return content;
	}

	private VerticalLayout createMyLocationLayout() {
		VerticalLayout content = new VerticalLayout();
		return content;
	}
	
	

}
