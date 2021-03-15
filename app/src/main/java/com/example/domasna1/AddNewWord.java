package com.example.domasna1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class AddNewWord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);

        Intent intent = getIntent();
        HashMap<String, String> dictionary = (HashMap<String, String>)intent.getSerializableExtra("dictionary");
        String FILE_NAME = (String)intent.getExtras().getString("FileName");

        EditText macedonianWordText = (EditText) findViewById(R.id.macedonianWordNew);
        EditText englishWordText = (EditText)findViewById(R.id.newEnglishWord);

        Button save = findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String englishWord = englishWordText.getText().toString().toLowerCase().trim();
                String macedonianWord = macedonianWordText.getText().toString().toLowerCase().trim();

                if(!macedonianWord.equals("") || !englishWord.equals(""))
                {
                    if(!dictionary.containsKey(macedonianWord))
                    {
                        try {
                            dictionary.put(macedonianWord, englishWord);
                            PrintStream out = new PrintStream(openFileOutput(FILE_NAME, MODE_PRIVATE));
                            for(String i :  dictionary.keySet())
                            {
                                out.println(i + " " +  dictionary.get(i));
                                System.out.println(i + " " +  dictionary.get(i));
                            }
                            out.close();
                            englishWordText.setText("");
                            macedonianWordText.setText("");
                            Toast.makeText(getApplicationContext(), "Зборот е додаден во речникот", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Зборот веќе постои во речникот", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Внеси македонски збор и неговиот превод", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}