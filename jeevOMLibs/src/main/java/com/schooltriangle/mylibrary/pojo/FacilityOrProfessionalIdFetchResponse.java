package com.schooltriangle.mylibrary.pojo;

import com.google.gson.annotations.Expose;

public class FacilityOrProfessionalIdFetchResponse {

	@Expose
	private Status Status;
	@Expose
	private FacilityOrProfessionalIdFetchData Data;

	public Status getStatus() {
		return Status;
	}

	public void setStatus(Status Status) {
		this.Status = Status;
	}

	public FacilityOrProfessionalIdFetchData getData() {
		return Data;
	}

	public void setData(FacilityOrProfessionalIdFetchData Data) {
		this.Data = Data;
	}

}
