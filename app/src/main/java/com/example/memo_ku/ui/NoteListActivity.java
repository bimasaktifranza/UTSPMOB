package com.example.memo_ku.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo_ku.R;

import com.example.memo_ku.model.Catatan;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;

public class NoteListActivity extends AppCompatActivity {

    private RecyclerView rvCatatan;
    private Button btnBackToHome;
    private ArrayList<Catatan> catatanList;
    private HashMap catatanMap;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        rvCatatan = findViewById(R.id.rvCatatan);
        rvCatatan.setLayoutManager(new LinearLayoutManager(this));

        catatanMap = new HashMap<>();
        catatanList = new ArrayList<>();
        adapter = new NoteAdapter(this, catatanList);
        rvCatatan.setAdapter(adapter);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("catatan");
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                Catatan catatan = snapshot.getValue(Catatan.class);
                if (catatan != null) {
                    catatan.setId(snapshot.getKey());
                    catatanMap.put(catatan.getId(), catatan);
                    catatanList.add(catatan);
                    adapter.notifyItemInserted(catatanList.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                String id = snapshot.getKey();
                Catatan updatedCatatan = snapshot.getValue(Catatan.class);
                if (updatedCatatan != null && id != null) {
                    updatedCatatan.setId(id);
                    for (int i = 0; i < catatanList.size(); i++) {
                        if (catatanList.get(i).getId().equals(id)) {
                            catatanList.set(i, updatedCatatan);
                            adapter.notifyItemChanged(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String id = snapshot.getKey();
                if (id != null) {
                    for (int i = 0; i < catatanList.size(); i++) {
                        if (catatanList.get(i).getId().equals(id)) {
                            catatanList.remove(i);
                            adapter.notifyItemRemoved(i);
                            catatanMap.remove(id);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(view ->{
            startActivity(new Intent(NoteListActivity.this, MainActivity.class));
        });
    }
}
