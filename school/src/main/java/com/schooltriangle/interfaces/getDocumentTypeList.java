package com.schooltriangle.interfaces;

import com.schooltriangle.mylibrary.pojo.AddressOfUser;
import com.schooltriangle.pojos.DocumentListResult;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;

public interface getDocumentTypeList {
	@GET("/DocumentType/list/all")
	void getDocumentTypeList(@Header("X-Jeevom-RequestLocation") AddressOfUser locationObject, Callback<DocumentListResult> callback);
}
