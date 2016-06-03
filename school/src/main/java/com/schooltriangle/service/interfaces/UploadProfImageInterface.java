package com.schooltriangle.service.interfaces;

import com.schooltriangle.pojos.DocumentBody;
import com.schooltriangle.pojos.UploadImageResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

public interface UploadProfImageInterface {
	@POST("/doctor/Profile/Photo/Upload")
	void upload(@Header("authorization") String token,
				@Body DocumentBody uploadImageRequest,
				Callback<UploadImageResponse> callback);
}
