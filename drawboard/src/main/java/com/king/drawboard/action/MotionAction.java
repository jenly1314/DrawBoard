package com.king.drawboard.action;

import android.graphics.Canvas;

/**
 * 触摸动作
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
public interface MotionAction {
    /**
     * 按下
     *
     * @param canvas
     * @param x
     * @param y
     */
    void actionDown(Canvas canvas, float x, float y);

    /**
     * 移动
     *
     * @param canvas
     * @param x
     * @param y
     */
    void actionMove(Canvas canvas, float x, float y);

    /**
     * 松开
     *
     * @param canvas
     * @param x
     * @param y
     */
    void actionUp(Canvas canvas, float x, float y);

    /**
     * 是否可移动
     *
     * @param x
     * @param y
     * @return
     */
    boolean canMove(float x, float y);
}
