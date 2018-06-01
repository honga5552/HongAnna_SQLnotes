package com.example.anna.mycontactapp2018;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editPhone;
    EditText editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyContactApp", "MainActivity: setting up the layout");
        setContentView(R.layout.activity_main);     //uses xml to set up layout
        editName = findViewById(R.id.editText_name);
        editPhone = findViewById(R.id.editText_phone);
        editAddress = findViewById(R.id.editText_address);



        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated DatabaseHelper");

    }

    public void addData(View view){
        Log.d("MyContactApp", "MainActivity: Add contact button pressed");
        boolean isInserted = myDb.insertData(
                editName.getText().toString(),
                editPhone.getText().toString(),
                editAddress.getText().toString());

        if (isInserted == true){
            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Failed - contact not inserted", Toast.LENGTH_SHORT).show();

        }
    }

    public void viewData (View view) {
        Log.d("MyContactApp", "MainActivity: View contact button pressed");
        Cursor res = myDb.getAllData();

        if (res.getCount() == 0){
            showMessage("Error", "no data found in database");
        }

        StringBuffer buffer = new StringBuffer();

        res.moveToFirst();
        while(!res.isAfterLast()){
            buffer.append("ID: " + res.getString(0) + "\n");
            buffer.append("Name: " + res.getString(1) + "\n");
            buffer.append("Phone: " + res.getString(2) + "\n");
            buffer.append("Address: " + res.getString(3) + "\n\n");

            res.moveToNext();
        }


        Log.d("MyContactApp", "MainActivity: In viewData - buffer assembled");

        showMessage("Data", buffer.toString());
    }

    public void showMessage(String title, String message){
        Log.d("MyContactApp", "MainActivity: ShowMessage() - building alert dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    //probably have to change?
    public static final String Extra_Message = "com.example.honga5552.mycontactapp2018.MESSAGE";
    public void SearchRecord (View view){
        Log.d("MyContactApp", "MainActivity: Launching SearchActivity");
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(Extra_Message, editName.getText().toString());
        startActivity(intent);
        //intent == another screen
    }

}