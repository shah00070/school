package com.schooltriangle.service.interfaces;


import com.schooltriangle.pojos.InsuranceDetailsResult;
import com.schooltriangle.pojos.InsuranceRequest;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.PUT;

public interface UpdateInsuranceDetails {
	@PUT("/Profile/Update")
	void updateInsurance(@Header("authorization") String token, @Body InsuranceRequest object, Callback<InsuranceDetailsResult> callback);

}
