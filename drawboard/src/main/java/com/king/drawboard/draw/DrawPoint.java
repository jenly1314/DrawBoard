package com.king.drawboard.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.text.TextUtils;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class DrawPoint extends Draw {

    private float lastX;
    private float lastY;

    public DrawPoint() {
    }

    public void setPoint(PointF point) {
        this.lastX = point.x;
        this.lastY = point.y;
    }

    @Override
    public void actionDown(Canvas canvas, float x, float y) {
        super.actionDown(canvas, x, y);
        lastX = x;
        lastY = y;
    }

    @Override
    public void actionMove(Canvas canvas, float x, float y) {
        super.actionMove(canvas, x, y);
        canvas.drawPoint(x, y, paint);
        lastX = x;
        lastY = y;
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawPoint(lastX, lastY, paint);
    }
}
