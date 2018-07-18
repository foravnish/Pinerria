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
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class Deshboard extends Fragment {


    public Deshboard() {
        // Required empty public constructor
    }
    Dialog  dialog;
    TextView totalP,approvedP,desApproceP,pendingP,soldP;
    TextView totalB,approvedB,desApproceB,pendingB,soldB;
    TextView totalR,approvedR,desApproceR,pendingR,soldR;
    RelativeLayout car1,car2,car3,car4,car5;
    RelativeLayout home1,home2,home3,home4,home5;
    RelativeLayout req1,req2,req3,req4,req5;
    JSONObject car,home,req;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_deshboard, container, false);
        getActivity().setTitle("My Account");
        MainActivity.linerFilter.setVisibility(View.GONE);

        totalP=view.findViewById(R.id.totalP);
        approvedP=view.findViewById(R.id.approvedP);
        desApproceP=view.findViewById(R.id.desApproceP);
        pendingP=view.findViewById(R.id.pendingP);
        soldP=view.findViewById(R.id.soldP);

        totalB=view.findViewById(R.id.totalB);
        approvedB=view.findViewById(R.id.approvedB);
        desApproceB=view.findViewById(R.id.desApproceB);
        pendingB=view.findViewById(R.id.pendingB);
        soldB=view.findViewById(R.id.soldB);

        totalR=view.findViewById(R.id.totalR);
        approvedR=view.findViewById(R.id.approvedR);
        desApproceR=view.findViewById(R.id.desApproceR);
        pendingR=view.findViewById(R.id.pendingR);
        soldR=view.findViewById(R.id.soldR);

        car1=view.findViewById(R.id.car1);
        car2=view.findViewById(R.id.car2);
        car3=view.findViewById(R.id.car3);
        car4=view.findViewById(R.id.car4);
        car5=view.findViewById(R.id.car5);

        home1=view.findViewById(R.id.home1);
        home2=view.findViewById(R.id.home2);
        home3=view.findViewById(R.id.home3);
        home4=view.findViewById(R.id.home4);
        home5=view.findViewById(R.id.home5);

        req1=view.findViewById(R.id.req1);
        req2=view.findViewById(R.id.req2);
        req3=view.findViewById(R.id.req3);
        req4=view.findViewById(R.id.req4);
        req5=view.findViewById(R.id.req5);

        new MyDeshboard().execute();

        Log.d("fgdgdfgbdfhgdf",getArguments().getString("type"));


        if (getArguments().getString("type").equalsIgnoreCase("yes")) {

                view.setFocusableInTouchMode(true);
                view.requestFocus();
                view.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {

                                Fragment fragment = new HomeFragment();
                                FragmentManager manager = getActivity().getSupportFragmentManager();
                                FragmentTransaction ft = manager.beginTransaction();
                                ft.replace(R.id.container, fragment).commit();

                                //Toast.makeText(getActivity(), "back", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        }
                        return false;
                    }
                });
        }


        car1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",car.toString());
                bundle.putString("typeforListing","car");
                bundle.putInt("position",0);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        car2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",car.toString());
                bundle.putString("typeforListing","car");
                bundle.putInt("position",1);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        car3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",car.toString());
                bundle.putString("typeforListing","car");
                bundle.putInt("position",2);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        car4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",car.toString());
                bundle.putString("typeforListing","car");
                bundle.putInt("position",3);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        car5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",car.toString());
                bundle.putString("typeforListing","car");
                bundle.putInt("position",4);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });

        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",home.toString());
                bundle.putString("typeforListing","home");
                bundle.putInt("position",0);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",home.toString());
                bundle.putString("typeforListing","home");
                bundle.putInt("position",1);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        home3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",home.toString());
                bundle.putString("typeforListing","home");
                bundle.putInt("position",2);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        home4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",home.toString());
                bundle.putString("typeforListing","home");
                bundle.putInt("position",3);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        home5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",home.toString());
                bundle.putString("typeforListing","home");
                bundle.putInt("position",4);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });

        req1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",req.toString());
                bundle.putString("typeforListing","req");
                bundle.putInt("position",0);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        req2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",req.toString());
                bundle.putString("typeforListing","req");
                bundle.putInt("position",1);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        req3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",req.toString());
                bundle.putString("typeforListing","req");
                bundle.putInt("position",2);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        req4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DeshboardListing();
                Bundle bundle=new Bundle();
                bundle.putString("type",req.toString());
                bundle.putString("typeforListing","req");
                bundle.putInt("position",3);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        req5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyRequirement();
                Bundle bundle=new Bundle();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();


//                Fragment fragment=new DeshboardListing();
//                Bundle bundle=new Bundle();
//                bundle.putString("type",req.toString());
//                bundle.putString("typeforListing","req");
//                bundle.putInt("position",4);
//                FragmentManager fm=getActivity().getSupportFragmentManager();
//                FragmentTransaction ft=fm.beginTransaction();
//                fragment.setArguments(bundle);
//                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });


        return view;

    }


    private class MyDeshboard extends  AsyncTask<String,Void,String> {

        public MyDeshboard(){

            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();
            map.put("userId", MyPrefrences.getUserID(getActivity()));
            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.dashboard,"POST",map);

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

                        JSONObject jsonObject1=jsonObject.getJSONObject("message");
                        car=jsonObject1.getJSONObject("car");
                        home=jsonObject1.getJSONObject("home");
                        req=jsonObject1.getJSONObject("requirement");

                        totalP.setText(car.optString("totalAdv"));
                        approvedP.setText(car.optString("approved"));
                        desApproceP.setText(car.optString("disapproved"));
                        pendingP.setText(car.optString("pending"));
                        soldP.setText(car.optString("sold"));

                        totalB.setText(home.optString("totalAdv"));
                        approvedB.setText(home.optString("approved"));
                        desApproceB.setText(home.optString("disapproved"));
                        pendingB.setText(home.optString("pending"));
                        soldB.setText(home.optString("sold"));

                        totalR.setText(req.optString("totalAdv"));
                        approvedR.setText(req.optString("approved"));
                        desApproceR.setText(req.optString("disapproved"));
                        pendingR.setText(req.optString("pending"));
                        soldR.setText(req.optString("sold"));



                    }
                    else {
                        Util.errorDialog(getActivity(),jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(getActivity(),"Some Error! Please try again...");
                e.printStackTrace();
            }
        }
    }
}
