package disktree.net.greenhillzone;

import android.graphics.Bitmap;

public class ParallaxLayer {

    public boolean fixed = false;

    public float offsetX;
    public float offsetY;

    private Bitmap bmp;

    public ParallaxLayer( Bitmap bmp, float offsetX, float offsetY ) {
        this.bmp = bmp;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public ParallaxLayer( Bitmap bmp ) {
        this.bmp = bmp;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public Bitmap getBitmap() {
        return bmp;
    }

    public final float getWidth() {
        return bmp.getWidth();
    }

    public final float getHeight() {
        return bmp.getHeight();
    }

    public final void clear() {
        if( bmp != null ) {
            bmp.recycle();
            bmp = null;
        }
    }
}
