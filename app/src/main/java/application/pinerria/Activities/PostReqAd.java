package application.pinerria.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import application.pinerria.Fragments.BuyProductListing;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;

import static android.text.Html.fromHtml;

public class PostReqAd extends AppCompatActivity {



    int check=0;
    Spinner categ,subcateg,targateArea,monthSpiner,yearSpiner,typeSpiner,manufectureSpiner,badroomSpiner,fuelTypeSpiner,valiFor,breadSpinner,state,city;
    EditText descreption,ageOfProd,headline,budget,kmsDone,mobile,emailID,name;
    Button submitAd;
    Dialog dialog;
    TextView tArea,dateUntil;

    List<HashMap<String,String>> PropertyType;
    List<HashMap<String,String>> BedroomData;
    List<HashMap<String,String>> ManufectData;
    List<HashMap<String,String>> FuelTypeData;
    List<HashMap<String,String>> BreedData;
    List<HashMap<String,String>> StateData;
    List<HashMap<String,String>> CityData;
    List<String> CatList = new ArrayList<String>();
    List<String> SubCatList = new ArrayList<String>();

    List<String> PropertyList = new ArrayList<String>();
    List<String> BedroomList = new ArrayList<String>();
    List<String> ManufectList = new ArrayList<String>();
    List<String> FuelTypeList = new ArrayList<String>();
    List<String> BreadList = new ArrayList<String>();
    List<String> StateListing = new ArrayList<String>();
    List<String> CityListing = new ArrayList<String>();
    TextView text,amounts,textRemaining;

    LinearLayout type,manufecture,priceBoth,monthYear,stateLiner,priceBothLin,monthYearLin,beadeliner;
    LinearLayout badroom; EditText brand;


    String [] TargetArea={"Select","Visible in my Residential Complex","Visible within Nearby Area","Visible within Entire Zone"};
    //    String months[]={"Month","January","February","March","April","May","June","July","August","September","October","November","December"};
    String ValidFor[]={"3 Months","6 Months"};
    String targetAreaString[]={"Visible in my Society","Visible within Nearby Area","Visible within Entire Zone"};

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String society3,area3,zone3,society6,area6,zone6;


    String catId,SubcatId,targetstr="";
    String values="";
    List<HashMap<String,String>> DataListCat;
    List<HashMap<String,String>> DataListSubCat;
    ArrayAdapter aa;
    ArrayAdapter aa1;
    ArrayAdapter targerA;
    int val ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_req_ad);
        final ImageView back_img=findViewById(R.id.back_img);
        TextView title=findViewById(R.id.title);
        title.setText("Post Requirement");
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        AdView adView = (AdView) findViewById(R.id.search_ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);



        categ=findViewById(R.id.category);
        subcateg=findViewById(R.id.subCategory);
       // targateArea=findViewById(R.id.targateArea);
        text=findViewById(R.id.text);
        mobile=findViewById(R.id.mobile);
        emailID=findViewById(R.id.emailID);
        submitAd=findViewById(R.id.submitAd);
        headline=findViewById(R.id.headline);
        descreption=findViewById(R.id.descreption);
        budget=findViewById(R.id.budget);
        name=findViewById(R.id.name);
        tArea=findViewById(R.id.tArea);
        dateUntil=findViewById(R.id.dateUntil);
        priceBothLin=findViewById(R.id.priceBothLin);

        mobile.setText(MyPrefrences.getMobile(getApplicationContext()));
        emailID.setText(MyPrefrences.getEmilId(getApplicationContext()));
        name.setText(MyPrefrences.getName(getApplicationContext()));
        DataListCat =new ArrayList<>();
        DataListSubCat =new ArrayList<>();
//
//        targerA = new ArrayAdapter(getApplicationContext(),R.layout.simple_spinner_item,TargetArea);
//        targerA.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        targateArea.setAdapter(targerA);

        tArea.setText("Applicable Zone: "+MyPrefrences.getCityName(getApplicationContext()));

        tArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"This Field can not be Change", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        dateUntil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"This Field can not be Change", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        Date c = new Date();

        if (MyPrefrences.getBuyCat(getApplicationContext()).equalsIgnoreCase("3")){
            priceBothLin.setVisibility(View.GONE);
        }
        else{
            priceBothLin.setVisibility(View.VISIBLE);
        }
        String mon=String.valueOf(c.getMonth()+3);
