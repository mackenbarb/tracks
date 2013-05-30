package org.developerworks.android.tracks;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	public final static String LOCATION = "Unknown";	// Location  sent in Intent
	public final static String DIRECTION = "Unknown";	// Direction sent in Intent
	public final static String TRACK = "Unknown";	// Track sent in Intent (TBCoded - will be Track user currently on)

	Location location = new Location("");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		/**Setting up location manager **/
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location newLocation) {
		      // Called when a new location is found by the network location provider.
		      location = newLocation;
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Called when the user clicks the Run button */
	public void run(View view) {
		Intent intent = new Intent(this, MapView.class);
		startActivity(intent);
		
//		//Create new Intent
//		Log.i("Run Method", "Got here!");
//		Intent intent = new Intent(this, CameraOverlay.class);
//
//
//		//Add all details to intent
//		intent.putExtra(LOCATION, location.toString());
//		intent.putExtra(DIRECTION, "TBCoded");
//		intent.putExtra(TRACK, "TBCoded");
//
//		//Create Toast to show what we're about to try display
//		Log.i("Run Method", "Toasty");
//		Context context = getApplicationContext();
//		CharSequence text = "Current Location - " + location.toString();
//		int duration = Toast.LENGTH_SHORT;
//
//		Toast toast = Toast.makeText(context, text, duration);
//		toast.show();
//
//		//Start new activity (aka sending details to MetLink)
//		Log.i("Run Method", "Starting activity");
//		startActivity(intent);
	}

}
