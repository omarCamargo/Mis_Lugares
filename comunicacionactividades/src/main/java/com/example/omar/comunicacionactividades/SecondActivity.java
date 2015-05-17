package com.example.omar.comunicacionactividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Omar_Camargo on 26/04/15.
 */
public class SecondActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        Intent mIntent = getIntent();
        Bundle Extras = getIntent().getExtras();
        //String message = mIntent.getStringExtra("userName");
        String message2 = Extras.getString("userName");

        TextView mText = (TextView) findViewById(R.id.lbl_hello);
        mText.setText("Hola "+message2+" ¿Aceptas las condiciones?");
        mText.setTextSize(18);
    }


    public void retrieveMessage(View view){

        int id = view.getId();

        Intent rtrveIntent = new Intent();


        if(id == R.id.btn_acpt){
            rtrveIntent.putExtra( "resultado", getResources().getString(R.string.str_Acpt) );
        }else if(id==R.id.btn_rjct){
            rtrveIntent.putExtra( "resultado",getResources().getString(R.string.str_rjctd) );
        }
        setResult(RESULT_OK,rtrveIntent);
        finish();

    }
}
