package com.example.myqtt_iot;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button LED1,LED2,LDR,color_picker,FanSpeedPicker;
    private String payload_value="1024";
    private TextToSpeech myTTS;
    private SpeechRecognizer mySpeechrecognizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //#################################################################################
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                mySpeechrecognizer.startListening(intent);
            }
        });
        //###################################################################################



        LED1=(Button) findViewById(R.id.LED1);
        LED1.setBackgroundColor(Color.BLUE);


        LDR=(Button) findViewById(R.id.LDR);
        LDR.setBackgroundColor(Color.BLUE);

        LED2=(Button) findViewById(R.id.LED2);
        LED2.setBackgroundColor(Color.BLUE);

        //#####################################################
       /* color_picker=(Button) findViewById(R.id.Color_picker);

        color_picker.setBackgroundColor(Color.BLUE);
        color_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });*/
        //#####################################################
        FanSpeedPicker=(Button) findViewById(R.id.FanSpeedPicker);

       FanSpeedPicker.setBackgroundColor(Color.BLUE);
       /* FanSpeedPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Main3Activity.class);
                startActivity(intent);
            }
        });*/

        //#######################################################################################
        initialise_text_to_speech();
        initialise_speech_recognizer();
        // speak("hello");
        //#######################################################################################

        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client=getConnectionRB(clientId);;

        //###########################################################################################
        //###########################################################################################

                LED1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LDR.setText("ON LDR");
                LDR.setBackgroundColor(Color.BLUE);
                String topic = "led1";
                if(LED1.getText().toString().equals("ON LED1")) {
                    payload_value = "1024";
                    LED1.setBackgroundColor(Color.CYAN);
                    LED1.setText("OFF LED1");
                }
                else
                {
                    payload_value = "0";
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
                    /*MqttMessage message2=new MqttMessage("OFF".getBytes());
                    client.publish("ldr",message2);*/
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
        //subscribe and callback code
       FanSpeedPicker.setOnClickListener(new View.OnClickListener() {
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


}

    //######################################################################################################################

    public MqttAndroidClient getConnectionRB(String clientId)  {
        try {
            /*MqttAndroidClient client =
                    new MqttAndroidClient(MainActivity.this, "tcp://192.168.1.104:1883",
                            clientId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            options.setUserName("pi");
            options.setPassword("mike".toCharArray());
            IMqttToken token = client.connect(options);
            /*if(!token.isComplete()) {
                Toast.makeText(this, "cannot connect to raspberry pi", Toast.LENGTH_LONG).show();*/

                MqttAndroidClient client1 =
                        new MqttAndroidClient(MainActivity.this, "tcp://tailor.cloudmqtt.com:13968",
                                clientId);

                MqttConnectOptions options1 = new MqttConnectOptions();
                options1.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
                options1.setUserName("ghleymma");
                options1.setPassword("jmvoCCetDGiy".toCharArray());
               IMqttToken token1 = client1.connect(options1);
                Toast.makeText(this, "Connected to CloudMQTT", Toast.LENGTH_LONG).show();

                //return client1;

           // }*/
            return client1;
        }
        catch(MqttException  e)
        {

        }

        return null;
    }


    //#######################################################################################################################
    private void initialise_text_to_speech()
    {
        myTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(myTTS.getEngines().size()==0)
                {
                    Toast.makeText(MainActivity.this,"there is no speech engine",Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    myTTS.setLanguage(Locale.US);
                    //speak("how are you?");
                }
            }
        });
    }

    private void speak(String message)
    {
        if(Build.VERSION.SDK_INT>=21)
            myTTS.speak(message,TextToSpeech.QUEUE_ADD,null,null);


    }

    private void initialise_speech_recognizer()
    {
        if(SpeechRecognizer.isRecognitionAvailable(this))
        {
            mySpeechrecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mySpeechrecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    List<String> speech_results=results.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                    );
                    procesResult(speech_results.get(0));

                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }
    private void procesResult(String command)
    {
        command=command.toLowerCase();



        if(command.indexOf("led")!=-1)
        {
            //Toast.makeText(MainActivity.this,command,Toast.LENGTH_LONG).show();
            if(command.indexOf("1")!=-1)
            {

                if(command.indexOf("on")!=-1)
                {

                    LED1.performClick();
                    speak("Turning on l e d one");
                }
                if(command.indexOf("off")!=-1)
                {
                    LED1.performClick();
                    speak("Turning off the l e d one");
                }
            }


            if(command.indexOf("colour")!=-1 || command.indexOf("color")!=-1)
            {

                if(command.indexOf("on")!=-1)
                {
                    speak("Turning on the colour l e d ");
                    LED2.performClick();
                }
                if(command.indexOf("off")!=-1)
                {
                    speak("Turning off the colour l e d ");
                    LED2.performClick();
                }
            }


           // Toast.makeText(MainActivity.this,command,Toast.LENGTH_LONG).show();

        }
       else if(command.indexOf("ldr")!=-1 || command.indexOf("india")!=-1)
        {
            //Toast.makeText(MainActivity.this,command,Toast.LENGTH_LONG).show();
            if(command.indexOf("on")!=-1)
            {

                LDR.performClick();
                speak("Turning on the l d r");
            }
            if(command.indexOf("off")!=-1)
            {
                LED1.performClick();
                speak("Turning off the l d r");
            }
        }
        else
        {

            speak(" sorry,cannot understand");
            //Toast.makeText(MainActivity.this,command,Toast.LENGTH_LONG).show();
        }
    }




}
