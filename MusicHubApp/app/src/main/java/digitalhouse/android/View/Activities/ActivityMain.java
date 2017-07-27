package digitalhouse.android.View.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import digitalhouse.android.Model.POJO.Album;
import digitalhouse.android.Model.POJO.Genre;
import digitalhouse.android.View.Fragments.FragmentAlbumList;
import digitalhouse.android.View.Fragments.FragmentFavorites;
import digitalhouse.android.View.Fragments.FragmentGenreList;
import digitalhouse.android.View.Fragments.FragmentListMusic;
import digitalhouse.android.View.Fragments.FragmentMyAccount;
import digitalhouse.android.View.Fragments.FragmentSongViewPager;
import digitalhouse.android.a0317moacns1c_01.R;


public class ActivityMain extends AppCompatActivity implements FragmentListMusic.NotifyChanges, FragmentFavorites.NotifyChangesFavorites, FragmentGenreList.ActivityNotificator, FragmentAlbumList.ActivityChangeNotificator, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener, MenuItemCompat.OnActionExpandListener {


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    //FireBase auth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Uri photoUrl;
    private String userEmail;
    private String userDisplayName;

    private TextView textoBuscado;

    //NAV
    private NavigationView navigationView;

    //Inicializo TODOS LOS FRAGMENT A UTLIZAR
    private FragmentGenreList fragmentGenreList = new FragmentGenreList();
    private FragmentListMusic fragmentListMusic = new FragmentListMusic();
    private FragmentFavorites fragmentFavorites = new FragmentFavorites();
    private FragmentMyAccount fragmentMyAccount = new FragmentMyAccount();
    private FragmentAlbumList fragmentAlbumList = new FragmentAlbumList();
    private FragmentSongViewPager fragmentSongViewPager = new FragmentSongViewPager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoBuscado = (TextView) findViewById(R.id.textoBuscado);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        // USER


        //Creo el BottomNavigattionView
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.botton_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.inicioItem:
                        loadFragment(fragmentGenreList, true);
                        break;
                    case R.id.favoriteItem:
                        loadFragment(fragmentFavorites, true);
                        break;

                }
                return true;
            }
        });

        loadFragment(fragmentGenreList, true);

        // Navigation drawer y listener para icono de abrir cerrar
        drawerLayout = (DrawerLayout) findViewById(R.id.parentDrawerID);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //configuraciones del actionbar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setLogo(R.drawable.logo);

        //navigationv View
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.menu_seccion_myprofile:
                            if (mAuth.getCurrentUser() != null){
                                loadFragment(fragmentMyAccount, true);
                            } else {
                                Intent intent1 = new Intent(ActivityMain.this, ActivityLogin.class);
                                startActivity(intent1);
                            }

                            break;
                        case R.id.menu_seccion_mymusic:
                            loadFragment(fragmentFavorites, false);
                            break;
