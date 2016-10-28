package singularfactory.app.presenters;

import org.json.JSONArray;
import org.json.JSONException;

import singularfactory.app.common.AppCommon;
import singularfactory.app.views.fragments.TextFragment;

public class PresenterText {
    private static final String TAG = PresenterText.class.getSimpleName();
    private AppCommon appCommon     = AppCommon.getInstance();

    /*******************/
    /**** API CALLS ****/
    /*******************/
    public void getTexts(final Object object, final String tagRequest, int verb, String url, String dialogMessage){
        appCommon.getModel().volleyAsynctask(object,tagRequest,verb,url,dialogMessage,true,null);
    }

    /*******************/
    /** API RESPONSES **/
    /*******************/
    public void getTextsResponse(Object object, JSONArray texts) throws JSONException {
        TextFragment textFragment = (TextFragment) object;
        textFragment.setTextsList(texts);

    }

    /** Response error **/
    public void responseError(Object object, String message) {
        TextFragment textFragment = (TextFragment) object;
        textFragment.onErrorGetTexts(message);
    }

}