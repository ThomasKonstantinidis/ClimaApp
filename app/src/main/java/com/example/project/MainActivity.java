package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button airTypeButton, airIntensityButton, exitButton, helpButton;
    //static State state;

    private boolean on;
    private ImageButton plusButton, minusButton, openButton;
    private TextView temperatureText;
    private int temperature;
    private static final String CELCIUS = "°C", AIRTYPE = "Τύπος Αέρα: ", AIRINTENSITY = "Ένταση Αέρα: ";
    private boolean isRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //state = new State(25);

        this.plusButton  = (ImageButton)findViewById(R.id.plus);
        this.minusButton  = (ImageButton)findViewById(R.id.minus);
        this.openButton  = (ImageButton)findViewById(R.id.open);
        this.airTypeButton = (Button)findViewById(R.id.AirType);
        this.airIntensityButton = (Button)findViewById(R.id.AirIntensity);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Έξοδος: ")
                .setMessage("Θέλετε να απενεργοποιήσετε το κλιματιστικό;")
                .setNegativeButton("Όχι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        on = true;
                    }
                })
                .setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goodbye();
                    }
                }).create().show();
    }

    private void goodbye(){
        Context context = this;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        TextView message = new TextView(context);
        message.setText("Το κλιματιστικό \nαπενεργοποιήθηκε");
        message.setGravity(Gravity.CENTER_HORIZONTAL);
        alertDialogBuilder.setView(message);



        final AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        new CountDownTimer( 1000, 1000){
            @Override
            public void onTick(long l) {}

            @Override
            public void onFinish() {
                alert.dismiss();
            }
        }.start();
    }
}