package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


import java.util.Calendar;

public class Ura extends View {
    private Paint circlePaint;
    private Paint handPaint;

    private int radius;
    private int centerX;
    private int centerY;

    private int hours;
    private int minutes;
    private int seconds;

    public Ura(Context context, AttributeSet attrs) {
        super(context, attrs);
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);

        handPaint = new Paint();
        handPaint.setColor(Color.BLACK);
        handPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        handPaint.setStrokeWidth(10);

        hours = Calendar.getInstance().get(Calendar.HOUR);
        minutes = Calendar.getInstance().get(Calendar.MINUTE);
        seconds = Calendar.getInstance().get(Calendar.SECOND);
    }

    public Ura(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        radius = Math.min(getWidth(), getHeight()) / 2 - 10;
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        // Draw the clock face
        canvas.drawCircle(centerX, centerY, radius, circlePaint);

        // Draw the clock numbers
        Paint numberPaint = new Paint();
        numberPaint.setColor(Color.BLACK);
        numberPaint.setTextSize(40);
        numberPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("12", centerX, centerY - radius + 50, numberPaint);
        canvas.drawText("6", centerX, centerY + radius - 50, numberPaint);
        canvas.drawText("3", centerX + radius - 50, centerY, numberPaint);
        canvas.drawText("9", centerX - radius + 50, centerY, numberPaint);
        // Draw the clock lines
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(5);
        canvas.drawLine(centerX, centerY - radius + 20, centerX, centerY - radius + 50, linePaint);
        canvas.drawLine(centerX + radius - 20, centerY, centerX + radius - 50, centerY, linePaint);
        canvas.drawLine(centerX, centerY + radius - 20, centerX, centerY + radius - 50, linePaint);
        canvas.drawLine(centerX - radius + 20, centerY, centerX - radius + 50, centerY, linePaint);

        // Draw the clock hands
        float hourAngle = (hours + (float) minutes / 60) / 12 * 360;
        canvas.save();
        canvas.rotate(hourAngle, centerX, centerY);
        canvas.drawLine(centerX, centerY + 20, centerX, centerY - radius / 2, handPaint);
        canvas.restore();

        float minuteAngle = minutes / 60f * 360;
        canvas.save();
        canvas.rotate(minuteAngle, centerX, centerY);
        canvas.drawLine(centerX, centerY + 20, centerX, centerY - radius + 50, handPaint);
        canvas.restore();

        // Draw the second hand
        Paint secondHandPaint = new Paint();
        secondHandPaint.setColor(Color.RED);
        secondHandPaint.setStyle(Paint.Style.STROKE);
        secondHandPaint.setStrokeWidth(3);

        float secondAngle = Calendar.getInstance().get(Calendar.SECOND) / 60f * 360;
        canvas.save();
        canvas.rotate(secondAngle, centerX, centerY);
        canvas.drawLine(centerX, centerY + 20, centerX, centerY - radius + 20, secondHandPaint);
        canvas.restore();

        // Draw the center circle
        Paint centerPaint = new Paint();
        centerPaint.setColor(Color.WHITE);
        canvas.drawCircle(centerX, centerY, 20, centerPaint);

        postInvalidateDelayed(500);
        invalidate();
    }

    public void setTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
        invalidate();
    }
}
