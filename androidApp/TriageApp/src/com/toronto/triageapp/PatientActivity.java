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

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PatientActivity extends Activity {
	
	private AutoCompleteTextView actv;
	private Nurse nurse;
	Context context = PatientActivity.this;
	CharSequence text = "";
	int duration = Toast.LENGTH_SHORT;
	private String userName;
	private String[] patientList;
	private ArrayAdapter<String> patientListAdapter;
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		
		// Get the nurse object and username string.
	    Intent intent = getIntent();
	    this.nurse = (Nurse) (intent.getSerializableExtra("nurseKey"));
	    this.userName = (String) (intent.getSerializableExtra("userNameKey"));
	    patientList = new String[nurse.getTriage().getPatients().size()];
	    
	    for (int i=0;i<patientList.length;i++) {
	    	patientList[i] = (nurse.getTriage().getPatients().get(i).getName()
	    						+ "\t" + "\t" + "Urgency: " 
	    						+ nurse.getTriage().getPatients().get(i).getUrgency() 
	    						+ "\n" + nurse.getTriage().getPatients()
	    						.get(i).getHealthcardNum());
	    }
	    
	    ArrayList<String> patientListClone = new ArrayList<String>();
	    
	    for (int i=0;i<patientList.length;i++) {
	    	patientListClone.add(patientList[i].split("\n")[1]);
	    }
	    
	    patientListAdapter = new ArrayAdapter<String>(this,
	    		android.R.layout.simple_list_item_1, patientList);
	    
	    lv = (ListView)findViewById(R.id.listView1);
	    lv.setAdapter(patientListAdapter); // Set listview to all the patients in the local record.

		//Listener for each individual listview item. 
		OnItemClickListener mMessageClickedHandler = new OnItemClickListener()
		{
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        // Send patient to RecordActivity in response to the listview click.
		    	String[] text = ((TextView)v).getText().toString().split("\n");
		    	int patientNum = Integer.parseInt(text[1]);
		    	sendRecord(v, patientNum);
		    }
		};
	    lv.setOnItemClickListener(mMessageClickedHandler);
	    
	 // Get a reference to the AutoCompleteTextView in the layout
	   ArrayAdapter<?> adapter2 = new ArrayAdapter<Object>(this, 
			   							android.R.layout.simple_list_item_1,
			   							patientListClone.toArray());
	   actv = (AutoCompleteTextView) findViewById(R.id.input_search);
	   actv.setAdapter(adapter2);
	}
	
	public void sendRecord(View view, int patientNumber) {
		Intent intent = new Intent(this, RecordActivity.class);
		this.nurse.loadPatient(patientNumber);
		intent.putExtra("nurseKey", this.nurse);
		intent.putExtra("userNameKey", this.userName);
	    startActivity(intent);
	}
	
	public void back(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("nurseKey", this.nurse);
		intent.putExtra("userNameKey", this.userName);
		startActivity(intent);
	}
	
	public void search(View view) {
		try {
			this.nurse.loadPatient(Integer.parseInt(((AutoCompleteTextView) 
			findViewById(R.id.input_search)).getText().toString()));
			String[] searchedPatient = {this.nurse.getPatient().getName() 
										+ "\t" + "\t"+ "Urgency: "
										+ nurse.getPatient().getUrgency() + "\n"
										+ this.nurse.getPatient().getHealthcardNum()
										};
			
			lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				searchedPatient)); 
			}
		catch(NumberFormatException ex) {
			this.text = "Please Enter a 6 digit Health Card Number";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			}
		}
	
	public void clearSearch(View view) {
		((AutoCompleteTextView) findViewById(R.id.input_search)).setText("");
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				this.patientList));
		}
	
	//Sorts listview by Urgency.
	public void unseen(View view) {
		ArrayList<Patient> unseen = new ArrayList<Patient>(this.nurse.getTriage().getUnseenByUrgency());
		String[] str = new String[unseen.size()];
		
		for (int i = 0; i < str.length; i++) {
			str[i] = (unseen.get(i).getName() + "\t" + "\t"+ "Urgency: " 
						+ unseen.get(i).getUrgency() + "\n" 
						+ unseen.get(i).getHealthcardNum());
			}
		lv.setAdapter(new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, str));
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
		return true;
	}
}