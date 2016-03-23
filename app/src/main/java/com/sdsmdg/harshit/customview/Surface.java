package com.sdsmdg.harshit.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Surface extends View {

    int radius = 50;
    double _xDelta=0, _yDelta = 0;
    Rocket rocket;

    //circle and text colors
    private int circleCol, labelCol;
    //label text
    private String circleText;
    //paint for drawing custom view
    private Paint circlePaint;

    public Surface(Context context, AttributeSet attrs) {
        super(context, attrs);

        rocket = new Rocket(0, 0);

        circlePaint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Rocket, 0, 0);

        try {
            circleCol = a.getInteger(R.styleable.Rocket_circleColor, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rocket = new Rocket(w/2, h/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        circlePaint.setColor(circleCol);

        canvas.drawCircle(rocket.x , rocket.y , radius, circlePaint);

    }


    public int getCircleColor() {
        return circleCol;
    }


    public void setCircleColor(int newColor) {
        //update the instance variable
        circleCol = newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public void update(AccelerometerData Adata)
    {

        _xDelta = _xDelta + Adata.vx;
        _yDelta = _yDelta + Adata.vy;

        rocket.x = rocket.x - (int)_xDelta;
        rocket.y = rocket.y + (int)_yDelta;

        invalidate();

    }

}