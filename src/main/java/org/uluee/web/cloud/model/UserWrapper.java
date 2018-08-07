/**
 * 
 */
package org.uluee.web.cloud.model;

/**
 * @author ACTERNITY
 *
 */
public class UserWrapper implements java.io.Serializable{
	String familyName;
	String firstName;
	String loginName;
	String password;
	String cPassword;
	String email;
	String idUser;	
	public String getEmail() {
		return email;
	}
	public UserWrapper setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getIdUser() {
		return idUser;
	}
	public UserWrapper setIdUser(String idUser) {
		this.idUser = idUser;
		return this;
	}
	public String getFamilyName() {
		return familyName;
	}
	public UserWrapper setFamilyName(String familyName) {
		this.familyName = familyName;
		return this;
	}
	public String getFirstName() {
		return firstName;
	}
	public UserWrapper setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	public String getLoginName() {
		return loginName;
	}
	public UserWrapper setLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public UserWrapper setPassword(String password) {
		this.password = password;
		return this;
	}
	public String getcPassword() {
		return cPassword;
	}
	public UserWrapper setcPassword(String cPassword) {
		this.cPassword = cPassword;
		return this;
	}	
}
