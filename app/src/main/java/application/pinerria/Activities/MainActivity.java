package application.pinerria.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

import application.pinerria.Fragments.AboutUs;
import application.pinerria.Fragments.FAQs;
import application.pinerria.Fragments.Help_SendEnquiry;
import application.pinerria.Fragments.PostHomeBusiness;
import application.pinerria.Fragments.Changepassword;
import application.pinerria.Fragments.ContactUs;
import application.pinerria.Fragments.HomeFragment;
import application.pinerria.Fragments.Deshboard;
import application.pinerria.Fragments.Profile;
import application.pinerria.Fragments.SelectBuyCategory;
import application.pinerria.Fragments.SelectCatagory;
import application.pinerria.Fragments.ShowRequirement;
import application.pinerria.Fragments.TermAndCondition;
import application.pinerria.R;
import application.pinerria.Util.Api;
import application.pinerria.Util.MyPrefrences;
import application.pinerria.Util.Util;
import application.pinerria.connection.JSONParser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment = null;
    Fragment fragment2 = null;
    public static RelativeLayout relative;
    public static TextView reqirement;
    public static TextView filter;
    public static LinearLayout linerFilter;
    Dialog dialog;
    public JSONObject threemonths,sixmonths;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relative=findViewById(R.id.relative);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Welcome to Pinerria");






        linerFilter=findViewById(R.id.linerFilter);
        reqirement=findViewById(R.id.reqirement);
        filter=findViewById(R.id.filter);

        MainActivity.linerFilter.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView1.getHeaderView(0);
        TextView name=(TextView)header.findViewById(R.id.name);
        TextView email=(TextView)header.findViewById(R.id.email);
        name.setText(MyPrefrences.getName(getApplicationContext()).toUpperCase());
        email.setText(MyPrefrences.getEmilId(getApplicationContext()));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final TextView sell=findViewById(R.id.sell);
        final TextView buy=findViewById(R.id.buy);
        final TextView postAd=findViewById(R.id.postAd);
        final TextView account=findViewById(R.id.account);
        final TextView help=findViewById(R.id.help);

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new SelectCatagory();
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
                MyPrefrences.setHomeType(getApplicationContext(),"sell");
//                sell.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.buy2) );
                sell.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sale3, 0, 0);
                postAd.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ads2, 0, 0);
                buy.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.buy2, 0, 0);
                account.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.accout2, 0, 0);
                help.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.help2, 0, 0);

            }
        });

        postAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment fragment=new Ho();
