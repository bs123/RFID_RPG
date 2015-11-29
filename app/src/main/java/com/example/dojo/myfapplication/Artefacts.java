package com.example.dojo.myfapplication;

public enum Artefacts {
    SWORD("3000E20020648118011816206C22", 150), BATTLE_AX("3000E20020648118012017406006", 100), BOW("3000E20020648118012017606008", 50), CLUB("3000E200206481180120196049D4", 20);


    private final String rfid;
    private final int points;

    Artefacts(String rfid, int points) {

        this.rfid= rfid;
        this.points = points;
    }
    public String getRfid() {
        return rfid;
    }

    public int getPoints() {
        return points;
    }

}
