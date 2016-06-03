package com.schooltriangle.service.interfaces;

import com.schooltriangle.mylibrary.pojo.AddressOfUser;
import com.schooltriangle.pojos.DocumentResult;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

public interface DownloadImageUrl {
	@GET("/Document/Download")
	void getDocumentUrl(@Header("X-Jeevom-RequestLocation") AddressOfUser locationObject, @Header("authorization") String token,
						@Query("DocumentId") int id, @Query("MemberId") String memberId,
						Callback<DocumentResult> callback);
}
