package org.top.todolistapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.top.todolistapp.deps.ApplicationDependencies;
import org.top.todolistapp.sqlite.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGoToDoAppBtnClick(View view) {
        Toast.makeText(this, "Not implemented", Toast.LENGTH_LONG).show();
    }

    public void onGoToDoTestBtnClick(View view) {
        startActivity(new Intent(this, TestToDoActivity.class));
    }
}
