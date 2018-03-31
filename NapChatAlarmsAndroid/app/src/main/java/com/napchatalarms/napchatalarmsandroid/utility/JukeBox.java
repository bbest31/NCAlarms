package com.napchatalarms.napchatalarmsandroid.utility;

import android.content.Context;

import com.napchatalarms.napchatalarmsandroid.R;

import java.util.HashMap;
import java.util.Map;

/** JukeBox holds the mapping of custom ringtones uri's to names and vice-versa.
 * Created by bbest on 30/03/18.
 */

public class JukeBox {
    private static JukeBox instance = null;
    private final Map<String, String> UriToNameMap = new HashMap<>();
    private final Map<String, String> NameToUriMap = new HashMap<>();

    private JukeBox(Context context) {
        getUriToNameMap().put("android.resource://" + context.getPackageName() + "/" + R.raw.bamboo_forest, context.getString(R.string.bamboo));
        getUriToNameMap().put( "android.resource://" + context.getPackageName() + "/" + R.raw.alley_cat, context.getString(R.string.alleycat));
        getUriToNameMap().put("android.resource://" + context.getPackageName() + "/" + R.raw.steampunk, context.getString(R.string.steampunk));

        for(Map.Entry<String,String> entry : getUriToNameMap().entrySet()){
            getNameToUriMap().put(entry.getValue(),entry.getKey());
        }
    }

    private static JukeBox getInstance(Context context) {
        if (instance == null) {
            instance = new JukeBox(context);
        }
        return instance;
    }


    private String getUri(String name) {
        return getNameToUriMap().get(name);
    }

    private String getName(String uri) {
        return getUriToNameMap().get(uri);
    }

    private Map<String, String> getUriToNameMap() {
        return UriToNameMap;
    }

    private Map<String, String> getNameToUriMap() {
        return NameToUriMap;
    }

    public static String getNameFromUri(Context context, String uri){
        return JukeBox.getInstance(context).getName(uri);
    }

    public static String getUriFromName(Context context, String name){
        return JukeBox.getInstance(context).getUri(name);
    }
}
