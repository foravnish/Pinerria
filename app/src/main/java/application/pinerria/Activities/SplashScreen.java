package application.pinerria.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import application.pinerria.R;
import application.pinerria.Util.MyPrefrences;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread background = new Thread() {
            public void run() {
                try {

                    sleep(3*1000);

                    if (MyPrefrences.getUserLogin(SplashScreen.this)==true){
                        Intent intent =new Intent(SplashScreen.this, MainActivity.class);
                        intent.putExtra("isflag","0");
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(SplashScreen.this, LoginAct.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();


    }
}
