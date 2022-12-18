package nolambda.uibook.browser.measurement

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import nolambda.uibook.browser.app.R

internal class MeasurementOverlayView(context: Context) : FrameLayout(context) {

    private lateinit var contentRoot: ViewGroup
    private lateinit var measurementHelper: MeasurementHelper

    private val path = Path()
    private val outRect = Rect()

    private val primaryColor = ContextCompat.getColor(context, R.color.hype_blue)

    private val paintDashed: Paint = Paint().apply {
        color = primaryColor
        style = Paint.Style.STROKE
        strokeWidth = 4f
        pathEffect = DashPathEffect(floatArrayOf(10f, 20f), 0F)
    }

    private val paintPrimary: Paint = Paint().apply {
        color = primaryColor
        style = Paint.Style.STROKE
        strokeWidth = 6f
    }

    private val paintSecondary: Paint = Paint().apply {
        color = primaryColor
        style = Paint.Style.STROKE
        strokeWidth = 6f
    }

    private val paintText: TextPaint = TextPaint().apply {
        color = primaryColor
        textSize = 45f
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 2f
    }

    @Px
    private val measurementTextOffset: Int = resources.getDimensionPixelSize(R.dimen.spacing4)
    private var currentView: View? = null
    private var rectPrimary: Rect? = null
    private var rectSecondary: Rect? = null
    private var measurementWidthText: TextView? = null
    private var measurementHeightText: TextView? = null

    /* Used for nested measurementHelper */
    private var measurementLeftText: TextView? = null
    private var measurementTopText: TextView? = null
    private var measurementRightText: TextView? = null
    private var measurementBottomText: TextView? = null

    init {
        setWillNotDraw(false)
    }

    fun setMeasurementHelper(helper: MeasurementHelper) {
        contentRoot = helper.getContentRoot()
        measurementHelper = helper
    }

