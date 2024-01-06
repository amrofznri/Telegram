package com.kuliah.telegram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AdapterData adapterData;
    List<AdapterData.DataModel> listdata;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recyclerview);
        listdata = new ArrayList<>();
        listdata.add(new AdapterData.DataModel("SISTEM INFORMASI 2022", "Fahmi SI B: Yang kelompok tele siapa aja?", R.drawable.si22logo));
        listdata.add(new AdapterData.DataModel("PRAKTIKUM ANDROID", "Kak Bahauddin : Final projek jangan lupa dikerjakan", R.drawable.profpic));
        listdata.add(new AdapterData.DataModel("Ghina SI A", "Kamu sudah ada di GOR?", R.drawable.profpic));
        listdata.add(new AdapterData.DataModel("Ulfa SI B", "Utiwiiiii", R.drawable.profpic));
        listdata.add(new AdapterData.DataModel("Tito SI B", "Ini kumpulnya dimana?", R.drawable.profpic));
        listdata.add(new AdapterData.DataModel("Ojan SI A", "Izin dulu ya, lagi gaenak badan", R.drawable.profpic));
        listdata.add(new AdapterData.DataModel("Fahmi SI B", "Pada dimana woy", R.drawable.profpic));
        listdata.add(new AdapterData.DataModel("Kak Bahauddin", "Bilangin yang mau asistensi saya tunggu di GOR", R.drawable.profpic));
        listdata.add(new AdapterData.DataModel("Echa SI A", "Siapa aja yg di GOR?", R.drawable.profpic));
        listdata.add(new AdapterData.DataModel("Kak Iqbal", "Yok yg mau asistensi", R.drawable.profpic));
        listdata.add(new AdapterData.DataModel("Kak Madyan", "Tunggu yaa", R.drawable.profpic));
        listdata.add(new AdapterData.DataModel("Tia SI B", "Ada colokan kosong ga?", R.drawable.profpic));
        listdata.add(new AdapterData.DataModel("Kak Sulis", "Ke Kak Baha nya dulu", R.drawable.profpic));

        linearLayoutManager = new LinearLayoutManager(this, linearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapterData = new AdapterData(this, listdata);
        recyclerView.setAdapter(adapterData);
        adapterData.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && e.getAction() == MotionEvent.ACTION_UP) {
                    int position = rv.getChildAdapterPosition(child);

                    AdapterData.DataModel clickedItem = listdata.get(position);

                    Intent ExplisitIntent = new Intent(Dashboard.this, ChatActivity.class);
                    ExplisitIntent.putExtra("title", clickedItem.getTitle());
                    ExplisitIntent.putExtra("message", clickedItem.getLastMessage());
                    ExplisitIntent.putExtra("image", clickedItem.getImageResource());
                    startActivity(ExplisitIntent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home){
                    showToast("Home");
                    Intent ExplicitIntent = new Intent(Dashboard.this, Profil.class);
                    startActivity(ExplicitIntent);
                }
                if (itemId == R.id.NewGroup) {
                    showToast("New Group Selected");
                } else if (itemId == R.id.contacs) {
                    showToast("Contacts Selected");
                    Intent ExplicitIntent = new Intent(Dashboard.this, Contact.class);
                    startActivity(ExplicitIntent);
                } else if (itemId == R.id.call) {
                    showToast("Call Selected");
                } else if (itemId == R.id.location) {
                    showToast("People Near By Selected");
                } else if (itemId == R.id.saved) {
                    showToast("Saved Messages Selected");
                } else if (itemId == R.id.setting) {
                    showToast("Setting Selected");
                    Intent ExplicitIntent = new Intent(Dashboard.this, Settings.class);
                    startActivity(ExplicitIntent);
                } else if (itemId == R.id.invite) {
                    showToast("Invite Friends Selected");
                } else if (itemId == R.id.Features) {
                    showToast("Telegram Featured Selected");
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(Dashboard.this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}