package com.schooltriangle.mylibrary.service.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

import com.schooltriangle.library.pojo.AddExternalUserResponse;
import com.schooltriangle.library.pojo.ApiResponse;
import com.schooltriangle.mylibrary.model.AddExternalUserRequest;

public interface AddExternalUser {

	@POST("/membership/LoginAsExternalProvider")
	void addExternal(@Body AddExternalUserRequest addExternalUser,
			Callback<ApiResponse<AddExternalUserResponse>> callback);
}
