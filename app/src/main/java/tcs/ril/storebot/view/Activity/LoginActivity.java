package tcs.ril.storebot.view.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tcs.ril.storebot.Network.Util;
import tcs.ril.storebot.R;
import tcs.ril.storebot.model.AuthToken;
import tcs.ril.storebot.model.PreferenceManager;
import tcs.ril.storebot.model.UserCredential;
import tcs.ril.storebot.view.Fragment.IPUpdateFragment;


public class LoginActivity extends AppCompatActivity {

    /*Declaring all the required variables*/
    private static final String LOGTAG = "LoginActivity";
    public static String ROOT_URL = "http://172.20.10.2/";
    private EditText usernameField,passwordField;
    private Button ipSettings,loginButton;
    private String username;
    private PreferenceManager preferenceManager;
    ImageView logoView;
    private int PERMISSION_ALL = 1;
    ConstraintLayout loginParent;
    LinearLayout logoLayout;
    /*List of all the permissions required by the application*/
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.RECORD_AUDIO
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Checking for all the permissions mentioned above in Permissions array*/
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        loginParent=findViewById(R.id.login_parent_layout);
        logoView=findViewById(R.id.logo);

        logoLayout=findViewById(R.id.logo_layout);
        /*Initializing all the fields in activity*/
        usernameField = findViewById(R.id.input_email);

        passwordField = findViewById(R.id.input_password);
        username = usernameField.getText().toString();
        ipSettings = findViewById(R.id.ip_settings);
        loginButton = findViewById(R.id.btn_login);

        /*Getting the data from Preference Manager(Custom in this application)*/
        preferenceManager = new PreferenceManager(LoginActivity.this);
        usernameField.setText(preferenceManager.getUserName(PreferenceManager.USERNAME));
        passwordField.setText(preferenceManager.getPassword(PreferenceManager.PASSWORD));
        ipSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipUpload();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceManager.setUserName(PreferenceManager.USERNAME, usernameField.getText().toString());
                preferenceManager.setPassword(PreferenceManager.PASSWORD, passwordField.getText().toString());

                ROOT_URL = "http://" + preferenceManager.getIp(PreferenceManager.IP) ;
                login();
            }
        });

        Log.d(LOGTAG, "onCreate: " + ROOT_URL);
       loginParent.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    Resources r = getResources();
                    float pxIpSettingsHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, r.getDisplayMetrics());
                    float pxLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
                    float pxTopMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
                    float pxRightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
                    float pxBottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r.getDisplayMetrics());
                    LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    p.setMargins(Math.round(pxLeftMargin),Math.round(pxTopMargin),Math.round(pxRightMargin),Math.round(pxBottomMargin));
                    LinearLayout.LayoutParams p1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,200);
                    LinearLayout.LayoutParams ipSettingsLayoutParams=new LinearLayout.LayoutParams(Math.round(pxIpSettingsHeight),Math.round(pxIpSettingsHeight));
                    ipSettingsLayoutParams.setMargins(Math.round(pxLeftMargin),Math.round(pxTopMargin),Math.round(pxRightMargin),Math.round(pxBottomMargin));
                        usernameField.setLayoutParams(p);
                        passwordField.setLayoutParams(p);
                        loginButton.setLayoutParams(p);
                        logoLayout.setLayoutParams(p1);
                        ipSettings.setLayoutParams(ipSettingsLayoutParams);

                }
                if(oldBottom<bottom)
                {

                    Resources r = getResources();
                    float pxLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
                    float pxTopMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
                    float pxRightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
                    float pxBottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r.getDisplayMetrics());
                    float pxIpSettingsHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());

                    LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    LinearLayout.LayoutParams p1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,400);
                    LinearLayout.LayoutParams ipSettingsLayoutParams=new LinearLayout.LayoutParams(Math.round(pxIpSettingsHeight),Math.round(pxIpSettingsHeight));
                    ipSettingsLayoutParams.setMargins(Math.round(pxLeftMargin),Math.round(pxTopMargin),Math.round(pxRightMargin),Math.round(pxBottomMargin));

                    p.setMargins(16,0,16,8);
                    usernameField.setLayoutParams(p);
                    passwordField.setLayoutParams(p);
                    loginButton.setLayoutParams(p);
                    logoLayout.setLayoutParams(p1);
                    ipSettings.setLayoutParams(ipSettingsLayoutParams);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        loginParent.setBackgroundResource(R.drawable.bg);
                    }
                }
            }
        });

    }

    /*Checking for permissions*/
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /*Call to show the fragment for setting the new IP address*/
    public void ipUpload() {
        IPUpdateFragment ipUpdate = new IPUpdateFragment();
        ipUpdate.show(getSupportFragmentManager(), "IP Update");
    }

    /*Function to login using retrofit*/
    public void login() {
        if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS, PERMISSION_ALL);
            Toast.makeText(LoginActivity.this, "Cannot login to app without all permissions", Toast.LENGTH_SHORT).show();
        } else {
    /*UserCredential is a java class containing the required fields for authentication*/
            UserCredential uc = new UserCredential(usernameField.getText().toString(), passwordField.getText().toString());

            Util.getUserService().loginAssociate(uc).enqueue(new Callback<AuthToken>() {
                @Override
                public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                    if (response.isSuccessful()) {
                        AuthToken authToken = response.body();
                        Util.setAccessToken(authToken.getToken());
                        if (authToken.getSuccess()) {
                            Log.d(LOGTAG, " auth token" + authToken.getToken());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Bundle extras =new Bundle();
                            preferenceManager.setAccessToken(PreferenceManager.ACCESS_TOKEN,authToken.getToken());
                            String profileURL=LoginActivity.ROOT_URL+"/"+authToken.getUrl();
                            preferenceManager.setProfileUrl(PreferenceManager.PROFILE_URL,profileURL);

                            extras.putString("TOKEN", authToken.getToken());
                            intent.putExtras(extras);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, authToken.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "User Authentication failed!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthToken> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

