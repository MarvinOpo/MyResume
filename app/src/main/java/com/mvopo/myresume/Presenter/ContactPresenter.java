package com.mvopo.myresume.Presenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;

import com.mvopo.myresume.Interface.ContactContract;
import com.mvopo.myresume.R;

public class ContactPresenter implements ContactContract.contactAction {

    ContactContract.contactView contactView;

    public ContactPresenter(ContactContract.contactView contactView) {
        this.contactView = contactView;
    }

    @Override
    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!contactView.smsAllowed()) {
                if (contactView.shouldShowPermission()) {
                    contactView.showPermissionDialogIntent();
                } else {
                    contactView.requestPermission();
                }

                return;
            }

            checkDrawOverlayPermission(true);
            return;
        }

        contactView.startService();
    }

    @Override
    public void setSendListener(final AlertDialog dialog) {
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtxCompany = dialog.findViewById(R.id.mail_company);
                EditText edtxContact = dialog.findViewById(R.id.mail_contact);
                EditText edtxSubject = dialog.findViewById(R.id.mail_subject);
                EditText edtxBody = dialog.findViewById(R.id.mail_body);

                String company = edtxCompany.getText().toString().trim();
                String contact = edtxContact.getText().toString().trim();
                String subject = edtxSubject.getText().toString().trim();
                String body = edtxBody.getText().toString().trim();

                if (company.isEmpty()) {
                    edtxCompany.setError("Required.");
                    edtxCompany.requestFocus();
                } else if (contact.isEmpty()) {
                    edtxContact.setError("Required.");
                    edtxContact.requestFocus();
                } else if (subject.isEmpty()) {
                    edtxSubject.setError("Required.");
                    edtxSubject.requestFocus();
                } else if (body.isEmpty()) {
                    edtxBody.setError("Required.");
                    edtxBody.requestFocus();
                } else {
                    if (contactView.isConnectedToInternet()) {
                        contactView.getMailBuilder(subject, company + "\n" +
                                contact + "\n\n" + body).send();
                    } else {
                        contactView.toastNoConnection();
                        contactView.dismissMailDialog();
                    }

                }
            }
        });
    }

    @Override
    public void checkDrawOverlayPermission(boolean shouldShowDialog) {
        if (!contactView.canDrawOverlays()) {
            if(shouldShowDialog) contactView.showOverlayDialogIntent();
            else contactView.toastOverlyDenied();
        }else{
            contactView.startService();
        }
    }
}
