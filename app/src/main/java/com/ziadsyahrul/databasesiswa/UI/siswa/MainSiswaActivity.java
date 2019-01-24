package com.ziadsyahrul.databasesiswa.UI.siswa;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ziadsyahrul.databasesiswa.R;
import com.ziadsyahrul.databasesiswa.adapter.SiswaAdapter;
import com.ziadsyahrul.databasesiswa.db.Constant;
import com.ziadsyahrul.databasesiswa.db.SiswaDatabase;
import com.ziadsyahrul.databasesiswa.model.SiswaModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainSiswaActivity extends AppCompatActivity {

    @BindView(R.id.rvSiswa)
    RecyclerView rvSiswa;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    // TODO 1 membuat variable yang kita butuhkan
    private SiswaDatabase siswaDatabase;
    private List<SiswaModel> siswaModelList;
    private int id_kelas;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_siswa);
        ButterKnife.bind(this);

        // Menangkap data dari activity sebelumnya
        bundle = getIntent().getExtras();

        // Menangkap data id_kelas ke dalam variable
        if (bundle != null) {
            id_kelas = bundle.getInt(Constant.KEY_ID_KELAS);

            // set title dengan menggunakan nama kelas yang di tangkap dari activity sebelumnya
            setTitle(bundle.getString(Constant.KEY_NAMA_KELAS));
        }

        // Membuat database object
        siswaDatabase = siswaDatabase.createDatabase(this);

        // membuat object list
        siswaModelList = new ArrayList<>();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Menghapus isi data di dalam list
        siswaModelList.clear();

        // Mengambil data dan mengisinya
        getData();

        // Mensetting adapter untuk menampilkan datanya
        // Mensetting item garis bawah
        rvSiswa.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // setting LinearLayout
        rvSiswa.setLayoutManager(new LinearLayoutManager(this));
        // Set adapter
        rvSiswa.setAdapter(new SiswaAdapter(this, siswaModelList));
    }

    private void getData() {
        // Operasi mengambil data yang ada di dalam SQlite menggunakan select dengan parameter id_kelas
        // untuk mengambil data siswa dengan kelas yang diinginkan
        siswaModelList = siswaDatabase.kelasDao().selectSiswa(id_kelas);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        startActivity(new Intent(this, TambahSiswaActivity.class).putExtra(Constant.KEY_ID_KELAS, id_kelas));
    }
}
