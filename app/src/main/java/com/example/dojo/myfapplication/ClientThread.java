package com.example.dojo.myfapplication;

import com.example.MqttNRedClient;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by Sai on 28-Nov-15.
 */
public class ClientThread extends Thread {


    @Override
    public  void run() {
        String brokerUrl="tcp://rfid-rpg.skybus.io:1883";
        String clientId="app";
        boolean cleanSession = true;
        boolean quietMode = false;
        String userName="app";
        String password ="app";
        MqttNRedClient mqttClient = null;
        try {
            mqttClient = new MqttNRedClient(brokerUrl, clientId, cleanSession, quietMode, userName, password);
            mqttClient.subscribe("zombikiller", 0);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
