package com.schooltriangle.pojos;

public class BookAppointmentResponse {
	private com.schooltriangle.mylibrary.pojo.Status Status ;
    private BookAppointmentInfo Data ;
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
	public BookAppointmentInfo getData() {
		return Data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(BookAppointmentInfo data) {
		Data = data;
	}
}
