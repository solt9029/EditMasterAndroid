package com.solt9029.editmasterandroid.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

public class PlayerNotesView extends BaseSurfaceView {
    public PlayerNotesView(Context context) {
        super(context);
    }

    public PlayerNotesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerNotesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }

        canvas.drawColor(Color.RED);

        holder.unlockCanvasAndPost(canvas);
    }
}

