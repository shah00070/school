package com.schooltriangle.mylibrary.service.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

import com.schooltriangle.mylibrary.pojo.CallbackRequestResponse;
import com.schooltriangle.mylibrary.pojo.ClaimRequestBody;

public interface ClaimInterface {
	@POST("/ServiceRequest/Message/Create")
	void requestClaim(@Body ClaimRequestBody callbackRequest, Callback<CallbackRequestResponse> callback);
}
