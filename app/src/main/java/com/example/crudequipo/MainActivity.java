package com.example.crudequipo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crudequipo.dal.EquipoDAL;
import com.example.crudequipo.dto.Equipo;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EquipoDAL equipoDAL;
    private ListView listView;
    private ArrayAdapter<Equipo> adapter;
    private ArrayList<Equipo> listaEquipo;
    int posicion = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.equipoDAL = new EquipoDAL(getApplicationContext(), new Equipo());
        this.listaEquipo = new EquipoDAL(getBaseContext()).seleccionar();

        this.listView = (ListView) findViewById(R.id.listaEquipos);

        this.adapter = new ArrayAdapter<Equipo>(getApplicationContext(), android.R.layout.activity_list_item, this.listaEquipo);

        this.listView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmacion");
        builder.setMessage("¿Desea borrar el producto?");
        builder.setPositiveButton("Si, borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                int serie = ((Equipo) listaEquipo.get(posicion)).getSerie();
                boolean r = equipoDAL.eliminar(serie);
                if (r){
                    Toast.makeText(getApplicationContext(), "Se ha eliminado", Toast.LENGTH_SHORT).show();
                    actualizarLista();
                }else{
                    Toast.makeText(getApplicationContext(), "No se pudo eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialogo = builder.create();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int p, long id) {
                posicion = p;
                dialogo.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int p, long id) {
                posicion = p;
            }
        });
    }

    public void EntrarEditarLista(){
        Intent intento = new Intent(MainActivity.this, EditarEquipoActivity.class);
        Equipo eq = (Equipo) listaEquipo.get(posicion);

        intento.putExtra("equipo", (Serializable) eq);
        startActivityForResult(intento, 100);
    }

    private void actualizarLista(){
        adapter.clear();
        adapter.addAll(equipoDAL.seleccionar());
        adapter.notifyDataSetChanged();
    }
}
