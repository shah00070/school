package com.schooltriangle.mylibrary.service.interfaces;

import retrofit.Callback;
import retrofit.http.GET;

import com.schooltriangle.mylibrary.pojo.ServiceRequestReasonListResponse;

public interface ServiceRequestReasonService {

	@GET("/ServiceRequest/ServiceRequestReason/4")
	void getReasons(Callback<ServiceRequestReasonListResponse> callback);
}
