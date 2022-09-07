package com.fersoft.growthbook;

import java.nio.charset.StandardCharsets;
import java.util.zip.Checksum;

public final class FNVHash implements Checksum {
    private final static int INITIAL_VALUE = 0x811C9DC5;
    private final static int MULTIPLIER = 16777619;

    private int hash = INITIAL_VALUE;

    public double hash(String text) {
        update(text.getBytes(StandardCharsets.UTF_8), 0, text.length());
        double n = getValue();
        reset();
        return (n % 1000) / 1000;
    }

    @Override
    public void update(int b) {
        hash ^= 0xff & b;
        hash *= MULTIPLIER;
    }

    @Override
    public void update(byte[] b, int off, int len) {
        int stop = off + len;
        for (int i = off; i < stop; i++) {
            hash ^= 0xff & b[i];
            hash *= MULTIPLIER;

            // This optimization of the multiplication might work in C, but not in Java (~2 times slower)
            // hash += (hash << 1) + (hash << 4) + (hash << 7) + (hash << 8) + (hash << 24);
        }
    }

    @Override
    public long getValue() {
        return hash & 0xffffffffL;
    }

    @Override
    public void reset() {
        hash = INITIAL_VALUE;
    }

}
