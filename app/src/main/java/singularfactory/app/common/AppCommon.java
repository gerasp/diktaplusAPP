package singularfactory.app.common;

import android.support.multidex.MultiDexApplication;

import singularfactory.app.R;
import singularfactory.app.models.Model;
import singularfactory.app.models.User;
import singularfactory.app.presenters.PresenterSplash;
import singularfactory.app.presenters.PresenterTexts;
import singularfactory.app.presenters.PresenterUser;

public class AppCommon extends MultiDexApplication {

    private static AppCommon singleton;
    public static AppCommon getInstance() { return singleton; }

    private final String baseURL = "http://192.168.1.138:8000/api/";
    private User user;


    @Override
    public void onCreate() {
        super.onCreate();
        presenterSplash = null;
        singleton       = this;
    }

    /************/
    /** MODELS **/
    /************/

    private Model model;
    public Model getModel(){
        if (model == null) model = new Model();
        return model;
    }

    /****************/
    /** PRESENTERS **/
    /****************/

    private PresenterSplash presenterSplash;
    public PresenterSplash getPresenterSplash() {
        if (presenterSplash == null) presenterSplash = new PresenterSplash();
        return presenterSplash;
    }

    private PresenterTexts presenterTexts;
    public PresenterTexts getPresenterTexts() {
        if (presenterTexts == null) presenterTexts = new PresenterTexts();
        return presenterTexts;
    }

    private PresenterUser presenterUser;
    public PresenterUser getPresenterUser() {
        if (presenterUser == null) presenterUser = new PresenterUser();
        return presenterUser;
    }


    /***************/
    /** UTILITIES **/
    /***************/

    public Utils getUtils() {
        return Utils.getInstance();
    }

    public static class Tags {

        /** SharedPreferences **/
        public final static String SHARED_API_VERSION   = getInstance().getString(R.string.app_name) + "_API_VERSION";
        public final static String SHARED_ACCESS_TOKEN  = getInstance().getString(R.string.app_name) + "_ACCESS_TOKEN";
        public final static String SHARED_MEDIA_URL     = getInstance().getString(R.string.app_name) + "_MEDIA_URL";
        public final static String SHARED_REFRESH_TOKEN = getInstance().getString(R.string.app_name) + "_REFRESH_TOKEN";
        public final static String SHARED_EMAIL         = getInstance().getString(R.string.app_name) + "_EMAIL";

        /** WebService **/
        public final static String WS_GET_GLOBAL_CONFIG = "WS_GET_GLOBAL_CONFIG";
    }


    /********************/
    /** PUBLIC METHODS **/
    /********************/
    public String getBaseURL() {
        return baseURL;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


}