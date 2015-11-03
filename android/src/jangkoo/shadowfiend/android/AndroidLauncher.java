package jangkoo.shadowfiend.android;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.json.JSONObject;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import com.android.vending.billing.IInAppBillingService;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.purchase.InAppPurchase;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import facebook.FacebookManager;

import jangkoo.game.model.Item;
import jangkoo.game.model.ShadowFiend;
import jangkoo.game.screen.LeaderBoard;
import jangkoo.game.shadowfiend.ShadowFiendGame;
import jangkoo.shadowfiend.android.R;
import jangkoo.shadowfiend.admanager.AdViewHelper;
import jangkoo.shadowfiend.admanager.IntestialAds;

public class AndroidLauncher extends AndroidApplication implements 
GameHelper.GameHelperListener,OnClickListener,InAppPurchaseListener,LeaderBoard{
	 private IntestialAds interstitialAd;
	 AdViewHelper mAdViewHelper;
	 private LayoutInflater inflater;
	 protected GameHelper mHelper;
	 ShadowFiend shadowfiend;
	    public static final int CLIENT_GAMES = GameHelper.CLIENT_GAMES;
	    public static final int CLIENT_APPSTATE = GameHelper.CLIENT_APPSTATE;
	    public static final int CLIENT_PLUS = GameHelper.CLIENT_PLUS;
	    public static final int CLIENT_ALL = GameHelper.CLIENT_ALL;
	    public static final String PREFS_NAME = "ShadowFiend";

	    // Requested clients. By default, that's just the games client.
	    protected int mRequestedClients = CLIENT_GAMES;
	    private final static String TAG = "ShadowFiend";
		private static final String MMR_LEADERBOARD_ID = "CgkIkuCni_kIEAIQAQ";
		private static final String WIN_LEADERBOARD_ID = "CgkIkuCni_kIEAIQBw";
		private static final int REQUEST_ACHIEVEMENTS = 100;
		private int REQUEST_LEADERBOARD = 1;
	    protected boolean mDebugLog = false;
	    private boolean adsDisplayed = true;
	    AndroidApplicationConfiguration config;
	    View view;
	    IInAppBillingService mService;
	    FacebookManager fbManager;
	    ServiceConnection mServiceConn = new ServiceConnection() {
	       @Override
	       public void onServiceDisconnected(ComponentName name) {
	           mService = null;
	       }

	       @Override
	       public void onServiceConnected(ComponentName name, 
	          IBinder service) {
	           mService = IInAppBillingService.Stub.asInterface(service);
	       }


	    };
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
	       Thread background = new Thread() {
	            public void run() {
	                 
	                try {
	                    // Thread will sleep for 5 seconds
	                    sleep(5*1000);
	                     
	                    // After 5 seconds redirect to another intent
	                    Intent i=new Intent(getBaseContext(),Splash.class);
	                    startActivity(i);
	                     
	                    //Remove activity
	                    finish();
	                     
	                } catch (Exception e) {
	                 
	                }
	            }
	        };
	    background.start();
	    //fbManager = new FacebookManager(this);
		shadowfiend = new ShadowFiend(300,300,0,new ArrayList <Item>());
		interstitialAd = new IntestialAds(this);
	   /* if(getPreferences(MODE_PRIVATE).getString(PREFS_NAME, null)!=null){
	    	String connectionsJSONString = getPreferences(MODE_PRIVATE).getString(PREFS_NAME, null);
		    Type type = new TypeToken  <ShadowFiend> () {}.getType();
		    try{
		    	ShadowFiend data = new Gson().fromJson(connectionsJSONString, type);
		   
		    shadowfiend = data;}
		    catch(Exception e){}
		    shadowfiend.v2Position.x = 300;
		    shadowfiend.v2Position.y = 300;
		    }
		    */
		inflater = LayoutInflater.from(this);
		fbManager = new FacebookManager(inflater.getContext());
		config = new AndroidApplicationConfiguration();
		view = initializeForView(new ShadowFiendGame(this,shadowfiend),config);
        if (mHelper == null) {
            getGameHelper();
        }
        mHelper.setup(this);
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
        
        if(mService!=null)
        try {
			Bundle ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);
			int response = ownedItems.getInt("RESPONSE_CODE");
			if (response == 0) {
			   ArrayList<String> ownedSkus =
			      ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
			   ArrayList<String>  purchaseDataList =
			      ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
			   ArrayList<String>  signatureList =
			      ownedItems.getStringArrayList("INAPP_DATA_SIGNATURE");
			   String continuationToken = 
			      ownedItems.getString("INAPP_CONTINUATION_TOKEN");
			   Log.e("sku",ownedSkus + "");
			   for (int i = 0; i < purchaseDataList.size(); ++i) {
			      String purchaseData = purchaseDataList.get(i);
			      String signature = signatureList.get(i);
			      String sku = ownedSkus.get(i);
			      if(sku.equalsIgnoreCase("adremove")){
			    	  adsDisplayed = false;
			    	  break;
			      }
			      // do something with this purchase information
			      // e.g. display the updated list of products owned by user
			   } 

			   // if continuationToken != null, call getPurchases again 
			   // and pass in the token to retrieve more items
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void newGame(){
		/*
		setContentView(R.layout.main_screen);
		Button play = (Button) findViewById(R.id.play);
		play.setOnClickListener(this);
		Button buy = (Button) findViewById(R.id.buy);
		buy.setOnClickListener(this);
		
		Button devine = (Button) findViewById(R.id.devinerapier);
		devine.setOnClickListener(this);
		Button adsremove = (Button) findViewById(R.id.adsremove);
		adsremove.setOnClickListener(this);
		*/
	}
	public void initGame(){
		setContentView(R.layout.main);
		LinearLayout layout = (LinearLayout)findViewById(R.id.game);
		if(view.getParent()!=null)
			((ViewManager)view.getParent()).removeView(view);
		layout.addView(view);
		LinearLayout ads = (LinearLayout)findViewById(R.id.ads); 
		if(adsDisplayed){
		    mAdViewHelper = new AdViewHelper(this, ads);
		    mAdViewHelper.refresh();
			//interstitialAd.showInterstitial(layout);
		}
	}
	@Override
	public void onInAppPurchaseRequested(InAppPurchase arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){/*
		case R.id.play:
			initGame();
	    	try{
	    		Games.Leaderboards.submitScore(getApiClient(), MMR_LEADERBOARD_ID, (long)1);
	    		}
	    		catch (Exception e){
	    			//Toast.makeText(AndroidLauncher.this, "No Connection", Toast.LENGTH_SHORT).show();
	    		}
			break;
		case R.id.buy:
			TableLayout tblo = (TableLayout) findViewById(R.id.itemList);
			tblo.setVisibility(View.VISIBLE);
			ArrayList<String> skuList = new ArrayList<String> ();
			skuList.add("divinerapier");
			Bundle querySkus = new Bundle();
			querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
			try {
				Bundle skuDetails = mService.getSkuDetails(3, 
						   getPackageName(), "inapp", querySkus);
				int response = skuDetails.getInt("RESPONSE_CODE");
				if (response == 0) {
				   ArrayList<String> responseList
				      = skuDetails.getStringArrayList("DETAILS_LIST");
				   
				   for (String thisResponse : responseList) {
				      JSONObject object = new JSONObject(thisResponse);
				      String sku = object.getString("productId");
				      String price = object.getString("price");
				      if (sku.equals("divinerapier")) {
				    	  TextView tv = (TextView) findViewById(R.id.devinerapierPrice);
				    	  tv.setText(price);
				    	  }
				      if (sku.equals("heart")) {
				    	  TextView tv = (TextView) findViewById(R.id.heartPrice);
				    	  tv.setText(price);
				    	  }
				   }
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.devinerapier:
			try {
				Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
						   "divinerapier", "inapp", "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
				PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
				startIntentSenderForResult(pendingIntent.getIntentSender(),
						   1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
						   Integer.valueOf(0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.adsremove:
			try {
				Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
						   "adremove", "inapp", "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
				PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
				startIntentSenderForResult(pendingIntent.getIntentSender(),
						   1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
						   Integer.valueOf(0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.heart:
			try {
				Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
						   "heart", "inapp", "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
				PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
				startIntentSenderForResult(pendingIntent.getIntentSender(),
						   1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
						   Integer.valueOf(0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			*/
		}
		
	}
    @Override
    protected void onStart() {
        super.onStart();
        mHelper.onStart(this);
    }
	@Override
	protected void onResume() {
	    super.onResume();

	    if (mAdViewHelper != null) {
	    	mAdViewHelper.resume();
	      }
	    
	}
    @Override
    protected void onStop() {
        super.onStop();
        mHelper.onStop();
    }

    @Override
    protected void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
        mHelper.onActivityResult(request, response, data);
    }
    public GameHelper getGameHelper() {
        if (mHelper == null) {
            mHelper = new GameHelper(this, mRequestedClients);
            mHelper.enableDebugLog(mDebugLog);
        }
        return mHelper;
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);

	}

	@Override
	public void onPause() {
	    super.onPause();
	    if (mAdViewHelper != null) {
	    	mAdViewHelper.pause();
	      }
	}

	@Override
	public void onDestroy() {
	    mAdViewHelper.destroy();
	    super.onDestroy();
	    if (mService != null) {
	        unbindService(mServiceConn);
	    } 
	}
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		initGame();
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		initGame();
	}
    protected GoogleApiClient getApiClient() {
        return mHelper.getApiClient();
    }
	@Override
	public void submitScore(String user, long score) {
		// TODO Auto-generated method stub
    	try{
    		Games.Leaderboards.submitScore(getApiClient(), MMR_LEADERBOARD_ID, score);
    		}
    		catch (Exception e){
    			//Toast.makeText(AndroidLauncher.this, "No Connection", Toast.LENGTH_SHORT).show();
    		}
		
	}
	@Override
	public void toMainMenu() {
		// TODO Auto-generated method stub
		AndroidLauncher.this.runOnUiThread(new Runnable() {
		     @Override
		     public void run() {
		    	 newGame();
		     }
		});
		
	}
	@Override
	public void writeToMemory() {
    	/*SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        String connectionsJSONString = new Gson().toJson(shadowfiend);
        editor.putString(PREFS_NAME, connectionsJSONString);
        editor.commit();
		*/
	}
	@Override
	public void viewScore() {
		// TODO Auto-generated method stub
		   try{
		   startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(),
				   MMR_LEADERBOARD_ID), REQUEST_LEADERBOARD);
		   }catch (Exception e){
			   //Toast.makeText(AndroidLauncher.this, "No Connection", Toast.LENGTH_SHORT).show();
			   Log.e(TAG,e.getMessage());
		   }
		
	}
	@Override
	public void rate() {
		// TODO Auto-generated method stub
	       Intent intent = new Intent(Intent.ACTION_VIEW);
	       //Try Google play
	       intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=jangkoo.shadowfiend.android"));
	       inflater.getContext().startActivity(intent);
		
	}
	@Override
	public void buy() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void adremove() {
		try {
			Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
					   "adremove", "inapp", "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
			PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
			startIntentSenderForResult(pendingIntent.getIntentSender(),
					   1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
					   Integer.valueOf(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void unlockAchivement(String id) {
		// TODO Auto-generated method stub
		try{
		Games.Achievements.unlock(getApiClient(), id);
		}catch (Exception e){
			   //Toast.makeText(AndroidLauncher.this, "No Connection", Toast.LENGTH_SHORT).show();
			   Log.e(TAG,e.getMessage());
		   }
	}
	@Override
	public void viewAchivement() {
		// TODO Auto-generated method stub
		try{
		startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()), REQUEST_ACHIEVEMENTS);
		}catch (Exception e){
			   //Toast.makeText(AndroidLauncher.this, "No Connection", Toast.LENGTH_SHORT).show();
			   Log.e(TAG,e.getMessage());
		   }
		
	}
	@Override
	public void buyItem(String itemName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void donate() {
		// TODO Auto-generated method stub
		try {
			Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
					   "donate", "inapp", "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
			PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
			startIntentSenderForResult(pendingIntent.getIntentSender(),
					   1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
					   Integer.valueOf(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void showads() {
		AndroidLauncher.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
				interstitialAd.showInterstitial();
           }
		});

	}
	@Override
	public void onBackPressed() {
		interstitialAd.showInterstitial();
	    return;
	}
	@Override
	public void subWinningScore(String user, long score) {
		// TODO Auto-generated method stub
    	try{
    		Games.Leaderboards.submitScore(getApiClient(), WIN_LEADERBOARD_ID, score);
    		}
    		catch (Exception e){
    			//Toast.makeText(AndroidLauncher.this, "No Connection", Toast.LENGTH_SHORT).show();
    		}
		
	}
	@Override
	public void viewWinningScore() {
		// TODO Auto-generated method stub
		   try{
		   startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(),
				   WIN_LEADERBOARD_ID), REQUEST_LEADERBOARD);
		   }catch (Exception e){
			   //Toast.makeText(AndroidLauncher.this, "No Connection", Toast.LENGTH_SHORT).show();
			   Log.e(TAG,e.getMessage());
		   }
	}
	@Override
	public void shareFB() {
		// TODO Auto-generated method stub
        fbManager.share();
	}
}
