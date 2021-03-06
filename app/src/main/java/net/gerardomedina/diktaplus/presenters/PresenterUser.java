package net.gerardomedina.diktaplus.presenters;

import android.support.v4.app.Fragment;

import net.gerardomedina.diktaplus.common.AppCommon;
import net.gerardomedina.diktaplus.views.activities.SplashActivity;
import net.gerardomedina.diktaplus.views.fragments.BaseFragment;
import net.gerardomedina.diktaplus.views.fragments.FriendFragment;
import net.gerardomedina.diktaplus.views.fragments.LoginFragment;
import net.gerardomedina.diktaplus.views.fragments.RankingFragment;
import net.gerardomedina.diktaplus.views.fragments.SettingsFragment;
import net.gerardomedina.diktaplus.views.fragments.SignupFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PresenterUser {
    private static final String TAG = PresenterUser.class.getSimpleName();
    private AppCommon appCommon     = AppCommon.getInstance();

    /*******************/
    /**** API CALLS ****/
    /*******************/
    public void getOauthToken(final Object object, final String tagRequest, int verb, String url, String dialogMessage, String [] jsonParams) throws JSONException{
        appCommon.getOauthModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,true,jsonParams);
    }

    public void loginUser(final Object object, final String tagRequest, int verb, String url, String dialogMessage, String usernameOrEmail) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email",usernameOrEmail);
        jsonObject.put("username",usernameOrEmail);
        appCommon.getModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,true,jsonObject.toString());
    }

    public void registerUser(final Object object, final String tagRequest, int verb, String url, String dialogMessage, String [] jsonParams) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username",jsonParams[0]);
        jsonObject.put("email",jsonParams[1]);
        jsonObject.put("password",jsonParams[2]);
        jsonObject.put("country",jsonParams[3]);
        appCommon.getModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,false,jsonObject.toString());
    }

    public void getUserInfo(final Object object, final String tagRequest, int verb, String url, String dialogMessage) {
        appCommon.getModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,false,null);
    }

    public void putUser(final Object object, final String tagRequest, int verb, String url, String dialogMessage, String [] jsonParams) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email",jsonParams[0]);
        jsonObject.put("country",jsonParams[1]);
        jsonObject.put("password",jsonParams[2]);
        appCommon.getModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,false,jsonObject.toString());
    }

    public void deleteUser(final Object object, final String tagRequest, int verb, String url, String dialogMessage) {
        appCommon.getModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,false,null);
    }

    public void getRanking(final Object object, final String tagRequest, int verb, String url, String dialogMessage) {
        appCommon.getModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,false,null);
    }

    public void getUsersByUsername(final Object object, final String tagRequest, int verb, String url, String dialogMessage) {
        appCommon.getModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,false,null);
    }

    public void getFriends(final Object object, final String tagRequest, int verb, String url, String dialogMessage) {
        appCommon.getModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,false,null);
    }

    public void getFriendInfo(final Object object, final String tagRequest, int verb, String url, String dialogMessage) {
        appCommon.getModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,false,null);
    }

    public void makeFriends(final Object object, final String tagRequest, int verb, String url, String dialogMessage) {
        appCommon.getModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,false,null);
    }

    public void deleteFriends(final Object object, final String tagRequest, int verb, String url, String dialogMessage) {
        appCommon.getModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,false,null);
    }

    /*******************/
    /** API RESPONSES **/
    /*******************/
    public void getOauthTokenResponse(Object object, String result)  {
        try {
            JSONObject json = new JSONObject(result);
            appCommon.getUtils().sharedSetValue(((LoginFragment) object).getContext(), "access_token", json.get("access_token"));
            LoginFragment loginFragment = (LoginFragment) object;
            loginFragment.getUser();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loginUserResponse(Object object, JSONObject user) throws JSONException {
        LoginFragment loginFragment = (LoginFragment) object;
        loginFragment.setUser(user);
    }

    public void registerUserResponse(Object object, JSONObject json) {
        SignupFragment signupFragment = (SignupFragment) object;
        signupFragment.registerUserSuccess();
    }

    public void getUserInfoResponse(Object object, JSONObject user) {
        SplashActivity splashActivity = (SplashActivity) object;
        splashActivity.setUserInfo(user);
    }

    public void putUserResponse(Object object, JSONObject json) {
        SettingsFragment settingsFragment = (SettingsFragment) object;
        settingsFragment.updateAccountSuccess();
    }

    public void deleteUserResponse(Object object, JSONObject user) {
        SettingsFragment settingsFragment = (SettingsFragment) object;
        settingsFragment.deleteAccountSuccess();
    }

    public void getRankingResponse(Object object, JSONArray users) throws JSONException {
        RankingFragment rankingFragment = (RankingFragment) object;
        rankingFragment.setRanking(users);
    }

    public void getUsersByUsernameResponse(Object object, JSONArray users) throws JSONException {
        FriendFragment friendFragment = (FriendFragment) object;
        friendFragment.setUsersByUsername(users);
    }

    public void getFriendsResponse(Object object, JSONArray users) throws JSONException {
        FriendFragment friendFragment = (FriendFragment) object;
        friendFragment.setFriends(users);
    }

    public void getFriendInfoResponse(Object object, JSONObject user) {
        FriendFragment friendFragment = (FriendFragment) object;
        friendFragment.setFriendInfo(user);
    }

    public void makeFriendsResponse(Object object, JSONArray users) throws JSONException {
        FriendFragment friendFragment = (FriendFragment) object;
        friendFragment.getFriends();
    }

    public void deleteFriendsResponse(Object object, JSONArray users) throws JSONException {
        FriendFragment friendFragment = (FriendFragment) object;
        friendFragment.getFriends();
    }

    /** Response error **/
    public void responseError(Object object, String message) {
        if (!message.equals("")) {
            if (object instanceof SettingsFragment) {
                ((SettingsFragment) object).showToast(message);
            } else if (object instanceof Fragment) {
                BaseFragment baseFragment = (BaseFragment) object;
                baseFragment.showDialog(message);
            } else if (object instanceof SplashActivity){
                SplashActivity splashActivity = (SplashActivity) object;
                appCommon.getUtils().sharedRemoveValue(splashActivity,"id");
                splashActivity.showSingleAlertWithReflection(splashActivity,splashActivity,"Your credentials are not valid anymore, please log in again","launchLoginActivity");
            }
        }
    }
}