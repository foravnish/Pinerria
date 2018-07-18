package application.pinerria.Activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.pinerria.Fragments.BuyProductListDetail;
import application.pinerria.Fragments.ShowRequirement;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;

public class ShowRequirementAct extends AppCompatActivity {
    List<HashMap<String,String>> DataList;
    Dialog dialog;
    GridView grigView;
    List<String> CatList = new ArrayList<String>();
    List<HashMap<String,String>> DataListCat;
    Spinner categ;
    String id;
    RequrementAdapter requrementAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_requirement);

        final ImageView back_img=findViewById(R.id.back_img);
        TextView title=findViewById(R.id.title);
        title.setText("Show Requirements");
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        grigView=findViewById(R.id.grigView);
        MainActivity.linerFilter.setVisibility(View.VISIBLE);
        DataList =new ArrayList<>();


        MainActivity.linerFilter.setVisibility(View.GONE);

        Log.d("fgdfghdfhdfh",getIntent().getStringExtra("typeCategory"));

        //new DeshBoardReqListing().execute();
        new ShowRequirementData(getIntent().getStringExtra("catid"),getIntent().getStringExtra("Subcatid")).execute();

    }

    class ShowRequirementData extends AsyncTask<String, Void, String> {

        String catId, subId;

        public ShowRequirementData(String catId,String subId) {

            this.catId = catId;
            this.subId = subId;
            dialog = new Dialog(ShowRequirementAct.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> map = new HashMap<>();

            map.put("catId", catId);
            map.put("SubCatId", subId.toString());

            JSONParser jsonParser = new JSONParser();
            String result = jsonParser.makeHttpRequest(Api.allAdsForRequirement, "POST", map);

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
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id", jsonObject1.optString("id"));
                            map.put("cat_id", jsonObject1.optString("cat_id"));
                            map.put("subcat_id", jsonObject1.optString("subcat_id"));
                            map.put("visibility", jsonObject1.optString("visibility"));
                            map.put("title", jsonObject1.optString("title"));
                            map.put("requirement", jsonObject1.optString("requirement"));
                            map.put("budget", jsonObject1.optString("budget"));
                            map.put("type", jsonObject1.optString("type"));
                            map.put("mobile", jsonObject1.optString("mobile"));
                            map.put("email", jsonObject1.optString("email"));
                            map.put("contact_person", jsonObject1.optString("contact_person"));
                            map.put("user_id", jsonObject1.optString("user_id"));
                            map.put("posted_date", jsonObject1.optString("posted_date"));
                            map.put("expiry_date", jsonObject1.optString("expiry_date"));
                            map.put("delete_status", jsonObject1.optString("delete_status"));
                            map.put("sold_status", jsonObject1.optString("sold_status"));
                            map.put("status", jsonObject1.optString("status"));
                            map.put("category", jsonObject1.optString("category"));
                            map.put("subcategory", jsonObject1.optString("subcategory"));

                            DataList.add(map);

                            requrementAdapter = new RequrementAdapter();
                            grigView.setAdapter(requrementAdapter);

                            grigView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    if (getIntent().getStringExtra("typeCategory").equalsIgnoreCase("sell")){

                                        Intent intent = new Intent(ShowRequirementAct.this, MainActivity.class);
                                        intent.putExtra("isflag", "sell");
                                        startActivity(intent);
                                    }
                                    else if (getIntent().getStringExtra("typeCategory").equalsIgnoreCase("home")){
                                        Intent intent = new Intent(ShowRequirementAct.this, MainActivity.class);
                                        intent.putExtra("isflag", "home");
                                        startActivity(intent);
                                    }

                                }
                            });
                        }

                    } else {
//                        Util.errorDialog(ShowRequirementAct.this, jsonObject.optString("message"));
//                        Toast.makeText(ShowRequirementAct.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();

                        Toast toast= Toast.makeText(getApplicationContext(),
                                jsonObject.optString("message"), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        finish();
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(ShowRequirementAct.this, "Some Error! or Please connect to the internet.");
                e.printStackTrace();
            }
        }

    }

    class DeshBoardReqListing extends AsyncTask<String,Void,String> {


        public DeshBoardReqListing(){

            dialog=new Dialog(ShowRequirementAct.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

//            map.put("memberId", MyPrefrences.getUserID(getActivity()));
//            map.put("status", "all");
//            map.put("sold_status", "all");
//            map.put("password", password.toString());

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.allAdsForRequirement,"POST",map);

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
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();

                            map.put("id", jsonObject1.optString("id"));
                            map.put("cat_id", jsonObject1.optString("cat_id"));
                            map.put("subcat_id", jsonObject1.optString("subcat_id"));
                            map.put("visibility", jsonObject1.optString("visibility"));
                            map.put("title", jsonObject1.optString("title"));
                            map.put("requirement", jsonObject1.optString("requirement"));
                            map.put("budget", jsonObject1.optString("budget"));
                            map.put("type", jsonObject1.optString("type"));
                            map.put("mobile", jsonObject1.optString("mobile"));
                            map.put("email", jsonObject1.optString("email"));
                            map.put("contact_person", jsonObject1.optString("contact_person"));
                            map.put("user_id", jsonObject1.optString("user_id"));
                            map.put("posted_date", jsonObject1.optString("posted_date"));
                            map.put("expiry_date", jsonObject1.optString("expiry_date"));
                            map.put("delete_status", jsonObject1.optString("delete_status"));
                            map.put("sold_status", jsonObject1.optString("sold_status"));
                            map.put("status", jsonObject1.optString("status"));
                            map.put("category", jsonObject1.optString("category"));
                            map.put("subcategory", jsonObject1.optString("subcategory"));

                            DataList.add(map);

                            requrementAdapter = new RequrementAdapter();
                            grigView.setAdapter(requrementAdapter);

                            grigView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                                    Intent  intent=new Intent(ShowRequirementAct.this, PostAdd.class);
//
//                                    intent.putExtra("Cat_id",getArguments().getString("Cat_id"));
//                                    intent.putExtra("threemonths",getArguments().getString("threemonths"));
//                                    intent.putExtra("sixmonths",getArguments().getString("sixmonths"));
//                                    intent.putExtra("areatype","society");
//                                    intent.putExtra("areatypenum","1");
//                                    intent.putExtra("heading",getArguments().getString("heading"));
//                                    intent.putExtra("forPayment","");
//                                    startActivity(intent);



                                }
                            });
                        }
                    }
                    else {
                        // Util.errorDialog(getActivity(),jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(ShowRequirementAct.this,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }


    class RequrementAdapter extends BaseAdapter {
        LayoutInflater inflater;
        TextView budget,budget1,catagery,subCat,postdate,expDate,status,title,desc,link;
        ImageView image;
        RequrementAdapter(){
            inflater=(LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

            view=inflater.inflate(R.layout.custonlistview_deshboard_req2,viewGroup,false);

            budget=view.findViewById(R.id.budget);
            budget1=view.findViewById(R.id.budget1);
            catagery=view.findViewById(R.id.catagery);
            subCat=view.findViewById(R.id.subCat);
            postdate=view.findViewById(R.id.postdate);
            expDate=view.findViewById(R.id.expDate);
            status=view.findViewById(R.id.status);
            title=view.findViewById(R.id.title);
            desc=view.findViewById(R.id.desc);
            link=view.findViewById(R.id.link);

            if (MyPrefrences.getHomeType(getApplicationContext()).equals("home")) {
                budget1.setVisibility(View.GONE);
                budget.setVisibility(View.GONE);
            } else {
                budget1.setVisibility(View.VISIBLE);
                budget.setVisibility(View.VISIBLE);
            }

            title.setText(DataList.get(i).get("title").toString());
            desc.setText(DataList.get(i).get("requirement").toString());
            budget.setText("₹ "+DataList.get(i).get("budget").toString());
            //catagery.setText(DataList.get(i).get("category").toString());
            //subCat.setText(DataList.get(i).get("subcategory").toString());
            //postdate.setText(DataList.get(i).get("posted_date").toString());
            //expDate.setText(DataList.get(i).get("expiry_date").toString());
            status.setText(DataList.get(i).get("visibility").toString());

//            if (DataList.get(i).get("visibility").toString().equalsIgnoreCase("1")){
//                status.setText("Visible in my Residential Complex");
//            }
//            else if (DataList.get(i).get("visibility").toString().equalsIgnoreCase("2")){
//                status.setText("Visible within Nearby Area");
//            }
//            else if (DataList.get(i).get("visibility").toString().equalsIgnoreCase("3")){
//                status.setText("Visible within Entire Zone");
//            }

//            status.setText(DataList.get(i).get("visibility").toString());

            return view;
        }
    }


}
