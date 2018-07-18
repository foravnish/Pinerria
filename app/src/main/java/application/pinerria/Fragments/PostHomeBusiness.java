package application.pinerria.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import application.pinerria.Activities.LoginAct;
import application.pinerria.Activities.MainActivity;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;

import static application.pinerria.Activities.MainActivity.relative;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostHomeBusiness extends Fragment {


    public PostHomeBusiness() {
        // Required empty public constructor
    }
    List<HashMap<String,String>> DataList;
    Adapter adapter;
    Dialog  dialog;
    GridView grigView;
    String Home3,Home6;
//    SwipeRefreshLayout swiplay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_post_catagory, container, false);
        grigView=view.findViewById(R.id.grigView);
//        swiplay=view.findViewById(R.id.swiplay);

        getActivity().setTitle("Home Business Category");
        MainActivity.linerFilter.setVisibility(View.GONE);
        relative.setVisibility(View.VISIBLE);

        DataList =new ArrayList<>();

        new PostCategory().execute();


        JSONObject three= null;
        JSONObject six= null;

        try {
            three = new JSONObject(getArguments().getString("threemonths"));
            six = new JSONObject(getArguments().getString("sixmonths"));

            Log.d("dsfdsfsdfasdafds",getArguments().getString("threemonths"));
            Log.d("dsfdsfsdfasdafds",getArguments().getString("sixmonths"));

             Home3=three.optString("Home");
             Home6=six.optString("Home");
        } catch (JSONException e) {
            e.printStackTrace();
        }




        grigView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Fragment fragment=new SelectArea();
                Bundle bundle=new Bundle();
                bundle.putString("Cat_id","3");
                bundle.putString("threemonths", Home3);
                bundle.putString("sixmonths", Home6);
                bundle.putString("heading", DataList.get(i).get("category"));
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();

            }
        });
        return view;
    }





    class Adapter extends BaseAdapter{
        LayoutInflater inflater;
        TextView catName;
        ImageView catImg;
        Adapter(){
            try {
                inflater=(LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

            view=inflater.inflate(R.layout.custonlistview_post_catagory,viewGroup,false);


            catName=view.findViewById(R.id.catName);
            catImg=view.findViewById(R.id.catImg);

            catName.setText(DataList.get(i).get("category").toString());
            Picasso.with(getActivity()).load(DataList.get(i).get("photo").toString()).into(catImg);


            return view;
        }
    }

    class PostCategory extends AsyncTask<String,Void,String> {


        public PostCategory(){


            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("typeId", "3");
            map.put("userId", MyPrefrences.getUserID(getActivity()));
//            map.put("password", password.toString());

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.BuyCategory,"POST",map);

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
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);


                                HashMap<String, String> map = new HashMap<>();

                                map.put("id", jsonObject1.optString("id"));
                                map.put("cat_type", jsonObject1.optString("cat_type"));
                                map.put("category", jsonObject1.optString("category"));
                                map.put("photo", jsonObject1.optString("photo"));
                                map.put("status", jsonObject1.optString("status"));

                                DataList.add(map);

                            adapter = new Adapter();
                            grigView.setAdapter(adapter);

//                            swiplay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                                @Override
//                                public void onRefresh() {
////                                    shuffle();
//                                    adapter = new Adapter();
//
//                                    grigView.setAdapter(adapter);
//                                    swiplay.setRefreshing(false);
//                                }
//                            });

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


}
