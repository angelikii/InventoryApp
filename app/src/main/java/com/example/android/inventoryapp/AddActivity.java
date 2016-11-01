package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract.ProductEntry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Angeletou on 31/10/2016.
 */

public class AddActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText priceEditText;
    EditText quantEditText;
    EditText supplEditText;

    String nameString;
    String priceString;
    String quantString;
    String supplMail;
    int priceInt;
    int quantInt;
    Uri newProductUri;

    boolean mProductChanged;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) { // why it appears before the onCreate?
            mProductChanged = true; //how do we know that the user has stopped providing input?
            return false;
        }
    };



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);

        Intent intent = getIntent();
        //TODO: Take user input and save it in the database
        // Find all relevant views that we will need to read user input from
        nameEditText = (EditText) findViewById(R.id.edit_name);
        priceEditText = (EditText) findViewById(R.id.edit_price);
        quantEditText = (EditText) findViewById(R.id.edit_quantity);
        supplEditText = (EditText) findViewById(R.id.edit_supplier);


        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.

        nameEditText.setOnTouchListener(mTouchListener);
        priceEditText.setOnTouchListener(mTouchListener);
        quantEditText.setOnTouchListener(mTouchListener);
        supplEditText.setOnTouchListener(mTouchListener);

        // Read from input fields

        Button saveBtn = (Button) findViewById(R.id.button_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                // Use trim to eliminate leading or trailing white space
                nameString = nameEditText.getText().toString().trim();
                priceString = priceEditText.getText().toString().trim();
                quantString = quantEditText.getText().toString().trim();
                supplMail = supplEditText.getText().toString().trim();

                if ((!TextUtils.isEmpty(nameString)) && (!TextUtils.isEmpty(priceString)) &&
                        (!TextUtils.isEmpty(quantString)) && (!TextUtils.isEmpty(supplMail))) {
                    try {
                        priceInt = Integer.parseInt(priceString);
                    } catch (NumberFormatException e) {
                    Log.i("",priceString +" is not a number");
                        Toast.makeText(AddActivity.this, "please in a number for price", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        quantInt = Integer.parseInt(quantString);
                    } catch (NumberFormatException e) {
                        Log.i("",quantString +" is not a number");
                        Toast.makeText(AddActivity.this, "please in a number for quantity", Toast.LENGTH_SHORT).show();
                    }
                    if ((priceInt<0)||(quantInt<0)) {
                        Toast.makeText(AddActivity.this, "please fill in positive numbers for quantity and price", Toast.LENGTH_SHORT).show();
                    } else if (!isEmailValid(supplMail))
                    {
                        Toast.makeText(AddActivity.this, supplMail + " please fill in a valid email address", Toast.LENGTH_SHORT).show();
                    } else {
                        newProductUri = saveProduct();
                        finish();
                    }
                }
                else {
                    Toast.makeText(AddActivity.this, "please fill in all the gaps", Toast.LENGTH_SHORT).show();

                }

            }
        });

        //if the user attempts to exit, display a dialog "save or discard?"

    }
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private Uri saveProduct() {

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(ProductEntry.COLUMN_PRICE, priceInt);
        values.put(ProductEntry.COLUMN_QUANTITY, quantInt);
        values.put(ProductEntry.COLUMN_SUPPLIER, supplMail);
        values.put(ProductEntry.COLUMN_SALES, 0);

        Toast.makeText(this, "contentValue ready!", Toast.LENGTH_SHORT).show();

// This is a NEW product, so insert a new pet into the provider, returning the content URI for the new product.
        Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);

        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(AddActivity.this, "insert_pet_failed", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(AddActivity.this, "insert_pet_successful", Toast.LENGTH_SHORT).show();
            return newUri;
        }

    }


}
