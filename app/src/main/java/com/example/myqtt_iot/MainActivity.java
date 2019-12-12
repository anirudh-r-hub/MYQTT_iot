package com.example.myqtt_iot;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private Button LED1,LED2,LDR;
    private String payload_value="1024";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //ActionBar bar=getActionBar();
        //bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#004D40")));



        LED1=(Button) findViewById(R.id.LED1);
        LED1.setBackgroundColor(Color.BLUE);


        LDR=(Button) findViewById(R.id.LDR);
        LDR.setBackgroundColor(Color.BLUE);

        LED2=(Button) findViewById(R.id.LED2);
        LED2.setBackgroundColor(Color.BLUE);
        //################################################################

        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client =
                new MqttAndroidClient(MainActivity.this , "tcp://tailor.cloudmqtt.com:13968",
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
                    Toast.makeText(getApplicationContext(),"inside onSuccess",Toast.LENGTH_LONG).show();

                    // We are connected
                    // Log.d(TAG, "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(getApplicationContext(),"inside onFailure",Toast.LENGTH_LONG).show();


                    // Something went wrong e.g. connection timeout or firewall problems
                    // Log.d(TAG, "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        //######################################################################

                LED1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LDR.setText("ON LDR");
                LDR.setBackgroundColor(Color.BLUE);
                String topic = "led1";
                if(LED1.getText().toString().equals("ON LED1")) {
                    payload_value = "0";
                    LED1.setBackgroundColor(Color.CYAN);
                    LED1.setText("OFF LED1");
                }
                else
                {
                    payload_value = "1024";
                    LED1.setBackgroundColor(Color.BLUE);
                    LED1.setText("ON LED1");

                }

                byte[] encodedPayload = new byte[0];
                try {
                    //Toast.makeText(getApplicationContext(),"inside onPublish",Toast.LENGTH_LONG).show();

                    encodedPayload = payload_value.getBytes("UTF-8");
                    MqttMessage message1 = new MqttMessage(encodedPayload);
                    MqttMessage message2=new MqttMessage("OFF".getBytes());
                    client.publish("ldr",message2);
                    client.publish(topic, message1);


                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });

    //##################################################################################################
        LED2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = "led2";
                if(LED2.getText().toString().equals("ON LED2")) {
                    payload_value = "0";
                    LED2.setBackgroundColor(Color.CYAN);
                    LED2.setText("OFF LED2");
                }
                else
                {
                    payload_value = "1024";
                    LED2.setBackgroundColor(Color.BLUE);
                    LED2.setText("ON LED2");

                }

                byte[] encodedPayload = new byte[0];
                try {
                    //Toast.makeText(getApplicationContext(),"inside onPublish",Toast.LENGTH_LONG).show();

                    encodedPayload = payload_value.getBytes("UTF-8");
                    MqttMessage message1 = new MqttMessage(encodedPayload);
                    MqttMessage message2=new MqttMessage("OFF".getBytes());
                    client.publish("ldr",message2);
                    client.publish(topic, message1);


                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });
//#######################################################################################################
                LDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = "ldr";
                payload_value="ON";
                String button_text=LDR.getText().toString();
                if(button_text.equals("OFF LDR"))
                {
                    payload_value="OFF";
                    LDR.setText("ON LDR");
                    LED1.setText("ON LED1");
                    LED1.setBackgroundColor(Color.BLUE);
                    LDR.setBackgroundColor(Color.BLUE);

                }
                else
                {
                    LED1.setText("OFF LED1");
                    LED1.setBackgroundColor(Color.CYAN);

                    LDR.setText("OFF LDR");
                    LDR.setBackgroundColor(Color.CYAN);
                }


                byte[] encodedPayload = new byte[0];
                try {
                    //Toast.makeText(getApplicationContext(),"inside onPublish",Toast.LENGTH_LONG).show();

                    encodedPayload = payload_value.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);

                    //else
                        client.publish(topic, message);
                        if(payload_value.equals("OFF"))
                        {
                            client.publish("led1",new MqttMessage("1024".getBytes()));
                        }
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });
   //################################################################################################

       /* button_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = "abc";
                int qos = 1;
                try {
                    IMqttToken subToken = client.subscribe(topic, qos);
                    subToken.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            Toast.makeText(getApplicationContext(),"Subscribed successfully",Toast.LENGTH_LONG).show();

                            // The message was published
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken,
                                              Throwable exception) {
                            // The subscription could not be performed, maybe the user was not
                            // authorized to subscribe on the specified topic e.g. using wildcards
                            Toast.makeText(getApplicationContext(),"Failed to subscribe",Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Toast.makeText(getApplicationContext(),new String(message.getPayload()),Toast.LENGTH_LONG).show();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });






    }*/

}
}
