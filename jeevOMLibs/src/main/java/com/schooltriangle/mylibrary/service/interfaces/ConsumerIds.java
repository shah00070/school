package com.schooltriangle.mylibrary.service.interfaces;

import com.schooltriangle.mylibrary.pojo.ConsumerProfilesRespone;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ConsumerIds {

	@GET("/profile/Member/Profiles/{memberId}")
	void getConsumerProfileIds(@Path("memberId") int member_id, Callback<ConsumerProfilesRespone> callback);
}
