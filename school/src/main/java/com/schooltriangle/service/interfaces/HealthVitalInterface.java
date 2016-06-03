package com.schooltriangle.service.interfaces;

import com.schooltriangle.library.pojo.ApiResponse;
import com.schooltriangle.pojos.HealthVitalsData;
import com.schooltriangle.pojos.MemberHealthCardsData;
import com.schooltriangle.pojos.RecordHealthVitalModel;
import com.schooltriangle.pojos.ResponseApi;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by user on 9/9/2015.
 */
public interface HealthVitalInterface {
    @GET("/Health/Groups")
    void getHealthGroups(@Header("X-Jeevom-RequestLocation") String locationObject, Callback<ResponseApi<List<HealthVitalsData>>> callback);

    @GET("/Health/GetMemberHealthCards/{member_id}/true")
    void getMemberAvailableHealthCards(@Header("X-Jeevom-RequestLocation") String locationObject, @Path("member_id") int id, Callback<ApiResponse<List<MemberHealthCardsData>>> callback);

    @PUT("/Health/{member_id}/Preferences/SaveIndicators")
    void updateHealthIndicators(@Header("X-Jeevom-RequestLocation") String locationObject, @Path("member_id") int id, @Body List<Integer> listOfId, Callback<ApiResponse<Boolean>> callback);

    @GET("/Health/GetMemberHealthCards/{member_id}/true")
    void getMemberCards(@Header("X-Jeevom-RequestLocation") String locationObject, @Path("member_id") int member_id, Callback<ApiResponse<List<MemberHealthCardsData>>> callback);

    @POST("/Health/Reading/Save")
    void saveIndicatorReading(@Body RecordHealthVitalModel object, Callback<ApiResponse<Boolean>> callback);
}
