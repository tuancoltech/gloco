package com.gloco.exercise.presentation.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.gloco.exercise.R;


/**
 * Created by "Tuan Nguyen" on 11/11/2016.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    private void showProgressDialog() {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.MyProgressDialogTheme);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            mProgressDialog.show();
        }
    }

    private void hideProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected void setLoadingDialog(boolean isActive) {
        if (isActive) {
            showProgressDialog();
        } else {
            hideProgressDialog();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
