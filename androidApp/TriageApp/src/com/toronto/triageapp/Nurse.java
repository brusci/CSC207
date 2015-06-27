package com.toronto.triageapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
public class Nurse implements Serializable {
	private static final long serialVersionUID = -5513814250886060377L;
	private Triage triage;
	private Patient patient;
	private ArrayList<Patient> patients;
	/**
	 * Constructor, takes a directory where patient_records.txt is stored if it
	 * doesn't exist create a new one and also a triage directory for
	 * initializing the triage.
	 * 
	 * @param dir
	 *            the directory where patient_records.txt is
	 * @param triageDir
	 *            the directory where the triage is saved
	 */
	public Nurse(String dir) {
		this.triage = new Triage(dir);
		this.patients = new ArrayList<Patient>();
		File file = new File(dir + "/" + "patient_records.txt");
		if (file.exists()) {
			this.populate(dir + "/" + "patient_records.txt");
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * An internal method that fills the patients variable with data read from
	 * patient_records.txt
	 * 
	 * @param filePath
	 *            the path of patient_records.txt
	 * @throws FileNotFoundException
	 *             if patient_records.txt doesn't exist throw this exception
	 */
	public void populate(String filePath){
		Scanner scanner;
		try {
			scanner = new Scanner(new FileInputStream(filePath));
			String[] record;
			while (scanner.hasNextLine()) {
				record = scanner.nextLine().split(",");
				int patientNum = Integer.parseInt(record[0]);
				this.patients.add(new Patient(record[1], record[2], patientNum));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Searches for the patient with the given patientNum and sets the local
	 * patient with the same properties as that patient, as well as the arrival
	 * time. Removes the patient from the pool of potential patients if it is
	 * found so that no duplicates will appear. If the local patient was not
	 * saved, put it back into the pool.
	 * 
	 * @param patientNum
	 */
	public void newPatient(int patientNum) {
		int position = -100;
		boolean found = false;
		for (Patient p : this.patients) {
			if (p.getHealthcardNum() == patientNum) {
				position = this.patients.indexOf(p);
				found = true;
				this.patient = new Patient(p);
				this.patient.setArrivalTime(new GregorianCalendar().getTime()
						.toString());
				break;
			}
		}
		System.out.println(this.patient);
		if (found) {
			this.patients.remove(position);
		}
	}
	
	public void newPatient(String name, String birthday, int patientNum)
	{
		this.patient = new Patient(name, birthday, patientNum);
		this.patient.setArrivalTime(new GregorianCalendar().getTime()
				.toString());
	}

	public Triage getTriage() {
		return triage;
	}

	public void setTriage(Triage triage) {
		this.triage = triage;
	}

	/**
	 * Updates the vital signs and symptoms of a patient from given values by
	 * adding them to the ArrayLists.
	 * 
	 * @param temp
	 *            a new temperature reading
	 * @param systolicPressure
	 *            a new systolicPressure reading
	 * @param diastolicPressure
	 *            a new diastolicPressure reading
	 * @param heartRate
	 *            a new heartRate reading
	 * @param symptom
	 *            a new symptom recording
	 */
	public void updatePatient(double temp,double systolicPressure,double diastolicPressure,double rate,String symptoms){
		this.patient.getTemp().add(temp);
		this.patient.getSystolicPressure().add(systolicPressure);
		this.patient.getDiastolicPressure().add(diastolicPressure);
		this.patient.getRate().add(rate);
		this.patient.getSymptoms().add(symptoms);
	}

	/**
	 * Saves the patient into the triage and sets the local patient to null
	 */
	public void savePatient() {
		this.triage.addPatient(new Patient(this.patient));
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * Saves the triage to file
	 */
	public void saveTriage() {
		this.triage.saveToFile();
	}

	/**
	 * Sets the local patient to a patient with the same properties as the found
	 * patient in triage
	 * 
	 * @param patientNum
	 *            the patient number of the loaded patient
	 */
	public void loadPatient(int patientNum) {
		this.patient = new Patient(this.triage.getPatient(patientNum));
	}

	/**
	 * Updates the time a patient is seen by a doctor by adding a new time to
	 * the list, and sets the seen property to true. Separate from
	 * updatePatient() since a patient's vital signs and symptoms can change
	 * without getting seen by a doctor.
	 * 
	 * @param timeSeen
	 */
	public void updatePatientMedication(String prescription,String instructions, boolean seen) {
		this.patient.getMedication().add(prescription);
		this.patient.getInstructions().add(instructions);
		this.patient.getSeenAt().add(Calendar.getInstance().getTime().toString());
		if(seen)
		{
			this.patient.setSeen(true);
		}
        else {
            this.patient.setSeen(false);
        }
	}
	
	public void updatePatientSeenBy(String seenBy, String userName)
	{
		this.patient.getSeenBy().add(seenBy + "///" + userName);
	}
}