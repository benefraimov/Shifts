package com.example.shifts;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AddShiftWithFragment extends Fragment {

    Button btnAddShift, btnBackPressCancel;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_shift_with, container, false);

        btnAddShift = view.findViewById(R.id.btnConfirm);
        btnBackPressCancel = view.findViewById(R.id.btnCancel);

        btnBackPressCancel = public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View v = inflater.inflate(R.layout.activity_info, null);
                return v;
            }
        });

        return view;
    }

}