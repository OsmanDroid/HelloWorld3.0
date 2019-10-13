package osmandroid.venturesity.helloworld3o;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    FloatingActionButton fab;

    FirebaseAuth mAuth;

    Button enroll;
    ImageButton chat;

    Location location;

    CardView tasksCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enroll = findViewById(R.id.enrollbtn);
        chat = findViewById(R.id.chatDoc);


        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)actionBar.setTitle("THERAPEUTIC");


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        tasksCardView = findViewById(R.id.tasksCV);

        mAuth = FirebaseAuth.getInstance();

        fab = findViewById(R.id.fabbtn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ChatActivity.class));
            }
        });

        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LiveDoctor.class));
            }
        });

        requestLocationUpdates();

        tasksCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TasksActivity.class));
            }
        });



    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        switch (menuItem.getItemId())
        {
            case R.id.liveConsult:
                startActivity(new Intent(MainActivity.this,LiveDoctor.class));
                break;
            case R.id.groupD:
                startActivity(new Intent(MainActivity.this,GroupDiscussion.class));
                break;
            case R.id.tasks:
                startActivity(new Intent(MainActivity.this,TasksActivity.class));
                break;
            case R.id.nav_hospitals:
                Uri hospitalURI;
                if(location!=null)
                hospitalURI = Uri.parse("https://www.google.co.in/maps/search/Hospital/@"+location.getLatitude()+","+location.getLongitude());
                else hospitalURI = Uri.parse("https://www.google.co.in/maps/search/Hospital/");

                Intent hospitalIntent = new Intent(Intent.ACTION_VIEW, hospitalURI);
                hospitalIntent.setPackage("com.google.android.apps.maps");
                if (hospitalIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(hospitalIntent);
                }else {
                    Toast.makeText(this,"No App to show Location",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_medicals:
                Uri medicalURI;
                if(location!=null)
                medicalURI= Uri.parse("https://www.google.co.in/maps/search/Medical/@"+location.getLatitude()+","+location.getLongitude());
                else medicalURI= Uri.parse("https://www.google.co.in/maps/search/Medical/");

                Intent medicalIntent = new Intent(Intent.ACTION_VIEW, medicalURI);
                medicalIntent.setPackage("com.google.android.apps.maps");
                if (medicalIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(medicalIntent);
                }else {
                    Toast.makeText(this,"No App to show Location",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.nav_breather:
                startActivity(new Intent(this,Breather.class));
                break;

            case R.id.nav_sounds:
                startActivity(new Intent(this,SoundActivity.class));
                break;

            case R.id.nav_challenges:
                startActivity(new Intent(this, Challenges.class));
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
                super.onBackPressed();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.signoutM:
                mAuth.signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);

    }


    private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(3000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    //todo store the path in string resource and also get bus id and pass
                    location = locationResult.getLastLocation();
                        if (location != null) {
                            Log.d("TAG", "location update " + location);

                        }

                }
            }, null);
        }
    }



}
