package digitalhouse.android.View.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.TintAwareDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.concurrent.Executor;

import digitalhouse.android.View.Activities.ActivityMain;
import digitalhouse.android.View.Activities.ActivityRegister;
import digitalhouse.android.a0317moacns1c_01.R;

import static android.R.attr.data;


public class FragmentLogin extends Fragment {

    private String userName;
    private String password;
    private FrameLayout parentLayout;
    private EditText editTextUserName;
    private EditText editTextUserPassword;
    private TwitterLoginButton loginButtonTwitter;
    private CallbackManager callbackmanager;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);


        //Usadp para mostrar el snackbar
        parentLayout = (FrameLayout) view.findViewById(R.id.parentLayout);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Registrarte
        TextView textViewRegister = (TextView) view.findViewById(R.id.buttonRegister);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityRegister.class);
                startActivity(intent);
            }
        });

        //Edit Text userName
        editTextUserName = (EditText) view.findViewById(R.id.editTextLoginUser);

        //Edit-Text password
        editTextUserPassword = (EditText) view.findViewById(R.id.editTextLoginPassword);

        //Button submit con listener
        Button buttonSummit = (Button) view.findViewById(R.id.loginButtonSubmit);
        buttonSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = editTextUserName.getText().toString();
                password = editTextUserPassword.getText().toString();

                if(formIsComplete()) {
                    signInWithMail(userName, password);
                    //Aca vamos a chequear si es usuario en la db y le avisamos si no lo encontramos en la base
                } else {
                    Snackbar.make(parentLayout, "Completar todos los campos", Snackbar.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //twitter
        loginButtonTwitter.onActivityResult(requestCode, resultCode, data);
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

                            Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private Boolean formIsComplete() {
        return !userName.equals("") &&
                !password.equals("");
    }

    public void signInWithMail (String email, String password){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Snackbar.make(parentLayout, "¡Bienvenido " + userName + "!", Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ActivityMain.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(parentLayout, e.toString() + userName + "!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void goActivityMain (){
        Intent intent = new Intent(getContext(), ActivityMain.class);
        startActivity(intent);
    }

}
