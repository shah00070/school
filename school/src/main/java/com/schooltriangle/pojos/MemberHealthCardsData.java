package com.schooltriangle.pojos;

import java.util.List;

/**
 * Created by user on 9/14/2015.
 */
public class MemberHealthCardsData {
    public HealthIndicatorGroup HealthIndicatorGroup ;
    public HealthIndicator HealthIndicator ;
    public List<Measurement> Measurement ;
    public boolean IsDummyData ;

    public com.schooltriangle.pojos.HealthIndicatorGroup getHealthIndicatorGroup() {
        return HealthIndicatorGroup;
    }

    public void setHealthIndicatorGroup(com.schooltriangle.pojos.HealthIndicatorGroup healthIndicatorGroup) {
        HealthIndicatorGroup = healthIndicatorGroup;
    }

    public com.schooltriangle.pojos.HealthIndicator getHealthIndicator() {
        return HealthIndicator;
    }

    public void setHealthIndicator(com.schooltriangle.pojos.HealthIndicator healthIndicator) {
        HealthIndicator = healthIndicator;
    }

    public List<com.schooltriangle.pojos.Measurement> getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(List<com.schooltriangle.pojos.Measurement> measurement) {
        Measurement = measurement;
    }

    public boolean isDummyData() {
        return IsDummyData;
    }

    public void setIsDummyData(boolean isDummyData) {
        IsDummyData = isDummyData;
    }
}
