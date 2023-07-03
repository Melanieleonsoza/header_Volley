package com.example.header_volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class datos_a_mostrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_amostrar);

        Bundle bundle=this.getIntent().getExtras();
        RequestQueue queue1 = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/productos/search";
        JSONObject jsonres= new JSONObject();
        try {
            jsonres.put("fuente","1");
        }catch (Exception e){}
        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.POST, url, jsonres, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                TextView txtdatos = (TextView)findViewById(R.id.txtdatos);
                String lstdatos="";
                JSONArray JSONlista = null;
                try {
                    JSONlista = response.getJSONArray("productos");
                    for(int i=0; i< JSONlista.length();i++){
                        JSONObject datos= JSONlista.getJSONObject(i);
                        lstdatos = lstdatos + "\n" +
                                "-----------------------------------------------------------------" + "\n" +
                                " IDENTIFICADOR_PRODUCTO "+datos.getString("id")+"\n" +
                                " PRODUCTO "+datos.getString("descripcion")+"\n" +
                                " CATEGORÍA "+datos.getString("p_categoria")+"\n" +
                                " PVP. "+datos.getString("precio_unidad")+"\n" ;
                    }
                    txtdatos.setText(lstdatos);
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "Bearer " + bundle.getString("token"));
                return headerMap;
            }
        };
         queue1.add(jsonreq);
    }
}