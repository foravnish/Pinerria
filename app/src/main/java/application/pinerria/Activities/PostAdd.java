package application.pinerria.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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

import com.ebs.android.sdk.Config;
import com.ebs.android.sdk.EBSPayment;
import com.ebs.android.sdk.PaymentRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import application.pinerria.Fragments.DeshAll;
import application.pinerria.Fragments.Deshboard;
import application.pinerria.Fragments.HomeFragment;
import application.pinerria.Fragments.SelectArea;
import application.pinerria.Fragments.ShowRequirement;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;

import static android.text.Html.fromHtml;

public class PostAdd extends AppCompatActivity {


    private static final int ACC_ID = 27791;// Provided by EBS
    private static final String SECRET_KEY = "87a9449095742721db3814e444495e9b";// Provided by EBS
    private static String HOST_NAME = "";
    String validNum;
    Spinner spiner1, spiner2;
    Dialog dialog4;
    boolean isImage1 = false, isImage2 = false, isImage3 = false, isImage4 = false, isImage5 = false;
    int check = 0;
    Spinner targetArea, category, subCategory, monthSpiner, yearSpiner, typeSpiner, manufectureSpiner, badroomSpiner, fuelTypeSpiner, valiFor, breadSpinner, state, city;
    EditText descreption, ageOfProd, headline, min, kmsDone, mobile, emailID;
    Button submitAd;
    Dialog dialog;
    List<HashMap<String, String>> DataList;
    List<HashMap<String, String>> DataListSubCat;
    List<HashMap<String, String>> PropertyType;
    List<HashMap<String, String>> BedroomData;
    List<HashMap<String, String>> ManufectData;
    List<HashMap<String, String>> FuelTypeData;
    List<HashMap<String, String>> BreedData;
    List<HashMap<String, String>> StateData;
    List<HashMap<String, String>> CityData;
    List<String> CatList = new ArrayList<String>();
    List<String> SubCatList = new ArrayList<String>();
    List<String> SubCatList2 = new ArrayList<String>();
    List<String> PropertyList = new ArrayList<String>();
    List<String> BedroomList = new ArrayList<String>();
    List<String> ManufectList = new ArrayList<String>();
    List<String> FuelTypeList = new ArrayList<String>();
    List<String> BreadList = new ArrayList<String>();
    List<String> StateListing = new ArrayList<String>();
    List<String> CityListing = new ArrayList<String>();
    TextView text,  textRemaining;
    ShimmerTextView amounts;
    Shimmer shimmer;

    LinearLayout type, manufecture, priceBoth, monthYear, stateLiner, priceBothLin, monthYearLin, beadeliner;
    LinearLayout badroom;
    EditText brand;
    LinearLayout fueltype, helpgreen,helpValidfor;
    ImageView image1, image2, image3, image4, image5;
    ImageView img1, img2, img3, img4, img5;

    String[] months = {"Month", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] spi1 = {"Select", "Excellent", "Good", "Satisfactory", "Needs improvement", "Poor"};
    String[] spi2 = {"Select", "Yes", "No"};
    //    String months[]={"Month","January","February","March","April","May","June","July","August","September","October","November","December"};
    String ValidFor[] = {"3 Months", "6 Months"};
    String ValidFor2[] = {"3 Months", "6 Months", "1 Months Free"};
    String targetAreaString[] = {"Visible in my Society", "Visible within Nearby Area", "Visible within Entire Zone"};
    Bitmap bm;
    String filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4, filepath5, fileName5 = null;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String society3, area3, zone3, society6, area6, zone6;

    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3;
    RadioButton selectedRadioButton;
    ProgressDialog progress;

    String catId, subId, manufId, fuelId, badroomId, breedId = "", cityId, stateId, properId;
    String values = "";
    int val;
    Button showReqAdd;
    ArrayList<HashMap<String, String>> custom_post_parameters;
    int  trial;

    int i1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);
        final ImageView back_img = findViewById(R.id.back_img);
        TextView title = findViewById(R.id.title);
        title.setText("Post Ad");
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        AdView adView = (AdView) findViewById(R.id.search_ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        targetArea = findViewById(R.id.targetArea);
        category = findViewById(R.id.category);
        subCategory = findViewById(R.id.subCategory);
        monthSpiner = findViewById(R.id.monthSpiner);
        yearSpiner = findViewById(R.id.yearSpiner);
        showReqAdd = findViewById(R.id.showReqAdd);

        text = findViewById(R.id.text);

        type = findViewById(R.id.type);
        manufecture = findViewById(R.id.manufecture);
        badroom = findViewById(R.id.badroom);
        brand = findViewById(R.id.brand);
        state = findViewById(R.id.state);
        kmsDone = findViewById(R.id.kmsDone);
        city = findViewById(R.id.city);
        fueltype = findViewById(R.id.fueltype);
        typeSpiner = findViewById(R.id.typeSpiner);
        manufectureSpiner = findViewById(R.id.manufectureSpiner);
        badroomSpiner = findViewById(R.id.badroomSpiner);
        fuelTypeSpiner = findViewById(R.id.fuelTypeSpiner);
        valiFor = findViewById(R.id.valiFor);
        breadSpinner = findViewById(R.id.breadSpinner);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        priceBoth = findViewById(R.id.priceBoth);
        monthYear = findViewById(R.id.monthYear);
        stateLiner = findViewById(R.id.stateLiner);
        priceBothLin = findViewById(R.id.priceBothLin);
        monthYearLin = findViewById(R.id.monthYearLin);
        beadeliner = findViewById(R.id.beadeliner);
        mobile = findViewById(R.id.mobile);
        emailID = findViewById(R.id.emailID);
        amounts = findViewById(R.id.amounts);
        ageOfProd = findViewById(R.id.ageOfProd);
        headline = findViewById(R.id.headline);
        min = findViewById(R.id.min);
        textRemaining = findViewById(R.id.textRemaining);
        descreption = findViewById(R.id.descreption);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        submitAd = findViewById(R.id.submitAd);
        helpgreen = findViewById(R.id.helpgreen);
        helpValidfor = findViewById(R.id.helpValidfor);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);

        Log.d("fgdfgdfhgdfhdfh", getIntent().getStringExtra("heading"));

        HOST_NAME = "EBS";


        shimmer = new Shimmer();
        shimmer.start(amounts);

        showReqAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!category.getSelectedItem().toString().equalsIgnoreCase("Select Category")){

                    if (!subCategory.getSelectedItem().toString().equalsIgnoreCase("Select Sub Category")){
                        Log.d("dgvdfgdfgdfh",catId);
                        Log.d("dgvdfgdfgdfh",subId);

                        Intent intent = new Intent(PostAdd.this, ShowRequirementAct.class);
                        intent.putExtra("catid",catId);
                        intent.putExtra("Subcatid",subId);
                        intent.putExtra("typeCategory",MyPrefrences.getHomeType(getApplicationContext()));
                        startActivity(intent);



                    }
                    else {
                        Util.errorDialog(PostAdd.this,"Please Select Sub Category");
                    }
                }
                else{
                    Util.errorDialog(PostAdd.this,"Please Select Category and Sub Category");
                }
                //Intent intent = new Intent(PostAdd.this, ShowRequirementAct.class);
                //startActivity(intent);

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.radioButton1: {

                        values = "Mob";
                        break;
                    }
                    case R.id.radioButton2: {

                        values = "E-mail";
                        break;
                    }
                    case R.id.radioButton3: {

                        values = "Either";
                        break;
                    }

                }

            }
        });


        DataList = new ArrayList<>();
        DataListSubCat = new ArrayList<>();
        PropertyType = new ArrayList<>();
        BedroomData = new ArrayList<>();
        ManufectData = new ArrayList<>();
        FuelTypeData = new ArrayList<>();
        BreedData = new ArrayList<>();
        StateData = new ArrayList<>();
        CityData = new ArrayList<>();


        ActivityCompat.requestPermissions(PostAdd.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);


        Log.d("gfdgdfhdhdgh", getIntent().getStringExtra("Cat_id").toString());

        Log.d("Hometype", MyPrefrences.getHomeType(getApplicationContext()));

        if (MyPrefrences.getHomeType(getApplicationContext()).equals("home")) {
            priceBothLin.setVisibility(View.GONE);
        } else if (MyPrefrences.getHomeType(getApplicationContext()).equals("sell")) {
            priceBothLin.setVisibility(View.VISIBLE);
        }


        helpgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showInfoAlertDialog(PostAdd.this, "Headline", HomeFragment.string[8]);
            }
        });
        helpValidfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showInfoAlertDialog(PostAdd.this, "Headline", HomeFragment.string[11]);
            }
        });

        if (getIntent().getStringExtra("Cat_id").equalsIgnoreCase("1")) {
            beadeliner.setVisibility(View.GONE);
        } else if (getIntent().getStringExtra("Cat_id").equalsIgnoreCase("2")) {

//            subCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    Log.d("dfsfgsdgdfgdfg",DataListSubCat.get(i).get("subcategory").toString());
//                    if (!DataListSubCat.get(i).get("subcategory").toString().equalsIgnoreCase("ALL")) {
//                        new BreedApi(DataListSubCat.get(i).get("subcategory").toString()).execute();
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
            // beadeliner.setVisibility(View.VISIBLE);
        } else if (getIntent().getStringExtra("Cat_id").equalsIgnoreCase("3")) {
            beadeliner.setVisibility(View.GONE);
        }

        submitAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d("gfdgdfhgdfhd", getIntent().getStringExtra("Cat_id").toString());

