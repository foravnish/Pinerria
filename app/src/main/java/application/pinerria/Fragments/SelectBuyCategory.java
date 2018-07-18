package application.pinerria.Fragments;


import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import application.pinerria.Activities.MainActivity;
import application.pinerria.R;
import application.pinerria.Util.MyPrefrences;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectBuyCategory extends Fragment {


    public SelectBuyCategory() {
        // Required empty public constructor
    }

    TextView category3,category2,category1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_select_buy_category, container, false);

        getActivity().setTitle("Select Buy Category");
        MainActivity.linerFilter.setVisibility(View.GONE);

        category1=view.findViewById(R.id.category1);
        category2=view.findViewById(R.id.category2);
        category3=view.findViewById(R.id.category3);
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyPrefrences.setBuyCat(getActivity(),"1");
                Fragment fragment=new BuyCarListing();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });

        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyPrefrences.setBuyCat(getActivity(),"2");
                Fragment fragment=new BuyOtherListing();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();

            }
        });

        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyPrefrences.setBuyCat(getActivity(),"3");
                Fragment fragment=new BuyHomeBusinessListing();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });


        return view;
    }

}
