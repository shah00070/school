package com.schooltriangle.mylibrary.interfaces;

public interface AsyncTaskListner {
	void onTaskComplete(String result);

	void onFacilitySignUp(String result);

	void onMemberSignUp(String result);
}