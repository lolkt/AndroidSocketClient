package com.vilyever.androidsocketclient;

import android.app.Application;



/**
 * App
 * AndroidSocketClient <com.vilyever.androidsocketclient>
 * Created by vilyever on 2016/3/30.
 * Feature:
 */
public class App extends Application {
    final App self = this;

    
    /* Constructors */
    
    
    /* Public Methods */
    
    
    /* Properties */
    
    
    /* Overrides */

    @Override
    public void onCreate() {
        super.onCreate();


        ContextHolder.init(this);

    }

    /* Delegates */
     
     
    /* Private Methods */
    
}