package com.ziadsyahrul.databasesiswa.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ziadsyahrul.databasesiswa.R;
import com.ziadsyahrul.databasesiswa.db.Constant;
import com.ziadsyahrul.databasesiswa.db.SiswaDatabase;
import com.ziadsyahrul.databasesiswa.model.KelasModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateKelasActivity extends AppCompatActivity {

    @BindView(R.id.edtNamaKelas)
    EditText edtNamaKelas;
    @BindView(R.id.edtNamaWali)
    EditText edtNamaWali;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;

    // Membuat variable bundle
    private Bundle bundle;

    // Membuat variable list
    private List<KelasModel> kelasModelList;

    // Membuat variable database
    private SiswaDatabase siswaDatabase;

    private int id_kelas;
    private String nama_kelas, nama_wali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_kelas);
        ButterKnife.bind(this);

        // set title
        setTitle("Update dtaa kelas");

        // Menangkap data dari activity sebelumnya
        bundle = getIntent().getExtras();

        // Buat penampung object list
        kelasModelList = new ArrayList<>();

        // Buat object database
        siswaDatabase = SiswaDatabase.createDatabase(this);

        // Menampilkan data sebelumnya ke layar
        showData();
    }

    private void showData() {
        // Mengambil data di dalam bundle
        id_kelas = bundle.getInt(Constant.KEY_ID_KELAS);
        nama_kelas = bundle.getString(Constant.KEY_NAMA_KELAS);
        nama_wali = bundle.getString (Constant.KEY_NAMA_WALI);

        edtNamaKelas.setText(nama_kelas);
        edtNamaWali.setText(nama_wali);

    }

    @OnClick(R.id.btnSimpan)
    public void onViewClicked() {
        // Mengambil data
        getData();

        // Mengirim data ke sqlite
        saveData();

        Toast.makeText(this, "Berhasil di update !", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void getData() {
        // Mengambil inputan user dan dimasukkan ke dalam variable
        nama_kelas = edtNamaKelas.getText().toString();
        nama_wali = edtNamaWali.getText().toString();

    }

        private void saveData() {
            // Membuat object kelasModel
            KelasModel kelasModel = new KelasModel();
            // Memasukkan data ke kelasmodel
            kelasModel.setId_kelas(id_kelas);
            kelasModel.setNama_kelas(nama_kelas);
            kelasModel.setNama_wali(nama_wali);
            // Melakukan operasi update untuk mengupdate data ke sqlite
            siswaDatabase.kelasDao().update(kelasModel);

    }
}