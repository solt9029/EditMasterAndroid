package com.solt9029.editmasterandroid.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import com.solt9029.editmasterandroid.constants.PositionConstants;
import com.solt9029.editmasterandroid.constants.SizeConstants;
import com.solt9029.editmasterandroid.util.CalcUtil;
import timber.log.Timber;

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

        int barNum = CalcUtil.calcBarNum(notes.size());
        Timber.d("barNum: " + barNum);
        for (int i = 0; i < barNum; i++) {
            float yPx = i * CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT, getContext()) - translateYPx;
            drawBar(yPx, canvas, paint);
        }

        holder.unlockCanvasAndPost(canvas);
    }

    public void drawBar(float yPx, Canvas canvas, Paint paint) {
        float leftPx = CalcUtil.convertDp2Px(PositionConstants.EDITOR_BAR_X, getContext());
        float topPx = yPx + CalcUtil.convertDp2Px(
                (float) (SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT - SizeConstants.EDITOR_BAR_INSIDE_HEIGHT) / 2,
                getContext());
        float rightPx = leftPx + CalcUtil.calcBarWidthPx(getWidth(), getContext());
        float bottomPx = topPx + CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_INSIDE_HEIGHT, getContext());
        canvas.drawRect(leftPx, topPx, rightPx, bottomPx, paint);
    }

}

