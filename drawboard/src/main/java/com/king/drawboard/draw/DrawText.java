package com.king.drawboard.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;

import androidx.annotation.Nullable;

/**
 * 绘制文本
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
public class DrawText extends Draw {

    private String text;

    private float offsetY;

    private float width;
    private float height;

    private final RectF rect;

    public DrawText() {
        rect = new RectF();
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void setPaint(Paint paint) {
        super.setPaint(paint);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        offsetY = (fontMetrics.ascent + fontMetrics.descent) / 2;
    }

    @Override
    public void actionDown(Canvas canvas, float x, float y) {
        super.actionDown(canvas, x, y);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        if (!TextUtils.isEmpty(text)) {
            width = paint.measureText(text);
        }
        height = fontMetrics.descent - fontMetrics.ascent;
    }

    @Override
    public void actionMove(Canvas canvas, float x, float y) {
        super.actionMove(canvas, x, y);
        draw(canvas);
    }

    @Nullable
    @Override
    public RectF getBoundingBox() {
        if (!TextUtils.isEmpty(text)) {
            rect.left = lastX - width / 2;
            rect.top = lastY - height / 2;
            rect.right = rect.left + width;
            rect.bottom = rect.top + height;
            return rect;
        }
        return super.getBoundingBox();
    }

    @Override
    public boolean canMove(float x, float y) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float width = paint.measureText(text);
        float height = fontMetrics.descent - fontMetrics.ascent;
        return Math.abs(lastX - x) < width / 2 && Math.abs(lastY - y) < height / 2;
    }

    @Override
    public void draw(Canvas canvas) {
        if (!TextUtils.isEmpty(text)) {
            canvas.drawText(text, lastX, lastY - offsetY, paint);
        }
    }
}
