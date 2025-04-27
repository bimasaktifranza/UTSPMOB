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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo_ku.R;
import com.example.memo_ku.model.Catatan;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView rvCatatan;
    private Button btnAdd;
    private ArrayList<Catatan> catatanList;
    private NoteAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvCatatan = view.findViewById(R.id.rvCatatan);
        btnAdd = view.findViewById(R.id.btnAddNote);
        rvCatatan.setLayoutManager(new LinearLayoutManager(getContext()));

        catatanList = new ArrayList<>();

        // Ensure getContext() is not null
        if (getContext() != null) {
            adapter = new NoteAdapter(getContext(), catatanList); // Pass context to the adapter
        }

        rvCatatan.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddNoteActivity.class));
        });

        // Get Firebase reference for "catatan"
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("catatan");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                catatanList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Catatan c = data.getValue(Catatan.class);
                    if (c != null) {
                        catatanList.add(c); // Add note to the list
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter that data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error (optional)
            }
        });

        return view;
    }
}
