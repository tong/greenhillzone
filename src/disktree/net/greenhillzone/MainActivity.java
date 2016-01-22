package disktree.net.greenhillzone;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {

    private static final String TAG = "greenhillzone";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView( R.layout.activity_main );

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        ParallaxView parallax = new ParallaxView( this, screenWidth, screenHeight, 0xff2400b6 );
        parallax.addLayer( getBitmapResource( R.drawable.greenhillzone_4 ), 0, 0 );
        parallax.addLayer( getBitmapResource( R.drawable.greenhillzone_3 ), 0, 0 );
        parallax.addLayer( getBitmapResource( R.drawable.greenhillzone_2 ), 0, 0 );
        parallax.addLayer( getBitmapResource( R.drawable.greenhillzone_1 ), 0, 0 );
        //setContentView( parallax );
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.parallax);
        layout.addView(parallax);

        AdView adView = (AdView) findViewById( R.id.adView );
        AdRequest.Builder builder = new AdRequest.Builder();
        builder.addTestDevice( AdRequest.DEVICE_ID_EMULATOR );
        builder.addTestDevice( "864EE20E570931A1A5735369633BEF3A" );
        AdRequest adRequest = builder.build();
        adView.loadAd(adRequest);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Intent intent = new Intent( WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER );
        intent.putExtra( WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName( this, WallpaperService.class ) );
        startActivity(intent);

        return super.onTouchEvent(event);
    }

    private Bitmap getBitmapResource( int id ) {
        return BitmapFactory.decodeResource( getResources(), id );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    private static final void log( String str ) {
        android.util.Log.d( TAG, "GreenhillWallpaperEngine: "+str );
    }
}
