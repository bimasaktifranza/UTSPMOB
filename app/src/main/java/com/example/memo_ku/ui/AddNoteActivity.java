package com.example.memo_ku.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memo_ku.R;
import com.example.memo_ku.model.Catatan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNoteActivity extends AppCompatActivity {

    private EditText etJudul, etIsi;
    private Button btnAddNote, btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etJudul = findViewById(R.id.etJudul);
        etIsi = findViewById(R.id.etIsi);
        btnAddNote = findViewById(R.id.btnAddNote);
        btnBackHome = findViewById(R.id.btnBackHome);

        btnAddNote.setOnClickListener(v -> {
            String judul = etJudul.getText().toString().trim();
            String isi = etIsi.getText().toString().trim();

            if (!judul.isEmpty() && !isi.isEmpty()) {
                String id = FirebaseDatabase.getInstance().getReference("catatan").push().getKey();
                Catatan catatan = new Catatan(id, judul, isi);
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("catatan");
                dbRef.child(id).setValue(catatan);

                Toast.makeText(this, "Catatan disimpan!", Toast.LENGTH_SHORT).show();
                etJudul.setText("");
                etIsi.setText("");
            } else {
                Toast.makeText(this, "Harap isi judul dan isi catatan", Toast.LENGTH_SHORT).show();
            }
        });


        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Menutup halaman ini
        });
    }
}
