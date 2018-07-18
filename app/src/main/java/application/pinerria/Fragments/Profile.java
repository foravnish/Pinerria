package application.pinerria.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
public class Profile extends Fragment {
    TextView name,state,area,city,email,mobile,company;
    Dialog dialog;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");
        MainActivity.linerFilter.setVisibility(View.GONE);

        name=(TextView)view.findViewById(R.id.name);
        state=(TextView)view.findViewById(R.id.state);
        area=(TextView)view.findViewById(R.id.area);
        city=(TextView)view.findViewById(R.id.city);
        email=(TextView)view.findViewById(R.id.email);
        mobile=(TextView)view.findViewById(R.id.mobile);
        company=(TextView)view.findViewById(R.id.company);

        new  ProfileApi(getActivity(),MyPrefrences.getEmilId(getActivity()),MyPrefrences.getPassword(getActivity())).execute();

        return view;
    }

    private class ProfileApi extends AsyncTask<String, Void, String> {
        Context context;
        String emilId,pass;
        public ProfileApi(Context context, String emilId, String pass) {
            this.context = context;
            this.emilId=emilId;
            this.pass=pass;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }


        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("email",emilId);
            map.put("password", pass);

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.Login,"POST",map);

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
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            name.setText(jsonObject1.optString("fname")+" "+jsonObject1.optString("lname"));
                            mobile.setText(jsonObject1.optString("contact"));
                            email.setText(jsonObject1.optString("email"));
                            state.setText(jsonObject1.optString("state_id"));
                            city.setText(jsonObject1.optString("city_id"));
                            area.setText(jsonObject1.optString("area_id"));
                            company.setText(jsonObject1.optString("company_id"));




                        }
                    }
                    else {
                        Util.errorDialog(context,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }



}
