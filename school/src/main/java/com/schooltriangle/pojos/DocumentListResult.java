package com.schooltriangle.pojos;

public class DocumentListResult {
	private com.schooltriangle.mylibrary.pojo.Status Status;

	public com.schooltriangle.mylibrary.pojo.Status getStatus() {
		return Status;
	}

	public void setStatus(com.schooltriangle.mylibrary.pojo.Status status) {
		Status = status;
	}

	public DocumentTypeListData getData() {
		return Data;
	}

	public void setData(DocumentTypeListData data) {
		Data = data;
	}

	private DocumentTypeListData Data;
}
