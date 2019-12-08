package com.example.crudequipo.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crudequipo.dto.Equipo;
import com.example.crudequipo.helpers.DatabaseHelper;

import java.util.ArrayList;

public class EquipoDAL {
    private DatabaseHelper helperdb;
    private Equipo equipo;

    public EquipoDAL(Context context){
        this.helperdb = new DatabaseHelper(context);
        this.equipo = new Equipo();

        SQLiteDatabase db = helperdb.getWritableDatabase();
    }

    public EquipoDAL(Context context, Equipo equipo){
        this.helperdb = new DatabaseHelper(context);
        this.equipo = equipo;
    }

    public ArrayList<Equipo> seleccionar() {
        SQLiteDatabase db = helperdb.getReadableDatabase();
        ArrayList<Equipo> list = new ArrayList<>();
        Cursor consulta = db.rawQuery("select * from equipo", null);
        if (consulta.moveToFirst()) {
            do {
                int serie = consulta.getInt(0);
                String marca = consulta.getString(1);
                String descripcion = consulta.getString(2);

                Equipo equipo = new Equipo(serie, marca, descripcion);
                list.add(equipo);
            } while (consulta.moveToNext());
        }
        return list;
    }

    public boolean actualizar(Equipo  equipo){
        SQLiteDatabase db = helperdb.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("marca", equipo.getMarca());
        c.put("descripcion", equipo.getDescripcion());

        try{
            int filas;
            filas = db.update("equipo", c, "serie=?", new String[]{
                    String.valueOf(equipo.getSerie())
            });
            return (filas > 0);
        }catch (Exception  e){

        }
        return false;
    }

    public boolean eliminar(int serie){
        SQLiteDatabase db = helperdb.getWritableDatabase();
        int filas;
        try{
            filas = db.delete("equipo", "serie=?", new String[]{
                    String.valueOf(serie)
            });
        }catch (Exception e){
            return false;
        }
        return (filas == 1);
    }

    public Equipo getEquipo(){
        return this.equipo;
    }
}
