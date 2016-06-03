package com.schooltriangle.interfaces;


import com.schooltriangle.mylibrary.pojo.AddressOfUser;
import com.schooltriangle.pojos.Documents;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

public interface GetDocumentListOfUser {

	@GET("/Document/List")
	void getDocumentList(@Header("X-Jeevom-RequestLocation") AddressOfUser locationObject, @Query("MemberId") String memberId, Callback<Documents> callback);
}
