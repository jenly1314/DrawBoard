package com.king.drawboard.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.Nullable;

import com.king.drawboard.action.MotionAction;

/**
 * 绘制抽象类定义；通过继承此类可实现具体的绘制对象
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
public abstract class Draw implements MotionAction {

    Paint paint;

    float lastX;
    float lastY;

    public Draw() {

    }

    /**
     * 获取画笔
     *
     * @return
     */
    public Paint getPaint() {
        return paint;
    }

    /**
     * 设置画笔
     *
     * @param paint
     */
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * 设置点
     *
     * @param point
     */
    public void setPoint(PointF point) {
        this.lastX = point.x;
        this.lastY = point.y;
    }

    @Override
    public void actionDown(Canvas canvas, float x, float y) {
        Log.d(getClass().getSimpleName(), String.format("actionDown: %f, %f", x, y));
        lastX = x;
        lastY = y;
    }

    @Override
    public void actionMove(Canvas canvas, float x, float y) {
        Log.d(getClass().getSimpleName(), String.format("actionMove: %f, %f", x, y));
        lastX = x;
        lastY = y;
    }

    @Override
    public void actionUp(Canvas canvas, float x, float y) {
        Log.d(getClass().getSimpleName(), String.format("actionUp: %f, %f", x, y));
    }

    @Override
    public boolean canMove(float x, float y) {
        return false;
    }

    /**
     * 获取边界框
     *
     * @return
     */
    @Nullable
    public RectF getBoundingBox() {
        return null;
    }

    public abstract void draw(Canvas canvas);
}