//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        String formattedDate = df.format(c);
        String formattedDate = c.getDate()+"-"+mon+"-"+String.valueOf(Calendar.getInstance().get(Calendar.YEAR));


        dateUntil.setText("This Requirement Displayed Until: "+formattedDate);
        Log.d("dfgvdfgbdfhdfhn",getIntent().getStringExtra("type"));

        new CategoryApi().execute();
        categ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catId=DataListCat.get(i).get("id").toString();
                new SubCategoryApi(catId).execute();
//
                if (DataListCat.get(i).get("cat_type").equalsIgnoreCase("3")){
                    priceBothLin.setVisibility(View.GONE);
                }
                else{
                    priceBothLin.setVisibility(View.VISIBLE);
                }
//                Log.d("fgdgdfgdfhdf",DataListCat.get(i).get("id"));
//                if (DataListCat.get(i).get("cat_type").toString().equals("1") || DataListCat.get(i).get("cat_type").toString().equals("2")) {
//                    catType = "car";
//                }
//                else if (DataListCat.get(i).get("cat_type").toString().equals("3")){
//                    catType="hb";
//                }
//
//                int spinnerPosition = aa.getPosition(DataListCat.get(i).get("category"));
//                editor2 = refineSearch.edit();
//                Log.d("fgdfgdfhdfh",String.valueOf(spinnerPosition));
//                editor2.putString("catPosition", String.valueOf(spinnerPosition));
//                val=spinnerPosition;
//                editor2.commit();
//
//                Log.d("fvgdgdfgbdfhnd",refineSearch.getString("catPosition", ""));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        subcateg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (!categ.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
                    SubcatId=DataListSubCat.get(i).get("id").toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        targateArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                targetstr= String.valueOf(targateArea.getSelectedItemId());
