package com.example.crudequipo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudequipo.dal.EquipoDAL;
import com.example.crudequipo.dto.Equipo;

public class EditarEquipoActivity extends AppCompatActivity {
    private EditText editMarca;
    private EditText editDesc;
    private EditText editSerie;
    private Button btnActualizar;
    private EquipoDAL equipoDAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_equipo);

        this.editMarca = (EditText) findViewById(R.id.editMarca);
        this.editDesc = (EditText) findViewById(R.id.editDesc);
        this.editSerie = (EditText) findViewById(R.id.editSerie);
        this.btnActualizar = (Button) findViewById(R.id.btnActualizar);

        this.equipoDAL = new EquipoDAL(getApplicationContext(), (Equipo) getIntent().getSerializableExtra("equipo"));

        this.editMarca.setText(equipoDAL.getEquipo().getMarca());
        this.editDesc.setText(equipoDAL.getEquipo().getDescripcion());

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Equipo eq = equipoDAL.getEquipo();
                eq.setMarca(String.valueOf(editMarca.getText()));
                eq.setDescripcion(String.valueOf(editDesc.getText()));

                if (equipoDAL.actualizar(eq)){
                    Toast.makeText(getApplicationContext(), "Actualizado", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Nose actualizo", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
