package com.king.drawboard.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.king.drawboard.action.MotionAction;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public abstract class Draw implements MotionAction {

    Paint paint;

    public Draw(){

    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    @Override
    public void actionDown(Canvas canvas, float x, float y) {
        Log.d(getClass().getSimpleName(),String.format("actionDown: %f, %f", x, y));
    }

    @Override
    public void actionMove(Canvas canvas, float x, float y) {
        Log.d(getClass().getSimpleName(),String.format("actionMove: %f, %f", x, y));
    }

    @Override
    public void actionUp(Canvas canvas, float x, float y) {
        Log.d(getClass().getSimpleName(),String.format("actionUp: %f, %f", x, y));
    }

    public abstract void draw(Canvas canvas);
}
