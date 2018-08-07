package org.uluee.web.component.preference;

import java.util.List;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.UserWrapper;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class UserLayout extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7397234342897681980L;
	String sessionId = ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId();
	private List<UserWrapper> users;
	private int userIndex = 0; 
	private UsersForm userLayoutTemp ;
	public UserLayout(){
		loadUser();
		createContent();

	}

	private void loadUser() {
		users = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getUserWrapper(sessionId);
	}

	private void createContent() {
		setSpacing(true);
		
		if(users != null){
			userLayoutTemp = new UsersForm(users.get(userIndex));
			addComponent(createButtonLayout());
			addComponent(userLayoutTemp);
			setComponentAlignment(userLayoutTemp, Alignment.MIDDLE_CENTER);
		}
	}

	private HorizontalLayout createButtonLayout() {
		HorizontalLayout content = new HorizontalLayout();
		content.setWidth(100, Unit.PERCENTAGE);
		
		Button left = new Button();
		left.addStyleName(ValoTheme.BUTTON_SMALL);
		left.setIcon(FontAwesome.ARROW_LEFT);
		left.addClickListener(e->{
			if(userIndex == 0){
				userIndex = (users.size()-1);
			}else{
				userIndex--;
			}
			goToIndex(userIndex);
		});
		
		Button right = new Button();
		right.addStyleName(ValoTheme.BUTTON_SMALL);
		right.setIcon(FontAwesome.ARROW_RIGHT);
		right.addClickListener(e->{
			if(userIndex == (users.size()-1)){
				userIndex = 0;
			}else{
				userIndex++;
			}
			goToIndex(userIndex);
		});
		
		content.addComponents(left,right);
		content.setComponentAlignment(left, Alignment.TOP_LEFT);
		content.setComponentAlignment(right, Alignment.TOP_RIGHT);
		return content;
	}
	
	private void goToIndex(int index){
		UsersForm u = new UsersForm(users.get(index));
		replaceComponent(userLayoutTemp, u);
		userLayoutTemp = u;
	}


	private class UsersForm extends VerticalLayout{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private PasswordField passwordConfTf;
		private PasswordField passwordTf;
		private TextField emailTf;
		private TextField loginNameTf;
		private TextField firstNameTf;
		private TextField familyNameTf;
		private UserWrapper user;

		public UsersForm(UserWrapper user){
			this.user = user;
			createContents();
			initValue();
			setSizeUndefined();
		}
		
		public UsersForm(){
			createContents();
		}

		private void initValue() {
			familyNameTf.setValue(user.getFamilyName()==null ? "" :user.getFamilyName());
			firstNameTf.setValue(user.getFirstName() == null ? "" : user.getFirstName());
			loginNameTf.setValue(user.getLoginName() == null ? "" : user.getLoginName());
			emailTf.setValue(user.getEmail() == null ? "" : user.getEmail());

		}

		private void createContents() {
			setMargin(true);
			setSpacing(true);
			familyNameTf = new TextField("Family name");
			firstNameTf = new TextField("First name");
			loginNameTf = new TextField("Login name");
			emailTf = new TextField("Email");
			passwordTf = new PasswordField("Password");
			passwordConfTf = new PasswordField("Confirm password");

			HorizontalLayout buttonLayout = createButtonLayoutForm();

			addComponent(familyNameTf);
			addComponent(firstNameTf);
			addComponent(loginNameTf);
			addComponent(emailTf);
			addComponent(passwordTf);
			addComponent(passwordConfTf);
			addComponent(buttonLayout);
		}

		private HorizontalLayout createButtonLayoutForm(){
			HorizontalLayout content = new HorizontalLayout();
			content.setSpacing(true);
			Button newButton = new Button("New");
			Button saveButton = new Button("Save");
			Button deleteButton = new Button("Delete");

			newButton.addStyleName(ValoTheme.BUTTON_TINY);
			saveButton.addStyleName(ValoTheme.BUTTON_TINY);
			deleteButton.addStyleName(ValoTheme.BUTTON_TINY);

			newButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
			saveButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
			deleteButton.addStyleName(ValoTheme.BUTTON_DANGER);

			newButton.addClickListener(e->{
				familyNameTf.setValue("");
				firstNameTf.setValue("");
				loginNameTf.setValue("");
				emailTf.setValue("");
				passwordTf.setValue("");
				passwordConfTf.setValue("");
				user = new UserWrapper();
			});

			saveButton.addClickListener(e->{
				if(familyNameTf.getValue().isEmpty() || 
						firstNameTf.getValue().isEmpty() ||
						loginNameTf.getValue().isEmpty() ||
						emailTf.getValue().isEmpty() ||
						passwordTf.getValue().isEmpty() ||
						passwordConfTf.getValue().isEmpty()){
					Notification.show("Please complete the data", Type.ERROR_MESSAGE);
					return;
				}

				if(!passwordConfTf.getValue().equals(passwordTf.getValue())){
					Notification.show("Password does not match", Type.ERROR_MESSAGE);
					return;
				}

				familyNameTf.getValue();
				firstNameTf.getValue();
				loginNameTf.getValue();
				emailTf.getValue();
				passwordTf.getValue();
				passwordConfTf.getValue();

				user.setPassword(passwordTf.getValue()).setEmail(emailTf.getValue()).
				setcPassword(passwordConfTf.getValue()).setFamilyName(familyNameTf.getValue()).
				setFirstName(firstNameTf.getValue()).setLoginName(loginNameTf.getValue());
				
				boolean result = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().saveUser(user, sessionId);
				if(result){
					Notification.show("Successfully saved", Type.HUMANIZED_MESSAGE);
				}else{
					Notification.show("Error on saving", Type.ERROR_MESSAGE);
				}
			});

			content.addComponents(newButton, saveButton, deleteButton);
			return content;
		}


	}

}

