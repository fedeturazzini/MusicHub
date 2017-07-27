package digitalhouse.android.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.twitter.sdk.android.core.Twitter;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import digitalhouse.android.View.Fragments.FragmentLogin;
import digitalhouse.android.View.Fragments.FragmentRegister;
import digitalhouse.android.a0317moacns1c_01.R;

public class ActivityLogin extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;
    private FragmentLogin fragmentLogin;
    private FragmentRegister fragmentRegister;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Twitter.initialize(this);
        setContentView(R.layout.activity_register_or_login);

        fragmentLogin = new FragmentLogin();

        loadFragment(fragmentLogin);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Pass the activity result to the login button.
//        loginButton.onActivityResult(requestCode, resultCode, data);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the fragment, which will then pass the result to the login
        // button.
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.registerLogin);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void loadFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.registerLogin, fragment);
        fragmentTransaction.commit();
    }
}
