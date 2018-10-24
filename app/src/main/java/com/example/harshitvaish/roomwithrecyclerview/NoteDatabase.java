package com.example.harshitvaish.roomwithrecyclerview;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities   ={Note.class},version=1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack).build();
        }
return instance;
    }
    private static RoomDatabase.Callback roomCallBack=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
         new PopulateAbAsyncTask(instance).execute();
        }
    };
     private static  class PopulateAbAsyncTask extends AsyncTask<Void,Void,Void>{
private NoteDao noteDao;

         public PopulateAbAsyncTask(NoteDatabase db) {
             this.noteDao = db.noteDao();
         }

         @Override
         protected Void doInBackground(Void... voids) {
             noteDao.insert(new Note("Title 1","Desciption 1",1));
             noteDao.insert(new Note("Title 2","Desciption 2",2));
             noteDao.insert(new Note("Title 3","Desciption 3",3));

             return null;
         }
     }
    }

