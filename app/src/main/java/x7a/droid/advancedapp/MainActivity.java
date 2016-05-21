package x7a.droid.advancedapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import x7a.droid.advancedapp.fragment.ChartPage;
import x7a.droid.advancedapp.fragment.DashboardPage;
import x7a.droid.advancedapp.fragment.SyncPage;
import x7a.droid.advancedapp.fragment.TransactionPage;
import x7a.droid.advancedapp.trial.FabPage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public Toolbar toolbar;
    DatabaseHelper DB;
    SharedPreferences get_shared_preference;
    SharedPreferences.Editor sp_editor;
    EditText desc_exp, amount_exp, desc_inc, amount_inc;
    TextView tv_result_exp, tv_result_inc, nav_email, nav_token;
    String emailUser, tokenUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ADVANCED");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_camera);

        //Get Cache
        get_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        View header = navView.getHeaderView(0);//initialize
        nav_token = (TextView) header.findViewById(R.id.token_id);
        nav_email = (TextView) header.findViewById(R.id.nav_user);
        nav_email.setText("Hi, " +get_shared_preference.getString("email", ""));
        nav_token.setText("ID = " +get_shared_preference.getString("token_authentication", ""));
        nav_email.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


//        Toast.makeText(MainActivity.this, "Welcome : "+get_shared_preference.getString("email",""), Toast.LENGTH_SHORT).show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
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
        desc_exp = (EditText) findViewById(R.id.desc_add_expenses);
        amount_exp = (EditText) findViewById(R.id.amount_add_expenses);
        desc_inc = (EditText) findViewById(R.id.desc_new_income);
        amount_inc = (EditText) findViewById(R.id.amount_new_income);
        tv_result_exp = (TextView) findViewById(R.id.tv_result_expenses);
        tv_result_inc = (TextView) findViewById(R.id.tv_result_income);
        Typeface supercell = Typeface.createFromAsset(getResources().getAssets(), "fonts/Supercell.ttf");

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_wow){
            get_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
            sp_editor = get_shared_preference.edit();
            sp_editor.putString("email", "");
            sp_editor.putString("token_authentication", "");
            sp_editor.commit();
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        if(id == R.id.clear_expanses) {
            desc_exp.setText("");
            amount_exp.setText("");
            tv_result_exp.setText("EXPENSES Again?");
            tv_result_exp.setTypeface(supercell);
        }
        if(id == R.id.clear_income) {
            desc_inc.setText("");
            amount_inc.setText("");
            tv_result_inc.setText("INCOME Again?");
            tv_result_inc.setTypeface(supercell);
        }
        if(id == R.id.cancel_sync){
            Toast.makeText(getApplicationContext(), "Cancel Sync?", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id. skip_sync){
            Toast.makeText(getApplicationContext(), "Skip Sync?", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment FG = null;
        FragmentManager FM;
        FragmentTransaction FT;
        Class FragmentChange = null;
        Intent intent;
        if (id == R.id.dashboard) {
            FragmentChange = DashboardPage.class;
            toolbar.setTitle("DASHBOARD");
//            navV.inflateHeaderView(R.layout.nav_header_main);
            Toast.makeText(this, "Welcome Dashboard Page", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.transaction) {
            FragmentChange = TransactionPage.class;
            Toast.makeText(this, "Welcome Transaction Page", Toast.LENGTH_SHORT).show();
            toolbar.setTitle("TRANSACTION");
        }
        else if (id == R.id.sync) {
            FragmentChange = SyncPage.class;
            toolbar.setTitle("SYNCHRONIZE");
        }
        else if(id == R.id.fab){
            FragmentChange = FabPage.class;
            toolbar.setTitle("Floating");
        }
        else if (id == R.id.chart){
            FragmentChange = ChartPage.class;
            toolbar.setTitle("Chart");
        }
        else if (id == R.id.maps) {
            FragmentChange = MapsPage.class;
            toolbar.setTitle("Maps");

//            Intent i = new Intent(this,MapsPage.class);
//            startActivity(i);
        }
        else if (id == R.id.exit){
            get_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
            sp_editor = get_shared_preference.edit();
            sp_editor.putString("email", "");
            sp_editor.putString("token_authentication", "");
            sp_editor.commit();

            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

//            Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show();
//            Intent i = new Intent(MainActivity.this,LoginActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(i);
////            System.gc();
//            finish();
        }
        else FragmentChange = null;
            try{
                FG = (Fragment)FragmentChange.newInstance();
                }catch (InstantiationException e){
                    e.printStackTrace();
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }
        FM = getSupportFragmentManager();
        FT = FM.beginTransaction();
        FT.replace(R.id.fragment_place, FG);
        FT.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
        }
    }
