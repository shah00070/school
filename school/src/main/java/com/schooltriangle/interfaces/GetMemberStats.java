package com.schooltriangle.interfaces;

import com.schooltriangle.pojos.MemberStatsResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

public interface GetMemberStats {
	@GET("/membership/Stats")
	void getMemberStatistics(@Header("authorization") String token, @Query("memberId") String id, Callback<MemberStatsResponse> callback);
}
