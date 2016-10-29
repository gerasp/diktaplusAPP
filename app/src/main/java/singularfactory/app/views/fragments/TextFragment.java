package singularfactory.app.views.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import singularfactory.app.R;
import singularfactory.app.models.Text;

public class TextFragment extends BaseFragment {
    View view;
    ExpandableListView textsList;
    ExpandableListAdapter textsListAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    JSONArray receivedList;
    Text selectedText;

    ImageButton languageLeft, languageRight, difficultyLeft, difficultyRight;

    final String[] languages = {"EN","ES","DE","FR","IT"};
    final int[] flags = {R.drawable.flag_gb,
            R.drawable.flag_es,
            R.drawable.flag_de,
            R.drawable.flag_fr,
            R.drawable.flag_it,
    };
    Locale[] languagesLocales;
    int selectedLanguage;
    TextView languageLabel;
    ImageView languageFlag;

    final String[] difficulties = {"Easy", "Medium", "Hard"};
    int selectedDifficulty;
    TextView difficultyLabel;

    public TextFragment() {
        // Required empty public constructor
    }

    public Text getSelectedText() {
        return selectedText;
    }

    private void getTextList() {
        appCommon.getPresenterText().getTexts(
                this,
                "Get texts",
                Request.Method.GET,
                appCommon.getBaseURL()+"texts/"+languages[selectedLanguage]+"/"+difficulties[selectedDifficulty],
                "Loading texts list...");
    }


    public void setTextsList(JSONArray texts) throws JSONException{
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        receivedList = texts;
        List<String> listItemInfo;
        for (int i = 0; i < texts.length(); i++) {
            listItemInfo = new ArrayList<String>();
            listDataHeader.add(texts.getJSONObject(i).getString("title"));
            listItemInfo.add("Best score: 0");
            listDataChild.put(listDataHeader.get(i), listItemInfo);
        }

        textsListAdapter = new ExpandableListAdapter(this.getContext(),this, listDataHeader, listDataChild);
        textsList.setAdapter(textsListAdapter);
    }

    public void onErrorGetTexts (String message) {
        textsList.setAdapter((BaseExpandableListAdapter)null);
        showToast(message);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_text, container, false);

        textsList = (ExpandableListView)view.findViewById(R.id.textsList);
        getTextList();

        // Default difficulty: "Medium"
        difficultyLabel = (TextView)view.findViewById(R.id.difficulty_label);
        selectedDifficulty = 1;
        updateDifficultyLabel();

        // Default language: "English"
        languageLabel = (TextView)view.findViewById(R.id.language_label);
        languageFlag = (ImageView)view.findViewById(R.id.language_flag);
        languagesLocales = new Locale[languages.length];
        for (int i = 0; i < languages.length; i++) {languagesLocales[i] = new Locale(languages[i]);}
        selectedLanguage = 0;
        updateLanguageLabel();

        // Switch language and difficulty buttons
        languageLeft = (ImageButton)view.findViewById(R.id.language_left);
        languageRight = (ImageButton)view.findViewById(R.id.language_right);
        difficultyLeft = (ImageButton)view.findViewById(R.id.difficulty_left);
        difficultyRight = (ImageButton)view.findViewById(R.id.difficulty_right);

        // Click listeners for the buttons
        languageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedLanguage == 0) selectedLanguage = languages.length-1;
                else selectedLanguage--;
                updateLanguageLabel();
                getTextList();
            }
        });
        languageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedLanguage == languages.length-1) selectedLanguage = 0;
                else selectedLanguage++;
                languageLabel.setText(toProperCase(languagesLocales[selectedLanguage].getDisplayName()));
                updateLanguageLabel();
                getTextList();
            }
        });
        difficultyLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDifficulty == 0) selectedDifficulty = difficulties.length-1;
                else selectedDifficulty--;
                updateDifficultyLabel();
                getTextList();
            }
        });
        difficultyRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDifficulty == difficulties.length-1) selectedDifficulty = 0;
                else selectedDifficulty++;
                updateDifficultyLabel();
                getTextList();
            }
        });

        return view;
    }

    private void updateLanguageLabel() {
        languageLabel.setText(toProperCase(languagesLocales[selectedLanguage].getDisplayName()));
        languageFlag.setImageResource(flags[selectedLanguage]);
    }

    private void updateDifficultyLabel() {
        switch (selectedDifficulty) {
            case 0:
                difficultyLabel.setText(R.string.easy);
                difficultyLabel.setTextColor(Color.GREEN);
                break;
            case 1:
                difficultyLabel.setText(R.string.medium);
                difficultyLabel.setTextColor(Color.YELLOW);
                break;
            case 2:
                difficultyLabel.setText(R.string.hard);
                difficultyLabel.setTextColor(Color.RED);
                break;
            default:break;
        }
    }

    public void getBestScore() throws JSONException {
        appCommon.getPresenterGame().getBestScore(
                this,
                "Get best score",
                Request.Method.GET,
                appCommon.getBaseURL()+"games/"+appCommon.getUser().getId()+"/"+selectedText.getId(),
                "Loading best score..");
    }

    public void setBestScore(int bestScore) {
        selectedText.setBestScore(bestScore);
        TextView bestScoreLabel = (TextView)view.findViewById(R.id.best_score_label);
        bestScoreLabel.setText(getActivity().getApplicationContext().getString(R.string.best_score, bestScore));
    }

    public void getTextContent(int groupPosition) throws JSONException {
        appCommon.getPresenterText().getTextContent(
                this,
                "Get text content",
                Request.Method.GET,
                appCommon.getBaseURL()+"texts/"+receivedList.getJSONObject(groupPosition).getInt("id"),
                "Loading text content..");
    }

    public void setTextContent(JSONObject text) {
        try {
            selectedText = new Text(text.getInt("id"),
                    text.getString("title"),
                    text.getString("content"),
                    text.getString("language"),
                    text.getString("difficulty"));
            getBestScore();
        } catch (JSONException e) {
            Log.e(TAG,"Error parsing received JSON");
        }
    }

    class ExpandableListAdapter extends BaseExpandableListAdapter {

        private TextFragment textFragment;
        private Context context;
        private List<String> listDataHeader;
        private HashMap<String, List<String>> listDataChild;

        public ExpandableListAdapter(Context context, TextFragment textFragment, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this.context = context;
            this.textFragment = textFragment;
            this.listDataHeader = listDataHeader;
            this.listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public void onGroupExpanded(int groupPosition) {
            try {
                getTextContent(groupPosition);
            } catch (JSONException e) {
                Log.e(TAG,"JSON error");
            }
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.fragment_text_child, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.best_score_label);

            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this.listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this.listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.fragment_text_head, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.textsListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
