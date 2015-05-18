package com.example.omar.mislugares;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import com.example.omar.mislugares.*;



/**
 * Created by omar-camargo on 18/05/15.
 */
public class VistaLugar extends ActionBarActivity {
    private long id;
    private Lugar lugar;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.vista_lugar);
    }
}
