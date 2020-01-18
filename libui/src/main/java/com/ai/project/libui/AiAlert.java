package com.ai.project.libui;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.widget.Toast;

public class AiAlert {
    private Context context;

    public AiAlert(Context context) {
        this.context = context;
    }

    public void showAlertToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showAskDialog(String message, DialogInterface.OnClickListener okListener) {
        try {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setCancelable(false)
                    .setNegativeButton(context.getString(R.string.btn_cancel), null)
                    .setPositiveButton(context.getString(R.string.btn_delete), okListener)
                    .setTitle(context.getString(R.string.app_name))
                    .setMessage(message);
            alertDialog.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showErrorDialog(Exception e) {
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

    void showInfoDialog(String title, String content) {
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
