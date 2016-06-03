package com.schooltriangle.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schooltriangle.MyApplication;

import com.schooltriangle.R;

/**
 * Created by shah on 24/12/15.
 */
public class ProductOverviewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_overview_fragment, container, false);


        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Product overview Fragment");
    }

}

