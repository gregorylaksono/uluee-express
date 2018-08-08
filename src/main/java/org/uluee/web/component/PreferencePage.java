package org.uluee.web.component;

import org.uluee.web.component.preference.MyLocationLayout;
import org.uluee.web.component.preference.PartnershipLayout;
import org.uluee.web.component.preference.UserLayout;

import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class PreferencePage extends Panel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4114742748985621091L;

	
	
	public PreferencePage() {
		super("Preferences");
		createContents();
		
	}

	private void createContents() {
		VerticalLayout mylocationLayout = new MyLocationLayout();
		VerticalLayout userLayout = new UserLayout();
		VerticalLayout partnershipLayout = new PartnershipLayout();
		
		TabSheet t = new TabSheet();
		t.addTab(mylocationLayout, "My location");
		t.addTab(userLayout, "User");
		t.addTab(partnershipLayout, "Parthership");
		t.setSizeFull();
		setContent(t);
	}

	

}
