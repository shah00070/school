package com.schooltriangle.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.schooltriangle.MyApplication;
import com.schooltriangle.R;

/**
 * Created by chandan on 12/5/16.
 */


public class newmyclassmates extends Fragment {
    public View rootView;

    ListView list;
    String[] web = {
            "Vipin Sharma",
            "Vipul Mishra,",
            "Ankit Singh",
            "Tarun Rawat",
            "Vastav Tyagi",
            "Deep Tripathi",

    } ;
    Integer[] imageId = {
            R.drawable.stu1,
            R.drawable.stu2,
            R.drawable.stu3,
            R.drawable.stu4,
            R.drawable.stu5,
            R.drawable.stu6,



    };

    String[] emailids = {
            "Yellow",
            "Red",
            "Green",
            "Yellow",
            "Blue",
            "Red",

    } ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.studentsocial_media, container,
                false);
        call();
        return rootView;
    }


    public void call(){

        CustomListstuden adapter = new
                CustomListstuden(getActivity(), web, imageId,emailids);
        list=(ListView)rootView.findViewById(R.id.list_viewstudent);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //    Toast.makeText(getActivity(), "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Health Mart Fragment");
    }
}
