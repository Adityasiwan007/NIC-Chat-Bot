package tcs.ril.storebot.model;


import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    public static String IP = "IP";
    public static String APP = "Spotlight";
    public static String SERVER = "192.168.43.200";
    public static String USERNAME = "USERNAME";
    public static String PASSWORD = "PASSWORD";
    public static String ACCESS_TOKEN = "ACCESS_TOKEN";



    public static String PROFILE_URL="";
    public static String NFC_CART = "NFC_CART";
    public static String IN_STORE = "IN_STORE";


    SharedPreferences sharedpreferences;
    private Context _context;

    public PreferenceManager(Context con) {
        this._context = con;
    }


    public void setIp(String ips, String ip) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(IP, Context.MODE_PRIVATE).edit();
        editor.putString(ips, ip);
        editor.commit();
    }

    public String getIp(String ips) {
        SharedPreferences sharedPrefs = _context.getSharedPreferences(IP, Context.MODE_PRIVATE);
        String ip = sharedPrefs.getString(ips, "192.168.43.200");
        return ip;
    }

    public String getUserName(String ips) {
        SharedPreferences sharedPrefs = _context.getSharedPreferences(USERNAME, Context.MODE_PRIVATE);
        String ip = sharedPrefs.getString(ips, "chucky");
        return ip;
    }

    public void setUserName(String ips, String ip) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(USERNAME, Context.MODE_PRIVATE).edit();
        editor.putString(ips, ip);
        editor.commit();
    }

    public String getPassword(String ips) {
        SharedPreferences sharedPrefs = _context.getSharedPreferences(PASSWORD, Context.MODE_PRIVATE);
        String ip = sharedPrefs.getString(ips, "chuck");
        return ip;
    }

    public void setPassword(String ips, String ip) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PASSWORD, Context.MODE_PRIVATE).edit();
        editor.putString(ips, ip);
        editor.commit();
    }

    public String getAccessToken(String ips) {
        SharedPreferences sharedPrefs = _context.getSharedPreferences(ACCESS_TOKEN, Context.MODE_PRIVATE);
        String ip = sharedPrefs.getString(ips, "LOGIN");
        return ip;
    }

    public void setAccessToken(String ips, String ip) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(ACCESS_TOKEN, Context.MODE_PRIVATE).edit();
        editor.putString(ips, ip);
        editor.commit();
    }

    public String getNfc(String ips) {
        SharedPreferences sharedPrefs = _context.getSharedPreferences(NFC_CART, Context.MODE_PRIVATE);
        String ip = sharedPrefs.getString(ips, "EMPTY");
        return ip;
    }

    public void setNfc(String ips, String ip) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(NFC_CART, Context.MODE_PRIVATE).edit();
        editor.putString(ips, ip);
        editor.commit();
    }


    public boolean getInStore(String inStoreString) {
        SharedPreferences sharedPrefs = _context.getSharedPreferences(IN_STORE, Context.MODE_PRIVATE);
        boolean inStore = sharedPrefs.getBoolean(inStoreString, false);
        return inStore;
    }

    public void setInStore(String inStoreString, boolean inStore) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(IN_STORE, Context.MODE_PRIVATE).edit();
        editor.putBoolean(inStoreString, inStore);
        editor.commit();
    }

    public  String getProfileUrl(String ips) {
        SharedPreferences sharedPrefs = _context.getSharedPreferences(IP, Context.MODE_PRIVATE);
        String url = sharedPrefs.getString(ips, "url");
        return url;
    }

    public  void setProfileUrl(String ips,String ip) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(IP, Context.MODE_PRIVATE).edit();
        editor.putString(ips, ip);
        editor.commit();
    }


}