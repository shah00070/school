package com.schooltriangle.mylibrary.service.interfaces;

import com.schooltriangle.mylibrary.pojo.ServiceRequestReasonListResponse;

import retrofit.Callback;
import retrofit.http.GET;

import retrofit.http.Path;

public interface ServiceRequestReasons {
	@GET("/ServiceRequest/ServiceRequestReason/{type}")
	void getServiceRequestReasons(@Path("type") int type, Callback<ServiceRequestReasonListResponse> callback);
}
