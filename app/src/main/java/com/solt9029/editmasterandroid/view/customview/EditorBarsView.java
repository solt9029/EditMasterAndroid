package com.solt9029.editmasterandroid.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.solt9029.editmasterandroid.util.CalcUtil;

import java.util.List;

public class EditorBarsView extends BaseSurfaceView implements SurfaceHolder.Callback {
    private int translateYPx;
    private List<Integer> notes;

    public EditorBarsView(Context context) {
        super(context);
    }

    public EditorBarsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditorBarsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setTranslateYPx(int translateYPx) {
        this.translateYPx = translateYPx;
        draw();
    }

    public void setNotes(List<Integer> notes) {
        this.notes = notes;
        draw();
    }

    public void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null || notes == null) {
            return;
        }

        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setStyle(Style.FILL);

        int barNum = notes.size() / 96;
        for (int i = 0; i < barNum; i++) {
            canvas.drawCircle((int) (getWidth() / 2.0), CalcUtil.convertDp2Px(i * 100 + 50, getContext()) - translateYPx, CalcUtil.convertDp2Px(20, getContext()), paint);
        }

        holder.unlockCanvasAndPost(canvas);
    }
}

