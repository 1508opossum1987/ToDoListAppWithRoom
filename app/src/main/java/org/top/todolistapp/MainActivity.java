package org.top.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.top.todolistapp.ui.ToDoListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGoToDoAppBtnClick(View view) {
        startActivity(new Intent(this, ToDoListActivity.class));
    }

    public void onGoToDoTestBtnClick(View view) {
        startActivity(new Intent(this, TestToDoActivity.class));
    }
}