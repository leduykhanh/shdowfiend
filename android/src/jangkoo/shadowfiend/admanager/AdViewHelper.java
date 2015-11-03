package jangkoo.shadowfiend.admanager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class AdViewHelper {
    /**
     * Constants
     */
    private final static String TAG = "AdViewHelper";
    
    /**
     * Members
     */
    private Context    mContext;
    private Handler    mHandler;
    private ViewGroup  mAdViewContainer;
    private AdView     mAdView;
    private AdListener mAdViewListener;
    public AdViewHelper(Context pContext, ViewGroup pViewGroup) {
        // Init
        mContext        = pContext;
        mHandler        = new Handler();
        
        // AdViewContainer...
        mAdViewContainer = new FrameLayout(mContext);
        mAdViewContainer.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));
        mAdViewContainer.setVisibility(View.GONE);
        pViewGroup.addView(mAdViewContainer);
         // AdViewListener...
        mAdViewListener = new AdListener() {

            /*
             * (non-Javadoc)
             * @see com.google.android.gms.ads.AdListener#onAdLoaded()
             */
            @Override
            public void onAdLoaded() {

                // Show ad...
                mAdViewContainer.setVisibility(View.VISIBLE);
            }
            /*
             * (non-Javadoc)
             * @see com.google.android.gms.ads.AdListener#onAdFailedToLoad(int)
             */
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Fail...
                Log.e(TAG, String.format("onAdFailedToLoad(%s)", errorCode));

                // Refresh it ourselves...
                mHandler.removeCallbacks(null);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                }, 5000);
            }
        };
    }

    /**
     * Refresh ad.
     */
    public final void refresh() {
        // Destroy old instance...
        destroy();
        
        // Create new instance...
        mAdView = new AdView(mContext);
        mAdView.setAdUnitId("ca-app-pub-8010283700967425/7571551398");
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdListener(mAdViewListener);
        
        // Add to container...
        mAdViewContainer.addView(mAdView);
        
        // Request new ad...
        mAdView.loadAd(new AdRequest.Builder().build());
    }
    
    /**
     * Pause ad.
     */
    public final void pause() {
        mAdView.pause();
    }
    
    /**
     * Resume ad.
     */
    public final void resume() {
        mAdView.resume();
    }

    /**
     * Destroy ad.
     */
    public void destroy() {
        mHandler.removeCallbacks(null);
        if (mAdView != null) {
            mAdView.destroy();
        }
        mAdViewContainer.removeAllViews();
    }

}
