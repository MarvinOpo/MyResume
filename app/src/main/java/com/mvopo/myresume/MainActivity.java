package com.mvopo.myresume;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowInsets;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mvopo.myresume.Fragment.AboutFragment;
import com.mvopo.myresume.Fragment.ContactFragment;
import com.mvopo.myresume.Fragment.ExperienceFragment;
import com.mvopo.myresume.Fragment.ProjectsFragment;
import com.mvopo.myresume.Fragment.ReferenceFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout drawer;
    private FragmentManager fm;
    private FragmentTransaction ft;

    private NavigationView navigationView;
    private ScrollView scrollView;

    private AboutFragment af = new AboutFragment();
    private ExperienceFragment ef = new ExperienceFragment();
    private ProjectsFragment pf = new ProjectsFragment();
    private ReferenceFragment rf = new ReferenceFragment();
    private ContactFragment cf = new ContactFragment();

    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.TRANSPARENT);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        scrollView = findViewById(R.id.container_scrollview);

        navigationView.setNavigationItemSelectedListener(this);
        removeNavGrayBar();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, af).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit app?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.super.onBackPressed();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_credit) {
            showSettingDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.civ_link:
                openLink("https://github.com/hdodenhof/CircleImageView");
                break;
            case R.id.bgmail_link:
                openLink("https://github.com/yesidlazaro/GmailBackground");
                break;
            case R.id.icon8_link:
                openLink("https://icons8.com/");
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        scrollView.fullScroll(scrollView.getTop());
        ft = fm.beginTransaction();

        if (id == R.id.nav_about) {
            ft.replace(R.id.fragment_container, af);
        } else if (id == R.id.nav_experience) {
            ft.replace(R.id.fragment_container, ef);
        } else if (id == R.id.nav_projects) {
            ft.replace(R.id.fragment_container, pf);
        } else if (id == R.id.nav_character_reference) {
            ft.replace(R.id.fragment_container, rf);
        } else if (id == R.id.nav_contact) {
            ft.replace(R.id.fragment_container, cf);
        }

        ft.commit();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawer.closeDrawer(GravityCompat.START);
            }
        }, 50);

        return true;
    }

    public void removeNavGrayBar() {
        if (navigationView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            navigationView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    return insets;
                }
            });
        }
    }

    public void showSettingDialog() {
        View settingDialog = getLayoutInflater().inflate(R.layout.setting_dialog, null);

        TextView civLink = settingDialog.findViewById(R.id.civ_link);
        TextView bgmailLink = settingDialog.findViewById(R.id.bgmail_link);
        TextView icons8Link = settingDialog.findViewById(R.id.icon8_link);

        civLink.setOnClickListener(this);
        bgmailLink.setOnClickListener(this);
        icons8Link.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(settingDialog);
        builder.show();
    }

    public void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
