package com.schooltriangle.library.pojo;

import com.schooltriangle.mylibrary.pojo.Status;

public class ApiResponse<T> {
	private Status Status;
	private T Data;

	public Status getStatus() {
		return Status;
	}

	public void setStatus(Status status) {
		Status = status;
	}

	public T getData() {
		return Data;
	}

	public void setData(T data) {
		Data = data;
	}

}
