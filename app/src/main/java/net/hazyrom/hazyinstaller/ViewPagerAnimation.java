package net.hazyrom.hazyinstaller;

import android.support.v4.view.ViewPager;
import android.view.View;

/*
 * NOTE: This class can be used as a library. There are nice annimations I found on the Net
 *       that your app can use. Feel free to try/use it.
 *
 * USAGE: viewPager.setPageTransformer(false, new ViewPagerTransformer(TransformType.FLOW));
 *        Of course you must change the viewPager, and if you want, also the transition.
 *        There are eight possibilities:
 *        -> FLOW
 *        -> DEPTH
 *        -> ZOOM
 *        -> FADE
 *        -> FAST_ENTER_FROM_CENTER
 *        -> ROTATION
 *        -> SLIDE_AND_FADE
 *        -> SLIDE_OVER
 *
 */

public class ViewPagerAnimation implements ViewPager.PageTransformer {

    private final TransformType mTransformType;

    private float minAlpha;
    private int degrees;
    private float distanceToCentreFactor;

    static int pageWidth;

    private static float scalingStart = 0;

    public ViewPagerAnimation(TransformType transformType) {
        mTransformType = transformType;
        scalingStart = 1 - scalingStart;
        distanceToCentreFactor = (float) Math.tan(Math.toRadians(degrees / 2)) / 2;
    }

    static enum TransformType {
        FLOW,
        DEPTH,
        ZOOM,
        FADE,
        FAST_ENTER_FROM_CENTER,
        ROTATION,
        SLIDE_AND_FADE,
        SLIDE_OVER
    }

    private static final float getAlpha(final float position) {
        return getSlowQuadraticAlpha(position);
    }

    private static final float getLinearAlpha(final float position) {
        if (position <= 0) {
            return 1 + position;
        }
        return 1 - position;
    }

    private static final float getFastQuadraticAlpha(final float position) {
        final float linearAlpha = getLinearAlpha(position);
        return linearAlpha * linearAlpha;
    }

    private static final float getSlowQuadraticAlpha(final float position) {
        return 1 - position * position;
    }

    private static final float MIN_SCALE_DEPTH = 0.75f;
    private static final float MIN_SCALE_ZOOM = 0.85f;
    private static final float MIN_ALPHA_ZOOM = 0.5f;
    private static final float SCALE_FACTOR_SLIDE = 0.85f;
    private static final float MIN_ALPHA_SLIDE = 0.35f;

    public void transformPage(View page, float position) {
        final float alpha;
        final float scale;
        final float translationX;

        switch (mTransformType) {
            case FLOW:
                page.setRotationY(position * -30f);
                return;

            case SLIDE_OVER:
                if (position < 0 && position > -1) {
                    scale = Math.abs(Math.abs(position) - 1) * (1.0f - SCALE_FACTOR_SLIDE) + SCALE_FACTOR_SLIDE;
                    alpha = Math.max(MIN_ALPHA_SLIDE, 1 - Math.abs(position));
                    int pageWidth = page.getWidth();
                    float translateValue = position * -pageWidth;
                    if (translateValue > -pageWidth) {
                        translationX = translateValue;
                    } else {
                        translationX = 0;
                    }
                } else {
                    alpha = 1;
                    scale = 1;
                    translationX = 0;
                }
                break;

            case DEPTH:
                if (position > 0 && position < 1) {
                    // moving to the right
                    alpha = (1 - position);
                    scale = MIN_SCALE_DEPTH + (1 - MIN_SCALE_DEPTH) * (1 - Math.abs(position));
                    translationX = (page.getWidth() * -position);
                } else {
                    // use default for all other cases
                    alpha = 1;
                    scale = 1;
                    translationX = 0;
                }
                break;

            case ZOOM:
                if (position >= -1 && position <= 1) {
                    scale = Math.max(MIN_SCALE_ZOOM, 1 - Math.abs(position));
                    alpha = MIN_ALPHA_ZOOM +
                            (scale - MIN_SCALE_ZOOM) / (1 - MIN_SCALE_ZOOM) * (1 - MIN_ALPHA_ZOOM);
                    float vMargin = page.getHeight() * (1 - scale) / 2;
                    float hMargin = page.getWidth() * (1 - scale) / 2;
                    if (position < 0) {
                        translationX = (hMargin - vMargin / 2);
                    } else {
                        translationX = (-hMargin + vMargin / 2);
                    }
                } else {
                    alpha = 1;
                    scale = 1;
                    translationX = 0;
                }
                break;

            case SLIDE_AND_FADE:
                float n = Math.abs(Math.abs(position) - 1);
                page.setAlpha(n);

            case FAST_ENTER_FROM_CENTER:
                if (position >= 0) {
                    int w = page.getWidth();
                    float scaleFactor = 1 - scalingStart * position;
                    page.setAlpha(1 - position);
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                    page.setTranslationX(w * (1 - position) - w);
                }

            case ROTATION:
                int pageHeight = page.getHeight();
                page.setPivotX((float) pageWidth / 2);
                page.setPivotY((pageHeight + pageWidth * distanceToCentreFactor));

                if (position < -1) { //[-infinity,1)
                    //off to the left by a lot
                    page.setRotation(0);
                    page.setAlpha(0);
                } else if (position <= 1) { //[-1,1]
                    page.setTranslationX((-position) * pageWidth); //shift the view over
                    page.setRotation(position * (180 - degrees)); //rotate it
                    // Fade the page relative to its distance from the center
                    page.setAlpha(Math.max(minAlpha, 1 - Math.abs(position) / 3));
                } else { //(1, +infinity]
                    //off to the right by a lot
                    page.setRotation(0);
                    page.setAlpha(0);
                }

            case FADE:
                //Keep the page static
                int wi = page.getWidth();
                page.setTranslationX(wi * (1 - position) - wi);

                // Apply the fade
                if (position >= 0) {
                    page.setAlpha(1 - position);
                } else {
                    page.setAlpha(1 + position);
                }

            default:
                return;
        }
        page.setAlpha(alpha);
        page.setTranslationX(translationX);
        page.setScaleX(scale);
        page.setScaleY(scale);
    }
}