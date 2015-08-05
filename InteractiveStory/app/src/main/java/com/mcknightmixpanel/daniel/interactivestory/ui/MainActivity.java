package com.mcknightmixpanel.daniel.interactivestory.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import org.json.JSONException;
import org.json.JSONObject;

import com.mcknightmixpanel.daniel.interactivestory.R;


public class MainActivity extends Activity {

    private EditText mNameField;
    private Button mStartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String projectToken = "2a31919e3003988ca5308a356e3b5dae"; // e.g.: "1ef7e30d2a58d27f4b90c42e31d6d7ad"
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);

        // start time to complete story timer
        mixpanel.timeEvent("Story Complete");

        // app open event
        try{
            JSONObject props = new JSONObject();
            props.put("Logged In", false);
            mixpanel.registerSuperProperties(props);
            mixpanel.track("App Open");
        }catch (JSONException e){
            Log.e("MYAPP", "Unable to add properties to JSONObject");
        }

        mNameField = (EditText)findViewById(R.id.nameEditText);
        mStartButton = (Button)findViewById(R.id.startButton);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String name = mNameField.getText().toString();
                    startStory(name);
            }
        });
    }

    private void startStory(String name){
        Intent intent = new Intent(this, StoryActivity.class);
        intent.putExtra(getString(R.string.key_name), name);
        startActivity(intent);
    }

    protected void onResume(){
        super.onResume();
        mNameField.setText("");
    }
}




