package com.schooltriangle.interfaces;

import com.schooltriangle.mylibrary.pojo.AddressOfUser;
import com.schooltriangle.pojos.AssociationResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;

public interface AssociationService {
	@GET("/Profile/Association")
	void getMemberAssociations(@Header("X-Jeevom-RequestLocation") AddressOfUser locationObject, @Header("authorization") String token, Callback<AssociationResponse> callback);
}
