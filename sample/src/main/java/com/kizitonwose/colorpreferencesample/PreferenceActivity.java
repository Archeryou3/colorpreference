package com.kizitonwose.colorpreferencesample;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.kizitonwose.colorpreference.ColorPreference;
import com.larswerkman.lobsterpicker.LobsterPicker;
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider;

/**
 * Created by Kizito Nwose on 9/28/2016.
 */
public class PreferenceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new MyPreferenceFragment()).commit();

    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        private final String CUSTOM_PICKER_PREF_KEY = "color_pref_lobster";

        @Override
        public void onCreate(final Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

            findPreference(CUSTOM_PICKER_PREF_KEY).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    showColorDialog(preference);
                    return true;
                }
            });


        }

        private void showColorDialog(final Preference preference) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View colorView = inflater.inflate(R.layout.dialog_color, null);

            int color = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(CUSTOM_PICKER_PREF_KEY, Color.YELLOW);
            final LobsterPicker lobsterPicker = (LobsterPicker) colorView.findViewById(R.id.lobsterPicker);
            LobsterShadeSlider shadeSlider = (LobsterShadeSlider) colorView.findViewById(R.id.shadeSlider);

            lobsterPicker.addDecorator(shadeSlider);
            lobsterPicker.setColorHistoryEnabled(true);
            lobsterPicker.setHistory(color);
            lobsterPicker.setColor(color);

            new AlertDialog.Builder(getActivity())
                    .setView(colorView)
                    .setTitle("Choose Color")
                    .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((ColorPreference) preference).setValue(lobsterPicker.getColor());
                        }
                    })
                    .setNegativeButton("CLOSE", null)
                    .show();
        }


    }


}