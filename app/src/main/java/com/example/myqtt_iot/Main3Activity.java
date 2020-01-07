package com.example.myqtt_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    private TextView FanSeekBarValue;
    private SeekBar FanSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        FanSeekBarValue = (TextView)findViewById(R.id.FanSeekBarValue);
        FanSeekBar = (SeekBar)findViewById(R.id.FanSeekBar);


        FanSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                FanSeekBarValue.setText(""+Math.round(progress*10.24));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
