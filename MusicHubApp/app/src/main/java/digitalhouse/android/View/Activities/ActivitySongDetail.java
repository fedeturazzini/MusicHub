package digitalhouse.android.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import digitalhouse.android.View.Fragments.FragmentSongDetail;
import digitalhouse.android.a0317moacns1c_01.R;

public class ActivitySongDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Pongo icono a la action bar...
        ActionBar ab = getSupportActionBar();
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setLogo(R.drawable.logo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        FragmentSongDetail fragmentSongDetail = new FragmentSongDetail();
        fragmentSongDetail.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentSongDetailContainer, fragmentSongDetail);
        fragmentTransaction.commit();

    }
}
