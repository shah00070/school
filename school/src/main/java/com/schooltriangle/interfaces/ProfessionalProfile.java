package com.schooltriangle.interfaces;

import com.schooltriangle.pojos.ProfileProfessionalResult;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

public interface ProfessionalProfile {
	@GET("/ProfessionalProfile/{id}")
	void getProfessionalProfile(@Header("X-Jeevom-RequestLocation") String locationObject, @Header("authorization") String token, @Path("id") String id,
								Callback<ProfileProfessionalResult> callback);
}
