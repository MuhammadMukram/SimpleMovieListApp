package com.example.simplemovielistapp.sqlitesection;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;

import com.example.simplemovielistapp.models.FavouriteModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class LoadAsyncData {
    private final WeakReference<Context> contextWeakReference;
    private final WeakReference<LoadCallback> weakCallback;

    public LoadAsyncData(Context contextWeakReference, LoadCallback weakCallback) {
        this.contextWeakReference = new WeakReference<>(contextWeakReference);
        this.weakCallback = new WeakReference<>(weakCallback);
    }

    public void execute() {
        Executor executors = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executors.execute(() -> {
            Context context = contextWeakReference.get();
            FavouriteHelper favouriteHelper = FavouriteHelper.getInstance(context);
            favouriteHelper.open();
            Cursor cursor = favouriteHelper.queryShowAll();
            ArrayList<FavouriteModel> favouriteModels = MappingHelper.cursorToArraylist(cursor);
            favouriteHelper.close();
            handler.post(() -> weakCallback.get().postExecute(favouriteModels));
        });
    }
}

