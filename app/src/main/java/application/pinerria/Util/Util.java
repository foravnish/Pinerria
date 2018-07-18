package application.pinerria.Util;

import android.app.Dialog;
import android.content.Context;
import android.database.CrossProcessCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.pinerria.Activities.PaymentSuccessActivity;
import application.pinerria.Fragments.BuyProductListDetail;
import application.pinerria.R;
import me.relex.circleindicator.CircleIndicator;
import uk.co.senab.photoview.PhotoViewAttacher;

import static android.icu.util.MeasureUnit.BYTE;
import static android.text.Html.fromHtml;

/**
 * Created by Andriod Avnish on 12-Feb-18.
 */

public class Util {

    public static boolean  ChangeCat=true;

    public  static JSONObject jsonObject;
    public  static ViewPager viewpager;
    public  static List<HashMap<String,String>> ImgData;
    public  static CustomPagerAdapter customPagerAdapter;
    public  static CircleIndicator indicator ;
    public static void showInfoAlertDialog(final Context context, String title, String details) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.info);
        dialog.setCancelable(true);
        Button ok = (Button) dialog.findViewById(R.id.ok);
        LinearLayout fp_lin = (LinearLayout) dialog.findViewById(R.id.fp_lin);
        fp_lin.setVisibility(View.GONE);
        TextView info = (TextView) dialog.findViewById(R.id.info);
        info.setVisibility(View.GONE);
        TextView titles = (TextView) dialog.findViewById(R.id.title);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ok.getLayoutParams();
        if (title.equalsIgnoreCase("FindPlayer Info")) {
            fp_lin.setVisibility(View.VISIBLE);
            params.addRule(RelativeLayout.BELOW, R.id.fp_lin);

        } else {
            info.setVisibility(View.VISIBLE);
            params.addRule(RelativeLayout.BELOW, R.id.info);

        }
        titles.setText(title);
        info.setText(details);
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }



    public static void showFullImageDialog(Context context, String imageurl, int pos,String titlename) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.showfullimage);
        ImageView back_img = (ImageView) dialog.findViewById(R.id.back_img);
