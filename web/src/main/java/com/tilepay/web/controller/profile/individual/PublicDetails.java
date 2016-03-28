package com.tilepay.web.controller.profile.individual;

import com.tilepay.core.constant.FieldSize;

import javax.validation.constraints.Size;

public class PublicDetails {

    @Size(max = FieldSize.MAX_USERNAME)
	private String userName;
	
	private String displayName;

    @Size(max = FieldSize.MAX_CAPTION)
	private String caption;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

}
