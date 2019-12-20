package com.barrault.projet;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

public class Credits extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        Toolbar toolbar = findViewById(R.id.main_menu_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='#ffffff'>HelloSigns </font>"));
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.credit:
                                Intent intentCredit = new Intent(Credits.this, Credits.class);
                                startActivity(intentCredit);
                                return true;


                            case R.id.main_home:
                                Intent MainActivity = new Intent(Credits.this, MainActivity.class);
                                startActivity(MainActivity);
                                return true;

                            case R.id.exit:
                                new AlertDialog.Builder(Credits.this)
                                        .setCancelable(true)
                                        .setMessage("Etes vous sûr de vouloir quitter ?")
                                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }})
                                        .setPositiveButton("Quitter", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                    String CHANNEL_ID = "EsieaWorldCup";
                                                    NotificationChannel channel = new
                                                            NotificationChannel(CHANNEL_ID,
                                                            getString(R.string.channel_name),
                                                            NotificationManager.IMPORTANCE_HIGH);

                                                    //channel.setDescription(getString(R.string.channel_description));
                                                    NotificationManager notificationManager =
                                                            getSystemService(NotificationManager.class);

                                                    notificationManager.createNotificationChannel(channel);
                                                }
                                                PendingIntent pendingIntent = PendingIntent.getActivity(Credits.this, 0, new Intent(Credits.this, MainActivity.class), 0);
                                                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(Credits.this, "EsieaWorldCup")
                                                        .setSmallIcon(R.drawable.logo)
                                                        .setContentTitle("ESIEA WorldCup")
                                                        .setContentText("Vous venez de quitter l'application.")
                                                        .setContentIntent(pendingIntent)
                                                        .setAutoCancel(true);
                                                notificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Credits.this);
                                                notificationManager.notify(1, notificationBuilder.build());

                                                moveTaskToBack(true);
                                                Process.killProcess(Process.myPid());
                                                System.exit(1);
                                            }
                                        })
                                        .show();
                                return true;
                        }
                        return true;
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
//