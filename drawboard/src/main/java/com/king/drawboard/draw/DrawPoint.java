package com.king.drawboard.draw;

import android.graphics.Canvas;

/**
 * 绘制点
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
public class DrawPoint extends Draw {

    public DrawPoint() {
    }


    @Override
    public void actionMove(Canvas canvas, float x, float y) {
        super.actionMove(canvas, x, y);
        draw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPoint(lastX, lastY, paint);
    }
}
