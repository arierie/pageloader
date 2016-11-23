package id.arieridwan.lib;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by arieridwan on 20/11/2016.
 */

public class PageLoader extends RelativeLayout {

    public static final String ROTATE_MODE = "rotate";
    public static final String FLIP_MODE = "flip";
    public static final String VIBRATE_MODE = "vibrate";
    public static final String SHAKE_MODE = "shake";
    public static final String BOUNCE_MODE = "bounce";
    private RelativeLayout progressView;
    private LinearLayout progressViewStart;
    private LinearLayout progressViewFailed;
    private ImageView imageLoading;
    private TextView textLoading;
    private ImageView imageError;
    private TextView textError;
    private TypedArray array;
    private Context mContext;
    private Typeface typeface;
    private Animation myRotation;
    private Animation myFlip;
    private Animation myVibrate;
    private Animation myShake;
    private Animation myBounce;

    public PageLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.pageloader, this, true);
        mContext = context;
        array = context.obtainStyledAttributes(attrs, R.styleable.PageLoader, 0, 0);
        initView();
    }

    private void initView() {
        progressView = (RelativeLayout) findViewById(R.id.progressPage);
        progressViewStart = (LinearLayout) findViewById(R.id.progressPageStart);
        progressViewFailed = (LinearLayout) findViewById(R.id.progressPageFailed);
        progressViewFailed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startProgress();
            }
        });
        imageLoading = (ImageView) findViewById(R.id.mImageLoading);
        textLoading = (TextView) findViewById(R.id.mTextLoading);
        imageError = (ImageView) findViewById(R.id.mImageError);
        textError = (TextView) findViewById(R.id.mTextError);
        typeface = Typeface.createFromAsset(getResources().getAssets(), "font/bariol_regular_italic.otf");
        textLoading.setTypeface(typeface);
        textError.setTypeface(typeface);
        setData();
    }

    private void setData() {
        try {
            int textSize = array.getDimensionPixelSize(R.styleable.PageLoader_setTextSize, 0);
            setTextSize(textSize);
            ColorStateList textColor = array.getColorStateList(R.styleable.PageLoader_setTextColor);
            setTextColor(textColor);
            String loadingText = array.getString(R.styleable.PageLoader_setLoadingText);
            setTextLoading(loadingText);
            String errorText = array.getString(R.styleable.PageLoader_setErrorText);
            setTextError(errorText);
            int loadingImage = array.getResourceId(R.styleable.PageLoader_setLoadingImage, 0);
            setImageLoading(loadingImage);
            String animationMode = array.getString(R.styleable.PageLoader_setLoadingAnimationMode);
            setLoadingAnimationMode(animationMode);
            int errorImage = array.getResourceId(R.styleable.PageLoader_setErrorImage, 0);
            setImageError(errorImage);
            int errorImageHeight = array.getDimensionPixelSize(R.styleable.PageLoader_setErrorImageHeight, 0);
            setErrorImageHeight(errorImageHeight);
            int errorImageWidth = array.getDimensionPixelSize(R.styleable.PageLoader_setErrorImageWidth, 0);
            setErrorImageWidth(errorImageWidth);
            int loadingImageHeight = array.getDimensionPixelSize(R.styleable.PageLoader_setLoadingImageHeight, 0);
            setLoadingImageHeight(loadingImageHeight);
            int loadingImageWidth = array.getDimensionPixelSize(R.styleable.PageLoader_setLoadingImageWidth, 0);
            setLoadingImageWidth(loadingImageWidth);
        } finally {
            array.recycle();
        }
    }

    // method attribut
    public void setTextSize(int i) {
        if (i != 0) {
            textError.setTextSize(TypedValue.COMPLEX_UNIT_SP, i);
            textLoading.setTextSize(TypedValue.COMPLEX_UNIT_SP, i);
            Toast.makeText(mContext,i+"",Toast.LENGTH_LONG).show();
        } else {
            textError.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
            textLoading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        }
    }

    public void setTextColor(ColorStateList c) {
        if (c != null) {
            textError.setTextColor(c);
            textLoading.setTextColor(c);
        } else {
            textError.setTextColor(getResources().getColor(R.color.colorGrayDark));
            textLoading.setTextColor(getResources().getColor(R.color.colorGrayDark));
        }
    }

    public void setTextLoading(String s) {
        if (!TextUtils.isEmpty(s)) {
            textLoading.setText(s);
        } else {
            textLoading.setText(getResources().getString(R.string.loading_text));
        }
    }

    public void setImageLoading(int i) {
        Drawable d;
        if (i != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                try {
                    imageLoading.setBackground(getResources().getDrawable(i));
                } catch (Exception e) {
                    try {
                        d = VectorDrawableCompat.create(getResources(), i, mContext.getTheme());
                        imageLoading.setBackground(d);
                    }
                    catch (Exception ee){
                        Toast.makeText(mContext,getResources().getString(R.string.loading_image_fail),Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                try {
                    imageLoading.setBackgroundDrawable(getResources().getDrawable(i));
                } catch (Exception e) {
                    try {
                        d = VectorDrawableCompat.create(getResources(), i, mContext.getTheme());
                        imageLoading.setBackgroundDrawable(d);
                    }
                    catch (Exception ee){
                        Toast.makeText(mContext,getResources().getString(R.string.loading_image_fail),Toast.LENGTH_LONG).show();
                    }
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                d = VectorDrawableCompat.create(getResources(), R.drawable.ic_search, mContext.getTheme());
                imageLoading.setBackground(d);
            } else {
                d = VectorDrawableCompat.create(getResources(), R.drawable.ic_search, mContext.getTheme());
                imageLoading.setBackgroundDrawable(d);
            }
        }
    }

    public void setLoadingImageHeight(int i) {
        if (i != 0) {
            imageLoading.getLayoutParams().height = i;
        } else {
            imageLoading.getLayoutParams().height = 64;
        }
    }

    public void setLoadingImageWidth(int i) {
        if (i != 0) {
            imageLoading.getLayoutParams().width = i;
        } else {
            imageLoading.getLayoutParams().width = 64;
        }
    }

    public void setLoadingAnimationMode(String s) {
        myRotation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_anim);
        myFlip = AnimationUtils.loadAnimation(mContext, R.anim.flip_anim);
        myVibrate = AnimationUtils.loadAnimation(mContext, R.anim.vibrate_anim);
        myShake = AnimationUtils.loadAnimation(mContext, R.anim.shake_anim);
        myBounce = AnimationUtils.loadAnimation(mContext, R.anim.bounce_anim);
        if (!TextUtils.isEmpty(s)) {
            switch (s.toLowerCase()) {
                case ROTATE_MODE:
                    imageLoading.startAnimation(myRotation);
                    break;
                case FLIP_MODE:
                    imageLoading.startAnimation(myFlip);
                    break;
                case VIBRATE_MODE:
                    imageLoading.startAnimation(myVibrate);
                    break;
                case SHAKE_MODE:
                    imageLoading.startAnimation(myShake);
                    break;
                case BOUNCE_MODE:
                    imageLoading.startAnimation(myBounce);
                    break;
                default:
                    imageLoading.startAnimation(myFlip);
                    break;
            }
        } else {
            imageLoading.startAnimation(myFlip);
        }
    }

    public void setTextError(String s) {
        if (!TextUtils.isEmpty(s)) {
            textError.setText(s);
        } else {
            textError.setText(getResources().getString(R.string.error_text));
        }
    }

    public void setImageError(int i) {
        Drawable d;
        if (i != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                try {
                    imageError.setBackground(getResources().getDrawable(i));
                } catch (Exception e) {
                    try {
                        d = VectorDrawableCompat.create(getResources(), i, mContext.getTheme());
                        imageError.setBackground(d);
                    }
                    catch (Exception ee){
                        Toast.makeText(mContext,getResources().getString(R.string.error_image_fail),Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                try {
                    imageError.setBackgroundDrawable(getResources().getDrawable(i));
                } catch (Exception e) {
                    try {
                        d = VectorDrawableCompat.create(getResources(), i, mContext.getTheme());
                        imageError.setBackgroundDrawable(d);
                    }
                    catch (Exception ee){
                        Toast.makeText(mContext,getResources().getString(R.string.error_image_fail),Toast.LENGTH_LONG).show();
                    }
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                d = VectorDrawableCompat.create(getResources(), R.drawable.ic_not_found, mContext.getTheme());
                imageError.setBackground(d);
            } else {
                d = VectorDrawableCompat.create(getResources(), R.drawable.ic_not_found, mContext.getTheme());
                imageError.setBackgroundDrawable(d);
            }
        }
    }

    public void setErrorImageHeight(int i) {
        if (i != 0) {
            imageError.getLayoutParams().height = i;
        } else {
            imageError.getLayoutParams().height = 64;
        }
    }

    public void setErrorImageWidth(int i) {
        if (i != 0) {
            imageError.getLayoutParams().width = i;
        } else {
            imageError.getLayoutParams().width = 64;
        }
    }

    // method in java
    public void setCustomAnimation(Animation a) {
        if (a != null) {
            imageLoading.startAnimation(a);
        } else {
            imageLoading.startAnimation(myFlip);
        }
    }

    public void setCustomFont(Typeface t) {
        if (t != null) {
            textError.setTypeface(t);
            textLoading.setTypeface(t);
        } else {
            textError.setTypeface(typeface);
            textLoading.setTypeface(typeface);
        }
    }

    public void setOnRetry(View.OnClickListener v) {
        progressViewFailed.setOnClickListener(v);
    }

    public void startProgress() {
        progressView.setVisibility(View.VISIBLE);
        progressViewStart.setVisibility(View.VISIBLE);
        progressViewFailed.setVisibility(View.GONE);
    }

    public void stopProgress() {
        progressView.setVisibility(View.GONE);
        progressViewStart.setVisibility(View.GONE);
        progressViewFailed.setVisibility(View.GONE);
    }

    public void stopProgressAndFailed() {
        progressView.setVisibility(View.VISIBLE);
        progressViewStart.setVisibility(View.GONE);
        progressViewFailed.setVisibility(View.VISIBLE);
    }
}
