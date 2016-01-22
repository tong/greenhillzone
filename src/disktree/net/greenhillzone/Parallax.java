package disktree.net.greenhillzone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint;
import java.util.ArrayList;

public class Parallax {

    public boolean enabled;
    public int width;
	public int height;
    public int backgroundColor;
    //public float scale = 1;

    private float offsetX;
	private float offsetY;

    private ArrayList<ParallaxLayer> layers;
    private Paint paint;
    private final Matrix matrix;
    private float minWidth = 100000000;

    Parallax( int width, int height, int backgroundColor, boolean enabled ) {

        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.enabled = enabled;

        clear();

        paint = new Paint();
        paint.setStyle( Paint.Style.FILL );
        paint.setAntiAlias( true );

        matrix = new Matrix();

        /*
        for( ParallaxLayer layer : layers ) {
            android.util.Log.d( "greenhillzone",  "LAYER: "+layer.getWidth()+":"+layer.getHeight() );
        }
        */
    }

    public final void addLayer( Bitmap bmp, float offsetX, float offetY ) {
        ParallaxLayer l = new ParallaxLayer( bmp, offsetX, offsetY );
        layers.add(l);
        if( l.getWidth() < minWidth ) minWidth = l.getWidth();
    }

    public final void addLayer( Bitmap bmp ) {
        addLayer( bmp, 0,0 );
    }

    public void clear() {
        layers = new ArrayList<ParallaxLayer>();
        offsetX = offsetY = 0;
    }

    public final void setOffset( float x, float y ) {
		offsetX = x;
		offsetY = y;
	}

    public void render( Canvas canvas ) {

        canvas.drawColor( backgroundColor );

        int i = 0;
        for( ParallaxLayer layer : layers ) {

            final Bitmap bmp = layer.getBitmap();
            if( bmp == null )
				continue;

            final float layerWidth = layer.getWidth();
            final float layerHeight = layer.getHeight();

            float tx = 0;
            float ty = 0;

            if( enabled ) {
                tx = offsetX * ( layerWidth - width );
            } else {
                tx = 0; //offsetX * minWidth;
            }
            ty = height - layerHeight;

            //matrix.preScale( scale, scale );
            matrix.setTranslate( -tx, ty );
            //matrix.postTranslate( -tx, ty );

            canvas.drawBitmap( layer.getBitmap(), matrix, paint );

            //matrix.reset();

            i++;
        }

        matrix.reset();
    }
}
