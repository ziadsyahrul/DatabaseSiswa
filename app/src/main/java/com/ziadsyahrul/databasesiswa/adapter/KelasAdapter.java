package com.ziadsyahrul.databasesiswa.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ziadsyahrul.databasesiswa.R;
import com.ziadsyahrul.databasesiswa.UI.MainActivity;
import com.ziadsyahrul.databasesiswa.UI.UpdateKelasActivity;
import com.ziadsyahrul.databasesiswa.UI.siswa.MainSiswaActivity;
import com.ziadsyahrul.databasesiswa.db.Constant;
import com.ziadsyahrul.databasesiswa.db.SiswaDatabase;
import com.ziadsyahrul.databasesiswa.model.KelasModel;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.ViewHolder> {

    // Membuat variable untuk menampung context
    private final Context context;
    // Membuat variable list dengan cetakan kelasModel
    private final List<KelasModel> kelasModelList;

    // Veriable database
    private SiswaDatabase siswaDatabase;

    // Membuat variable bundle untuk membawa data
    private Bundle bundle;

    // Membuat variable alertDialog
    private AlertDialog alertDialog;

    public KelasAdapter(Context context, List<KelasModel> kelasModelList) {
        this.context = context;
        this.kelasModelList = kelasModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_kelas, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // Memindahkan data di dalam list dengan index position ke dalam kelas kelasModel
        final KelasModel kelasModel = kelasModelList.get(i);
        // Menampilkan data ke layar
        viewHolder.tvNamaWali.setText(kelasModel.getNama_wali());
        viewHolder.tvKelas.setText(kelasModel.getNama_kelas());

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        // Mensetting color background cardView
        viewHolder.cvKelas.setCardBackgroundColor(color);

        // Membuat onClick icon overflow
        viewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Membuat object database
                siswaDatabase = siswaDatabase.createDatabase(context);

                // Membuat object popupmenu
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);

                // Inflate menu ke dalam popupmenu
                popupMenu.inflate(R.menu.popup_menu);

                // Menampilkan menu
                popupMenu.show();

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, MainSiswaActivity.class);

                    }
                });

                // onClick pada salah satu menu pada popupmenu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.delete:

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setMessage("Are you sure delete " + kelasModel.getNama_kelas() +" ?");
                                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int position) {

                                        // Melakukan operasi delete data
                                        siswaDatabase.kelasDao().delete(kelasModel);

                                        // Menghapus data yang telah di hapus pada list
                                        kelasModelList.remove(i);

                                        // memberitahu bahwa data sudah hilang
                                        notifyItemRemoved(i);
                                        notifyItemRangeChanged(0, kelasModelList.size());

                                        Toast.makeText(context, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                                break;


                            case R.id.edit:
                                // Membuat object bundle
                                bundle = new Bundle();

                                // Mengisi bundle dengan data
                                bundle.putInt(Constant.KEY_ID_KELAS, kelasModel.getId_kelas());
                                bundle.putString(Constant.KEY_NAMA_KELAS, kelasModel.getNama_kelas());
                                bundle.putString(Constant.KEY_NAMA_WALI, kelasModel.getNama_wali());

                                // Berpindah halaman dengan membawa data
                                context.startActivity(new Intent(context, UpdateKelasActivity.class).putExtras(bundle));
                                break;
                        }
                        return false;
                    }
                });


            }
        });

        // Berpindah ke halaman MainSiswaActivity
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putInt(Constant.KEY_ID_KELAS, kelasModel.getId_kelas());
                bundle.putString(Constant.KEY_NAMA_KELAS,kelasModel.getNama_kelas());
                context.startActivity(new Intent(context, MainSiswaActivity.class).putExtras(bundle));
            }
        });


    }

    @Override
    public int getItemCount() {
        return kelasModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvKelas)
        TextView tvKelas;
        @BindView(R.id.tvNamaWali)
        TextView tvNamaWali;
        @BindView(R.id.cvKelas)
        CardView cvKelas;
        @BindView(R.id.overflow)
        ImageButton overflow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