//                new CarDataSubmit(descreption.getText().toString(), ageOfProd.getText().toString(),
//                        headline.getText().toString(), min.getText().toString(), kmsDone.getText().toString(), mobile.getText().toString(),
//                        emailID.getText().toString(), brand.getText().toString()).execute();

                if (!category.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
                    if (validate()) {
                        if (radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked()) {
                            if (check == 1) {
                                if (!subCategory.getSelectedItem().equals("Select Sub Category")) {

                                    double amnt = Double.parseDouble(amounts.getText().toString().replace("₹ ", ""));
                                    Log.d("fgdgdfghdfhdfhj", String.valueOf(amnt));

                                    if (amnt > 0) {
                                        final Dialog dialog = new Dialog(PostAdd.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.alertdialogcustom_opt);
                                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        TextView text = (TextView) dialog.findViewById(R.id.msg_txv);
                                        text.setText(fromHtml("Amount to be Paid ₹ " + amnt + ",\nContinue to make payment ?"));
                                        Button ok = (Button) dialog.findViewById(R.id.btn_ok);
                                        Button cancel = (Button) dialog.findViewById(R.id.cancel);
                                        ok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();

                                                submiting();

                                            }
                                        });
                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.show();
                                    } else {
                                        submiting();
                                    }


                                } else {
                                    Util.errorDialog(PostAdd.this, "Please Select Sub Category");
                                }


                            } else {
                                Util.errorDialog(PostAdd.this, "Please Select Default Image");
                            }
                        } else {
                            Util.errorDialog(PostAdd.this, "Please Select Advice to potential buyers...");
                        }

                    }
                } else {
                    Util.errorDialog(PostAdd.this, "Please Select Category");
                }

            }

            private void submiting() {

                if (getIntent().getStringExtra("Cat_id").toString().equals("1")) {
                    Log.d("fggdfsgdsfgdg", "1");
                    Log.d("fggdfghhfghfgfsgdsfgdg", String.valueOf(category.getSelectedItemId()));
//                    if (category.getSelectedItem().toString().equalsIgnoreCase("Car")) {
                    if (String.valueOf(category.getSelectedItemId()).equals("1")) {
                        Log.d("fgdgdfghdfhdf", "car");

                        if (!manufectureSpiner.getSelectedItem().toString().equalsIgnoreCase("Manufacturer")) {
                            if (!fuelTypeSpiner.getSelectedItem().toString().equalsIgnoreCase("Fuel Type")) {
                                new CarDataSubmit(descreption.getText().toString(), ageOfProd.getText().toString(),
                                        headline.getText().toString(), min.getText().toString(), kmsDone.getText().toString(), mobile.getText().toString(),
                                        emailID.getText().toString(), brand.getText().toString()).execute();
                            } else {
                                Util.errorDialog(PostAdd.this, "Please select Fuel Type");
                            }
                        }
                        else{
                            Util.errorDialog(PostAdd.this, "Please select Manufactuer");
                        }

                    } else if (category.getSelectedItem().toString().equalsIgnoreCase("Property")) {
                        Log.d("fgdgdfghdfhdf", "property");

                        new PropDataSubmit(descreption.getText().toString(), ageOfProd.getText().toString(),
                                headline.getText().toString(), min.getText().toString(), kmsDone.getText().toString(), mobile.getText().toString(),
                                emailID.getText().toString(), brand.getText().toString()).execute();
                    }


                } else if (getIntent().getStringExtra("Cat_id").toString().equals("2")) {
                    Log.d("fggdfsgdsfgdg", "2");

                    new OtherThenDataSubmit(descreption.getText().toString(), ageOfProd.getText().toString(),
                            headline.getText().toString(), min.getText().toString(), kmsDone.getText().toString(), mobile.getText().toString(),
                            emailID.getText().toString(), brand.getText().toString()).execute();


                } else if (getIntent().getStringExtra("Cat_id").toString().equals("3")) {
                    Log.d("fggdfsgdsfgdg", "3");


                    new HomeBusDataSubmit(descreption.getText().toString(), ageOfProd.getText().toString(),
                            headline.getText().toString(), min.getText().toString(), kmsDone.getText().toString(), mobile.getText().toString(),
                            emailID.getText().toString(), brand.getText().toString()).execute();

                }
            }
        });


