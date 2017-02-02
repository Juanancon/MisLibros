package com.juanancon.a2daw.mislibros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResumenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Aquí se asocia una vista a la actividad
        setContentView(R.layout.resumen_libro);
    }
}
