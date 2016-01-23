package disktree.net.greenhillzone;

import android.graphics.Bitmap;

public class ParallaxLayer {

    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_TOP = 2;

    public boolean fixed = false;
    public float offsetX;
    public float offsetY;

    private int align;
    private Bitmap bmp;

    public ParallaxLayer( Bitmap bmp, int align, float offsetX, float offsetY ) {
        this.bmp = bmp;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.align = align;
    }

    public ParallaxLayer( Bitmap bmp, int align ) {
        this( bmp, align, 0, 0 );
    }

    public ParallaxLayer( Bitmap bmp ) {
        this( bmp, ALIGN_BOTTOM, 0, 0 );
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

    public final int getAlign() {
        return align;
    }

    public final void clear() {
        if( bmp != null ) {
            bmp.recycle();
            bmp = null;
        }
    }
}
