package application.pinerria.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.pinerria.Activities.MainActivity;
import application.pinerria.Activities.PostAdd;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.AppController;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;
import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyProductListDetail extends Fragment {


    public BuyProductListDetail() {
        // Required empty public constructor
    }

    ViewPager viewPager;
    CustomPagerAdapter  customPagerAdapter;
    List<HashMap<String,String>> ImgData;
    CircleIndicator indicator ;
    TextView contactDetail,emailId,Mobile;
    JSONObject jsonObject;
    LinearLayout emailLiner,mobileLiner;
    TextView statusProduct,title,manufect,milage,fuelType,mDate,ageOfProd,price,lastWeek,visitors,used,descreption,expire;
    Dialog dialog,dialog4;
    RadioButton rb;
    LinearLayout ownLiner,linerlayout1,homeBusinessLay;
    CheckBox sold;
    String intDetails="No";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_buy_product_list_detail, container, false);
        Log.d("fgbhfghfgjhfgj",getArguments().getString("DataList"));
        Log.d("fgbhfghjfghhfgjhfgj",getArguments().getString("type"));
        Log.d("fgbhfghjfghhffdfgjhfgj",getArguments().getString("category"));



        getActivity().setTitle(getArguments().getString("category"));
        MainActivity.linerFilter.setVisibility(View.GONE);

        ImgData =new ArrayList<>();
        viewPager= (ViewPager)view.findViewById(R.id.viewpager);
        indicator = (CircleIndicator)view.findViewById(R.id.indicator);
        customPagerAdapter=new CustomPagerAdapter();

        TextView postId,title,manufect,milage,fuelType,mDate,ageOfProd,price,lastWeek,visitors,used,descreption,expire;

        String Prev_Response=getArguments().getString("DataList");

        postId=view.findViewById(R.id.postId);
        title=view.findViewById(R.id.title);
        manufect=view.findViewById(R.id.manufect);
        milage=view.findViewById(R.id.milage);
        fuelType=view.findViewById(R.id.fuelType);
        mDate=view.findViewById(R.id.mDate);
        ageOfProd=view.findViewById(R.id.ageOfProd);
        price=view.findViewById(R.id.price);
        lastWeek=view.findViewById(R.id.lastWeek);
        visitors=view.findViewById(R.id.visitors);
        used=view.findViewById(R.id.used);
        descreption=view.findViewById(R.id.descreption);
        expire=view.findViewById(R.id.expire);
        contactDetail=view.findViewById(R.id.contactDetail);
        emailId=view.findViewById(R.id.emailId);
        Mobile=view.findViewById(R.id.Mobile);
        ownLiner=view.findViewById(R.id.ownLiner);
        sold=view.findViewById(R.id.sold);
        statusProduct=view.findViewById(R.id.statusProduct);
        linerlayout1=view.findViewById(R.id.linerlayout1);
        homeBusinessLay=view.findViewById(R.id.homeBusinessLay);

        if (MyPrefrences.getBuyCat(getActivity()).equals("3")){
            linerlayout1.setVisibility(View.GONE);
            homeBusinessLay.setVisibility(View.GONE);
        }
        else {
            linerlayout1.setVisibility(View.VISIBLE);
            homeBusinessLay.setVisibility(View.VISIBLE);
        }



        try {
            jsonObject=new JSONObject(Prev_Response);

            JSONArray jsonArray=jsonObject.getJSONArray("finalImage");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                Log.d("gbfhfghfgh",jsonObject1.toString());
                HashMap<String,String> map=new HashMap<>();
                map.put("img",jsonObject1.optString("image"));
                ImgData.add(map);
                viewPager.setAdapter(customPagerAdapter);
                indicator.setViewPager(viewPager);
            }
            postId.setText(jsonObject.optString("pid"));
            title.setText(jsonObject.optString("post_title"));


            String insertDate = jsonObject.optString("expiry_date");
            String[] items2 = insertDate.split("-");
            String d =items2[0];
            String m =items2[1];
            String y =items2[2];

            expire.setText(y+"-"+m+"-"+d );
