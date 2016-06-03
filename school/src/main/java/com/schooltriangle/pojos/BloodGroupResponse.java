package com.schooltriangle.pojos;

public class BloodGroupResponse {
	private com.schooltriangle.mylibrary.pojo.Status Status;
	/**
	 * @return the status
	 */
	public com.schooltriangle.mylibrary.pojo.Status getStatus() {
		return Status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(com.schooltriangle.mylibrary.pojo.Status status) {
		Status = status;
	}
	/**
	 * @return the data
	 */
	public BloodGroupInfo getData() {
		return Data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(BloodGroupInfo data) {
		Data = data;
	}
	private BloodGroupInfo Data;
}
