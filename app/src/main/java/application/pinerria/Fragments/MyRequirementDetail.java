package application.pinerria.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.pinerria.Activities.MainActivity;
import application.pinerria.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyRequirementDetail extends Fragment {


    public MyRequirementDetail() {
        // Required empty public constructor
    }

    Dialog dialog4;
    TextView postId,title,manufect,milage,fuelType,mDate,ageOfProd,price,lastWeek,visitors,used,descreption,expire;
    TextView contactDetail,emailId,Mobile,nameCont,Subcat,cat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_my_requirement_detail, container, false);
        getActivity().setTitle("");
        MainActivity.linerFilter.setVisibility(View.INVISIBLE);

        Log.d("dsfsdfgsdgsfgsd",getArguments().getString("DataList"));

        postId=view.findViewById(R.id.postId);
        title=view.findViewById(R.id.title);
        nameCont=view.findViewById(R.id.nameCont);


        //ageOfProd=view.findViewById(R.id.ageOfProd);
        price=view.findViewById(R.id.price);

        cat=view.findViewById(R.id.cat);
        Subcat=view.findViewById(R.id.Subcat);
        descreption=view.findViewById(R.id.descreption);
        expire=view.findViewById(R.id.expire);
        contactDetail=view.findViewById(R.id.contactDetail);
        emailId=view.findViewById(R.id.emailId);
        Mobile=view.findViewById(R.id.Mobile);

        try {
//            JSONArray jsonArray=new JSONArray(getArguments().getString("DataList"));

           // for (int i=0;i<jsonArray.length();i++){
                JSONObject  jsonObject=new JSONObject(getArguments().getString("DataList"));

                getActivity().setTitle(jsonObject.optString("title"));

                cat.setText(jsonObject.optString("category"));
                Subcat.setText(jsonObject.optString("subcategory"));
                postId.setText(jsonObject.optString("id"));
                title.setText(jsonObject.optString("title"));
                Mobile.setText(jsonObject.optString("mobile"));
                emailId.setText(jsonObject.optString("email"));
                nameCont.setText(jsonObject.optString("contact_person"));
                descreption.setText(jsonObject.optString("requirement"));
                expire.setText(jsonObject.optString("expiry_date"));
            //}

        } catch (JSONException e) {
            e.printStackTrace();
        }


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
                                "mailto",emailId.getText().toString(), null));
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

                                Log.d("fsdfgsdgfsdg",Mobile.getText().toString());
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + Mobile.getText().toString()));
                                startActivity(callIntent);
                            }
                            else {

                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + Mobile.getText().toString()));
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
        return view;
    }

}
