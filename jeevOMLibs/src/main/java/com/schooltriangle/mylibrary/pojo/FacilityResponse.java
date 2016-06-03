package com.schooltriangle.mylibrary.pojo;

import java.io.Serializable;

public class FacilityResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private com.schooltriangle.mylibrary.pojo.Status Status;
	private FacilityInfo Data;

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
	public FacilityInfo getData() {
		return Data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(FacilityInfo data) {
		Data = data;
	}

}
