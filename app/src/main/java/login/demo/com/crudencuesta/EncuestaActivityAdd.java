package login.demo.com.crudencuesta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EncuestaActivityAdd extends AppCompatActivity {

    private EditText txtIdEncuesta;
    private Button btnConsulta;


    private Button btnGrabar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta_add);
        txtIdEncuesta = (EditText) findViewById(R.id.txtSearch);
        btnGrabar = (Button) findViewById(R.id.btnGrabar);

        btnGrabar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 grabarEncuesta(txtIdEncuesta.getText().toString());
             }
         });


    }

    public void grabarEncuesta(String pregunta){

        RequestQueue queue = Volley.newRequestQueue(EncuestaActivityAdd.this);
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("pregunta", pregunta);


        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, "http://192.168.1.9:8089/encuesta",

                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(EncuestaActivityAdd.this, "Ingreso Correcto",Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EncuestaActivityAdd.this,"Error", Toast.LENGTH_LONG).show();
                    }


                });
        queue.add(postRequest);

    }


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EncuestaActivityAdd.class );
        return intent;
    }
}
