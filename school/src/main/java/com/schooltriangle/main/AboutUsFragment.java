package com.schooltriangle.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.schooltriangle.MyApplication;

import com.schooltriangle.R;

/**
 * Created by chandan on 24/12/15.
 */
public class AboutUsFragment extends Fragment {
    EditText et;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_us_fragment, container,
                false);
        /* et =  (EditText)rootView.findViewById(R.id.person_nameu);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                InputMethodManager inputMethodManager=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(et.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);

            }
        };
        timer.schedule(task, 200);*/
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("About us Fragment");
    }

}

