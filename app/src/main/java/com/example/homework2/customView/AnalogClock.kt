package com.example.homework2.customView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import com.example.homework2.R
import java.lang.Integer.max
import kotlin.math.min
import kotlin.properties.Delegates

@SuppressLint("ViewConstructor")
class AnalogClock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.DefaultAnalogClockFieldStyle,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var analogClockFields: AnalogClockFields? = null
        set(value) {
            field = value
            updateViewSize()
            requestLayout()
            invalidate()
        }

    private var hourHandColor by Delegates.notNull<Int>()
    private var minuteHandColor by Delegates.notNull<Int>()
    private var secondHandColor by Delegates.notNull<Int>()
    private var hourHandSize by Delegates.notNull<Float>()
    private var minuteHandSize by Delegates.notNull<Float>()
    private var secondHandSize by Delegates.notNull<Float>()
    private var colorDigit by Delegates.notNull<Int>()
    private var colorBackground by Delegates.notNull<Int>()

    private val fieldRect = RectF(0f, 0f, 0f, 0f)
    private var centerX = 0f
    private var centerY = 0f
    private var clockRadius = 0
    private var digitSize = 0

    private lateinit var hourHandPaint: Paint
    private lateinit var minuteHandPaint: Paint
    private lateinit var secondHandPaint: Paint
    private lateinit var digitPaint: Paint
    private lateinit var backgroundPaint: Paint

    init {
        if (attrs != null) {
            initAttributes(attrs, defStyleAttr, defStyleRes)
        } else {
            initDefaultSettings()
        }
        initPaints()
        if (isInEditMode) {
            analogClockFields = AnalogClockFields(8, 6, 30, 2, 2)
        }
    }

    private fun initPaints() {
        hourHandPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        hourHandPaint.color = hourHandColor
        hourHandPaint.style = Paint.Style.STROKE
        hourHandPaint.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            4f, resources.displayMetrics
        )

        minuteHandPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        minuteHandPaint.color = minuteHandColor
        minuteHandPaint.style = Paint.Style.STROKE
        minuteHandPaint.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2f, resources.displayMetrics
        )

        secondHandPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        secondHandPaint.color = secondHandColor
        secondHandPaint.style = Paint.Style.STROKE
        secondHandPaint.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            1f, resources.displayMetrics
        )

        digitPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        digitPaint.color = colorDigit
        digitPaint.style = Paint.Style.FILL

        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint.color = colorBackground
        backgroundPaint.style = Paint.Style.FILL

    }

    private fun initAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.AnalogClock,
            defStyleAttr,
            defStyleRes
        )
        hourHandColor = typedArray.getColor(R.styleable.AnalogClock_hourHandColor, HOUR_HAND_COLOR)
        minuteHandColor =
            typedArray.getColor(R.styleable.AnalogClock_minuteHandColor, MINUTE_HAND_COLOR)
        secondHandColor =
            typedArray.getColor(R.styleable.AnalogClock_secondHandColor, SECOND_HAND_COLOR)
        hourHandSize =
            typedArray.getFloat(R.styleable.AnalogClock_hourHandSize, HOUR_HAND_SIZE_PERCENT)
        minuteHandSize =
            typedArray.getFloat(R.styleable.AnalogClock_minuteHandSize, MINUTE_HAND_SIZE_PERCENT)
        secondHandSize =
            typedArray.getFloat(R.styleable.AnalogClock_secondHandSize, SECOND_HAND_SIZE_PERCENT)
        colorDigit = typedArray.getColor(R.styleable.AnalogClock_colorDigit, COLOR_DIGIT)
        colorBackground =
            typedArray.getColor(R.styleable.AnalogClock_colorBackground, COLOR_BACKGROUND)
        typedArray.recycle()
    }

    private fun initDefaultSettings() {
        hourHandColor = HOUR_HAND_COLOR
        minuteHandColor = MINUTE_HAND_COLOR
        secondHandColor = SECOND_HAND_COLOR
        hourHandSize = HOUR_HAND_SIZE_PERCENT
        minuteHandSize = MINUTE_HAND_SIZE_PERCENT
        secondHandSize = SECOND_HAND_SIZE_PERCENT
        colorDigit = COLOR_DIGIT
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val minHeight = suggestedMinimumHeight + paddingTop + paddingBottom
        val desiredRadiusInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            DESIRED_RADIUS.toFloat(), resources.displayMetrics
        ).toInt()
        val desiredWidth = max(minWidth, desiredRadiusInPixels * 2 + paddingLeft + paddingRight)
        val desiredHeight = max(minHeight, desiredRadiusInPixels * 2 + paddingTop + paddingBottom)
        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateViewSize()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (analogClockFields == null) return
        if (clockRadius == 0) return
        if (fieldRect.width() <= 0) return
        if (fieldRect.height() <= 0) return

        drawBackground(canvas)
        drawDigits(canvas)
        drawHands(canvas)
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawCircle(
            centerX, centerY,
            clockRadius.toFloat(), backgroundPaint
        )
    }

    private fun drawDigits(canvas: Canvas) {
        if ((analogClockFields!!.day > 0) && (analogClockFields!!.day < 32)) {
            digitPaint.textSize = digitSize.toFloat()
            val textWidthDiv2 = digitPaint.measureText(analogClockFields!!.day.toString()) / 2
            canvas.drawText(
                analogClockFields!!.day.toString(),
                centerX / 2 - textWidthDiv2,
                centerY,
                digitPaint
            )
        }
        if ((analogClockFields!!.month > 0) && (analogClockFields!!.month < 13)) {
            val textWidthDiv2 = digitPaint.measureText(analogClockFields!!.month.toString()) / 2
            canvas.drawText(
                analogClockFields!!.month.toString(),
                centerX + centerX / 2 - textWidthDiv2,
                centerY,
                digitPaint
            )
        }

        for (i in 1..12) {
            canvas.rotate((30).toFloat(), centerX, centerY)
            val textWidthDiv2 = digitPaint.measureText(i.toString()) / 2
            canvas.drawText(
                i.toString(),
                centerX - textWidthDiv2,
                digitSize.toFloat(),
                digitPaint
            )
        }
    }

    private fun drawHands(canvas: Canvas) {
        canvas.rotate((analogClockFields!!.hour * 30).toFloat(), centerX, centerY)
        canvas.drawLine(
            centerX,
            centerY + clockRadius * TAIL_HAND_SIZE_PERCENT,
            centerX,
            centerY - clockRadius * hourHandSize,
            hourHandPaint
        )
        canvas.rotate((-analogClockFields!!.hour * 30.toFloat()), centerX, centerY)

        canvas.rotate((analogClockFields!!.minute * 6).toFloat(), centerX, centerY)
        canvas.drawLine(
            centerX,
            centerY + clockRadius * TAIL_HAND_SIZE_PERCENT,
            centerX,
            centerY - clockRadius * minuteHandSize,
            minuteHandPaint
        )
        canvas.rotate((-analogClockFields!!.minute * 6.toFloat()), centerX, centerY)

        canvas.rotate((analogClockFields!!.second * 6).toFloat(), centerX, centerY)
        canvas.drawLine(
            centerX,
            centerY + clockRadius * TAIL_HAND_SIZE_PERCENT,
            centerX,
            centerY - clockRadius * secondHandSize,
            secondHandPaint
        )
        canvas.rotate((-analogClockFields!!.second * 6.toFloat()), centerX, centerY)
    }


    private fun updateViewSize() {
        val field: AnalogClockFields = this.analogClockFields ?: return
        val safeWidth = width - paddingRight - paddingLeft
        val safeHeight = height - paddingTop - paddingBottom
        val radius = min(safeWidth, safeHeight) / 2f
        fieldRect.left = paddingLeft + (safeWidth - radius * 2) / 2
        fieldRect.top = paddingTop + (safeHeight - radius * 2) / 2
        fieldRect.right = fieldRect.left + radius * 2
        fieldRect.bottom = fieldRect.top + radius * 2
        clockRadius = radius.toInt()
        centerX = (fieldRect.right - fieldRect.left) / 2f
        centerY = (fieldRect.bottom - fieldRect.top) / 2f
        digitSize = clockRadius / 8
    }

    companion object {
        const val HOUR_HAND_COLOR = Color.WHITE
        const val MINUTE_HAND_COLOR = Color.WHITE
        const val SECOND_HAND_COLOR = Color.WHITE
        const val HOUR_HAND_SIZE_PERCENT = 0.40f
        const val MINUTE_HAND_SIZE_PERCENT = 0.55f
        const val SECOND_HAND_SIZE_PERCENT = 0.70f
        const val TAIL_HAND_SIZE_PERCENT = 0.10f
        const val COLOR_DIGIT = Color.WHITE
        const val COLOR_BACKGROUND = Color.BLACK
        const val DESIRED_RADIUS = 100
    }
}