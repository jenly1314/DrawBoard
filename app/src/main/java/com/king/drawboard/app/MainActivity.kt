package com.king.drawboard.app

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.PopupWindowCompat
import androidx.lifecycle.lifecycleScope
import com.king.drawboard.app.databinding.ActivityMainBinding
import com.king.drawboard.view.DrawBoardView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 代码示例
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
class MainActivity : AppCompatActivity() {

    var isGreen = false

    var popup: PopupWindow? = null

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val popContentView by lazy {
        LayoutInflater.from(this).inflate(R.layout.pop_select, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        popContentView.measure(0, 0)
        binding.ivDrawMode.measure(0, 0)

        // 绘制直线时，是否带箭头
//        binding.drawBoardView.isDrawLineArrow = true

        binding.drawBoardView.isShowSelectedBox = true
        binding.drawBoardView.paintColor = Color.RED

        binding.drawBoardView.setDrawText("DrawBoard")
        binding.drawBoardView.setDrawBitmap(BitmapFactory.decodeResource(resources, R.drawable.dog))

    }

    private fun showSelectPopupWindow() {
        if (this.popup?.isShowing == true) {
            this.popup?.dismiss()
            return
        }
        val popup = PopupWindow(this)
        popup.isOutsideTouchable = true
        popup.contentView = popContentView

        val padding =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12f, resources.displayMetrics)
                .toInt()

        val y = popContentView.measuredHeight + binding.ivDrawMode.measuredHeight + padding

        PopupWindowCompat.showAsDropDown(
            popup,
            binding.ivDrawMode,
            -padding,
            -y,
            Gravity.CENTER_HORIZONTAL
        )

        this.popup = popup

    }

    /**
     *  改变绘制模式
     */
    private fun changeDrawMode(@DrawBoardView.DrawMode drawMode: Int, @DrawableRes resId: Int) {
        binding.drawBoardView.drawMode = drawMode
        popup?.dismiss()
        binding.ivDrawMode.setImageResource(resId)
    }

    /**
     * 保存图片
     */
    private fun saveBitmap() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, "draw_${System.currentTimeMillis()}")
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        put(
                            MediaStore.Images.Media.RELATIVE_PATH,
                            "${Environment.DIRECTORY_PICTURES}/drawboard"
                        )
                    }
                }
                val uri = contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )

                contentResolver.openOutputStream(uri!!)?.use {
                    binding.drawBoardView.resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                }
            }
            Toast.makeText(this@MainActivity, "保存成功", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.ivDrawMode -> showSelectPopupWindow()
            R.id.ivPen -> {
                if (!isGreen) {
                    isGreen = true
                    binding.drawBoardView.paintColor = Color.GREEN
                    binding.ivPen.setImageResource(R.drawable.btn_menu_pen_green)
                } else {
                    isGreen = false
                    binding.drawBoardView.paintColor = Color.RED
                    binding.ivPen.setImageResource(R.drawable.btn_menu_pen_red)
                }
            }

            R.id.ivClear -> binding.drawBoardView.clear()
            R.id.ivUndo -> binding.drawBoardView.undo()
            R.id.ivRedo -> binding.drawBoardView.redo()
            R.id.ivSave -> saveBitmap()
            R.id.ivPath -> changeDrawMode(
                DrawBoardView.DrawMode.DRAW_PATH,
                R.drawable.btn_menu_path
            )

            R.id.ivLine -> changeDrawMode(
                DrawBoardView.DrawMode.DRAW_LINE,
                R.drawable.btn_menu_line
            )

            R.id.ivRect -> changeDrawMode(
                DrawBoardView.DrawMode.DRAW_RECT,
                R.drawable.btn_menu_rect
            )

            R.id.ivOval -> changeDrawMode(
                DrawBoardView.DrawMode.DRAW_OVAL,
                R.drawable.btn_menu_oval
            )

            R.id.ivText -> changeDrawMode(
                DrawBoardView.DrawMode.DRAW_TEXT,
                R.drawable.btn_menu_text
            )

            R.id.ivBitmap -> changeDrawMode(
                DrawBoardView.DrawMode.DRAW_BITMAP,
                R.drawable.btn_menu_bitmap
            )

            R.id.ivMosaic -> changeDrawMode(
                DrawBoardView.DrawMode.MOSAIC,
                R.drawable.btn_menu_mosaic
            )

            R.id.ivEraser -> changeDrawMode(
                DrawBoardView.DrawMode.ERASER,
                R.drawable.btn_menu_eraser
            )
        }
    }
}