package com.schooltriangle.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schooltriangle.R;

/**
 * Created by perveen akhtar on 5/8/2016.
 */
public class lockeradapter extends  RecyclerView.Adapter<lockerviewholder> {

    String [] title={"8th Class Merit","Year 2015-2016 Fee Receipt","7th Class Result","Football Tournament Winner"};

    String [] date = {"09/05/2016","09/05/2016","10/05/2016","11/05/2016"};

    String [] loctype = {"Certificate","Admin Document","Marksheet","Certificate"};

    String [] description = {"This certificate was awarded to me for my meritorious performance throughout 8th Class.","This is a tuition fee receipt submitted for the academic year 2015-16.The fee amount is Rs.60,000.",
            "This is a copy of my 7th class academic year result.I scored 93% aggregate in 7th class.","This Certificate was awarded to us when we won the interschool competition at district level."

    };

    String [] tag = {"#Firstposition","#Feereceipt","#Resultmarksheet","#sports#football"};
    Context context;
    LayoutInflater inflater;
    public lockeradapter(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public lockerviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.mylocker_item_list, parent, false);

        lockerviewholder vHolder=new lockerviewholder(v);
        return vHolder;
    }



    @Override
    public void onBindViewHolder(lockerviewholder holder, int position) {

        holder.t1.setText(title[position]);
        holder.t2.setText(date[position]);
        holder.t3.setText(loctype[position]);
       holder.t4.setText(description[position]);
        holder.t5.setText(tag[position]);


    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            lockerviewholder vholder = (lockerviewholder) v.getTag();
            int position = vholder.getPosition();
            //  Toast.makeText(context,""+position,Toast.LENGTH_LONG ).show();

        }
    };



    @Override
    public int getItemCount() {

        return title.length;
    }
}


