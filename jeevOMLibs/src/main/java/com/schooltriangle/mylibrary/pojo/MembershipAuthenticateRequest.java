package com.schooltriangle.mylibrary.pojo;

public class MembershipAuthenticateRequest {
	private String EmailOrPhone;
	private String PasswordHash;
	private boolean doNotCheckEmailPhoneVerified;

	public String getEmailOrPhone() {
		return EmailOrPhone;
	}

	public void setEmailOrPhone(String emailOrPhone) {
		EmailOrPhone = emailOrPhone;
	}

	public String getPasswordHash() {
		return PasswordHash;
	}

	public void setPasswordHash(String passwordHash) {
		PasswordHash = passwordHash;
	}

	public boolean isDoNotCheckEmailPhoneVerified() {
		return doNotCheckEmailPhoneVerified;
	}

	public void setDoNotCheckEmailPhoneVerified(
			boolean doNotCheckEmailPhoneVerified) {
		this.doNotCheckEmailPhoneVerified = doNotCheckEmailPhoneVerified;
	}

}
