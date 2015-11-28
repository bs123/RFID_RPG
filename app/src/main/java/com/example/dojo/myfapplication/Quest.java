package com.example.dojo.myfapplication;

/**
 * Created by dojo on 28.11.15.
 */
public class Quest {

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

    public String getHero() {
        return hero;
    }


    public String getStart() {
        return start;
    }


    public String getTarget() {
        return target;
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

    public void setArtefactActivated(boolean artefactActivated) {
        this.artefactActivated = artefactActivated;
    }

}