//                FragmentManager fm=getSupportFragmentManager();
//                FragmentTransaction ft=fm.beginTransaction();
//                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
                new Packages("home").execute();
                MyPrefrences.setHomeType(MainActivity.this,"home");

                sell.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sell2, 0, 0);
                postAd.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ads3, 0, 0);
                buy.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.buy2, 0, 0);
                account.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.accout2, 0, 0);
                help.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.help2, 0, 0);
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment=new SelectBuyCategory();
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();

                sell.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sell2, 0, 0);
                postAd.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ads2, 0, 0);
                buy.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.buy3, 0, 0);
                account.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.accout2, 0, 0);
                help.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.help2, 0, 0);

            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new Deshboard();
                Bundle  bundle=new Bundle();
                bundle.putString("type","no");
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
                fragment.setArguments(bundle);
                sell.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sell2, 0, 0);
                postAd.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ads2, 0, 0);
                buy.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.buy2, 0, 0);
                account.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.accout3, 0, 0);
                help.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.help2, 0, 0);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new Help_SendEnquiry();
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
                sell.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sell2, 0, 0);
                postAd.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ads2, 0, 0);
                buy.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.buy2, 0, 0);
                account.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.accout2, 0, 0);
                help.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.help3, 0, 0);
            }
        });


        if (getIntent().getStringExtra("isflag").equalsIgnoreCase("1")){
            Fragment fragment= new Deshboard();
            Bundle  bundle=new Bundle();
            bundle.putString("type","yes");
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction();
            fragment.setArguments(bundle);
            ft.replace(R.id.container,fragment).commit();

        }
        else if (getIntent().getStringExtra("isflag").equalsIgnoreCase("0")) {
            Fragment fragment = new HomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container, fragment).commit();
        }
        else if (getIntent().getStringExtra("isflag").equalsIgnoreCase("sell")) {
            Fragment fragment = new SelectCatagory();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container, fragment).commit();
            MyPrefrences.setHomeType(getApplicationContext(),"sell");
        }
        else if (getIntent().getStringExtra("isflag").equalsIgnoreCase("home")) {
//            Fragment fragment = new PostHomeBusiness();
//            FragmentManager manager = getSupportFragmentManager();
//            FragmentTransaction ft = manager.beginTransaction();
//            ft.replace(R.id.container, fragment).commit();
            new Packages("home").execute();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


//
//        if (getSupportFragmentManager().findFragmentByTag("fragBack") != null) {
//
//        }
//        else {
//            super.onBackPressed();
//            return;
//        }
//        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
//
//            Fragment frag = getSupportFragmentManager().findFragmentByTag("fragBack");
//            FragmentTransaction transac = getSupportFragmentManager().beginTransaction().remove(frag);
//            transac.commit();
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
//            fragment2= new HomeFragment();
            fragment= new Profile();
//            replace2();
            replace();

        } else if (id == R.id.myAccount) {
            fragment= new Deshboard();
            Bundle  bundle=new Bundle();
            bundle.putString("type","no");
            fragment.setArguments(bundle);

            replace();
        } else if (id == R.id.postAd) {
            fragment= new HomeFragment();
            replace();

        } else if (id == R.id.viewAd) {

        } else if (id == R.id.showReq) {
            fragment= new ShowRequirement();
            replace();
        } else if (id == R.id.about) {
            fragment= new AboutUs();
            replace();
        } else if (id == R.id.faq) {
            fragment= new FAQs();
            replace();


        } else if (id == R.id.tnc) {
            fragment= new TermAndCondition();
            replace();


        } else if (id == R.id.postReq) {
            Intent intent=new Intent(MainActivity.this,PostReqAd.class);
            startActivity(intent);

        } else if (id == R.id.help) {
            fragment= new Help_SendEnquiry();
            replace();

        } else if (id == R.id.contact) {
            fragment= new ContactUs();
            replace();
        } else if (id == R.id.chngPass) {
            fragment= new Changepassword();
            replace();
        } else if (id == R.id.logout) {
            MyPrefrences.resetPrefrences(MainActivity.this);
            startActivity(new Intent(getApplicationContext(),LoginAct.class));
            finishAffinity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replace() {

        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction ft=manager.beginTransaction();
        ft.replace(R.id.container,fragment).addToBackStack("fragBack").commit();
    }



    class Packages extends AsyncTask<String,Void,String> {

        String car;

        public Packages(String car){
            this.car=car;
            dialog=new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            //map.put("typeId", getArguments().getString("Cat_id"));

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.packageCharge,"POST",map);

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
                        JSONObject jsonObject1=jsonArray.getJSONObject(0);
                        threemonths=jsonObject1.optJSONObject("three_month_charges");
                        sixmonths=jsonObject1.optJSONObject("six_month_charges");

                        Log.d("dsdfjdshfsdf",threemonths.toString());
                        Log.d("sixmonths",threemonths.toString());


                        Fragment fragment=new PostHomeBusiness();
                        Bundle bundle=new Bundle();
                        bundle.putString("threemonths", threemonths.toString());
                        bundle.putString("sixmonths", sixmonths.toString());
                        FragmentManager fm=getSupportFragmentManager();
                        FragmentTransaction ft=fm.beginTransaction();
                        fragment.setArguments(bundle);
                        ft.replace(R.id.container,fragment).addToBackStack(null).commit();


                    }
                    else {
                        Util.errorDialog(MainActivity.this,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(MainActivity.this,"Some Error! or Please connect to the internet.");
                e.printStackTrace();
            }
        }

    }


}