//        Log.d("dgfdgdfhgdfhdjhd",getIntent().getStringExtra("forPayment"));
//
//        if (getIntent().getStringExtra("forPayment").equalsIgnoreCase("car")){
//            Log.d("fgfdgdfhd","car");
//            new CarDataSubmit(descreption.getText().toString(), ageOfProd.getText().toString(),
//                    headline.getText().toString(), min.getText().toString(), kmsDone.getText().toString(), mobile.getText().toString(),
//                    emailID.getText().toString(), brand.getText().toString()).execute();
//        }
//        else if (getIntent().getStringExtra("forPayment").equalsIgnoreCase("property")){
//            Log.d("fgfdgdfhd","pro");
//            new PropDataSubmit(descreption.getText().toString(), ageOfProd.getText().toString(),
//                    headline.getText().toString(), min.getText().toString(), kmsDone.getText().toString(), mobile.getText().toString(),
//                    emailID.getText().toString(), brand.getText().toString()).execute();
//        }
//        else if (getIntent().getStringExtra("forPayment").equalsIgnoreCase("other")){
//            Log.d("fgfdgdfhd","oth");
//            new OtherThenDataSubmit(descreption.getText().toString(), ageOfProd.getText().toString(),
//                    headline.getText().toString(), min.getText().toString(), kmsDone.getText().toString(), mobile.getText().toString(),
//                    emailID.getText().toString(), brand.getText().toString()).execute();
//        }
//        else if (getIntent().getStringExtra("forPayment").equalsIgnoreCase("homeB")){
//            Log.d("fgfdgdfhd","hom");
//            new HomeBusDataSubmit(descreption.getText().toString(), ageOfProd.getText().toString(),
//                    headline.getText().toString(), min.getText().toString(), kmsDone.getText().toString(), mobile.getText().toString(),
//                    emailID.getText().toString(), brand.getText().toString()).execute();
//        }
//        else {
//            Log.d("fgfdgdfhd","else");
//            new Car_Pro_Category().execute();
//            new StateList().execute();
//
//        }

        new Car_Pro_Category().execute();
        new StateList().execute();


        //  new DrpDownData().execute();

        mobile.setText(MyPrefrences.getMobile(getApplicationContext()));
        emailID.setText(MyPrefrences.getEmilId(getApplicationContext()));

        ArrayAdapter aa = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, months);
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        monthSpiner.setAdapter(aa);
//
//        Log.d("fsdfsdfgsdgdsdsdssdfgfg", String.valueOf(trial));
//        if (trial>0) {
//            ArrayAdapter Valid = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, ValidFor2);
//            Valid.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//            valiFor.setAdapter(Valid);
//        }
//        else{
//            ArrayAdapter Valid = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, ValidFor);
//            Valid.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//            valiFor.setAdapter(Valid);
//        }
        ArrayAdapter targetAStr = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, targetAreaString);
        targetAStr.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        targetArea.setAdapter(targetAStr);

        ArrayList<String> year_array = new ArrayList<String>();
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        year_array.add("Year");
        for (int i = year; i >= 1950; i--) {

            year_array.add(i + "");
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(PostAdd.this, R.layout.simple_spinner_item, year_array);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        yearSpiner.setAdapter(dataAdapter);

        monthSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("dfgdfhdfhdhdg", monthSpiner.getSelectedItem().toString());


                try {
                    Calendar dob = Calendar.getInstance();
                    Calendar today = Calendar.getInstance();
                    dob.set(Integer.parseInt(yearSpiner.getSelectedItem().toString()), Integer.parseInt(monthSpiner.getSelectedItem().toString()), 01);
                    int yearCal = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
                    int months = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH);
                    int mon = yearCal * 12;
                    int total = mon + months;
                    if (total<-2){
                       Util.errorDialog(PostAdd.this,"Month & Year can not be greater than current date. Please change the same.");
                    }

                    else {
                        if (total+1==-1){
                            ageOfProd.setText("0");
                        }
                        else {
                            ageOfProd.setText(total + 1 + "");
                        }
                    }
                    Log.d("fddgdgsdfgdfsg", String.valueOf(total));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        yearSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("dfgdfhdfhdhdg", yearSpiner.getSelectedItem().toString());

                try {
                    Calendar dob = Calendar.getInstance();
                    Calendar today = Calendar.getInstance();
                    dob.set(Integer.parseInt(yearSpiner.getSelectedItem().toString()), Integer.parseInt(monthSpiner.getSelectedItem().toString()), 01);
                    int yearCal = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
                    int months = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH);
                    int mon = yearCal * 12;
                    int total = mon + months;
                    if (total<-2){
                        Util.errorDialog(PostAdd.this,"Month & Year can not be greater than current date. Please change the same.");
                    }

                    else {
                        if (total+1==-1){
                            ageOfProd.setText("0");
                        }
                        else {
                            ageOfProd.setText(total + 1 + "");
                        }
                    }
                    Log.d("fddgdgsdfgdfsg", String.valueOf(total));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Log.d("fgsdgdfgdfhd", getIntent().getStringExtra("threemonths"));
//        Log.d("fgsdgdfgdfhd",getIntent().getStringExtra("sixmonths"));


        try {
            JSONObject threeJson = new JSONObject(getIntent().getStringExtra("threemonths"));
            JSONObject sixJson = new JSONObject(getIntent().getStringExtra("sixmonths"));

            society3 = threeJson.optString("within_office");
            area3 = threeJson.optString("within_nearby");
            zone3 = threeJson.optString("within_citys");

            society6 = sixJson.optString("within_office");
            area6 = sixJson.optString("within_nearby");
            zone6 = sixJson.optString("within_citys");


        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Log.d("dfdsgfdgdfghd",getIntent().getStringExtra("areatype"));
        if (getIntent().getStringExtra("areatype").equalsIgnoreCase("society")) {
            targetArea.setSelection(0);
            Log.d("dfdsgdfhdfgh", society3 + " " + society6);
            amounts.setText("₹ " + society3);

        } else if (getIntent().getStringExtra("areatype").equalsIgnoreCase("area")) {
            targetArea.setSelection(1);
            Log.d("dfdsgdfhdfgh", area3 + " " + area6);
            amounts.setText("₹ " + area3);
        } else if (getIntent().getStringExtra("areatype").equalsIgnoreCase("zone")) {
            targetArea.setSelection(2);
            Log.d("dfdsgdfhdfgh", zone3 + " " + zone6);
            amounts.setText("₹ " + zone3);
        }

        valiFor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (selectedItem.equals("3 Months")) {
                    if (getIntent().getStringExtra("areatype").equalsIgnoreCase("society")) {
                        targetArea.setSelection(0);
                        Log.d("dfdsgdfhdfgh", society3 + " " + society6);
                        amounts.setText("₹ " + society3);


                    } else if (getIntent().getStringExtra("areatype").equalsIgnoreCase("area")) {
                        targetArea.setSelection(1);
                        Log.d("dfdsgdfhdfgh", area3 + " " + area6);
                        amounts.setText("₹ " + area3);
                    } else if (getIntent().getStringExtra("areatype").equalsIgnoreCase("zone")) {
                        targetArea.setSelection(2);
                        Log.d("dfdsgdfhdfgh", zone3 + " " + zone6);
                        amounts.setText("₹ " + zone3);
                    }
                    validNum="3";

                } else if (selectedItem.equals("6 Months")) {

                    if (getIntent().getStringExtra("areatype").equalsIgnoreCase("society")) {
                        targetArea.setSelection(0);
                        Log.d("dfdsgdfhdfgh", society3 + " " + society6);
                        amounts.setText("₹ " + society6);

                    } else if (getIntent().getStringExtra("areatype").equalsIgnoreCase("area")) {
                        targetArea.setSelection(1);
                        Log.d("dfdsgdfhdfgh", area3 + " " + area6);
                        amounts.setText("₹ " + area6);
                    } else if (getIntent().getStringExtra("areatype").equalsIgnoreCase("zone")) {
                        targetArea.setSelection(2);
                        Log.d("dfdsgdfhdfgh", zone3 + " " + zone6);
                        amounts.setText("₹ " + zone6);
                    }
                    validNum="6";
                }
                else if (selectedItem.equals("1 Months Free")){
                    if (getIntent().getStringExtra("areatype").equalsIgnoreCase("society")) {
                        targetArea.setSelection(0);
                        Log.d("dfdsgdfhdfgh", society3 + " " + society6);
                        amounts.setText("₹ " + "0");

                    } else if (getIntent().getStringExtra("areatype").equalsIgnoreCase("area")) {
                        targetArea.setSelection(1);
                        Log.d("dfdsgdfhdfgh", area3 + " " + area6);
                        amounts.setText("₹ " + "0");
                    } else if (getIntent().getStringExtra("areatype").equalsIgnoreCase("zone")) {
                        targetArea.setSelection(2);
                        Log.d("dfdsgdfhdfgh", zone3 + " " + zone6);
                        amounts.setText("₹ " + "0");
                    }
                    validNum="1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        descreption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                textRemaining.setText(String.valueOf(charSequence.length()));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = descreption.length();
                int rem = 3000 - length;
                String convert = String.valueOf(rem);
                textRemaining.setText(convert + " Characters remaining");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Util.cancelPgDialog(dialog);
                //DataList.get(i).get("id");
                Log.d("fdsdgdfgbdfh", category.getSelectedItem().toString());
                if (!category.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
                    new SubCategoryApi(DataList.get(i).get("id")).execute();

                } else if (category.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
                    SubCatList2.add("Select Sub Category");
                    ArrayAdapter aa = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, SubCatList2);
                    aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                    subCategory.setAdapter(aa);
                }

                //Here.... Visibility GONE or VISIBLE
                catId = DataList.get(i).get("id");
                if (DataList.get(i).get("category").equalsIgnoreCase("Pets")) {


                    subCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Util.cancelPgDialog(dialog);

                            if (!subCategory.getSelectedItem().toString().equals("Select Sub Category")) {
                                beadeliner.setVisibility(View.VISIBLE);
                                text.setText("Month & year of Birth / First Purchase");
                            }
                            if (subCategory.getSelectedItem().toString().equals("Select Sub Category")) {
                                beadeliner.setVisibility(View.GONE);
                            }
                            subId = DataListSubCat.get(i).get("id").toString();
                            Log.d("dfsfgsdgdfgdfg", DataListSubCat.get(i).get("id").toString());
//                            new BreedApi(DataListSubCat.get(i).get("subcategory").toString()).execute();
//
                            if (DataListSubCat.get(i).get("id").toString().equalsIgnoreCase("")) {
                                BreadList.clear();
                                BreadList.add("Select Breed");
                            }

                            try {
                                if (DataListSubCat.get(i).get("subcategory").toString() != null) {
                                    new BreedApi(DataListSubCat.get(i).get("subcategory").toString()).execute();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BreadList.clear();
                            BreadList.add("Select Breed");

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } else {
                    beadeliner.setVisibility(View.GONE);
                }

                Log.d("fsdfgsdgdfgdfgd",DataList.get(i).get("id"));
                if (DataList.get(i).get("id").equalsIgnoreCase("2")) {
//                if (catId.equals("2")) {
                    text.setText("Month & year of Completion / First Purchase");

                    type.setVisibility(View.VISIBLE);
                    badroom.setVisibility(View.VISIBLE);
                    state.setVisibility(View.VISIBLE);
                    city.setVisibility(View.VISIBLE);
                    stateLiner.setVisibility(View.VISIBLE);

                    manufecture.setVisibility(View.GONE);
                    brand.setVisibility(View.GONE);
                    kmsDone.setVisibility(View.GONE);
                    fueltype.setVisibility(View.GONE);
                    monthYearLin.setVisibility(View.VISIBLE);
                    monthYear.setVisibility(View.VISIBLE);
                    ageOfProd.setVisibility(View.VISIBLE);


                } else if (DataList.get(i).get("id").equalsIgnoreCase("6")) {
//                } else if (catId.equals("6")) {

                    text.setText("Month & year of Manufacture / First Purchase");

                    type.setVisibility(View.GONE);
                    badroom.setVisibility(View.GONE);
                    state.setVisibility(View.GONE);
                    city.setVisibility(View.GONE);
                    stateLiner.setVisibility(View.GONE);

                    manufecture.setVisibility(View.VISIBLE);
                    brand.setVisibility(View.VISIBLE);
                    kmsDone.setVisibility(View.VISIBLE);
                    fueltype.setVisibility(View.VISIBLE);
                    text.setVisibility(View.VISIBLE);
                    //priceBothLin.setVisibility(View.VISIBLE);
                    monthYearLin.setVisibility(View.VISIBLE);
                    monthYear.setVisibility(View.VISIBLE);
                    ageOfProd.setVisibility(View.VISIBLE);

                } else {
                    type.setVisibility(View.GONE);
                    badroom.setVisibility(View.GONE);
                    state.setVisibility(View.GONE);
                    city.setVisibility(View.GONE);
                    manufecture.setVisibility(View.GONE);
                    brand.setVisibility(View.GONE);
                    kmsDone.setVisibility(View.GONE);
                    fueltype.setVisibility(View.GONE);
                    monthYear.setVisibility(View.GONE);
                    priceBoth.setVisibility(View.VISIBLE);
                    stateLiner.setVisibility(View.VISIBLE);
//                    priceBothLin.setVisibility(View.VISIBLE);
                    monthYearLin.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    ageOfProd.setVisibility(View.GONE);
                    stateLiner.setVisibility(View.GONE);
                }

                if (getIntent().getStringExtra("heading").equalsIgnoreCase("other")) {
                    Log.d("dsfsdfgsdg", "true");
                    monthYearLin.setVisibility(View.VISIBLE);
                    monthYear.setVisibility(View.VISIBLE);
                    ageOfProd.setVisibility(View.VISIBLE);
                    text.setVisibility(View.VISIBLE);
                    text.setText("Month & year of Manufacture / First Purchase");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        subCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!category.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
                    subId = DataListSubCat.get(i).get("id").toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fuelTypeSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                fuelId = FuelTypeData.get(i).get("id");

                Log.d("dfsdfgsdgdf", fuelId + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        manufectureSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                manufId = ManufectData.get(i).get("id");
                Log.d("fgdgdfgdfh", ManufectData.get(i).get("id"));
                Log.d("fgdgdfgdfh", manufId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        typeSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!typeSpiner.getSelectedItem().toString().equalsIgnoreCase("Type")) {
                    properId = PropertyType.get(i).get("id").toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        badroomSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!badroomSpiner.getSelectedItem().toString().equalsIgnoreCase("Bedrooms")) {
                    badroomId = BedroomData.get(i).get("id").toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        breadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                breedId = BreedData.get(i).get("id");
                Log.d("gfsdgfsdhgdfhd", breedId + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!city.getSelectedItem().toString().equalsIgnoreCase("Select City")) {
                    cityId = CityData.get(i).get("id").toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOne(1, 2);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOne(3, 4);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOne(5, 6);
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOne(7, 8);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOne(9, 10);
            }
        });

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOne(1, 2);
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOne(3, 4);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOne(5, 6);
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOne(7, 8);
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOne(9, 10);
            }
        });

    }

    private void imageOne(final int cam, final int gal) {

        final CharSequence[] items = {"Take from Camera", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(PostAdd.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                try {
                    if (items[item].equals("Take from Camera")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, cam);
                    } else if (items[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(
                                Intent.createChooser(intent, "Select File"),
                                gal);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "errorrr...", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setCancelable(true);
        builder.show();

    }

    private boolean validate() {


        if (TextUtils.isEmpty(headline.getText().toString())) {
            headline.setError("Oops! Headline blank");
            headline.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(min.getText().toString())) {
            if (MyPrefrences.getHomeType(getApplicationContext()).equals("sell")) {
                min.setError("Oops! Price blank");
                min.requestFocus();
                return false;
            }


        } else if (TextUtils.isEmpty(descreption.getText().toString())) {
            descreption.setError("Oops! Descreption blank");
            descreption.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(emailID.getText().toString())) {
            emailID.setError("Oops! Email id blank");
            emailID.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(mobile.getText().toString())) {
            mobile.setError("Oops! mobile no blank");
            mobile.requestFocus();
            return false;
        } else if (!emailID.getText().toString().trim().matches(emailPattern)) {
            emailID.setError("Oops! invalid email");
            emailID.requestFocus();
            return false;
        } else if (mobile.getText().toString().length() < 10) {
            mobile.setError("Oops! invalid mobile no.");
            mobile.requestFocus();
            return false;
        }


        return true;

    }


    class BreedApi extends AsyncTask<String, Void, String> {

        String id;

        public BreedApi(String id) {
            this.id = id;
            dialog = new Dialog(PostAdd.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            // Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> map = new HashMap<>();

//            map.put("typeId", getArguments().getString("Cat_id"));
            map.put("subCategoryId", id);

            JSONParser jsonParser = new JSONParser();
            String result = jsonParser.makeHttpRequest(Api.breedtype, "POST", map);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("response", ": " + s);

            //  Util.cancelPgDialog(dialog);
            try {
                final JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        BreedData.clear();

                        //  CatList.add("Select");


                        final JSONArray jsonArray = jsonObject.getJSONArray("message");

                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("", "");
                        BreedData.add(map2);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id", jsonObject1.optString("id"));
                            map.put("pets_type", jsonObject1.optString("pets_type"));
                            map.put("breedtype", jsonObject1.optString("breedtype"));

                            BreadList.add(jsonObject1.optString("breedtype"));

                            BreedData.add(map);

                            ArrayAdapter aa = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, BreadList);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            breadSpinner.setAdapter(aa);

                        }
                    } else {
                        Util.errorDialog(PostAdd.this, jsonObject.optString("message"));
                        BreadList.clear();
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(PostAdd.this, "Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }

    class StateList extends AsyncTask<String, Void, String> {


        public StateList() {

            dialog = new Dialog(PostAdd.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            // Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> map = new HashMap<>();

//            map.put("typeId", getArguments().getString("Cat_id"));
//            map.put("typeId", getIntent().getStringExtra("Cat_id"));

            JSONParser jsonParser = new JSONParser();
            String result = jsonParser.makeHttpRequest(Api.getState, "POST", map);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("response", ": " + s);

            //  Util.cancelPgDialog(dialog);
            try {
                final JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        StateData.clear();
                        StateListing.clear();
                        //  CatList.add("Select");
                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("category", "");
                        StateData.add(map2);
                        StateListing.add("Select State");

                        final JSONArray jsonArray = jsonObject.getJSONArray("message");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id", jsonObject1.optString("id"));
                            map.put("country_id", jsonObject1.optString("country_id"));
                            map.put("state", jsonObject1.optString("state"));

                            StateListing.add(jsonObject1.optString("state"));

                            StateData.add(map);

                            ArrayAdapter aa = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, StateListing);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            state.setAdapter(aa);

                        }

                        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                if (!state.getSelectedItem().toString().equalsIgnoreCase("Select State")) {
                                    stateId = StateData.get(i).get("id").toString();
                                    new CityListApi(StateData.get(i).get("id")).execute();
                                }
                                else if (state.getSelectedItem().toString().equalsIgnoreCase("Select State")) {
                                    CityListing.add("Select City");
                                    ArrayAdapter aa = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, CityListing);
                                    aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                                    city.setAdapter(aa);
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });


                    } else {
                        Util.errorDialog(PostAdd.this, jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(PostAdd.this, "Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }


    class Car_Pro_Category extends AsyncTask<String, Void, String> {


        public Car_Pro_Category() {

            dialog = new Dialog(PostAdd.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            // Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> map = new HashMap<>();

//            map.put("typeId", getArguments().getString("Cat_id"));
            map.put("typeId", getIntent().getStringExtra("Cat_id"));
            map.put("userId", MyPrefrences.getUserID(getApplicationContext()));

            JSONParser jsonParser = new JSONParser();
            String result = jsonParser.makeHttpRequest(Api.BuyCategory, "POST", map);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("response", ": " + s);

            //  Util.cancelPgDialog(dialog);
            try {
                final JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        DataList.clear();
                        CatList.clear();
                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("category", "");
                        map2.put("id", "");
                        DataList.add(map2);
                        CatList.add("Select Category");
//
                        Log.d("sdfdsfsdfgsdgsdfgsg",jsonObject.optString("trial"));

                        trial= Integer.parseInt(jsonObject.optString("trial"));


                        Log.d("fsdfsdfgsdgdsdsdssdfgfg", String.valueOf(trial));
                        if (trial>0) {
                            ArrayAdapter Valid = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, ValidFor2);
                            Valid.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            valiFor.setAdapter(Valid);
                        }
                        else{
                            ArrayAdapter Valid = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, ValidFor);
                            Valid.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            valiFor.setAdapter(Valid);
                        }


                        final JSONArray jsonArray = jsonObject.getJSONArray("message");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id", jsonObject1.optString("id"));
                            map.put("cat_type", jsonObject1.optString("cat_type"));
                            map.put("category", jsonObject1.optString("category"));
                            map.put("photo", jsonObject1.optString("photo"));
                            map.put("status", jsonObject1.optString("status"));
                            CatList.add(jsonObject1.optString("category"));

                            DataList.add(map);

                            ArrayAdapter aa = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, CatList);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            category.setAdapter(aa);

                            if (jsonObject1.optString("category").equals(getIntent().getStringExtra("heading"))) {
                                Log.d("fgvfdgsfdgsfdfdfgdgrg", String.valueOf(i + 1));
                                val = i + 1;
                            }
                            category.setSelection(val);

                        }
                    } else {
                        Util.errorDialog(PostAdd.this, jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(PostAdd.this, "Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(PostAdd.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    class CityListApi extends AsyncTask<String, Void, String> {

        String id;

        public CityListApi(String id) {
            this.id = id;
            dialog = new Dialog(PostAdd.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            //Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> map = new HashMap<>();
            Log.d("stateId", id);
            map.put("stateId", id);

            JSONParser jsonParser = new JSONParser();
            String result = jsonParser.makeHttpRequest(Api.getCity, "POST", map);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("response", ": " + s);

            // Util.cancelPgDialog(dialog);
            try {
                final JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {

                        CityData.clear();
                        CityListing.clear();
                        //SubCatList.add("Select");
                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("category", "");
                        CityData.add(map2);
                        CityListing.add("Select City");
                        final JSONArray jsonArray = jsonObject.getJSONArray("message");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id", jsonObject1.optString("id"));
                            map.put("country_id", jsonObject1.optString("country_id"));
                            map.put("state_id", jsonObject1.optString("state_id"));
                            map.put("city", jsonObject1.optString("city"));
                            map.put("std_code", jsonObject1.optString("std_code"));
                            CityListing.add(jsonObject1.optString("city"));

                            CityData.add(map);

                            ArrayAdapter aa = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, CityListing);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            city.setAdapter(aa);
                        }

                    } else {
                        Util.errorDialog(PostAdd.this, jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(PostAdd.this, "Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }


    class SubCategoryApi extends AsyncTask<String, Void, String> {

        String id;

        public SubCategoryApi(String id) {
            this.id = id;
            dialog = new Dialog(PostAdd.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            //Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> map = new HashMap<>();
//            Log.d("fgfdgdfhgghdfhg",id);
            map.put("categoryId", id);

            JSONParser jsonParser = new JSONParser();
            String result = jsonParser.makeHttpRequest(Api.subcategorybyCat, "POST", map);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("response", ": " + s);

            // Util.cancelPgDialog(dialog);
            try {
                final JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {

                        new DrpDownData().execute();

                        DataListSubCat.clear();
                        SubCatList.clear();

                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("id", "");
                        DataListSubCat.add(map2);
                        SubCatList.add("Select Sub Category");

                        final JSONArray jsonArray = jsonObject.getJSONArray("message");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id", jsonObject1.optString("id"));
                            map.put("subcategory", jsonObject1.optString("subcategory"));
                            SubCatList.add(jsonObject1.optString("subcategory"));

                            DataListSubCat.add(map);

                            ArrayAdapter aa = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, SubCatList);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            subCategory.setAdapter(aa);
                        }


                    } else {
                        Util.errorDialog(PostAdd.this, jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(PostAdd.this, "Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }

    class DrpDownData extends AsyncTask<String, Void, String> {


        public DrpDownData() {

            dialog = new Dialog(PostAdd.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            try {
                Util.showPgDialog(dialog);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> map = new HashMap<>();

            //map.put("typeId", getArguments().getString("Cat_id"));

            JSONParser jsonParser = new JSONParser();
            String result = jsonParser.makeHttpRequest(Api.allHiddenData, "POST", map);

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

                        //final JSONArray jsonArray=jsonObject.getJSONArray("message");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        final JSONArray jsonArray = jsonObject1.getJSONArray("propertType");
                        PropertyList.clear();
                        PropertyType.clear();

                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("type", "");
                        PropertyType.add(map2);
                        PropertyList.add("Type");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id", jsonObject2.optString("id"));
                            map.put("propertytype", jsonObject2.optString("propertytype"));
                            PropertyList.add(jsonObject2.optString("propertytype"));

                            PropertyType.add(map);

                            ArrayAdapter aa = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, PropertyList);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            typeSpiner.setAdapter(aa);

                        }


                        final JSONArray jsonArray2 = jsonObject1.getJSONArray("bedrooms");
                        BedroomList.clear();
                        BedroomData.clear();

                        HashMap<String, String> map3 = new HashMap<>();
                        map3.put("category", "");
                        BedroomData.add(map3);
                        BedroomList.add("Bedrooms");

                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject jsonObject2 = jsonArray2.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id", jsonObject2.optString("id"));
                            map.put("bedroomstype", jsonObject2.optString("bedroomstype"));
                            BedroomList.add(jsonObject2.optString("bedroomstype"));

                            BedroomData.add(map);

                            ArrayAdapter aa = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, BedroomList);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            badroomSpiner.setAdapter(aa);

                        }

                        final JSONArray jsonArray3 = jsonObject1.getJSONArray("manufacturer");
                        ManufectData.clear();
                        ManufectList.clear();
                        ManufectList.add("Manufacturer");
                        for (int i = 0; i < jsonArray3.length(); i++) {
                            JSONObject jsonObject2 = jsonArray3.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id", jsonObject2.optString("id"));
                            map.put("cat_id", jsonObject2.optString("cat_id"));
                            map.put("subcat_id", jsonObject2.optString("subcat_id"));
                            map.put("manufacturer", jsonObject2.optString("manufacturer"));
                            ManufectList.add(jsonObject2.optString("manufacturer"));

                            ManufectData.add(map);

                            ArrayAdapter aa = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, ManufectList);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            manufectureSpiner.setAdapter(aa);

                        }


                        final JSONArray jsonArray4 = jsonObject1.getJSONArray("fueltype");
                        FuelTypeList.clear();
                        FuelTypeData.clear();
                        HashMap<String, String> map4 = new HashMap<>();
                        map4.put("", "");
                        FuelTypeData.add(map4);
                        FuelTypeList.add("Fuel Type");

                        for (int i = 0; i < jsonArray4.length(); i++) {
                            JSONObject jsonObject2 = jsonArray4.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id", jsonObject2.optString("id"));
                            map.put("fueltype", jsonObject2.optString("fueltype"));
                            FuelTypeList.add(jsonObject2.optString("fueltype"));

                            FuelTypeData.add(map);

                            ArrayAdapter aa = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, FuelTypeList);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            fuelTypeSpiner.setAdapter(aa);

                        }

                    } else {
                        Util.errorDialog(PostAdd.this, jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(PostAdd.this, "Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2)
                onSelectFromGalleryResult(data, 2);
            else if (requestCode == 4)
                onSelectFromGalleryResult(data, 4);
            else if (requestCode == 6)
                onSelectFromGalleryResult(data, 6);
            else if (requestCode == 8)
                onSelectFromGalleryResult(data, 8);
            else if (requestCode == 10)
                onSelectFromGalleryResult(data, 10);
            else if (requestCode == 1)
                onSelectFromGalleryResult(data, 1);
            else if (requestCode == 3)
                onSelectFromGalleryResult(data, 3);
            else if (requestCode == 5)
                onSelectFromGalleryResult(data, 5);
            else if (requestCode == 7)
                onSelectFromGalleryResult(data, 7);
            else if (requestCode == 9)
                onSelectFromGalleryResult(data, 9);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onSelectFromGalleryResult(Intent data, int i) {

        if (i == 2) {
            Uri fileUri = data.getData();
            filepath1 = getPathFromUri(getApplicationContext(), fileUri);
            fileName1 = imagename(getApplicationContext(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), fileUri);
                image1.setImageBitmap(bitmap);
                image1.setVisibility(View.VISIBLE);
                img1.setVisibility(View.GONE);

            } catch (IOException e) {
                e.printStackTrace();
            }
            check = 1;
            isImage1 = true;
            isImage2 = false;
            isImage3 =false;
            isImage4 = false;
            isImage5 = false;

        } else if (i == 4) {
            Uri fileUri = data.getData();
            filepath2 = getPathFromUri(getApplicationContext(), fileUri);
            fileName2 = imagename(getApplicationContext(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), fileUri);
                image2.setImageBitmap(bitmap);
                image2.setVisibility(View.VISIBLE);
                img2.setVisibility(View.GONE);

            } catch (IOException e) {
                e.printStackTrace();
            }
            isImage2 = true;
            isImage1 = false;
            isImage3 =false;
            isImage4 = false;
            isImage5 = false;
        } else if (i == 6) {
            Uri fileUri = data.getData();
            filepath3 = getPathFromUri(getApplicationContext(), fileUri);
            fileName3 = imagename(getApplicationContext(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), fileUri);
                image3.setImageBitmap(bitmap);
                image3.setVisibility(View.VISIBLE);
                img3.setVisibility(View.GONE);

            } catch (IOException e) {
                e.printStackTrace();
            }
            isImage3 = true;
            isImage2 = false;
            isImage1 =false;
            isImage4 = false;
            isImage5 = false;
        } else if (i == 8) {
            Uri fileUri = data.getData();
            filepath4 = getPathFromUri(getApplicationContext(), fileUri);
            fileName4 = imagename(getApplicationContext(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), fileUri);
                image4.setImageBitmap(bitmap);
                image4.setVisibility(View.VISIBLE);
                img4.setVisibility(View.GONE);

            } catch (IOException e) {
                e.printStackTrace();
            }

            isImage4 = true;
            isImage2 = false;
            isImage3 =false;
            isImage1 = false;
            isImage5 = false;
        } else if (i == 10) {
            Uri fileUri = data.getData();
            filepath5 = getPathFromUri(getApplicationContext(), fileUri);
            fileName5 = imagename(getApplicationContext(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), fileUri);
                image5.setImageBitmap(bitmap);
                image5.setVisibility(View.VISIBLE);
                img5.setVisibility(View.GONE);

            } catch (IOException e) {
                e.printStackTrace();
            }
            isImage5 = true;
            isImage2 = false;
            isImage3 =false;
            isImage4 = false;
            isImage1 = false;
        } else if (i == 1) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image1.setImageBitmap(photo);
            image1.setVisibility(View.VISIBLE);
            img1.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            filepath1 = finalFile.toString();

            String filename1 = filepath1.substring(filepath1.lastIndexOf("/") + 1);
            fileName1 = filename1;
            Log.d("dgdfgdfhgdfhd", filepath1.toString());
            Log.d("dgdfgdfhgdfhd", filename1.toString());
//                 System.out.println(mImageCaptureUri);
            check = 1;
            isImage1 = true;
            isImage2 = false;
            isImage3 =false;
            isImage4 = false;
            isImage5 = false;
        }

//            bm = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//
//            File destination = new File(Environment.getExternalStorageDirectory(),
//                    System.currentTimeMillis() + ".jpg");
//
//            FileOutputStream fo;
//            try {
//                destination.createNewFile();
//                fo = new FileOutputStream(destination);
//                fo.write(bytes.toByteArray());
//                fo.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            image1.setBackgroundColor(Color.WHITE);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 230);
//            image1.setLayoutParams(layoutParams);
//            image1.setImageBitmap(bm);
//            try {
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] imageBytes = baos.toByteArray();
//
//                //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            image1.setVisibility(View.VISIBLE);
//            img1.setVisibility(View.GONE);
//            check=1;


        else if (i == 3) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image2.setImageBitmap(photo);
            image2.setVisibility(View.VISIBLE);
            img2.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            filepath2 = finalFile.toString();

            String filename2 = filepath2.substring(filepath2.lastIndexOf("/") + 1);
            fileName2 = filename2;
            Log.d("dgdfgdfhgdfhd", filepath2.toString());
            Log.d("dgdfgdfhgdfhd", filename2.toString());
            isImage2 = true;
            isImage1 = false;
            isImage3 =false;
            isImage4 = false;
            isImage5 = false;
        } else if (i == 5) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image3.setImageBitmap(photo);
            image3.setVisibility(View.VISIBLE);
            img3.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            filepath3 = finalFile.toString();

            String filename3 = filepath3.substring(filepath3.lastIndexOf("/") + 1);
            fileName3 = filename3;
            Log.d("dgdfgdfhgdfhd", filepath3.toString());
            Log.d("dgdfgdfhgdfhd", filename3.toString());
            isImage3 = true;
            isImage1 = false;
            isImage2 =false;
            isImage4 = false;
            isImage5 = false;
        } else if (i == 7) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image4.setImageBitmap(photo);
            image4.setVisibility(View.VISIBLE);
            img4.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            filepath4 = finalFile.toString();

            String filename4 = filepath4.substring(filepath4.lastIndexOf("/") + 1);
            fileName4 = filename4;
            Log.d("dgdfgdfhgdfhd", filepath4.toString());
            Log.d("dgdfgdfhgdfhd", filename4.toString());
            isImage4 = true;
            isImage1 = false;
            isImage3 =false;
            isImage2 = false;
            isImage5 = false;
        } else if (i == 9) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image5.setImageBitmap(photo);
            image5.setVisibility(View.VISIBLE);
            img5.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            filepath5 = finalFile.toString();

            String filename5 = filepath5.substring(filepath5.lastIndexOf("/") + 1);
            fileName5 = filename5;
            Log.d("dgdfgdfhgdfhd", filepath5.toString());
            Log.d("dgdfgdfhgdfhd", filename5.toString());
            isImage5 = true;
        }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPathFromUri(final Context context, final Uri uri) {
        boolean isAfterKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        ///storage/emulated/0/Download/Amit-1.pdf
        Log.e("Uri Authority ", "uri:" + uri.getAuthority());
        if (isAfterKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if ("com.android.externalstorage.documents".equals(
                    uri.getAuthority())) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else {
                    return "/stroage/" + type + "/" + split[1];
                }
            } else if ("com.android.providers.downloads.documents".equals(
                    uri.getAuthority())) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if ("com.android.providers.media.documents".equals(
                    uri.getAuthority())) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                contentUri = MediaStore.Files.getContentUri("external");
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {//MediaStore
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String[] projection = {
                MediaStore.Files.FileColumns.DATA
        };
        try {
            cursor = context.getContentResolver().query(
                    uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int cindex = cursor.getColumnIndexOrThrow(projection[0]);
                return cursor.getString(cindex);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static String imagename(Context context, Uri currImageURI) {
        String displayName = "";
        File file = new File(currImageURI.toString());
        String uriString = currImageURI.toString();
        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(currImageURI, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    Log.e("display name content", ": " + displayName);
                }
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = file.getName();
            Log.e("display name file", ": " + displayName);
        }
        Log.e("display name ", ": " + displayName);
        return displayName;
    }


    private class CarDataSubmit extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private static final String TAG_STATUS = "status";
        private static final String TAG_MESSAGE = "msg";

        String descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand;
        HashMap<String, String> params = new HashMap<>();

        //EditText descreption,ageOfProd,headline,min,kmsDone,mobile,emailID;
        CarDataSubmit(String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {
            this.descreption = descreption;
            this.ageOfProd = ageOfProd;
            this.headline = headline;
            this.min = min;
            this.kmsDone = kmsDone;
            this.mobile = mobile;
            this.emailID = emailID;
            this.brand = brand;

        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(PostAdd.this);
            progress.setCancelable(false);
            progress.setTitle("Please wait...");
            progress.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject jsonObject = null;
            try {
                if (isImage1 == true) {
                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage2 == true) {
                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1, filepath2, fileName2,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage3 == true) {
                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage4 == true) {
                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage5 == true) {
                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4, filepath5, fileName5,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }

                if (jsonObject != null) {

                    return jsonObject;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject json) {
            String message = "";
            String data = "";

            if (progress.isShowing())
                progress.dismiss();

            if (json != null) {


                if (json.optString("status").equalsIgnoreCase("success")) {
                    try {

                        Log.d("fgdgdfghdfhdf", "car");
                        double amnt = Double.parseDouble(amounts.getText().toString().replace("₹ ", ""));
                        MyPrefrences.setPostingId(getApplicationContext(), json.getString("paymentId"));
                        MyPrefrences.setPostingId2(getApplicationContext(), json.getString("posting_id"));
                        Log.d("rupee", String.valueOf(amnt));
                        if (amnt > 0) {
                            paymentGatway(amnt, "car");
                        } else {
                            new PostingID("1").execute();
                            // Util.errorDialog(PostAdd.this, json.getString("message"));
                            errorDialog2(PostAdd.this, json.getString("message"), json.getString("posting_id"));
                        }
                        //Toast.makeText(PostAdd.this, ""+jsonObject.optString("message") + message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
//                    Toast.makeText(PostAdd.this, "Error " + json, Toast.LENGTH_LONG).show();
                    Util.errorDialog(PostAdd.this, json.optString("message"));
                }
            }
        }

    }

    private void errorDialog2(PostAdd postAdd, String message, final String pID) {
        Button Yes_action, No_action;
        TextView heading;
        final EditText comment;
        dialog4 = new Dialog(PostAdd.this);
        dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog4.setContentView(R.layout.alertdialogcustom_feedback_form);

        Button ok = (Button) dialog4.findViewById(R.id.btn_ok);
        Button cancel = (Button) dialog4.findViewById(R.id.cancel);

        heading = (TextView) dialog4.findViewById(R.id.msg_txv);

        heading.setText("Your advertisement has been posted successfully.\n" +
                "It will be visible to your targeted customers after the admin approval i.e. within 48 working hours.\n" +
                "Your Posting ID is : #" + pID);

        spiner1 = (Spinner) dialog4.findViewById(R.id.spiner1);
        spiner2 = (Spinner) dialog4.findViewById(R.id.spiner2);
        comment = (EditText) dialog4.findViewById(R.id.comment);

        ArrayAdapter aa1 = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, spi1);
        aa1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spiner1.setAdapter(aa1);

        ArrayAdapter aa2 = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, spi2);
        aa2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spiner2.setAdapter(aa2);


        spiner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==1){
                    comment.setVisibility(View.VISIBLE);
                }
                else if (i==2){
                    comment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent=new Intent(PostAdd.this,MainActivity.class);
//                intent.putExtra("isflag","1");
//                startActivity(intent);

                if (!spiner1.getSelectedItem().toString().equalsIgnoreCase("Select")) {

                    if (!spiner2.getSelectedItem().toString().equalsIgnoreCase("Select")) {

                        if (spiner2.getSelectedItem().toString().equalsIgnoreCase("No")){
                            new FeedbackApi(pID,spiner1.getSelectedItem().toString(),spiner2.getSelectedItem().toString(),"&nbsp;").execute();

                        }
                        else if (spiner2.getSelectedItem().toString().equalsIgnoreCase("Yes")) {
                            if (!comment.getText().toString().equalsIgnoreCase("")) {


                                new FeedbackApi(pID, spiner1.getSelectedItem().toString(), spiner2.getSelectedItem().toString(), comment.getText().toString()).execute();
                                // Toast.makeText(PostAdd.this, "yes", Toast.LENGTH_SHORT).show();


                            } else {
                                Util.errorDialog(PostAdd.this, "Enter Comment");
                            }
                        }
                    } else {
                        Util.errorDialog(PostAdd.this, "Please select technical difficulties");
                    }
                } else {
                    Util.errorDialog(PostAdd.this, "Please select experience");
                }



            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog4.dismiss();
                Intent intent = new Intent(PostAdd.this, MainActivity.class);
                intent.putExtra("isflag", "1");
                startActivity(intent);

            }
        });

        dialog4.show();
    }

    private void feedbackForm() {

        final Dialog dialog = new Dialog(PostAdd.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialogcustom_feedback_form);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView text = (TextView) dialog.findViewById(R.id.msg_txv);
        text.setText(fromHtml("Share your Feedback"));
        Button ok = (Button) dialog.findViewById(R.id.btn_ok);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        Spinner spiner1 = (Spinner) dialog.findViewById(R.id.spiner1);
        Spinner spiner2 = (Spinner) dialog.findViewById(R.id.spiner2);

        ArrayAdapter aa1 = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, spi1);
        aa1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spiner1.setAdapter(aa1);

        ArrayAdapter aa2 = new ArrayAdapter(PostAdd.this, R.layout.simple_spinner_item, spi1);
        aa2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spiner2.setAdapter(aa2);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
//				Intent intent=new Intent(PaymentSuccessActivity.this,MainActivity.class);
//				intent.putExtra("isflag","1");
//				startActivity(intent);

            }
        });

        dialog.show();
    }

    private void paymentGatway(double amount, final String typeP) {
//        Button Yes_action, No_action;
//        TextView heading;
//        dialog4 = new Dialog(PostAdd.this);
//        dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog4.setContentView(R.layout.payment_confirm);
//
//        Yes_action = (Button) dialog4.findViewById(R.id.Yes_action);
//
//        heading = (TextView) dialog4.findViewById(R.id.heading);
//
//
//        heading.setText("Continue to Make Payment ₹ " + amount);
//        Yes_action.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                callEbsKit(PostAdd.this, typeP);
//
//                dialog4.dismiss();
//            }
//        });
//
//
//        dialog4.show();


        new RequirementApi(typeP,amount).execute();


//        callEbsKit(PostAdd.this, typeP,amount);

    }

    class RequirementApi extends AsyncTask<String,Void,String>{

        String emailid,password;
        String type;
        double amount;
        public RequirementApi(String type, double amount){

            this.emailid=emailid;
            this.password=password;
            this.type=type;
            this.amount=amount;
            dialog=new Dialog(PostAdd.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            Random r = new Random();
            i1 = r.nextInt(800 - 650) + 65;
            Log.d("fgsdgfsdghdfhd", String.valueOf(i1));



            map.put("amount", String.valueOf(amount));
            map.put("account_id", String.valueOf(ACC_ID));
            map.put("reference_no",String.valueOf(i1) );
            map.put("email", "customerhelpdesk@pinerria.com");
            map.put("currency", "INR");


            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.generateHashKey,"POST",map);

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

                        callEbsKit(PostAdd.this, type,amount);
                        //Util.errorDialog(PostAdd.this,"success");

                    }
                    else {
                        Util.errorDialog(PostAdd.this,"Not success");
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(PostAdd.this,"Some Error! or Please connect to the internet.");
                e.printStackTrace();
            }
        }

    }


    private void callEbsKit(PostAdd buyProduct, String TypeP,double amount) {
        /**
         * Set Parameters Before Initializing the EBS Gateway, All mandatory
         * values must be provided
         */

        /** Payment Amount Details */
        // Total Amount

        PaymentRequest.getInstance().setTransactionAmount(String.valueOf(amount));

        /** Mandatory */

        PaymentRequest.getInstance().setAccountId(ACC_ID);
        PaymentRequest.getInstance().setSecureKey(SECRET_KEY);

        // Reference No
//        Random r = new Random();
//        int i1 = r.nextInt(800 - 650) + 65;
//        Log.d("fgsdgfsdghdfhd", String.valueOf(i1));

        PaymentRequest.getInstance().setReferenceNo(String.valueOf(i1));
        /** Mandatory */

        // Email Id
        //PaymentRequest.getInstance().setBillingEmail("test_tag@testmail.com");

        PaymentRequest.getInstance().setBillingEmail("customerhelpdesk@pinerria.com");
        /** Mandatory */

        PaymentRequest.getInstance().setFailureid(String.valueOf(amount));

        // PaymentRequest.getInstance().setFailuremessage(getResources().getString(R.string.payment_failure_message));
        // System.out.println("FAILURE MESSAGE"+getResources().getString(R.string.payment_failure_message));

        /** Mandatory */

        // Currency
        PaymentRequest.getInstance().setCurrency("INR");
        /** Mandatory */

        /** Optional */
        // Your Reference No or Order Id for this transaction
        PaymentRequest.getInstance().setTransactionDescription(
                "Test Transaction");

        /** Billing Details */
        PaymentRequest.getInstance().setBillingName(TypeP);
        /** Optional */
        PaymentRequest.getInstance().setBillingAddress("North Mada Street");
        /** Optional */
        PaymentRequest.getInstance().setBillingCity("Chennai");
        /** Optional */
        PaymentRequest.getInstance().setBillingPostalCode("600019");
        /** Optional */
        PaymentRequest.getInstance().setBillingState("Tamilnadu");
        /** Optional */
        PaymentRequest.getInstance().setBillingCountry("IND");
        /** Optional */
        PaymentRequest.getInstance().setBillingPhone("01234567890");
        /** Optional */

        /** Shipping Details */
        PaymentRequest.getInstance().setShippingName("Test_Name");
        /** Optional */
        PaymentRequest.getInstance().setShippingAddress("North Mada Street");
        /** Optional */
        PaymentRequest.getInstance().setShippingCity("Chennai");
        /** Optional */
        PaymentRequest.getInstance().setShippingPostalCode("600019");
        /** Optional */
        PaymentRequest.getInstance().setShippingState("Tamilnadu");
        /** Optional */
        PaymentRequest.getInstance().setShippingCountry("IND");
        /** Optional */
        PaymentRequest.getInstance().setShippingEmail("test@testmail.com");
        /** Optional */
        PaymentRequest.getInstance().setShippingPhone("01234567890");
        /** Optional */

        PaymentRequest.getInstance().setLogEnabled(String.valueOf(amount));


        /**
         * Payment option configuration
         */

        /** Optional */
        PaymentRequest.getInstance().setHidePaymentOption(false);

        /** Optional */
        PaymentRequest.getInstance().setHideCashCardOption(false);

        /** Optional */
        PaymentRequest.getInstance().setHideCreditCardOption(false);

        /** Optional */
        PaymentRequest.getInstance().setHideDebitCardOption(false);

        /** Optional */
        PaymentRequest.getInstance().setHideNetBankingOption(false);

        /** Optional */
        PaymentRequest.getInstance().setHideStoredCardOption(false);

        /**
         * Initialise parameters for dyanmic values sending from merchant
         */

        custom_post_parameters = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hashpostvalues = new HashMap<String, String>();
        hashpostvalues.put("account_details", "saving");
        hashpostvalues.put("merchant_type", "gold");
        custom_post_parameters.add(hashpostvalues);

        PaymentRequest.getInstance()
                .setCustomPostValues(custom_post_parameters);
        /** Optional-Set dyanamic values */

        // PaymentRequest.getInstance().setFailuremessage(getResources().getString(R.string.payment_failure_message));

        EBSPayment.getInstance().init(PostAdd.this, ACC_ID, SECRET_KEY,
                Config.Mode.ENV_LIVE, Config.Encryption.ALGORITHM_SHA512, "EBS");

    }

    private class PropDataSubmit extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private static final String TAG_STATUS = "status";
        private static final String TAG_MESSAGE = "msg";

        String descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand;
        HashMap<String, String> params = new HashMap<>();

        //EditText descreption,ageOfProd,headline,min,kmsDone,mobile,emailID;
        PropDataSubmit(String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {
            this.descreption = descreption;
            this.ageOfProd = ageOfProd;
            this.headline = headline;
            this.min = min;
            this.kmsDone = kmsDone;
            this.mobile = mobile;
            this.emailID = emailID;
            this.brand = brand;
        }


        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(PostAdd.this);
            progress.setCancelable(false);
            progress.setTitle("Please wait...");
            progress.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

//                JSONObject jsonObject = uploadImage2(PostAdd.this, filepath1, fileName1,filepath2,fileName2,filepath3,fileName3,filepath4,fileName4,filepath5,fileName5,
//                        descreption,ageOfProd,headline,min,kmsDone,mobile,emailID,brand);

                JSONObject jsonObject = null;

                if (isImage1 == true) {
                    jsonObject = uploadImage2(PostAdd.this, filepath1, fileName1,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage2 == true) {
                    jsonObject = uploadImage2(PostAdd.this, filepath1, fileName1, filepath2, fileName2,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage3 == true) {
                    jsonObject = uploadImage2(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage4 == true) {
                    jsonObject = uploadImage2(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage5 == true) {
                    jsonObject = uploadImage2(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4, filepath5, fileName5,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }

                if (jsonObject != null) {

                    return jsonObject;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject json) {
            String message = "";
            String data = "";

            if (progress.isShowing())
                progress.dismiss();

            if (json != null) {


                if (json.optString("status").equalsIgnoreCase("success")) {
                    try {
//                        double amnt= Double.parseDouble(amounts.getText().toString().replace("₹ ", "").replace("₹ ",""));
//                        Log.d("rupee", String.valueOf(amnt));
//


                        Log.d("fgdgdfghdfhdf", "prpperty");
                        double amnt = Double.parseDouble(amounts.getText().toString().replace("₹ ", ""));
                        MyPrefrences.setPostingId(getApplicationContext(), json.getString("paymentId"));
                        MyPrefrences.setPostingId2(getApplicationContext(), json.getString("posting_id"));
                        Log.d("rupee", String.valueOf(amnt));
                        if (amnt > 0) {
                            paymentGatway(amnt, "property");
                        } else {
//                            Util.errorDialog(PostAdd.this, json.getString("message"));
                            new PostingID("1").execute();
                            errorDialog2(PostAdd.this, json.getString("message"), json.getString("posting_id"));
                        }
                        //Toast.makeText(PostAdd.this, ""+jsonObject.optString("message") + message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
//                    Toast.makeText(PostAdd.this, "Error " + json, Toast.LENGTH_SHORT).show();
                    Util.errorDialog(PostAdd.this, json.optString("message"));
                }
            }
        }

    }

    private class OtherThenDataSubmit extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private static final String TAG_STATUS = "status";
        private static final String TAG_MESSAGE = "msg";

        String descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand;
        HashMap<String, String> params = new HashMap<>();

        //EditText descreption,ageOfProd,headline,min,kmsDone,mobile,emailID;
        OtherThenDataSubmit(String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {
            this.descreption = descreption;
            this.ageOfProd = ageOfProd;
            this.headline = headline;
            this.min = min;
            this.kmsDone = kmsDone;
            this.mobile = mobile;
            this.emailID = emailID;
            this.brand = brand;

        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(PostAdd.this);
            progress.setCancelable(false);
            progress.setTitle("Please wait...");
            progress.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

//                JSONObject jsonObject = uploadImage2(PostAdd.this, filepath1, fileName1,filepath2,fileName2,filepath3,fileName3,filepath4,fileName4,filepath5,fileName5,
//                        descreption,ageOfProd,headline,min,kmsDone,mobile,emailID,brand);

                JSONObject jsonObject = null;

                if (isImage1 == true) {
                    jsonObject = uploadImage3(PostAdd.this, filepath1, fileName1,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage2 == true) {
                    jsonObject = uploadImage3(PostAdd.this, filepath1, fileName1, filepath2, fileName2,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage3 == true) {
                    jsonObject = uploadImage3(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage4 == true) {
                    jsonObject = uploadImage3(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage5 == true) {
                    jsonObject = uploadImage3(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4, filepath5, fileName5,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }

                if (jsonObject != null) {

                    return jsonObject;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject json) {
            String message = "";
            String data = "";

            if (progress.isShowing())
                progress.dismiss();

            if (json != null) {


                if (json.optString("status").equalsIgnoreCase("success")) {
                    try {


                        Log.d("fgdgdfghdfhdf", "other");
                        double amnt = Double.parseDouble(amounts.getText().toString().replace("₹ ", ""));
                        MyPrefrences.setPostingId(getApplicationContext(), json.getString("paymentId"));
                        MyPrefrences.setPostingId2(getApplicationContext(), json.getString("posting_id"));
                        Log.d("rupee", String.valueOf(amnt));
                        if (amnt > 0) {
                            paymentGatway(amnt, "other");
                        } else {
//                            Util.errorDialog(PostAdd.this, json.getString("message"));
                            new PostingID("1").execute();
                            errorDialog2(PostAdd.this, json.getString("message"), json.getString("posting_id"));
                        }

                        //Toast.makeText(PostAdd.this, ""+jsonObject.optString("message") + message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
//                    Toast.makeText(PostAdd.this, "Error " + json, Toast.LENGTH_SHORT).show();
                    Util.errorDialog(PostAdd.this, json.optString("message"));
                }
            }
        }

    }


    private class HomeBusDataSubmit extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private static final String TAG_STATUS = "status";
        private static final String TAG_MESSAGE = "msg";

        String descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand;
        HashMap<String, String> params = new HashMap<>();

        //EditText descreption,ageOfProd,headline,min,kmsDone,mobile,emailID;
        HomeBusDataSubmit(String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {
            this.descreption = descreption;
            this.ageOfProd = ageOfProd;
            this.headline = headline;
            this.min = min;
            this.kmsDone = kmsDone;
            this.mobile = mobile;
            this.emailID = emailID;
            this.brand = brand;

        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(PostAdd.this);
            progress.setCancelable(false);
            progress.setTitle("Please wait...");
            progress.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

//                JSONObject jsonObject = uploadImage2(PostAdd.this, filepath1, fileName1,filepath2,fileName2,filepath3,fileName3,filepath4,fileName4,filepath5,fileName5,
//                        descreption,ageOfProd,headline,min,kmsDone,mobile,emailID,brand);

                JSONObject jsonObject = null;

                if (isImage1 == true) {
                    jsonObject = uploadImage4(PostAdd.this, filepath1, fileName1,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage2 == true) {
                    jsonObject = uploadImage4(PostAdd.this, filepath1, fileName1, filepath2, fileName2,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage3 == true) {
                    jsonObject = uploadImage4(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage4 == true) {
                    jsonObject = uploadImage4(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }
                if (isImage5 == true) {
                    jsonObject = uploadImage4(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4, filepath5, fileName5,
                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
                }

                if (jsonObject != null) {

                    return jsonObject;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject json) {
            String message = "";
            String data = "";

            if (progress.isShowing())
                progress.dismiss();

            if (json != null) {


                if (json.optString("status").equalsIgnoreCase("success")) {
                    try {

                        Log.d("fgdgdfghdfhdf", "homeB");
                        double amnt = Double.parseDouble(amounts.getText().toString().replace("₹ ", ""));
                        MyPrefrences.setPostingId(getApplicationContext(), json.getString("paymentId"));
                        MyPrefrences.setPostingId2(getApplicationContext(), json.getString("posting_id"));
                        Log.d("rupee", String.valueOf(amnt));
                        if (amnt > 0) {
                            paymentGatway(amnt, "homeB");
                        } else {

//                            Util.errorDialog(PostAdd.this, json.getString("message"));
                            new PostingID("1").execute();
                            errorDialog2(PostAdd.this, json.getString("message"), json.getString("posting_id"));

//                            Intent intent= new Intent(PostAdd.this,MainActivity.class);
//                            startActivity(intent);

                        }

                        //Toast.makeText(PostAdd.this, ""+jsonObject.optString("message") + message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
//                    Toast.makeText(PostAdd.this, "Error " + json, Toast.LENGTH_SHORT).show();
                    Util.errorDialog(PostAdd.this, json.optString("message"));

                }
            }
        }

    }


    private JSONObject uploadImage(Context context, String filepath1, String fileName1,
                                   String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("manufacturer_id", manufId)
                    .addFormDataPart("brand_id", brand)
                    .addFormDataPart("mileage", kmsDone)
                    .addFormDataPart("fueltype_id", fuelId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))


                    .build();

            Log.d("fvfgdgdfhgghfhgdfh", amounts.getText().toString().replace("₹ ", ""));
            Log.d("fvfgdgdfhgdfhqwdfs",amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/carPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

    private JSONObject uploadImage(Context context, String filepath1, String fileName1, String filepath2, String fileName2,
                                   String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("manufacturer_id", manufId)
                    .addFormDataPart("brand_id", brand)
                    .addFormDataPart("mileage", kmsDone)
                    .addFormDataPart("fueltype_id", fuelId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))

                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/carPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }


    private JSONObject uploadImage(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3,
                                   String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);
        File sourceFile3 = new File(filepath3);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG3 = filepath3.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("manufacturer_id", manufId)
                    .addFormDataPart("brand_id", brand)
                    .addFormDataPart("mileage", kmsDone)
                    .addFormDataPart("fueltype_id", fuelId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/carPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }


    private JSONObject uploadImage(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3, String filepath4, String fileName4,
                                   String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);
        File sourceFile3 = new File(filepath3);
        File sourceFile4 = new File(filepath4);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG3 = filepath3.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG4 = filepath4.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("manufacturer_id", manufId)
                    .addFormDataPart("brand_id", brand)
                    .addFormDataPart("mileage", kmsDone)
                    .addFormDataPart("fueltype_id", fuelId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .addFormDataPart("image4", fileName4, RequestBody.create(MEDIA_TYPE_PNG4, sourceFile4))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/carPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

    private JSONObject uploadImage(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3, String filepath4, String fileName4, String filepath5, String fileName5,
                                   String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);
        File sourceFile3 = new File(filepath3);
        File sourceFile4 = new File(filepath4);
        File sourceFile5 = new File(filepath5);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG3 = filepath3.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG4 = filepath4.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG5 = filepath5.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("manufacturer_id", manufId)
                    .addFormDataPart("brand_id", brand)
                    .addFormDataPart("mileage", kmsDone)
                    .addFormDataPart("fueltype_id", fuelId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .addFormDataPart("image4", fileName4, RequestBody.create(MEDIA_TYPE_PNG4, sourceFile4))
                    .addFormDataPart("image5", fileName5, RequestBody.create(MEDIA_TYPE_PNG5, sourceFile5))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/carPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }


    private JSONObject uploadImage2(Context context, String filepath1, String fileName1,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)

                    .addFormDataPart("propertytype_id", properId)
                    .addFormDataPart("bedrooms", badroomId)
                    .addFormDataPart("property_state", stateId)
                    .addFormDataPart("property_city", cityId)

                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))


                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/propertyPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

    private JSONObject uploadImage2(Context context, String filepath1, String fileName1, String filepath2, String fileName2,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("propertytype_id", properId)
                    .addFormDataPart("bedrooms", badroomId)
                    .addFormDataPart("property_state", stateId)
                    .addFormDataPart("property_city", cityId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))

                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/propertyPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();

        }
        return jsonObject;
    }


    private JSONObject uploadImage2(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);
        File sourceFile3 = new File(filepath3);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG3 = filepath3.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("propertytype_id", properId)
                    .addFormDataPart("bedrooms", badroomId)
                    .addFormDataPart("property_state", stateId)
                    .addFormDataPart("property_city", cityId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/propertyPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }


    private JSONObject uploadImage2(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3, String filepath4, String fileName4,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);
        File sourceFile3 = new File(filepath3);
        File sourceFile4 = new File(filepath4);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG3 = filepath3.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG4 = filepath4.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("propertytype_id", properId)
                    .addFormDataPart("bedrooms", badroomId)
                    .addFormDataPart("property_state", stateId)
                    .addFormDataPart("property_city", cityId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .addFormDataPart("image4", fileName4, RequestBody.create(MEDIA_TYPE_PNG4, sourceFile4))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/propertyPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

    private JSONObject uploadImage2(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3, String filepath4, String fileName4, String filepath5, String fileName5,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);
        File sourceFile3 = new File(filepath3);
        File sourceFile4 = new File(filepath4);
        File sourceFile5 = new File(filepath5);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG3 = filepath3.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG4 = filepath4.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG5 = filepath5.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("propertytype_id", properId)
                    .addFormDataPart("bedrooms", badroomId)
                    .addFormDataPart("property_state", stateId)
                    .addFormDataPart("property_city", cityId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .addFormDataPart("image4", fileName4, RequestBody.create(MEDIA_TYPE_PNG4, sourceFile4))
                    .addFormDataPart("image5", fileName5, RequestBody.create(MEDIA_TYPE_PNG5, sourceFile5))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/propertyPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();

        }
        return jsonObject;
    }


    private JSONObject uploadImage3(Context context, String filepath1, String fileName1,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            Log.d("fdsgdfghdfhgdfh",validNum);
            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("brand_id", breedId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))


                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/otherthanPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

    private JSONObject uploadImage3(Context context, String filepath1, String fileName1, String filepath2, String fileName2,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("brand_id", breedId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))

                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/otherthanPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }


    private JSONObject uploadImage3(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);
        File sourceFile3 = new File(filepath3);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG3 = filepath3.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("brand_id", breedId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/otherthanPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }


    private JSONObject uploadImage3(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3, String filepath4, String fileName4,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);
        File sourceFile3 = new File(filepath3);
        File sourceFile4 = new File(filepath4);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG3 = filepath3.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG4 = filepath4.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("brand_id", breedId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .addFormDataPart("image4", fileName4, RequestBody.create(MEDIA_TYPE_PNG4, sourceFile4))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/otherthanPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

    private JSONObject uploadImage3(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3, String filepath4, String fileName4, String filepath5, String fileName5,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);
        File sourceFile3 = new File(filepath3);
        File sourceFile4 = new File(filepath4);
        File sourceFile5 = new File(filepath5);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG3 = filepath3.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG4 = filepath4.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG5 = filepath5.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)
                    .addFormDataPart("brand_id", breedId)
                    .addFormDataPart("min_price", min)
                    .addFormDataPart("manufacture_month_yr", monthSpiner.getSelectedItem().toString())
                    .addFormDataPart("manufacture_yr", yearSpiner.getSelectedItem().toString())
                    .addFormDataPart("product_age", ageOfProd)
                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .addFormDataPart("image4", fileName4, RequestBody.create(MEDIA_TYPE_PNG4, sourceFile4))
                    .addFormDataPart("image5", fileName5, RequestBody.create(MEDIA_TYPE_PNG5, sourceFile5))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/otherthanPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }


    private JSONObject uploadImage4(Context context, String filepath1, String fileName1,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)

                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))

                    .build();


            Log.d("fvfgdgdfhgghfhgdfh", amounts.getText().toString().replace("₹ ", ""));
            Log.d("fvfgdgdfhgdfhqwdfs",amounts.getText().toString().replace("₹ ", ""));


            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/hbPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result12345", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

    private JSONObject uploadImage4(Context context, String filepath1, String fileName1, String filepath2, String fileName2,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)

                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))

                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/hbPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }


    private JSONObject uploadImage4(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);
        File sourceFile3 = new File(filepath3);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG3 = filepath3.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)

                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))

                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/hbPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(15, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }


    private JSONObject uploadImage4(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3, String filepath4, String fileName4,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);
        File sourceFile3 = new File(filepath3);
        File sourceFile4 = new File(filepath4);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG3 = filepath3.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG4 = filepath4.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)

                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .addFormDataPart("image4", fileName4, RequestBody.create(MEDIA_TYPE_PNG4, sourceFile4))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/hbPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

    private JSONObject uploadImage4(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3, String filepath4, String fileName4, String filepath5, String fileName5,
                                    String descreption, String ageOfProd, String headline, String min, String kmsDone, String mobile, String emailID, String brand) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);
        File sourceFile2 = new File(filepath2);
        File sourceFile3 = new File(filepath3);
        File sourceFile4 = new File(filepath4);
        File sourceFile5 = new File(filepath5);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG2 = filepath2.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG3 = filepath3.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG4 = filepath4.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");
            final MediaType MEDIA_TYPE_PNG5 = filepath5.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("cat_id", catId.toString())
                    .addFormDataPart("subcat_id", subId)
                    .addFormDataPart("post_title", headline)

                    .addFormDataPart("description", descreption)
                    .addFormDataPart("mobile", mobile)
                    .addFormDataPart("email", emailID)
                    .addFormDataPart("contact_preference", values)
                     .addFormDataPart("duration", validNum)
                    .addFormDataPart("typ", "1")
                    .addFormDataPart("v", getIntent().getStringExtra("areatypenum"))
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("postcity_id", MyPrefrences.getCityId(getApplicationContext()))
                    .addFormDataPart("postarea_id", MyPrefrences.getAreaId(getApplicationContext()))
                    .addFormDataPart("postcompany_id", MyPrefrences.getCompId(getApplicationContext()))
                    .addFormDataPart("agentId", MyPrefrences.getAgentId(getApplicationContext()))
                    .addFormDataPart("postingAmount", amounts.getText().toString().replace("₹ ", ""))
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .addFormDataPart("image4", fileName4, RequestBody.create(MEDIA_TYPE_PNG4, sourceFile4))
                    .addFormDataPart("image5", fileName5, RequestBody.create(MEDIA_TYPE_PNG5, sourceFile5))
                    .build();

            Log.d("fvfgdgdfhgdfh", amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("https://www.pinerria.com/AndroidApi/hbPosting")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }





    class FeedbackApi extends AsyncTask<String, Void, String> {

        String pId, val1,val2,comm;

        public FeedbackApi(String pId,String val1,String val2,String comm) {

            this.pId = pId;
            this.val1 = val1;
            this.val2 = val2;
            this.comm = comm;
            dialog = new Dialog(PostAdd.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> map = new HashMap<>();

            map.put("userId", MyPrefrences.getUserID(getApplicationContext()));
            map.put("postinfId", pId);
            map.put("exp", val1.toString());
            map.put("technical", val2.toString());
            map.put("comment", comm.toString());

            JSONParser jsonParser = new JSONParser();
            String result = jsonParser.makeHttpRequest(Api.feedbackSend, "POST", map);

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

                        Toast.makeText(getApplicationContext(), "Your Feedback Submitted Successfully...", Toast.LENGTH_LONG).show();
                        dialog4.dismiss();
                        Intent intent = new Intent(PostAdd.this, MainActivity.class);
                        intent.putExtra("isflag", "1");
                        startActivity(intent);


                    } else {
                        Util.errorDialog(PostAdd.this, jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(PostAdd.this, "Some Error! or Please connect to the internet.");
                e.printStackTrace();
            }
        }

    }

    class PostingID extends AsyncTask<String,Void,String> {

        String status,password;
        public PostingID(String status){

            this.status=status;
            dialog=new Dialog(PostAdd.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("postingId", MyPrefrences.getPostingId(getApplicationContext()));
            map.put("paymentStatus", "1");
            map.put("getJsonData", "");

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.paymentSuccess,"POST",map);

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
                        //PaymentSuccessActivity.this.finish();


                    }

                }
            } catch (JSONException e) {
                Util.errorDialog(PostAdd.this,"Some Error! or Please connect to the internet.");
                e.printStackTrace();
            }
        }

    }

}


