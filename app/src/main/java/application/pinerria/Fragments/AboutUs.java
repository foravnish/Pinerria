package application.pinerria.Fragments;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import application.pinerria.Activities.MainActivity;
import application.pinerria.Activities.PostAdd;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUs extends Fragment {


    public AboutUs() {
        // Required empty public constructor
    }


    Dialog dialog;
    TextView textAbout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_about_us, container, false);
        getActivity().setTitle("About us");
        MainActivity.linerFilter.setVisibility(View.GONE);

        textAbout=(TextView)view.findViewById(R.id.textAbout);

        new AboutUsAPi().execute();
        return view;
    }

    class AboutUsAPi extends AsyncTask<String, Void, String> {

        String pId, val1,val2,comm;

        public AboutUsAPi() {

            this.pId = pId;
            this.val1 = val1;
            this.val2 = val2;
            this.comm = comm;
            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> map = new HashMap<>();

//            map.put("userId", MyPrefrences.getUserID(getActivity()));

            JSONParser jsonParser = new JSONParser();
            String result = jsonParser.makeHttpRequest(Api.aboutUs, "POST", map);

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
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1.optString("id").equalsIgnoreCase("2")) {
                                textAbout.setText(Html.fromHtml(jsonObject1.optString("content")));
                            }
                        }

                    } else {
                        Util.errorDialog(getActivity(), jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(getActivity(), "Some Error! or Please connect to the internet.");
                e.printStackTrace();
            }
        }

    }


}
