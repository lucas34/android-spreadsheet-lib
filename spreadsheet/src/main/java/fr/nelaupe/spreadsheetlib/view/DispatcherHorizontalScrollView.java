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
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created with IntelliJ
 * Created by Lucas Nelaupe
 * Date 26/03/15
 */
public class DispatcherHorizontalScrollView extends HorizontalScrollView {

    private View target;

    public DispatcherHorizontalScrollView(Context context) {
        super(context);
    }

    public DispatcherHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatcherHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTarget(View h) {
        this.target = h;
        target.setDrawingCacheEnabled(true);
        setDrawingCacheEnabled(true);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (target != null) {
            target.scrollTo(l, t);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

}
