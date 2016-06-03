package com.schooltriangle.pojos;

public class JeevSearch {
	private com.schooltriangle.mylibrary.pojo.Status Status;

	public com.schooltriangle.mylibrary.pojo.Status getStatus() {
		return Status;
	}

	public void setStatus(com.schooltriangle.mylibrary.pojo.Status status) {
		Status = status;
	}

	public JeevSearchInfo getData() {
		return Data;
	}

	public void setData(JeevSearchInfo data) {
		Data = data;
	}

	private JeevSearchInfo Data;

}
