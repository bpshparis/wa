package com.bpshparis.wa;

import java.util.ArrayList;
import java.util.List;

public class Horaire{

    String jour = "";
    List<String> heures = new ArrayList<String>();

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public List<String> getHeures() {
        return heures;
    }

    public void setHeures(List<String> heures) {
        this.heures = heures;
    }
    
}