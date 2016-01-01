package swe574.boun.edu.androidproject.network;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

/**
 * Created by Jongaros on 1/1/2016.
 */
public class RequestQueueBuilder {
    private static final int MAX_SERIAL_THREAD_POOL_SIZE = 1;
    private static final int MAX_CACHE_SIZE = 2 * 1024 * 1024;

    public static RequestQueue preapareSerialQueue(Context context) {
        RequestQueue requestQueue = null;
        Cache cache = new DiskBasedCache(context.getCacheDir(), MAX_CACHE_SIZE);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network, MAX_SERIAL_THREAD_POOL_SIZE);
        return requestQueue;
    }
}
