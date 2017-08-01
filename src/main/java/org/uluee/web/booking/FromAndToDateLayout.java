package org.uluee.web.booking;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;

public class FromAndToDateLayout extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2328325726308818881L;
	private DateField fromDate;
	private DateField toDate;

	public FromAndToDateLayout() {
		createContents();
	}

	private void createContents() {
		setMargin(new MarginInfo(true, false, true, false));
		setSpacing(true);
		setHeight(null);
		fromDate = new DateField();
		toDate = new DateField();

		fromDate.setWidth(100, Unit.PERCENTAGE);
		toDate.setWidth(100, Unit.PERCENTAGE);

		addComponent(fromDate);
		addComponent(toDate);		
	}

	public DateField getFromDate() {
		return fromDate;
	}

	public void setFromDate(DateField fromDate) {
		this.fromDate = fromDate;
	}

	public DateField getToDate() {
		return toDate;
	}

	public void setToDate(DateField toDate) {
		this.toDate = toDate;
	}
	
	
}
