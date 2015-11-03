package jangkoo.shadowfiend.android;

import android.app.Activity;
import android.os.Bundle;

public class Splash extends Activity {
    
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.splash); 
        
   } 
    
   @Override
   protected void onDestroy() {
        
       super.onDestroy();
        
   }
}
