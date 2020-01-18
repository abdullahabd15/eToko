package com.ai.project.libui;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

public abstract class AiActivity extends AppCompatActivity {
    private AiAlert alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alert = new AiAlert(this);
    }

    public void showAlertToast(String message) {
        alert.showAlertToast(message);
    }

    public void showAskDialog(String message, DialogInterface.OnClickListener okListener) {
        alert.showAskDialog(message, okListener);
    }

    public void showErrorDialog(Exception e) {
        alert.showErrorDialog(e);
    }

    public void showInfoDialog(String title, String content) {
        alert.showInfoDialog(title, content);
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
