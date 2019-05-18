package com.solt9029.editmasterandroid.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import com.solt9029.editmasterandroid.constants.SizeConstants;
import com.solt9029.editmasterandroid.util.CalcUtil;

public class PlayerNotesView extends BaseSurfaceView {
    private float currentTime = 0;

    public PlayerNotesView(Context context) {
        super(context);
    }

    public PlayerNotesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerNotesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
        draw();
    }

    public void draw() {
        Canvas canvas = getHolder().lockCanvas();
        if (canvas == null) {
            return;
        }

        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        float radiusPx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_NORMAL_OUTSIDE, getContext());
        canvas.drawCircle(currentTime * 10, (int) (getHeight() / 2.0), radiusPx, paint);

        getHolder().unlockCanvasAndPost(canvas);
    }
}

