package com.noam.noamelectricity.Shapse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class band extends View {
    private Rect rect;
    private Paint paint;
    private static final int currentHeight = 250,
            currentWidth = 25;

    public band(Context context) {
        super(context);
        init(null);
    }

    public band(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public band(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public band(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        rect = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#EFEFEF"));
        // currentHeight = 250;
        // currentWidth = 25;
    }

    public void setColour(int colour) {
        paint.setColor(colour);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect.left = 0;
        rect.top = 0;
        rect.right = rect.left + currentWidth;
        rect.bottom = rect.top + currentHeight;

        canvas.drawRect(rect, paint);
    }
}