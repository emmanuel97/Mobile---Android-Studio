package com.example.emmanuelbulacio.agendacontatos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private SQLiteDatabase banco;
private Banco dbConection;
private ArrayList<Curso> cursos;
private ListView listaDeCursos;
private ArrayAdapter<Curso> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        dbConection=new Banco(this);
        listar();
        listaDeCursos = (ListView) findViewById(R.id.meuscursos);
        listaDeCursos.setOnItemClickListener(onItemClickListener);
        adapter = new ArrayAdapter<Curso>(this,android.R.layout.simple_list_item_1,cursos);
        listaDeCursos.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener () {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
            Curso c=cursos.get(position);
            eliminarCurso(c.getNome());
            //Toast.makeText(Main2Activity.this,""+(String)listaDeCursos.getItemAtPosition(position),Toast.LENGTH_SHORT);
            Log.d("SLIDA7Del",c.getNome());
        }
    };

    public void eliminarCurso(String curso){
            banco=dbConection.getWritableDatabase();
            banco.delete("cursos","nome='"+curso+"'",null);
            banco.close();
            atualizar();
    }

    public void atualizar(){
        Log.d("SLIDA7Del","voltei");
        Intent novaActivity= new Intent(this,MainActivity.class);
        startActivity(novaActivity);
    }
    /*
    public void click(View v){
        /* if(v==inserir){
        banco=dbConection.getWritableDatabase();
        ContentValues valores=new ContentValues();
        valores.put("nome",nome.getText().toString());
        valores.put("area",tel.getText().toString());
        valores.put("tempo",tel.getText().toString());
        System.out.print("row number: "+banco.insert("cursos",null,valores));

        banco.close();
    }
        else if(v==editar) {
            banco=dbConection.getWritableDatabase();
            ContentValues valores=new ContentValues();
            valores.put("tempo",tel.getText().toString());
            banco.update("cursos",valores,"nome='"+nome.getText().toString()+"'",null);
            banco.close();
        }
        else if(v==excluir){
        banco=dbConection.getWritableDatabase();
        banco.delete("cursos","nome='"+nome.getText().toString()+"'",null);
        banco.close();
    }
        listar();
    }
    */
    public void listar(){
        cursos=new ArrayList<>();
        banco=dbConection.getReadableDatabase();
        String filtro[]= {"nome","area","tempo"};
        Cursor rs =	banco.query("cursos",filtro,null,null,null,null,null);
        if(rs.moveToFirst()){
            do {
                cursos.add(new Curso(rs.getString(0),rs.getString(1),rs.getString(2)));
            }while(rs.moveToNext());}
        rs.close();
        banco.close();
    }

    public void travel(View v){
        Intent novaActivity= new Intent(this,Main2Activity.class);
        startActivity(novaActivity);
    }


}
