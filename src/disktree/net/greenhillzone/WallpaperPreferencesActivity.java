package disktree.net.greenhillzone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

//public class WallpaperSettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
public class WallpaperPreferencesActivity extends PreferenceActivity {

	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate( savedInstanceState );

		setContentView( R.layout.activity_settings );
		addPreferencesFromResource( R.xml.prefs );

		//getPreferenceManager().setSharedPreferencesName( Wallpaper.SHARED_PREFS_NAME );

		adView = new AdView( this );
		adView.setAdSize( AdSize.SMART_BANNER );
        adView.setAdUnitId( getString( R.string.admob_banner ) );

		AdRequest.Builder builder = new AdRequest.Builder();
		builder.addTestDevice( AdRequest.DEVICE_ID_EMULATOR );
        builder.addTestDevice( "864EE20E570931A1A5735369633BEF3A" );

		RelativeLayout adLayout = (RelativeLayout) findViewById(R.id.ad);
		adLayout.addView( adView );

		AdRequest adRequest = builder.build();
		adView.loadAd( adRequest );

		String id = om.Admob.ID;

		//adView = AdMob.createBanner( this );
		//RelativeLayout adLayout = (RelativeLayout) findViewById(R.id.ad);
        //adLayout.addView( adView );
	}

	@Override
    public void onPause() {
        adView.pause();
        super.onPause();
    }

	@Override
	protected void onResume() {
		super.onResume();
		adView.resume();
	}

	@Override
	protected void onDestroy() {
		// getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		adView.destroy();
		super.onDestroy();
	}

	/*
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu, menu );
        return true;
    }
	*/

	/*
	@Override
	public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key ) {
		android.util.Log.d("greenhillzone", "onSharedPreferenceChanged: " + key );
	}
	*/

}
