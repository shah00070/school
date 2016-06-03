package com.schooltriangle.pojos;

/**
 * Created by chandan on 31/12/15.
 */
public class ServiceRequestPaymentResponse {

    private com.schooltriangle.mylibrary.pojo.Status Status;
    private PackageDetailsData Data;

    public com.schooltriangle.mylibrary.pojo.Status getStatus() {
        return Status;
    }

    public void setStatus(com.schooltriangle.mylibrary.pojo.Status status) {
        Status = status;
    }

    public PackageDetailsData getData() {
        return Data;
    }

    public void setData(PackageDetailsData data) {
        Data = data;
    }
}
