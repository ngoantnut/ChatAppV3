package com.example.chatappfb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private TabLayout mytabLayout;
    private BottomNavigationView mybottomNavigationView;
    ChatsFragment chatsFragment = new ChatsFragment();
    GroupsFragment groupsFragment = new GroupsFragment();
    ContactsFragment contactsFragment= new ContactsFragment();
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //check account
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mToolbar= (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        mybottomNavigationView= findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,chatsFragment).commit();
        mybottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_tabs_1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,chatsFragment).commit();
                        return  true;
                    case R.id.menu_tabs_2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,groupsFragment).commit();
                        return  true;
                    case R.id.menu_tabs_3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,contactsFragment).commit();
                        return  true;
                }
                return false;
            }
        });




}

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser==null){
            SendUserToLoginActivity();
        }
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginFActivity.class);
        startActivity(loginIntent);
    }

//Tạo sub-menu để thêm lựa chọn
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.sub_menu_1, menu);
        return true;
    }
//Đặt vị trí sub-menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.main_logout_option){
            mAuth.signOut();
            Intent loginIntent1 = new Intent(MainActivity.this, LoginFActivity.class);
            startActivity(loginIntent1);
        }
         if(item.getItemId()==R.id.main_find_friends_option){

         }
        if(item.getItemId()==R.id.main_settings_option){

        }

        return true;
    }
}
