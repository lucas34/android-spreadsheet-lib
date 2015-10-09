/*
 * Copyright 2015-present Lucas Nelaupe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fr.nelaupe.spreadsheetlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

import java.util.Locale;

import fr.nelaupe.spreadsheetlib.R;

/**
 * Created with IntelliJ
 * Created by Lucas Nelaupe
 * Date 26/03/15
 */
public class ArrowButton extends Button {

    private static final int[] state_up = new int[]{R.attr.state_up};
    private static final int[] state_down = new int[]{R.attr.state_down};
    private static final int[] state_none = new int[]{R.attr.state_none};
    private final Rect mBounds;
    private int drawableWidth;
    private int iconPadding;
    private int textGravity;
    private states currentState;

    public ArrowButton(Context context) {
        super(context);
        mBounds = new Rect();
    }

    public ArrowButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBounds = new Rect();
        applyAttributes(attrs);
    }

    public ArrowButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mBounds = new Rect();
        applyAttributes(attrs);
    }

    private void applyAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IconButton);
        int paddingId = typedArray.getDimensionPixelSize(R.styleable.IconButton_iconPadding, 0);
        setIconPadding(paddingId);
        typedArray.recycle();
    }

    private void setIconPadding(int padding) {
        iconPadding = padding;
        requestLayout();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] original = super.onCreateDrawableState(extraSpace + 1);

        if (currentState == null) {
            return original;
        }

        switch (currentState) {

            case UP:
                return mergeDrawableStates(original, state_up);
            case DONW:
                return mergeDrawableStates(original, state_down);
            case NONE:
                return mergeDrawableStates(original, state_none);
        }

        return original;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        setGravity(textGravity);

        Paint textPaint = getPaint();
        textPaint.setTextSize(getTextSize());
        String text = getText().toString().toUpperCase(Locale.getDefault());
        textPaint.getTextBounds(text, 0, text.length(), mBounds);

        int textWidth = (int) textPaint.measureText(getText().toString());

        int compute = getWidth() - drawableWidth - iconPadding - textWidth;
        int compoundDrawablePadding = -compute + iconPadding;

        if (((textGravity & Gravity.LEFT) == Gravity.LEFT) || ((textGravity & Gravity.START) == Gravity.START)) {
            setCompoundDrawablePadding(compoundDrawablePadding - getPaddingLeft());
            setPadding(getPaddingLeft(), getPaddingTop(), compute - getPaddingLeft(), getPaddingBottom());
        } else if (((textGravity & Gravity.RIGHT) == Gravity.RIGHT) || ((textGravity & Gravity.END) == Gravity.END)) {
            setCompoundDrawablePadding(compoundDrawablePadding + getPaddingRight());
            setPadding(compute + getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        } else {
            int contentLeft = (int) ((getWidth() / 2.0) - drawableWidth - iconPadding - textWidth / 2);
            setCompoundDrawablePadding(-contentLeft + iconPadding);
            setPadding(contentLeft, getPaddingTop(), contentLeft, getPaddingBottom());
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);

        if (left != null) {
            drawableWidth = left.getIntrinsicWidth();
        } else if (right != null) {
            drawableWidth = right.getIntrinsicWidth();
        }

        requestLayout();
    }

    public void setState(states state) {
        currentState = state;
        refreshDrawableState();
    }

    public void setTextGravity(int gravity) {
        textGravity = gravity;
    }

    public enum states {
        UP,
        DONW,
        NONE
    }

}
