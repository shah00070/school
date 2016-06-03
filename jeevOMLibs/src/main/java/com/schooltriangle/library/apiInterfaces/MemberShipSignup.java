package com.schooltriangle.library.apiInterfaces;

import com.schooltriangle.library.pojo.ApiResponse;
import com.schooltriangle.library.pojo.MemberInformation;
import com.schooltriangle.library.pojo.MembershipSignupResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface MemberShipSignup {

	@POST("/membership/Signup")
	void memberShipSignUp(@Body MemberInformation memberInformation,
			Callback<ApiResponse<MembershipSignupResponse>> callback);

}
