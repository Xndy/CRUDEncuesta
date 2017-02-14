package login.demo.com.crudencuesta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class EnsuataSearchActivity extends AppCompatActivity {

    private Button btnConsulta;
    private TextView txtPregunt;
    private EditText txtSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensuata_search);

        txtSearch = (EditText) findViewById(R.id.txtSearch);
        btnConsulta = (Button) findViewById(R.id.btnConsulta);
        txtPregunt = (TextView) findViewById(R.id.txtPreguntaS);

        btnConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarEncuestaporID(Integer.parseInt(txtSearch.getText().toString()));
            }
        });
    }

    public void cargarEncuestaporID(Integer idEncuesta){

        final StringRequest getEncuesta=new StringRequest(Request.Method.GET, "http://192.168.1.9:8089/encuesta/"+idEncuesta,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson= new Gson()   ;
                        Encuesta encuesta = gson.fromJson(response, Encuesta.class);

                        txtPregunt.setText(encuesta.getPregunta());



                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(EnsuataSearchActivity.this,"Error", Toast.LENGTH_LONG).show();

                    }
                }
        );
        RequestQueueSingleton.getInstance(EnsuataSearchActivity.this.getApplication()).addToRequestQueue(getEncuesta);

    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EnsuataSearchActivity.class );
        return intent;
    }




}
