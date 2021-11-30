package com.king.drawboard.draw;

import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class DrawLine extends Draw {


    private float downX;
    private float downY;
    private float lastX;
    private float lastY;

    public DrawLine(){
    }

    public void setPoint(PointF point0, PointF point1) {
        this.downX = point0.x;
        this.downY = point0.y;
        this.lastX = point1.x;
        this.lastY = point1.y;
    }

    @Override
    public void actionDown(Canvas canvas, float x, float y) {
        super.actionDown(canvas, x, y);
        downX = x;
        downY = y;
    }

    @Override
    public void actionMove(Canvas canvas, float x, float y) {
        super.actionMove(canvas, x, y);
        lastX = x;
        lastY = y;
        canvas.drawLine(downX, downY, lastX, lastY, paint);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(downX, downY, lastX, lastY, paint);
    }
}
