package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button airTypeButton, airIntensityButton, exitButton, helpButton;

    private boolean on;
    private ImageButton plusButton, minusButton, openButton;
    private TextView temperatureText;
    static State state;
    private int temperature;
    private static final String CELCIUS = "°C", AIRTYPE = "Τύπος Αέρα: ", AIRINTENSITY = "Ένταση Αέρα: ";
    private boolean isRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        state = new State(25, "Κρύος Αέρας","Μέτρια Ένταση");

        this.on = false;
        this.plusButton  = (ImageButton)findViewById(R.id.plus);
        this.minusButton  = (ImageButton)findViewById(R.id.minus);
        this.openButton  = (ImageButton)findViewById(R.id.open);
        this.exitButton  = (Button) findViewById(R.id.exit);
        this.airTypeButton = (Button)findViewById(R.id.AirType);
        this.airIntensityButton = (Button)findViewById(R.id.AirIntensity);
        this.temperatureText = (TextView)findViewById(R.id.Temperature);
        this.temperature = state.getTemperature();

    }

    public void onStart(){
        super.onStart();
        this.airIntensityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(on){
                    Intent intent = new Intent(MainActivity.this, AirIntensityActivity.class);
                    intent.putExtra("currentIntensity", state.getAirIntensity());
                    startActivity(intent);
                }
            }
        });

        this.airTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to go to an other class
                if(on) {
                    Intent intent = new Intent(MainActivity.this, AirType.class);
                    intent.putExtra("currentType", state.getAirType());
                    startActivity(intent);
                }
            }
        });

        this.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(on && temperature<28){
                    temperature++;
                    temperatureText.setText(temperature + CELCIUS);
                }
            }
        });

        this.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(on && temperature>16){
                    temperature--;
                    temperatureText.setText(temperature + CELCIUS);
                }
            }
        });

        this.openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(on){
                    // Same situation as the back pressed
                    goodbye();
                }
                else{
                    temperatureText.setText(state.getTemperature() + CELCIUS);
                    on = true;
                }
            }
        });

        this.exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        this.helpButton = (Button)findViewById(R.id.help);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpPanel(R.layout.help_panel);
            }
        });

    }

    public void onBackPressed(){
        if(on) {
            new AlertDialog.Builder(this)
                    .setTitle("Έξοδος: ")
                    .setMessage("Θέλετε να απενεργοποιήσετε το κλιματιστικό;")
                    .setNegativeButton("Όχι", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { on = true; }
                    })
                    .setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            goodbye();
                        }
                    }).create().show();
        }else{
            Context context = this;

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            TextView message = new TextView(context);
            message.setText("Αντίο");
            message.setGravity(Gravity.CENTER);
            alertDialogBuilder.setView(message);

            temperatureText.setText(null);

            final AlertDialog alert = alertDialogBuilder.create();
            alert.show();

            new CountDownTimer( 500, 500){
                @Override
                public void onTick(long l) {}

                @Override
                public void onFinish() {
                    alert.dismiss();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }.start();
        }

    }

    private void goodbye(){
        Context context = this;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        TextView message = new TextView(context);
        message.setText("Το κλιματιστικό \nαπενεργοποιήθηκε");
        message.setGravity(Gravity.CENTER_HORIZONTAL);
        alertDialogBuilder.setView(message);

        temperatureText.setText(null);
        on = false;


        final AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        new CountDownTimer( 500, 500){
            @Override
            public void onTick(long l) {}

            @Override
            public void onFinish() {
                alert.dismiss();
            }
        }.start();
    }

    public void helpPanel(int xmlFile){
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View view = inflater.inflate(xmlFile, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Πίσω", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}