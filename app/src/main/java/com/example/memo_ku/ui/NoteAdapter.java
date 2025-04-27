package com.example.memo_ku.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo_ku.R;
import com.example.memo_ku.model.Catatan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final List<Catatan> catatanList;
    private final Context context;

    public NoteAdapter(Context context, List<Catatan> catatanList) {
        this.context = context;
        this.catatanList = catatanList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_catatan, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Catatan catatan = catatanList.get(position);
        holder.tvJudul.setText(catatan.judul);
        holder.tvIsi.setText(catatan.isi);

        holder.btnDelete.setOnClickListener(v -> {
            if (catatan.id != null && !catatan.id.isEmpty()) {
                DatabaseReference dbRef = FirebaseDatabase.getInstance()
                        .getReference("catatan").child(catatan.id);

                dbRef.removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "Catatan dihapus", Toast.LENGTH_SHORT).show();
                            // Tidak perlu hapus manual dari list atau notifyItemRemoved, sudah ditangani di ChildEventListener
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Gagal hapus: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(context, "ID catatan tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return catatanList.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvIsi;
        Button btnDelete;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvIsi = itemView.findViewById(R.id.tvIsi);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
