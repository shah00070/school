package com.schooltriangle.mylibrary.service.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

import com.schooltriangle.mylibrary.pojo.PhoneOrEmailCodeGenerateResponse;

public interface PhoneCodeGenerate {
	@POST("/membership/ResendPhoneVerificationCode")
	void onPhoneCodeGenerate(@Body String phone, Callback<PhoneOrEmailCodeGenerateResponse> callback);

}
