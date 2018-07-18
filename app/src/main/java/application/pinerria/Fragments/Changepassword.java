package application.pinerria.Fragments;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
public class Changepassword extends Fragment {


    public Changepassword() {
        // Required empty public constructor
    }
    Button submit;
    EditText confirm,newPwd,password;
    Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_changepassword, container, false);

        password= (EditText) view.findViewById(R.id.password);
        newPwd= (EditText) view.findViewById(R.id.newPwd);
        confirm= (EditText) view.findViewById(R.id.confirm);

        submit= (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkValidation()){

                    new ChangepasswordApi(password.getText().toString(),confirm.getText().toString()).execute();

                }
            }
        });
        getActivity().setTitle("Change Password");
        MainActivity.linerFilter.setVisibility(View.GONE);

        return view;
    }

    private boolean  checkValidation() {
        if (TextUtils.isEmpty(password.getText().toString()))
        {
            password.setError("Oops! Password blank");
            password.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(newPwd.getText().toString()))
        {
            newPwd.setError("Oops! New Password blank");
            newPwd.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(confirm.getText().toString())){
            confirm.setError("Oops! Confirm Password blank");
            confirm.requestFocus();
            return false;
        }
        else if (!confirm.getText().toString().equals(newPwd.getText().toString())){

            Util.errorDialog(getActivity(),"Confirm Password must be same !");
            return false;
        }
        else if (password.getText().toString().length()<6){
            password.setError("Oops! Password length must 6 ");
            password.requestFocus();
            return false;
        }

        else if (newPwd.getText().toString().length()<6){
            newPwd.setError("Oops! New Password length must 6 ");
            newPwd.requestFocus();
            return false;
        }

        else if (confirm.getText().toString().length()<6){
            confirm.setError("Oops! Confirm Password length must 6 ");
            confirm.requestFocus();
            return false;
        }
        return true;
    }
    class ChangepasswordApi extends AsyncTask<String,Void,String> {

        String old,newPass;
        public ChangepasswordApi(String old,String newPass){
            this.old=old;
            this.newPass=newPass;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("memberId", MyPrefrences.getUserID(getActivity()));
            map.put("oldPassword", old.toString());
            map.put("newPassword", newPass.toString());
//            map.put("password", password.toString());

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.changePassword,"POST",map);

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


                        confirm.setText("");
                        newPwd.setText("");
                        password.setText("");

                        Util.errorDialog(getActivity(),"You have Changed Password");
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
