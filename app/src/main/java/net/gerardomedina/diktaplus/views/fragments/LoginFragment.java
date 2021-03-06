package net.gerardomedina.diktaplus.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import net.gerardomedina.diktaplus.R;
import net.gerardomedina.diktaplus.models.User;
import net.gerardomedina.diktaplus.views.activities.MainActivity;

public class LoginFragment extends BaseFragment {

    EditText usernameOrEmail;
    EditText password;
    String savedEmail;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        usernameOrEmail = (EditText)view.findViewById(R.id.username_input);
        password = (EditText)view.findViewById(R.id.password_input);
        if (savedEmail!=null) usernameOrEmail.setText(savedEmail);

        return view;
    }

    public void getOauthToken() throws JSONException {
        String [] params = {usernameOrEmail.getText().toString().trim(), password.getText().toString()};
        appCommon.getPresenterUser().getOauthToken(
                this,
                "Get Oauth token",
                Request.Method.POST,
                appCommon.getOauthURL(),
                "Trying to get OAuth token...",
                params);
    }

    public void getUser() throws JSONException {
        String usernameOrEmailString = usernameOrEmail.getText().toString().trim();
        appCommon.getPresenterUser().loginUser(
                this,
                "Login user",
                Request.Method.POST,
                appCommon.getBaseURL()+"users/login",
                "Trying to log in...",
                usernameOrEmailString);
    }

    public void setUser(JSONObject userJson) {
        try {
            appCommon.setUser(new User(userJson.getInt("id"),
                    userJson.getString("email"),
                    userJson.getString("username"),
                    userJson.getString("country"),
                    userJson.getInt("total_score"),
                    userJson.getInt("level")));
            appCommon.getUtils().sharedSetValue(getContext(),"id",userJson.getInt("id"));
        } catch (JSONException e) {
            Log.e(TAG,"Error parsing received JSON");
        }
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        getActivity().finish();
    }
}
