package com.example.android.inventoryapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Angeletou on 31/10/2016.
 */

public class DetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private Uri mCurrentPetUri;
    /** Identifier for the pet data loader */
    private static final int EXISTING_PRODUCT_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_view);

        Intent intent = getIntent();
        mCurrentPetUri = intent.getData();
        getLoaderManager().initLoader(EXISTING_PRODUCT_LOADER, null, this);

        //TODO: Take the uri from the intent and retrieve the data from the database.
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}


