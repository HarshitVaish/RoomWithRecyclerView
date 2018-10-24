package com.example.harshitvaish.roomwithrecyclerview;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDatabase database=NoteDatabase.getInstance(application);
        noteDao=database.noteDao();
        allNotes =noteDao.getAllNOtes();

    }
    public void insert(Note note){
        new insertNOteAsyncTesk(noteDao).execute(note);
    }
    public void update(Note note){
        new updateNOteAsyncTesk((noteDao)).execute(note);
    }
    public void delete(Note note){
        new deleteNOteAsyncTesk(noteDao).execute(note);
    }
    public void deleteAllNotes(){
        new deleteAllNOteAsyncTesk(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class insertNOteAsyncTesk extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public insertNOteAsyncTesk(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
           noteDao.insert(notes[0]);
            return null;
        }
    }
    private static class updateNOteAsyncTesk extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public updateNOteAsyncTesk(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    private static class deleteNOteAsyncTesk extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public deleteNOteAsyncTesk(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    private static class deleteAllNOteAsyncTesk extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        public deleteAllNOteAsyncTesk(NoteDao noteDao) {
            this.noteDao = noteDao;

        }



        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();

            return null;
        }
    }
}
