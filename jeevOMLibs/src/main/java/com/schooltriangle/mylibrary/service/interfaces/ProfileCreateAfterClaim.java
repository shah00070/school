package com.schooltriangle.mylibrary.service.interfaces;

import com.schooltriangle.mylibrary.pojo.MemberCreateResponse;

import retrofit.Callback;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface ProfileCreateAfterClaim {
	@PUT("/Profile/Create/{memberid}")
	void createMemberAfterClaim(@Path("memberid") String memberid, Callback<MemberCreateResponse> callback);
}
