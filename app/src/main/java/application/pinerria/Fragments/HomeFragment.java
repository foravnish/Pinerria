package application.pinerria.Fragments;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import application.pinerria.Activities.MainActivity;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    Dialog dialog,dialog4;
//    String string,string1,string2,string3,string4,string5,string6,string7,string8;
    public  static String[] string;
    public JSONObject threemonths,sixmonths;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);


//        AdView adView = (AdView) view.findViewById(R.id.search_ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        adView.loadAd(adRequest);

        LinearLayout question1=view.findViewById(R.id.question1);
        LinearLayout  question2=view.findViewById(R.id.question2);
        LinearLayout  question3=view.findViewById(R.id.question3);

        LinearLayout sell=view.findViewById(R.id.sell);
        LinearLayout buy=view.findViewById(R.id.buy);
        LinearLayout post=view.findViewById(R.id.post);

        getActivity().setTitle("Welcome to Pinerria");
        MainActivity.linerFilter.setVisibility(View.GONE);

        string =new String[100];

        new HelpData().execute();

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new SelectCatagory();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
                MyPrefrences.setHomeType(getActivity(),"sell");

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Packages("home").execute();
                MyPrefrences.setHomeType(getActivity(),"home");

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new SelectBuyCategory();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
                MyPrefrences.setHomeType(getActivity(),"buy");

            }
        });



        question1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showInfoAlertDialog(getActivity(), "Sell", string[0]);

            }
        });


        question2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showInfoAlertDialog(getActivity(), "Buy", string[1]);
            }
        });


        question3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showInfoAlertDialog(getActivity(), "Post Ad", string[2]);
            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {


                        Button Yes_action,No_action;
                        TextView heading;
                        dialog4 = new Dialog(getActivity());
                        dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog4.setContentView(R.layout.update_state1);

                        Yes_action=(Button)dialog4.findViewById(R.id.Yes_action);
                        No_action=(Button)dialog4.findViewById(R.id.No_action);
                        heading=(TextView)dialog4.findViewById(R.id.heading);


                        heading.setText("Are you sure you want to exit?");
                        Yes_action.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //System.exit(0);
                                //getActivity().finish();
                                getActivity().finishAffinity();

                            }
                        });

                        No_action.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog4.dismiss();
                            }
                        });
                        dialog4.show();
//

                        //Toast.makeText(getActivity(), "back", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });

        return view;
    }




    class HelpData extends AsyncTask<String,Void,String> {


        public HelpData(){

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
            String result =jsonParser.makeHttpRequest(Api.helpData,"POST",map);

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

                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject0 = jsonArray.getJSONObject(i);

                            string[i] = jsonObject0.optString("content");
                        }

                    }
                    else {
                        Util.errorDialog(getActivity(),jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(getActivity(),"Some Error! or Please connect to the internet.");
                e.printStackTrace();
            }
        }

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
                        threemonths=jsonObject1.optJSONObject("three_month_charges");
                        sixmonths=jsonObject1.optJSONObject("six_month_charges");

                        Log.d("dsdfjdshfsdf",threemonths.toString());
                        Log.d("sixmonths",threemonths.toString());


                        Fragment fragment=new PostHomeBusiness();
                        Bundle bundle=new Bundle();
                        bundle.putString("threemonths", threemonths.toString());
                        bundle.putString("sixmonths", sixmonths.toString());
                        FragmentManager fm=getActivity().getSupportFragmentManager();
                        FragmentTransaction ft=fm.beginTransaction();
                        fragment.setArguments(bundle);
                        ft.replace(R.id.container,fragment).addToBackStack(null).commit();


                    }
                    else {
                        Util.errorDialog(getActivity(),jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(getActivity(),"Some Error! or Please connect to the internet.");
                e.printStackTrace();
            }
        }

    }




}
