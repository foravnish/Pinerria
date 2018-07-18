package application.pinerria.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import application.pinerria.Activities.MainActivity;
import application.pinerria.Activities.PostAdd;
import application.pinerria.Activities.PostReqAd;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;

import static android.text.Html.fromHtml;
import static application.pinerria.Activities.PostAdd.getDataColumn;
import static application.pinerria.Activities.PostAdd.imagename;

/**
 * A simple {@link Fragment} subclass.
 */
public class Help_SendEnquiry extends Fragment {


    public Help_SendEnquiry() {
        // Required empty public constructor
    }

    Spinner spiner;
    Button sendEnq,sendqu,fromAdmin;
    EditText subject,messages;
    String[] country = { "Log a Query", "Issues", "Suggestion" };
    String queryFor;
    List<HashMap<String,String>> DataList;
    List<HashMap<String,String>> DataListFromAdmin;
    Dialog dialog;
    GridView grigView;
    GridView grigView2;
    HelpAdapter helpAdapter;
    MessageFrmmAdapter messageFrmmAdapter;
    ImageView image1, image2, image3, image4, image5;
    ImageView img1, img2, img3, img4, img5;
    String filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4, filepath5, fileName5 = null;
    boolean isImage1 = false, isImage2 = false, isImage3 = false, isImage4 = false, isImage5 = false;
    ProgressDialog progress;
    Boolean isAnyImage=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_help__send_enquiry1, container, false);

        MainActivity.linerFilter.setVisibility(View.GONE);
        getActivity().setTitle("Help Desk");

        grigView=view.findViewById(R.id.grigView);
        sendqu=view.findViewById(R.id.sendqu);
        fromAdmin=view.findViewById(R.id.fromAdmin);

        DataList =new ArrayList<>();
        DataListFromAdmin =new ArrayList<>();
        new SendHelpQuiry().execute();

        sendqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSendEnq();
            }
        });

        fromAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFromAdmin();
                Util.showPgDialog(dialog);
