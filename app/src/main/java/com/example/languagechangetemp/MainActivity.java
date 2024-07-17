package com.example.languagechangetemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Test_code";

    public static final String SHARED_PREFERENCES_NAME = "LanguageAppTemp";
    public static final String SHARED_PREFERENCES_LANGUAGE = "Language";

    private TextView helloWorldTv;
    private RadioGroup languagesRadioGroup;
    private RadioButton defaultLanguageRadioButton, englishRadioButton, russianRadioButton,
            hebrewRadioButton, japaneseRadioButton;
    private Button secondActivityBtn;

    private SharedPreferences sharedPreferences;
    private String lang;

    private Resources resources;
    private DisplayMetrics displayMetrics;
    private Configuration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setReferences();

        resources = getResources();
        displayMetrics = resources.getDisplayMetrics();
        configuration = resources.getConfiguration();

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        lang = sharedPreferences.getString(SHARED_PREFERENCES_LANGUAGE, "");
        Log.d(TAG, "onCreate: Shared Preferences Language: " + lang);
        setLanguage(lang);
        setSelectedLanguageRadioButton(lang);

        languagesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.default_language_radio_button:
                        Log.d(TAG, "onCheckedChanged: Default language");
                        setLanguage("");
                        break;
                    case R.id.english_radio_button:
                        Log.d(TAG, "onCheckedChanged: English");
                        setLanguage("en");
                        break;
                    case R.id.russian_radio_button:
                        Log.d(TAG, "onCheckedChanged: Russian");
                        setLanguage("ru");
                        break;
                    case R.id.hebrew_radio_button:
                        Log.d(TAG, "onCheckedChanged: Hebrew");
                        setLanguage("iw");
                        break;
                    case R.id.japanese_radio_button:
                        Log.d(TAG, "onCheckedChanged: Japanese");
                        setLanguage("ja");
                        break;
                    default:
                        Log.d(TAG, "onCheckedChanged: Wrong selected");
                }
            }
        });

        secondActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), SecondActivity.class));
            }
        });
    }

    private void setReferences() {
        helloWorldTv = findViewById(R.id.hello_world_tv);
        languagesRadioGroup = findViewById(R.id.languages_radio_group);
        defaultLanguageRadioButton = findViewById(R.id.default_language_radio_button);
        englishRadioButton = findViewById(R.id.english_radio_button);
        russianRadioButton = findViewById(R.id.russian_radio_button);
        hebrewRadioButton = findViewById(R.id.hebrew_radio_button);
        japaneseRadioButton = findViewById(R.id.japanese_radio_button);
        secondActivityBtn = findViewById(R.id.second_activity_btn);
    }

    private void setLanguage(String language) {
        Log.d(TAG, "setLanguage: Set new language: " + language);

        String localLang = configuration.locale.getLanguage();

        if (localLang.equals(language)){
            Log.d(TAG, "setLanguage: same language");
            return;
        }

        configuration.locale = new Locale(language);
        resources.updateConfiguration(configuration, displayMetrics);
        onConfigurationChanged(configuration);

        saveSelectedLanguage(language);
    }

    private void saveSelectedLanguage(String language) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_LANGUAGE, language);
        editor.commit();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        finish();
        startActivity(getIntent());

//        recreate(); // this option not working when app only opened and need set language. But after app already worked, making easy reload activity.

//        helloWorldTv.setText(R.string.hello_world); // Option for update language only per items, without reload activity
    }

    private void setSelectedLanguageRadioButton(String language) {
        if (language.equals("") || language.isEmpty()) {
            defaultLanguageRadioButton.setChecked(true);
            return;
        }
        if (language.equals("en")) {
            englishRadioButton.setChecked(true);
            return;
        }
        if (language.equals("iw")) {
            hebrewRadioButton.setChecked(true);
            return;
        }
        if (language.equals("ru")) {
            russianRadioButton.setChecked(true);
            return;
        }
        if (language.equals("ja")) {
            japaneseRadioButton.setChecked(true);
            return;
        }
    }
}