package com.mit.fault.analysis.system.entities;

public class Base {
    float kvRating;
    float mvaRating;

    public Base(float kvRating, float mvaRating) {
        this.kvRating = kvRating;
        this.mvaRating = mvaRating;
    }

    public float getKvRating() {
        return kvRating;
    }

    public float getMvaRating() {
        return mvaRating;
    }
}
