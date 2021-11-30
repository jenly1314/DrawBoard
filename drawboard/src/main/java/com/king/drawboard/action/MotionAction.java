package com.king.drawboard.action;

import android.graphics.Canvas;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface MotionAction {

    void actionDown(Canvas canvas, float x, float y);

    void actionMove(Canvas canvas, float x, float y);

    void actionUp(Canvas canvas, float x, float y);
}
