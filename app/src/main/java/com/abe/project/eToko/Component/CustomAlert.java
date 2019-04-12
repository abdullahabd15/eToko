package com.abe.project.eToko.Component;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.abe.project.eToko.R;

public class CustomAlert {
    private Context context;

    public CustomAlert(Context context) {
        this.context = context;
    }

    public void showAlertToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showErrorDialog(Exception e) {
        try {
            final AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(context);
            errorDialogBuilder.setCancelable(false)
                    .setTitle(context.getString(R.string.title_error_occured))
                    .setMessage(e.getMessage())
                    .setPositiveButton(context.getString(R.string.btn_close), null)
                    .setNeutralButton(context.getString(R.string.btn_detail), (dialog, which) ->
                            showInfoDialog(context.getString(R.string.title_error_detail), StacktraceHandler.getStacktrace(e))
                    );

            errorDialogBuilder.create().show();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public void showInfoDialog(String title, String content) {
        try {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setCancelable(false)
                    .setTitle(title)
                    .setMessage(content)
                    .setPositiveButton(context.getString(R.string.btn_close), null);

            dialogBuilder.create().show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
