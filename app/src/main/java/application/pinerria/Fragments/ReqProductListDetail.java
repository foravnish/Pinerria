package application.pinerria.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import application.pinerria.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReqProductListDetail extends Fragment {


    public ReqProductListDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_req_product_list_detail, container, false);
    }

}
