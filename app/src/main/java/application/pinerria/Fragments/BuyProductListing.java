package application.pinerria.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.pinerria.Activities.MainActivity;
import application.pinerria.Activities.PostReqAd;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.AppController;
import application.pinerria.Util.Model;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;
import uk.co.senab.photoview.PhotoViewAttacher;

import static android.text.Html.fromHtml;
import static application.pinerria.Activities.MainActivity.relative;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyProductListing extends Fragment {


    public  BuyProductListing() {
        // Required empty public constructor
    }
    ArrayList<String> mylist;
    List<HashMap<String,String>> DataList;
    List<HashMap<String,String>> DataListCat;
    List<HashMap<String,String>> DataListSubCat;
//    List<Model> DataListSubCat;
    CarAdapter caradapter;
    PropAdapter propadapter;
    HomeAdapter homeadapter;
    HomeAdapterHB homeadapterHB;
    Dialog dialog;
    GridView grigView;
    Spinner categ,subcateg,ageofProd;
    List<String> CatList = new ArrayList<String>();
    String [] agoOfPro={"Select","0-6 Months","7-12 Months","13-18 Months","19-24 Months","25-36 Months","37-48 Months","More then 48 Months"};
    List<String> SubCatList = new ArrayList<String>();
    String id;
    String catId,SubcatId,catType="",catTypeID="";
    String visValue="3",ageOfP="",datePosted1="",datePosted2="",datePosted3="",datePosted4="",datePosted5="",datePosted6="";
    public  static SharedPreferences refineSearch;
    public  static SharedPreferences.Editor editor2;
    ArrayAdapter aa;
    ArrayAdapter aa1;
    int val ;
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;
    int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
    List<String> data=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_buy_product_listing, container, false);
        getActivity().setTitle("Listing for "+getArguments().getString("type"));

        Button refine=(Button)view.findViewById(R.id.refine);
        Button postReq=(Button)view.findViewById(R.id.postReq);
        refine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //visValue="";
                filter(getArguments().getString("type"));
            }
        });

        postReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alertdialogcustom_opt);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView text = (TextView) dialog.findViewById(R.id.msg_txv);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
                text.setText(fromHtml("Did not find what you are looking for?"));
                Button ok = (Button) dialog.findViewById(R.id.btn_ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getActivity(),PostReqAd.class);
                        intent.putExtra("type",getArguments().getString("type"));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        //FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.fab);
        refineSearch = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor2 = refineSearch.edit();

        editor2.putString("SubcatPosition", "");
        editor2.putString("visValue", "3");
        editor2.commit();



        Log.d("bghbghggjhfgjf",getArguments().getString("type"));

        MainActivity.linerFilter.setVisibility(View.INVISIBLE);
