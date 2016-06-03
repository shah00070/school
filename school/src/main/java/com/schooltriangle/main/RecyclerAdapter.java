package com.schooltriangle.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schooltriangle.R;

/**
 * Created by kundan on 10/26/2015.
 */
public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerViewHolder> {

    String [] name={"Sudha Rao","Vanita Malik","Mukta Sharma","Paushali Bhaduri"};

    String [] desc = {"Hi Class! We will be having a class test of Mathematics on 20th May.Syallabus covered will be complete Unit 5 of Trigonometry.All the best!",
            "Hi Kushagra! Don't forget to submit 2 passport size photographs of yours by tomorrow for the NTSE form.",
            "Social Science assignment pending.Submission date is 21st May(Saturday)",
            "Science project submission date will be 4th May.Only completed project will be considered.",

    };

    String [] fac = {
            "Faculty - Mathematics","Class Teacher - VIII D","Faculty - Social Science","Faculty - Science"

    };
Context context;
    LayoutInflater inflater;
    public RecyclerAdapter(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.notification_item_list, parent, false);

        RecyclerViewHolder viewHolder=new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.tv1.setText(name[position]);
        holder.tv2.setText(desc[position]);
        holder.tv3.setText(fac[position]);
        holder.imageView.setOnClickListener(clickListener);
        holder.imageView.setTag(holder);


    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            RecyclerViewHolder vholder = (RecyclerViewHolder) v.getTag();
            int position = vholder.getPosition();
          //  Toast.makeText(context,""+position,Toast.LENGTH_LONG ).show();

        }
    };



    @Override
    public int getItemCount() {
        return name.length;
    }
}


