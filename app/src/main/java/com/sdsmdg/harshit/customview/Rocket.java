package com.sdsmdg.harshit.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

public class Rocket extends View {

    int radius = 50;
    int X = 50, Y = 50;
    double _xDelta=0, _yDelta = 0;
    AccelerometerData Adata;

    //circle and text colors
    private int circleCol, labelCol;
    //label text
    private String circleText;
    //paint for drawing custom view
    private Paint circlePaint;

    public Rocket(Context context, AttributeSet attrs) {
        super(context, attrs);

        circlePaint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Rocket, 0, 0);

        try {
            circleCol = a.getInteger(R.styleable.Rocket_circleColor, 0);
        } finally {
            a.recycle();
        }
        Adata = new AccelerometerData();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        circlePaint.setColor(circleCol);

        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, radius, circlePaint);

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

    public void moveRocket()
    {

        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int maxX = mdispSize.x - 50;
        int maxY = mdispSize.y - 50;

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this
                .getLayoutParams();
        int X = layoutParams.leftMargin;
        int Y = layoutParams.leftMargin;

        _xDelta = _xDelta + Adata.vx/50;
        _yDelta = _yDelta + Adata.vy/50;

        X = X - (int)_xDelta;
        Y = Y + (int)_yDelta;

        if(X > maxX)
            X = maxX;
        if(Y == maxY)
            Y = maxY;
        if(X < 50)
            X = 50;
        if(Y < 50)
            Y = 50;

        layoutParams.leftMargin = X;
        layoutParams.topMargin = Y;

        rocket.setLayoutParams(layoutParams);

        mRootLayout.invalidate();



    }

}
