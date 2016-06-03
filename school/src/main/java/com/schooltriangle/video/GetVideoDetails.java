package com.schooltriangle.video;

import com.schooltriangle.library.pojo.ApiResponse;
import com.schooltriangle.pojos.VideoRequestData;
import com.schooltriangle.pojos.VideoResponse;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

public interface GetVideoDetails {
	@POST("/intv/video/Create/{appointmentId}")
	void getVideoDetails(@Path("appointmentId") String app_id,
						 @Header("authorization") String token,
						 @Body VideoRequestData videoRequestData,
						 retrofit.Callback<ApiResponse<VideoResponse>> callback);

}