//            time.setText(m1 );

            ageOfProd.setText(jsonObject.optString("product_age")+" Months");
            price.setText("₹ "+ NumberFormat.getInstance().format(Double.parseDouble(jsonObject.optString("min_price"))));
            lastWeek.setText(jsonObject.optString("search_last_week"));
            visitors.setText(jsonObject.optString("visitor_last_week"));
            used.setText(jsonObject.optString("user_intrested"));
            descreption.setText(jsonObject.optString("description"));
//            expire.setText(jsonObject.optString("expiry_date"));
            emailId.setText("Email Id: "+jsonObject.optString("email"));
            Mobile.setText("Mobile No: "+jsonObject.optString("mobile"));

            emailId.setPaintFlags(emailId.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            Mobile.setPaintFlags(Mobile.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

            Log.d("fgbhfghjfghhffdfgjhfgj",getArguments().getString("countstatus"));
            if (getArguments().getString("countstatus").equalsIgnoreCase("1")){
                new ReadMore(jsonObject.optString("pid")).execute();
            }
//            else if (getArguments().getString("countstatus").equalsIgnoreCase("0")){
//                Toast.makeText(getActivity(), "no", Toast.LENGTH_SHORT).show();
//            }


//
            emailId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Button Yes_action,No_action;
                    TextView heading;
                    dialog4 = new Dialog(getActivity());
                    dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog4.setContentView(R.layout.update_state1);

                    Yes_action=(Button)dialog4.findViewById(R.id.Yes_action);
                    No_action=(Button)dialog4.findViewById(R.id.No_action);
                    heading=(TextView)dialog4.findViewById(R.id.heading);


                    heading.setText("Do you want to Email Now?");
                    Yes_action.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                    "mailto",jsonObject.optString("email"), null));
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Pinerria");
                            intent.putExtra(Intent.EXTRA_TEXT, "");
                            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                            dialog4.dismiss();

                        }
                    });

                    No_action.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog4.dismiss();
                        }
                    });
                    dialog4.show();


                }
            });
            Mobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Button Yes_action,No_action;
                    TextView heading;
                    dialog4 = new Dialog(getActivity());
                    dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog4.setContentView(R.layout.update_state1);

                    Yes_action=(Button)dialog4.findViewById(R.id.Yes_action);
                    No_action=(Button)dialog4.findViewById(R.id.No_action);
                    heading=(TextView)dialog4.findViewById(R.id.heading);


                    heading.setText("Do you want to Call Now?");
                    Yes_action.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog4.dismiss();
                            try
                            {
                                if(Build.VERSION.SDK_INT > 22)
                                {

                                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);
                                        return;
                                    }

                                    Log.d("fsdfgsdgfsdg",jsonObject.optString("mobile"));
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + jsonObject.optString("mobile")));
                                    startActivity(callIntent);
                                }
                                else {

                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + jsonObject.optString("mobile")));
                                    startActivity(callIntent);
                                }
                            }
                            catch (Exception ex)
                            {ex.printStackTrace();
                            }

                        }
                    });

                    No_action.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog4.dismiss();
                        }
                    });
                    dialog4.show();



                }
            });

            if (jsonObject.optString("contact_preference").equalsIgnoreCase("E-mail")){
                Mobile.setVisibility(View.GONE);
                emailId.setVisibility(View.VISIBLE);

            }
            else if (jsonObject.optString("contact_preference").equalsIgnoreCase("Mob")){
                Mobile.setVisibility(View.VISIBLE);
                emailId.setVisibility(View.GONE);
            }
            else{
                Mobile.setVisibility(View.VISIBLE);
                emailId.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        contactDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Util.showInfoAlertDialog(getActivity(), "Contact Details", "sdsfd");
                  DialogContactDetail();
            }
        });


        if (jsonObject.optString("myPost").equalsIgnoreCase("Yes")){
            ownLiner.setVisibility(View.VISIBLE);
            contactDetail.setVisibility(View.GONE);
        }
        else if (jsonObject.optString("myPost").equalsIgnoreCase("No")){
            ownLiner.setVisibility(View.GONE);
            contactDetail.setVisibility(View.VISIBLE);
        }


        if (MyPrefrences.getBuyCat(getActivity()).toString().equalsIgnoreCase("1")){
            sold.setText("Tag Sold");
        }
        else if (MyPrefrences.getBuyCat(getActivity()).toString().equalsIgnoreCase("2")){
            sold.setText("Tag Sold");
        }
        else if (MyPrefrences.getBuyCat(getActivity()).toString().equalsIgnoreCase("3")){
            sold.setText("Stop Display");

        }

        if (jsonObject.optString("status").equalsIgnoreCase("1")){
            statusProduct.setText("Approved");
        }
        else if (jsonObject.optString("status").equalsIgnoreCase("2")){
            statusProduct.setText("Pending");
        }

        if (jsonObject.optString("sold_status").equalsIgnoreCase("1")){
            sold.setChecked(true);
        }
        else if (jsonObject.optString("sold_status").equalsIgnoreCase("0")){
            sold.setChecked(false);
        }


        sold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if (b==true){
                  new TagSoldApi("1").execute();
              }
              else{
                  new TagSoldApi("0").execute();
              }
            }
        });


         if (getArguments().getString("type").equalsIgnoreCase("car")){
             if (!jsonObject.optString("manufacturer_id").equalsIgnoreCase("")){
               manufect.setText(jsonObject.optString("manufacturer_id"));
                 manufect.setVisibility(View.VISIBLE);
             }
             if (!jsonObject.optString("mileage").equalsIgnoreCase("")){
                 milage.setText(jsonObject.optString("mileage"));
                 milage.setVisibility(View.VISIBLE);
             }
             if (!jsonObject.optString("fueltype_id").equalsIgnoreCase("")){
                 fuelType.setText(jsonObject.optString("fueltype_id"));
                 fuelType.setVisibility(View.VISIBLE);
             }
             if (!jsonObject.optString("manufacture_month_yr").equalsIgnoreCase("")){
                 mDate.setText(jsonObject.optString("manufacture_month_yr")+"/"+jsonObject.optString("manufacture_yr")); mDate.setVisibility(View.VISIBLE);

             }
        }

        else {

             if (!jsonObject.optString("manufacture_month_yr").equalsIgnoreCase("")){
                 manufect.setText(jsonObject.optString("manufacture_month_yr")+"/"+jsonObject.optString("manufacture_yr"));
                 manufect.setVisibility(View.VISIBLE);
             }
             if (!jsonObject.optString("propertytype_id").equalsIgnoreCase("")){
                 milage.setText(jsonObject.optString("propertytype_id"));
                 milage.setVisibility(View.VISIBLE);
             }
             if (!jsonObject.optString("bedrooms").equalsIgnoreCase("")){
                 fuelType.setText(jsonObject.optString("bedrooms"));
                 fuelType.setVisibility(View.VISIBLE);
             }
             if (!jsonObject.optString("property_city").equalsIgnoreCase("")){
                 mDate.setText(jsonObject.optString("property_city"));
                 mDate.setVisibility(View.VISIBLE);
             }


        }

        if (getArguments().getString("type").equalsIgnoreCase("home")){
            sold.setText("Stop Display");

        }

        return view;
    }

    private void DialogContactDetail() {

        final Dialog dialog0 = new Dialog(getActivity());
        dialog0.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog0.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog0.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog0.setContentView(R.layout.contact_dialog_layout);
        Button submitEnq = dialog0.findViewById(R.id.submitEnq);
        Button cancel = dialog0.findViewById(R.id.cancel);

        final RadioButton radioButton1=dialog0.findViewById(R.id.radioButton1);
        final RadioButton radioButton2=dialog0.findViewById(R.id.radioButton2);
        final RadioButton radioButton3=dialog0.findViewById(R.id.radioButton3);
        final RadioButton radioButton11=dialog0.findViewById(R.id.radioButton11);
        final RadioButton radioButton12=dialog0.findViewById(R.id.radioButton12);
        final LinearLayout linerlayout=dialog0.findViewById(R.id.linerlayout);
        final RadioGroup radioGroup;

        radioGroup = (RadioGroup) dialog0.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {

                }
            }
        });

        radioButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intDetails="Yes";
            }
        });
        radioButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intDetails="No";
            }
        });

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linerlayout.setVisibility(View.VISIBLE);
            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linerlayout.setVisibility(View.GONE);
                intDetails="No";
            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linerlayout.setVisibility(View.GONE);
                intDetails="No";
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog0.dismiss();
            }
        });
        submitEnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButton1.isChecked() || radioButton2.isChecked() ||radioButton3.isChecked()){

                   // Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                    new yourIntrest(rb.getText().toString(),dialog0).execute();

                }
                else{
                    Util.errorDialog(getActivity(),"Please Select any one!");
                }

            }
        });
        TextView title = (TextView) dialog0.findViewById(R.id.title);
        title.setText("SEND YOUR INTEREST");


        dialog0.show();

    }


    class TagSoldApi extends AsyncTask<String,Void,String> {

        String  text;
        Dialog dialog0;
        public TagSoldApi(String text){
            this.text=text;
            this.dialog0=dialog0;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("userId", MyPrefrences.getUserID(getActivity()));
            map.put("postId", jsonObject.optString("pid"));
            map.put("tagStatus", text.toString());

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.tagSold,"POST",map);

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

                        Util.errorDialog(getActivity(),"Your Posting Successfully Updated");

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


    class yourIntrest extends AsyncTask<String,Void,String> {

        String  text;
        Dialog dialog0;
        public yourIntrest(String text, Dialog dialog0){
            this.text=text;
            this.dialog0=dialog0;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();
            Log.d("stateId", MyPrefrences.getUserID(getActivity()));
            map.put("userId", MyPrefrences.getUserID(getActivity()));
            map.put("postId", jsonObject.optString("pid"));
            map.put("message", text.toString());
            map.put("postedBy", jsonObject.optString("posted_by"));
            map.put("intrestDetails", intDetails);

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.yourIntrest,"POST",map);

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

                        dialog0.dismiss();

                        Util.errorDialog(getActivity(),"Your Interest Successfully sent to Seller.");

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

    private class CustomPagerAdapter extends PagerAdapter {
        LayoutInflater layoutInflater;
        Button download;
        public CustomPagerAdapter() {
        }

        @Override
        public int getCount() {
            return ImgData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view==object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            NetworkImageView networkImageView;

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.custom_photogallery, container, false);
            networkImageView = (NetworkImageView) view.findViewById(R.id.networkImageView);


            Log.d("fgdfgdfghsg",ImgData.get(position).get("img").toString());

//            Picasso.with(getActivity()).load(ImgData.get(position).get("img")).resize(250, 150).centerCrop().into(networkImageView);

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            networkImageView.setImageUrl(ImgData.get(position).get("img").toString(),imageLoader);

            networkImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("gfhbgfgjhfgjfh",ImgData.get(position).get("img"));

//                    Util.showFullImageDialog(getActivity(), ImgData.get(position).get("img"), getArguments().getString("category"));
                    Util.showFullImageDialog(getActivity(), getArguments().getString("DataList"),position, getArguments().getString("category"));

                }
            });
            (container).addView(view);

//            thread=new Thread(){
//                @Override
//                public void run() {
//                    super.run();
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    viewPager.setCurrentItem(2);
//                }
//            };
//
//            thread.start();
//



            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((LinearLayout) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.POSITION_NONE;
        }
    }

    class ReadMore extends AsyncTask<String, Void, String> {

        String pid, val1,val2,comm;

        public ReadMore(String pid) {

           this.pid=pid;

            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> map = new HashMap<>();

            map.put("userId", MyPrefrences.getUserID(getActivity()));
            map.put("postingId", pid);

            JSONParser jsonParser = new JSONParser();
            String result = jsonParser.makeHttpRequest(Api.readMoreCounter, "POST", map);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("response", ": " + s);

            Util.cancelPgDialog(dialog);
        }

    }


}
