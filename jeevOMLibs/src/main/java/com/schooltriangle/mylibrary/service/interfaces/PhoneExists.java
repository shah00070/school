package com.schooltriangle.mylibrary.service.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

import com.schooltriangle.mylibrary.pojo.PhoneEmaiExistsResponse;
import com.schooltriangle.mylibrary.pojo.PhoneEmailExistsRequest;

public interface PhoneExists {

	@POST("/membership/IsPotentialPhoneExists")
	void isPhoneExists(@Header("authorization") String token,
			@Body PhoneEmailExistsRequest phoneEmailRequest,
			Callback<PhoneEmaiExistsResponse> callback);
}
