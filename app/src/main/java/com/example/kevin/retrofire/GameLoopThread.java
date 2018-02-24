package com.example.kevin.retrofire;


import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoopThread extends Thread {
    private final static int MAX_FPS = 50;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    private final BattleZoneView view;
    private final SurfaceHolder surfaceHolder;
    private boolean isRunning = false;
    private Canvas canvas = null;

    public GameLoopThread(BattleZoneView view) {
        this.view = view;
        this.surfaceHolder = view.getHolder();
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                canvas = surfaceHolder.lockCanvas();

                synchronized (surfaceHolder) {
                    long startTime = System.currentTimeMillis();

                    view.update();

                    view.draw(canvas);

                    long timeDiff = System.currentTimeMillis() - startTime;
                    int sleepTime = (int) (FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            Log.e(getClass().getName(), "Error Thread sleep");
                        }
                    }

                    while (sleepTime < 0) {
                        this.view.update();
                        sleepTime += FRAME_PERIOD;
                    }

                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
