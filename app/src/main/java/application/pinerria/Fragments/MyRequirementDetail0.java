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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
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
public class MyRequirementDetail0 extends Fragment {


    public MyRequirementDetail0() {
        // Required empty public constructor
    }
    List<HashMap<String,String>> DataList;
    GridView grigView;
    Dialog dialog;
    CarAdapter caradapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_requirement_detail0, container, false);
        getActivity().setTitle(""+getArguments().getString("title"));
        MainActivity.linerFilter.setVisibility(View.INVISIBLE);
        grigView=view.findViewById(R.id.grigView);
        DataList=new ArrayList<>();

        Log.d("sdfsdfgsdfgdsfhgdfh",getArguments().getString("id"));
        Log.d("sdfsdfgsdfgdsfhgdfh",getArguments().getString("date"));

        new ReqListing().execute();



        return view;
    }


    class ReqListing extends AsyncTask<String,Void,String> {


        public ReqListing(){

            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();


            map.put("categoryId", getArguments().getString("id"));
            map.put("postedDate", getArguments().getString("date"));
            //map.put("userId", MyPrefrences.getUserID(getActivity()));
//
            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.adsRelatedToMyReq,"POST",map);

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
                            map.put("cat_id", jsonObject1.optString("cat_id"));
                            map.put("subcat_id", jsonObject1.optString("subcat_id"));
                            map.put("post_title", jsonObject1.optString("post_title"));

                            map.put("description", jsonObject1.optString("description"));
                            map.put("mobile", jsonObject1.optString("mobile"));
                            map.put("email", jsonObject1.optString("email"));
                            map.put("posted_date", jsonObject1.optString("posted_date"));
                            map.put("approve_date", jsonObject1.optString("approve_date"));
                            map.put("expiry_date", jsonObject1.optString("expiry_date"));
                            map.put("image1", jsonObject1.optString("image1"));

//                            map.put("fname", jsonObject1.optString("fname"));
//                            map.put("contact", jsonObject1.optString("contact"));
//                            map.put("property_city", jsonObject1.optString("property_city"));
//                            map.put("property_state", jsonObject1.optString("property_state"));
//                            map.put("description", jsonObject1.optString("description"));

                            caradapter = new CarAdapter();
                            grigView.setAdapter(caradapter);
                            DataList.add(map);

                            grigView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Fragment fragment=new BuyProductListDetail();

                                    Bundle bundle=new Bundle();
                                    try {
                                        bundle.putString("DataList",jsonArray.get(i).toString());
                                        bundle.putString("category",DataList.get(i).get("post_title").toLowerCase());
                                        bundle.putString("type", "");
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



    class CarAdapter extends BaseAdapter {
        LayoutInflater inflater;
        TextView title,postingId,months,price,fueltype,kmsDone,manufactre;
        ImageView image;
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

            manufactre=view.findViewById(R.id.manufactre);

            title.setText(DataList.get(i).get("post_title").toString());
            postingId.setText(DataList.get(i).get("id").toString());

            manufactre.setText(DataList.get(i).get("description").toString());


            Picasso.with(getActivity()).load(DataList.get(i).get("image1").toString())
                    .resize(250, 150).centerCrop().into(image);
            return view;
        }
    }


}
