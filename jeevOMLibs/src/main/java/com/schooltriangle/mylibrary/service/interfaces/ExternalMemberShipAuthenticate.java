package com.schooltriangle.mylibrary.service.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

import com.schooltriangle.library.pojo.ExternalLoginInfo;
import com.schooltriangle.mylibrary.pojo.MembershipAuthenticateResponse;

public interface ExternalMemberShipAuthenticate {
	@POST("/membership/Authenticate")
	void onSuccessFullMemberSignIn(
			@Body ExternalLoginInfo memAuthenticateRequest,
			Callback<MembershipAuthenticateResponse> callback);
}
