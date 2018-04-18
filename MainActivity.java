package com.example.android.securestan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText et1, et2;
    Button b1;
    String prn, col, url1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText) findViewById(R.id.ETprnno);
        et2 = (EditText) findViewById(R.id.ETcollege);
        b1 = (Button) findViewById(R.id.BTNnext);



      /* et2 = setEnabled(false);
        b1 = setEnabled(false);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    et2 = setEnabled(false);
                } else
                    et2.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    b1 = setEnabled(false);
                } else
                    b1.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/


    }



    public void nextpage(View view){
        prn=et1.getText().toString();
        col=et2.getText().toString();
        check();
        Intent i= new Intent(this,Login.class);
        i.putExtra("TextBox",et1.getText().toString());
        startActivity(i);
    }
    public boolean check() {
        Log.d(TAG,"We are im check function" );

        url1 = "https://api.ample90.hasura-app.io/firstupdate";
        Log.d(TAG,"hello prn is " + prn);
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            JSONObject jsonObject = new JSONObject()
                    .put("prn",prn)
                    .put("college", col);


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
                    String responseData=response.body().string();
                    Log.d(TAG,"response data is " + responseData);
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
        }
        return true;
    }


}
