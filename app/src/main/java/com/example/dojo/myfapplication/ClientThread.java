package com.example.dojo.myfapplication;

import com.example.MqttNRedClient;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
    private  List<String> syncUniqueRFidPresentList= Collections.synchronizedList(new ArrayList<String>());


    public ClientThread(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    class MyMqttNRedClient extends MqttNRedClient {
        MyMqttNRedClient(String brokerUrl, String clientId, boolean cleanSession, boolean quietMode, String userName, String password) throws MqttException {
            super(brokerUrl, clientId, cleanSession, quietMode, userName, password);
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws MqttException {
            String msgPayload  = new String(message.getPayload());
            String time = new Timestamp(System.currentTimeMillis()).toString();

            System.out.println("Time:\t" + time +
                    "  Topic:\t" + topic +
                    "  Message:\t" + msgPayload.toString()) ;

            //populate Set
            if(!syncUniqueRFidPresentList.contains(msgPayload)) {
                syncUniqueRFidPresentList.add(msgPayload);
            }
          //  syncUniqueRFidPresent.add(msgPayload);
           // uniqueRFidPresent.add(message.getPayload().toString());
      //      mainActivity.handOverRefreshedSet(syncUniqueRFidPresent);
            mainActivity.handOverRefreshedList(syncUniqueRFidPresentList);
        }
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
