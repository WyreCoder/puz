package com.example.puz;

public class Helpers {
    public static void setTimeout(Runnable runnable, int delay){
        final Runnable run = runnable;
        final int del = delay;
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(del);
                    run.run();
                } catch (Exception exc) {
                    System.err.println(exc);
                }
            }
        }.start();
    }
}
