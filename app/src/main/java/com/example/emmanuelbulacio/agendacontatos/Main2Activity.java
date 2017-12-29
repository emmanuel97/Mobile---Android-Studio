package com.example.emmanuelbulacio.agendacontatos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private ArrayList<Curso> cursos;
    private ListView listaDeCursos;
    private ArrayAdapter<Curso> adapter;
    private SQLiteDatabase banco;
    private Banco dbConection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dbConection=new Banco(this);
        if(cursos==null)cursos=new ArrayList<>();
        getCursos();
        listaDeCursos = (ListView) findViewById(R.id.cursos);
        listaDeCursos.setOnItemClickListener(onItemClickListener);
        adapter = new ArrayAdapter<Curso>(this,android.R.layout.simple_list_item_1,cursos);
        listaDeCursos.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener () {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
            Curso c=cursos.get(position);
            inserirCurso(c.getNome(),c.getArea(),c.getTempo());
            //Toast.makeText(Main2Activity.this,""+(String)listaDeCursos.getItemAtPosition(position),Toast.LENGTH_SHORT);
            Log.d("SLIDA7In",c.getNome());
        }
    };


    public void inserirCurso(String nome,String area,String tempo){
        banco=dbConection.getWritableDatabase();
        ContentValues valores=new ContentValues();
        valores.put("nome",nome);
        valores.put("area",area);
        valores.put("tempo",tempo);
        System.out.print("row number: "+banco.insert("cursos",null,valores));

        banco.close();
    }

    public void getCursos() {
        JSONObject json = null;
        HttpURLConnection connection = null;
        URL urlToRequest = null;
        try {
            urlToRequest = new URL("https://suap.ifrn.edu.br/api/v2/edu/cursos/");
            connection = (HttpURLConnection) urlToRequest.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-CSRFToken", "uQZKmiOHr2IBHa4Tc3ZcFe5wDH5qE9ZmP8CROm2YIVxka7jVIaAI2coehjGjVWjP");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Basic MjAxNTIwMTQwNDAwMTM6TWF2ZXJpY2s3MDM=");
            json = getData(connection);
        } catch (MalformedURLException e) {
            e.getMessage();
            Log.d("SLIDA8", "description6");
        } catch (ProtocolException e) {
            e.getMessage();
            Log.d("SLIDA7", "description7");
        } catch (IOException e) {
            e.getMessage();
            Log.d("SLIDA9", "description6");
        }

        if (connection != null) {
            connection.disconnect();
        }
        if (json != null) {
            try {
                JSONArray array = json.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonobject = array.getJSONObject(i);
                    cursos.add(new Curso(jsonobject.getString("descricao"), jsonobject.getString("modalidade"), jsonobject.getString("carga_horaria")));
                }
            } catch (JSONException e) {
                e.getMessage();
                Log.d("SLIDA10", "description6");
            }
        }
    }

    public JSONObject getData(HttpURLConnection connection) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Log.d("SLIDA2","description2");
            connection.connect();
            // handle issues
            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // handle unauthorized (if service requires user login)
                Log.d("SLIDA3","description3");
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                // handle any other errors, like 404, 500,..
                Log.d("SLIDA4","description4");
            }
            Log.d("SLIDA5","description5");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";
            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            Log.d("SLIDA",response.toString());
            json = response.toString();
            if(json!=null)Log.d("SLIDA6","description6");
            JSONObject jsnobject = new JSONObject(json);
            return jsnobject;
        }catch (ProtocolException e) {e.getMessage();Log.d("SLIDA7","description7");
        }catch (IOException e) {e.getMessage();Log.d("SLIDA9","description6");
        } catch (JSONException e) {e.getMessage();Log.d("SLIDA10","description6");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    public void voltar(View v) {
        Intent novaActivity= new Intent(this,MainActivity.class);
        startActivity(novaActivity);
    }
}
//Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 994118080"));
//startActivity(intent);