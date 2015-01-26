package com.intuit.Beans;

/* This is a bean class for storing all the information
 * with respect to URL collections in the database
 * 
 */

public class URLDbBean {
	
	// The key word for the URL
	private String urlKey;
	// The corresponding value for the URL
	private String urlValue;
	// The user email of the person who has created and hence the by default owner
	private String ownerEmail;
	// The user email of the person who last modified the key-value pair
	private String lastModifiedUserEmail;
	// The date the collection was last modified
	private String lastModifiedDate;
	// The status of the URL(200OK,401) which is basically the HTTP response
	private String urlStatus;
	// The org to which this URL belongs to
	private String orgId;
	// general information with respect to url
	private String urlInfo;
	
	//Default Constructor
	public URLDbBean(){
		
	}
	
	
	//Constructor with all fields present
	public URLDbBean(String urlKey, String urlValue, String ownerEmail,
			String lastModifiedUserEmail, String lastModifiedDate,
			String urlStatus, String orgId, String urlInfo) {
		super();
		this.urlKey = urlKey;
		this.urlValue = urlValue;
		this.ownerEmail = ownerEmail;
		this.lastModifiedUserEmail = lastModifiedUserEmail;
		this.lastModifiedDate = lastModifiedDate;
		this.urlStatus = urlStatus;
		this.orgId = orgId;
		this.urlInfo = urlInfo;
	}
	
	//Overloaded Constructor
	public URLDbBean(String urlKey, String urlValue, String ownerEmail,
				String lastModifiedUserEmail,
				String urlStatus, String orgId, String urlInfo) {
			super();
			this.urlKey = urlKey;
			this.urlValue = urlValue;
			this.ownerEmail = ownerEmail;
			this.lastModifiedUserEmail = lastModifiedUserEmail;
			this.urlStatus = urlStatus;
			this.orgId = orgId;
			this.urlInfo = urlInfo;
	}
	
	/**
	 * @return the urlKey
	 */
	public String getUrlKey() {
		return urlKey;
	}
	/**
	 * @param urlKey the urlKey to set
	 */
	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}
	/**
	 * @return the urlValue
	 */
	public String getUrlValue() {
		return urlValue;
	}
	/**
	 * @param urlValue the urlValue to set
	 */
	public void setUrlValue(String urlValue) {
		this.urlValue = urlValue;
	}
	/**
	 * @return the ownerEmail
	 */
	public String getOwnerEmail() {
		return ownerEmail;
	}
	/**
	 * @param ownerEmail the ownerEmail to set
	 */
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	/**
	 * @return the lastModifiedUserEmail
	 */
	public String getLastModifiedUserEmail() {
		return lastModifiedUserEmail;
	}
	/**
	 * @param lastModifiedUserEmail the lastModifiedUserEmail to set
	 */
	public void setLastModifiedUserEmail(String lastModifiedUserEmail) {
		this.lastModifiedUserEmail = lastModifiedUserEmail;
	}
	/**
	 * @return the lastModifiedDate
	 */
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	/**
	 * @return the urlStatus
	 */
	public String getUrlStatus() {
		return urlStatus;
	}
	/**
	 * @param urlStatus the urlStatus to set
	 */
	public void setUrlStatus(String urlStatus) {
		this.urlStatus = urlStatus;
	}
	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/**
	 * @return the urlInfo
	 */
	public String getUrlInfo() {
		return urlInfo;
	}
	/**
	 * @param urlInfo the urlInfo to set
	 */
	public void setUrlInfo(String urlInfo) {
		this.urlInfo = urlInfo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "URLDbBean [urlKey=" + urlKey + ", urlValue=" + urlValue
				+ ", ownerEmail=" + ownerEmail + ", lastModifiedUserEmail="
				+ lastModifiedUserEmail + ", lastModifiedDate="
				+ lastModifiedDate + ", urlStatus=" + urlStatus + ", orgId="
				+ orgId + ", urlInfo=" + urlInfo + "]";
	}
	
	
	
}
