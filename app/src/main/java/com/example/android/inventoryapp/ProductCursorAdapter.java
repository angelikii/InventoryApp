package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract.ProductEntry;

/**
 * Created by Angeletou on 31/10/2016.
 */

public class ProductCursorAdapter extends CursorAdapter {

    View row;

    Bitmap bmpProduct;

    Cursor cursor;

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /*Makes a new blank list item view. No data is set (or bound) to the views yet.*/
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) { // why newView? we had usually getView here?

        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);

    }

    /* This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.*/
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        final int rowid = cursor.getPosition();


        // Find individual views that we want to modify in the list item layout
       final TextView nameTextView = (TextView) view.findViewById(R.id.product_name);
        final TextView priceTextView = (TextView) view.findViewById(R.id.product_price);
       final TextView quantTextView = (TextView) view.findViewById(R.id.quantity_value);
        final TextView salesTextView = (TextView) view.findViewById(R.id.sales_value);
        final ImageView imgImageView = (ImageView) view.findViewById(R.id.product_thumbnail);

        // Find the columns of attributes that we're interested in
       final int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
       final int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRICE);
        final int quantColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY);
        final int salesColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SALES);
        final int imgColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PIC);


        // Read the attributes from the Cursor for the current product
        final String name = cursor.getString(nameColumnIndex);
        final int price = cursor.getInt(priceColumnIndex);
        final int quant = cursor.getInt(quantColumnIndex);
        final int sales = cursor.getInt(salesColumnIndex);
        byte[] imgProductArray = cursor.getBlob(imgColumnIndex);
        if (imgProductArray != null) {
            bmpProduct = BitmapFactory.decodeByteArray(imgProductArray, 0, imgProductArray.length);
            imgImageView.setImageBitmap(bmpProduct);
        }

        // Update the TextViews with the attributes for the current product
        nameTextView.setText(name);
        priceTextView.setText(String.valueOf(price));
        quantTextView.setText(String.valueOf(quant));
        salesTextView.setText(String.valueOf(sales));

        final TextView sale1Btn = (TextView) view.findViewById(R.id.button_sale1);
        sale1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on click, update the current product
                ContentValues values = new ContentValues();
                if (quant - 1 >= 0) {
                    int newQuant = quant - 1;
                    int newSales = sales + 1;
                    values.put(ProductEntry.COLUMN_QUANTITY, newQuant);
                    values.put(ProductEntry.COLUMN_SALES, newSales);
                    Uri currentProductUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, rowid);
                   int rowsAffected = context.getContentResolver().update(currentProductUri, values, null, null);
                    if (rowsAffected > 0)
                    {
                        Toast.makeText(context, "update succeeded :)", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, "update failed :(", Toast.LENGTH_SHORT).show();

                    }

                }
            }


        });
        // if product updated, the TextViews will show the new values (?)
        int quantUpdate = cursor.getInt(quantColumnIndex);
        int salesUpdate = cursor.getInt(salesColumnIndex);
        quantTextView.setText(String.valueOf(quantUpdate));
        salesTextView.setText(String.valueOf(salesUpdate));


    }


}
