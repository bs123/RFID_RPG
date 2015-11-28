package com.example.dojo.myfapplication;

import com.example.MqttNRedClient;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sai on 28-Nov-15.
 */
public class ClientThread extends Thread {


    private final MainActivity mainActivity;
    //String brokerUrl = "tcp://rfid-rpg.skybus.io:1883";
    String brokerUrl = "tcp://54.93.174.164:1883";
    String clientId = "app";
    boolean cleanSession = true;
    boolean quietMode = false;
    String userName = "app";
    String password = "app";
    MqttNRedClient mqttClient = null;

    public ClientThread(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    class MyMqttNRedClient extends MqttNRedClient {
        MyMqttNRedClient(String brokerUrl, String clientId, boolean cleanSession, boolean quietMode, String userName, String password) throws MqttException {
            super(brokerUrl, clientId, cleanSession, quietMode, userName, password);
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws MqttException {
            String time = new Timestamp(System.currentTimeMillis()).toString();
            System.out.println("Time:\t" +time +
                    "  Topic:\t" + topic +
                    "  Message:\t" + new String(message.getPayload()) +
                    "  QoS:\t" + message.getQos());

            Set<String> uniqueRFidPresent = new HashSet<String>();
           // Set<String> syncUniqueRFidPresent = Collections.synchronizedSet(uniqueRFidPresent);

            //populate Set
            //syncUniqueRFidPresent.add(message.getPayload().toString());
            uniqueRFidPresent.add(message.getPayload().toString());
            mainActivity.handOverRefreshedSet(uniqueRFidPresent);
        }

        //       "  Message:\t" + new String(message.getPayload()) +
        //       "  QoS:\t" + message.getQos());
    }


    // @Override
    public void run() {

        MyMqttNRedClient mqttClient = null;
        try {
            mqttClient = new MyMqttNRedClient(brokerUrl, clientId, cleanSession, quietMode, userName, password);
            mqttClient.subscribe("zombikiller", 0);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        //   } catch (MqttException e) {
        //      e.printStackTrace();
        //}
    }
}
