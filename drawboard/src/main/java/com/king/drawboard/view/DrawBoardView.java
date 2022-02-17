package com.king.drawboard.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.king.drawboard.R;
import com.king.drawboard.draw.Draw;
import com.king.drawboard.draw.DrawBitmap;
import com.king.drawboard.draw.DrawCircle;
import com.king.drawboard.draw.DrawLine;
import com.king.drawboard.draw.DrawOval;
import com.king.drawboard.draw.DrawPath;
import com.king.drawboard.draw.DrawPoint;
import com.king.drawboard.draw.DrawRect;
import com.king.drawboard.draw.DrawText;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class DrawBoardView extends View {

    /**
     * 触摸时默认允许的容差值
     */
    private static final float TOUCH_TOLERANCE = 4F;
    /**
     * 触摸时默认点的比例
     */
    private static final float TOUCH_POINT_RATIO = 1.2F;

    /**
     * 触摸时允许的容差值
     */
    private float touchTolerance;
    /**
     * 触摸点的比例
     */
    private float touchPointRatio;
    /**
     * 原始图片
     */
    private Bitmap originBitmap;

    /**
     * 绘制层图片
     */
    private Bitmap drawingBitmap;

    /**
     * 预览层图片
     */
    private Bitmap previewBitmap;

    /**
     * 触摸层画布
     */
    private Bitmap touchBitmap;

    /**
     * 矩阵：主要用于对图片进行移动和缩放相关的处理
     */
    private Matrix matrix;
    /**
     * 绘制层画布
     */
    private Canvas drawingCanvas;
    /**
     * 预览层画布
     */
    private Canvas previewCanvas;
    /**
     * 触摸层画布
     */
    private Canvas touchCanvas;
    /**
     * 是否改变图片
     */
    private boolean isChangeBitmap;
    /**
     * 是否触摸
     */
    private boolean isTouch;
    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 触摸点画笔
     */
    private Paint pointPaint;
    /**
     * 画笔的颜色
     */
    private int paintColor = Color.RED;
    /**
     * 触摸点画笔的颜色
     */
    private int touchPointColor = 0xAFCCCCCC;
    /**
     * 绘制文本的字体大小
     */
    private float drawTextSize;
    /**
     * 文本是否是粗体
     */
    private boolean isFakeBoldText;
    /**
     * 文本是否需要下划线
     */
    private boolean isUnderlineText;
    /**
     * 画笔线条描边宽度
     */
    private float lineStrokeWidth;
    /**
     * 橡皮擦描边宽度
     */
    private float eraserStrokeWidth;
    /**
     * 缩放点描边宽度
     */
    private float zoomPointStrokeWidth;
    /**
     * 画笔的 Paint.Style
     */
    private Paint.Style paintStyle = Paint.Style.STROKE;
    /**
     * 画笔的着色器
     */
    private Shader paintShader;
    /**
     * 画笔的 Xfermode
     */
    private Xfermode paintXfermode;
    /**
     * 画笔的 PathEffect
     */
    private PathEffect pathEffect;
    /**
     * 画笔的 BlendMode
     */
    private BlendMode blendMode;
    /**
     * 绘图模式
     */
    @DrawMode
    private int drawMode = DrawMode.DRAW_PATH;
    /**
     * 是否绘制
     */
    private boolean isDraw;
    /**
     * 是否是缩放
     */
    private boolean isZoom;
    /**
     * 控件的宽度
     */
    private int width;
    /**
     * 控件的高度
     */
    private int height;
    /**
     * 最后一次的横坐标值
     */
    private float lastX;
    /**
     * 最后一次的纵坐标值
     */
    private float lastY;
    /**
     * 最后一次中心点的横坐标值
     */
    private float lastCenterPointX;
    /**
     * 最后一次中心点的纵坐标值
     */
    private float lastCenterPointY;
    /**
     * 最后一次两指之间的距离
     */
    private float lastFingerDistance;
    /**
     * 支持最小的放大倍数
     */
    private float minZoom = 1;
    /**
     * 支持最大的放大倍数
     */
    private float maxZoom = 4;
    /**
     * 是否自适应
     */
    private boolean isFit = true;
    /**
     * 当前图片的宽度，缩放时此值会发生变化
     */
    private int currentBitmapWidth;
    /**
     * 当前图片的高度，缩放时此值会发生变化
     */
    private int currentBitmapHeight;
    /**
     * 手指在横坐标方向上的移动距离
     */
    private float movedDistanceX;
    /**
     * 手指在纵坐标方向上的移动距离
     */
    private float movedDistanceY;
    /**
     * 当前图片在矩阵上的横向偏移值
     */
    private float currentTranslateX;
    /**
     * 当前图片在矩阵上的纵向偏移值
     */
    private float currentTranslateY;
    /**
     * 当前图片在矩阵上的缩放比例
     */
    private float currentRatio;
    /**
     * 手指移动的距离所造成的缩放比例
     */
    private float scaledRatio;
    /**
     * 图片初始化时的缩放比例
     */
    private float initRatio;
    /**
     * 绘图记录：主要用于撤销和保存
     */
    private LinkedList<Draw> drawList;
    /**
     * 绘图记录备份：主要用于恢复
     */
    private List<Draw> backupDrawList;
    /**
     * 是否撤销过
     */
    private boolean isRevoked;
    /**
     * 绘图对象
     */
    private Draw draw;
    /**
     * 需要绘制的文本内容
     */
    private String drawText;
    /**
     * 需要绘制的位图
     */
    private Bitmap drawBitmap;
    /**
     * 锚点是否居中
     */
    private boolean isDrawBitmapAnchorCenter;
    /**
     * 用于存储所有支持的绘图模式
     */
    private Map<Integer,Class<? extends Draw>> drawMap;
    /**
     * 是否启用绘图
     */
    private boolean isDrawEnabled = true;
    /**
     * 是否启用缩放
     */
    private boolean isZoomEnabled = true;
    /**
     * 是否显示触摸点
     */
    private boolean isShowTouchPoint = true;
    /**
     * 是否可撤销
     */
    private boolean hasUndo;
    /**
     * 是否可恢复
     */
    private boolean hasRedo;
    /**
     * 缩放监听
     */
    private OnZoomListener onZoomListener;
    /**
     * 绘制监听
     */
    private OnDrawListener onDrawListener;

    /**
     * 绘图模式
     */
    @IntDef({DrawMode.DRAW_PATH, DrawMode.DRAW_POINT, DrawMode.DRAW_LINE,
            DrawMode.DRAW_RECT, DrawMode.DRAW_OVAL, DrawMode.DRAW_CIRCLE,
            DrawMode.DRAW_TEXT, DrawMode.DRAW_BITMAP, DrawMode.ERASER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawMode {
        /**
         * 绘制路径
         */
        int DRAW_PATH = 1;
        /**
         * 绘制点
         */
        int DRAW_POINT = 2;
        /**
         * 绘制线
         */
        int DRAW_LINE = 3;
        /**
         * 绘制矩形
         */
        int DRAW_RECT = 4;
        /**
         * 绘制椭圆
         */
        int DRAW_OVAL = 5;
        /**
         * 绘制圆
         */
        int DRAW_CIRCLE = 6;
        /**
         * 绘制文本
         */
        int DRAW_TEXT = 7;
        /**
         * 绘制图片
         */
        int DRAW_BITMAP = 8;
        /**
         * 橡皮擦
         */
        int ERASER = 9;
    }


    public DrawBoardView(Context context) {
        this(context,null);
    }

    public DrawBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DrawBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs){

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        drawTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,15f,displayMetrics);
        lineStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2f,displayMetrics);
        eraserStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10f,displayMetrics);
        zoomPointStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6f,displayMetrics);

        touchTolerance = TOUCH_TOLERANCE;
        touchPointRatio = TOUCH_POINT_RATIO;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawBoardView);

        int size = a.getIndexCount();
        for(int i = 0;i < size;i++){
            int attr = a.getIndex(i);
            if(attr == R.styleable.DrawBoardView_dbvMinZoom){
                minZoom = a.getFloat(attr, 1f);
            }else if(attr == R.styleable.DrawBoardView_dbvMaxZoom){
                minZoom = a.getFloat(attr, 4f);
            }else if(attr == R.styleable.DrawBoardView_dbvFit){
                isFit = a.getBoolean(attr, true);
            }else if(attr == R.styleable.DrawBoardView_dbvDrawEnabled){
                isDrawEnabled = a.getBoolean(attr, true);
            }else if(attr == R.styleable.DrawBoardView_dbvZoomEnabled){
                isZoomEnabled = a.getBoolean(attr, true);
            }else if(attr == R.styleable.DrawBoardView_dbvShowTouchPoint){
                isShowTouchPoint = a.getBoolean(attr, true);
            }else if(attr == R.styleable.DrawBoardView_android_src){
                Drawable drawable = a.getDrawable(attr);
                if(drawable != null){
                    originBitmap = getBitmapFormDrawable(drawable);
                    isChangeBitmap = true;
                }
            }else if(attr == R.styleable.DrawBoardView_dbvPaintColor){
                paintColor = a.getColor(attr, Color.RED);
            }else if(attr == R.styleable.DrawBoardView_dbvTouchPointColor){
                touchPointColor = a.getColor(attr, Color.RED);
            }else if(attr == R.styleable.DrawBoardView_dbvDrawTextSize){
                drawTextSize = a.getDimension(attr, drawTextSize);
            }else if(attr == R.styleable.DrawBoardView_dbvDrawTextBold){
                isFakeBoldText = a.getBoolean(attr, false);
            }else if(attr == R.styleable.DrawBoardView_dbvDrawTextUnderline){
                isUnderlineText = a.getBoolean(attr, false);
            }else if(attr == R.styleable.DrawBoardView_dbvTouchTolerance){
                touchTolerance = a.getFloat(attr, TOUCH_TOLERANCE);
            }
        }

        a.recycle();

        pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(touchPointColor);

        drawList = new LinkedList<>();
        backupDrawList = new ArrayList<>();
        matrix = new Matrix();
        initDrawMap();
    }

    /**
     * 获取 {@link Bitmap}
     * @param drawable
     * @return
     */
    private Bitmap getBitmapFormDrawable(@NonNull Drawable drawable){
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,bitmap.getWidth(),bitmap.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 初始化支持的绘图模式
     */
    private void initDrawMap(){
        drawMap = new HashMap<>();
        drawMap.put(DrawMode.DRAW_PATH, DrawPath.class);
        drawMap.put(DrawMode.DRAW_POINT, DrawPoint.class);
        drawMap.put(DrawMode.DRAW_LINE, DrawLine.class);
        drawMap.put(DrawMode.DRAW_RECT, DrawRect.class);
        drawMap.put(DrawMode.DRAW_OVAL, DrawOval.class);
        drawMap.put(DrawMode.DRAW_CIRCLE, DrawCircle.class);
        drawMap.put(DrawMode.DRAW_TEXT, DrawText.class);
        drawMap.put(DrawMode.DRAW_BITMAP, DrawBitmap.class);
        drawMap.put(DrawMode.ERASER, DrawPath.class);
    }

    /**
     * 获取支持的绘图模式
     * @return
     */
    public Map<Integer,Class<? extends Draw>> getDrawMap(){
        return drawMap;
    }

    /**
     * 设置图片
     * @param drawableId
     */
    public void setImageResource(@DrawableRes int drawableId){
        setImageBitmap(BitmapFactory.decodeResource(getResources(), drawableId));
    }

    /**
     * 设置图片（画板背景图层）
     * @param bitmap
     */
    public void setImageBitmap(Bitmap bitmap){
        if(bitmap != null){
            originBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            drawList.clear();
            backupDrawList.clear();
            matrix.reset();
            isChangeBitmap = true;
        }else{
            originBitmap = null;
        }
        invalidate();
    }

    /**
     * 获取图片（画板背景图层和画板图层合并后的图片）
     * @return
     */
    public Bitmap getImageBitmap(){
        Bitmap bitmap = null;
        if(originBitmap != null){
            bitmap = Bitmap.createBitmap(originBitmap.getWidth(),originBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawBitmap(canvas, new Matrix(), false);
        }
        return bitmap;
    }

    /**
     * 更新图片
     */
    private synchronized void updateImageBitmap(){
        if(originBitmap == null){//如果原始图片为空，则创建一个和控件视图宽高一致的图片
            originBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            //创建绘图层和预览层画布相关
            drawingBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            drawingCanvas = new Canvas(drawingBitmap);
            previewBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            previewCanvas = new Canvas(previewBitmap);
            touchBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            touchCanvas = new Canvas(touchBitmap);

            matrix.reset();

            currentTranslateX = 0;
            currentTranslateY = 0;
            initRatio = 1f;
            currentRatio = 1f;

            currentBitmapWidth = width;
            currentBitmapHeight = height;

            if(onZoomListener != null){
                onZoomListener.onZoomUpdate(getRealZoom(), getZoom());
            }

        }else if(isChangeBitmap){//如果图片发生了改变，则需要重新计算
            isChangeBitmap = false;
            matrix.reset();
            int bitmapWidth = originBitmap.getWidth();
            int bitmapHeight = originBitmap.getHeight();

            //如果图片的宽或高 大于控件视图的宽或高
            if(isFit || bitmapWidth > width || bitmapHeight > height){
                float wRatio = width / (float)bitmapWidth;
                float hRatio = height / (float)bitmapHeight;
                if(wRatio < hRatio){//图片宽铺满时
                    //以宽的比例进行等比例缩放保证图片能完全显示
                    matrix.postScale(wRatio, wRatio);
                    float translateY = (height - (bitmapHeight * wRatio)) / 2f;
                    //进行平移，保证图片居中显示
                    matrix.postTranslate(0, translateY);

                    currentTranslateX = 0;
                    currentTranslateY = translateY;
                    initRatio = wRatio;
                    currentRatio = wRatio;
                }else{//图片高铺满时
                    //以高的比例进行等比例缩放保证图片能完全显示
                    matrix.postScale(hRatio, hRatio);
                    float translateX = (width - (bitmapWidth * hRatio)) / 2f;
                    //进行平移，保证图片居中显示
                    matrix.postTranslate(translateX, 0);

                    currentTranslateX = translateX;
                    currentTranslateY = 0;
                    initRatio = hRatio;
                    currentRatio = hRatio;
                }

            }else{
                //图片的宽和高都小于控件视图的宽和高，则直接将图片居中显示
                float translateY = (height - bitmapHeight) / 2f;
                float translateX = (width - bitmapWidth) / 2f;

                matrix.postTranslate(translateX, translateY);
                currentTranslateX = translateX;
                currentTranslateY = translateY;
                initRatio = 1f;
                currentRatio = 1f;
            }

            currentBitmapWidth = (int) (bitmapWidth * initRatio);
            currentBitmapHeight = (int) (bitmapHeight * initRatio);

            //创建绘图层和预览层画布相关
            drawingBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
            drawingCanvas = new Canvas(drawingBitmap);
            previewBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
            previewCanvas = new Canvas(previewBitmap);
            touchBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
            touchCanvas = new Canvas(touchBitmap);

            if(onZoomListener != null){
                onZoomListener.onZoomUpdate(getRealZoom(), getZoom());
            }
        }
    }


    /**
     * 清除画布
     */
    public void clear(){
        drawingCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        drawList.clear();
        backupDrawList.clear();
        hasUndo = false;
        hasRedo = false;
        invalidate();
    }

    /**
     * 撤销一步
     */
    public void undo(){
        if(!drawList.isEmpty()){
            drawingCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            //删除最后一步
            drawList.removeLast();
            //然后将画图记录重新画上去
            for(Draw draw: drawList){
                draw.draw(drawingCanvas);
            }
            invalidate();
            isRevoked = true;
            hasRedo = true;
            //判断是否撤销到最后一步
            if(drawList.isEmpty()){
                hasUndo = false;
            }
        }else{
            hasUndo = false;
        }
    }

    /**
     * 恢复一步（反撤销）
     */
    public void redo(){
        int backupSize = backupDrawList.size();
        if(backupSize > 0){
            int size = drawList.size();
            if(size < backupSize){
                drawingCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                if(size == 0){//size为0时，表示已经撤销到最后一步了
                    drawList.add(backupDrawList.get(0));
                }else{//恢复一步即可
                    drawList.add(backupDrawList.get(size));
                }
                //提前判断是否恢复到最后一步
                if(size + 1 == backupSize){
                    hasRedo = false;
                }
                //然后将画图记录重新画上去
                for(Draw draw: drawList){
                    draw.draw(drawingCanvas);
                }
                invalidate();
            }else{
                hasRedo = false;
            }
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){//当布局发生改变时，记住控件视图的宽高
            width = getWidth();
            height = getHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBitmap(canvas, matrix, isTouch);
    }

    /**
     * 通过代码进行绘制
     * 需要注意：绘制原始坐标点需要转换成矩阵上的坐标点；{@link #transfer(PointF)}
     * @param draw
     */
    public void draw(@NonNull Draw draw){
        if(drawingCanvas != null){
            if(draw.getPaint() == null){//如果画笔为空则根据当前配置自动创建画笔，如果draw 是绘制其他有依赖的需自己配置后再传进来
                draw.setPaint(createPaint(drawMode));
            }
            //进行绘制
            draw.draw(drawingCanvas);
            //将绘制记录保存起来，便于后续的撤销和恢复相关操作
            drawList.add(draw);
            if(isRevoked){
                backupDrawList.clear();
                backupDrawList.addAll(drawList);
                hasRedo = false;
                isRevoked = false;
            }else{
                backupDrawList.add(draw);
            }
            hasUndo = true;
            if(onDrawListener != null){
                onDrawListener.onDraw(draw);
            }

            invalidate();
        }
    }

    /**
     * 将原始点坐标转换成画布矩阵上的点坐标，当通过代码调用{@link #draw(Draw)}绘制时可能会用到此转换方法
     * @param point
     */
    public void transfer(PointF point){
        point.x = (point.x - currentTranslateX) / currentRatio;
        point.y = (point.y - currentTranslateY) / currentRatio;
    }

    /**
     * 绘制图片
     * @param canvas
     */
    private void drawBitmap(Canvas canvas, Matrix matrix, boolean isTouch){
        updateImageBitmap();

        if(originBitmap != null){
            canvas.drawBitmap(originBitmap, matrix, null);
        }
        if(drawingBitmap != null){
            canvas.drawBitmap(drawingBitmap, matrix, null);
        }
        if(previewBitmap != null){
            canvas.drawBitmap(previewBitmap, matrix, null);
        }
        if(isShowTouchPoint && isTouch && touchBitmap != null){
            canvas.drawBitmap(touchBitmap, matrix, null);
        }
    }

    /**
     * 绘制触摸点
     * @param x
     * @param y
     */
    private void drawTouchPoint(float x, float y){
        touchCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        if(drawMode == DrawMode.ERASER){
            drawTouchPoint(touchCanvas, x, y, eraserStrokeWidth / 2 * touchPointRatio);
        }else{
            drawTouchPoint(touchCanvas, x, y, lineStrokeWidth / 2 * touchPointRatio);
        }
    }

    /**
     * 绘制缩放时触摸点
     * @param x
     * @param y
     */
    private void drawZoomPoint(float x, float y){
        touchCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        drawTouchPoint(touchCanvas, x, y, zoomPointStrokeWidth / 2 * touchPointRatio);
    }

    /**
     * 绘制缩放时触摸点
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     */
    private void drawZoomPoint(float x0, float y0, float x1, float y1){
        touchCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        drawTouchPoint(touchCanvas, x0, y0, zoomPointStrokeWidth);
        drawTouchPoint(touchCanvas, x1, y1, zoomPointStrokeWidth);
    }

    /**
     * 绘制触摸点
     * @param canvas
     * @param x
     * @param y
     * @param radius
     */
    private void drawTouchPoint(Canvas canvas,float x, float y, float radius){
        canvas.drawCircle(x, y, radius, pointPaint);
    }

    /**
     * 创建画笔
     * @param drawMode
     * @return
     */
    private Paint createPaint(int drawMode){

        Paint paint = new Paint();
        if (drawMode == DrawMode.ERASER) { // 当为橡皮擦模式时
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(eraserStrokeWidth);
            paint.setAntiAlias(false);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        } else if (drawMode == DrawMode.DRAW_TEXT) { // 当为绘制文本时
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setAntiAlias(true);
            paint.setColor(paintColor);
            paint.setTextSize(drawTextSize);
            paint.setFakeBoldText(isFakeBoldText);
            paint.setUnderlineText(isUnderlineText);
        } else {
            paint.setStyle(paintStyle);
            paint.setColor(paintColor);
            paint.setStrokeWidth(lineStrokeWidth);
            paint.setAntiAlias(true);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);

            if (paintShader != null) {
                paint.setShader(paintShader);
            }
            if (paintXfermode != null) {
                paint.setXfermode(paintXfermode);
            }
            if (pathEffect != null) {
                paint.setPathEffect(pathEffect);
            }
            if (blendMode != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                paint.setBlendMode(blendMode);
            }
        }

        return paint;
    }


    /**
     * 创建绘图对象
     * @param drawMode
     * @return
     */
    private Draw createDraw(@DrawMode int drawMode){
        Class<? extends Draw> drawClass = drawMap.get(drawMode);
        if(drawClass != null){
            try {
                Draw draw = drawClass.newInstance();
                if(draw instanceof DrawText){
                    ((DrawText) draw).setTextPaint(paint);
                    ((DrawText) draw).setText(drawText);
                }else if(draw instanceof DrawBitmap){
                    ((DrawBitmap) draw).setBitmap(drawBitmap);
                    ((DrawBitmap) draw).setAnchorCenter(isDrawBitmapAnchorCenter);
                }
                return draw;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Draw(){
            @Override
            public void draw(Canvas canvas) {

            }
        };
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isTouch = true;
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                if(isDrawEnabled && event.getPointerCount() == 1){//单指
                    isZoom = false;
                    isDraw = false;
                    paint = createPaint(drawMode);
                    draw = createDraw(drawMode);
                    draw.setPaint(paint);
                    float x = event.getX();
                    float y = event.getY();
                    float ratioX = (x - currentTranslateX) / currentRatio;
                    float ratioY = (y - currentTranslateY) / currentRatio;
                    if(isShowTouchPoint){
                        touchCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    }
                    if(drawMode == DrawMode.ERASER){//如果是橡皮擦模式，则直接使用绘制层画布
                        draw.actionDown(drawingCanvas, ratioX, ratioY);
                    }else{
                        //绘制前先清空预览画布
                        previewCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        draw.actionDown(previewCanvas, ratioX, ratioY);
                    }

                    lastX = event.getX();
                    lastY = event.getY();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if(isZoomEnabled && event.getPointerCount() >= 2){//多指：判定为缩放
                    isZoom = true;
                    float xPoint0 = event.getX(0);
                    float yPoint0 = event.getY(0);
                    float xPoint1 = event.getX(1);
                    float yPoint1 = event.getY(1);
                    // 两指之间的中心点
                    lastCenterPointX = (xPoint0 + xPoint1) / 2;
                    lastCenterPointY = (yPoint0 + yPoint1) / 2;
                    // 两指之间的距离
                    lastFingerDistance = distance(xPoint0, yPoint0, xPoint1, yPoint1);

                    lastX = event.getX();
                    lastY = event.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isDrawEnabled && !isZoom){
                    float x = event.getX();
                    float y = event.getY();
                    if(Math.abs(lastX - x) > touchTolerance || Math.abs(lastY - y) > touchTolerance){
                        isDraw = true;
                        float ratioX = (x - currentTranslateX) / currentRatio;
                        float ratioY = (y - currentTranslateY) / currentRatio;
                        if(drawMode == DrawMode.ERASER){//如果是橡皮擦模式，则直接使用绘制层画布
                            draw.actionMove(drawingCanvas, ratioX, ratioY);
                        }else{
                            //绘制前先清空预览画布
                            previewCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                            draw.actionMove(previewCanvas, ratioX, ratioY);
                        }
                        if(isShowTouchPoint){
                            drawTouchPoint(ratioX, ratioY);
                        }
                        lastX = x;
                        lastY = y;
                    }
                }else if(isZoomEnabled && isZoom){
                    processZoomEvent(event);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if(isShowTouchPoint){
                    touchCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isShowTouchPoint){
                    touchCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                }
                if(draw != null && isDraw){
                    float x = event.getX();
                    float y = event.getY();
                    float ratioX = (x - currentTranslateX) / currentRatio;
                    float ratioY = (y - currentTranslateY) / currentRatio;
                    if(drawMode == DrawMode.ERASER){//如果是橡皮擦模式，则直接使用绘制层画布
                        draw.actionUp(drawingCanvas, ratioX, ratioY);
                    }else {
                        //绘制前先清空预览画布
                        previewCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        draw.actionUp(previewCanvas, ratioX, ratioY);
                    }
                    //将之前绘制预览的结果绘制在图层上
                    draw.draw(drawingCanvas);
                    //将绘制记录保存起来，便于后续的撤销和恢复相关操作
                    drawList.add(draw);
                    if(isRevoked){
                        backupDrawList.clear();
                        backupDrawList.addAll(drawList);
                        hasRedo = false;
                        isRevoked = false;
                    }else{
                        backupDrawList.add(draw);
                    }
                    hasUndo = true;
                    if(onDrawListener != null){
                        onDrawListener.onDraw(draw);
                    }
                }
                lastX = -1;
                lastY = -1;
                lastCenterPointX = -1;
                lastCenterPointY = -1;
                isZoom = false;
                isTouch = false;
                isDraw = false;
                break;
        }
        invalidate();

        return true;
    }


    /**
     * 处理缩放事件
     * @param event
     */
    private void processZoomEvent(MotionEvent event){

        int pointCount = event.getPointerCount();
        if(pointCount >= 2){//多指时，计算缩放和平移
            float xPoint0 = event.getX(0);
            float yPoint0 = event.getY(0);
            float xPoint1 = event.getX(1);
            float yPoint1 = event.getY(1);

            // 两指之间的中心点
            float centerPointX = (xPoint0 + xPoint1) / 2;
            float centerPointY = (yPoint0 + yPoint1) / 2;
            // 两指之间的距离
            float fingerDistance = distance(xPoint0, yPoint0, xPoint1, yPoint1);

            scaledRatio = fingerDistance / lastFingerDistance;

            //缩放比例
            if(scaledRatio < 1f){//两点之间的距离小于上一次，表示缩小
                float minRatio = isFit ? initRatio : minZoom;
                if(currentRatio > minRatio){
                    currentRatio = currentRatio * scaledRatio;
                    //边界处理，currentRatio 最小不得小于 minRatio
                    if(currentRatio < minRatio){
                        currentRatio = minRatio;
                    }
                }

            }else{//反之，表示放大
                float maxRatio = isFit ? Math.max(initRatio, maxZoom) : maxZoom;
                if(currentRatio < maxZoom){
                    currentRatio = currentRatio * scaledRatio;
                    //边界处理，currentRatio 最大不得大于 maxRatio
                    if(currentRatio > maxRatio){
                        currentRatio = maxRatio;
                    }
                }
            }

            //平移距离
            if (lastCenterPointX != -1 && lastCenterPointY != -1) {
                movedDistanceX = centerPointX - lastCenterPointX;
                movedDistanceY = centerPointY - lastCenterPointY;
            }

            //更新最后一次记录的值
            lastCenterPointX = centerPointX;
            lastCenterPointY = centerPointY;

            lastFingerDistance = fingerDistance;

        }else{//单指时，只计算平移
            movedDistanceX = event.getX() - lastX;
            movedDistanceY = event.getY() - lastY;
        }

        // 进行边界检查，不允许将图片拖出边界
        if (currentTranslateX + movedDistanceX > 0) {
            movedDistanceX = 0;
        } else if (width - (currentTranslateX + movedDistanceX) > currentBitmapWidth) {
            movedDistanceX = 0;
        }
        if (currentTranslateY + movedDistanceY > 0) {
            movedDistanceY = 0;
        } else if (height - (currentTranslateY + movedDistanceY) > currentBitmapHeight) {
            movedDistanceY = 0;
        }
        //进行矩阵缩放
        zoom(lastCenterPointX, lastCenterPointY);

        //如果需要显示触摸点，则进行显示
        if(isShowTouchPoint){
            if(pointCount >= 2){
                float xPoint0 = event.getX(0);
                float yPoint0 = event.getY(0);
                float xPoint1 = event.getX(1);
                float yPoint1 = event.getY(1);
                float ratioX0 = (xPoint0 - currentTranslateX) / currentRatio;
                float ratioY0 = (yPoint0 - currentTranslateY) / currentRatio;
                float ratioX1 = (xPoint1 - currentTranslateX) / currentRatio;
                float ratioY1 = (yPoint1 - currentTranslateY) / currentRatio;
                drawZoomPoint(ratioX0, ratioY0, ratioX1, ratioY1);
            }else{
                float xPoint0 = event.getX(0);
                float yPoint0 = event.getY(0);
                float ratioX0 = (xPoint0 - currentTranslateX) / currentRatio;
                float ratioY0 = (yPoint0 - currentTranslateY) / currentRatio;
                drawZoomPoint(ratioX0, ratioY0);
            }
        }

        lastX = event.getX();
        lastY = event.getY();
    }

    /**
     * 矩阵缩放
     * @param centerPointX
     * @param centerPointY
     */
    private void zoom(float centerPointX, float centerPointY){
        matrix.reset();
        // 将图片按总缩放比例进行缩放
        matrix.postScale(currentRatio, currentRatio);
        float scaledWidth = originBitmap.getWidth() * currentRatio;
        float scaledHeight = originBitmap.getHeight() * currentRatio;
        float translateX;
        float translateY;

        // 如果当前图片宽度小于控件视图宽度，则按控件视图中心的横坐标进行水平缩放。否则按两指的中心点的横坐标进行水平缩放
        if (currentBitmapWidth < width) {
            translateX = (width - scaledWidth) / 2f;
        } else {
            translateX = currentTranslateX * scaledRatio + centerPointX * (1 - scaledRatio);
            translateX = translateX + movedDistanceX; //加上中心点移动距离
            // 进行边界检查，保证图片缩放后在水平方向上不会偏移出控件视图
            if (translateX > 0) {
                translateX = 0;
            } else if (width - translateX > scaledWidth) {
                translateX = width - scaledWidth;
            }
        }
        // 如果当前图片高度小于控件视图高度，则按控件视图中心的纵坐标进行垂直缩放。否则按两指的中心点的纵坐标进行垂直缩放
        if (currentBitmapHeight < height) {
            translateY = (height - scaledHeight) / 2f;
        } else {
            translateY = currentTranslateY * scaledRatio + centerPointY * (1 - scaledRatio);
            translateY = translateY + movedDistanceY;   //加上中心点移动距离
            // 进行边界检查，保证图片缩放后在垂直方向上不会偏移出控件视图
            if (translateY > 0) {
                translateY = 0;
            } else if (height - translateY > scaledHeight) {
                translateY = height - scaledHeight;
            }
        }
        // 缩放后对图片进行偏移，以保证缩放后中心点位置不变
        matrix.postTranslate(translateX, translateY);
        currentTranslateX = translateX;
        currentTranslateY = translateY;
        currentBitmapWidth = (int) scaledWidth;
        currentBitmapHeight = (int) scaledHeight;

        if(onZoomListener != null){
            onZoomListener.onZoomUpdate(getRealZoom(), getZoom());
        }
    }

    /**
     * 计算两点之间的距离
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @return
     */
    private float distance(float x0, float y0, float x1, float y1) {
        float disX = Math.abs(x0 - x1);
        float disY = Math.abs(y0 - y1);
        return (float)Math.sqrt(disX * disX + disY * disY);
    }

    /**
     * 获取绘图模式
     * @return
     */
    @DrawMode
    public int getDrawMode() {
        return drawMode;
    }

    /**
     * 设置绘图模式
     * @param drawMode
     */
    public void setDrawMode(@DrawMode int drawMode) {
        this.drawMode = drawMode;
    }

    /**
     * 获取图片的宽度
     * @return
     */
    public int getBitmapWidth() {
        if(originBitmap != null){
            return originBitmap.getWidth();
        }
        return width;
    }

    /**
     * 获取图片的高度
     * @return
     */
    public int getBitmapHeight() {
        if(originBitmap != null){
            return originBitmap.getHeight();
        }
        return height;
    }

    /**
     * 获取当前图片的宽度，缩放的宽度
     * @return
     */
    public int getCurrentBitmapWidth() {
        return currentBitmapWidth;
    }

    /**
     * 获取当前图片的高度，缩放的高度
     * @return
     */
    public int getCurrentBitmapHeight() {
        return currentBitmapHeight;
    }

    /**
     * 触摸时允许的容差值
     * @return
     */
    public float getTouchTolerance() {
        return touchTolerance;
    }

    /**
     * 设置触摸时允许的容差值
     * @param touchTolerance
     */
    public void setTouchTolerance(float touchTolerance) {
        this.touchTolerance = touchTolerance;
    }

    /**
     * 触摸点的比例
     * @return
     */
    public float getTouchPointRatio() {
        return touchPointRatio;
    }

    /**
     * 设置触摸点的比例
     * @param touchPointRatio
     */
    public void setTouchPointRatio(float touchPointRatio) {
        this.touchPointRatio = touchPointRatio;
    }

    /**
     * 获取画笔颜色
     * @return
     */
    public int getPaintColor() {
        return paintColor;
    }

    /**
     * 设置画笔颜色
     * @param paintColor
     */
    public void setPaintColor(@ColorInt int paintColor) {
        this.paintColor = paintColor;
    }

    /**
     * 获取触摸点的颜色
     * @return
     */
    public int getTouchPointColor() {
        return touchPointColor;
    }

    @NonNull
    public Paint.Style getPaintStyle() {
        return paintStyle;
    }

    /**
     * 设置画笔的 Paint.Style
     * @param paintStyle
     */
    public void setPaintStyle(@NonNull Paint.Style paintStyle) {
        this.paintStyle = paintStyle;
    }

    @Nullable
    public Shader getPaintShader() {
        return paintShader;
    }

    /**
     * 设置画笔的 Shader
     * @param paintShader
     */
    public void setPaintShader(@Nullable Shader paintShader) {
        this.paintShader = paintShader;
    }

    /**
     * 获取画笔的 Xfermode
     * @return
     */
    @Nullable
    public Xfermode getPaintXfermode() {
        return paintXfermode;
    }

    /**
     * 设置画笔的 Xfermode
     * @param xfermode
     */
    public void setPaintXfermode(@Nullable Xfermode xfermode) {
        this.paintXfermode = xfermode;
    }

    @Nullable
    public PathEffect getPathEffect() {
        return pathEffect;
    }

    /**
     * 设置画笔的 PathEffect
     * @param pathEffect
     */
    public void setPathEffect(@Nullable PathEffect pathEffect) {
        this.pathEffect = pathEffect;
    }

    @Nullable
    public BlendMode getBlendMode() {
        return blendMode;
    }

    /**
     * 设置画笔的 BlendMode
     * @param blendMode
     */
    @Nullable
    @RequiresApi(Build.VERSION_CODES.Q)
    public void setBlendMode(@Nullable BlendMode blendMode) {
        this.blendMode = blendMode;
    }

    /**
     * 设置触摸点的颜色
     * @param touchPointColor
     */
    public void setTouchPointColor(int touchPointColor) {
        this.touchPointColor = touchPointColor;
    }

    /**
     * 获取绘制文本的颜色
     * @return
     */
    public int getDrawTextColor() {
        return paintColor;
    }

    /**
     * 设置绘制文本的颜色
     * @param drawTextColor
     */
    public void setDrawTextColor(int drawTextColor) {
        this.paintColor = drawTextColor;
    }

    /**
     * 绘制文本的字体大小
     * @param drawTextSize
     */
    public void setDrawTextSize(float drawTextSize) {
        this.drawTextSize = drawTextSize;
    }

    /**
     * 绘制文本是否是粗体
     * @param fakeBoldText
     */
    public void setDrawTextBold(boolean fakeBoldText) {
        isFakeBoldText = fakeBoldText;
    }

    /**
     * 绘制文本是否需要下划线
     * @param underlineText
     */
    public void setDrawTextUnderline(boolean underlineText) {
        isUnderlineText = underlineText;
    }

    /**
     * 设置画笔线条描边宽度
     * @param lineStrokeWidth
     */
    public void setLineStrokeWidth(float lineStrokeWidth) {
        this.lineStrokeWidth = lineStrokeWidth;
    }

    /**
     * 设置橡皮擦描边宽度
     * @param eraserStrokeWidth
     */
    public void setEraserStrokeWidth(float eraserStrokeWidth) {
        this.eraserStrokeWidth = eraserStrokeWidth;
    }

    /**
     * 设置缩放点描边宽度
     * @param zoomPointStrokeWidth
     */
    public void setZoomPointStrokeWidth(float zoomPointStrokeWidth) {
        this.zoomPointStrokeWidth = zoomPointStrokeWidth;
    }

    /**
     * 是否自适应
     * @return
     */
    public boolean isFit() {
        return isFit;
    }

    /**
     * 设置是否自适应
     * @param fit
     */
    public void setFit(boolean fit) {
        isFit = fit;
    }

    /**
     * 是否启用绘图
     * @return
     */
    public boolean isDrawEnabled() {
        return isDrawEnabled;
    }

    /**
     * 设置是否启用绘图
     * @param drawEnabled
     */
    public void setDrawEnabled(boolean drawEnabled) {
        isDrawEnabled = drawEnabled;
    }

    /**
     * 是否启用缩放
     * @return
     */
    public boolean isZoomEnabled() {
        return isZoomEnabled;
    }

    /**
     * 设置是否启用缩放
     * @param zoomEnabled
     */
    public void setZoomEnabled(boolean zoomEnabled) {
        isZoomEnabled = zoomEnabled;
    }

    /**
     * 是否可撤销
     * @return
     */
    public boolean isHasUndo() {
        return hasUndo;
    }

    /**
     * 是否可恢复
     * @return
     */
    public boolean isHasRedo() {
        return hasRedo;
    }

    /**
     * 获取当前图片在矩阵上的横向偏移值
     * @return
     */
    public float getCurrentTranslateX() {
        return currentTranslateX;
    }

    /**
     * 获取当前图片在矩阵上的纵向偏移值
     * @return
     */
    public float getCurrentTranslateY() {
        return currentTranslateY;
    }

    /**
     * 相对的变焦倍数
     * @return
     */
    public float getZoom() {
        return currentRatio / initRatio;
    }

    /**
     * 真实的变焦倍数
     * @return
     */
    public float getRealZoom() {
        return currentRatio;
    }

    /**
     * 设置需要绘制的文本内容，当为 {@link DrawMode#DRAW_TEXT} 模式时生效
     * @param drawText
     */
    public void setDrawText(String drawText) {
        this.drawText = drawText;
    }

    /**
     * 设置需要绘制的位图，当为 {@link DrawMode#DRAW_BITMAP} 模式时生效
     * @param drawBitmap
     */
    public void setDrawBitmap(Bitmap drawBitmap) {
        this.drawBitmap = drawBitmap;
    }

    /**
     * 设置需要位置的位图锚点是否居中
     * @param drawBitmapAnchorCenter
     */
    public void setDrawBitmapAnchorCenter(boolean drawBitmapAnchorCenter) {
        isDrawBitmapAnchorCenter = drawBitmapAnchorCenter;
    }

    /**
     * 缩放监听
     * @param onZoomListener
     */
    public void setOnZoomListener(OnZoomListener onZoomListener) {
        this.onZoomListener = onZoomListener;
    }

    /**
     * 缩放监听
     */
    public interface OnZoomListener{
        /**
         * 缩放更新
         * @param realZoom 图片的真实的变焦倍数
         * @param zoom 图片的相对变焦倍数
         */
        void onZoomUpdate(float realZoom, float zoom);
    }

    /**
     * 绘图监听
     * @param onDrawListener
     */
    public void setOnDrawListener(OnDrawListener onDrawListener) {
        this.onDrawListener = onDrawListener;
    }
    /**
     * 绘图监听
     */
    public interface OnDrawListener{
        /**
         * 绘制监听
         * @param draw
         */
        void onDraw(Draw draw);
    }
}
