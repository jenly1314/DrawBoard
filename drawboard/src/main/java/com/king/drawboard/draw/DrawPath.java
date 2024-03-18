package com.king.drawboard.draw;

import android.graphics.Canvas;
import android.graphics.Path;

/**
 * 绘制路径
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
public class DrawPath extends Draw {

    private Path path;


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
    }

    @Override
    public void actionMove(Canvas canvas, float x, float y) {
        path.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2);
        super.actionMove(canvas, x, y);
        draw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }
}
