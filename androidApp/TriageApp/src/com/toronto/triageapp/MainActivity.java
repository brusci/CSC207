/*   <Application for Physicians and Nurses to keep track of patient records.>
 *   Copyright (C) <2014>  <Robert Staskiewicz, Danniel Yang, Arlo Shallit, Ramanan s.>
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.toronto.triageapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
*  Login screen, national and local record loader
*  
*
* @author bob.staskiewicz@mail.utoronto.ca (Robert Staskiewicz)
* @author danniel.yang@mail.utoronto.ca (Danniel Yang)
* @author shallit.arlo@gmail.com (Arlo Shallit)
* @author TODO: ADD RAMAMAN
*/

public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.toronto.triageapp.MESSAGE"; // Useless? 
	private Nurse nurse;
	private String userName;
	Context context = MainActivity.this;
	CharSequence text = "";
	int duration = Toast.LENGTH_SHORT;
	private File file;
	

	@SuppressLint("DefaultLocale") // Always use default system time.
	public void staffLogin(View view) throws FileNotFoundException {
		String fileName = "passwords.txt";
		this.file = new File(this.getFilesDir().getPath() + "/" + fileName);
		EditText userNameNumberText = (EditText) findViewById(R.id.user_name);
		EditText passwordText = (EditText) findViewById(R.id.password);
		Scanner scanner;
		scanner = new Scanner(new FileInputStream(this.file.getAbsoluteFile()));
		String[] record;
		boolean isPass = false;
		
		if (file.exists()) {
			while (scanner.hasNextLine()) {
				record = scanner.nextLine().split(",");
				if (record[0].equals(userNameNumberText.getText().toString()) 
						&& record[1].equals(passwordText.getText().toString())) {
					isPass = true;
					this.userName = record[2].substring(0,1).toUpperCase()
							+ record[2].substring(1, record[2].length()) 
							+ "///" + record[0].substring(0,1).toUpperCase()
							+ record[0].substring(1, record[0].length());
					this.text = "Welcome " + record[2] 
							+ " " + record[0].substring(0,1).toUpperCase() // Capitalize first letter of staff name.
							+ record[0].substring(1, record[0].length()) + "!";
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					
					userNameNumberText.setVisibility(View.INVISIBLE);
					Button button = (Button) findViewById(R.id.sign_in);
				    button.setVisibility(View.INVISIBLE);
				    passwordText.setVisibility(View.INVISIBLE);
					break;
				}
			}
			scanner.close();
	}
		else {
			scanner.close();
			this.text = "There is no password.txt file";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		if (isPass == true) {
			TextView numTxtView = (TextView) findViewById(R.id.input_add);
			Button button = (Button) findViewById(R.id.button_new_record);
		    button.setVisibility(View.VISIBLE);
		    button = (Button) findViewById(R.id.button_patient);
		    button.setVisibility(View.VISIBLE);
		    button = (Button) findViewById(R.id.button_new_patient);
		    button.setVisibility(View.VISIBLE);
		    button = (Button) findViewById(R.id.button_log_out);
		    button.setVisibility(View.VISIBLE);
		    numTxtView.setVisibility(View.VISIBLE);  
	        TextView nameTxtView = (TextView) findViewById(R.id.input_name);
	        nameTxtView.setVisibility(View.VISIBLE);
	        TextView birthdayTxtView = (TextView) findViewById(R.id.input_birthday);
	        birthdayTxtView.setVisibility(View.VISIBLE);
		}
		else {
			this.text = "Invalid username or password";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
	
	/** Called when the user clicks the Send button */
	public void sendPatient(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, PatientActivity.class);
		intent.putExtra("nurseKey", this.nurse);
		intent.putExtra("userNameKey", this.userName);
	    startActivity(intent);
	}

	public void newRecord(View view) {
	    // Add patient with health card number to records.
		EditText healthCardNumberText = (EditText) findViewById(R.id.input_add);
		int patientNum = 0;

		try {
		    patientNum = Integer.parseInt(healthCardNumberText.getText().toString());
		    this.nurse.newPatient(patientNum);
		    if (this.nurse.getPatient() != null) {
		    		this.text = "Added Patient to Record";
		    		Toast toast = Toast.makeText(context, text, duration);
		    		toast.show();
		    		this.nurse.savePatient();
			}
		    else {
		    	this.text = "Health Card Number not in National Database!";
		    	Toast toast = Toast.makeText(context, text, duration);
		    	toast.show();
		    }
		}
		catch(NumberFormatException nfe) {
			this.text = "Please Enter a Number";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		this.nurse.saveTriage();
	}
	
	public void newPatient(View view) {
		EditText healthCardNumberText = (EditText) findViewById(R.id.input_add);
		EditText nameText = (EditText) findViewById(R.id.input_name);
		EditText birthdayText = (EditText) findViewById(R.id.input_birthday);
		int patientNum = 0;
		
		try {
		    patientNum = Integer.parseInt(healthCardNumberText.getText().toString());
			this.text = "Added New Patient";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		catch(NumberFormatException nfe) {
			this.text = "Please Enter a 6 digit Health Card Number";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		String name = nameText.getText().toString();
		String birthday = birthdayText.getText().toString();
		this.nurse.newPatient(name, birthday, patientNum);
		this.nurse.savePatient();
		this.nurse.saveTriage();
	}
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    if (getIntent()!=null) {
	    	this.nurse = (Nurse) getIntent().getSerializableExtra("nurseKey");
	    	this.userName = (String) getIntent().getSerializableExtra("userNameKey");
	    }
	    
	    if (this.nurse==null) {
	    	this.nurse = new Nurse(this.getFilesDir().getPath());
	    }
	    
	    // Hides login layout and shows record layout.
	    if (this.userName != null) { 
		    EditText userNameNumberText = (EditText) findViewById(R.id.user_name);
			EditText passwordText = (EditText) findViewById(R.id.password);
			Button button = (Button) findViewById(R.id.sign_in);
		    userNameNumberText.setVisibility(View.INVISIBLE);
		    button.setVisibility(View.INVISIBLE);
		    passwordText.setVisibility(View.INVISIBLE);
			TextView numTxtView = (TextView) findViewById(R.id.input_add);
			Button button1 = (Button) findViewById(R.id.button_new_record);
		    button1.setVisibility(View.VISIBLE);
		    button1 = (Button) findViewById(R.id.button_patient);
		    button1.setVisibility(View.VISIBLE);
		    button1 = (Button) findViewById(R.id.button_new_patient);
		    button1.setVisibility(View.VISIBLE);
		    button1 = (Button) findViewById(R.id.button_log_out);
		    button1.setVisibility(View.VISIBLE);
		    numTxtView.setVisibility(View.VISIBLE);  
	        TextView nameTxtView = (TextView) findViewById(R.id.input_name);
	        nameTxtView.setVisibility(View.VISIBLE);
	        TextView birthdayTxtView = (TextView) findViewById(R.id.input_birthday);
	        birthdayTxtView.setVisibility(View.VISIBLE);
	    }
	}
	
	// Hides record layout and shows login layout.
	public void logOut(View view) {
		this.userName = null;
	    EditText userNameNumberText = (EditText) findViewById(R.id.user_name);
		EditText passwordText = (EditText) findViewById(R.id.password);
		Button button = (Button) findViewById(R.id.sign_in);
	    userNameNumberText.setVisibility(View.VISIBLE);
	    button.setVisibility(View.VISIBLE);
	    passwordText.setVisibility(View.VISIBLE);
		TextView numTxtView = (TextView) findViewById(R.id.input_add);
		Button button1 = (Button) findViewById(R.id.button_new_record);
	    button1.setVisibility(View.INVISIBLE);
	    button1 = (Button) findViewById(R.id.button_patient);
	    button1.setVisibility(View.INVISIBLE);
	    button1 = (Button) findViewById(R.id.button_new_patient);
	    button1.setVisibility(View.INVISIBLE);
	    button1 = (Button) findViewById(R.id.button_log_out);
	    button1.setVisibility(View.INVISIBLE);
	    numTxtView.setVisibility(View.INVISIBLE);  
        TextView nameTxtView = (TextView) findViewById(R.id.input_name);
        nameTxtView.setVisibility(View.INVISIBLE);
        TextView birthdayTxtView = (TextView) findViewById(R.id.input_birthday);
        birthdayTxtView.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}