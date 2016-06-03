package com.schooltriangle.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schooltriangle.MyApplication;
import com.schooltriangle.mylibrary.util.JeevOMUtil;

import com.schooltriangle.R;

/**
 * Created by chandan on 8/12/15.
 */
public class DealsAndOfferFragment extends Fragment implements JeevOMUtil {
    public View rootView;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        // Tracking the screen view
       // callmethod();
        MyApplication.getInstance().trackScreenView("Deals & Offer details");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.deals_offer_fragment, container,
                false);

       // callmethod();
        return rootView;
    }

   /* private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new Month(), "Monthly");
       // adapter.addFrag(new Week(), "Weakly");


        viewPager.setAdapter(adapter);
    }*/
    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }
  /*  class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }*/
  /* public void callmethod(){
       viewPager = (ViewPager) rootView.findViewById(R.id.viewpagere);
       setupViewPager(viewPager);

       tabLayout = (TabLayout) rootView.findViewById(R.id.tabse);
       tabLayout.setupWithViewPager(viewPager);
   }*/


}
