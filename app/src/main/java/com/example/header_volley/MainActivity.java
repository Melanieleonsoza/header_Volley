package com.example.header_volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void mostrar(View view){

        EditText txtusuario=(EditText)findViewById(R.id.txtusuario) ;
        EditText txtclave=(EditText)findViewById(R.id.txtcontra);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/login";
        JSONObject jsonres= new JSONObject() ;
        try {
            jsonres.put("correo",txtusuario.getText().toString());
            jsonres.put("clave",txtclave.getText().toString());
        }
        catch(JSONException e) {
        }
        JsonObjectRequest req=new JsonObjectRequest(Request.Method.POST, url, jsonres, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent intent = new Intent(MainActivity.this, datos_a_mostrar.class);
                Bundle b = new Bundle();
                try {
                    b.putString("token", response.getString("access_token"));
                    intent.putExtras(b);
                    startActivity(intent);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "ALGO SALIO MAL:(  VUELVE A INTENTAR", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(req);
    }

}