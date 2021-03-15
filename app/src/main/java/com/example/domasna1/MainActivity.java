package com.example.domasna1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, String> dictionary;
    private static int REQUEST_CODE = 46322;
    private static String FILE_NAME = "Recnik.txt";
    SearchView searcField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dictionary = new HashMap<>();

        createDictionaryInInternalStorage();
        loadDictionary();

        searcField = findViewById(R.id.searchQueryText);
        searcField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchDictionary(newText.toLowerCase());
                return false;
            }
        });

        Button addNewWordBtn = findViewById(R.id.addNewWord);
        addNewWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewWord.class);
                intent.putExtra("dictionary", dictionary);
                intent.putExtra("FileName", FILE_NAME);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    public void createDictionaryInInternalStorage(){
        try {
            File file = new File(getApplicationContext().getFilesDir(), FILE_NAME);
            if(!file.exists()) {
                Scanner scanner = new Scanner(getResources().openRawResource(R.raw.recnik));
                PrintStream out = new PrintStream(openFileOutput(FILE_NAME, MODE_PRIVATE));
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    out.println(line);
                }
                scanner.close();
                out.close();
            }
            else
            {
                //exist
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadDictionary(){
        try {
            Scanner scanner = new Scanner(openFileInput(FILE_NAME));
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] pieces = line.split("\\s+");
                String word1 = pieces[0].trim();
                String word2 = pieces[1].trim();
                if(!word1.equals("") && !word2.equals("")) {
                    dictionary.put(word1, word2);
                }
            }
            scanner.close();

            //TreeMap for sorting
            TreeMap<String, String> sorted = new TreeMap<>();
            sorted.putAll(dictionary);

            GridLayout gridLayout = findViewById(R.id.gridLayout);
            for(String i : sorted.keySet())
            {
                addLayoutElements(gridLayout, i, sorted);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    public void addLayoutElements(GridLayout gridLayout, String i, TreeMap sorted){
        TextView textView1 = new TextView(this);
        textView1.setText(i);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        gridLayout.addView(textView1);
        GridLayout.LayoutParams param =new GridLayout.LayoutParams();
        param.height = GridLayout.LayoutParams.WRAP_CONTENT;
        param.width = GridLayout.LayoutParams.WRAP_CONTENT;
        param.columnSpec = (GridLayout.spec(GridLayout.UNDEFINED, GridLayout.LEFT, 2f));
        textView1.setLayoutParams (param);

        TextView textView2 = new TextView(this);
        textView2.setText((String) sorted.get(i));
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        gridLayout.addView(textView2);
        GridLayout.LayoutParams param2 =new GridLayout.LayoutParams();
        param2.height = GridLayout.LayoutParams.WRAP_CONTENT;
        param2.width = GridLayout.LayoutParams.WRAP_CONTENT;
        param2.columnSpec = (GridLayout.spec(GridLayout.UNDEFINED, GridLayout.CENTER, 2f));
        param2.setGravity(Gravity.LEFT);
        textView2.setLayoutParams (param2);

        Button btn = new Button(this);
        btn.setText("Измени");
        btn.setTextColor(getResources().getColor(R.color.white));
        btn.setBackgroundColor(getResources().getColor(R.color.teal_700));
        btn.setAllCaps(false);
        gridLayout.addView(btn);
        GridLayout.LayoutParams param3 = new GridLayout.LayoutParams();
        param3.columnSpec = (GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT, 1f));
        btn.setLayoutParams(param3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChangeWord.class);
                intent.putExtra("MacedonianWord", i);
                intent.putExtra("dictionary", dictionary);
                intent.putExtra("FileName", FILE_NAME);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }
    @SuppressLint("NewApi")
    public void addLayoutElements(GridLayout gridLayout, String i, HashMap sorted){
        TextView textView1 = new TextView(this);
        textView1.setText(i);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        gridLayout.addView(textView1);
        GridLayout.LayoutParams param =new GridLayout.LayoutParams();
        param.height = GridLayout.LayoutParams.WRAP_CONTENT;
        param.width = GridLayout.LayoutParams.WRAP_CONTENT;
        param.columnSpec = (GridLayout.spec(GridLayout.UNDEFINED, GridLayout.LEFT, 2f));
        textView1.setLayoutParams (param);

        TextView textView2 = new TextView(this);
        textView2.setText((String) sorted.get(i));
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        gridLayout.addView(textView2);
        GridLayout.LayoutParams param2 =new GridLayout.LayoutParams();
        param2.height = GridLayout.LayoutParams.WRAP_CONTENT;
        param2.width = GridLayout.LayoutParams.WRAP_CONTENT;
        param2.columnSpec = (GridLayout.spec(GridLayout.UNDEFINED, GridLayout.CENTER, 2f));
        param2.setGravity(Gravity.LEFT);
        textView2.setLayoutParams (param2);

        Button btn = new Button(this);
        btn.setText("Измени");
        btn.setTextColor(getResources().getColor(R.color.white));
        btn.setBackgroundColor(getResources().getColor(R.color.teal_700));
        gridLayout.addView(btn);
        GridLayout.LayoutParams param3 = new GridLayout.LayoutParams();
        param3.columnSpec = (GridLayout.spec(GridLayout.UNDEFINED, GridLayout.RIGHT, 1f));
        btn.setLayoutParams(param3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChangeWord.class);
                intent.putExtra("MacedonianWord", i);
                intent.putExtra("dictionary", dictionary);
                intent.putExtra("FileName", FILE_NAME);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @SuppressLint("NewApi")
    public void searchDictionary(String word)
    {
      //  TextView macedonianWords = findViewById(R.id.macedonianWords);
      //  TextView englishWords = findViewById(R.id.englishWords);
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        int count = gridLayout.getChildCount();
        gridLayout.removeAllViews();
        TreeMap<String, String> sorted = new TreeMap<>();
        // Copy all data from hashMap into TreeMap
        sorted.putAll(dictionary);

        if (!word.equals("")) {
            if (dictionary.keySet().contains(word)) {
                addLayoutElements(gridLayout, word, dictionary);
            }
            else {
                gridLayout.removeAllViews();
            }
        } else {
            loadDictionary();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_CANCELED)
            {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        }
    }
}