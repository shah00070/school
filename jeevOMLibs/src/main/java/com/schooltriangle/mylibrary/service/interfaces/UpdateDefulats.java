package com.schooltriangle.mylibrary.service.interfaces;

import com.schooltriangle.mylibrary.pojo.Status;
import com.schooltriangle.mylibrary.pojo.UpdateDefaultModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.PUT;

public interface UpdateDefulats {

	@PUT("/Profile/Update")
	void updateDefaults(@Body UpdateDefaultModel object, Callback<Status> updated);
}
