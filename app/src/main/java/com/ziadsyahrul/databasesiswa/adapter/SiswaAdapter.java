package com.ziadsyahrul.databasesiswa.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ziadsyahrul.databasesiswa.R;
import com.ziadsyahrul.databasesiswa.UI.siswa.UpdateSiswaActivity;
import com.ziadsyahrul.databasesiswa.db.Constant;
import com.ziadsyahrul.databasesiswa.db.SiswaDatabase;
import com.ziadsyahrul.databasesiswa.model.SiswaModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SiswaAdapter extends RecyclerView.Adapter<SiswaAdapter.ViewHolder> {

    private final Context context;
    private final List<SiswaModel> siswaModelList;


    public SiswaAdapter(Context context, List<SiswaModel> siswaModelList) {
        this.context = context;
        this.siswaModelList = siswaModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_siswa, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int which) {
        // Kita memindahkan data yang dipilih ke dalam list
        final SiswaModel siswaModel = siswaModelList.get(which);

        // kita tampilkan data ke layar
        viewHolder.txtNameSiswa.setText(siswaModel.getNama_siswa());

        // Mengambil huruf pertama
        String nama = siswaModel.getNama_siswa().substring(0, 1);

        // Membuat color generator untuk mendapatkan color material
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();

        // Mensetting TextDrawable untuk membuat lingkaran
        TextDrawable drawable = TextDrawable.builder().buildRound(nama, color);

        // Menampilkan gambar lingkaran ke layar
        viewHolder.imgView.setImageDrawable(drawable);

        // TODO 6 membuat delete
        // Membuat database siswa
        final SiswaDatabase siswaDatabase = SiswaDatabase.createDatabase(context);

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure want to delete " + siswaModel.getNama_siswa() + "?");
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, final int i) {
                        siswaDatabase.kelasDao().deleteSiswa(siswaModel);
                        siswaModelList.remove(which);
                        notifyItemRemoved(which);
                        notifyItemRangeChanged(0, siswaModelList.size());
                        Toast.makeText(context, "Berhasil di hapus", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.id_siswa, siswaModel.getId_siswa());
                bundle.putString(Constant.nama_siswa, siswaModel.getNama_siswa());
                bundle.putString(Constant.umur, siswaModel.getUmur());
                bundle.putString(Constant.jenis_kelamin, siswaModel.getJenis_kelamin());
                bundle.putString(Constant.asal, siswaModel.getAsal());
                bundle.putString(Constant.email, siswaModel.getEmail());
                bundle.putInt(Constant.id_kelas, siswaModel.getId_kelas());

                context.startActivity(new Intent(context, UpdateSiswaActivity.class).putExtras(bundle));
            }
        });


    }

    @Override
    public int getItemCount() {
        return siswaModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgView)
        ImageView imgView;
        @BindView(R.id.txt_name_siswa)
        TextView txtNameSiswa;
        @BindView(R.id.btnDelete)
        ImageButton btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
