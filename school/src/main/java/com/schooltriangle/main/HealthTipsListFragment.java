package com.schooltriangle.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.schooltriangle.MyApplication;
import com.schooltriangle.mylibrary.dialog.GlobalAlert;

import com.schooltriangle.R;

/**
 * Created by chandan on 24/12/15.
 */
public class HealthTipsListFragment extends Fragment {


    private GlobalAlert globalAlert;
    String[] mobileArray = {"Turning lessons into stories and relating name and events to a story will help you memorize better",
            "Walking a little bit during study break will help you relax better than just sitting quietly.",
            " Using a finger or a pen while reading will help you concentrate better.",
            "Dedicate an entire dresser to your school books, supplies, and notebooks so that nothing gets lost.\n",
            "Put your alarm clock out of your reach so that you cannot hit the snooze button when it starts to ring.\n",
            "Whether it's your bedroom at night or the library after school, find a study constant space and a regular study time that works for you and stick with it.\n" +
                    "7. Scholar.google.com will give you better academic related results than Google.com.\n",
            " Chewing the same flavour gum when taking a test as you did when studying will help you memorize better.\n",
            " Retention rate is 60% better if you study your notes within 24 hours of taking them.\n",
            " Want to learn a concept in a jiffy? Google the subject matter + “filetype:ppt” to find free lecture slides online.\n",
            " At least once a week you should go back over the things you've studied in class. Thinking things over can help you remember when you need them the most.\n",
            "Reading information out loud means mentally storing it in two ways: seeing it and hearing it.\n",
            " Don’t just start the week with the vague goal of studying—instead, break up that goal into smaller tasks.\n",
            " Classical music in particular has been shown to reduce anxiety and tension during exam days.\n",
    "In the days leading up to a big exam, aim to get those seven to nine hours a night so sleep deprivation doesn’t undo all the hard work you’ve put in."};




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.health_tips_list_fragment, container,
                false);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_listview, mobileArray);

        ListView listView = (ListView) rootView.findViewById(R.id.listVieww);
        listView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Health tips listing");
    }




    // Show Global Message
    private void showAlert(String message) {
        globalAlert.show();
        globalAlert.setMessage(message);
    }
}

