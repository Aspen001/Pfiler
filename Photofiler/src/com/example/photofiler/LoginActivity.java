package com.example.photofiler;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user,
 * and it also allows a user to create (register) an account.
 */
public class LoginActivity extends Activity implements OnClickListener {
	
	private boolean checkLogin = false;

	private String[] passwordEx = new String[] {
		"foo@example.com:hello", "bar@example.com:world" };

	private UserLoginTask authTask = null;

	// Values for email and password at the time of the login attempt.
	private String email;
	private String password;
	
	private MainActivity controller; 
	
	// UI references.
	private EditText emailView;
	private EditText passwordView;
	private View loginFormView;
	private View loginStatusView;
	private TextView loginStatusMessageView;
	private Button btnRegister;
	private Button btnLogIn;
	private ImageView logoPicture;
 


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		logoPicture = (ImageView)findViewById(R.drawable.ic_launcher);
		
		emailView = (EditText) findViewById(R.id.email);
		emailView.setText(email);

		passwordView = (EditText) findViewById(R.id.password);
		passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			public boolean onEditorAction(TextView textView, int id,
					KeyEvent keyEvent) {
				if (id == R.id.login || id == EditorInfo.IME_NULL) {
					attemptLogin();
					return true;
				}
				return false;
			}
		});

		loginFormView = findViewById(R.id.login_form);
		loginStatusView = findViewById(R.id.login_status);
		loginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		btnLogIn = (Button) findViewById(R.id.sign_in_button);
		btnRegister = (Button) findViewById(R.id.createacount);


		btnLogIn.setOnClickListener(this); //Setting listener to the button "Log In"
		btnRegister.setOnClickListener(this); //Setting listener to the button "Register"
				

	}

	public void onClick(final View v){
		switch(v.getId()){
		case R.id.sign_in_button:
	 			
			attemptLogin(); 
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			if (checkLogin == true){
				
				final Handler handler = new Handler();
				 handler.postDelayed(new Runnable() {
	                 @Override
	                 public void run() {
	                	 appStart(v);
	                	 finish();
	                 }
	               }, 3000);
			} 
			
			
//			attemptLogin1();
			break;
		case R.id.createacount: //if the button register is pressed then a new Intent would be create and then sends you to the website so you can create an account
			Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
			myWebLink.setData(Uri.parse("http://www.photofiler.se/blimedlem.php"));
			startActivity(myWebLink);
			break;

		}
	}
	
	public void appStart(View v){
		 Intent homeScreen = new Intent(this, MainActivity.class); //need to use this when login is correct 
		 startActivity(homeScreen);
	}



	/**
	 * This method attempts to login 
	 * if there is any problem with password
	 * or username this will be showed for the user
	 * for example if the user miss to type "@" then
	 * the user can't login 
	 */
	public void attemptLogin() {
//		if (authTask != null) {
//			return;
//		}

		// Reset errors.
		emailView.setError(null);
		passwordView.setError(null);

		// Store values at the time of the login attempt.
		email = emailView.getText().toString(); //stores the mail address
		password = passwordView.getText().toString(); //stores the password 

		boolean cancel = false; //the boolean cancel used for getting the login process killed when something is wrong
		View focusView = null;

		// Checking for a valid password
		//If the text field contains a password then its true or when the password length is more then 10 characters
		if (TextUtils.isEmpty(password)) {
			passwordView.setError(getString(R.string.error_field_required));
			focusView = passwordView;
			cancel = true; //login process is cancelled if the condition is true
		} else if (password.length() < 3) { //this means that the password must contains at least 4 characters  
			passwordView.setError(getString(R.string.error_invalid_password));
			focusView = passwordView;
			cancel = true;//login process is cancelled if the condition is true
		}

		// Checking for a valid e-mail 
		//If the text field contains an email then its true or when the mail address contains @ 
		if (TextUtils.isEmpty(email)) {
			emailView.setError(getString(R.string.error_field_required));
			focusView = emailView;
			cancel = true;//login process is cancelled if the condition is true
		} else if (!email.contains("@")) {
			emailView.setError(getString(R.string.error_invalid_email));
			focusView = emailView;
			cancel = true;//login process is cancelled if the condition is true
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			checkLogin = true;

			
			loginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
//			checkLogin = true;
			
//			authTask = new UserLoginTask();
//			authTask.execute((Void) null);
			
			
			
			
		}
	}

	/**
	 * This method shows a progress of logging in to the application
	 * A short animation will be showed during the progress 
	 * Because of all android version not are allowed to do 
	 * short animation like this there is a if statement that 
	 * controls the version of android and can on that way decide if
	 * the phone is compatible with the short log in animation  
	 * @param show
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			loginStatusView.setVisibility(View.VISIBLE);
			loginStatusView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {

				public void onAnimationEnd(Animator animation) {
					loginStatusView.setVisibility(show ? View.VISIBLE
							: View.GONE);
				}
			});

			loginFormView.setVisibility(View.VISIBLE);
			loginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
				public void onAnimationEnd(Animator animation) {
					loginFormView.setVisibility(show ? View.GONE
							: View.VISIBLE);
				}
			});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			loginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * This method should be used to authenticate the user 
	 * The app should connect a java server which the connects 
	 * to our mySQL database to see if the user is valid or not 
	 */
	public void attemptLogin1(){
//		tcpClient.sendLogin(email, password);
		
//		if(anv�ndarnamn.equalsTo(n�got anv�ndarnamn p� databasen ){
//			Intent homeScreen = new Intent(this, MainActivity.class); //need to use this when login is correct 
//			startActivity(homeScreen);
//		}

		
	}
	
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		protected Boolean doInBackground(Void... params) {
			//attempt authentication against a network service.
			//connect the server here i think 

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			for (String credential : passwordEx) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(email)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(password);
				}
			}

			// register the new account here.
			return true;
		}


		protected void onPostExecute(final Boolean success) {
			authTask = null;
			showProgress(false);

			if (success) {
				finish();
			} else {
				passwordView.setError(getString(R.string.error_incorrect_password));
				passwordView.requestFocus();
			}
		}


		protected void onCancelled() {
			authTask = null;
			showProgress(false);
		}
	}
}
