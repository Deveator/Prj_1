package com.example.prj_1.CuView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.prj_1.R;

public class OneBall extends View {

    Point point_1;
    Point startMovePoint;
    Canvas canvas;
    Paint paint;
    public ColorBall colorball;
    public static int x_coor;
    public static int y_coor;


    public OneBall(Context context) {
        super(context);
        init(context);
    }

    public OneBall(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public OneBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        paint = new Paint();
        setFocusable(true); // necessary for getting the touch events
        canvas = new Canvas();
        // setting the start point for the balls
        point_1 = new Point();
        point_1.x = 50;
        point_1.y = 20;

        colorball = new ColorBall(context, R.drawable.gray_circle, point_1);
    }

    // the method that draws the ball
    @Override
    protected void onDraw(Canvas canvas) {
        //   canvas.drawColor(0xffff00ff); //if you want another background color

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.parseColor("#55000000"));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        // mPaint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(5);

        canvas.drawPaint(paint);
        paint.setColor(Color.parseColor("#55FFFFFF"));

        BitmapDrawable mBitmap;
        mBitmap = new BitmapDrawable();

        canvas.drawBitmap(colorball.getBitmap(), colorball.getX(), colorball.getY(),
                new Paint());
    }

    // events when touching the screen
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        // you may need the x/y location
        int X = (int) event.getX();
        int Y = (int) event.getY();

        // put your code in here to handle the event
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;

            // help to move colorball
            case MotionEvent.ACTION_MOVE:
                colorball.setX((int) event.getX());
                colorball.setY((int) event.getY());
                x_coor = colorball.getX();
                y_coor = colorball.getY();
                invalidate();
                break;


        }

        // tell the View to redraw the Canvas
        invalidate();

        // tell the View that we handled the event
        return true;
    }
}
