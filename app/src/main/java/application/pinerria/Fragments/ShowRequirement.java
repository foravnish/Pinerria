package application.pinerria.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.pinerria.Activities.MainActivity;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowRequirement extends Fragment {

    public ShowRequirement() {
        // Required empty public constructor
    }
    List<HashMap<String,String>> DataList;
    Dialog dialog;
    GridView grigView;
    List<String> CatList = new ArrayList<String>();
    List<HashMap<String,String>> DataListCat;
    Spinner categ;
    String id;
    RequrementAdapter requrementAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_show_requirement, container, false);
        grigView=view.findViewById(R.id.grigView);
        MainActivity.linerFilter.setVisibility(View.VISIBLE);
        DataList =new ArrayList<>();


        MainActivity.linerFilter.setVisibility(View.GONE);
        getActivity().setTitle("Show Requirement");

        new DeshBoardReqListing().execute();

        return view;
    }

    class DeshBoardReqListing extends AsyncTask<String,Void,String> {


        public DeshBoardReqListing(){

            dialog=new Dialog(getActivity());
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

//                            grigView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                    Fragment fragment=new BuyProductListDetail();
//                                    Bundle bundle=new Bundle();
//                                    try {
//                                        bundle.putString("DataList",jsonArray.get(i).toString());
//                                        bundle.putString("category","");
//                                        bundle.putString("type", "");
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    FragmentManager fm=getActivity().getSupportFragmentManager();
//                                    FragmentTransaction ft=fm.beginTransaction();
//                                    fragment.setArguments(bundle);
//                                    ft.replace(R.id.container,fragment).addToBackStack(null).commit();
//
//                                }
//                            });
                        }
                    }
                    else {
                        // Util.errorDialog(getActivity(),jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(getActivity(),"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }


    class RequrementAdapter extends BaseAdapter {
        LayoutInflater inflater;
        TextView budget,catagery,subCat,postdate,expDate,status,title,desc;
        ImageView image;
        RequrementAdapter(){
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

            view=inflater.inflate(R.layout.custonlistview_deshboard_req,viewGroup,false);

            budget=view.findViewById(R.id.budget);
            catagery=view.findViewById(R.id.catagery);
            subCat=view.findViewById(R.id.subCat);
            postdate=view.findViewById(R.id.postdate);
            expDate=view.findViewById(R.id.expDate);
            status=view.findViewById(R.id.status);
            title=view.findViewById(R.id.title);
            desc=view.findViewById(R.id.desc);


            title.setText(DataList.get(i).get("title").toString());
            desc.setText(DataList.get(i).get("requirement").toString());
            budget.setText(DataList.get(i).get("budget").toString());
            catagery.setText(DataList.get(i).get("category").toString());
            subCat.setText(DataList.get(i).get("subcategory").toString());
            postdate.setText(DataList.get(i).get("posted_date").toString());
            expDate.setText(DataList.get(i).get("expiry_date").toString());
            status.setText(DataList.get(i).get("status").toString());
            return view;
        }
    }




}
