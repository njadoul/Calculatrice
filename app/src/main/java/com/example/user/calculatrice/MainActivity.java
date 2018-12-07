package com.example.user.calculatrice;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper databasehelper;
    ScriptEngine engine;

    Intent intent;

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databasehelper = new DatabaseHelper(this);
        intent = new Intent(MainActivity.this, Historique.class);

        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("rhino");

        ImageButton mSpeakBtn = findViewById(R.id.imageButton);
        mSpeakBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Bonjour, dit-moi votre calcul");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this, ""+a, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    TextView calcule = findViewById(R.id.Ecran);

                    if (!Pattern.compile("[a-zA-Z]+").matcher(result.get(0)).find()){
                        calcule.setText(result.get(0));
                    }
                    else{
                        Toast.makeText(this, "Mauvaise expression", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }

        }
    }

    private boolean verifFloat(String str){

        try{
            Double.parseDouble(str);
        }catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @SuppressLint("SetTextI18n")
    public void Affiche(View v) {

        TextView visu = findViewById(R.id.Ecran);
        TextView nombre = (TextView) v;
        if(!nombre.getText().toString().equals("*") && (!nombre.getText().toString().equals("-")) && (!nombre.getText().toString().equals("+")) && (!nombre.getText().toString().equals("/"))){

            visu.setText(visu.getText().toString() + nombre.getText().toString());

        }
        else {
            char chara = visu.getText().toString().charAt(visu.length() - 1);
            if(chara !='*' && chara != '/' && chara != '+' && chara != '-'){

                visu.setText(visu.getText().toString() + nombre.getText().toString());
            }

        }

    }



    public void Calcul(View v) {
        TextView cal = findViewById(R.id.Ecran);
        double result = 0;
        String exp = cal.getText().toString();


        try{
            result = (double)engine.eval(exp);
        }catch(Exception e){
            Toast.makeText(this, "Exeption Raised", Toast.LENGTH_SHORT).show();
        }

        TextView affi = findViewById(R.id.Ecran2);
        exp = String.valueOf(result);
        affi.setText(exp);

        if(verifFloat(exp)){
            databasehelper.insertData(cal.getText().toString(), affi.getText().toString());
        }

    }

    public void suppr(View v) {
        TextView cal = findViewById(R.id.Ecran);
        String exp = cal.getText().toString();
        if(exp.length() != 0) {
            exp = exp.substring(0, exp.length() - 1);
            cal.setText(exp);
        }
    }

    public void clear(View v){
        TextView cal = findViewById(R.id.Ecran);
        TextView result = findViewById(R.id.Ecran2);
        String exp = cal.getText().toString();
        String exp2 = result.getText().toString();
        if(exp.length() != 0) {
            exp = exp.replace(exp,"");
            cal.setText(exp);

            exp2 = exp2.replace(exp2,"");
            result.setText(exp2);
        }
    }

    public void histo(View v){

        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        TextView cal = findViewById(R.id.Ecran);
        TextView result = findViewById(R.id.Ecran2);

        CharSequence reponse = result.getText().toString();
        CharSequence expression = cal.getText().toString();

        outState.putCharSequence("reponse", reponse);
        outState.putCharSequence("expression", expression);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        TextView cal = findViewById(R.id.Ecran);
        TextView result = findViewById(R.id.Ecran2);

        CharSequence reponse = Objects.requireNonNull(savedInstanceState.getCharSequence("reponse")).toString();
        CharSequence expression = Objects.requireNonNull(savedInstanceState.getCharSequence("expression")).toString();

        cal.setText(expression);
        result.setText(reponse);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.graphbutton){
            startActivity(new Intent(this, GraphActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
