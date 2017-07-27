package digitalhouse.android.View.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import digitalhouse.android.a0317moacns1c_01.R;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;


public class FragmentMyAccount extends Fragment {

    //FireBase auth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Uri photoUrl;
    private String userEmail;
    private String userDisplayName;
    private String userFirstName;
    private StorageReference raizFireBase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        //Firebase
        mAuth = FirebaseAuth.getInstance();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                verificarUsuario(firebaseAuth);
                cargaUsuario(firebaseAuth, getView());
            }
        });

        Button botonUploadoPhoto = (Button) view.findViewById(R.id.botonUploadPhoto);
        botonUploadoPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openChooserWithGallery(getActivity(), " Elija la opcion para subir su foto de perfil ", 0);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        verificarUsuario(mAuth);
        cargaUsuario(mAuth, getView());
    }

    public void verificarUsuario (FirebaseAuth auth){
        if (auth.getCurrentUser() != null){
            photoUrl = mAuth.getCurrentUser().getPhotoUrl();
            userEmail = mAuth.getCurrentUser().getEmail();
            userDisplayName = mAuth.getCurrentUser().getDisplayName();
        }
    }

    public void cargaUsuario (FirebaseAuth auth, View view){
        TextView userNameProfile = (TextView) view.findViewById(R.id.usernameMyProfile);
        ImageView imagenProfile = (ImageView) view.findViewById(R.id.imageViewUserImage);
        TextView userName = (TextView) view.findViewById(R.id.textViewUserName);
        TextView emailUser = (TextView) view.findViewById(R.id.emailMyProfile);
        view.findViewById(R.id.userFirstName);

        if (auth.getCurrentUser() != null){
            userNameProfile.setText(userDisplayName);
            Glide.with(getContext()).load(photoUrl).into(imagenProfile);
            userName.setText(userDisplayName);
            emailUser.setText(userEmail);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                loadFoto(imageFile);
            }

        });


    }

    public void loadFoto (File imageFile){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        raizFireBase = storage.getReferenceFromUrl("gs://moacms1m-04.appspot.com/userimages");
        Uri uri = Uri.fromFile(imageFile);
        StorageReference nuevaFoto = raizFireBase.child(uri.getLastPathSegment()).child(mAuth.getCurrentUser().getUid()).child(mAuth.getCurrentUser().getEmail()); // esto creo q esta mal
        UploadTask uploadTask = nuevaFoto.putFile(uri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "Foto subida", Toast.LENGTH_LONG).show();
            }
        });

    }
}
