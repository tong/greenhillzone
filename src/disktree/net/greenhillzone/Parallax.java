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

    public int width;
	public int height;

    private float offsetX;
	private float offsetY;

    private ArrayList<ParallaxLayer> layers;

    private int backgroundColor;
    private Paint paint;
    private final Matrix matrix;
    private ArrayList<Bitmap> bitmaps;

    Parallax( int width, int height, int backgroundColor ) {

        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;

        layers = new ArrayList<ParallaxLayer>();

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
    }

    public final void addLayer( Bitmap bmp ) {
        ParallaxLayer l = new ParallaxLayer( bmp, 0, 0 );
        layers.add(l);
    }

    public final void setOffset( float x, float y ) {
		offsetX = x;
		offsetY = y;
	}

    public void render( Canvas canvas ) {

        canvas.drawColor( backgroundColor );

        //Matrix matrix = new Matrix();
        //float scale = 1;

        int i = 0;
        for( ParallaxLayer layer : layers ) {

            final Bitmap bmp = layer.getBitmap();
            if( bmp == null )
				continue;

            final float layerWidth = layer.getWidth();
            final float layerHeight = layer.getHeight();

            float tx = offsetX * ( layerWidth - width );
            float ty = height - layerHeight;

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
