package com.toronto.triageapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author      Ramanan Sivagurunathan
 */

public class Patient implements Serializable, Comparable<Patient>{
	private static final long serialVersionUID = 8945830384768397426L;
	private String name;
	private String birthdate;
	private int healthcardNum;
	private String arrivalTime;
	private ArrayList<String> seenBy;
	private ArrayList<Double> temp;
	private ArrayList<Double> systolicPressure;
	private ArrayList<Double> diastolicPressure;
	private ArrayList<Double> rate;
	private ArrayList<String> symptoms;
	private ArrayList<String> seenAt;
	private boolean seen;
	private ArrayList<String> medication;
	private ArrayList<String> instructions;

    /**
	 * Constructs a Patient with attributes name, birthdate, and health card number
	 */
	public Patient(String name, String birthdate, int healthcardNum) {
		this.name = name;
		this.birthdate = birthdate;
		this.healthcardNum = healthcardNum;
		this.seenBy = new ArrayList<String>();
		this.temp = new ArrayList<Double>();
		this.systolicPressure = new ArrayList<Double>();
		this.diastolicPressure = new ArrayList<Double>();
		this.rate = new ArrayList<Double>();
		this.symptoms = new ArrayList<String>();
		this.seenAt = new ArrayList<String>();
		this.seen = false;
		this.medication = new ArrayList<String>();
		this.instructions = new ArrayList<String>();
	}
	

	public ArrayList<String> getSeenBy() {
			return seenBy;
		}


		public void setSeenBy(ArrayList<String> seenBy) {
			this.seenBy = seenBy;
		}


	/**
	 *
	 * A copy constructor to keep the values of Patient because when we save the local
	 * patient it gets refreshed.
	 *
	 * @param otherPatient
	 *			-another Patient
	 */
	public Patient(Patient otherPatient) {
		this.name = otherPatient.getName();
		this.birthdate = otherPatient.getBirthdate();
		this.healthcardNum = otherPatient.getHealthcardNum();
		this.arrivalTime = otherPatient.getArrivalTime();
		this.seenBy = new ArrayList<String>(otherPatient.getSeenBy());
		this.temp = new ArrayList<Double>(otherPatient.getTemp());
		this.systolicPressure = new ArrayList<Double>(otherPatient.getSystolicPressure());
		this.diastolicPressure = new ArrayList<Double>(otherPatient.getDiastolicPressure());
		this.rate = new ArrayList<Double>(otherPatient.getRate());
		this.symptoms = new ArrayList<String>(otherPatient.getSymptoms());
		this.seenAt = new ArrayList<String>(otherPatient.getSeenAt());
		this.seen = otherPatient.getSeen();
		this.medication = new ArrayList<String>(otherPatient.getMedication());
		this.instructions = new ArrayList<String>(otherPatient.getInstructions());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getSeenAt() {
		return seenAt;
	}

	public void setSeenAt(ArrayList<String> seenAt) {
		this.seenAt = seenAt;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public int getHealthcardNum() {
		return healthcardNum;
	}

	public void setHealthcardNum(int healthcardNum) {
		this.healthcardNum = healthcardNum;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public ArrayList<Double> getTemp() {
		return temp;
	}

	public void setTemp(ArrayList<Double> temp) {
		this.temp = temp;
	}

	public ArrayList<Double> getSystolicPressure() {
		return systolicPressure;
	}

	public void setSystolicPressure(ArrayList<Double> systolicPressure) {
		this.systolicPressure = systolicPressure;
	}

	public ArrayList<Double> getDiastolicPressure() {
		return diastolicPressure;
	}

	public void setDiastolicPressure(ArrayList<Double> diastolicPressure) {
		this.diastolicPressure = diastolicPressure;
	}

	public ArrayList<Double> getRate() {
		return rate;
	}

	public void setRate(ArrayList<Double> rate) {
		this.rate = rate;
	}

	public ArrayList<String> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(ArrayList<String> symptoms) {
		this.symptoms = symptoms;
	}

	public boolean getSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}


	public ArrayList<String> getMedication() {
		return medication;
	}

	public void setMedication(ArrayList<String> medication) {
		this.medication = medication;
	}

	public ArrayList<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(ArrayList<String> instructions) {
		this.instructions = instructions;
	}

	public String toString() {
		return this.name + ", "  + this.birthdate +", "+ this.healthcardNum;
	}

	/**
	 *
	 * Returns an int corresponding to the urgency level of the patient,
	 * based on their age, temperature, heart rate, and blood pressure.
	 *
	 * @return		The patient's urgency level
	 *			
	 */
	public int getUrgency() {
		int counter = 0;

		String[] d = this.birthdate.split("-");
		if (d == null || d.length != 3){
			   return -1;
			}
		int year = Integer.parseInt(d[0]);
		int month = Integer.parseInt(d[1]);
		int day = Integer.parseInt(d[2]);

		if (Calendar.getInstance().get(Calendar.YEAR) - year < 2) {
			counter += 1;
		}	
		else if (Calendar.getInstance().get(Calendar.YEAR) - year == 2) {
			if (Calendar.getInstance().get(Calendar.MONTH) < month) {
				counter +=1;
			}
			else if (Calendar.getInstance().get(Calendar.MONTH) == month) {
				if (Calendar.getInstance().get(Calendar.DATE) <= day)
					counter +=1; 	
			}
		}
		if(this.temp.size()!=0)
		{
			if (this.temp.get(this.temp.size() - 1) >= 39.0) {
				counter += 1;
			}	
			if (this.rate.get(this.rate.size() - 1) >= 100.0
					|| this.rate.get(this.rate.size() - 1) <= 50.0) {
				counter += 1;
			}	
			if (this.systolicPressure.get(this.systolicPressure.size() - 1) >= 140.0
					|| this.diastolicPressure
							.get(this.diastolicPressure.size() - 1) >= 90.0) {
				counter += 1;
			}	
		}
		return counter;

	}	

	/**
	 *
	 * Returns an int value of the difference in urgency between two 
	 * patients. Called implicitly in collections.sort
	 *
	 * @param		otherPatient	
	 * 					-Another patient 
	 * @return		The patient's urgency level
	 *			
	 */
	@Override
	public int compareTo(Patient otherPatient) {
		return this.getUrgency() - otherPatient.getUrgency();
	}
	
}