//                Fragment fragment= new MesageFromAdmin();
//                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
//                FragmentTransaction ft=fragmentManager.beginTransaction();
//                ft.replace(R.id.container,fragment).addToBackStack(null).commit();

            }
        });


        return view;

    }

    private void DialogFromAdmin() {

        final Dialog dialog0 = new Dialog(getActivity());
        dialog0.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog0.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog0.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog0.setContentView(R.layout.fragment_help_from_admin);
        ImageView back_img = (ImageView) dialog0.findViewById(R.id.back_img);
        grigView2=dialog0.findViewById(R.id.grigView);
        new MessageFromAdmitApi().execute();

//        sendEnq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (chaeckValidation()){
//                    new HelpDeshApi(subject.getText().toString(),messages.getText().toString(),queryFor).execute();
//                }
//            }
//        });
        TextView title = (TextView) dialog0.findViewById(R.id.title);
        title.setText("Message From Admin");
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog0.cancel();

            }
        });
        dialog0.show();

    }

    private void DialogSendEnq() {

        final Dialog dialog0 = new Dialog(getActivity());
        dialog0.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog0.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog0.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog0.setContentView(R.layout.fragment_help__send_enquiry);
        ImageView back_img = (ImageView) dialog0.findViewById(R.id.back_img);

        spiner=(Spinner)dialog0.findViewById(R.id.spiner);

        subject=(EditText)dialog0.findViewById(R.id.subject);
        messages=(EditText)dialog0.findViewById(R.id.messages);

        image1 = dialog0.findViewById(R.id.image1);
        image2 = dialog0.findViewById(R.id.image2);
        image3 = dialog0.findViewById(R.id.image3);
        image4 = dialog0.findViewById(R.id.image4);
        image5 = dialog0.findViewById(R.id.image5);
        img1 = dialog0.findViewById(R.id.img1);
        img2 = dialog0.findViewById(R.id.img2);
        img3 = dialog0.findViewById(R.id.img3);
        img4 = dialog0.findViewById(R.id.img4);
        img5 = dialog0.findViewById(R.id.img5);

        sendEnq=dialog0.findViewById(R.id.sendEnq);


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


        sendEnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chaeckValidation()){

                    if (isAnyImage==true) {

                        new HelpDeshApi(subject.getText().toString(), messages.getText().toString(), queryFor, dialog0).execute();
                    }
                    else if (isAnyImage==false){
                        new HelpDeshApi2(subject.getText().toString(), messages.getText().toString(), queryFor, dialog0).execute();
                    }
                }
            }
        });

        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(aa);

        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                queryFor=spiner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        sendEnq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (chaeckValidation()){
//                    new HelpDeshApi(subject.getText().toString(),messages.getText().toString(),queryFor).execute();
//                }
//            }
//        });
        TextView title = (TextView) dialog0.findViewById(R.id.title);
        title.setText("Message To Admin");
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog0.cancel();

            }
        });


        dialog0.show();

    }

    private void imageOne(final int cam, final int gal) {

        final CharSequence[] items = {"Take from Camera", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                    Toast.makeText(getActivity(), "errorrr...", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setCancelable(true);
        builder.show();

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
            filepath1 = getPathFromUri(getActivity(), fileUri);
            fileName1 = imagename(getActivity(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
                image1.setImageBitmap(bitmap);
                image1.setVisibility(View.VISIBLE);
                img1.setVisibility(View.GONE);

            } catch (IOException e) {
                e.printStackTrace();
            }
//            check = 1;
            isImage1 = true;
            isImage2 = false;
            isImage3 =false;
            isImage4 = false;
            isImage5 = false;
            isAnyImage=true;

        } else if (i == 4) {
            Uri fileUri = data.getData();
            filepath2 = getPathFromUri(getActivity(), fileUri);
            fileName2 = imagename(getActivity(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
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
            isAnyImage=true;

        } else if (i == 6) {
            Uri fileUri = data.getData();
            filepath3 = getPathFromUri(getActivity(), fileUri);
            fileName3 = imagename(getActivity(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
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
            isAnyImage=true;
        } else if (i == 8) {
            Uri fileUri = data.getData();
            filepath4 = getPathFromUri(getActivity(), fileUri);
            fileName4 = imagename(getActivity(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
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
            isAnyImage=true;
        } else if (i == 10) {
            Uri fileUri = data.getData();
            filepath5 = getPathFromUri(getActivity(), fileUri);
            fileName5 = imagename(getActivity(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
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
            isAnyImage=true;
        } else if (i == 1) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image1.setImageBitmap(photo);
            image1.setVisibility(View.VISIBLE);
            img1.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getActivity(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            filepath1 = finalFile.toString();

            String filename1 = filepath1.substring(filepath1.lastIndexOf("/") + 1);
            fileName1 = filename1;
            Log.d("dgdfgdfhgdfhd", filepath1.toString());
            Log.d("dgdfgdfhgdfhd", filename1.toString());
//                 System.out.println(mImageCaptureUri);
            //check = 1;
            isImage1 = true;

            isImage2 = false;
            isImage3 =false;
            isImage4 = false;
            isImage5 = false;
            isAnyImage=true;
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
            Uri tempUri = getImageUri(getActivity(), photo);

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
            isAnyImage=true;
        } else if (i == 5) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image3.setImageBitmap(photo);
            image3.setVisibility(View.VISIBLE);
            img3.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getActivity(), photo);

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
            isAnyImage=true;
        } else if (i == 7) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image4.setImageBitmap(photo);
            image4.setVisibility(View.VISIBLE);
            img4.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getActivity(), photo);

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
            isAnyImage=true;
        } else if (i == 9) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image5.setImageBitmap(photo);
            image5.setVisibility(View.VISIBLE);
            img5.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getActivity(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            filepath5 = finalFile.toString();

            String filename5 = filepath5.substring(filepath5.lastIndexOf("/") + 1);
            fileName5 = filename5;
            Log.d("dgdfgdfhgdfhd", filepath5.toString());
            Log.d("dgdfgdfhgdfhd", filename5.toString());
            isImage5 = true;

            isImage1 = false;
            isImage3 =false;
            isImage4 = false;
            isImage2 = false;
            isAnyImage=true;
        }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
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



    private boolean chaeckValidation() {

        if (TextUtils.isEmpty(subject.getText().toString())){
            subject.setError("oops! subject is blank");
            subject.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(messages.getText().toString())) {
            messages.setError("oops! Message is blank");
            messages.requestFocus();
            return false;
        }
        return true;
    }

    class MessageFromAdmitApi extends AsyncTask<String,Void,String> {

        String productType;
        public MessageFromAdmitApi(){
            this.productType=productType;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


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

                        DataListFromAdmin.clear();
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

                            DataListFromAdmin.add(map);

                            messageFrmmAdapter = new MessageFrmmAdapter();
                            grigView2.setAdapter(messageFrmmAdapter);

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


    class SendHelpQuiry extends AsyncTask<String,Void,String> {

        String productType;
        public SendHelpQuiry(){
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
            String result =jsonParser.makeHttpRequest(Api.sentMessage,"POST",map);

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

                            helpAdapter = new HelpAdapter();
                            grigView.setAdapter(helpAdapter);

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



    class HelpDeshApi extends AsyncTask<String, Void, JSONObject> {

        String sub,mess,queryFor;
        Dialog dialog0;
        public HelpDeshApi(String sub, String mess, String queryFor, Dialog dialog0){

            this.sub=sub;
            this.mess=mess;
            this.queryFor=queryFor;
            this.dialog0= dialog0;

            progress = new ProgressDialog(getActivity());
            progress.setCancelable(false);
            progress.setTitle("Please wait...");
            progress.show();


        }

        @Override
        protected JSONObject doInBackground(String... strings) {

            JSONObject jsonObject = null;
            try {
                if (isImage1 == true) {
                    jsonObject = uploadImage(getActivity(), filepath1, fileName1,
                            sub, mess, queryFor);
                }
                if (isImage2 == true) {
                    jsonObject = uploadImage(getActivity(), filepath1, fileName1, filepath2, fileName2,
                            sub, mess, queryFor);
                }
                if (isImage3 == true) {
                    jsonObject = uploadImage(getActivity(), filepath1, fileName1, filepath2, fileName2, filepath3, fileName3,
                            sub, mess, queryFor);
                }
                if (isImage4 == true) {
                    jsonObject = uploadImage(getActivity(), filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4,
                            sub, mess, queryFor);
                }
                if (isImage5 == true) {
                    jsonObject = uploadImage(getActivity(), filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4, filepath5, fileName5,
                            sub, mess, queryFor);
                }
                if (jsonObject != null) {
                    return jsonObject;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

//            HashMap<String,String> map=new HashMap<>();
//
//
//            map.put("memberId", MyPrefrences.getUserID(getActivity()));
//            map.put("subject", sub);
//            map.put("message", mess);
//            map.put("queryFor", queryFor);
//
//            JSONParser jsonParser=new JSONParser();
//            String result =jsonParser.makeHttpRequest(Api.helpDesk,"POST",map);
//
//            return result;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            String message = "";
            String data = "";

            if (progress.isShowing())
                progress.dismiss();

            if (json != null) {


                if (json.optString("status").equalsIgnoreCase("success")) {
                    try {
//                        double amnt= Double.parseDouble(amounts.getText().toString().replace("₹ ",""));
//                        Log.d("rupee", String.valueOf(amnt));
//
//                        Util.errorDialog(getActivity(), json.getString("message"));
                        errorDialog(json.getString("message"));


                        //Toast.makeText(PostAdd.this, ""+jsonObject.optString("message") + message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("fdsdgdfsgdfgdf",e.toString());
                    }

                } else {
                    Toast.makeText(getActivity(), "Error " + json, Toast.LENGTH_SHORT).show();
                }
            }
        }


    }


    class HelpDeshApi2 extends AsyncTask<String,Void,String>{

        String sub,mess,queryFor;
        Dialog dialog;
        public HelpDeshApi2(String sub, String mess, String queryFor, Dialog dialog){

            this.sub=sub;
            this.mess=mess;
            this.queryFor=queryFor;
            this.dialog= dialog;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("memberId", MyPrefrences.getUserID(getActivity()));
            map.put("subject", sub);
            map.put("message", mess);
            map.put("queryFor", queryFor);

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.helpDesk,"POST",map);

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

                       errorDialog(jsonObject.getString("message"));

                    }

                }
            } catch (JSONException e) {
                Util.cancelPgDialog(dialog);
                Util.errorDialog(getActivity(),"Some Error! or Please connect to the internet.");
                e.printStackTrace();
            }
        }


    }

    private void errorDialog( String message) {


        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialogcustom);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView text = (TextView) dialog.findViewById(R.id.msg_txv);
        text.setText(fromHtml(message));
        Button ok = (Button) dialog.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("isflag", "0");
                startActivity(intent);

            }
        });
        dialog.show();
    }


    private JSONObject uploadImage(Context context, String filepath1, String fileName1,
                                   String sub, String msg, String query) {

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
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getActivity()))
                    .addFormDataPart("subject", sub)
                    .addFormDataPart("message", msg)
                    .addFormDataPart("queryFor", query)

                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))


                    .build();



            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url(Api.helpDesk)
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
            Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

    private JSONObject uploadImage(Context context, String filepath1, String fileName1, String filepath2, String fileName2,
                                   String sub, String msg, String query) {

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
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getActivity()))
                    .addFormDataPart("subject", sub)
                    .addFormDataPart("message", msg)
                    .addFormDataPart("queryFor", query)

                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .build();



            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url(Api.helpDesk)
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
            Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }


    private JSONObject uploadImage(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3,
                                   String sub, String msg, String query) {

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
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getActivity()))
                    .addFormDataPart("subject", sub)
                    .addFormDataPart("message", msg)
                    .addFormDataPart("queryFor", query)
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .build();



            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url(Api.helpDesk)
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
            Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }


    private JSONObject uploadImage(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3, String filepath4, String fileName4,
                                   String sub, String msg, String query) {

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
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getActivity()))
                    .addFormDataPart("subject", sub)
                    .addFormDataPart("message", msg)
                    .addFormDataPart("queryFor", query)
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .addFormDataPart("image4", fileName4, RequestBody.create(MEDIA_TYPE_PNG4, sourceFile4))
                    .build();



            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url(Api.helpDesk)
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
            Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

    private JSONObject uploadImage(Context context, String filepath1, String fileName1, String filepath2, String fileName2, String filepath3, String fileName3, String filepath4, String fileName4, String filepath5, String fileName5,
                                   String sub, String msg, String query) {

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
                    .addFormDataPart("memberId", MyPrefrences.getUserID(getActivity()))
                    .addFormDataPart("subject", sub)
                    .addFormDataPart("message", msg)
                    .addFormDataPart("queryFor", query)
                    .addFormDataPart("image1", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .addFormDataPart("image2", fileName2, RequestBody.create(MEDIA_TYPE_PNG2, sourceFile2))
                    .addFormDataPart("image3", fileName3, RequestBody.create(MEDIA_TYPE_PNG3, sourceFile3))
                    .addFormDataPart("image4", fileName4, RequestBody.create(MEDIA_TYPE_PNG4, sourceFile4))
                    .addFormDataPart("image5", fileName5, RequestBody.create(MEDIA_TYPE_PNG5, sourceFile5))
                    .build();



            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url(Api.helpDesk)
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
            Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
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
            return DataListFromAdmin.size();
        }

        @Override
        public Object getItem(int i) {
            return DataListFromAdmin.get(i);
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


            send_date.setText("Date: "+DataListFromAdmin.get(i).get("send_date"));
            message.setText("Message: "+DataListFromAdmin.get(i).get("message"));
            subject.setText("Subject: "+DataListFromAdmin.get(i).get("subject"));
            queryFor.setText("Query for: "+DataListFromAdmin.get(i).get("queryFor"));
            receiver_id.setText("To, "+DataListFromAdmin.get(i).get("receiver_id"));
            id.setText("Enquiry Id: "+DataListFromAdmin.get(i).get("id"));

            return view;
        }
    }


    class HelpAdapter extends BaseAdapter {
        LayoutInflater inflater;
        TextView id,receiver_id,queryFor,subject,message,send_date;
        ImageView image;
        HelpAdapter(){
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