//        ImageView fact_image = (ImageView) dialog.findViewById(R.id.fact_image);
        viewpager = (ViewPager) dialog.findViewById(R.id.viewpager);
        indicator = (CircleIndicator)dialog.findViewById(R.id.indicator);
        ImgData =new ArrayList<>();
        customPagerAdapter=new CustomPagerAdapter(context);
        String Prev_Response=imageurl;

        try {
            jsonObject=new JSONObject(Prev_Response);

            JSONArray jsonArray=jsonObject.getJSONArray("finalImage");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                Log.d("gbfhfghfgh",jsonObject1.toString());
                HashMap<String,String> map=new HashMap<>();
                map.put("img",jsonObject1.optString("image"));
                ImgData.add(map);
                viewpager.setAdapter(customPagerAdapter);
                viewpager.setCurrentItem(pos);
                indicator.setViewPager(viewpager);
              //  indicator.setViewPager(viewPager);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Util.showImage(context, imageurl, fact_image);
//        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(fact_image);
//        //photoViewAttacher.onDrag(2,2);
//        photoViewAttacher.update();
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText(titlename);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    public static void showImage(Context context, String url, ImageView imageView) {
        if (!url.isEmpty() && url != null) {
            Picasso.with(context).load(url).placeholder(R.color.colorPrimary).error(R.color.gray2).resize(250, 150).centerCrop().into(imageView);
        }
    }


    ///////////////show progress dialog for Async Task
    public static void showPgDialog(Dialog dialog) {

        dialog.setContentView(R.layout.dialogprogress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


//        progressDialog.setMessage("Please Wait....");
//        progressDialog.show();
    }

    public static void cancelPgDialog(Dialog dialog) {
//        if (progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

    }
    public static void errorDialog(Context context, String message) {
        final Dialog dialog = new Dialog(context);
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
            }
        });
        dialog.show();

    }

    public static class CustomPagerAdapter extends PagerAdapter {
        LayoutInflater layoutInflater;
        Button download;
        Context  context;
        Drawable drawable;
        byte[] BYTE;
        ByteArrayOutputStream byteArrayOutputStream;
        public CustomPagerAdapter(Context context) {
            this.context=context;
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

            ZoomageView networkImageView;
//            ImageView networkImageView;

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.custom_photogallery2, container, false);
            networkImageView = (ZoomageView) view.findViewById(R.id.networkImageView);

            Log.d("fgdfgdfghsg",ImgData.get(position).get("img").toString());

//            ImageLoader imageLoader=AppController.getInstance().getImageLoader();
//            networkImageView.setImageUrl(ImgData.get(position).get("img"),imageLoader);

//            Picasso.with(context).load(ImgData.get(position).get("img")).transform(new CropSquareTransformation()).into(networkImageView);
            Picasso.with(context).load(ImgData.get(position).get("img")).into(networkImageView);
//

//            byteArrayOutputStream=new ByteArrayOutputStream();
//
////            drawable = context.getResources().getDrawable(R.drawable.sdf);
////            Bitmap bitmap1 = ((BitmapDrawable)drawable).getBitmap();
//
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            Bitmap bitmap1 = BitmapFactory.decodeFile(ImgData.get(position).get("img"), options);
//
//            bitmap1.compress(Bitmap.CompressFormat.JPEG,40,byteArrayOutputStream);
//            BYTE = byteArrayOutputStream.toByteArray();
//            Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);
//            networkImageView.setImageBitmap(bitmap2);


//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            Bitmap bitmap = BitmapFactory.decodeFile(ImgData.get(position).get("img"), options);
//
//            CropSquareTransformation  ob=new CropSquareTransformation();
//            //ob.transform(bitmap);
//            networkImageView.setImageBitmap(ob.transform(bitmap));



//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            Bitmap bitmap = BitmapFactory.decodeFile(ImgData.get(position).get("img"), options);
////
//            int  size=Math.min(bitmap.getWidth(),bitmap.getHeight());
//            int mwidth=(bitmap.getWidth()-size)/2;
//            int mhieght=(bitmap.getHeight()-size)/2;
//
//            Bitmap bitmap1=Bitmap.createBitmap(bitmap,mwidth,mhieght,size,size);
//            if (bitmap1!=bitmap){
//                bitmap.recycle();
//            }
//            networkImageView.setImageBitmap(bitmap1);


//            Bitmap scaledBitmap = scaleDown(bitmap, 200, true);
//
//            networkImageView.setImageBitmap(scaledBitmap);

//            Picasso.with(context).load(ImgData.get(position).get("img")).resize(200, 100).centerCrop().into(networkImageView);

//            Util.showImage(context, ImgData.get(position).get("img"), networkImageView);
//            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(networkImageView);
//
////            photoViewAttacher.onDrag(2,2);
//            photoViewAttacher.update();


//            networkImageView.setOnClickListener(new View.OnClickListener() {
//                @O    verride
//                public void onClick(View view) {
//                    Log.d("gfhbgfgjhfgjfh",ImgData.get(position).get("img"));
//
////                    Util.showFullImageDialog(getActivity(), ImgData.get(position).get("img"), getArguments().getString("category"));
//                    Util.showFullImageDialog(context, context.getArguments().getString("DataList"), getArguments().getString("category"));
//
//                }
//            });
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

        public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                       boolean filter) {
            float ratio = Math.min(
                    (float) maxImageSize / realImage.getWidth(),
                    (float) maxImageSize / realImage.getHeight());
            int width = Math.round((float) ratio * realImage.getWidth());
            int height = Math.round((float) ratio * realImage.getHeight());

            Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                    height, filter);
            return newBitmap;
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

    static class CropSquareTransformation implements Transformation {
        private int mWidth;
        private int mHeight;
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            mWidth = (source.getWidth() - size) / 2;
            mHeight = (source.getHeight() - size) / 2;
            Bitmap bitmap = Bitmap.createBitmap(source, mWidth, mHeight, size, size);
            if (bitmap != source) {
                source.recycle();
            }
            return bitmap;
        }
        @Override public String key() {
            return "CropSquareTransformation(width=" + mWidth + ", height=" + mHeight + ")";
        }
    }


}