//        MainActivity.filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                filter(getArguments().getString("type"),getArguments().getString("id"));
//                filter(getArguments().getString("type"));
//            }
//        });
//
//        MainActivity.reqirement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "req", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        MainActivity.filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                filter(getArguments().getString("type"),getArguments().getString("id"));
//                filter(getArguments().getString("type"));
//            }
//        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),PostReqAd.class);
//                intent.putExtra("type",getArguments().getString("type"));
//
//                startActivity(intent);
//            }
//        });
        grigView=view.findViewById(R.id.grigView);

        relative.setVisibility(View.VISIBLE);

        DataList =new ArrayList<>();
        DataListCat =new ArrayList<>();
        DataListSubCat =new ArrayList<>();

        new ProductListing().execute();


        if (MyPrefrences.getBuyCat(getActivity()).equals("3")){
            homeadapterHB = new HomeAdapterHB();
            grigView.setAdapter(homeadapterHB);
            Log.d("sgsdfgsdfgsdfgsd","true");
        }


        return  view;
    }

    private void filter(String type) {


        data.clear();


            final Dialog dialog0 = new Dialog(getActivity());
            dialog0.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog0.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog0.setContentView(R.layout.filert_layout);
            ImageView back_img = (ImageView) dialog0.findViewById(R.id.back_img);
            final EditText min = (EditText) dialog0.findViewById(R.id.min);
            final EditText max = (EditText) dialog0.findViewById(R.id.max);
            Button sendEnq = (Button) dialog0.findViewById(R.id.sendEnq);
            final RadioGroup radioGroup = (RadioGroup) dialog0.findViewById(R.id.radioGroup);
            final CheckBox checkBox1  =  dialog0.findViewById(R.id.checkBox1);
            final CheckBox checkBox2  =  dialog0.findViewById(R.id.checkBox2);
            final CheckBox checkBox3  =  dialog0.findViewById(R.id.checkBox3);
            final CheckBox checkBox4  =  dialog0.findViewById(R.id.checkBox4);
            final CheckBox checkBox5  =  dialog0.findViewById(R.id.checkBox5);
            final CheckBox checkBox6  =  dialog0.findViewById(R.id.checkBox6);
        categ=dialog0.findViewById(R.id.categ);
        subcateg=dialog0.findViewById(R.id.subcateg);
        ageofProd=dialog0.findViewById(R.id.ageofProd);

     //   categ.setSelection(Integer.parseInt(refineSearch.getString("catPosition", "")));

//        editor.putString("catid", catId);
//        editor.putString("catSubid", SubcatId);
//        editor.putString("visValue", visValue);
//        editor.putString("ageP", ageOfP);
//        editor.putString("min", min.getText().toString());
//        editor.putString("max", max.getText().toString());
//        editor.putString("datepost", datePosted);
//        editor.putString("cattype", catType);

        Log.d("dfdgvsdgdfsgdfh",refineSearch.getString("catid",""));


            checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox1.isChecked()){
                        datePosted1="0-3";
                        data.add("0-3");
                    }
                }
            });


        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox2.isChecked()){
                    datePosted2="4-10";
                    data.add("4-10");

                }

            }
        });
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox3.isChecked()){
                    datePosted3="11-33";
                    data.add("11-33");
                }
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox4.isChecked()){
                    datePosted4="30-60";
                    data.add("30-60");
                }
            }
        });
        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox5.isChecked()){
                    datePosted5="60-90";
                    data.add("60-90");
                }
            }
        });
        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox6.isChecked()){
                    datePosted6="90-2000000";
                    data.add("90-2000000");
                }

            }
        });

        //Log.d("dfdsdsgdfgdfg",type);
            new CategoryApi(type).execute();

        ageofProd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==1){
                    ageOfP="0-6";
                }
                else if (i==2){
                    ageOfP="7-12";
                }
                else if (i==3){
                    ageOfP="13-18";
                }
                else if (i==4){
                    ageOfP="19-24";
                }
                else if (i==5){
                    ageOfP="25-36";
                }
                else if (i==6){
                    ageOfP="37-48";
                }
                else if (i==7){
                    ageOfP="49-1000";
                }
                Log.d("sdfvsdgfdsgdfhdf",ageOfP);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                switch (i) {
                    case R.id.radioButton1: {
                        Log.d("fdgdfhgdfh", "1");
                        visValue = "1";
                        //editor2 = refineSearch.edit();
//                        editor2.putString("visValue", visValue);
//                        editor2.commit();
                        break;
                    }
                    case R.id.radioButton2: {
                        Log.d("fdgdfhgdfh", "2");
                        visValue = "2";
//                        editor2 = refineSearch.edit();
//                        editor2.putString("visValue", visValue);
//                        editor2.commit();
                        break;
                    }
                    case R.id.radioButton3: {
                        Log.d("fdgdfhgdfh", "3");
                        visValue = "3";
//                        editor2 = refineSearch.edit();
//                        editor2.putString("visValue", visValue);
//                        editor2.commit();
                        break;
                    }

                }

            }
        });


        categ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catId=DataListCat.get(i).get("id").toString();
                catTypeID=DataListCat.get(i).get("cat_type").toString();

                new SubCategoryApi(catId).execute();

                Log.d("fgdgdfgdfhdf",DataListCat.get(i).get("id"));
                if (DataListCat.get(i).get("cat_type").toString().equals("1") || DataListCat.get(i).get("cat_type").toString().equals("2")) {
                    catType = "car";
                }
                else if (DataListCat.get(i).get("cat_type").toString().equals("3")){
                    catType="hb";
                }

                int spinnerPosition = aa.getPosition(DataListCat.get(i).get("category"));
                editor2 = refineSearch.edit();
                Log.d("fgdfgdfhdfh",String.valueOf(spinnerPosition));
                editor2.putString("catPosition", String.valueOf(spinnerPosition));
                val=spinnerPosition;
                editor2.commit();

                Log.d("fvgdgdfgbdfhnd",refineSearch.getString("catPosition", ""));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        subcateg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SubcatId=DataListSubCat.get(i).get("id");

                try {
                    Log.d("fgdgdfgdfhdf",DataListSubCat.get(i).get("id"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                int spinnerPosition = aa1.getPosition(DataListSubCat.get(i).get("subcategory"));
//                editor2 = refineSearch.edit();
//
//                Log.d("fgdfgfgfgfgdfhdfh",String.valueOf(spinnerPosition));
//                editor2.putString("SubcatPosition", String.valueOf(spinnerPosition));
//                editor2.commit();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



//        CatList.add(type);
//        ArrayAdapter aa1 = new ArrayAdapter(getActivity(),R.layout.simple_spinner_item,CatList);
//        aa1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        categ.setAdapter(aa1);


                ArrayAdapter aa = new ArrayAdapter(getActivity(),R.layout.simple_spinner_item,agoOfPro);
                aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                ageofProd.setAdapter(aa);

//            categ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    //Util.cancelPgDialog(dialog);
//
//                    id= DataListCat.get(i).get("id");
//                    new SubCategoryApi(id).execute();
//                }
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });

            TextView title = (TextView) dialog0.findViewById(R.id.title);
                title.setText("Refine Search");
                back_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog0.cancel();
                        new ProductListing().execute();
                    }
                });

        dialog0.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    new ProductListing().execute();
                    dialog0.dismiss();
                    return  true;
                }
                return false;
            }
        });

        sendEnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.ChangeCat=false;
                getActivity().setTitle("Listing for "+categ.getSelectedItem().toString());
                try {
                    Log.d("fgdfgdfgdfhgdfh1",catId);
                    Log.d("fgdfgdfgdfhgdfh2",SubcatId);
                    Log.d("fgdfgdfgdfhgdfh3",visValue);
                    Log.d("fgdfgdfgdfhgdfh4",ageOfP);
                    Log.d("fgdfgdfgdfhgdfh5",min.getText().toString());
                    Log.d("fgdfgdfgdfhgdfh6",max.getText().toString());
                    Log.d("fgdfgdfgdfhgdfh7",datePosted1.toString());
                    Log.d("fgdfgdfgdfhgdfh7",datePosted2.toString());
                    Log.d("fgdfgdfgdfhgdfh7",datePosted3.toString());
                    Log.d("fgdfgdfgdfhgdfh7",datePosted4.toString());
                    Log.d("fgdfgdfgdfhgdfh7",datePosted5.toString());
                    Log.d("fgdfgdfgdfhgdfh7",datePosted6.toString());
                    Log.d("fgdfgdfgdfhgdfh8",catType.toString());


                    Log.d("sdfsdgfsdgfgdfgdfgd",data.toString().replace("[","").replace("]","").replace(" ",""));

//                    mylist = new ArrayList<String>();
//                    mylist.add(datePosted1);
//                    mylist.add(datePosted2);
//                    mylist.add(datePosted3);
//                    mylist.add(datePosted4);
//                    mylist.add(datePosted5);
//                    mylist.add(datePosted6);
//                    Log.d("sdfgsgsfgdfgdfgh", String.valueOf(mylist));
//
//
//                    JSONArray jsonArray = new JSONArray();
//                    JSONObject obj = new JSONObject();
//
//                    try {
//                        obj.put("id", mylist);
//
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    jsonArray.put(obj);
//
//
//                    Log.d("sdfgsdgdfgdfhgdg",jsonArray.toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }

                new ProductListing2(dialog0,catId,SubcatId,visValue,ageOfP,min.getText().toString(),max.getText().toString(),String.valueOf(mylist),datePosted2,datePosted3,datePosted4,datePosted5,datePosted6).execute();

                editor2 = refineSearch.edit();
                editor2.putString("catid", catId);
                editor2.putString("catSubid", SubcatId);
//                editor2.putString("catSubid", DataListSubCat.get(subcateg.getSelectedItemPosition()).getId().toString());
                //editor2.putString("visValue", visValue);
                editor2.putString("ageP", ageOfP);
                editor2.putString("min", min.getText().toString());
                editor2.putString("max", max.getText().toString());
//                editor2.putString("datepost1", datePosted1);
//                editor2.putString("datepost2", datePosted2);
//                editor2.putString("datepost3", datePosted3);
//                editor2.putString("datepost4", datePosted4);
//                editor2.putString("datepost5", datePosted5);
//                editor2.putString("datepost6", datePosted6);
                editor2.putString("cattype", catType);
                editor2.commit();

            }
        });

                dialog0.show();

    }

    class CarAdapter extends BaseAdapter {
        LayoutInflater inflater;
        TextView title,postingId,months,price,fueltype,kmsDone,manufactre;
        NetworkImageView image;
        CarAdapter(){
            inflater=(LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return DataList.size();
        }

        @Override
        public Object getItem(int i) {
            return DataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view=inflater.inflate(R.layout.custonlistview_cat_listing,viewGroup,false);

            title=view.findViewById(R.id.title);
            image=view.findViewById(R.id.image);
            postingId=view.findViewById(R.id.postingId);

            months=view.findViewById(R.id.months);
            price=view.findViewById(R.id.price);
            fueltype=view.findViewById(R.id.fueltype);
            kmsDone=view.findViewById(R.id.kmsDone);
            manufactre=view.findViewById(R.id.manufactre);
           // DecimalFormat formatter = new DecimalFormat("#,###,###");

            title.setText(DataList.get(i).get("post_title").toString());
            postingId.setText(DataList.get(i).get("pid").toString());

            months.setText(DataList.get(i).get("manufacture_month_yr").toString()+"/"+ DataList.get(i).get("manufacture_yr").toString());
            //price.setText("₹ "+DataList.get(i).get("min_price").toString());
            price.setText("₹ "+ NumberFormat.getInstance().format(Double.parseDouble(DataList.get(i).get("min_price"))));
            fueltype.setText(DataList.get(i).get("fueltype_id").toString());
            kmsDone.setText(DataList.get(i).get("mileage").toString());
            manufactre.setText(DataList.get(i).get("manufacturer_id").toString());
//
//            Picasso.with(getActivity()).load(DataList.get(i).get("image1").toString())
//                    .resize(600, 200).centerCrop().into(image);

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            image.setImageUrl(DataList.get(i).get("image1").toString(),imageLoader);
            return view;
        }
    }

    class PropAdapter extends BaseAdapter {
        LayoutInflater inflater;
        TextView title,postingId,months,price,proType,badroom,location;
        NetworkImageView image;
        PropAdapter(){
            inflater=(LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return DataList.size();
        }

        @Override
        public Object getItem(int i) {
            return DataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view=inflater.inflate(R.layout.custonlistview_cat_listing_prp,viewGroup,false);


            title=view.findViewById(R.id.title);
            image=view.findViewById(R.id.image);
            postingId=view.findViewById(R.id.postingId);

            months=view.findViewById(R.id.months);
            price=view.findViewById(R.id.price);

            proType=view.findViewById(R.id.proType);
            badroom=view.findViewById(R.id.badroom);
            location=view.findViewById(R.id.location);


            title.setText(DataList.get(i).get("post_title").toString());
            postingId.setText(DataList.get(i).get("pid").toString());

            months.setText(DataList.get(i).get("manufacture_month_yr").toString()+"/"+ DataList.get(i).get("manufacture_yr").toString());
//            price.setText("₹ "+DataList.get(i).get("min_price").toString());
            price.setText("₹ "+ NumberFormat.getInstance().format(Double.parseDouble(DataList.get(i).get("min_price"))));
            proType.setText(DataList.get(i).get("propertytype_id").toString());
            badroom.setText(DataList.get(i).get("bedrooms").toString());
            location.setText(DataList.get(i).get("property_city").toString()+", "+DataList.get(i).get("property_state").toString());

//            Picasso.with(getActivity()).load(DataList.get(i).get("image1").toString())
//                    .resize(600, 200).centerCrop().into(image);

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            image.setImageUrl(DataList.get(i).get("image1").toString(),imageLoader);
            return view;
        }
    }

    class HomeAdapterHB extends BaseAdapter {
        LayoutInflater inflater;
        TextView title,postingId,months,price,fueltype,kmsDone,descreption;
        NetworkImageView image;
        HomeAdapterHB(){
            inflater=(LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return DataList.size();
        }

        @Override
        public Object getItem(int i) {
            return DataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view=inflater.inflate(R.layout.custonlistview_cat_listing_home_hb,viewGroup,false);

            title=view.findViewById(R.id.title);
            image=view.findViewById(R.id.image);
            postingId=view.findViewById(R.id.postingId);

            months=view.findViewById(R.id.months);
            price=view.findViewById(R.id.price);
            fueltype=view.findViewById(R.id.fueltype);
            kmsDone=view.findViewById(R.id.kmsDone);
            descreption=view.findViewById(R.id.descreption);

            title.setText(DataList.get(i).get("post_title").toString());
            postingId.setText(DataList.get(i).get("pid").toString());
            descreption.setText(DataList.get(i).get("description").toString());

            Log.d("fgdfghfdhfdh",DataList.get(i).get("manufacture_month_yr").toString());
            Log.d("fgdfghfdhfdh",DataList.get(i).get("min_price").toString());
            if (!DataList.get(i).get("manufacture_month_yr").toString().equals("")) {
                months.setText(DataList.get(i).get("manufacture_month_yr").toString() + "/" + DataList.get(i).get("manufacture_yr").toString());
            }
//            price.setText("₹ "+DataList.get(i).get("min_price").toString());
            if (!DataList.get(i).get("min_price").toString().equals("")) {
                price.setText("₹ " + NumberFormat.getInstance().format(Double.parseDouble(DataList.get(i).get("min_price"))));
            }

//            fueltype.setText(DataList.get(i).get("fueltype_id").toString());
//            kmsDone.setText(DataList.get(i).get("mileage").toString());
//            manufactre.setText(DataList.get(i).get("manufacturer_id").toString());

            Log.d("fddsfdsaffas", String.valueOf(size));
            Log.d("dfssdfsdfsdfsdfsdfs",DataList.get(i).get("image1").toString());
//            Picasso.with(getActivity()).load(DataList.get(i).get("image1").toString()).resize(600, 200)
//                    //.transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
//                  //  .skipMemoryCache()
//                    .centerInside()
//                    .into(image);


            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            image.setImageUrl(DataList.get(i).get("image1").toString(),imageLoader);
            return view;
        }
    }

    class HomeAdapter extends BaseAdapter {
        LayoutInflater inflater;
        TextView title,postingId,months,price,fueltype,kmsDone,manufactre;
        NetworkImageView image;
        HomeAdapter(){
            inflater=(LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return DataList.size();
        }

        @Override
        public Object getItem(int i) {
            return DataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view=inflater.inflate(R.layout.custonlistview_cat_listing_home,viewGroup,false);

            title=view.findViewById(R.id.title);
            image=view.findViewById(R.id.image);
            postingId=view.findViewById(R.id.postingId);

            months=view.findViewById(R.id.months);
            price=view.findViewById(R.id.price);
            fueltype=view.findViewById(R.id.fueltype);
            kmsDone=view.findViewById(R.id.kmsDone);
            manufactre=view.findViewById(R.id.manufactre);

            title.setText(DataList.get(i).get("post_title").toString());
            postingId.setText(DataList.get(i).get("pid").toString());

            Log.d("fgdfghfdhfdh",DataList.get(i).get("manufacture_month_yr").toString());
            Log.d("fgdfghfdhfdh",DataList.get(i).get("min_price").toString());
            if (!DataList.get(i).get("manufacture_month_yr").toString().equals("")) {
                months.setText(DataList.get(i).get("manufacture_month_yr").toString() + "/" + DataList.get(i).get("manufacture_yr").toString());
            }
//            price.setText("₹ "+DataList.get(i).get("min_price").toString());
            if (!DataList.get(i).get("min_price").toString().equals("")) {
                price.setText("₹ " + NumberFormat.getInstance().format(Double.parseDouble(DataList.get(i).get("min_price"))));
            }
//            fueltype.setText(DataList.get(i).get("fueltype_id").toString());
//            kmsDone.setText(DataList.get(i).get("mileage").toString());
//            manufactre.setText(DataList.get(i).get("manufacturer_id").toString());

//            Picasso.with(getActivity()).load(DataList.get(i).get("image1").toString()).resize(600, 200).centerCrop().into(image);


            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            image.setImageUrl(DataList.get(i).get("image1").toString(),imageLoader);

            return view;
        }
    }


    class ProductListing2 extends AsyncTask<String,Void,String> {

        String catId,subCatId,visVal,ageValue,min,max,datePosted1,datePosted2,datePosted3,datePosted4,datePosted5,datePosted6;
        Dialog dialog0;

        public ProductListing2(Dialog dialog0,String catId,String subCatId,String visVal,String ageValue,String min,String max,String datePosted1,String datePosted2,String datePosted3,String datePosted4,String datePosted5,String datePosted6){
            this.catId=catId;
            this.subCatId=subCatId;
            this.visVal=visVal;
            this.ageValue=ageValue;
            this.dialog0=dialog0;
            this.min=min;
            this.max=max;
            this.datePosted1=datePosted1;
            this.datePosted2=datePosted2;
            this.datePosted3=datePosted3;
            this.datePosted4=datePosted4;
            this.datePosted5=datePosted5;
            this.datePosted6=datePosted6;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            Log.d("fgfdgdfhgghdfhg",getArguments().getString("id"));

            if (MyPrefrences.getBuyCat(getActivity()).equals("1")|| MyPrefrences.getBuyCat(getActivity()).equals("2")){
                Log.d("vfgbvdfgdfgf","car");
                map.put("type", "car");
            }
            else if (MyPrefrences.getBuyCat(getActivity()).equals("3")){
                Log.d("vfgbvdfgdfgf","hb");
                map.put("type", "hb");
            }

            map.put("userId", MyPrefrences.getUserID(getActivity()));
            map.put("categoryId", catId);

            map.put("subcat_id", subCatId+"");
            map.put("visibility", visVal);
            map.put("product_age", ageValue);
            map.put("min_price", min);
            map.put("max_price", max);
            map.put("posteddate", data.toString().replace("[","").replace("]","").replace(" ",""));

            Log.d("dsfsfsdfsdgsfgdfgds",data.toString().replace("[","").replace("]","").replace(" ",""));
//            map.put("posteddate[]", datePosted2);
//            map.put("posteddate[]", datePosted3);
//            map.put("posteddate[]", datePosted4);
//            map.put("posteddate[]", datePosted5);
//            map.put("posteddate[]", datePosted6);

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.advListByCategory,"POST",map);

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
                        DataList.clear();
                        final JSONArray jsonArray=jsonObject.getJSONArray("message");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                HashMap<String, String> map = new HashMap<>();

                                map.put("id", jsonObject1.optString("id"));
                                map.put("post_title", jsonObject1.optString("post_title"));
                                map.put("image1", jsonObject1.optString("image1"));
                                map.put("pid", jsonObject1.optString("pid"));

                                map.put("manufacturer_id", jsonObject1.optString("manufacturer_id"));
                                map.put("mileage", jsonObject1.optString("mileage"));
                                map.put("fueltype_id", jsonObject1.optString("fueltype_id"));
                                map.put("min_price", jsonObject1.optString("min_price"));
                                map.put("manufacture_month_yr", jsonObject1.optString("manufacture_month_yr"));
                                map.put("manufacture_yr", jsonObject1.optString("manufacture_yr"));

                                map.put("propertytype_id", jsonObject1.optString("propertytype_id"));
                                map.put("bedrooms", jsonObject1.optString("bedrooms"));
                                map.put("property_city", jsonObject1.optString("property_city"));
                                map.put("property_state", jsonObject1.optString("property_state"));
                                map.put("description", jsonObject1.optString("description"));

                                DataList.add(map);

                                if (getArguments().getString("type").equalsIgnoreCase("car")) {
                                    caradapter = new CarAdapter();
                                    grigView.setAdapter(caradapter);
                                } else if (getArguments().getString("type").equalsIgnoreCase("property")) {
                                    propadapter = new PropAdapter();
                                    grigView.setAdapter(propadapter);
                                } else {
                                    homeadapter = new HomeAdapter();
                                    grigView.setAdapter(homeadapter);
                                }


                                if (categ.getSelectedItem().equals("Car")){
                                    caradapter = new CarAdapter();
                                    grigView.setAdapter(caradapter);
                                }
                                else if (categ.getSelectedItem().equals("Property")){
                                    propadapter = new PropAdapter();
                                    grigView.setAdapter(propadapter);
                                }

                                if (catTypeID.equals("2")){
                                    homeadapter = new HomeAdapter();
                                    grigView.setAdapter(homeadapter);
                                }
                                else if (catTypeID.equals("3")){
                                    homeadapterHB = new HomeAdapterHB();
                                    grigView.setAdapter(homeadapterHB);
                                }

                                grigView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Fragment fragment = new BuyProductListDetail();
                                        Bundle bundle = new Bundle();
                                        try {
                                            bundle.putString("DataList", jsonArray.get(i).toString());
                                            bundle.putString("category", DataList.get(i).get("post_title").toLowerCase());
                                            bundle.putString("type", getArguments().getString("type"));
                                            bundle.putString("countstatus", "1");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        FragmentManager fm = getActivity().getSupportFragmentManager();
                                        FragmentTransaction ft = fm.beginTransaction();
                                        fragment.setArguments(bundle);
                                        ft.replace(R.id.container, fragment).addToBackStack(null).commit();

                                    }
                                });
                            }

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




    class ProductListing extends AsyncTask<String,Void,String> {


        public ProductListing(){

            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            Log.d("fgfdgdfhgghdfhg",getArguments().getString("id"));

            if (MyPrefrences.getBuyCat(getActivity()).equals("1")|| MyPrefrences.getBuyCat(getActivity()).equals("2")){
                Log.d("vfgbvdfgdfgf","car");
                map.put("type", "car");
            }
            else if (MyPrefrences.getBuyCat(getActivity()).equals("3")){
                Log.d("vfgbvdfgdfgf","hb");
                map.put("type", "hb");
            }
            map.put("categoryId", getArguments().getString("id"));
            map.put("userId", MyPrefrences.getUserID(getActivity()));
//            map.put("subcat_id", password.toString());

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.advListByCategory,"POST",map);

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

                        DataList.clear();
                        final JSONArray jsonArray=jsonObject.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id", jsonObject1.optString("id"));
                            map.put("post_title", jsonObject1.optString("post_title"));
                            map.put("image1", jsonObject1.optString("image1"));
                            map.put("pid", jsonObject1.optString("pid"));

                            map.put("manufacturer_id", jsonObject1.optString("manufacturer_id"));
                            map.put("mileage", jsonObject1.optString("mileage"));
                            map.put("fueltype_id", jsonObject1.optString("fueltype_id"));
                            map.put("min_price", jsonObject1.optString("min_price"));
                            map.put("manufacture_month_yr", jsonObject1.optString("manufacture_month_yr"));
                            map.put("manufacture_yr", jsonObject1.optString("manufacture_yr"));

                            map.put("propertytype_id", jsonObject1.optString("propertytype_id"));
                            map.put("bedrooms", jsonObject1.optString("bedrooms"));
                            map.put("property_city", jsonObject1.optString("property_city"));
                            map.put("property_state", jsonObject1.optString("property_state"));
                            map.put("description", jsonObject1.optString("description"));

                            DataList.add(map);

                            if (getArguments().getString("type").equalsIgnoreCase("car")){
                            caradapter = new CarAdapter();
                                grigView.setAdapter(caradapter);
                            }
                            else if (getArguments().getString("type").equalsIgnoreCase("property")){
                                propadapter=new PropAdapter();
                                grigView.setAdapter(propadapter);
                            }
                            else {
                                homeadapter=new HomeAdapter();
                                grigView.setAdapter(homeadapter);
                            }

                            if (MyPrefrences.getBuyCat(getActivity()).equals("3")){
                                homeadapterHB = new HomeAdapterHB();
                                grigView.setAdapter(homeadapterHB);
                                Log.d("sgsdfgsdfgsdfgsd","true");
                            }

                            grigView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Fragment fragment=new BuyProductListDetail();

                                    Bundle bundle=new Bundle();
                                    try {
                                        bundle.putString("DataList",jsonArray.get(i).toString());
                                        bundle.putString("category",DataList.get(i).get("post_title").toLowerCase());
                                        bundle.putString("type", getArguments().getString("type"));
                                        bundle.putString("countstatus", "1");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    FragmentManager fm=getActivity().getSupportFragmentManager();
                                    FragmentTransaction ft=fm.beginTransaction();
                                    fragment.setArguments(bundle);
                                    ft.replace(R.id.container,fragment).addToBackStack(null).commit();

                                }
                            });
                        }
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


    class CategoryApi extends AsyncTask<String,Void,String> {

        String type;
        public CategoryApi(String type){
            this.type=type;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();
            Log.d("fgfdgdfhgghdfhg",getArguments().getString("id"));
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

                            Log.d("dfdsdsgdfgdfg",type);

                            aa = new ArrayAdapter(getActivity(),R.layout.simple_spinner_item,CatList);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            categ.setAdapter(aa);

                            if (jsonObject1.optString("category").equals(type)){
                                Log.d("fgvfdgsfdgsfdfdfgdgrg", String.valueOf(i+1));
                                val=i;
                            }


                            if (Util.ChangeCat==false) {
                                if (!refineSearch.getString("catPosition", "").equals("")) {
                                    categ.setSelection(Integer.parseInt(refineSearch.getString("catPosition", "")));
                                    Log.d("fsdfsdgasd","true");
                                }
                            }
                            else if (Util.ChangeCat==true) {
                                categ.setSelection(val);
                                Log.d("fsdfsdgasd","false");
                            }

                        }

                        int spinnerPosition = aa.getPosition(type);
                        editor2 = refineSearch.edit();
                        Log.d("fgdfgdfhddfdfdsgdgfdgfh",String.valueOf(spinnerPosition+1));
                        editor2.putString("catPosition", String.valueOf(spinnerPosition+1));
                        editor2.commit();

                        Log.d("fvgdgdfgbdfhnd",refineSearch.getString("catPosition", ""));


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

    class SubCategoryApi extends AsyncTask<String,Void,String> {

        String  id;
        public SubCategoryApi(String id){
            this.id=id;
            dialog=new Dialog(getActivity());
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
                        map2.put("id","");
                        DataListSubCat.add(map2);
                        SubCatList.add("Select Sub Category");


                        final JSONArray jsonArray=jsonObject.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
//                            DataListSubCat.add(new Model(jsonObject1.optString("id"),jsonObject1.optString("subcategory")));
//                            SubCatList.add(jsonObject1.optString("subcategory"));

                            HashMap<String, String> map = new HashMap<>();
////
                            map.put("id",jsonObject1.optString("id"));
                            map.put("subcategory",jsonObject1.optString("subcategory"));
                            SubCatList.add(jsonObject1.optString("subcategory"));
                            DataListSubCat.add(map);




//                            if (!refineSearch.getString("SubcatPosition", "").equals("")) {
//                                subcateg.setSelection(Integer.parseInt(refineSearch.getString("SubcatPosition", "")));
//                            }
                        }
                        aa1 = new ArrayAdapter(getActivity(),R.layout.simple_spinner_item,SubCatList);
                        aa1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        subcateg.setAdapter(aa1);

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


    private class BitmapTransform implements Transformation {

        private final int maxWidth;
        private final int maxHeight;

        public BitmapTransform(int maxWidth, int maxHeight) {
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
        }


        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth, targetHeight;
            double aspectRatio;

            if (source.getWidth() > source.getHeight()) {
                targetWidth = maxWidth;
                aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                targetHeight = (int) (targetWidth * aspectRatio);
            } else {
                targetHeight = maxHeight;
                aspectRatio = (double) source.getWidth() / (double) source.getHeight();
                targetWidth = (int) (targetHeight * aspectRatio);
            }

            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return maxWidth + "x" + maxHeight;
        }

    }
}
