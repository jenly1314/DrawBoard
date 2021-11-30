package com.king.drawboard.draw;

import android.graphics.Canvas;
import android.graphics.Path;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class DrawPath extends Draw {

    private Path path;

    private float lastX;
    private float lastY;

    public DrawPath() {
        path = new Path();
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public void actionDown(Canvas canvas, float x, float y) {
        super.actionDown(canvas, x, y);
        path.moveTo(x, y);
        lastX = x;
        lastY = y;
    }

    @Override
    public void actionMove(Canvas canvas, float x, float y) {
        super.actionMove(canvas, x, y);
        path.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2);
        canvas.drawPath(path, paint);
        lastX = x;
        lastY = y;
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }
}
