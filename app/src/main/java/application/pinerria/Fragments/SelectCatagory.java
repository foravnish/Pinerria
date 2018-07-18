package application.pinerria.Fragments;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import application.pinerria.Activities.MainActivity;
import application.pinerria.Activities.PostAdd;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;

import static application.pinerria.Fragments.HomeFragment.string;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectCatagory extends Fragment {


    public SelectCatagory() {
        // Required empty public constructor
    }
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_select_catagory, container, false);
        MainActivity.linerFilter.setVisibility(View.GONE);

        getActivity().setTitle("Select Product Type");
//        relative.setVisibility(View.VISIBLE);

//        AdView adView = (AdView)view. findViewById(R.id.search_ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        TextView questionCar=view.findViewById(R.id.questionCar);
        TextView questionOther=view.findViewById(R.id.questionOther);

        LinearLayout linerCar=view.findViewById(R.id.linerCar);
        LinearLayout linerOther=view.findViewById(R.id.linerOther);

        questionCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showInfoAlertDialog(getActivity(), "Sell a Property or Car", string[3]);
            }
        });

        questionOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showInfoAlertDialog(getActivity(), "Sell Other then Property or Car", string[4]);
            }
        });

        linerCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Packages("car").execute();

            }
        });
        linerOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Packages("other").execute();


            }
        });

        return view;
    }

    class Packages extends AsyncTask<String,Void,String> {

        String car;

        public Packages(String car){
            this.car=car;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            //map.put("typeId", getArguments().getString("Cat_id"));

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.packageCharge,"POST",map);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("response", ": " + s);

            Util.cancelPgDialog(dialog);
            try {
                final JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {

                        JSONArray jsonArray=jsonObject.getJSONArray("message");
                        JSONObject jsonObject1=jsonArray.getJSONObject(0);
                        JSONObject threemonths=jsonObject1.optJSONObject("three_month_charges");
                        JSONObject sixmonths=jsonObject1.optJSONObject("six_month_charges");

                        JSONObject car3=threemonths.getJSONObject("Car");
                        JSONObject car6=sixmonths.getJSONObject("Car");

                        JSONObject Home3=threemonths.getJSONObject("Home");
                        JSONObject Home6=sixmonths.getJSONObject("Home");

                        JSONObject Other3=threemonths.getJSONObject("Other");
                        JSONObject Other6=sixmonths.getJSONObject("Other");


                        Log.d("dsdfjdshfsdf",threemonths.toString());
                        Log.d("sixmonths",threemonths.toString());



                        if (car.equalsIgnoreCase("car")) {
                           Fragment fragment = new SelectArea();
                           Bundle bundle = new Bundle();
                           bundle.putString("Cat_id", "1");
                           bundle.putString("threemonths", car3.toString());
                           bundle.putString("sixmonths", car6.toString());
                            bundle.putString("heading", "car");

                           FragmentManager fm = getActivity().getSupportFragmentManager();
                           FragmentTransaction ft = fm.beginTransaction();
                           fragment.setArguments(bundle);
                           ft.replace(R.id.container, fragment).addToBackStack(null).commit();
                       }
                       else if (car.equalsIgnoreCase("other")) {

                           Fragment fragment=new SelectArea();
                           Bundle bundle=new Bundle();
                           bundle.putString("Cat_id","2");
                            bundle.putString("threemonths", Other3.toString());
                            bundle.putString("sixmonths", Other6.toString());
                            bundle.putString("heading", "other");

                           FragmentManager fm=getActivity().getSupportFragmentManager();
                           FragmentTransaction ft=fm.beginTransaction();
                           fragment.setArguments(bundle);
                           ft.replace(R.id.container,fragment).addToBackStack(null).commit();
                       }




                    }
                    else {
                        Util.errorDialog(getActivity(),jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(getActivity(),"Please Connect to the Internet.");
                e.printStackTrace();
            }
        }

    }


}
