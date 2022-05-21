package com.example.project;

import static com.example.project.MainActivity.state;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AirIntensityActivity extends AppCompatActivity {

    private Button back;
    private CheckBox[] choices;
    private boolean[] overlap;
    private HashMap<Integer, String> map;
    private String intensity;
    private boolean selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_intensity);
        String incoming = getIntent().getStringExtra("currentIntensity");
        this.selected = false;
        this.map = new HashMap<>();
        this.map.put(0, "Χαμηλή Ένταση");
        this.map.put(1, "Μέτρια Ένταση");
        this.map.put(2, "Υψηλή Ένταση");
        this.choices = new CheckBox[3];
        this.back = (Button)findViewById(R.id.back2);

        this.choices[0] = (CheckBox)findViewById(R.id.low);
        this.choices[1] = (CheckBox)findViewById(R.id.medium);
        this.choices[2] = (CheckBox)findViewById(R.id.high);
        for(CheckBox b: this.choices){
            b.setOnClickListener(mListener);
        }

        this.overlap = new boolean[3];
        Arrays.fill(this.overlap, false);
        int key = getKeyByValue(this.map, incoming);
        this.choices[key].setChecked(true);
        this.overlap[key] = true;
        this.setIntensity();
   }

    private final View.OnClickListener mListener = (v) -> {
        int id = v.getId();
        for(int i=0; i< choices.length; i++){
            if(choices[i].getId() == id){
                choices[i].setChecked(true);
                overlap[i] = choices[i].isChecked();
            }
            else{
                choices[i].setChecked(false);
                overlap[i] = false;
            }
        }
    };

    private void setIntensity(){
        this.back.setOnClickListener(view -> {
            for(int i=0; i< choices.length; i++) {
                if (choices[i].isChecked()) {
                    intensity = map.get(i);
                    selected = true;
                }
            }
            if(selected){
                state.setAirIntensity(intensity);
                Intent intent = new Intent();
                intent.putExtra("type", state.getAirIntensity());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}