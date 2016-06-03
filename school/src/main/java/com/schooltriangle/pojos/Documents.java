package com.schooltriangle.pojos;

public class Documents {
	private com.schooltriangle.mylibrary.pojo.Status Status;
	private DocumentListData Data;

	public com.schooltriangle.mylibrary.pojo.Status getStatus() {
		return Status;
	}

	public void setStatus(com.schooltriangle.mylibrary.pojo.Status status) {
		Status = status;
	}

	public DocumentListData getData() {
		return Data;
	}

	public void setData(DocumentListData data) {
		Data = data;
	}
}
