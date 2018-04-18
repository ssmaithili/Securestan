package com.example.android.securestan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Login extends AppCompatActivity {


    private static final String TAG = "Login";
    EditText etname,etemail,etbranch,etyear,etmobileno,etrollno,etprnno;
    String name,email,branch,year,mobile,roll,prn1,url1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etname = (EditText) findViewById(R.id.ETname);
        etemail = (EditText) findViewById(R.id.ETemail);
        etbranch = (EditText) findViewById(R.id.ETbranch);
        etyear = (EditText) findViewById(R.id.ETyear);
        etmobileno = (EditText) findViewById(R.id.ETmobileno);
        etrollno = (EditText) findViewById(R.id.ETrollno);
        //Bundle Extras = getExtra().getIntent;
       // String prn1 = Extras.getString("TextBox");
         prn1=getIntent().getStringExtra("TextBox");
    }
    public void signuppage(View view){
        name=etname.getText().toString();
        email=etemail.getText().toString();
        branch=etbranch.getText().toString();
        year=etyear.getText().toString();
        mobile=etmobileno.getText().toString();
        roll=etrollno.getText().toString();
        check();
        Intent k= new Intent(this,Signup.class);
        startActivity(k);
    }

    public boolean check() {
        Log.d(TAG,"Wea are im check function" );

        url1 = "https://api.ample90.hasura-app.io/dbupdate";

        Log.d(TAG,"hello prn is " + prn1);

        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            JSONObject jsonObject = new JSONObject()
                    .put("mobile",mobile)
                    .put("email",email)
                    .put("branch", branch)
                    .put("year", year)
                    .put("roll", roll)
                    .put("prn",prn1)
                    .put("name",name);

            Log.d(TAG,"we are inside try block ");

            RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
            Request request = new Request.Builder()
                    .url(url1)
                    .post(body)
                    .build();
            Log.d(TAG,"json object is"+jsonObject.toString());
            Log.d(TAG, "request is " + request);
            client.newCall(request).enqueue(new Callback() {
                @Override

                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(!response.isSuccessful()){
                        Log.d(TAG,"Response not successful");

                        throw new IOException("Unexpected code" + response);
                    }
                }
            });

            // To execute the call synchronously
            // try {
            //     Response response = client.newCall(request).execute();
            //     String responseString = response.body().string(); // handle response
            // } catch (IOException e) {
            //     e.printStackTrace(); // handle error
            // }

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
