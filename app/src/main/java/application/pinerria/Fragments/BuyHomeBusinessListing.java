package application.pinerria.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public class BuyHomeBusinessListing extends Fragment {


    public BuyHomeBusinessListing() {
        // Required empty public constructor
    }
    List<HashMap<String,String>> DataList;
    Adapter adapter;
    Dialog dialog;
    GridView grigView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_buy_home_business_listing, container, false);

        grigView=view.findViewById(R.id.grigView);

        getActivity().setTitle("Home Business Category");
        MainActivity.linerFilter.setVisibility(View.GONE);
        relative.setVisibility(View.VISIBLE);


        DataList =new ArrayList<>();

        new PostCategory().execute();

        grigView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Util.ChangeCat=true;
                Fragment fragment=new BuyProductListing();
                Bundle bundle=new Bundle();
                bundle.putString("id",DataList.get(i).get("id").toString());
                bundle.putString("type",DataList.get(i).get("category").toString());
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();


            }
        });

        return view;
    }


    class Adapter extends BaseAdapter {
        LayoutInflater inflater;
        TextView catName;
        ImageView catImg;
        Adapter(){
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

            view=inflater.inflate(R.layout.custonlistview_post_catagory,viewGroup,false);


            catName=view.findViewById(R.id.catName);
            catImg=view.findViewById(R.id.catImg);

            catName.setText(DataList.get(i).get("category").toString());
            Picasso.with(getActivity()).load(DataList.get(i).get("photo").toString())
                    .resize(600, 200).centerCrop().into(catImg);
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

            Log.d("dfsdfsdgsgsfg",MyPrefrences.getUserID(getActivity()));
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
