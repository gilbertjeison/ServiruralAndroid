package com.meysi.desarrollo.servirural_android;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class activity_linea extends AppCompatActivity implements View.OnClickListener{

    Button btnRecaudo;
    Button btnVentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_linea);

        btnRecaudo = (Button)findViewById(R.id.btnRecaudo);
        btnVentas = (Button)findViewById(R.id.btnVentas);

        btnRecaudo.setOnClickListener(this);
        btnVentas.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnRecaudo:
                CargarRecaudos();
                break;

            case  R.id.btnVentas:
                onClickWhatsApp();
                break;



        }
    }

    private void CargarRecaudos() {
        startActivity(new Intent
                (getApplicationContext(), ListRecaudosActivity.class));
    }

    public void onClickWhatsApp() {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {


            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";

            try {
                PackageInfo info=pm.getPackageInfo("com.gbwhatsapp", PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e1) {
                e1.printStackTrace();
            }
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.gbwhatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }



}
