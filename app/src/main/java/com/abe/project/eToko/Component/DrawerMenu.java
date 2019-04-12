package com.abe.project.eToko.Component;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abe.project.eToko.Data.UserData;
import com.abe.project.eToko.R;

public class DrawerMenu {
    private final Activity activity;
    private final Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView ivProfilePic;
    private TextView tvUsername;

    private ILeftMenu listener;

    public DrawerMenu(Activity activity, Toolbar toolbar) {
        this.activity = activity;
        this.toolbar = toolbar;

        initComponent();
    }

    private void initComponent() {
        drawerLayout = activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                try {
                    if (listener == null)
                        return false;

                    boolean result = listener.onMenuItemSelected(menuItem.getItemId());
                    drawerLayout.closeDrawer(GravityCompat.START);

                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });

        View view = navigationView.getHeaderView(0);
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        tvUsername = view.findViewById(R.id.tvUsername);
    }

    public void setMenuResourse(int resourse) {
        navigationView.getMenu().clear();
        navigationView.inflateMenu(resourse);
    }

    public void setProfileInfo(UserData userData) {
        if (userData != null) {
            tvUsername.setText(userData.userName);
        }
    }

    public void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (listener != null)
                listener.onHandleBackPressed();
        }
    }

    public void setLeftMenuListener(ILeftMenu listener) {
        this.listener = listener;
    }

    public interface ILeftMenu {
        boolean onMenuItemSelected(int itemId) throws Exception;
        void onHandleBackPressed();
    }
}
