package com.example;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Test;

import static org.junit.Assert.*;

public class MqttNRedClientTest {


    String brokerUrl="tcp://rfid-rpg.skybus.io:1883";
    String clientId="app";
    boolean cleanSession = true;
    boolean quietMode = false;
    String userName="app";
    String password ="app";


    @Test
    public void testSubscribe() throws MqttException {

        MqttNRedClient mqttClient = new MqttNRedClient(brokerUrl, clientId, cleanSession, quietMode, userName, password);
        assertNotNull(mqttClient);

    }

        @Test
    public void testSubscribeQueue() throws Exception {
        MqttNRedClient mqttClient = new MqttNRedClient(brokerUrl, clientId, cleanSession, quietMode, userName, password);
        mqttClient.subscribe("zombikiller", 0);

    }
}