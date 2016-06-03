package com.schooltriangle.service.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

import com.schooltriangle.pojos.Documents;
import com.schooltriangle.pojos.UploadDocumentObject;

public interface UploadDocuments {
	@POST("/Document/Upload")
	void startUploadingDocuments(@Header("X-Jeevom-RequestLocation") String locationObject, @Body UploadDocumentObject object,
								 Callback<Documents> result);
}
