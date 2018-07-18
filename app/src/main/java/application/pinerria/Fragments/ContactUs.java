package application.pinerria.Fragments;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
public class ContactUs extends Fragment {


    Button sendEnq;
    TextView phone,email,email2;
    Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_contact_us1, container, false);
        getActivity().setTitle("Contact Us");
        MainActivity.linerFilter.setVisibility(View.GONE);
        sendEnq=view.findViewById(R.id.sendEnq);

        phone=view.findViewById(R.id.phone);
        email=view.findViewById(R.id.email);
        email2=view.findViewById(R.id.email2);

        new ContactusApi().execute();

        sendEnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSendEnq();
            }
        });


//        submitNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(checkValidation()){
//
//                    Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        return view;
    }
//

    private void DialogSendEnq() {

        final Dialog dialog0 = new Dialog(getActivity());
        dialog0.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog0.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog0.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog0.setContentView(R.layout.fragment_contact_us);

        Button submitNow=(Button)dialog0.findViewById(R.id.submitNow);
        submitNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        TextView title = (TextView) dialog0.findViewById(R.id.title);
        title.setText("SEND ENQUIRY");



        dialog0.show();

    }


//    private boolean  checkValidation() {
//        if (TextUtils.isEmpty(name.getText().toString()))
//        {
//            name.setError("Oops! Name blank");
//            name.requestFocus();
//            return false;
//        }
//        else if (TextUtils.isEmpty(emailID.getText().toString()))
//        {
//            emailID.setError("Oops! Email Id blank");
//            emailID.requestFocus();
//            return false;
//        }
//        else if (TextUtils.isEmpty(contactNo.getText().toString())){
//            contactNo.setError("Oops! Contact No. blank");
//            contactNo.requestFocus();
//            return false;
//        }
//
//
//        else if (TextUtils.isEmpty(subject.getText().toString())){
//            subject.setError("Oops! Subject blank");
//            subject.requestFocus();
//            return false;
//        }
//
//        else if (TextUtils.isEmpty(message.getText().toString())){
//            message.setError("Oops! Message blank");
//            message.requestFocus();
//            return false;
//        }
//
//        return true;
//    }

    class ContactusApi extends AsyncTask<String,Void,String> {

        String old,newPass;
        public ContactusApi(){

            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();


//            map.put("password", password.toString());

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.contactUs,"POST",map);

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

                        final JSONArray jsonArray=jsonObject.getJSONArray("message");

                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        phone.setText(jsonObject1.optString("number").toString());
                        email.setText(jsonObject1.optString("email").toString());
                        email2.setText(jsonObject1.optString("altemail").toString());

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
