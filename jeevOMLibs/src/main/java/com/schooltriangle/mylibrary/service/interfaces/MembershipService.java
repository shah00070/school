package com.schooltriangle.mylibrary.service.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

import com.schooltriangle.mylibrary.pojo.MembershipInfo;
import com.schooltriangle.mylibrary.pojo.MembershipRequest;

public interface MembershipService {
	@POST("/membership/registeruser")
	void registerMember(@Body MembershipRequest membershipRequest, Callback<MembershipInfo> callback);
}
