package com.example.memo_ku.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.memo_ku.R;

public class HomeContainerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_container, container, false);

        Button btnTambah = view.findViewById(R.id.btnTambahCatatan);
        Button btnLihat = view.findViewById(R.id.btnLihatCatatan);

        btnTambah.setOnClickListener(v ->
                startActivity(new Intent(getContext(), AddNoteActivity.class))
        );

        btnLihat.setOnClickListener(v ->
                startActivity(new Intent(getContext(), NoteListActivity.class))
        );

        return view;
    }
}
