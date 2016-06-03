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
import com.schooltriangle.adapters.PopularSearchAdapter;

import com.schooltriangle.R;


/**
 * Created by chandan on 16/12/15.
 */
public class PopularSearchFragment extends Fragment {
    private View rootView;

    private String[] itemList;
    private String[] date;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.popular_search_fragment, container,
                false);
        setUiOnScreen();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Popular search fragment");
    }

    public void setUiOnScreen() {
        ListView listView = (ListView) rootView.findViewById(R.id.search_listview);
        itemList = getActivity().getResources().getStringArray(R.array.popular_search_list);
        date = getActivity().getResources().getStringArray(R.array.date_list);
        PopularSearchAdapter adapter = new PopularSearchAdapter(getActivity(), itemList,date);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
if(position==0){

   // getActivity().overridePendingTransition(com.school.mylibrary.R.anim.trans_left_in, com.school.mylibrary.R.anim.trans_left_exit);
  /*  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

    Fragment fragment = new Image_notice();




    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    fragTransaction.replace(R.id.container_body, fragment);
    fragTransaction.commit();
*/
}else if(position==1){
  /*  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

    Fragment fragment = new text_notice();
    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    fragTransaction.replace(R.id.container_body, fragment);
    fragTransaction.commit();*/
}
else if(position==2){
    // getActivity().overridePendingTransition(com.school.mylibrary.R.anim.trans_left_in, com.school.mylibrary.R.anim.trans_left_exit);
   /* FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

    Fragment fragment = new Image_notice();
    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    fragTransaction.replace(R.id.container_body, fragment);
    fragTransaction.commit();*/
}
else if(position==3){
   /* FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

    Fragment fragment = new text_notice();
    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    fragTransaction.replace(R.id.container_body, fragment);
    fragTransaction.commit();*/
}
else if(position==4){
   /* FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

    Fragment fragment = new text_notice();
    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    fragTransaction.replace(R.id.container_body, fragment);
    fragTransaction.commit();*/
}
else if(position==5){
    // getActivity().overridePendingTransition(com.school.mylibrary.R.anim.trans_left_in, com.school.mylibrary.R.anim.trans_left_exit);
  /*  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

    Fragment fragment = new Image_notice();
    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    fragTransaction.replace(R.id.container_body, fragment);
    fragTransaction.commit();*/
}
else if(position==6){
/*    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

    Fragment fragment = new text_notice();
    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    fragTransaction.replace(R.id.container_body, fragment);
    fragTransaction.commit();*/
}
else if(position==7){
//    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
//
//    Fragment fragment = new text_notice();
//    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
//    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//    fragTransaction.replace(R.id.container_body, fragment);
//    fragTransaction.commit();
}
else if(position==8){
    // getActivity().overridePendingTransition(com.school.mylibrary.R.anim.trans_left_in, com.school.mylibrary.R.anim.trans_left_exit);
  /*  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

    Fragment fragment = new Image_notice();
    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    fragTransaction.replace(R.id.container_body, fragment);
    fragTransaction.commit();*/
}
else if(position==9){
//    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
//
//    Fragment fragment = new text_notice();
//    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
//    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//    fragTransaction.replace(R.id.container_body, fragment);
//    fragTransaction.commit();
}


else if(position==10){
    // getActivity().overridePendingTransition(com.school.mylibrary.R.anim.trans_left_in, com.school.mylibrary.R.anim.trans_left_exit);
//    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
//
//    Fragment fragment = new Image_notice();
//    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
//    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//    fragTransaction.replace(R.id.container_body, fragment);
//    fragTransaction.commit();
}

else if(position==11){
//    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
//
//    Fragment fragment = new text_notice();
//    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
//    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//    fragTransaction.replace(R.id.container_body, fragment);
//    fragTransaction.commit();
}

else if(position==12){
    // getActivity().overridePendingTransition(com.school.mylibrary.R.anim.trans_left_in, com.school.mylibrary.R.anim.trans_left_exit);
//    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
//
//    Fragment fragment = new Image_notice();
//    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
//    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//    fragTransaction.replace(R.id.container_body, fragment);
//    fragTransaction.commit();
}

else if(position==13){
//    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
//
//    Fragment fragment = new text_notice();
//    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
//    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//    fragTransaction.replace(R.id.container_body, fragment);
//    fragTransaction.commit();
}

else if(position==14){
    // getActivity().overridePendingTransition(com.school.mylibrary.R.anim.trans_left_in, com.school.mylibrary.R.anim.trans_left_exit);
//    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
//
//    Fragment fragment = new Image_notice();
//    //getActivity().getSupportActionBar().setTitle(getResources().getString(R.string.health_library));
//    fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//    fragTransaction.replace(R.id.container_body, fragment);
//    fragTransaction.commit();
}


            }
        });
    }


}
