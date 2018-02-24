package com.example.kevin.retrofire;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BattleZoneView extends SurfaceView implements SurfaceHolder.Callback {
    private BattleZoneModel model = null;
    private GameLoopThread thread;

    public BattleZoneView(Context context, AttributeSet attrs) {
        super(context, attrs);

        thread = new GameLoopThread(this);

        // Permet l'appel aux méthodes surfaceCreated, changed et destroyed.
        getHolder().addCallback(this);

        setWillNotDraw(false);
    }

    public void setModel(BattleZoneModel model) {
        this.model = model;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        model.setHeight(this.getHeight());
        model.setWidth(this.getWidth());
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (model != null)
            model.drawAll(canvas);
    }

    public void update() {
        if (model != null) model.update();
    }

}
