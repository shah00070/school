package com.schooltriangle.mylibrary.service.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

import com.schooltriangle.mylibrary.pojo.PhoneEmaiExistsResponse;
import com.schooltriangle.mylibrary.pojo.PhoneEmailExistsRequest;

public interface PhoneEmailExists {

	@POST("/membership/IsEmailExists")
	void isEmailExists(@Body PhoneEmailExistsRequest phoneEmailRequest, Callback<PhoneEmaiExistsResponse> callback);
}
