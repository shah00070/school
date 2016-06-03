package com.schooltriangle.service.interfaces;

import com.schooltriangle.library.pojo.ApiResponse;
import com.schooltriangle.mylibrary.pojo.Status;
import com.schooltriangle.pojos.UpdateCellEmailInfo;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

public interface UpdateCellEmail {
	@POST("/membership/UpdateEmailCellNumber")
	void updateCellEmail(@Header("X-Jeevom-RequestLocation") String locationObject, @Header("authorization") String token,
						 @Body UpdateCellEmailInfo updateCellEmail,
						 Callback<ApiResponse<Status>> callback);
}
