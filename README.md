# DrawBoard

![Image](app/src/main/ic_launcher-playstore.png)

[![MavenCentral](https://img.shields.io/maven-central/v/com.github.jenly1314/drawboard?logo=sonatype)](https://repo1.maven.org/maven2/com/github/jenly1314/DrawBoard)
[![JitPack](https://img.shields.io/jitpack/v/github/jenly1314/DrawBoard?logo=jitpack)](https://jitpack.io/#jenly1314/DrawBoard)
[![CI](https://img.shields.io/github/actions/workflow/status/jenly1314/DrawBoard/build.yml?logo=github)](https://github.com/jenly1314/DrawBoard/actions/workflows/build.yml)
[![Download](https://img.shields.io/badge/download-APK-brightgreen?logo=github)](https://raw.githubusercontent.com/jenly1314/DrawBoard/master/app/release/app-release.apk)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen?logo=android)](https://developer.android.com/guide/topics/manifest/uses-sdk-element#ApiLevels)
[![License](https://img.shields.io/github/license/jenly1314/DrawBoard?logo=open-source-initiative)](https://opensource.org/licenses/mit)


DrawBoard 是一个自定义 View实现的画板；方便对图片进行编辑和各种涂鸦相关操作。

* 主要支持的一些操作：撤销、恢复、清空、缩放；
* 主要支持的绘制模式：画路径、画点、画直线、画矩形、画椭圆、画圆、画文字、画图片、橡皮擦、马赛克等。

## 效果展示
![Image](GIF.gif)

> 你也可以直接下载 [演示App](https://raw.githubusercontent.com/jenly1314/DrawBoard/master/app/release/app-release.apk) 体验效果

## 引入

### Gradle:

1. 在Project的 **build.gradle** 或 **setting.gradle** 中添加远程仓库

    ```gradle
    repositories {
        //...
        mavenCentral()
    }
    ```

2. 在Module的 **build.gradle** 中添加依赖项

    ```gradle
    implementation 'com.github.jenly1314:drawboard:1.1.0'
    ```
## 使用

### DrawBoardView自定义属性说明
| 属性 | 属性类型      | 默认值                                  | 属性说明          |
| :------|:----------|:-------------------------------------|:--------------|
| dbvMinZoom | float     | 1.0                                  | 支持最小的放大倍数     |
| dbvMaxZoom | float     | 4.0                                  | 支持最大的放大倍数     |
| dbvFit | boolean   | true                                 | 是否自适应         |
| dbvDrawEnabled | boolean   | true                                 | 是否启用绘图        |
| dbvZoomEnabled | boolean   | true                                 | 是否启用缩放        |
| dbvShowTouchPoint | boolean   | true                                 | 是否显示触摸点       |
| dbvShowSelectedBox | boolean   | false                                | 是否显示选中框       |
| android:src | drawable  |                                      | 设置源图片（画板背景图层） |
| dbvPaintColor | color     | <font color=#FF0000>#FFFF0000</font> | 画笔的颜色         |
| dbvSelectedBoxColor | color     | <font color=#FF0000>#AFFF0000</font> | 选中框颜色         |
| dbvTouchPointColor | color     | <font color=#CCCCCC>#AFCCCCCC</font> | 触摸点的颜色        |
| dbvLineStrokeWidth | dimension | 2dp                                  | 画笔线条笔划宽度      |
| dbvSelectedStrokeWidth | dimension | 1dp                                  | 选中时边框笔划宽度     |
| dbvEraserStrokeWidth | dimension | 10dp                                 | 橡皮擦笔划宽度       |
| dbvMosaicStrokeWidth | dimension | 10dp                                 | 马赛克比笔划宽度      |
| dbvZoomPointStrokeWidth | dimension | 6dp                                  | 缩放点笔划宽度       |
| dbvDrawTextSize | dimension | 15sp                                 | 绘制文本的字体大小     |
| dbvDrawTextBold | boolean   | false                                | 绘制文本是否是粗体     |
| dbvDrawTextUnderline | boolean   | false                                | 绘制文本是否需要下划线   |
| dbvTouchTolerance | float     | 4.0                                  | 触摸时允许的容差值     |
| dbvTouchPointRatio | float     | 1.2                                  | 触摸点的比例        |
| dbvDrawLineArrow | boolean   | false                                | 绘制直线时是否带箭头    |
| dbvLineArrowSize | dimension   | 6dp                                  | 绘制直线时的箭头大小    |

### 示例

布局示例
```xml

    <com.king.drawboard.view.DrawBoardView
        android:id="@+id/drawBoardView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

```
### 说明

主要支持的绘制模式
```kotlin

/**
 * 绘制路径
 */
DrawMode.DRAW_PATH
/**
 * 绘制点
 */
DrawMode.DRAW_POINT
/**
 * 绘制线
 */
DrawMode.DRAW_LINE
/**
 * 绘制矩形
 */
DrawMode.DRAW_RECT
/**
 * 绘制椭圆
 */
DrawMode.DRAW_OVAL
/**
 * 绘制圆
 */
DrawMode.DRAW_CIRCLE
/**
 * 绘制文本
 */
DrawMode.DRAW_TEXT
/**
 * 绘制图片
 */
DrawMode.DRAW_BITMAP
/**
 * 橡皮擦
 */
DrawMode.ERASER
/**
 * 马赛克
 */
DrawMode.MOSAIC

```
> 如果以上操作不完全满足你的需求，`DrawBoard`对外还提供了很多可定制化的配置（例如：自实现一个 [`Draw`](drawboard/src/main/java/com/king/drawboard/draw/Draw.java)）。

```kotlin
/**
 * 如设置绘制模式为：DrawMode.DRAW_PATH
 */
drawBoardView.setDrawMode(DrawMode.DRAW_PATH)
```

主要常用的一些方法
```kotlin
/**
 * 撤销一步
 */
drawBoardView.undo()
/**
 * 恢复一步
 */
drawBoardView.redo()
/**
 * 清除画布
 */
drawBoardView.clear()
/**
 * 改变绘制模式
 */
drawBoardView.setDrawMode(drawMode)
/**
 * 设置画笔颜色
 */
drawBoardView.setPaintColor(paintColor)
/**
 * 设置画笔线条笔划宽度
 */
drawBoardView.setLineStrokeWidth(lineStrokeWidth)
/**
 * 设置橡皮擦笔划宽度
 */
drawBoardView.setEraserStrokeWidth(eraserStrokeWidth)
/**
 * 设置马赛克笔划宽度
 */
drawBoardView.setMosaicStrokeWidth(mosaicStrokeWidth)
/**
 * 通过代码进行绘制
 */
drawBoardView.draw(draw)
/**
 * 设置图片（画板背景图层）
 */
drawBoardView.setImageBitmap(bitmap)
/**
 * 获取结果图片（画板背景图层和画板图层合并后的图片）
 */
drawBoardView.getResultBitmap()

```

更多使用详情，请查看[app](app)中的源码使用示例或直接查看[API帮助文档](https://jenly1314.github.io/DrawBoard/api/)

## 相关推荐

- [ImageViewer](https://github.com/jenly1314/ImageViewer) 一个图片查看器，一般用来查看图片详情或查看大图时使用。
- [SpinCounterView](https://github.com/jenly1314/SpinCounterView) 一个类似码表变化的旋转计数器动画控件。
- [CounterView](https://github.com/jenly1314/CounterView) 一个数字变化效果的计数器视图控件。
- [RadarView](https://github.com/jenly1314/RadarView) 一个雷达扫描动画后，然后展示得分效果的控件。
- [SuperTextView](https://github.com/jenly1314/SuperTextView) 一个在TextView的基础上扩展了几种动画效果的控件。
- [LoadingView](https://github.com/jenly1314/LoadingView) 一个圆弧加载过渡动画，圆弧个数，大小，弧度，渐变颜色，完全可配。
- [WaveView](https://github.com/jenly1314/WaveView) 一个水波纹动画控件视图，支持波纹数，波纹振幅，波纹颜色，波纹速度，波纹方向等属性完全可配。
- [GiftSurfaceView](https://github.com/jenly1314/GiftSurfaceView) 一个适用于直播间送礼物拼图案的动画控件。
- [FlutteringLayout](https://github.com/jenly1314/FlutteringLayout) 一个适用于直播间点赞桃心飘动效果的控件。
- [DragPolygonView](https://github.com/jenly1314/DragPolygonView) 一个支持可拖动多边形，支持通过拖拽多边形的角改变其形状的任意多边形控件。
- [CircleProgressView](https://github.com/jenly1314/CircleProgressView) 一个圆形的进度动画控件，动画效果纵享丝滑。
- [ArcSeekBar](https://github.com/jenly1314/ArcSeekBar) 一个弧形的拖动条进度控件，配置参数完全可定制化。
- [compose-component](https://github.com/jenly1314/compose-component) 一个Jetpack Compose的组件库；主要提供了一些小组件，便于快速使用。

<!-- end -->

## 版本日志

#### v1.1.0：2024-3-18
* 新增支持绘制马赛克
* 绘制直线时，支持带箭头
* 绘制图片或文字时，支持选中再次移动（限最近一次操作）
* 对外提供更多绘制相关配置
* 更新Gradle至v7.5

#### [查看更多版本日志](CHANGELOG.md)

---

![footer](https://jenly1314.github.io/page/footer.svg)

