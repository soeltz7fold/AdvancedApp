package x7a.droid.advancedapp;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public Toolbar toolbar;
    DatabaseHelper DB;
    EditText desc_exp, amount_exp, result_exp,
            desc_inc, amount_inc, result_inc;
    Button add_exp, add_inc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ADVANCED");
        toolbar.setNavigationIcon(R.drawable.ic_menu_camera);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
//        if (id == R.id.setelan) {
//            finish();
//        }
        if (id == R.id.action_wow){
            finish();
        }
        if(id == R.id.clear_expanses) {
            EditText desc_exp = (EditText) findViewById(R.id.desc_add_expenses);
            EditText amount_exp = (EditText) findViewById(R.id.amount_add_expenses);
            TextView result_exp = (TextView) findViewById(R.id.tv_new_expenses);
            desc_exp.setText("");
            amount_exp.setText("");
            result_exp.setText("EXPENSES Again?");
            Typeface supercell = Typeface.createFromAsset(getResources().getAssets(), "fonts/Supercell.ttf");
            result_exp.setTypeface(supercell);
        }
        if(id == R.id.clear_income) {
            EditText desc_inc = (EditText) findViewById(R.id.desc_new_income);
            EditText amount_inc = (EditText) findViewById(R.id.amount_new_income);
            TextView result_inc = (TextView) findViewById(R.id.tv_new_income);
            desc_inc.setText("");
            amount_inc.setText("");
            result_inc.setText("INCOME Again?");
            Typeface supercell = Typeface.createFromAsset(getResources().getAssets(), "fonts/Supercell.ttf");
            result_inc.setTypeface(supercell);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.dashboard) {
            Fragment FG = new DashboardPage();
            FragmentManager FM = getSupportFragmentManager();
            FragmentTransaction FT = FM.beginTransaction();
            Bundle bundle = new Bundle();
            FG.setArguments(bundle);
            FT.replace(R.id.fragment_place, FG);
            FT.commit();
            toolbar.setTitle("DASHBOARD");
            Toast.makeText(this, "Welcome Dashboard Page", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.transaction) {
            Fragment FG = new TransactionPage();
            FragmentManager FM = getSupportFragmentManager();
            FragmentTransaction FT = FM.beginTransaction();
            Bundle bundle = new Bundle();
            FG.setArguments(bundle);
            FT.replace(R.id.fragment_place, FG);
            FT.commit();
            Toast.makeText(this, "Welcome Transaction Page", Toast.LENGTH_SHORT).show();
            toolbar.setTitle("TRANSACTION");

        }

        else if (id == R.id.sync) {
            Fragment FG = new SyncPage();
            FragmentManager FM = getSupportFragmentManager();
            FragmentTransaction FT = FM.beginTransaction();
            Bundle bundle = new Bundle();
            FG.setArguments(bundle);
            FT.replace(R.id.fragment_place, FG);
            FT.commit();
            toolbar.setTitle("SYNCHRONIZE");
            Toast.makeText(this, "Welcome Synchronize Page", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.exit) {
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
