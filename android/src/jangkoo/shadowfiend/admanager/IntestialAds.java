package jangkoo.shadowfiend.admanager;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;

public class IntestialAds {
	private InterstitialAd interstitialAd;
    private Handler    mHandler;
	public IntestialAds(Context pContext){
		interstitialAd = new InterstitialAd(pContext);
		interstitialAd.setAdUnitId("ca-app-pub-8010283700967425/9048284593");
	    mHandler        = new Handler();
	    //Set the IAP Listener.
	    interstitialAd.setInAppPurchaseListener((InAppPurchaseListener) pContext);
	    // Set the AdListener.
	    interstitialAd.setAdListener(new AdListener() {
	      @Override
	      public void onAdLoaded() {
	        //Log.d(LOG_TAG, "onAdLoaded");
	        //Toast.makeText(MainActivity.this, "onAdLoaded", Toast.LENGTH_SHORT).show();

	      }

	      @Override
	      public void onAdFailedToLoad(int errorCode) {
	      //  String message = String.format("onAdFailedToLoad (%s)", getErrorReason(errorCode));
	        //Log.d(LOG_TAG, message);
	        //Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
	        // Refresh it ourselves...
	        mHandler.removeCallbacks(null);
	        mHandler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	loadInterstitial();
	            }
	        }, 5000);

	      }
	    });
	    loadInterstitial();
	}
   
 public void loadInterstitial() {
	    AdRequest adRequest = new AdRequest.Builder().build();
	    interstitialAd.loadAd(adRequest);
	  }

	  /** Called when the Show Interstitial button is clicked. */
 public void showInterstitial(View unusedView) {
	    // Disable the show button until another interstitial is loaded.


	    if (interstitialAd.isLoaded()) {
	      interstitialAd.show();
	    } else {
	      loadInterstitial();
	    }
	  }
 public void showInterstitial(){
	    if (interstitialAd.isLoaded()) {
		      interstitialAd.show();
		    } else {
		    	loadInterstitial();	
		    } 
 }

}
