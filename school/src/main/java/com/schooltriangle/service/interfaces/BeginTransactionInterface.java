package com.schooltriangle.service.interfaces;

import com.schooltriangle.library.pojo.ApiResponse;
import com.schooltriangle.mylibrary.pojo.AddressOfUser;
import com.schooltriangle.pojos.BeginTransactionRequest;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

public interface BeginTransactionInterface {
	@POST("/TransactionV1/Begin")
	void beginTransaction(@Header("X-Jeevom-RequestLocation") AddressOfUser locationObject, @Body BeginTransactionRequest object,
						  Callback<ApiResponse<Integer>> callback);
}