package com.solt9029.editmasterandroid.view.customview;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import androidx.annotation.Nullable;
import timber.log.Timber;

public class ScrollContainerView extends ScrollView {
    @Nullable
    private OnScrollChangeListener listener;

    ScrollContainerView(Context context) {
        super(context);
    }

    ScrollContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    ScrollContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (listener != null) {
            listener.onScrollChange(x, y, oldX, oldY);
        }
        Timber.d("ScrollX: " + x + " px");
        Timber.d("ScrollY: " + y + " px");
    }

    public interface OnScrollChangeListener {
        void onScrollChange(int x, int y, int oldX, int oldY);
    }
}