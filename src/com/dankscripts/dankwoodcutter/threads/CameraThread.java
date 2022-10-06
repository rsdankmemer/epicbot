package com.dankscripts.dankwoodcutter.threads;

import com.epicbot.api.shared.util.Random;
import com.dankscripts.dankwoodcutter.DankWoodcutter;

public class CameraThread extends Thread implements Runnable {

    private DankWoodcutter script;
    private volatile boolean running;
    private long startTime, randomTime;


    public CameraThread(DankWoodcutter script) {
        this.script = script;
        running = true;
        startTime = System.currentTimeMillis();
        randomTime = Random.nextInt(0, 270000);
    }

    @Override
    public void run() {
        while(running) {
            if(System.currentTimeMillis() > (startTime + randomTime)) {
                System.out.println("Turning camera");
                script.getAPIContext().camera().setPitch(Random.nextInt(32, 98));
                script.getAPIContext().camera().setYaw(Random.nextInt(0, 2038));
                startTime = System.currentTimeMillis();
                randomTime = Random.nextInt(0, 270000);
            }
        }
    }

    public void setScriptExited(boolean scriptExited) {
        this.running = scriptExited;
    }

}
