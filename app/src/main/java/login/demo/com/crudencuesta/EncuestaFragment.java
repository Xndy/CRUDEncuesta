package login.demo.com.crudencuesta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EncuestaFragment extends Fragment {



    private View view;

    private RecyclerView recyclerView;
    public EncuestaAdapter encuestaAdapter;
    private List<Encuesta> encList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_menu_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_nueva_encuesta:

                Intent intent=EncuestaActivityAdd.newIntent(getActivity());
                startActivity(intent);
                return true;
            case R.id.menu_item_consulta_encuesta:

                Intent i=EnsuataSearchActivity.newIntent(getActivity());
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_encuesta, container, false);


        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_encuesta);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cargarEncuesta();
        encuestaAdapter=new EncuestaAdapter(encList);

        recyclerView.setAdapter(encuestaAdapter);

        return view;
    }

    public void cargarEncuesta(){

        final StringRequest getEncuesta=new StringRequest(Request.Method.GET, "http://192.168.1.9:8089/encuesta",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson= new Gson()   ;
                        List<Encuesta> getEncuestaList = gson.fromJson(response,
                                new TypeToken<List<Encuesta>>(){}.getType());
                        for(Encuesta e: getEncuestaList){
                            encList.add(e);
                        }
                        encuestaAdapter.notifyDataSetChanged();


                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(),"Error", Toast.LENGTH_LONG).show();

                    }
                }
        );
        RequestQueueSingleton.getInstance(getActivity().getApplication()).addToRequestQueue(getEncuesta);

    }




    private class EncuestaAdapter extends RecyclerView.Adapter<EncuestaHolder>{

        private List<Encuesta> encuestas;

        public EncuestaAdapter(List<Encuesta> encuestas) {
            this.encuestas= encuestas;
        }

        @Override
        public EncuestaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view=layoutInflater.inflate(R.layout.fragment_encuesta_list,parent,false);
            return new EncuestaHolder(view);
        }

        @Override
        public void onBindViewHolder(EncuestaHolder holder, int position) {
            Encuesta encuesta = encuestas.get(position);
            holder.llenarEncuesta(encuesta);
        }

        @Override
        public int getItemCount() {
            return encuestas.size();
        }
    }

    private class EncuestaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView txtEncuestaGET;
        private Encuesta encuestas;

        public EncuestaHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txtEncuestaGET=(TextView)itemView.findViewById(R.id.txtEncuestaGET);
        }

        public  void  llenarEncuesta(Encuesta encuesta){
            this.encuestas=encuesta;
            System.out.println("enncuesta" + encuestas);
            txtEncuestaGET.setText(encuestas.getPregunta());



        }
        // aparece el nombre del crimen que se esta dando click
        @Override
        public void onClick(View v) {
            showAlert(encuestas.getIdEncuesta());


        }


        public void showAlert(final Integer idEncuesta){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

            // Setting Dialog Title
            alertDialog.setTitle("¿Desea eliminar esta encuesta?");

            // Setting Dialog Message
            //alertDialog.setMessage("GPS no esta habilitado. ¿Desea ir a ajustes para habilitarlo?");

            // On pressing Settings button
            alertDialog.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog,int which) {

                    eliminarEncuesta(idEncuesta);

                }
            });

            // on pressing cancel button
            alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }

        public void eliminarEncuesta(Integer idEncuesta){

            final StringRequest deleteEncuesta=new StringRequest(Request.Method.DELETE, "http://192.168.1.9:8089/encuesta/"+idEncuesta,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getActivity(),"Eliminado", Toast.LENGTH_LONG).show();

                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getActivity(),"Error", Toast.LENGTH_LONG).show();

                        }
                    }
            );
            RequestQueueSingleton.getInstance(getActivity().getApplication()).addToRequestQueue(deleteEncuesta);

        }


    }
    // actualiza
    private void updateUI(){
      //  encuestaAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }




}
