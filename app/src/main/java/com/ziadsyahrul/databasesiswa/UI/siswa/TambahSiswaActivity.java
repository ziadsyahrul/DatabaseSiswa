package com.ziadsyahrul.databasesiswa.UI.siswa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ziadsyahrul.databasesiswa.R;
import com.ziadsyahrul.databasesiswa.db.Constant;
import com.ziadsyahrul.databasesiswa.db.SiswaDatabase;
import com.ziadsyahrul.databasesiswa.model.SiswaModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahSiswaActivity extends AppCompatActivity {

    @BindView(R.id.edtNamaSiswa)
    EditText edtNamaSiswa;
    @BindView(R.id.edtUmur)
    EditText edtUmur;
    @BindView(R.id.radio_laki)
    RadioButton radioLaki;
    @BindView(R.id.radio_perempuan)
    RadioButton radioPerempuan;
    @BindView(R.id.radio_jenis_kelamin)
    RadioGroup radioJenisKelamin;
    @BindView(R.id.edtAsal)
    EditText edtAsal;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;

    // TODO 1 membuat variable yang dibutuhkan
    private SiswaDatabase siswaDatabase;
    private int id_kelas;
    private String namaSiswa, asal, umur, jenis_kelamin, email;
    private boolean empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_siswa);
        ButterKnife.bind(this);

        // TODO 2 Mensetting dan menangkap data dari activity sebelumnya
        // Mensetting title
        setTitle("Add Siswa");

        // tangkap id_kelas dari activity sebelumnya
        id_kelas = getIntent().getIntExtra(Constant.KEY_ID_KELAS, 0);

        // kita buat object database
        siswaDatabase = SiswaDatabase.createDatabase(this);
    }

    @OnClick(R.id.btnSimpan)
    public void onViewClicked() {
        // Memastikan semuanya terisi
        cekData();

        if (!empty){
            saveData();
            clearData();

            Toast.makeText(this, "Berhasil disimpan !", Toast.LENGTH_SHORT).show();

            finish();
        }else {
            Toast.makeText(this, "Masih ada kolom yang belum diisi", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearData() {
        edtNamaSiswa.setText(" ");
        edtUmur.setText(" ");
        edtAsal.setText(" ");
        edtEmail.setText(" ");
        radioJenisKelamin.clearCheck();
    }

    private void saveData() {
        // Membuat penampung SiswaModel
        SiswaModel siswaModel = new SiswaModel();

        // kita masukkan data ke dalam SiswaModel
        siswaModel.setId_kelas(id_kelas);
        siswaModel.setNama_siswa(namaSiswa);
        siswaModel.setAsal(asal);
        siswaModel.setUmur(umur);
        siswaModel.setJenis_kelamin(jenis_kelamin);
        siswaModel.setEmail(email);

        // kita lakukan operasi insert
        siswaDatabase.kelasDao().insertSiswa(siswaModel);
    }

    private void cekData() {
        namaSiswa = edtNamaSiswa.getText().toString();
        asal = edtAsal.getText().toString();
        umur = edtUmur.getText().toString();
        email = edtEmail.getText().toString();

        empty = namaSiswa.isEmpty() || asal.isEmpty() || umur.isEmpty() || email.isEmpty() || jenis_kelamin.isEmpty();
    }

    @OnClick({R.id.radio_laki, R.id.radio_perempuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_laki:
                jenis_kelamin = radioLaki.getText().toString();
                break;
            case R.id.radio_perempuan:
                jenis_kelamin = radioPerempuan.getText().toString();
                break;
        }
    }
}
