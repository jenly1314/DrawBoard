package com.king.drawboard.draw;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

/**
 * 绘制直线
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
public class DrawLine extends Draw {

    private float downX;
    private float downY;

    private boolean isDrawArrow;

    private float arrowSize;
    private Path path;

    public DrawLine() {
        path = new Path();
    }

    public void setPoint(PointF fromPoint, PointF toPoint) {
        this.downX = fromPoint.x;
        this.downY = fromPoint.y;
        this.lastX = toPoint.x;
        this.lastY = toPoint.y;
    }

    /**
     * 设置是否绘制箭头；默认为：false；当设置为：true时，绘制直线时，结尾处将会带有箭头。
     *
     * @param drawArrow
     */
    public void setDrawArrow(boolean drawArrow) {
        isDrawArrow = drawArrow;
    }

    /**
     * 设置绘制的箭头大小
     *
     * @param arrowSize
     */
    public void setArrowSize(float arrowSize) {
        this.arrowSize = arrowSize;
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
        draw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d("DrawLine", "downX:" + downX +  "," + downY + "-lastX" + lastX + "," + lastY);

        canvas.drawLine(downX, downY, lastX, lastY, paint);
        if (isDrawArrow && arrowSize > 0) {
            drawArrow(canvas);
        }
    }

    /**
     * 绘制箭头
     *
     * @param canvas
     */
    private void drawArrow(Canvas canvas) {
        double a = Math.atan2(lastY - downY, lastX - downX);
        double angle = 20 * Math.PI / 180;
        float x1 = (float) (lastX - arrowSize * Math.cos(a + angle));
        float y1 = (float) (lastY - arrowSize * Math.sin(a + angle));
        float x2 = (float) (lastX - arrowSize * Math.cos(a - angle));
        float y2 = (float) (lastY - arrowSize * Math.sin(a - angle));

        path.reset();
        // 直线的终点
        path.moveTo(lastX, lastY);
        // 箭头的第一个点
        path.lineTo(x1, y1);
        // 箭头的第二个点
        path.lineTo(x2, y2);
        path.close();
        canvas.drawPath(path, paint);
    }
}
