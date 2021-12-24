package com.example.pencatatanpenduduk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pencatatanpenduduk.Helpers.DBHelper;
import com.example.pencatatanpenduduk.model.Penduduk;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PendudukAdapter extends RecyclerView.Adapter<PendudukAdapter.ViewHolder> {

    private ArrayList<Penduduk> penduduks;
    private DBHelper dbHelper;
    private Context context;
    private Bundle bundle = new Bundle();

    PendudukAdapter(ArrayList<Penduduk> penduduks ) {
//        this.penduduks.clear();
        this.penduduks = penduduks;
        notifyDataSetChanged();

    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView nama_lengkap, alamat, tanggal_lahir;
        CircleImageView profileImage;
        CardView item;


        ViewHolder(View v) {

            super(v);

            nama_lengkap = v.findViewById(R.id.nama_pnduduk);
            alamat = v.findViewById(R.id.alamat_pnduduk);
            tanggal_lahir = v.findViewById(R.id.tgl_lahir_pnduduk);

            profileImage = v.findViewById(R.id.penduduk_image);
            item = v.findViewById(R.id.itemList);
            item.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Menu Option:");
            contextMenu.add(getAdapterPosition(),101,0,"Edit");
            contextMenu.add(getAdapterPosition(),102,0,"Hapus");
        }
    }
    @NonNull
    @Override
    public PendudukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_penduduk, parent, false);
        ViewHolder vh = new ViewHolder(view);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PendudukAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nama_lengkap.setText(penduduks.get(position).getnamaLengkap());
        holder.tanggal_lahir.setText(penduduks.get(position).gettanggalLahir());
        holder.alamat.setText(penduduks.get(position).getAlamat().length() > 27 ? penduduks.get(position).getAlamat().substring(0,27) + "...." : penduduks.get(position).getAlamat() );
        if (penduduks.get(position).getFoto() != null){
            Uri uri = Uri.parse(penduduks.get(position).getFoto());
            holder.profileImage.setImageURI(uri);
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lempar id menggunakan intent
                bundle.putInt("id",penduduks.get(position).get_id());

                Intent intent = new Intent(context,ProfileActivity.class);
                intent.putExtra("userData",bundle);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return penduduks.size();

    }

    public void removeItem(int position)
    {
        dbHelper = new DBHelper(context);
        dbHelper.deleteData(penduduks.get(position).get_id());
        penduduks.remove(position);
        notifyDataSetChanged();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, penduduks.size());
    }

    public void editItem(int position)
    {

        bundle.putInt("id",penduduks.get(position).get_id());
        bundle.putString("name",penduduks.get(position).getnamaLengkap());
        bundle.putString("alamat",penduduks.get(position).getAlamat());
        bundle.putString("tanggal_lahir",penduduks.get(position).gettanggalLahir());
        bundle.putString("jk",penduduks.get(position).getjenisKelamin());
        bundle.putString("nomor_telepon",penduduks.get(position).getNomorTlp());
        bundle.putString("gaji",penduduks.get(position).getGaji());
        bundle.putString("agama",penduduks.get(position).getAgama());

        String [] hobis = penduduks.get(position).getHobi().split(",");
        bundle.putStringArray("hobi",hobis);
        bundle.putString("editMode","Edit");
        bundle.putString("jenisKelamin",penduduks.get(position).getjenisKelamin());

        if (penduduks.get(position).getFoto() != null){
            bundle.putString("avatar",penduduks.get(position).getFoto());
        }

        Intent intent = new Intent(context,AddPendudukActivity.class);
        intent.putExtra("userData",bundle);
        context.startActivity(intent);
    }

    public  void filteredList(ArrayList<Penduduk> filtered){
        penduduks = filtered;
        notifyDataSetChanged();
    }

}


