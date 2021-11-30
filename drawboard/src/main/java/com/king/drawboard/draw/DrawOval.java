package com.king.drawboard.draw;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class DrawOval extends Draw {

    private RectF rect;

    public DrawOval(){
        rect = new RectF();
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    @Override
    public void actionDown(Canvas canvas, float x, float y) {
        super.actionDown(canvas, x, y);
        rect.left = x;
        rect.top = y;
    }

    @Override
    public void actionMove(Canvas canvas, float x, float y) {
        super.actionMove(canvas, x, y);
        rect.right = x;
        rect.bottom = y;
        canvas.drawOval(rect, paint);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawOval(rect, paint);
    }
}