    override fun onDraw(canvas: Canvas) {
        if (rectPrimary == null) {
            return
        }

        //rectangle around target
        canvas.drawRect(rectPrimary!!, paintPrimary)

        /*
         * guidelines
         */
        // top-left to top
        path.reset()
        path.moveTo(rectPrimary!!.left.toFloat(), 0f)
        path.lineTo(rectPrimary!!.left.toFloat(), rectPrimary!!.top.toFloat())
        canvas.drawPath(path, paintDashed)

        // top-right to top
        path.reset()
        path.moveTo(rectPrimary!!.right.toFloat(), 0f)
        path.lineTo(rectPrimary!!.right.toFloat(), rectPrimary!!.top.toFloat())
        canvas.drawPath(path, paintDashed)

        // top-left to left
        path.reset()
        path.moveTo(0f, rectPrimary!!.top.toFloat())
        path.lineTo(rectPrimary!!.left.toFloat(), rectPrimary!!.top.toFloat())
        canvas.drawPath(path, paintDashed)

        // bottom-left to left
        path.reset()
        path.moveTo(0f, rectPrimary!!.bottom.toFloat())
        path.lineTo(rectPrimary!!.left.toFloat(), rectPrimary!!.bottom.toFloat())
        canvas.drawPath(path, paintDashed)

        // bottom-left to bottom
        path.reset()
        path.moveTo(rectPrimary!!.left.toFloat(), rectPrimary!!.bottom.toFloat())
        path.lineTo(rectPrimary!!.left.toFloat(), bottom.toFloat())
        canvas.drawPath(path, paintDashed)

        // bottom-right to bottom
        path.reset()
        path.moveTo(rectPrimary!!.right.toFloat(), rectPrimary!!.bottom.toFloat())
        path.lineTo(rectPrimary!!.right.toFloat(), bottom.toFloat())
        canvas.drawPath(path, paintDashed)

        // bottom-right to right
        path.reset()
        path.moveTo(rectPrimary!!.right.toFloat(), rectPrimary!!.bottom.toFloat())
        path.lineTo(right.toFloat(), rectPrimary!!.bottom.toFloat())
        canvas.drawPath(path, paintDashed)

        // top-right to right
        path.reset()
        path.moveTo(rectPrimary!!.right.toFloat(), rectPrimary!!.top.toFloat())
        path.lineTo(right.toFloat(), rectPrimary!!.top.toFloat())
        canvas.drawPath(path, paintDashed)
        if (rectSecondary == null) {
            // draw width measurement text
            if (measurementWidthText != null) {
                canvas.save()
                canvas.translate(
                    (rectPrimary!!.centerX() - measurementWidthText!!.width / 2).toFloat(), (
                            rectPrimary!!.top - measurementWidthText!!.height - measurementTextOffset).toFloat()
                )
                measurementWidthText!!.draw(canvas)
                canvas.restore()
            }

            // draw height measurement text
            if (measurementHeightText != null) {
                canvas.save()
                canvas.translate(
                    (rectPrimary!!.right + measurementTextOffset).toFloat(), (
                            rectPrimary!!.bottom - rectPrimary!!.height() / 2 - measurementHeightText!!.height / 2).toFloat()
                )
                measurementHeightText!!.draw(canvas)
                canvas.restore()
            }
        } else {
            canvas.drawRect(rectSecondary!!, paintSecondary)

            /*
             * view to view measurement views
             */if (rectPrimary!!.bottom < rectSecondary!!.top) {
                // secondary is below. draw vertical line.
                canvas.drawLine(
                    rectSecondary!!.centerX().toFloat(),
                    rectPrimary!!.bottom.toFloat(),
                    rectSecondary!!.centerX().toFloat(),
                    rectSecondary!!.top.toFloat(),
                    paintPrimary
                )
                if (measurementHeightText != null) {
                    canvas.save()
                    canvas.translate(
                        (rectSecondary!!.centerX() - measurementHeightText!!.width / 2).toFloat(), (
                                (rectPrimary!!.bottom + rectSecondary!!.top) / 2 - measurementHeightText!!.height / 2).toFloat()
                    )
                    measurementHeightText!!.draw(canvas)
                    canvas.restore()
                }
            }
            if (rectPrimary!!.right < rectSecondary!!.left) {
                // secondary is right. draw horizontal line.
                canvas.drawLine(
                    rectPrimary!!.right.toFloat(),
                    rectSecondary!!.centerY().toFloat(),
                    rectSecondary!!.left.toFloat(),
                    rectSecondary!!.centerY().toFloat(),
                    paintPrimary
                )
                if (measurementWidthText != null) {
                    canvas.save()
                    canvas.translate(
                        ((rectPrimary!!.right + rectSecondary!!.left) / 2 - measurementWidthText!!.width / 2).toFloat(),
                        (
                                rectSecondary!!.centerY() - measurementWidthText!!.height / 2).toFloat()
                    )
                    measurementWidthText!!.draw(canvas)
                    canvas.restore()
                }
            }
            if (rectSecondary!!.bottom < rectPrimary!!.top) {
                // secondary is above. draw vertical line.
                canvas.drawLine(
                    rectSecondary!!.centerX().toFloat(),
                    rectPrimary!!.top.toFloat(),
                    rectSecondary!!.centerX().toFloat(),
                    rectSecondary!!.bottom.toFloat(),
                    paintPrimary
                )
                if (measurementHeightText != null) {
                    canvas.save()
                    canvas.translate(
                        (rectSecondary!!.centerX() - measurementHeightText!!.width / 2).toFloat(), (
                                (rectPrimary!!.top + rectSecondary!!.bottom) / 2 - measurementHeightText!!.height / 2).toFloat()
                    )
                    measurementHeightText!!.draw(canvas)
                    canvas.restore()
                }
            }
            if (rectSecondary!!.right < rectPrimary!!.left) {
                // secondary is left. draw horizontal line.
                canvas.drawLine(
                    rectPrimary!!.left.toFloat(),
                    rectSecondary!!.centerY().toFloat(),
                    rectSecondary!!.right.toFloat(),
                    rectSecondary!!.centerY().toFloat(),
                    paintPrimary
                )
                if (measurementWidthText != null) {
                    canvas.save()
                    canvas.translate(
                        ((rectPrimary!!.left + rectSecondary!!.right) / 2 - measurementWidthText!!.width / 2).toFloat(),
                        (
                                rectSecondary!!.centerY() - measurementWidthText!!.height / 2).toFloat()
                    )
                    measurementWidthText!!.draw(canvas)
                    canvas.restore()
                }
            }

            // check nested
            var inside: Rect? = null
            var outside: Rect? = null
            if (rectPrimary!!.contains(rectSecondary!!)) {
                outside = rectPrimary
                inside = rectSecondary
            } else if (rectSecondary!!.contains(rectPrimary!!)) {
                outside = rectSecondary
                inside = rectPrimary
            }

            /*
             * parent to child measurement views
             */if (inside != null && outside != null) {
                // left inside
                canvas.drawLine(
                    outside.left.toFloat(),
                    inside.centerY().toFloat(),
                    inside.left.toFloat(),
                    inside.centerY().toFloat(),
                    paintPrimary
                )
                if (measurementLeftText != null) {
                    canvas.save()
                    canvas.translate(
                        ((outside.left + inside.left) / 2 - measurementLeftText!!.width / 2).toFloat(), (
                                inside.centerY() - measurementLeftText!!.height / 2).toFloat()
                    )
                    measurementLeftText!!.draw(canvas)
                    canvas.restore()
                }

                // right inside
                canvas.drawLine(
                    outside.right.toFloat(),
                    inside.centerY().toFloat(),
                    inside.right.toFloat(),
                    inside.centerY().toFloat(),
                    paintPrimary
                )
                if (measurementRightText != null) {
                    canvas.save()
                    canvas.translate(
                        ((outside.right + inside.right) / 2 - measurementRightText!!.width / 2).toFloat(), (
                                inside.centerY() - measurementRightText!!.height / 2).toFloat()
                    )
                    measurementRightText!!.draw(canvas)
                    canvas.restore()
                }

                // top inside
                canvas.drawLine(
                    inside.centerX().toFloat(),
                    outside.top.toFloat(),
                    inside.centerX().toFloat(),
                    inside.top.toFloat(),
                    paintPrimary
                )
                if (measurementTopText != null) {
                    canvas.save()
                    canvas.translate(
                        (inside.centerX() - measurementTopText!!.width / 2).toFloat(), (
                                (outside.top + inside.top) / 2 - measurementTopText!!.height / 2).toFloat()
                    )
                    measurementTopText!!.draw(canvas)
                    canvas.restore()
                }

                // bottom inside
                canvas.drawLine(
                    inside.centerX().toFloat(),
                    outside.bottom.toFloat(),
                    inside.centerX().toFloat(),
                    inside.bottom.toFloat(),
                    paintPrimary
                )
                if (measurementBottomText != null) {
                    canvas.save()
                    canvas.translate(
                        (inside.centerX() - measurementBottomText!!.width / 2).toFloat(), (
                                (outside.bottom + inside.bottom) / 2 - measurementBottomText!!.height / 2).toFloat()
                    )
                    measurementBottomText!!.draw(canvas)
                    canvas.restore()
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            return true
        }
        if (event.action == MotionEvent.ACTION_UP) {
            val x = event.x
            val y = event.y
            val touchTarget = findTarget(contentRoot, x, y)

            // reset selection if target is root or same target.
            if (contentRoot === touchTarget || currentView === touchTarget) {
                currentView = null
                rectPrimary = null
                rectSecondary = null
            } else if (rectPrimary == null) {
                setPrimaryTarget(touchTarget)
            } else {
                setSecondaryTarget(touchTarget)
            }
            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun findTarget(root: View?, x: Float, y: Float): View? {
        if (root !is ViewGroup) return root

        // we consider the "best target" to be the view width the smallest width / height
        // whose location on screen is within the given touch area.
        var bestTarget = root
        val count = root.childCount
        for (i in 0 until count) {
            val child = root.getChildAt(i)
            measurementHelper.getContentRootLocation(child, outRect)
            if (child.visibility != VISIBLE) {
                continue
            }
            if (x >= outRect.left && x <= outRect.right && y >= outRect.top && y <= outRect.bottom) {
                val target = findTarget(child, x, y)
                if (target!!.width <= bestTarget!!.width && target.height <= bestTarget.height) {
                    bestTarget = target
                }
            }
        }
        return bestTarget
    }

    private fun setPrimaryTarget(view: View?) {
        currentView = view
        rectPrimary = Rect()
        measurementHelper.getContentRootLocation(view!!, rectPrimary!!)
        setWidthMeasurementText(rectPrimary!!.width())
        setHeightMeasurementText(rectPrimary!!.height())
    }

    private fun setSecondaryTarget(view: View?) {
        rectSecondary = Rect()
        measurementHelper.getContentRootLocation(view!!, rectSecondary!!)
        if (rectPrimary!!.bottom < rectSecondary!!.top) {
            setHeightMeasurementText(rectSecondary!!.top - rectPrimary!!.bottom)
        }
        if (rectPrimary!!.right < rectSecondary!!.left) {
            setWidthMeasurementText(rectSecondary!!.left - rectPrimary!!.right)
        }
        if (rectSecondary!!.bottom < rectPrimary!!.top) {
            setHeightMeasurementText(rectPrimary!!.top - rectSecondary!!.bottom)
        }
        if (rectSecondary!!.right < rectPrimary!!.left) {
            setWidthMeasurementText(rectPrimary!!.left - rectSecondary!!.right)
        }

        // check nested
        var inside: Rect? = null
        var outside: Rect? = null
        if (rectPrimary!!.contains(rectSecondary!!)) {
            outside = rectPrimary
            inside = rectSecondary
        } else if (rectSecondary!!.contains(rectPrimary!!)) {
            outside = rectSecondary
            inside = rectPrimary
        }
        if (inside != null && outside != null) {
            setLeftMeasurementText(inside.left - outside.left)
            setTopMeasurementText(inside.top - outside.top)
            setRightMeasurementText(outside.right - inside.right)
            setBottomMeasurementText(outside.bottom - inside.bottom)
        }
    }

    private fun setWidthMeasurementText(@Px measurement: Int) {
        if (measurement <= 0) {
            measurementWidthText = null
            return
        }
        measurementWidthText = makeMeasurementView(measurement)
    }

    private fun setHeightMeasurementText(@Px measurement: Int) {
        if (measurement <= 0) {
            measurementHeightText = null
            return
        }
        measurementHeightText = makeMeasurementView(measurement)
    }

    private fun setLeftMeasurementText(@Px measurement: Int) {
        if (measurement <= 0) {
            measurementLeftText = null
            return
        }
        measurementLeftText = makeMeasurementView(measurement)
    }

    private fun setTopMeasurementText(@Px measurement: Int) {
        if (measurement <= 0) {
            measurementTopText = null
            return
        }
        measurementTopText = makeMeasurementView(measurement)
    }

    private fun setRightMeasurementText(@Px measurement: Int) {
        if (measurement <= 0) {
            measurementRightText = null
            return
        }
        measurementRightText = makeMeasurementView(measurement)
    }

    private fun setBottomMeasurementText(@Px measurement: Int) {
        if (measurement <= 0) {
            measurementBottomText = null
            return
        }
        measurementBottomText = makeMeasurementView(measurement)
    }

    /**
     * Prepares a TextView to display a measurement
     * Centers text
     * Sets text with "dp" suffix
     * Sets selection color as text color
     * Sets TextView background
     * Lays out view
     *
     * @param measurement The measurement to display in the created TextView
     */
    private fun makeMeasurementView(@Px measurement: Int): TextView {
        val tv = TextView(context)
        tv.gravity = Gravity.CENTER
        val text = measurementHelper.toDp(measurement).toString() + "dp"
        tv.text = text
        tv.setTextColor(primaryColor)
        tv.setBackgroundResource(R.drawable.hm_rounded_measurement)
        layoutTextView(tv)
        return tv
    }

    /**
     * Lays out textview to (text dimensions + padding) * system font scale
     * Padding based on screen density
     *
     * @param tv The TextView to layout
     */
    private fun layoutTextView(tv: TextView) {
        val fontScale = resources.configuration.fontScale

        /*
         * Multiplier for padding based on phone density
         * Density of 2 does not need padding
         */
        val densityMultiplier = context.resources.displayMetrics.density - 2
        val bounds = Rect()
        paintText.getTextBounds(tv.text.toString().toCharArray(), 0, tv.text.length, bounds)
        tv.layout(
            0, 0, ((bounds.width() + 33 * densityMultiplier) * fontScale).toInt(),
            ((bounds.height() + 22 * densityMultiplier) * fontScale).toInt()
        )
    }
}
