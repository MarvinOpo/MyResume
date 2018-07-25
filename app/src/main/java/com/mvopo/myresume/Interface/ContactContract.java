package com.mvopo.myresume.Interface;

import android.app.AlertDialog;
import android.content.Intent;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

public class ContactContract {

    public interface contactView{
        void startService();

        //Contact via SMS
        void requestPermission();
        boolean smsAllowed();
        boolean shouldShowPermission();
        void showPermissionDialogIntent();
        void showOverlayDialogIntent();
        boolean canDrawOverlays();
        void toastOverlyDenied();

        //Contact via email
        void showMailDialog();
        boolean isConnectedToInternet();
        void toastNoConnection();
        void dismissMailDialog();

        BackgroundMail.Builder getMailBuilder(String subject, String body);
    }

    public interface contactAction{
        void checkPermission();
        void setSendListener(AlertDialog dialog);
        void checkDrawOverlayPermission(boolean shouldShowDialog);
    }
}
