package com.schooltriangle.pojos;

public class ServiceConfigInfo {
	private com.schooltriangle.mylibrary.pojo.Status Status ;
	private ServiceConfigResult Data;

	public ServiceConfigResult getData() {
		return Data;
	}

	public void setData(ServiceConfigResult data) {
		Data = data;
	}

	public com.schooltriangle.mylibrary.pojo.Status getStatus() {
		return Status;
	}

	public void setStatus(com.schooltriangle.mylibrary.pojo.Status status) {
		Status = status;
	}
}
