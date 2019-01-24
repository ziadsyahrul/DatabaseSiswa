package com.ziadsyahrul.databasesiswa.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ziadsyahrul.databasesiswa.R;
import com.ziadsyahrul.databasesiswa.adapter.KelasAdapter;
import com.ziadsyahrul.databasesiswa.db.SiswaDatabase;
import com.ziadsyahrul.databasesiswa.model.KelasModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rvKelas)
    RecyclerView rvKelas;

    // TODO 1 Membuat variable
    private SiswaDatabase siswaDatabase;
    private List<KelasModel> kelasModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO 2 Mensetting variable yang dibutuhkan
        // Membuat object database
        siswaDatabase = SiswaDatabase.createDatabase(this);

        // Membuat object List
        kelasModelList = new ArrayList<>();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TambahKelasActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Mengosongkan List agar dipastikan list dapat diisi dengan data yang paling baru
        kelasModelList.clear();
        // mengambil data dari SQLite
        getData();
        // Mensetting dan proses menampilkan data ke recyclerview
        rvKelas.setLayoutManager(new GridLayoutManager(this, 2));
        rvKelas.setAdapter(new KelasAdapter(this, kelasModelList));
    }

    private void getData() {
        // Operasi mengambil data dari database
        kelasModelList = siswaDatabase.kelasDao().select();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
