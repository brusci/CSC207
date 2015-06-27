package com.toronto.triageapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Triage implements Serializable {
	private static final long serialVersionUID = -2993867469742000577L;
	private List<Patient> patients;
	private File file;
	/**
	 * Constructor, takes directory and either loads data from the file or
	 * creates a new file.
	 * 
	 * @param dir
	 *            the directory in which to save the triage data.
	 */
	public Triage(String dir) {
		this.patients = new ArrayList<Patient>();
		String fileName = "patients.txt";
		this.file = new File(dir + "/" + fileName);
		if (file.exists()) {
			// uses method populate to fill the list from the file
			this.populate();
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
	 * If the given patient is not in triage, it adds it, otherwise it replaces
	 * the old patient.
	 * 
	 * @param patient
	 *            the new or updated patient.
	 */
	public void addPatient(Patient patient) {
		Patient p = getPatient(patient.getHealthcardNum());
		if (p != null) {
			removePatient(p);
		}
		patients.add(patient);
	}
	/**
	 * Removes the given patient object from the local list of patients.
	 * @param  patient  the patient to be removed.
	 */
	public void removePatient(Patient patient) {
		patients.remove(patient);
	}

	/**
	 * Returns the patient object of matching patient number.
	 * 
	 * @param patientNum
	 *            the patient number of the desired patient object.
	 * @return x the requested patient object.
	 */
	public Patient getPatient(int patientNum) {
		for (Patient x : this.patients) {
			if (x.getHealthcardNum() == patientNum) {
				return x;
			}
		}
		return null;
	}

	/**
	 * Returns the local list of patients.
	 * 
	 * @return patients the locally stored list of patients.
	 */
	public List<Patient> getPatients() {
		return this.patients;
	}
	/**
	 * Returns an array of string representations of the patients in patients.
	 * @return array of strings representing patients.
	 */
	public String[] toStringArray() {
		String[] str = new String[this.patients.size()];
		for (int i = 0; i < str.length; i++) {
			str[i] = (this.patients.get(i).getName() + "\n" + this.patients
					.get(i).getHealthcardNum());
		}
		return str;
	}

	/**
	 * Saves the local list of patients to the serialized text file.
	 */
	public void saveToFile() {
		try {
			FileWriter fw = new FileWriter(this.file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(Patient p:this.patients)
			{
				String str = p.getHealthcardNum() + "////" + p.getName() + "////" + p.getBirthdate() + "////" + p.getArrivalTime()
						+ "////" + p.getSeen() + "////";
				for(int i=0;i<p.getTemp().size();i++)
				{
					str +=  p.getSeenAt().get(i) + ";/;" + p.getSeenBy().get(i).split("///")[0] + ";/;" + p.getSeenBy().get(i).split("///")[1] + ";/;" + p.getTemp().get(i) + ";/;" + p.getRate().get(i) + ";/;" + p.getDiastolicPressure().get(i)
							+ ";/;" + p.getSymptoms().get(i) + ";/;" + p.getSystolicPressure().get(i) + ";/;"
							+ p.getMedication().get(i) + ";/;" + p.getInstructions().get(i) + ";/;";
				}
				str += "\n";
				bw.write(str);
			}
			bw.close();
 
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Populates this.patients from the textfile in the given filepath.
	 * 
	 * @param filePath
	 *            the location of the file from which to populate the patient
	 *            list.
	 * @throws Exception 
	 */
	private void populate(){
		Scanner scanner;
		try {
			scanner = new Scanner(new FileInputStream(this.file.getAbsoluteFile()));
			String[] record;
			while (scanner.hasNextLine()) {
				record = scanner.nextLine().split("////");
				int patientNum = Integer.parseInt(record[0]);
				Patient p = new Patient(record[1], record[2], patientNum);
				p.setArrivalTime(record[3]);
				p.setSeen(Boolean.parseBoolean(record[4]));
				String[] vitalSigns;
				System.out.println(record.length);
				if(record.length>5)
				{
					vitalSigns = record[5].split(";/;");
					for(int i=0;i<vitalSigns.length;i+=10)
					{
						p.getSeenAt().add(vitalSigns[i]);
						p.getSeenBy().add(vitalSigns[i+1] + "///" + vitalSigns[i+2]);
						p.getTemp().add(Double.parseDouble(vitalSigns[i+3]));
						p.getRate().add(Double.parseDouble(vitalSigns[i+4]));
						p.getDiastolicPressure().add(Double.parseDouble(vitalSigns[i+5]));
						p.getSymptoms().add(vitalSigns[i+6]);
						p.getSystolicPressure().add(Double.parseDouble(vitalSigns[i+7]));
						p.getMedication().add(vitalSigns[i+8]);		
						p.getInstructions().add(vitalSigns[i+9]);
					}
				}
				this.patients.add(new Patient(p));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<Patient> getUnseenByUrgency(){
		if (patients == null || patients.size()==0){
			   return new ArrayList<Patient>();
			}
		ArrayList<Patient> arrayPatients = new ArrayList<Patient>(patients);
		Collections.sort(arrayPatients);
		Collections.reverse(arrayPatients);
		for(Patient p:this.patients)
		{
			if(p.getSeen())
			{
				arrayPatients.remove(p);
			}
		}
		return arrayPatients;
		/*
		ArrayList<Patient> sortedPatients = new ArrayList<Patient>();
		System.out.println(this.patients.size());
		for(int j = 4; j>=0; j--)
		{
			for(int i = 0; i< this.patients.size(); i++)
			{
				if(this.patients.get(i).getUrgency() == j && !this.patients.get(i).getSeen())
				{
					sortedPatients.add(new Patient(this.patients.get(i)));
				}
			}
		}
		return sortedPatients;*/
	}
}
