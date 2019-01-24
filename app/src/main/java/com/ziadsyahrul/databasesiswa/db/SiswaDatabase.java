package com.ziadsyahrul.databasesiswa.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ziadsyahrul.databasesiswa.model.KelasModel;
import com.ziadsyahrul.databasesiswa.model.SiswaModel;


@Database(entities = {KelasModel.class, SiswaModel.class}, version = 1)
public abstract class SiswaDatabase extends  RoomDatabase {

    public abstract KelasDao kelasDao();

    private static SiswaDatabase INSTANCE;

    // Membuat method return untuk membuat database
    public static SiswaDatabase createDatabase(Context context){
        synchronized (SiswaDatabase.class){
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, SiswaDatabase.class, "db_siswa").allowMainThreadQueries().build();
            }
        }
        return INSTANCE;
    }
}
