package com.schooltriangle.pojos;

import com.schooltriangle.mylibrary.pojo.Status;

public class ServiceRequisitionResult {
    private Status Status;
    private String Data;

    public Status getStatus() {
        return Status;
    }

    public void setStatus(Status status) {
        Status = status;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
