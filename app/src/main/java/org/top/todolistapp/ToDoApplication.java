package org.top.todolistapp;

import android.app.Application;

import org.top.todolistapp.deps.ApplicationDependencies;

public class ToDoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationDependencies.initApplicationContext(this);
    }
}
