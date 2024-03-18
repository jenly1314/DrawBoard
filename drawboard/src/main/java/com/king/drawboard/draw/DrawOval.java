package com.king.drawboard.draw;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * 绘制椭圆
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
public class DrawOval extends Draw {

    private RectF rect;

    public DrawOval() {
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
        draw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawOval(rect, paint);
    }
}
