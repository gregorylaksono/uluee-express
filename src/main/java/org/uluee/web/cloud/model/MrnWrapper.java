/**
 * 
 */
package org.uluee.web.cloud.model;

import java.io.Serializable;

/**
 * @author ACTERNITY
 *
 */
public class MrnWrapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2766091213494811882L;
	private String mrn;
	
	public String getMrn() {
		return mrn;
	}	
	public void setMrn(String mrn) {
		this.mrn = mrn;		
	}
	
	
}
