package com.solt9029.editmasterandroid.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.solt9029.editmasterandroid.util.CalcUtil;

public class EditorBarsView extends BaseSurfaceView implements SurfaceHolder.Callback {
    public EditorBarsView(Context context) {
        super(context);
    }

    public EditorBarsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditorBarsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void draw(int translateY) {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }

        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setStyle(Style.FILL);

        int count = 0;
        for (int y = -translateY; y < CalcUtil.convertDp2Px(2000, getContext()) - translateY; y += CalcUtil.convertDp2Px(80, getContext())) {
            canvas.drawCircle(width / 2, y, CalcUtil.convertDp2Px(10, getContext()) + count, paint);
            count++;
        }

        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
        draw(0);
    }
}

