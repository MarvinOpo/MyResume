package com.mvopo.myresume.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.mvopo.myresume.FloatingSmsService;
import com.mvopo.myresume.Helper.ConnectionChecker;
import com.mvopo.myresume.Helper.ListAdapter;
import com.mvopo.myresume.Interface.ContactContract;
import com.mvopo.myresume.Interface.ServiceContract;
import com.mvopo.myresume.Model.Message;
import com.mvopo.myresume.Presenter.ContactPresenter;
import com.mvopo.myresume.R;

import java.util.ArrayList;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by mvopo on 7/16/2018.
 */

public class ContactFragment extends Fragment implements View.OnClickListener, ContactContract.contactView,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private final int sms_permission_code = 100;
    private final int overlay_permission_code = 200;
    private Button btnSms, btnMail;

    private ContactPresenter mPresenter;

    private ListView lvThreads;
    private ArrayList<Message> messageThreads = new ArrayList<>();
    private ListAdapter adapter;

    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        mPresenter = new ContactPresenter(this);

        btnSms = view.findViewById(R.id.btn_sms);
        btnMail = view.findViewById(R.id.btn_mail);

        btnSms.setOnClickListener(this);
        btnMail.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_sms:
                String number = "09218155172";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
                break;
            case R.id.btn_mail:
                showMailDialog();
                break;
        }
    }

    @Override
    public void startService() {
        Intent foregroundIntent = new Intent(ServiceContract.ACTION_FOREGROUND);
        foregroundIntent.putExtra("phoneNum", "09218155172");
        foregroundIntent.setClass(getContext(), FloatingSmsService.class);

        getActivity().startService(foregroundIntent);
    }

    @Override
    public boolean smsAllowed() {
        return checkSelfPermission(getContext(), Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean shouldShowPermission() {
        return shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS);
    }

    @Override
    public void showPermissionDialogIntent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please allow permission required for this app to use MVOpo Floating SMS." +
                "\n\nSMS - Read and Write SMS for contacting Mark Vincent Opo.");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Use Default SMS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String number = "09218155172";
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.fromParts("sms", number, null));
                startActivity(intent);
            }
        });
        builder.show();
    }

    @Override
    public void showOverlayDialogIntent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please allow drawing overlay for this app." +
                "\n\nOverlay - Opens a customized messaging panel that floats over apps. " +
                "This feature is limited to messaging Mark Vincent B. Opo.");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getActivity().getPackageName()));
                startActivityForResult(intent, overlay_permission_code);
            }
        });
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean canDrawOverlays() {
        return Settings.canDrawOverlays(getContext());
    }

    @Override
    public void toastOverlyDenied() {
        Toast.makeText(getContext(), "Overly denied, Cant contact via SMS", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_STATE}, sms_permission_code);
    }

    @Override
    public boolean isConnectedToInternet() {
        return new ConnectionChecker(getContext()).isConnectedToInternet();
    }

    @Override
    public void toastNoConnection() {
        Toast.makeText(getContext(), "No internet conenction", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissMailDialog() {
        dialog.dismiss();
    }

    @Override
    public BackgroundMail.Builder getMailBuilder(String subject, String body) {
        return BackgroundMail.newBuilder(getContext())
                .withUsername("mvopo.resume@gmail.com")
                .withPassword("mvoporesume123")
                .withMailto("markvincentopo@gmail.com")
                .withSubject(subject)
                .withBody(body)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        dismissMailDialog();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case overlay_permission_code:
                mPresenter.checkDrawOverlayPermission(false);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case sms_permission_code: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.checkDrawOverlayPermission(true);
                } else {
                    showPermissionDialogIntent();
                }
            }
        }
    }

    @Override
    public void showMailDialog() {
        final View mailDialog = LayoutInflater.from(getContext()).inflate(R.layout.mail_dialog, null);

        AlertDialog.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            builder = new AlertDialog.Builder(getContext(), R.style.DialogStyle);
        else
            builder = new AlertDialog.Builder(getContext());

        builder.setView(mailDialog);
        builder.setPositiveButton("Send", null);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();

        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();

        mPresenter.setSendListener(dialog);
    }
}
