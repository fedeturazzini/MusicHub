package digitalhouse.android.View.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import digitalhouse.android.View.Fragments.FragmentRegister;
import digitalhouse.android.a0317moacns1c_01.R;

public class ActivityRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FragmentRegister fragmentRegister = new FragmentRegister();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.registerFragmentContainer, fragmentRegister);
        fragmentTransaction.commit();
    }
}
