package facebook;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.*;
import com.facebook.share.widget.ShareDialog;

public class FacebookManager{
    Context context;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    public FacebookManager(Context c){
        context = c;
        FacebookSdk.sdkInitialize(c);
        shareDialog = new ShareDialog((Activity)c);

    }
    public void share() {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle("Shadow Fiend Solo")
                .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=jangkoo.shadowfiend.android"))
                .setContentDescription("I am alone here, let's come play solo SF only mid :)")
                .build();
        shareDialog.show((Activity)context, content);
    }
}