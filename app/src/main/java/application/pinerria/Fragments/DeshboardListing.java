package application.pinerria.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import application.pinerria.Activities.MainActivity;
import application.pinerria.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeshboardListing extends Fragment {


    public DeshboardListing() {
        // Required empty public constructor
    }
    private TabLayout tabLayout;
    private ViewPager viewPager;
    JSONObject  jsonCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_deshboard_listing, container, false);
        MainActivity.linerFilter.setVisibility(View.GONE);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        if (getArguments().getInt("position")==0){
            viewPager.setCurrentItem(0);
        }
        else if (getArguments().getInt("position")==1){
            viewPager.setCurrentItem(1);
        }
        else if (getArguments().getInt("position")==2){
            viewPager.setCurrentItem(2);
        }
        else if (getArguments().getInt("position")==3){
            viewPager.setCurrentItem(3);
        }
        else if (getArguments().getInt("position")==4){
            viewPager.setCurrentItem(4);
        }

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        Log.d("dfvgdgdfgdf",getArguments().getString("type"));
        Log.d("dfvgdgdfgdffdgdgdfg",getArguments().getString("typeforListing"));
        try {
            jsonCount=new JSONObject(getArguments().getString("type"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (getArguments().getString("typeforListing").toString().equals("req")){
            adapter.addFrag(DeshAll.NewInstance(getArguments().getString("typeforListing").toString()), "All"+"("+jsonCount.optString("totalAdv")+")");
            adapter.addFrag(DeshApproved.NewInstance(getArguments().getString("typeforListing").toString()), "Approved"+"("+jsonCount.optString("approved")+")");
            adapter.addFrag(DeshDesapproved.NewInstance(getArguments().getString("typeforListing").toString()), "Disapproved"+"("+jsonCount.optString("disapproved")+")");
            adapter.addFrag(DeshPending.NewInstance(getArguments().getString("typeforListing").toString()), "Pending"+"("+jsonCount.optString("pending")+")");
        }

        else {
//        adapter.addFrag(new DeshAll(), "All"+"("+jsonCount.optString("totalAdv")+")");
            adapter.addFrag(DeshAll.NewInstance(getArguments().getString("typeforListing").toString()), "All" + "(" + jsonCount.optString("totalAdv") + ")");
            adapter.addFrag(DeshApproved.NewInstance(getArguments().getString("typeforListing").toString()), "Approved" + "(" + jsonCount.optString("approved") + ")");
            adapter.addFrag(DeshDesapproved.NewInstance(getArguments().getString("typeforListing").toString()), "Disapproved" + "(" + jsonCount.optString("disapproved") + ")");
            adapter.addFrag(DeshPending.NewInstance(getArguments().getString("typeforListing").toString()), "Pending" + "(" + jsonCount.optString("pending") + ")");
            adapter.addFrag(DeshSold.NewInstance(getArguments().getString("typeforListing").toString()), "Sold" + "(" + jsonCount.optString("sold") + ")");
        }
//        adapter.addFrag(new DeshAll(), "All"+"("+jsonCount.optString("totalAdv")+")");
//        adapter.addFrag(new DeshApproved(), "Approved"+"("+jsonCount.optString("approved")+")");
//        adapter.addFrag(new DeshDesapproved(), "Disapproved"+"("+jsonCount.optString("disapproved")+")");
//        adapter.addFrag(new DeshPending(), "Pending"+"("+jsonCount.optString("pending")+")");
//        adapter.addFrag(new DeshSold(), "Sold"+"("+jsonCount.optString("sold")+")");

        viewPager.setAdapter(adapter);

    }


    static class ViewPagerAdapter extends FragmentPagerAdapter {
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
        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }


    }



}
