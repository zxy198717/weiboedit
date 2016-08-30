package com.example.alvinzeng.weiboedit.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;


public class NoEnterEditText extends EditText {
    public NoEnterEditText(Context context) {
        super(context);
    }

    public NoEnterEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoEnterEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_ENTER)
        {
            // Just ignore the [Enter] key
            return true;
        }
        // Handle all other keys in the default way
        return super.onKeyDown(keyCode, event);
    }
}
