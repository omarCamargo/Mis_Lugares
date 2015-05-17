package com.example.omar.mislugares;

import android.os.Bundle;

import android.preference.PreferenceActivity;


/**
 * Created by Omar_Camargo on 16/05/15.
 */
public class Preferencias extends PreferenceActivity {

    @Override public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);

    }
}
