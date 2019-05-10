package com.solt9029.editmasterandroid.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.solt9029.editmasterandroid.util.CalcUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import timber.log.Timber;

public class EditorBarsView extends BaseSurfaceView {
    public EditorBarsView(Context context) {
        super(context);
    }

    public EditorBarsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditorBarsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void draw(int translateY, List<Integer> notes) {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }

        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setStyle(Style.FILL);

        int barNum = notes.size() / 96;
        for (int i = 0; i < barNum; i++) {
            canvas.drawCircle(getWidth() / 2, CalcUtil.convertDp2Px(i * 100 + 50, getContext()) - translateY, CalcUtil.convertDp2Px(20, getContext()), paint);
        }

        holder.unlockCanvasAndPost(canvas);
    }
}

