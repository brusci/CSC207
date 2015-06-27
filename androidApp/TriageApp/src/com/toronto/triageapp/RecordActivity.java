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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RecordActivity extends Activity {
	private Nurse nurse;
	private String userName;
	private String[] patientRecords;
	Context context = RecordActivity.this;
	CharSequence text = "";
	int duration = Toast.LENGTH_SHORT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		
		Intent intent = getIntent();
	    this.nurse = (Nurse) ((intent.getSerializableExtra("nurseKey")));
	    this.userName = (String) ((intent.getSerializableExtra("userNameKey")));
	    
 		((TextView) (findViewById(R.id.nameView))).setText(this.nurse.getPatient().getName());
		((TextView) (findViewById(R.id.dateofbirthView))).setText("" +this.nurse.getPatient().getBirthdate());
		((TextView) (findViewById(R.id.healthcardnumberView))).setText("" + this.nurse.getPatient().getHealthcardNum());
		((TextView) (findViewById(R.id.arrivaltimeView))).setText(this.nurse.getPatient().getArrivalTime());
		((TextView) (findViewById(R.id.seenView))).setText("" + this.nurse.getPatient().getSeen());
		
		if(this.userName.split("///")[0].equals("Nurse")) {
			EditText medicationTextView = (EditText) findViewById(R.id.medicationView);
		    medicationTextView.setVisibility(View.INVISIBLE);
			EditText instructionTextView = (EditText) findViewById(R.id.instructionView);
		    instructionTextView.setVisibility(View.INVISIBLE);
		    }
		else {
			EditText temperatureTextView = (EditText) findViewById(R.id.temperatureView);
			temperatureTextView.setVisibility(View.INVISIBLE);
			EditText systolicPressureTextView = (EditText) findViewById(R.id.systolicpressureView);
			systolicPressureTextView.setVisibility(View.INVISIBLE);
			EditText diastolicPressureTextView = (EditText) findViewById(R.id.diastolicpressureView);
			diastolicPressureTextView.setVisibility(View.INVISIBLE);
			EditText symptomsTextView = (EditText) findViewById(R.id.symptomsView);
			symptomsTextView.setVisibility(View.INVISIBLE);
			EditText heartrateTextView = (EditText) findViewById(R.id.heartrateView);
			heartrateTextView.setVisibility(View.INVISIBLE);
			}
	    refreshList();
	    }
	
	public void back(View view) {
		Intent intent = new Intent(this, PatientActivity.class);
		intent.putExtra("nurseKey", this.nurse);
		intent.putExtra("userNameKey", this.userName);
		startActivity(intent);
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record, menu);
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
	
	public void saveRecord(View view) {
	    // Save local patient records to a text file
		if (this.userName.split("///")[0].equals("Nurse")) {
			if (!((EditText) (findViewById(R.id.temperatureView))).getText().toString().equals("")
					&& !((EditText) (findViewById(R.id.systolicpressureView))).getText().toString().equals("")
					&& !((EditText) (findViewById(R.id.diastolicpressureView))).getText().toString().equals("")
					&& !((EditText) (findViewById(R.id.symptomsView))).getText().toString().equals("")
					&& !((EditText) (findViewById(R.id.heartrateView))).getText().toString().equals("")) {
						nurse.updatePatient(Double.parseDouble(
								((EditText) (findViewById(R.id.temperatureView))).getText().toString()), 
						Double.parseDouble(((EditText) (findViewById(R.id.systolicpressureView))).getText().toString()),
						Double.parseDouble(((EditText) (findViewById(R.id.diastolicpressureView))).getText().toString()),
						Double.parseDouble(((EditText) (findViewById(R.id.heartrateView))).getText().toString()),
						((EditText) (findViewById(R.id.symptomsView))).getText().toString());
                        if ((((EditText) (findViewById(R.id.seenNurseView))).getText().toString().equals(""))
                            | ((EditText) (findViewById(R.id.seenNurseView))).getText().toString().equals("false")) {
                            nurse.updatePatientMedication("N/A", "N/A", false);

                        } // determines if doctor has seen the patient or not (from Nurse account).
                        else {
                            nurse.updatePatientMedication("N/A", "N/A", true);
                        }
						nurse.updatePatientSeenBy(this.userName.split("///")[0], this.userName.split("///")[1]);
						refreshList(); // Refresh listview after local record is saved
						((EditText) (findViewById(R.id.temperatureView))).setText("");
                        ((EditText) (findViewById(R.id.seenNurseView))).setText("");
						((EditText) (findViewById(R.id.systolicpressureView))).setText("");
						((EditText) (findViewById(R.id.diastolicpressureView))).setText("");
						((EditText) (findViewById(R.id.heartrateView))).setText("");
						((EditText) (findViewById(R.id.symptomsView))).setText("");
                         ((TextView) (findViewById(R.id.seenView))).setText("" + this.nurse.getPatient().getSeen());
						nurse.savePatient();
						this.nurse.saveTriage();
						}
			else {
				this.text = "Please Enter All Fields!";
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				}
			}
		else {
			if (!((EditText) (findViewById(R.id.medicationView))).getText().toString().equals("")
					&& !((EditText) (findViewById(R.id.instructionView))).getText().toString().equals("")) {
						nurse.updatePatientMedication(
								((EditText)(findViewById(R.id.medicationView))).getText().toString(), 
						((EditText) (findViewById(R.id.instructionView))).getText().toString(), true);
						nurse.updatePatient(-100, -100, -100, -100, "N/A");
						nurse.updatePatientSeenBy(this.userName.split("///")[0], this.userName.split("///")[1]);
						refreshList();
                        ((TextView) (findViewById(R.id.seenView))).setText("" + this.nurse.getPatient().getSeen());
                         ((EditText) (findViewById(R.id.seenNurseView))).setText("");
						((EditText) (findViewById(R.id.medicationView))).setText("");
						((EditText) (findViewById(R.id.instructionView))).setText("");
						nurse.savePatient();
						this.nurse.saveTriage();
						}
			else {
				this.text = "Please Enter All Fields!";
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				}
			}
		}
	
	// refresh listview with a new record after it has been saved
	public void refreshList() {
		this.patientRecords = new String[this.nurse.getPatient().getTemp().size()];
		
		for (int i=0;i<this.patientRecords.length;i++) {
			this.patientRecords[i] = this.nurse.getPatient().getSeenBy().get(patientRecords.length-i-1).split("///")[0]
					+ ": " + this.nurse.getPatient().getSeenBy().get(patientRecords.length-i-1).split("///")[1] + "\n" 
					+ "Seen At: " + this.nurse.getPatient().getSeenAt().get(patientRecords.length-i-1);
			
			if (this.nurse.getPatient().getSeenBy().get(patientRecords.length-i-1).split("///")[0].equals("Nurse")) {
				this.patientRecords[i] += "\n" + "Temperature: " 
						+ this.nurse.getPatient().getTemp().get(patientRecords.length-i-1) + "\n" 
					+ "Heartrate: " 
						+ this.nurse.getPatient().getRate().get(patientRecords.length-i-1)+ "\n" 
					+ "Diastolic Pressure: " 
						+ this.nurse.getPatient().getDiastolicPressure().get(patientRecords.length-i-1)+ "\n" 
					+ "Systolic Pressure: " 
						+ this.nurse.getPatient().getSystolicPressure().get(patientRecords.length-i-1)+ "\n" 
					+ "Symptoms: " + this.nurse.getPatient().getSymptoms().get(patientRecords.length-i-1);
				}
			else {
				this.patientRecords[i] += "\n" + 
						 "Medication: " 
						+ this.nurse.getPatient().getMedication().get(patientRecords.length-i-1) + "\n" 
						+ "Instructions: "
						+ this.nurse.getPatient().getInstructions().get(patientRecords.length-i-1);
				}
			}
		
		ArrayAdapter<String> patientRecordAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.patientRecords);
	    ListView patientAdapter = (ListView)findViewById(R.id.expListView);
	    patientAdapter.setAdapter(patientRecordAdapter);
	    }
}