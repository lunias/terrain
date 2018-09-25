package com.ethanaa.terrain.cube;

import java.util.ArrayList;

abstract class CallbackMC implements Runnable {

    private ArrayList<float []> vertices;

    void setVertices(ArrayList<float []> vertices) {

        this.vertices = vertices;
    }

    public ArrayList<float []> getVertices() {

        return this.vertices;
    }
}
