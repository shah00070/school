package com.schooltriangle.pojos;

import com.schooltriangle.mylibrary.pojo.Status;

public class JeevProfileResult {

	private Status Status;
	private JeevProfileInfo Data;

	public Status getStatus() {
		return Status;
	}

	public void setStatus(Status status) {
		Status = status;
	}

	public JeevProfileInfo getData() {
		return Data;
	}

	public void setData(JeevProfileInfo data) {
		Data = data;
	}
}