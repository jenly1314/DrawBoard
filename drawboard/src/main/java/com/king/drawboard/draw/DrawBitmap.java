package com.king.drawboard.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import androidx.annotation.Nullable;

/**
 * 绘制图片
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
public class DrawBitmap extends Draw {

    Bitmap bitmap;

    private final RectF rect;

    public DrawBitmap() {
        rect = new RectF();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public void actionMove(Canvas canvas, float x, float y) {
        super.actionMove(canvas, x, y);
        draw(canvas);
    }

    @Override
    public boolean canMove(float x, float y) {
        return Math.abs(lastX - x) < bitmap.getWidth() / 2f && Math.abs(lastY - y) < bitmap.getHeight() / 2f;
    }

    @Nullable
    @Override
    public RectF getBoundingBox() {
        if(bitmap != null) {
            rect.left = lastX - bitmap.getWidth() / 2f;
            rect.top = lastY - bitmap.getHeight() / 2f;
            rect.right = rect.left + bitmap.getWidth();
            rect.bottom = rect.top + bitmap.getHeight();
            return rect;
        }
        return super.getBoundingBox();
    }

    @Override
    public void draw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, lastX - bitmap.getWidth() / 2f, lastY - bitmap.getHeight() / 2f, null);
        }
    }
}
