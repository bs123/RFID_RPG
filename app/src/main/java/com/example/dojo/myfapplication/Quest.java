package com.example.dojo.myfapplication;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by dojo on 28.11.15.
 */
public class Quest {

    private static final String TAG = Quest.class.toString();
    private String hero;
    private String artefact; //Todo ENUM
    private String start;
    private String target;

    private boolean artefactReserved;
    private boolean artefactActivated;


    public Quest(String hero, String artefact, String start, String target) {
        this.hero = hero;
        this.artefact = artefact;
        this.start = start;
        this.target = target;

    }

    public String getArtefact() {
        return artefact;
    }

    public boolean isArtefactReserved() {
        return artefactReserved;
    }

    public void setArtefactReserved(boolean artefactReserved) {
        this.artefactReserved = artefactReserved;
    }

    public boolean isArtefactActivated() {
        return artefactActivated;
    }

    private void setArtefactActivated(boolean artefactActivated) {
        this.artefactActivated = artefactActivated;
    }

    public boolean toggleAndEvalArtefactReserved(String lastesMsg) {
        boolean temp = (lastesMsg.contains(hero) && lastesMsg.contains(artefact) && lastesMsg.contains(start));
        Log.i(TAG, "Quest - found artefact : " + temp);
        //only set once to true
        if (temp)
            setArtefactReserved(temp);

        return temp;
    }

    public boolean toggleAndEvalArtefactActivated(String latestMsg) {
        boolean temp = latestMsg.contains(hero) && latestMsg.contains(artefact) && latestMsg.contains(target);
        Log.i(TAG, "Quest - fullfilled : " + temp);

        //only set once to true
        if (temp)
            setArtefactActivated(temp);
        return temp;
    }

    public int countAvailableMonsters(String latestMsg) {

        boolean temp = (latestMsg.contains(hero) && isArtefactActivated());
        Log.i(TAG, "Quest - fullfilled : " + latestMsg);

        if (temp)
            return  1;

        return 0;

    }
}
