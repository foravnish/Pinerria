package application.pinerria.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import application.pinerria.Activities.MainActivity;
import application.pinerria.Activities.PostAdd;
import application.pinerria.Activities.upload;
import application.pinerria.R;
import application.pinerria.Util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectArea extends Fragment {


    public SelectArea() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_select_area, container, false);

        getActivity().setTitle("Select Targeted Area");
        MainActivity.linerFilter.setVisibility(View.GONE);

        LinearLayout qImage1=view.findViewById(R.id.qImage1);
        LinearLayout qImage2=view.findViewById(R.id.qImage2);
        LinearLayout qImage3=view.findViewById(R.id.qImage3);

        TextView selectArea1=view.findViewById(R.id.selectArea1);
        TextView selectArea2=view.findViewById(R.id.selectArea2);
        TextView selectArea3=view.findViewById(R.id.selectArea3);


        Log.d("fgdfgbdfhdh",getArguments().getString("threemonths"));
        qImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showInfoAlertDialog(getActivity(), "Select Society", HomeFragment.string[5]);
            }
        });

        qImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showInfoAlertDialog(getActivity(), "Select Area", HomeFragment.string[6]);
            }
        });

        qImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showInfoAlertDialog(getActivity(), "Select Zone", HomeFragment.string[7]);
            }
        });

        selectArea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent  intent=new Intent(getActivity(), PostAdd.class);
                intent.putExtra("Cat_id",getArguments().getString("Cat_id"));
                intent.putExtra("threemonths",getArguments().getString("threemonths"));
                intent.putExtra("sixmonths",getArguments().getString("sixmonths"));
                intent.putExtra("areatype","society");
                intent.putExtra("areatypenum","1");
                intent.putExtra("heading",getArguments().getString("heading"));
                intent.putExtra("forPayment","");
                startActivity(intent);

//                Fragment fragment=new FormForPost();
//                Bundle bundle=new Bundle();
//                bundle.putString("Cat_id",getArguments().getString("Cat_id"));
//                FragmentManager fm=getActivity().getSupportFragmentManager();
//                FragmentTransaction ft=fm.beginTransaction();
//                fragment.setArguments(bundle);
//                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });

        selectArea2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent  intent=new Intent(getActivity(), PostAdd.class);
                intent.putExtra("Cat_id",getArguments().getString("Cat_id"));
                intent.putExtra("threemonths",getArguments().getString("threemonths"));
                intent.putExtra("sixmonths",getArguments().getString("sixmonths"));
                intent.putExtra("areatype","area");
                intent.putExtra("areatypenum","2");
                intent.putExtra("heading",getArguments().getString("heading"));
                intent.putExtra("forPayment","");
                startActivity(intent);

//                Fragment fragment=new FormForPost();
//                Bundle bundle=new Bundle();
//                bundle.putString("Cat_id",getArguments().getString("Cat_id"));
//                FragmentManager fm=getActivity().getSupportFragmentManager();
//                FragmentTransaction ft=fm.beginTransaction();
//                fragment.setArguments(bundle);
//                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });

        selectArea3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent=new Intent(getActivity(), PostAdd.class);
                intent.putExtra("Cat_id",getArguments().getString("Cat_id"));
                intent.putExtra("threemonths",getArguments().getString("threemonths"));
                intent.putExtra("sixmonths",getArguments().getString("sixmonths"));
                intent.putExtra("areatype","zone");
                intent.putExtra("areatypenum","3");
                intent.putExtra("heading",getArguments().getString("heading"));
                intent.putExtra("forPayment","");
                startActivity(intent);

//                Fragment fragment=new FormForPost();
//                Bundle bundle=new Bundle();
//                bundle.putString("Cat_id",getArguments().getString("Cat_id"));
//                FragmentManager fm=getActivity().getSupportFragmentManager();
//                FragmentTransaction ft=fm.beginTransaction();
//                fragment.setArguments(bundle);
//                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });

        return view;
    }

}
