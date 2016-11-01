package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventoryapp.data.InventoryContract.ProductEntry;

/**
 * Created by Angeletou on 31/10/2016.
 */

public class ProductCursorAdapter extends CursorAdapter{

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
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.product_name);
        TextView priceTextView = (TextView) view.findViewById(R.id.product_price);
        TextView quantTextView = (TextView) view.findViewById(R.id.quantity_value);
        TextView salesTextView = (TextView) view.findViewById(R.id.sales_value);

        // Find the columns of attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRICE);
        int quantColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY);
        int salesColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SALES);

        // Read the attributes from the Cursor for the current product
        String name = cursor.getString(nameColumnIndex);
        int price = cursor.getInt(priceColumnIndex);
        int quant = cursor.getInt(quantColumnIndex);
        int sales = cursor.getInt(salesColumnIndex);


        // Update the TextViews with the attributes for the current product
        nameTextView.setText(name);
        priceTextView.setText(price);
        quantTextView.setText(quant);
        salesTextView.setText(sales);

    }
}
