package com.schooltriangle.mylibrary.service.interfaces;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import com.schooltriangle.mylibrary.pojo.FacilityOrProfessionalIdFetchResponse;

public interface FacilityOrProfessionalIdFetch {
	@GET("/profile/Member/{userid}")
	void onFetchProfessionalFacilityId(@Path("userid") String userid, Callback<FacilityOrProfessionalIdFetchResponse> callback);
}
