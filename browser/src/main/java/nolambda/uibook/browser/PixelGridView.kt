package nolambda.uibook.browser

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class PixelGridView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_ALPHA = 50
    }

    //number of row and column 
    private var horizontalGridCount = 20

    private val horizontal: Drawable = ColorDrawable(Color.BLACK).apply {
        alpha = DEFAULT_ALPHA
    }
    private val vertical: Drawable = ColorDrawable(Color.BLACK).apply {
        alpha = DEFAULT_ALPHA
    }

    private val width: Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        0.9f,
        context.resources.displayMetrics
    )

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        horizontal.setBounds(left, 0, right, width.toInt())
        vertical.setBounds(0, top, width.toInt(), bottom)
    }

    private fun getLinePosition(lineNumber: Int): Float {
        val lineCount = horizontalGridCount
        return 1f / (lineCount + 1) * (lineNumber + 1f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // drawTask.start();
        val count = horizontalGridCount
        for (n in 0 until count) {
            val pos = getLinePosition(n)

            // Draw horizontal line
            canvas.translate(0f, pos * height)
            horizontal.draw(canvas)
            canvas.translate(0f, -pos * height)

            // Draw vertical line
            canvas.translate(pos * getWidth(), 0f)
            vertical.draw(canvas)
            canvas.translate(-pos * getWidth(), 0f)
        }
        //drawTask.end(count);
    }
}