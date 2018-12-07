package com.example.user.calculatrice;

import android.annotation.SuppressLint;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;

import android.widget.Toast;

import java.util.ArrayList;

public class Historique extends AppCompatActivity implements AdapterView.OnItemClickListener {


    DatabaseHelper databaseHelper;
    ArrayList<Donnee> listItem;
    ListView listeCal;
    CalAdapter adapter;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique);

        databaseHelper = new DatabaseHelper(this);
        listItem = new ArrayList<>();
        listeCal = findViewById(R.id.liste);

        viewData();
    }



    private void viewData() {
        Cursor cur = databaseHelper.viewData(true, null);

        if(cur.getCount() == 0){
            Toast.makeText(this, "Historique Vide", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter = new CalAdapter(this, R.layout.adapter_layout, listItem);
            while (cur.moveToNext()){
                Donnee donnee = new Donnee(cur.getString(1),cur.getString(2));
                listItem.add(donnee);

            }
            listeCal.setAdapter(adapter);
        }
        cur.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton){
            databaseHelper.deleteData();
            listItem.clear();
            for (int position = 0; position < adapter.getCount(); position++){
                adapter.remove(adapter.getItem(position));
            }
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Historique Vidé", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
