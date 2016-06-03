package com.schooltriangle.interfaces;

import com.schooltriangle.pojos.BloodGroup;
import com.schooltriangle.pojos.ConsumerDetailsResponse;
import com.schooltriangle.pojos.FamilyMembersResponse;

import java.util.List;


public interface SendConsumerDetails {

	void sendConsumerDetails(double bmi, double bmr,
							 FamilyMembersResponse familyDetails, List<BloodGroup> bloodGroups,
							 ConsumerDetailsResponse consumer);
}
