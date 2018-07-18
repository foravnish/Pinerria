package application.pinerria.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class MesageFromAdmin extends Fragment {


    public MesageFromAdmin() {
        // Required empty public constructor
    }

    Dialog dialog;
    GridView gridView;
    List<HashMap<String,String>> DataList;
    MessageFrmmAdapter messageFrmmAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mesage_from_admin, container, false);
        gridView=view.findViewById(R.id.grigView);
        DataList =new ArrayList<>();
        new MessageFromAdmitApi().execute();
        getActivity().setTitle("Message From Admin");

        return view;
    }

    class MessageFromAdmitApi extends AsyncTask<String,Void,String> {

        String productType;
        public MessageFromAdmitApi(){
            this.productType=productType;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("memberId", MyPrefrences.getUserID(getActivity()));

//            map.put("password", password.toString());

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.inboxMessage,"POST",map);

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
                            map.put("sender_id", jsonObject1.optString("sender_id"));
                            map.put("receiver_id", jsonObject1.optString("receiver_id"));
                            map.put("message_id", jsonObject1.optString("message_id"));
                            map.put("queryFor", jsonObject1.optString("queryFor"));
                            map.put("subject", jsonObject1.optString("subject"));
                            map.put("message", jsonObject1.optString("message"));
                            map.put("delete_status", jsonObject1.optString("delete_status"));
                            map.put("send_date", jsonObject1.optString("send_date"));
                            map.put("ip", jsonObject1.optString("ip"));
                            map.put("status", jsonObject1.optString("status"));

                            DataList.add(map);

                            messageFrmmAdapter = new MessageFrmmAdapter();
                            gridView.setAdapter(messageFrmmAdapter);

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

    class MessageFrmmAdapter extends BaseAdapter {
        LayoutInflater inflater;
        TextView id,receiver_id,queryFor,subject,message,send_date;
        ImageView image;
        MessageFrmmAdapter(){
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

            view=inflater.inflate(R.layout.custonlistview_enquiry,viewGroup,false);

            send_date=view.findViewById(R.id.send_date);
            message=view.findViewById(R.id.message);
            subject=view.findViewById(R.id.subject);
            queryFor=view.findViewById(R.id.queryFor);
            receiver_id=view.findViewById(R.id.receiver_id);
            id=view.findViewById(R.id.id);


            send_date.setText("Date: "+DataList.get(i).get("send_date"));
            message.setText("Message: "+DataList.get(i).get("message"));
            subject.setText("Subject: "+DataList.get(i).get("subject"));
            queryFor.setText("Query for: "+DataList.get(i).get("queryFor"));
            receiver_id.setText("To, "+DataList.get(i).get("receiver_id"));
            id.setText("Enquiry Id: "+DataList.get(i).get("id"));

            return view;
        }
    }




}
