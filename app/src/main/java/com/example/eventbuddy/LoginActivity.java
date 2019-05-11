package com.example.eventbuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    protected static String user_name_from_login = "user_name_from_login";
    private FirebaseAuth firebaseAuth;
    //private Button loginButton;
    //private Button registerButton;
    private CheckBox remember_me;
    private EditText emailView;
    private EditText passwordView;
    private EditText userNameView;
    protected String displayName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            firebaseAuth = FirebaseAuth.getInstance();

            //loginButton = findViewById(R.id.btLogin);
            //registerButton = findViewById(R.id.btRegister);
            remember_me = findViewById(R.id.remember_me);
            emailView = findViewById(R.id.login_email);
            passwordView = findViewById(R.id.login_password);
            userNameView = findViewById(R.id.login_userName);

            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            boolean remember_me_pref = sharedPref.getBoolean("remember_me", false);
            if (remember_me_pref) {
            remember_me.setChecked(true);
            autoLogin();
            } else {
            remember_me.setChecked(false);
            }

            //saveDisplayName();
            }

    @Override
    public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            /*if(!(currentUser.getEmail().isEmpty())){
                Intent intent = new Intent();
                if(displayName == null)
                    displayName = "a";
                intent.putExtra(user_name_from_login,displayName);
                finish();
            }
    */
            }

    public void registerNewUser(View v) {
            Intent intent = new Intent(this, RegisterActivity.class);
            finish();
            startActivity(intent);
            }

    public void autoLogin() {
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    final String username = sharedPref.getString("username", null);
            String email = sharedPref.getString("email", null);
            String password = sharedPref.getString("password", null);

            if (email == null || password == null || username == null)
            return;

            userNameView.setText(username);
            emailView.setText(email);
            passwordView.setText(password);

            firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(user_name_from_login, username);
            finish();
            startActivity(intent);
            } else {
            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
            }
            });
            }

    public void signInUser(View v) {
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String username = userNameView.getText().toString();

        if (email.equals("") || password.equals("") || username.equals(""))
            return;

        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", email);
        editor.putString("username", username);
        editor.putString("password", password);
        if (remember_me.isChecked())
            editor.putBoolean("remember_me", true);
        else
            editor.putBoolean("remember_me", false);
        editor.apply();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAGG", "signInWithEmail:success");
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            displayName = userNameView.getText().toString();
                            //User user = new User(firebaseUser, displayName);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra(user_name_from_login, displayName);
                            finish();
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAGG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }}