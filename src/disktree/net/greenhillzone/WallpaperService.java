package disktree.net.greenhillzone;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import disktree.net.greenhillzone.ParallaxLayer;

public class WallpaperService extends android.service.wallpaper.WallpaperService {

	private static final String TAG = "greenhillzone";

	@Override
	public final Engine onCreateEngine() {
		return new WallpaperEngine( getApplicationContext() );
	}

	private final class WallpaperEngine extends Engine implements OnSharedPreferenceChangeListener {

		private final Handler handler = new Handler();
		private final Runnable drawRunner = new Runnable() {
			@Override public final void run() { drawFrame(); }
		};

		private Context context;
		private SharedPreferences prefs;
		private Parallax parallax;
		private String spritesheetName;

		WallpaperEngine(Context context) {

			super();
			this.context = context;

			WallpaperManager wallpaperManager = WallpaperManager.getInstance( context );
			int wallpaperWidth = wallpaperManager.getDesiredMinimumWidth();
			int wallpaperHeight = wallpaperManager.getDesiredMinimumHeight();
			//log( "WALLPAPER: "+wallpaperWidth+":"+wallpaperHeight);

			/*
			//Display display = context.getWindowManager().getDefaultDisplay();
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display display = windowManager.getDefaultDisplay();
			//Point displaySize = new Point();
			//log( "SCREEN1: "+displaySize.x+":"+displaySize.y);
			log( "SCREEN1: "+display.getWidth()+":"+display.getHeight() );
			*/

			//DisplayMetrics metrics = context.getResources().getDisplayMetrics();
			//int screenWidth = metrics.widthPixels;
			//int screenHeight = metrics.heightPixels;
			//log( "SCREEN2: "+screenWidth+":"+screenHeight);

			prefs = PreferenceManager.getDefaultSharedPreferences(context);
			prefs.registerOnSharedPreferenceChangeListener(this);

			parallax = new Parallax( 0, 0, 0xff2400b6, prefs.getBoolean( "pref_parallax", true ) );
			applySpritesheet( "greenhillzone" );

			//handler.post( drawRunner );
		}

		private void applySpritesheet( String name ) {
			if( name.equals( spritesheetName ) )
				return;
			parallax.clear();
			if( name.equals( "greenhillzone" ) ) {
				spritesheetName = name;
				parallax.backgroundColor = 0xff2400b6;
				parallax.addLayer( getBitmapResource( R.drawable.greenhillzone_4 ), ParallaxLayer.ALIGN_TOP, 0, 0 );
				parallax.addLayer( getBitmapResource( R.drawable.greenhillzone_3 ) );
				parallax.addLayer( getBitmapResource( R.drawable.greenhillzone_2 ) );
				parallax.addLayer( getBitmapResource( R.drawable.greenhillzone_1 ) );
			} else if( name.equals( "marblezone" ) ) {
				spritesheetName = name;
				parallax.backgroundColor = 0xff0000b6;
				parallax.addLayer( getBitmapResource( R.drawable.marblezone_4 ) );
				parallax.addLayer( getBitmapResource( R.drawable.marblezone_3 ) );
				parallax.addLayer( getBitmapResource( R.drawable.marblezone_2 ) );
				parallax.addLayer( getBitmapResource( R.drawable.marblezone_1 ) );
			} else if( name.equals( "labyrinthzone" ) ) {
				spritesheetName = name;
				parallax.backgroundColor = 0xff000000;
				parallax.addLayer( getBitmapResource( R.drawable.labyrinthzone_3 ) );
				parallax.addLayer( getBitmapResource( R.drawable.labyrinthzone_2 ), ParallaxLayer.ALIGN_TOP, 0, 0 );
				parallax.addLayer( getBitmapResource( R.drawable.labyrinthzone_1 ) );
			} else if( name.equals( "scrapbrainzone" ) ) {
				spritesheetName = name;
				parallax.backgroundColor = 0xff6d4900;
				parallax.addLayer( getBitmapResource( R.drawable.scrapbrainzone_4 ), ParallaxLayer.ALIGN_TOP, 0, 0 );
				parallax.addLayer( getBitmapResource( R.drawable.scrapbrainzone_3 ) );
				parallax.addLayer( getBitmapResource( R.drawable.scrapbrainzone_2 ) );
				parallax.addLayer( getBitmapResource( R.drawable.scrapbrainzone_1 ) );
			} else if( name.equals( "starlightzone" ) ) {
				spritesheetName = name;
				parallax.backgroundColor = 0xff000000;
				parallax.addLayer( getBitmapResource( R.drawable.starlightzone_5 ) );
				parallax.addLayer( getBitmapResource( R.drawable.starlightzone_4 ) );
				parallax.addLayer( getBitmapResource( R.drawable.starlightzone_3 ) );
				parallax.addLayer( getBitmapResource( R.drawable.starlightzone_2 ) );
				parallax.addLayer( getBitmapResource( R.drawable.starlightzone_1 ) );
			} else if( name.equals( "springyardzone" ) ) {
				spritesheetName = name;
				parallax.backgroundColor = 0xff6d246d;
				//parallax.addLayer( getBitmapResource( R.drawable.springyardzone_4 ) );
				parallax.addLayer( getBitmapResource( R.drawable.springyardzone_3 ) );
				parallax.addLayer( getBitmapResource( R.drawable.springyardzone_2 ) );
				parallax.addLayer( getBitmapResource( R.drawable.springyardzone_1 ) );
			} else if( name.equals( "random" ) ) {
				//TODO
			} else {
				log( "UNKNOWN SPRITESHEET: "+spritesheetName );
			}
			handler.post( drawRunner );
			//drawFrame();
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			prefs.unregisterOnSharedPreferenceChangeListener( this );
		}

