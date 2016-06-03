package com.schooltriangle.mylibrary.service.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

import com.schooltriangle.mylibrary.pojo.AddUserRequest;
import com.schooltriangle.mylibrary.pojo.AddUserResponse;

public interface AddUser {

	@POST("/membership/adduser")
	void user(@Body AddUserRequest addUserRequest, Callback<AddUserResponse> callback);
}
