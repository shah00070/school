package com.schooltriangle.pojos;

import com.schooltriangle.mylibrary.pojo.Status;

public class JeevSlotsResult {
	private Status Status;
	private JeevSlotsInfo Data;

	public Status getStatus() {
		return Status;
	}

	public void setStatus(Status status) {
		Status = status;
	}

	public JeevSlotsInfo getData() {
		return Data;
	}

	public void setData(JeevSlotsInfo data) {
		Data = data;
	}
}