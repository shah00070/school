package com.schooltriangle.pojos;

import java.util.List;

/**
 * Created by chandan on 10/12/15.
 */
public class ServiceResultPromotionData {

    private com.schooltriangle.mylibrary.pojo.Status Status;

    private List<DealsAndOfferResponse> Data;

    public List<DealsAndOfferResponse> getData() {
        return Data;
    }

    public void setData(List<DealsAndOfferResponse> data) {
        Data = data;
    }

    public com.schooltriangle.mylibrary.pojo.Status getStatus() {
        return Status;
    }

    public void setStatus(com.schooltriangle.mylibrary.pojo.Status status) {
        Status = status;
    }

}

