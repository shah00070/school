package com.schooltriangle.pojos;

import com.schooltriangle.mylibrary.pojo.Status;

public class DocumentResult {

	private Status Status;
	private DocumentDetails Data;

	public Status getStatus() {
		return Status;
	}

	public void setStatus(Status status) {
		Status = status;
	}

	public DocumentDetails getData() {
		return Data;
	}

	public void setData(DocumentDetails data) {
		Data = data;
	}
}