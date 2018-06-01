package com.example.anna.mycontactapp2018;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText searchWord;
    TextView resultBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MyContactApp","SearchActivity: onCreate");
        super.onCreate(savedInstanceState);

      //  searchWord = findViewById(R.id.editText_search);

        myDb = new DatabaseHelper(this);
        setContentView(R.layout.activity_search);

        //Get the intent that initialized this activity
        android.content.Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.Extra_Message);


        String results = getResults(message);

        //Capture the layout textview and set the string as the text
        //android.widget.TextView textview = findViewById(R.id.textView_results);
        //textview.setText(message);
       // showMessage("results", results);

        resultBox =  (TextView)findViewById(R.id.textView_results);


        resultBox.setText(results);



    }

    public String getResults (String searchString) {
        Cursor res = myDb.getAllData();

        if (res.getCount() == 0){
            showMessage("Error", "no data found in database");

            return new String("");

        }

        StringBuffer buffer = new StringBuffer();

        res.moveToFirst();
        while(!res.isAfterLast()){
            if(res.getString(1).contains(searchString)) {
                buffer.append("ID: " + res.getString(0) + "\n");
                buffer.append("Name: " + res.getString(1) + "\n");
                buffer.append("Phone: " + res.getString(2) + "\n");
                buffer.append("Address: " + res.getString(3) + "\n\n");
            }
            res.moveToNext();
        }


        if(buffer.length() == 0){
            buffer.append("Sorry, no contacts found");
        }


        return buffer.toString();
    }

    public void showMessage(String title, String message){
        Log.d("MyContactApp", "SearchActivity: ShowMessage() - building alert dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void doSearch(View view){
        Log.d("MyContactApp","SearchActivity: in doSearch" );
        // searchWorld is null
        String searchText=searchWord.getText().toString();
        String results = getResults(searchText);

        //Capture the layout textview and set the string as the text
        showMessage("results", results);

    }


}
