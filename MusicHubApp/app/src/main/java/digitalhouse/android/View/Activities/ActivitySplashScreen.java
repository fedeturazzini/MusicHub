package digitalhouse.android.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import digitalhouse.android.a0317moacns1c_01.R;

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        setContentView(R.layout.activity_splash_screen);

        //nuevo handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //nuevo intent
                Intent intent = new Intent(ActivitySplashScreen.this, ActivityMain.class);
                startActivity(intent);
                //evitar que se vuelve a ver esta activity si el user vuelve para atras
                ActivitySplashScreen.this.finish();
            }
        }, 1500);
    }
}
