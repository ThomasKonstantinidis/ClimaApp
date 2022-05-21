package com.example.project;

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

import static com.example.project.MainActivity.state;

public class AirType extends AppCompatActivity {

    private CheckBox[] choices;
    private Button back;
    private String type;
    private boolean selected;
    private int key;
    private boolean[] overlap;
    private HashMap<Integer, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_type);
        String incoming = getIntent().getStringExtra("currentType");
        this.back = (Button)findViewById(R.id.back);
        this.selected = false;
        this.map = new HashMap<>();
        this.map.put(0, "Ζεστός Αέρας");
        this.map.put(1, "Κρύος Αέρας");
        this.map.put(2, "Ανεμιστήρας");

        this.choices = new CheckBox[3];
        this.choices[0] = (CheckBox)findViewById(R.id.warm_air);
        this.choices[1] = (CheckBox)findViewById(R.id.cold_air);
        this.choices[2] = (CheckBox)findViewById(R.id.fan);
        for(CheckBox b: this.choices){
            b.setOnClickListener(mListener);
        }

        this.overlap = new boolean[3];
        Arrays.fill(this.overlap, false);
        key = getKeyByValue(this.map, incoming);
        this.choices[key].setChecked(true);
        this.overlap[key] = true;
        setType(key);
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

    private void setType(int key){
        this.back.setOnClickListener(view -> {
            for(int i=0; i< choices.length; i++) {
                if (choices[i].isChecked()) {
                    type = map.get(i);
                    selected = true;
                }
            }
            if(selected){
                state.setAirType(type);
                Intent intent = new Intent();
                intent.putExtra("type", state.getAirType());
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

