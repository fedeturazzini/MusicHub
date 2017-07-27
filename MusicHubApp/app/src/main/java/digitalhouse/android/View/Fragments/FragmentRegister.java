package digitalhouse.android.View.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


import digitalhouse.android.View.Activities.ActivityMain;
import digitalhouse.android.a0317moacns1c_01.R;


public class FragmentRegister extends Fragment {

    private String userName;
    private String userEmail;
    private String password;
    private String confirmPassword;
    private FrameLayout parentLayout;
    private EditText editTextUserName;
    private EditText editTextUserEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonSubmit;
    private DatabaseReference databaseReference;
    private TwitterLoginButton loginButtonTwitter;


    // Firebase auth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        initizalizeViews(view);
        mAuth = FirebaseAuth.getInstance();

        //Button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = editTextUserName.getText().toString();
                userEmail = editTextUserEmail.getText().toString();
                password = editTextPassword.getText().toString();
                confirmPassword = editTextConfirmPassword.getText().toString();


                if(formIsComplete()) {
                    if (userNameIsAvailable()) {
                        register(userEmail, password);
                    } else {
                        Snackbar.make(parentLayout, "Usuario no disponible", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(parentLayout, "Complete todos los campos", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        // Twitter log in
        loginButtonTwitter = (TwitterLoginButton) view.findViewById(R.id.login_button_twitter);
        loginButtonTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls.

                handleTwitterSession(result.data);

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure}
                Toast.makeText(getContext(), exception.toString() , Toast.LENGTH_SHORT).show();

            }
        });

        final ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    String userId = currentProfile.getId();
                }
            }
        };


        return view;
    }

    private Boolean formIsComplete() {
        return !userName.equals("") &&
                !userEmail.equals("") &&
                !password.equals("") &&
                !confirmPassword.equals("");

    }

    public void initizalizeViews (View view) {

        //Boton cargar datos
        buttonSubmit = (Button) view.findViewById(R.id.registerButtonSumbit);

        //Usadp para mostrar el snackbar
        parentLayout = (FrameLayout) view.findViewById(R.id.parentLayout);

        //editext username
        editTextUserName = (EditText) view.findViewById(R.id.registerEditTextUser);

        //editext email
        editTextUserEmail = (EditText) view.findViewById(R.id.registerEditTextEmail);

        //password
        editTextPassword = (EditText) view.findViewById(R.id.registerEditTextPassword);

        //Confirm password
        editTextConfirmPassword = (EditText) view.findViewById(R.id.registerEditTextConfirmPassword);

    }

    private Boolean userNameIsAvailable() {
        //aca vamos a chequear si el user esta disponible
        return true;
    }

    public void register (String emailIngresado, String passwordIngresado){
        mAuth.createUserWithEmailAndPassword(emailIngresado, passwordIngresado).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    user.sendEmailVerification();
                    databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("username").setValue(userName);
                    Snackbar.make(parentLayout, "¡Usuario registrado!", Snackbar.LENGTH_SHORT).show();
                    goActivityMain();
                } else {
                    Toast.makeText(getContext(),"" + task.getException() + " " , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Twitter
    private void handleTwitterSession(TwitterSession session) {

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            databaseReference.child("users").child(user.getUid()).child("username").setValue(user.getDisplayName());
                            Toast.makeText(getActivity(), "Register successfull!", Toast.LENGTH_SHORT).show();
                            goActivityMain();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("username").setValue(user.getDisplayName());
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void goActivityMain (){
        Intent intent = new Intent(getContext(), ActivityMain.class);
        startActivity(intent);
    }
}
