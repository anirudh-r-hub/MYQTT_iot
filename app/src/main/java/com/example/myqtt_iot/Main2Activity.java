package com.example.myqtt_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class Main2Activity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    View view;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView=(ImageView) findViewById(R.id.color_picker);
        textView=(TextView) findViewById(R.id.text_view);
        view=(View) findViewById(R.id.view);
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache(true);

        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client =
                new MqttAndroidClient(Main2Activity.this , "tcp://tailor.cloudmqtt.com:13968",
                        clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        options.setUserName("ghleymma");
        options.setPassword("jmvoCCetDGiy".toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //Toast.makeText(getApplicationContext(),"inside onSuccess",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    //Toast.makeText(getApplicationContext(),"inside onFailure",Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }


        //image view

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE)
                {
                    bitmap=imageView.getDrawingCache();
                    int pixel=bitmap.getPixel((int)event.getX(),(int)event.getY());

                    int r= Color.red(pixel);
                    int g=Color.green(pixel);
                    int b=Color.blue(pixel);


                    String hex="#"+Integer.toHexString(pixel);
                    view.setBackgroundColor(Color.rgb(r,g,b));
                    textView.setText("RGB: "+r+", "+g+", "+b+"\nHEX"+hex);
                    String payload_value=hex;
                    byte[] encodedPayload = new byte[0];
                    try {
                        //Toast.makeText(getApplicationContext(),"inside onPublish",Toast.LENGTH_LONG).show();

                        encodedPayload = payload_value.getBytes("UTF-8");
                        MqttMessage message1 = new MqttMessage(encodedPayload);
                        client.publish("LED2", message1);


                    } catch (UnsupportedEncodingException | MqttException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
    }
}
