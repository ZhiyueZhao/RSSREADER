package com.example.zhiyue.RSSpro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private EditText etxFontSize, etxShow, etxDesLength;

    private TextView txtPreview;

    private CheckBox cbTitle, cbDate, cbDesc;

    private RadioButton rdoAscending, rdoDescending;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //get preferences instance
        prefs = getSharedPreferences("RSSPrefs", MODE_PRIVATE);

        //get view refs

        etxFontSize = (EditText)findViewById(R.id.etxFontSize);
        etxShow = (EditText)findViewById(R.id.etxShow);
        etxDesLength = (EditText)findViewById(R.id.etxDesLength);

        txtPreview = (TextView)findViewById(R.id.txtPreview);

        cbTitle = (CheckBox)findViewById(R.id.cbTitle);
        cbDate = (CheckBox)findViewById(R.id.cbDate);
        cbDesc = (CheckBox)findViewById(R.id.cbDesc);

        rdoAscending = (RadioButton) findViewById(R.id.rdoAsc);
        rdoDescending = (RadioButton) findViewById(R.id.rdoDesc);

        etxFontSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                int size = 0;

                try
                {
                    size = Integer.parseInt(String.valueOf(s));
                }
                catch(NumberFormatException ex)
                {
                    size = 1;
                }

                size = size < 1 ? 1 : size;

                txtPreview.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //check if shared prefs exist
        if(prefs.contains("FontSize") &&
                prefs.contains("NumToShow") &&
                prefs.contains("ShowTitle") &&
                prefs.contains("ShowDate") &&
                prefs.contains("ShowDesc") &&
                prefs.contains("SortAscending"))
        {
            etxFontSize.setText(String.valueOf(prefs.getInt("FontSize", 24)));
            etxShow.setText(String.valueOf(prefs.getInt("NumToShow", 0)));
            etxDesLength.setText(String.valueOf(prefs.getInt("DesLength", 150)));

            cbTitle.setChecked(prefs.getBoolean("ShowTitle", true));
            cbDate.setChecked(prefs.getBoolean("ShowDate", true));
            cbDesc.setChecked(prefs.getBoolean("Show", true));
            cbDesc.setChecked(prefs.getBoolean("ShowDesc", true));

            if(prefs.getBoolean("SortAscending", true))
            {
                rdoAscending.setChecked(true);
            }
            else
            {
                rdoDescending.setChecked(true);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        savePrefs();

        super.onBackPressed();
    }

    private void savePrefs()
    {
        //store EditText fields
        String  fontSize = etxFontSize.getText().toString().trim(),
                numToShow = etxShow.getText().toString().trim(),
                desLength = etxDesLength.getText().toString().trim();

        //determine if values are empty
        if(fontSize.length() > 0 && numToShow.length() > 0)
        {
            //store integer values
            int     size = Integer.parseInt(fontSize),
                    show = Integer.parseInt(numToShow),
                    length = Integer.parseInt(desLength);

            //get prefs editor
            SharedPreferences.Editor editor = prefs.edit();

            //store values
            editor.putInt("FontSize", (size < 1 ? 1 : size));
            editor.putInt("NumToShow", (show < 0 ? 0 : show));
            editor.putInt("DesLength", (length < 50 ? 50 : length));

            editor.putBoolean("ShowTitle", cbTitle.isChecked());
            editor.putBoolean("ShowDate", cbDate.isChecked());
            editor.putBoolean("ShowDesc", cbDesc.isChecked());

            editor.putBoolean("SortAscending", rdoAscending.isChecked());

            //write to xml file and note success/failure
            if(editor.commit())
            {
                //display success message
                Toast.makeText(this, "Settings Saved", Toast.LENGTH_SHORT).show();
            }
            else
            {
                //display failure message
                Toast.makeText(this, "An error occurred while saving", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
