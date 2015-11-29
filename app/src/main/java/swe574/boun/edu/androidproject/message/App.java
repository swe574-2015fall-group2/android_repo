package swe574.boun.edu.androidproject.message;

import android.app.Application;
import android.os.Handler;

import swe574.boun.edu.androidproject.tools.NativeLoader;

public class App extends Application {

    public static volatile Handler applicationHandler = null;
    private static App Instance;

    public static App getInstance() {
        return Instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Instance = this;

        applicationHandler = new Handler(getInstance().getMainLooper());

        NativeLoader.initNativeLibs(App.getInstance());

    }
}