//                    case R.id.menu_seccion_settings:
//                        // Implementar
//                        break;
//                    case R.id.menu_seccion_help:
//                        loadFragment(fragmentHelp, true);
//                        break;
//                    case R.id.menu_seccion_signout:
//                        finish();
//                        Intent intent = new Intent(Intent.ACTION_MAIN);
//                        intent.addCategory(Intent.CATEGORY_HOME);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        break;
                        case R.id.menu_seccion_register:
                            if (mAuth.getCurrentUser() == null){
                                Intent intent1 = new Intent(ActivityMain.this, ActivityLogin.class);
                                signInSiagnOut(mAuth);
                                headerNavigationUser(mAuth);
                                startActivity(intent1);

                            } else {
                                FirebaseAuth.getInstance().signOut();
                                signInSiagnOut(mAuth);
                                headerNavigationUser(mAuth);
                            }


                }
                drawerLayout.closeDrawers();
                return false;
            }
        });

        // Listener para verificar si estoy logeado o no y asi cambiar el nav drawer
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                signInSiagnOut(firebaseAuth);
                verificarUsuario(firebaseAuth);
                headerNavigationUser(firebaseAuth);
            }
        });

    }

    //Navigation Botton
    public boolean onMenuItemActionExpand(MenuItem item) {
        Toast.makeText(getApplicationContext(), "Buscador activado", Toast.LENGTH_SHORT).show();
        return true;
    }


    public boolean onMenuItemActionCollapse(MenuItem item) {
        Toast.makeText(getApplicationContext(), "Buscador desactivado", Toast.LENGTH_SHORT).show();
        return true;
    }


    public boolean onQueryTextSubmit(String s) {

        return false;
    }


    public boolean onQueryTextChange(String s) {

        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

//        MenuItem searchItem = menu.findItem(R.id.buscarItem);
//
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setOnQueryTextListener(this);
//
//
//        MenuItemCompat.setOnActionExpandListener(searchItem, this);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void sendSong(Integer position, String urlRequest, String albumImageURL, Integer fragmentID, ImageView imageMusic) {
        Bundle bundle = new Bundle();

        bundle.putInt(FragmentSongViewPager.KEY_POSITION, position);
        bundle.putString(FragmentSongViewPager.KEY_URL_REQUEST, urlRequest);
        bundle.putString(FragmentSongViewPager.KEY_ALBUM_IMAGE_URL, albumImageURL);
        bundle.putInt(FragmentSongViewPager.KEY_FRAGMENT_ID, fragmentID);

        FragmentSongViewPager fragmentSongViewPager = new FragmentSongViewPager();

        fragmentSongViewPager.setArguments(bundle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            bundle.putString("Transition", imageMusic.getTransitionName());
//            fragmentSongViewPager.setSharedElementEnterTransition(new ImageTransition());
//            fragmentSongViewPager.setEnterTransition(new android.transition.Fade());
//            fragmentListMusic.setExitTransition(new android.transition.Fade());
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.contenedorLista, fragmentSongViewPager).addToBackStack(null).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedorLista, fragmentSongViewPager).addToBackStack(null)
                    .commit();
        }
          loadFragment(fragmentSongViewPager, true);
    }



    @Override
    public void getGenre(Genre genre) {
        Bundle bundle = new Bundle();

        bundle.putInt(FragmentAlbumList.KEY_GENRE_ID, genre.getGenreID());

        bundle.putString(FragmentAlbumList.KEY_GENRE_NAME, genre.getGenreName());
        bundle.putString(FragmentAlbumList.KEY_GENERE_PICTURE_URL, genre.getGenrePictureResource());

        fragmentAlbumList = new FragmentAlbumList();

        fragmentAlbumList.setArguments(bundle);
        loadFragment(fragmentAlbumList,true);
    }

    @Override
    public void notifyChanges(Album album) {
        Bundle bundle = new Bundle();

        bundle.putString(FragmentListMusic.TRACKLIST_URL, album.getTracklistURL());
        bundle.putString(FragmentListMusic.TRACKLIST_ALBUM_IMAGE, album.getCover_medium());

        fragmentListMusic = new FragmentListMusic();

        fragmentListMusic.setArguments(bundle);
        loadFragment(fragmentListMusic, true);
    }

    @Override
    public void sendPosition(Integer position, Integer fragmentID) {
        Bundle bundle = new Bundle();

        bundle.putInt(FragmentSongViewPager.KEY_POSITION, position);
        bundle.putInt(FragmentSongViewPager.KEY_FRAGMENT_ID, fragmentID);

        fragmentSongViewPager = new FragmentSongViewPager();

        fragmentSongViewPager.setArguments(bundle);
        loadFragment(fragmentSongViewPager, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        signInSiagnOut(mAuth);
        verificarUsuario(mAuth);
        headerNavigationUser(mAuth);
//        mAuth.addAuthStateListener(mAuthListener);
    }

    public void headerNavigationUser (FirebaseAuth auth){
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        ImageView imagenUsuario = (ImageView) headerView.findViewById(R.id.imagenUsuario);
        TextView username = (TextView) headerView.findViewById(R.id.usuarioNavigation);
        TextView emailUser = (TextView) headerView.findViewById(R.id.textViewMailHeader);

        if (auth.getCurrentUser() != null){
            Glide.with(this).load(photoUrl).into(imagenUsuario);
            username.setText(userDisplayName);
            emailUser.setText(userEmail);
        } else {
            Glide.with(this).load(" ").into(imagenUsuario);
            username.setText(" ");
            emailUser.setText(" ");
        }
        navigationView.refreshDrawableState();

    }

    public void signInSiagnOut (FirebaseAuth auth){
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        Menu menu = navigationView.getMenu();
        MenuItem itemSignOut = menu.getItem(2);
        if (auth.getCurrentUser() != null){
            itemSignOut.setTitle("Sign Out");
            itemSignOut.setIcon(R.drawable.logouticon);
        } else {
            itemSignOut.setTitle("Log in");
            itemSignOut.setIcon(R.drawable.ic_https_black_24dp);
        }
        navigationView.refreshDrawableState();
    }

    public void verificarUsuario (FirebaseAuth auth){
        if (auth.getCurrentUser() != null){
            photoUrl = mAuth.getCurrentUser().getPhotoUrl();
            userEmail = mAuth.getCurrentUser().getEmail();
            userDisplayName = mAuth.getCurrentUser().getDisplayName();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (mAuthListener != null){
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
    }

    public void loadFragment(Fragment fragment, Boolean addToBack){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorLista, fragment);
        if (addToBack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}
