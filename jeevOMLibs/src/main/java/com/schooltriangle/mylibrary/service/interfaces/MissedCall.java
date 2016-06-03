package com.schooltriangle.mylibrary.service.interfaces;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Path;

import com.schooltriangle.mylibrary.pojo.MissedCallDataResponse;

public interface MissedCall {
	@POST("/membership/phone/{phone}/verifyViaMissedCall")
	void verifyMissedCall(@Path("phone") String phone, Callback<MissedCallDataResponse> callback);
}
