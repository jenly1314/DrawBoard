# DrawBoard

![Image](app/src/main/ic_launcher-playstore.png)

[![Download](https://img.shields.io/badge/download-App-blue.svg)](https://raw.githubusercontent.com/jenly1314/DrawBoard/master/app/release/app-release.apk)
[![MavenCentral](https://img.shields.io/maven-central/v/com.github.jenly1314/drawboard)](https://repo1.maven.org/maven2/com/github/jenly1314/drawboard)
[![JitPack](https://jitpack.io/v/jenly1314/DrawBoard.svg)](https://jitpack.io/#jenly1314/DrawBoard)
[![CircleCI](https://circleci.com/gh/jenly1314/DrawBoard.svg?style=svg)](https://circleci.com/gh/jenly1314/DrawBoard)
[![API](https://img.shields.io/badge/API-21%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/mit-license.php)

DrawBoard 是一个自定义 View实现的画板；方便对图片进行编辑和各种涂鸦相关操作。

* 主要支持的一些操作：撤销、恢复、清空、缩放；
* 主要支持的绘制模式：画路径、画点、画直线、画矩形、画椭圆、画圆、画文字、画图片、橡皮擦、马赛克等。

## Gif 展示
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

2. 在Module的 **build.gradle** 里面添加引入依赖项

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

<!-- end -->

## 版本日志

#### v1.1.0：2024-3-18
* 新增支持绘制马赛克
* 绘制直线时，支持带箭头
* 绘制图片或文字时，支持选中再次移动（限最近一次操作）
* 对外提供更多绘制相关配置
* 更新Gradle至v7.5

#### [查看更多版本日志](CHANGELOG.md)

## 赞赏
如果你喜欢DrawBoard，或感觉DrawBoard帮助到了你，可以点右上角“Star”支持一下，你的支持就是我的动力，谢谢 :smiley:
<p>您也可以扫描下面的二维码，请作者喝杯咖啡 :coffee:

<div>
   <img src="https://jenly1314.github.io/image/page/rewardcode.png">
</div>

## 关于我

| 我的博客                                                                                | GitHub                                                                                  | Gitee                                                                                  | CSDN                                                                                 | 博客园                                                                            |
|:------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------|
| <a title="我的博客" href="https://jenly1314.github.io" target="_blank">Jenly's Blog</a> | <a title="GitHub开源项目" href="https://github.com/jenly1314" target="_blank">jenly1314</a> | <a title="Gitee开源项目" href="https://gitee.com/jenly1314" target="_blank">jenly1314</a>  | <a title="CSDN博客" href="http://blog.csdn.net/jenly121" target="_blank">jenly121</a>  | <a title="博客园" href="https://www.cnblogs.com/jenly" target="_blank">jenly</a>  |

## 联系我

| 微信公众号        | Gmail邮箱                                                                          | QQ邮箱                                                                              | QQ群                                                                                                                       | QQ群                                                                                                                       |
|:-------------|:---------------------------------------------------------------------------------|:----------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------|
| [Jenly666](http://weixin.qq.com/r/wzpWTuPEQL4-ract92-R) | <a title="给我发邮件" href="mailto:jenly1314@gmail.com" target="_blank">jenly1314</a> | <a title="给我发邮件" href="mailto:jenly1314@vip.qq.com" target="_blank">jenly1314</a> | <a title="点击加入QQ群" href="https://qm.qq.com/cgi-bin/qm/qr?k=6_RukjAhwjAdDHEk2G7nph-o8fBFFzZz" target="_blank">20867961</a> | <a title="点击加入QQ群" href="https://qm.qq.com/cgi-bin/qm/qr?k=Z9pobM8bzAW7tM_8xC31W8IcbIl0A-zT" target="_blank">64020761</a> |

<div>
   <img src="https://jenly1314.github.io/image/page/footer.png">
</div>


