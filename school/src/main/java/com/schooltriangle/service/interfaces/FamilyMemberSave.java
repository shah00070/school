package com.schooltriangle.service.interfaces;



import com.schooltriangle.mylibrary.pojo.AddressOfUser;
import com.schooltriangle.mylibrary.pojo.Status;
import com.schooltriangle.pojos.AddFamilyMember;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.PUT;

public interface FamilyMemberSave {
	@PUT("/Profile/Update")
	void addFamilyMember(@Header("X-Jeevom-RequestLocation") AddressOfUser locationObject, @Header("authorization") String token, @Body AddFamilyMember object, Callback<Status> callback);

}
