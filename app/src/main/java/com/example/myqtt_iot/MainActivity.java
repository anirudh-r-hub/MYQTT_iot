package com.example.myqtt_iot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button ON,OFF,LDR;
    private String payload_value="1024";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        ON=(Button) findViewById(R.id.ON);

        OFF=(Button) findViewById(R.id.OFF);

        LDR=(Button) findViewById(R.id.LDR);

        //################################################################

        String clientId = MqttClient.generateClientId();
        ON=(Button)findViewById(R.id.ON);
        OFF=(Button) findViewById(R.id.OFF);
        LDR=(Button) findViewById(R.id.LDR);
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

                ON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = "mobile";
                payload_value="0";
                byte[] encodedPayload = new byte[0];
                try {
                    Toast.makeText(getApplicationContext(),"inside onPublish",Toast.LENGTH_LONG).show();

                    encodedPayload = payload_value.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = "mobile";
                payload_value="1024";
                byte[] encodedPayload = new byte[0];
                try {
                    Toast.makeText(getApplicationContext(),"inside onPublish",Toast.LENGTH_LONG).show();

                    encodedPayload = payload_value.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        LDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = "ldr";
                payload_value="ON";
                byte[] encodedPayload = new byte[0];
                try {
                    Toast.makeText(getApplicationContext(),"inside onPublish",Toast.LENGTH_LONG).show();

                    encodedPayload = payload_value.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });

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
