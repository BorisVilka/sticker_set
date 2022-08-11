package org.kwork4;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kwork4.databinding.FragmentPreferencesBinding;

public class PreferencesFragment extends Fragment {

    private FragmentPreferencesBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPreferencesBinding.inflate(inflater,container,false);
        binding.dark.setOnClickListener(view -> {
            boolean b = getContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("night",false);
           if(!b) {
               getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
                       .edit()
                       .putBoolean("night",true)
               .apply();
           }
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        });
        binding.button2.setOnClickListener(view -> {
            boolean b = getContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("night",false);
            if(b) {
                getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean("night",false)
                .apply();
            }
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        });
        return binding.getRoot();
    }
}