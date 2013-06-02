package com.example.photofiler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public class InputDialog {
	
	private String userInput="null";

	public void dialog(Context context, String title, String message) {
		 final AlertDialog.Builder alert = new AlertDialog.Builder(context);

	        alert.setTitle(title);
	        alert.setMessage(message);

	        // Set an EditText view to get user input 
	        final EditText input = new EditText(context);
	        alert.setView(input);

	        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	userInput = input.getText().toString();
	          }
	        });

	        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int whichButton) {
	            // Canceled.
	          }
	        });

	        alert.show();
		
	}
	
	public String getUserInput(){
		return userInput;
	}

}
