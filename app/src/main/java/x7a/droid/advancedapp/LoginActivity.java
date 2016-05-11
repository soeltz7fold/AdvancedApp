package x7a.droid.advancedapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    public static final String BASE_URL_LOGIN = "http://private-f4dc2-signuplogin.apiary-mock.com";
    private static final String BASE_URL_LOGO = "https://lh3.ggpht.com/KB6kv1TncNFL6VkUwfDR6EKt1k-AnY397xFm7BWSHOG_ZjJSnLjB23vtfBQsb6Udz3U=w300";
    private String strEmail, strPassword, strToken;
    Retrofit retrofit;
    Gson gson;
    InterfaceApi login_api;
    TextView tv_result_api;
    ImageView imgLogo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin, btnReg;
        final AutoCompleteTextView inputEmail;
        final EditText inputPassword;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Login First");
//        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
            imgLogo = (ImageView) findViewById(R.id.imgLogo);
            tv_result_api = (TextView)findViewById(R.id.tv_result_api);
            inputEmail = (AutoCompleteTextView) findViewById(R.id.email);
            populateAutoComplete();
            inputPassword = (EditText) findViewById(R.id.password);
            btnReg = (Button) findViewById(R.id.email_register_button);
            btnLogin = (Button) findViewById(R.id.email_sign_in_button);

            SharedPreferences get_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
            if (get_shared_preference.getString("token_authentication", "").equals("")) {
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        strEmail = inputEmail.getText().toString();
                        strPassword = inputPassword.getText().toString();
                        Log.e("str", strEmail +strPassword);
                        if (inputEmail.getText().toString().equals("") || inputPassword.getText().toString().equals("")) {
                            Toast.makeText(LoginActivity.this, "Email/Password Can't Blank", Toast.LENGTH_SHORT).show();
                        } else {
                            sync_login_retrofit(BASE_URL_LOGIN);
                        }
                    }
                });
            } else {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,Try.class);
                startActivity(i);
                Toast.makeText(LoginActivity.this, "check Picasso", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Picasso.with(this)
                .load(BASE_URL_LOGO)
                .placeholder(R.mipmap.ic_bk)
                .error(R.mipmap.ic_nav)
                .resize(400,300)
                .centerCrop()
                .into(imgLogo);
    }

    private void populateAutoComplete() {
        AutoCompleteTextView inputEmail = (AutoCompleteTextView) findViewById(R.id.email);
        String[] countries = getResources().getStringArray(R.array.autocomplete_email);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        inputEmail.setAdapter(adapter);
    }

    public void sync_login_retrofit(String BASE_URL_LOGIN){
        final Typeface supercell = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Supercell.ttf");
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_LOGIN)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            login_api = retrofit.create(InterfaceApi.class);
            // // implement interface for get all user
            Call<Users> call = login_api.getUsers();
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    int status = response.code();
                    String emailTmp = "", passTmp, tokentmp = "",statusUser="";
//                    Log.e("Response Status ", String.valueOf(status));

                    for (Users.UserItem user : response.body().getUsers()) {
//                        tv_result_api.append(
//                                "Id = " + String.valueOf(user.getId()) +
//                                        System.getProperty("line.separator") +
//                                        "Email = " + user.getEmail() +
//                                        System.getProperty("line.separator") +
//                                        "Password = " + user.getPassword() +
//                                        System.getProperty("line.separator") +
//                                        "Token Auth = " + user.getToken_authentication() +
//                                        System.getProperty("line.separator") +
//                                        System.getProperty("line.separator"));
                        emailTmp = user.getEmail();
                        passTmp = user.getPassword();
                        tokentmp = user.getToken_authentication();
                        Log.e("Response Status ", emailTmp + ", " + passTmp + ", " + tokentmp);

                        if (strEmail.equals(String.valueOf(user.getEmail()))
                                && (strPassword.equals(String.valueOf(user.getPassword()))))
//                            Log.e("try", strEmail + strPassword);
                        {
                            statusUser = "registered";
//                            tv_result_api.setText(Log.e("try", strEmail + strPassword));
                            Log.e("status", statusUser);
                            Toast.makeText(LoginActivity.this, "PASSED", Toast.LENGTH_SHORT).show();
                            break;
                        }
//                        else{
//                            Toast.makeText(LoginActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
//                        }
                    }
                    if(statusUser.equals("registered")){
                        SharedPreferences set_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
                        SharedPreferences.Editor sp_editor = set_shared_preference.edit();
                        sp_editor.putString("email", emailTmp);
                        sp_editor.putString("token_authentication", tokentmp);
                        sp_editor.commit();

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        tv_result_api.setText(String.valueOf("FAILED"));
                        tv_result_api.setTextColor(Color.parseColor("#FFA000"));
                        tv_result_api.setTypeface(supercell);
                        Toast.makeText(LoginActivity.this, "Fails Login.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Log.e("OnFailure ", t.toString());
                }
            });
        }
    }