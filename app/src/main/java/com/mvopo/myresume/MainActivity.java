package com.mvopo.myresume;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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

import com.mvopo.myresume.Fragment.AboutFragment;
import com.mvopo.myresume.Fragment.ContactFragment;
import com.mvopo.myresume.Fragment.ExperienceFragment;
import com.mvopo.myresume.Fragment.ProjectsFragment;
import com.mvopo.myresume.Fragment.ReferenceFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fm;
    private FragmentTransaction ft;

    private NavigationView navigationView;
    private ScrollView scrollView;

    private AboutFragment af = new AboutFragment();
    private ExperienceFragment ef = new ExperienceFragment();
    private ProjectsFragment pf = new ProjectsFragment();
    private ReferenceFragment rf = new ReferenceFragment();
    private ContactFragment cf = new ContactFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.TRANSPARENT);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            super.onBackPressed();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        scrollView.fullScroll(scrollView.getTop());
        ft = fm.beginTransaction();

        if (id == R.id.nav_about) {
            ft.replace(R.id.fragment_container, af).commit();
        } else if (id == R.id.nav_experience) {
            ft.replace(R.id.fragment_container, ef).commit();
        } else if (id == R.id.nav_projects) {
            ft.replace(R.id.fragment_container, pf).commit();
        } else if (id == R.id.nav_character_reference) {
            ft.replace(R.id.fragment_container, rf).commit();
        } else if (id == R.id.nav_contact) {
            ft.replace(R.id.fragment_container, cf).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void removeNavGrayBar(){
        if (navigationView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            navigationView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    return insets;
                }
            });
        }
    }
}