//
//                Log.d("fgdgdfgdfhdf",targetstr);
////
////                int spinnerPosition = aa1.getPosition(DataListSubCat.get(i).get("subcategory"));
////                editor2 = refineSearch.edit();
////
////                Log.d("fgdfgfgfgfgdfhdfh",String.valueOf(spinnerPosition));
////                editor2.putString("SubcatPosition", String.valueOf(spinnerPosition));
////                editor2.commit();
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });



        submitAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                new sendPostValue().execute();

                if (!subcateg.getSelectedItem().toString().equalsIgnoreCase("Select Sub Category")) {
                    if (validate()) {

                        new RequirementApi().execute();
//                        if (!targateArea.getSelectedItem().equals("Select")) {
//                            Log.d("dfsdgfsdgsdfg", catId.toString());
//                            Log.d("dfsdgfsdgsdfg", SubcatId.toString());
//                            Log.d("dfsdgfsdgsdfg", targetstr.toString());
//                            Log.d("dfsdgfsdgsdfg", headline.getText().toString());
//                            Log.d("dfsdgfsdgsdfg", descreption.getText().toString());
//                            Log.d("dfsdgfsdgsdfg", String.valueOf(targateArea.getSelectedItem()));
//
//
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Select Target Area", Toast.LENGTH_SHORT).show();
//                        }

                    }
                }
                else{
                    Util.errorDialog(PostReqAd.this,"Please Select Sub category.");
                }
            }
        });


    }

    private boolean validate() {

        if (TextUtils.isEmpty(headline.getText().toString()))
        {
            headline.setError("Oops! Title blank");
            headline.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(descreption.getText().toString()))
        {
            descreption.setError("Oops! Descreption blank");
            descreption.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(budget.getText().toString()))
        {

            if (MyPrefrences.getBuyCat(getApplicationContext()).equalsIgnoreCase("3")){
//                priceBothLin.setVisibility(View.GONE);
                return true;
            }
            else{
//                priceBothLin.setVisibility(View.VISIBLE);
                budget.setError("Oops! Budget blank");
                budget.requestFocus();
                return false;
            }


        }

        else if (TextUtils.isEmpty(emailID.getText().toString()))
        {
            emailID.setError("Oops! Email id blank");
            emailID.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(mobile.getText().toString()))
        {
            mobile.setError("Oops! mobile no blank");
            mobile.requestFocus();
            return false;
        }

        else if (!emailID.getText().toString().trim().matches(emailPattern))
        {
            emailID.setError("Oops! invalid email");
            emailID.requestFocus();
            return false;
        }

        else if (mobile.getText().toString().length()<10){
            mobile.setError("Oops! invalid mobile no.");
            mobile.requestFocus();
            return false;
        }




        return true;

    }

    class RequirementApi extends AsyncTask<String,Void,String>{

        String emailid,password;
        public RequirementApi(){

            this.emailid=emailid;
            this.password=password;
            dialog=new Dialog(PostReqAd.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            Log.d("dfsdgfsdgsdfg", catId.toString());
            Log.d("dfsdgfsdgsdfg", SubcatId.toString());
            Log.d("dfsdgfsdgsdfg", targetstr.toString());
            Log.d("dfsdgfsdgsdfg", headline.getText().toString());
            Log.d("dfsdgfsdgsdfg", descreption.getText().toString());
         //   Log.d("dfsdgfsdgsdfg", String.valueOf(targateArea.getSelectedItem()));

            map.put("cat_id", catId.toString());
            map.put("subcat_id", SubcatId.toString());
            map.put("visibility", MyPrefrences.getCityId(getApplicationContext()));
            map.put("title", headline.getText().toString());
            map.put("requirement", descreption.getText().toString());
            map.put("budget", budget.getText().toString());
            map.put("contact_person", name.getText().toString());
            map.put("mobile", mobile.getText().toString());
            map.put("email", emailID.getText().toString());
            map.put("apnashop_uid", MyPrefrences.getUserID(getApplicationContext()));
            map.put("displayUntil", dateUntil.getText().toString());


            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.postRequirement,"POST",map);

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

//                        Util.errorDialog(PostReqAd.this,"Your Requirement Successfully Posted");

                        final Dialog dialog = new Dialog(PostReqAd.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.alertdialogcustom);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView text = (TextView) dialog.findViewById(R.id.msg_txv);
                        text.setText(fromHtml("Your Requirement Successfully Posted"));
                        Button ok = (Button) dialog.findViewById(R.id.btn_ok);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        dialog.show();

//
//                        }
                    }
                    else {
                        Util.errorDialog(PostReqAd.this,jsonObject.optString("data"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(PostReqAd.this,"Some Error! or Please connect to the internet.");
                e.printStackTrace();
            }
        }


    }


    class CategoryApi extends AsyncTask<String,Void,String> {

        String type;
        public CategoryApi(){
            this.type=type;
            dialog=new Dialog(PostReqAd.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

//            map.put("categoryId", getArguments().getString("id"));
//            map.put("userId", MyPrefrences.getUserID(getActivity()));
//            map.put("password", password.toString());

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.categoryList,"POST",map);

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
                        DataListCat.clear();
                        CatList.clear();
                        // CatList.add("Select");
                        final JSONArray jsonArray=jsonObject.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id",jsonObject1.optString("id"));
                            map.put("category",jsonObject1.optString("category"));
                            map.put("cat_type",jsonObject1.optString("cat_type"));

                            CatList.add(jsonObject1.optString("category"));

                            DataListCat.add(map);



                            aa = new ArrayAdapter(getApplicationContext(),R.layout.simple_spinner_item,CatList);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            categ.setAdapter(aa);


                            Log.d("dfgdfhdhdfhdfjhd",getIntent().getStringExtra("type"));
                            if (jsonObject1.optString("category").equals(getIntent().getStringExtra("type"))){
                                Log.d("fgvfdgsfdgsfdfdfgdgrg", String.valueOf(i+1));
                                val=i;
                            }
                            categ.setSelection(val);

                        }




                    }
                    else {
                        Util.errorDialog(getApplicationContext(),jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(getApplicationContext(),"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }
    class SubCategoryApi extends AsyncTask<String,Void,String> {

        String  id;
        public SubCategoryApi(String id){
            this.id=id;
            dialog=new Dialog(PostReqAd.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();
            Log.d("fgfdgdfhgghdfhg",id);
            map.put("categoryId", id);

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.subcategorybyCat,"POST",map);

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

                        DataListSubCat.clear();
                        SubCatList.clear();

                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("id", "");
                        DataListSubCat.add(map2);
                        SubCatList.add("Select Sub Category");

                        // SubCatList.add("Select");
                        final JSONArray jsonArray=jsonObject.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id",jsonObject1.optString("id"));
                            map.put("subcategory",jsonObject1.optString("subcategory"));
                            SubCatList.add(jsonObject1.optString("subcategory"));

                            DataListSubCat.add(map);

                            aa1 = new ArrayAdapter(getApplicationContext(),R.layout.simple_spinner_item,SubCatList);
                            aa1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            subcateg.setAdapter(aa1);


                        }

                    }
                    else {
                        Util.errorDialog(getApplicationContext(),jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(getApplicationContext(),"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }






}
