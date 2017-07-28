package org.uluee.web.cloud.model;

public class Commodity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4556298683405914173L;
	private Long commId;
	private Long annId;
	private String commName;
	private String sccCode;
	private String sccName;
	
	public Long getCommId() {
		return commId;
	}
	public void setCommId(Long comId) {
		this.commId = comId;
	}
	public Long getAnnId() {
		return annId;
	}
	public void setAnnId(Long annId) {
		this.annId = annId;
	}
	public String getCommName() {
		return commName;
	}
	public void setCommName(String comName) {
		this.commName = comName;
	}
	public String getSccCode() {
		return sccCode;
	}
	public void setSccCode(String sccCode) {
		this.sccCode = sccCode;
	}
	public String getSccName() {
		return sccName;
	}
	public void setSccName(String sccName) {
		this.sccName = sccName;
	}
	
}
