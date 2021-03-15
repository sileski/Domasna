package com.example.domasna1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

public class ChangeWord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_word);

        Intent intent = getIntent();
        HashMap<String, String> dictionary = (HashMap<String, String>)intent.getSerializableExtra("dictionary");
        String macedonianWord = (String)intent.getExtras().getString("MacedonianWord").trim();
        String FILE_NAME = (String)intent.getExtras().getString("FileName");

        EditText macedonianWordText = findViewById(R.id.macedonianWordHolder);
        EditText englishWordText = findViewById(R.id.englishWordHolder);
        macedonianWordText.setText(macedonianWord);


        Button editBtn = findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String englishWord = englishWordText.getText().toString().toLowerCase().trim();

                if(dictionary.containsKey(macedonianWord))
                {
                    if(!englishWord.equals("") || !englishWord.equals(dictionary.get(macedonianWord)))
                    {
                        try {
                            dictionary.remove(macedonianWord);
                            dictionary.put(macedonianWord.trim(), englishWordText.getText().toString().trim());
                            PrintStream out = new PrintStream(openFileOutput(FILE_NAME, MODE_PRIVATE));
                            for(String i :  dictionary.keySet())
                            {
                                out.println(i + " " +  dictionary.get(i));
                                System.out.println(i + " " +  dictionary.get(i));
                            }
                            out.close();
                            englishWordText.setText("");
                            Toast.makeText(getApplicationContext(), "Зборот е променат", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Внеси го новиот англиски збор", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Грешка", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dictionary.containsKey(macedonianWord))
                {
                    try {
                        dictionary.remove(macedonianWord);
                        PrintStream out = new PrintStream(openFileOutput(FILE_NAME, MODE_PRIVATE));
                        for(String i :  dictionary.keySet())
                        {
                            out.println(i + " " +  dictionary.get(i));
                            System.out.println(i + " " +  dictionary.get(i));
                        }
                        out.close();
                        englishWordText.setText("");
                        Toast.makeText(getApplicationContext(), "Зборот е избришан", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}