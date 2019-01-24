package com.ziadsyahrul.databasesiswa.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ziadsyahrul.databasesiswa.model.KelasModel;
import com.ziadsyahrul.databasesiswa.model.SiswaModel;

import java.util.List;

@Dao
public interface KelasDao {

    // Mengambil data
    @Query("SELECT * FROM KELAS ORDER BY nama_ke" +
            "las DESC")
    List<KelasModel> select();

    // Memasukkan data
    @Insert
    void insert(KelasModel kelasModel);

    // Menghapus data
    @Delete
    void delete(KelasModel kelasModel);

    // Mengupdate data
    @Update
    void update(KelasModel kelasModel);


    // Mengambil data siswa
    @Query("SELECT * FROM TB_SISWA WHERE id_kelas = :id_kelas ORDER BY nama_siswa ASC")
    List<SiswaModel> selectSiswa(int id_kelas);

    // Memasukkan data siswa
    @Insert
    void insertSiswa(SiswaModel siswaModel);

    // Menghapus data siswa
    @Delete
    void deleteSiswa(SiswaModel siswaModel);

    @Update
    // Mengupdate data siswa
    void updateSiswa(SiswaModel siswaModel);


}
