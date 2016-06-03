package com.schooltriangle.pojos;

public class InsuranceDetailsResult {
	private com.schooltriangle.mylibrary.pojo.Status Status;
	private InsuraceResultInfo Data;

	/**
	 * @return the status
	 */
	public com.schooltriangle.mylibrary.pojo.Status getStatus() {
		return Status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(com.schooltriangle.mylibrary.pojo.Status status) {
		Status = status;
	}

	/**
	 * @return the data
	 */
	public InsuraceResultInfo getData() {
		return Data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(InsuraceResultInfo data) {
		Data = data;
	}
}
