package com.example.alvinzeng.weiboedit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alvinzeng.weiboedit.widget.ArticleImageItemView;
import com.example.alvinzeng.weiboedit.widget.ArticleImageItemView_;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import im.years.imagepicker.ImagePickerManager;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    ArrayList<View> subViews = new ArrayList<>();

    @ViewById
    EditText titleEditText;
    @ViewById
    EditText contentEditText;
    @ViewById
    LinearLayout contentLayout;
    @ViewById
    TextView imageTextView;

    ImagePickerManager imagePickerManager;

    @AfterViews
    protected void setupViews() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setupContentEditText(contentEditText);
        subViews.add(contentEditText);

        imagePickerManager = new ImagePickerManager(this);
    }


    @Click
    void imageTextView() {
        imagePickerManager.pickImage(false, new ImagePickerManager.ImagePickerListener() {
            @Override
            public void onImageChosen(ChosenImage chosenImage) {
                appendImageView(chosenImage.getOriginalPath());
            }

            @Override
            public void onError(String s) {
                Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();

            }
        });
    }

    void appendImageView(String file) {
        ArticleImageItemView articleImageItemView = ArticleImageItemView_.build(this);
        ImageView imageView = articleImageItemView.imageView;
        Glide.with(this).load(file).into(imageView);
        appendViewAfterCurrentFocus(articleImageItemView);
    }


    void appendViewAfterCurrentFocus(View view) {
        View currentFocus = getWindow().getCurrentFocus();
        if (currentFocus == null) {
            return;
        }
        int index = subViews.indexOf(currentFocus);
        if (index == -1) {
            index = subViews.size() - 1;
        }
        subViews.add(index + 1, view);
        contentLayout.addView(view, index + 1);

        if (!(view instanceof EditText)) {
            if (subViews.get(subViews.size() - 1) == view) {
                appendEditTextEnd();
            }
        } else {
            ((EditText) view).requestFocus();
        }
    }

    void appendEditTextEnd() {
        EditText editText = createNewEditText();
        subViews.add(editText);
        contentLayout.addView(editText);
    }

    void appendEditTextAfter(EditText currentEditText) {
        EditText editText = createNewEditText();
        appendViewAfterCurrentFocus(editText);
    }

    void removeViewBefore(EditText currentEditText) {
        int index = subViews.indexOf(currentEditText);
        if (index == 0) {
            return;
        }

        View view = subViews.get(index - 1);
        if (view instanceof EditText) {
            subViews.remove(currentEditText);
            contentLayout.removeView(currentEditText);
            ((EditText) view).requestFocus();
        } else {
            subViews.remove(view);
            contentLayout.removeView(view);
        }
    }

    EditText createNewEditText() {
        EditText editText = (EditText) LayoutInflater.from(this).inflate(R.layout.view_article_add_edittext, null);
        setupContentEditText(editText);
        return editText;
    }

    void setupContentEditText(final EditText editText) {

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN && editText.getText().toString().isEmpty()) {
                    removeViewBefore(editText);
                    return true;
                }
                return false;
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean result = (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
                if (result) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        appendEditTextAfter(editText);
                    }
                }

                return result;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePickerManager.onActivityResult(requestCode, resultCode, data);
    }
}
