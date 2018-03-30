package com.napchatalarms.napchatalarmsandroid.utility;

import android.content.Context;

import com.napchatalarms.napchatalarmsandroid.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bbest on 30/03/18.
 */

public class MusicBox {
    private static MusicBox instance = null;
    private Map<String, String> UriToNameMap = new HashMap<>();
    private Map<String, String> NameToUriMap = new HashMap<>();

    protected MusicBox(Context context) {
        getUriToNameMap().put("android.resource://" + context.getPackageName() + "/" + R.raw.bamboo_forest, context.getString(R.string.bamboo));
        getUriToNameMap().put( "android.resource://" + context.getPackageName() + "/" + R.raw.alley_cat, context.getString(R.string.alleycat));
        getUriToNameMap().put("android.resource://" + context.getPackageName() + "/" + R.raw.steampunk, context.getString(R.string.steampunk));

        for(Map.Entry<String,String> entry : getUriToNameMap().entrySet()){
            getNameToUriMap().put(entry.getValue(),entry.getKey());
        }
    }

    public static MusicBox getInstance(Context context) {
        if (instance == null) {
            instance = new MusicBox(context);
        }
        return instance;
    }


    public String getUri(String name) {
        return getNameToUriMap().get(name);
    }

    public String getName(String uri) {
        return getUriToNameMap().get(uri);
    }

    private Map<String, String> getUriToNameMap() {
        return UriToNameMap;
    }

    private Map<String, String> getNameToUriMap() {
        return NameToUriMap;
    }

    public static String getNameFromUri(Context context, String uri){
        return MusicBox.getInstance(context).getName(uri);
    }

    public static String getUriFromName(Context context, String name){
        return MusicBox.getInstance(context).getUri(name);
    }
}
