package com.example.alvinzeng.weiboedit.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.alvinzeng.weiboedit.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Years.im on 16/6/25.
 */
@EViewGroup(R.layout.view_article_image_item)
public class ArticleImageItemView extends LinearLayout {
    @ViewById
    public ImageView imageView;


    public ArticleImageItemView(Context context) {
        super(context);
    }

    public ArticleImageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArticleImageItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
