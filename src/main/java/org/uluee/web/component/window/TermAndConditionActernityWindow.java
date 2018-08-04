package org.uluee.web.component.window;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TermAndConditionActernityWindow extends Window{
	
	public TermAndConditionActernityWindow() {
		createContents();
	}

	private void createContents() {
		VerticalLayout contentLayout = new VerticalLayout();
		contentLayout.setMargin(true);
		contentLayout.setSpacing(true);
		
		Label title1 = new Label();
		Label title2 = new Label();
		Label content1 = new Label();
		Label content2 = new Label();
		Label content3 = new Label();
		title1.setContentMode(ContentMode.HTML);
		title2.setContentMode(ContentMode.HTML);
		content1.setContentMode(ContentMode.HTML);
		content2.setContentMode(ContentMode.HTML);
		content3.setContentMode(ContentMode.HTML);
		
		
		title1.setValue("<span style='text-align:center';><h3>Standard Terms and Conditions for Users</h3></span>"
				+ "<bre> <span style='text-align:center';> <h3><b><i>of the Acternity software</i></b></h3> </span>");
		
		content1.setValue("<p style='text-align:center;'><u>Operating Policies</u></p>");
		content2.setValue("<span><p>Please read this Agreement carefully before accessing or using the Service. "
				+ "By accessing or using the Service, you agree to be bound by the terms and conditions set forth below. "
				+ "If you do not wish to be bound by these terms and conditions, you may not access or use the Service. "
				+ "<b>ACT Aviation Centre of Technology GmbH (hereinafter „ACT“)</b> may modify this Agreement at any time, "
				+ "and such modifications shall be effective immediately upon posting of the modified Agreement. "
				+ "You agree to review the Agreement periodically to be aware of such modifications and your continued access or use of the Service shall be deemed your conclusive acceptance of the modified Agreement.</p></span>");

		content3.setValue("<span style='text-align:center;'><p><u>Subject Matter, Registration, Identification</u></p></span>"
				+ "<p>1. ACT developed the Acternity software which is protected by patent and European copyright and is made available to all companies involved in the cargo business.</p>"
				+ "<p>2. Any company involved in the cargo business (e.g. airline, ocean carrier, forwarder, sales agent, shipper) can register on the Acternity website as a User.</p>"
				+ "<p>3. The Acternity service is free of charge unless explicitly agreed upon otherwise in an Annex to this contract.</p>"
				+ "<p>4. Upon registration, the User will be given an ID by ACT and shall choose a password. Both are necessary for the User account.</p>"
				+ "<p style='text-align:center;'><u>Obligations of the User</u></p>"
				+ "<p>5. The User has a duty to answer all the questions asked during registration completely, accurately and truthfully, to enter the data required for his cargo business into the system as specified by <b><i>Acternity</i></b> and always keep such data, including a hyperlink to the User's Standard Terms and Conditions of Business, up-to-date.</p>"
				+ "<p>6. The User warrants that he is authorised to enter such data (e.g. is entitled to act on behalf of a particular airline), that data entry does not infringe any third-party rights and that the content does not violate any laws or regulations.</p>"
				+ "<p>7. The User has a duty to check the entered data for completeness, accuracy and correctness before sending it to the Acternity system. The User shall also verify that the entries and data processes proposed by <b><i>Acternity</i></b> are appropriate for the User's business. ACT shall only be liable for the correct sending of the data. Sole liability for the correctness, accuracy and fitness for purpose of the entered and selected data and data processes lies with the User.</p>"
				+ "<p>8. The User shall ensure that only those persons authorised by the User can use his or her ID and password.</p>"
				+ "<p>9. The User shall be liable for the correctness, accuracy and lawfulness of the entered data, whether or not the User themselves or a person authorised by them or acting as their (sub-)agent has entered the data. The User agrees to indemnify, defend and hold ACT, directors, employees, agents, licensors, suppliers and any third party information providers to the Service harmless from and against all losses, expenses, damages and costs, including reasonable attorneys’ fees, resulting from any violation of this Agreement by the User.</p>"
				+ "<p>10. The User shall not be entitled to sell, rent or otherwise distribute <b><i>Acternity</b></i>, or parts thereof or access to it to third parties without written permission.</p>"
				+ "<p style='text-align:center'><u>Duties of ACT</u></p>"
				+ "<p>11. ACT undertakes to ensure that the data required for the User's cargo business and entered into the system by him are available and accessible to all other Acternity Users at all times. Necessary suspensions of service for maintenance work, development or improvements are exempt herefrom.</p>"
				+ "<p>12. ACT reserves the right to change, modify or adapt Acternity accordingly in case of regulatory or statutory changes or technical innovation.</p>"
				+ "<p>13. ACT shall protect the data entered by the User against unauthorised access by third parties (e.g. data theft, content manipulation or blocking).</p>"
				+ "<p>14. ACT shall ensure that the data entered by the User are visible in the network only. ACT shall not be entitled to make the data accessible to third parties outside the network. ACT shall be entitled to pass on transport process data in anonymised form to third parties.</p>"
				+ "<p>15. ACT warrants that <b><i>Acternity</b></i> does not use cookies, but points out that other websites linking to or from Acternity may use cookies.</p>"
				+ "<p>16. ACT makes all entries dating back at least three months available to the User free of charge for inspection and download. Information on entries dating back more than three months is available for a fee.</p>"
				+ "<p>17. ACT shall supply data transfer services only. ACT shall not be liable for the correctness or accuracy of the data entered into <b><i>Acternity</b></i> by the User or any other authorised persons. ACT will not be liable for any incidental, consequential, or indirect damages (including, but not limited to, damages for loss of profits, business interruption, loss of programs or information, and the like) arising out of the use of or inability to use the Service, or any information, or transactions provided on the Service or downloaded or hyperlinked from the Service. ACT will not be liable for any claim attributable to errors, omissions, or other inaccuracies in the Service and/or materials or information downloaded through, or hyperlinked from, the Service.</p>"
				+ "<p>18. ACT cannot and does not guarantee or warrant that files available for downloading through the Service will be free of infection or viruses, worms, Trojan horses or other code that manifest contaminating or destructive properties. The User is responsible for implementing sufficient procedures and checkpoints to satisfy his particular requirements for accuracy of data input and output, and for maintaining a means external to the Service for the reconstruction of any lost data. ACT does not warrant that the Service will be uninterrupted or error-free or that defects in the Service will be corrected.</p>"
				+ "<p>19. Serious violation of this Agreement by the User shall entitle ACT to exclude the User from further use of Acternity.</p>"
				+ "<p>20. All legal relations between ACT and the User shall be exclusively governed by German law. Legal venue for all disputes is Berlin.</p>");
		
		contentLayout.addComponent(title1);
		contentLayout.addComponent(title2);
		contentLayout.addComponent(content1);
		contentLayout.addComponent(content2);
		contentLayout.addComponent(content3);
		setModal(true);
		setResizable(false);
		setClosable(true);
		setContent(contentLayout);
		setWidth(600, Unit.PIXELS);
		setHeight(450, Unit.PIXELS);
	}

}
