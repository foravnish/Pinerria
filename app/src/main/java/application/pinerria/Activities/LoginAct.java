package application.pinerria.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import application.pinerria.Fragments.Changepassword;
import application.pinerria.Fragments.TermAndCondition;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;

public class LoginAct extends AppCompatActivity {
    Dialog  dialog,dialog4,dialog5;
    EditText emailID,password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText newPwd;
    EditText confirm;
    JSONObject jsonObject1;
    TextView textAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        emailID=findViewById(R.id.emailID);
        password=findViewById(R.id.password);

        Button LoginNow=findViewById(R.id.LoginNow);

        LoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidation()){
                    new LoginApi(emailID.getText().toString(),password.getText().toString()).execute();
                }
            }
        });



    }

    private boolean  checkValidationPwd() {

        if (TextUtils.isEmpty(newPwd.getText().toString()))
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

            Util.errorDialog(LoginAct.this,"Confirm Password must be same !");
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


    private boolean checkValidation() {
        if (TextUtils.isEmpty(emailID.getText().toString()))
        {
            emailID.setError("Oops! Email Id blank");
            emailID.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(password.getText().toString()))
        {
            password.setError("Oops! Password blank");
            password.requestFocus();
            return false;
        }
        else if (!emailID.getText().toString().trim().matches(emailPattern)){
            emailID.setError("Oops! Email id is Invalid");
            emailID.requestFocus();
            return false;
        }

        return true;
    }

    class LoginApi extends AsyncTask<String,Void,String>{

        String emailid,password;
        public LoginApi(String emailid, String password){

            this.emailid=emailid;
            this.password=password;
            dialog=new Dialog(LoginAct.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

    }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("email",URLEncoder.encode(emailid.toString()));
            map.put("password",password.toString());

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
                            jsonObject1=jsonArray.getJSONObject(i);

//                            termsNCondition(jsonObject1.optString("password").toString(),jsonObject1.optString("id").toString());

                            if (jsonObject1.optString("first_pass_changed").toString().equalsIgnoreCase("0")){
                                termsNCondition(jsonObject1.optString("password").toString(),jsonObject1.optString("id").toString());


                            }
                            else {
                                Log.d("dfdsgsg", jsonObject1.optString("id").toString());
                                MyPrefrences.setUserLogin(getApplicationContext(), true);
                                MyPrefrences.setUserID(getApplicationContext(), jsonObject1.optString("id").toString());
                                MyPrefrences.setMobile(getApplicationContext(), jsonObject1.optString("contact").toString());
                                MyPrefrences.setEmilId(getApplicationContext(), jsonObject1.optString("email").toString());
                                MyPrefrences.setName(getApplicationContext(), jsonObject1.optString("fname").toString() + " " + jsonObject1.optString("lname").toString());
                                MyPrefrences.setPassword(getApplicationContext(), jsonObject1.optString("password").toString());
                                MyPrefrences.setCityId(getApplicationContext(), jsonObject1.optString("city_ids").toString());
                                MyPrefrences.setCityName(getApplicationContext(), jsonObject1.optString("zone").toString());
                                MyPrefrences.setAreaId(getApplicationContext(), jsonObject1.optString("area_ids").toString());
                                MyPrefrences.setCompId(getApplicationContext(), jsonObject1.optString("company_ids").toString());
                                MyPrefrences.setAgentId(getApplicationContext(), jsonObject1.optString("agentId").toString());

                                Intent intent = new Intent(LoginAct.this, MainActivity.class);
                                intent.putExtra("isflag", "0");
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                    else {
                        Util.errorDialog(LoginAct.this,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(LoginAct.this,"Some Error! or Please connect to the internet.");
                Log.d("fsdgfdgdfgdf",e.toString());
                e.printStackTrace();
            }
        }

    }

    private void termsNCondition(final String pwd, final String memberId) {

        Button Yes_action, No_action;
        TextView heading;
        final EditText comment;
        dialog4 = new Dialog(LoginAct.this);
        dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog4.setContentView(R.layout.alertdialogcustom_tnc);

        Button ok = (Button) dialog4.findViewById(R.id.btn_ok);
        Button cancel = (Button) dialog4.findViewById(R.id.cancel);
        TextView text_tnc = (TextView) dialog4.findViewById(R.id.text_tnc);
        final CheckBox checkBox = (CheckBox) dialog4.findViewById(R.id.checkBox);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox.isChecked()){

                    dialog4.dismiss();

                    changePassword(pwd,memberId);
                }
                else{
                    Util.errorDialog(LoginAct.this,"Please Read and Check Terms & Conditions");
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog4.dismiss();

            }
        });

        text_tnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AboutUsAPi().execute();


            }
        });
        dialog4.show();

    }

    private void changePassword(final String pwd, final String memberId) {



        Button Yes_action, No_action;
        TextView heading;
        final EditText comment;
        dialog5 = new Dialog(LoginAct.this);
        dialog5.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog5.setContentView(R.layout.alertdialogcustom_change_pwd);
        Button submit = (Button) dialog5.findViewById(R.id.submit);
        newPwd = (EditText) dialog5.findViewById(R.id.newPwd);
        confirm = (EditText) dialog5.findViewById(R.id.confirm);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkValidationPwd()){

                    new ChangepasswordApi(pwd.toString(),confirm.getText().toString(),memberId).execute();
//                    dialog5.dismiss();
                }



            }
        });
        dialog5.show();
    }

    class ChangepasswordApi extends AsyncTask<String,Void,String> {

        String old,newPass,memberId;
        public ChangepasswordApi(String old,String newPass,String memberId){
            this.old=old;
            this.newPass=newPass;
            this.memberId=memberId;
            dialog=new Dialog(LoginAct.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("memberId", memberId.toString());
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

                        dialog5.dismiss();
                        confirm.setText("");
                        newPwd.setText("");

                                Log.d("dfdsgsg", jsonObject1.optString("id").toString());
//                                MyPrefrences.setUserLogin(getApplicationContext(), true);
                                MyPrefrences.setUserID(getApplicationContext(), jsonObject1.optString("id").toString());
                                MyPrefrences.setMobile(getApplicationContext(), jsonObject1.optString("contact").toString());
                                MyPrefrences.setEmilId(getApplicationContext(), jsonObject1.optString("email").toString());
                                MyPrefrences.setName(getApplicationContext(), jsonObject1.optString("fname").toString() + " " + jsonObject1.optString("lname").toString());
                                MyPrefrences.setPassword(getApplicationContext(), jsonObject1.optString("password").toString());
                                MyPrefrences.setCityId(getApplicationContext(), jsonObject1.optString("city_ids").toString());
                                MyPrefrences.setAreaId(getApplicationContext(), jsonObject1.optString("area_ids").toString());
                                MyPrefrences.setCompId(getApplicationContext(), jsonObject1.optString("company_ids").toString());
                                MyPrefrences.setAgentId(getApplicationContext(), jsonObject1.optString("agentId").toString());

                                Intent intent = new Intent(LoginAct.this, MainActivity.class);
                                intent.putExtra("isflag", "0");
                                startActivity(intent);
                                finish();


                        Toast.makeText(LoginAct.this, "Login Successfully...", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Util.errorDialog(LoginAct.this,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(LoginAct.this,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }


        class AboutUsAPi extends AsyncTask<String, Void, String> {

        String pId, val1,val2,comm;

        public AboutUsAPi() {

            this.pId = pId;
            this.val1 = val1;
            this.val2 = val2;
            this.comm = comm;
            dialog = new Dialog(LoginAct.this);
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

                        Button Yes_action, No_action;
                        TextView heading;
                        final EditText comment;

                        dialog5 = new Dialog(LoginAct.this);
                        dialog5.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog5.setContentView(R.layout.alertdialogcustom_tnc_data);
                        Button ok = (Button) dialog5.findViewById(R.id.btn_ok);
                        textAbout=(TextView)dialog5.findViewById(R.id.textAbout);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog5.dismiss();

                            }
                        });
                        dialog5.show();

                        JSONArray jsonArray=jsonObject.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1.optString("id").equalsIgnoreCase("4")) {
                                textAbout.setText(Html.fromHtml(jsonObject1.optString("content")));
                            }

                        }


                    } else {
                        Util.errorDialog(LoginAct.this, jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(LoginAct.this, "Some Error! or Please connect to the internet.");
                e.printStackTrace();
            }
        }

    }

}
