package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener,View.OnKeyListener{
    Boolean Signupactive = true;
    EditText h ;
    EditText p ;
    TextView login;
    String g;
    LocationManager locationManager;
    LocationListener locationListener;
    //public void setintent(){
      //  Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
       // startActivity(intent);
    //}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0) {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }}}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView login=findViewById(R.id.login);
        login.setOnClickListener(this);
        p=findViewById(R.id.editText2);
        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                 g=location.toString();
                 Log.i("location of user",g);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},2);}
            else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }

        p.setOnKeyListener(this);
        ImageView img1=findViewById(R.id.imageView);
        ConstraintLayout rl=findViewById(R.id.loginpage);
        rl.setOnClickListener(this);
        img1.setOnClickListener(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("80ae709bccdb6f52a1e28df124921b2b38ed163f")
                // if defined
                .clientKey("ce73e8361b4798480e7d26620a0444f98d6fee87")
                .server("http://3.17.56.110:80/parse")
                .build()
        );
if(ParseUser.getCurrentUser()!=null){
    setintent();
}

        }
    public void setintent(){
        Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
        ParseUser current=ParseUser.getCurrentUser();
        String j=current.getUsername();
        intent.putExtra("user",j);
        startActivity(intent);
    }

    public void sighup(View view) {
        h = findViewById(R.id.editText);
        p = findViewById(R.id.editText2);
        if (h.getText().toString().matches("") || p.getText().toString().matches("")) {
            Toast.makeText(this, "enter user name and password", Toast.LENGTH_SHORT).show();
        } else {
            if (Signupactive) {
                ParseUser user = new ParseUser();
                user.setUsername(h.getText().toString());
                user.setPassword(p.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null) {
                                                    Log.i("done", "done");
                                                    setintent();
                                                } else {
                                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                );
            } else {
                ParseUser.logInInBackground(h.getText().toString(), p.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e == null){
                            Log.i("done","logined");
                            setintent();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();;
                        }

                    }
                });
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            Button n = findViewById(R.id.signup);
            login=findViewById(R.id.login);
            if (Signupactive) {
                n.setText("Login");
                Signupactive = false;
                login.setText("or,signup");

            } else {
                n.setText("signup");
                Signupactive = true;
                login.setText("or,login");

            }

        }
        else if(v.getId()==R.id.imageView||v.getId()==R.id.loginpage)
        {
            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
          if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
              sighup(v);
          }


        return false;
    }
}
        /*ParseObject gameScore = new ParseObject("GameScore");
        gameScore.put("score", 1337);
        gameScore.put("playerName", "Sean Plott");
        gameScore.put("cheatMode", false);
        gameScore.saveInBackground();*/
        // ParseQuery<ParseObject> query=ParseQuery.getQuery("GameScore");
        /*query.getInBackground("ZLrDIOUaIQ", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e==null && object!=null)
                {
                 object.put("score",1200);
                 object.saveInBackground();
                }
            }
        });*/
        /*query.whereEqualTo("playerName", "Sean Plott");
        query.findInBackground(new FindCallback<ParseObject>() {
                                   @Override
                                   public void done(List<ParseObject> objects, ParseException e) {
                                       if(e==null) {
                                           if(objects.size()>0)
                                           {
for(ParseObject object:objects){

}
                                           }


                                       }
                                   }
                               }
        );
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }*/

