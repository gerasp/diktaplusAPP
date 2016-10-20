package singularfactory.app.views.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import singularfactory.app.AppCommon;
import singularfactory.app.R;
import singularfactory.app.models.Model;
import singularfactory.app.views.activities.initializations.InitMainActivity;

public class MainActivity extends BaseActivity implements InitMainActivity.InitMainActivityListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public InitMainActivity itemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (AppCommon.getInstance().getModel() == null) {
            AppCommon.getInstance().setModel(Model.getInstance());
            //Detect if model is null. App lost resources... you may need to reinitialize something else...
        }

        initialize(findViewById(android.R.id.content));
        initializeActionBarAndToggle();
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

    private void initialize(View view) {

        itemView = new InitMainActivity(this);
        itemView.initialize(view);
        itemView.initializeActions();
        itemView.initializeCustomFonts();
        itemView.setInitMainActivityListener(this);
    }

    private void initializeActionBarAndToggle() {
        //ActionBar
        setSupportActionBar(itemView.toolbar);

        //Drawer Toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, itemView.drawer, itemView.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        itemView.drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Navigation
        itemView.navigationView.setNavigationItemSelectedListener(this);
    }

    /** Settings **/
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    /****/

    /** NavigationView.OnNavigationItemSelectedListener **/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        itemView.drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /****/


}
