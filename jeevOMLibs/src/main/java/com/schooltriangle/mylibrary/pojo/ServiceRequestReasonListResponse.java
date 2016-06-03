package com.schooltriangle.mylibrary.pojo;

import com.schooltriangle.mylibrary.model.Status;

public class ServiceRequestReasonListResponse {
	private Status Status;
	private ServiceRequestReasonListContainer Data;
	public Status getStatus() {
		return Status;
	}
	public void setStatus(Status status) {
		Status = status;
	}
	public ServiceRequestReasonListContainer getData() {
		return Data;
	}
	public void setData(ServiceRequestReasonListContainer data) {
		Data = data;
	}
}
