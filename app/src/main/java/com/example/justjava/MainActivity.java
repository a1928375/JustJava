/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.examplejust.java
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        quantity = quantity + 1;
        if (quantity > 100) {
            Toast.makeText(this, "We can't handle \"Too many\" cups of coffee at a time! \n100 cups are max!", Toast.LENGTH_LONG).show();
            quantity=100;
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        quantity = quantity - 1;
        if (quantity < 1) {
            Toast.makeText(this, "You can't order less than 1 cup of coffee!\n1 cup is min!", Toast.LENGTH_LONG).show();
            quantity = 1;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = findViewById(R.id.checkbox_Whipped_cream);
        boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = findViewById(R.id.checkbox_chocolate);
        boolean hasChocolate = chocolate.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        EditText userName = findViewById(R.id.user_name);
        Editable realUserName = userName.getText();
        //Log.i("MainActivity", "Has checked:" + hasWhippedCream + hasChocolate);
        String message = getString(R.string.user_name_1)+ " " + realUserName + "\n" + getString(R.string.add_cream) + "  " + hasWhippedCream + "\n" + getString(R.string.add_chocolate) + "  " + hasChocolate + "\n" + getString(R.string.quantity_1) + " " + quantity + "\n" + getString(R.string.total) + price + "\n" + getString(R.string.thank_you);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + realUserName);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//        displayMessage(message);
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean cream, boolean chocolate) {
        int price = 5;
        if (cream == true && chocolate == false) {
            price = quantity * 6;
        } else if (cream == false && chocolate == true) {
            price = quantity * 7;
        } else if (cream == true && chocolate == true) {
            price = quantity * 8;
        } else {
            price = quantity * 5;
        }
        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}

