package com.sdsmdg.harjot.MusicDNA;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.support.v7.widget.SwitchCompat;
import android.widget.TextView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.squareup.leakcanary.RefWatcher;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    RelativeLayout densitycard, themeCard, aboutCard, albumArtCard;
    SwitchCompat albumArtToggle;
    ImageView themeColorImg;
    SeekBar densitySeekbar;
    TextView densityTextDialog, densityText;

    onAlbumArtBackgroundToggled mCallback;
    onColorChangedListener mCallback2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public interface onColorChangedListener {
        public void onColorChanged();
    }

    public interface onAlbumArtBackgroundToggled {
        public void onAlbumArtBackgroundChangedVisibility(int visibility);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallback = (onAlbumArtBackgroundToggled) getContext();
        mCallback2 = (onColorChangedListener) getContext();

        densitycard = (RelativeLayout) view.findViewById(R.id.density_card);
        densityText = (TextView) view.findViewById(R.id.density_value);
        densityText.setText(String.valueOf(100 - (int) (HomeActivity.minAudioStrength * 100)));

        densitycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.density_dialog);
                densitySeekbar = (SeekBar) dialog.findViewById(R.id.density_dialog_seekbar);
                densitySeekbar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(HomeActivity.themeColor, PorterDuff.Mode.SRC_IN));
                densityTextDialog = (TextView) dialog.findViewById(R.id.density_dialog_value);
                densitySeekbar.setMax(100);
                densitySeekbar.setProgress(Integer.parseInt(densityText.getText().toString()));
                densityTextDialog.setText(String.valueOf(densitySeekbar.getProgress()));
                densitySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        HomeActivity.minAudioStrength = 1.0f - ((float) progress / (float) 100);
                        HomeActivity.settings.setMinAudioStrength(HomeActivity.minAudioStrength);
                        densityTextDialog.setText(String.valueOf(progress));
                        densityText.setText(String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                dialog.show();

            }
        });

        themeCard = (RelativeLayout) view.findViewById(R.id.theme_card);
        themeColorImg = (ImageView) view.findViewById(R.id.theme_color_img);
        themeColorImg.setBackgroundColor(HomeActivity.themeColor);
        themeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialogBuilder
                        .with(getContext())
                        .setTitle("Choose color")
                        .initialColor(((ColorDrawable) themeColorImg.getBackground()).getColor())
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(9)
                        .showColorPreview(true)
                        .lightnessSliderOnly()
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int color) {

                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int color, Integer[] allColors) {
                                HomeActivity.settings.setThemeColor(color);
                                HomeActivity.themeColor = color;
                                HomeActivity.toolbar.setBackgroundColor(color);
                                HomeActivity.fragmentToolbar.setBackgroundColor(color);
                                themeColorImg.setBackgroundColor(color);
                                mCallback2.onColorChanged();
                                if (Build.VERSION.SDK_INT >= 21) {
                                    Window window = ((Activity) (getContext())).getWindow();
                                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                    window.setStatusBarColor(color);
                                }
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show();
            }
        });

        albumArtCard = (RelativeLayout) view.findViewById(R.id.album_art_card);
        albumArtToggle = (SwitchCompat) view.findViewById(R.id.album_art_toggle);
        albumArtToggle.setChecked(HomeActivity.settings.isAlbumArtBackgroundEnabled());
        albumArtCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumArtToggle.toggle();
            }
        });
        albumArtToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HomeActivity.settings.setAlbumArtBackgroundEnabled(isChecked);
                mCallback.onAlbumArtBackgroundChangedVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });

        aboutCard = (RelativeLayout) view.findViewById(R.id.about_card);
        aboutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("About");
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setMessage("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                alertDialog.show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RefWatcher refWatcher = MusicDNAApplication.getRefWatcher(getContext());
        refWatcher.watch(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MusicDNAApplication.getRefWatcher(getContext());
        refWatcher.watch(this);
    }
}