		@Override
		public void onVisibilityChanged( boolean visible ) {
			super.onVisibilityChanged( visible );
			if( visible )
				handler.post( drawRunner );
			else
				handler.removeCallbacks( drawRunner );
		}

		@Override
		public void onSurfaceChanged( SurfaceHolder holder, int format, int width, int height ) {
			super.onSurfaceChanged( holder, format, width, height );
			//log( width+":"+height );
			parallax.width = width;
			parallax.height = height;
			drawFrame();
		}

		@Override
		public void onSurfaceDestroyed( SurfaceHolder holder ) {
			super.onSurfaceDestroyed( holder );
			handler.removeCallbacks( drawRunner );
		}

		@Override
		public void onOffsetsChanged( float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels ) {
			super.onOffsetsChanged( xOffset, yOffset, xStep, yStep, xPixels, yPixels );
			parallax.setOffset( xOffset, yOffset );
			//log(""+xStep+" : "+xPixels);
			//log("################");
			//log( "OFFSET: "+xOffset+":"+yOffset );
			//log( "STEP: "+xStep+":"+yStep );
			//log( "PIXELS: "+xPixels+":"+yPixels );
			handler.post( drawRunner );
		}

		/*
		@Override
		public void onTouchEvent( MotionEvent event ) {
			super.onTouchEvent( event );
			if( event.getAction() == MotionEvent.ACTION_MOVE ) {
				mTouchX = event.getX();
				mTouchY = event.getY();
			} else {
				mTouchX = -1;
				mTouchY = -1;
			}
		}
		*/

		public void onSharedPreferenceChanged( SharedPreferences prefs, String key ) {
			log( "onSharedPreferenceChanged: " + key );
			if( key.equals( "pref_parallax" ) ) {
				//Preference pref = findPreference(key);
				parallax.enabled = prefs.getBoolean( key, true );
			} else if( key.equals( "pref_spritesheet" ) ) {
				applySpritesheet( prefs.getString( key, "greenhillzone" ) );
			}
		}

		public void drawFrame() {
			final SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();
				if( canvas != null ) {
					draw( canvas );
				}else{
					log("???????????");
				}
			} catch( IllegalArgumentException e ) {
				log( "ERROR "+e.toString() );
			} finally {
				if( canvas != null ) {
					holder.unlockCanvasAndPost( canvas );
				}
			}
			handler.removeCallbacks( drawRunner );
		}

		private final Bitmap getBitmapResource( int id ) {
			return BitmapFactory.decodeResource( getResources(), id );
		}

		private final void draw( Canvas canvas ) {
			parallax.render( canvas );
		}

		private final void log( String str ) {
			Log.d( TAG, "GreenhillWallpaperEngine: "+str );
		}
	}
}
