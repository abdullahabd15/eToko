package com.ai.project.eToko.component;

import android.app.Activity;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.project.eToko.data.UserData;
import com.ai.project.eToko.R;

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
