package com.example.smallnetworkandroom;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.smallnetworkandroom.model.StockEntity;
import com.example.smallnetworkandroom.model.StockEntityDAO;
import com.example.smallnetworkandroom.viewmodels.Converters;

@Database(entities = {StockEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class StockRoomDatabase extends RoomDatabase {
    public abstract StockEntityDAO stockDao();

    private static volatile StockRoomDatabase INSTANCE;

    static StockRoomDatabase getDataBase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StockRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            StockRoomDatabase.class,
                            "stock_database")
                            .addCallback(sRoomDatabaseCallBack)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            AppExecutors.getInstance().diskIO().execute(() -> {
                //Populate database in the background
                //if you want to start with more words just add them

                StockEntity starbucks = new StockEntity("SBUX");
                StockEntity facebook = new StockEntity("FB");
                StockEntity pepsi = new StockEntity("PEP");
                StockEntityDAO stockDao = INSTANCE.stockDao();
                stockDao.deleteAll();
                stockDao.insertStock(starbucks);
                stockDao.insertStock(facebook);
                stockDao.insertStock(pepsi);
            });
        }
    };
}
