import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Doctor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String doctorName;
	private String userID;
	private String phoneNumber;
	private String emailAddress;
	private String joindate;
	private String slot1day;
	private String slot2day;
	private boolean slot1avail;
	private boolean slot2avail;
	
	private String slot1patient;
	private String slot2patient;
	private String slot1patientid;
	private String slot2patientid;

	public Doctor() {

	}

	public Doctor(String userID) {
		openDoctorFile(userID);
	}
	
	public void openDoctorFile(String userID) {
		try {
			FileInputStream fi = new FileInputStream("./Doctors/Doctor_" + userID + ".txt");
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read object
			Doctor d = (Doctor) oi.readObject();
			this.doctorName = d.doctorName;
			this.userID = d.userID;
			this.emailAddress = d.emailAddress;
			this.phoneNumber = d.phoneNumber;
			this.joindate = d.joindate;
			this.slot1day = d.slot1day;
			this.slot1avail = d.slot1avail;
			this.slot2day = d.slot2day;
			this.slot2avail = d.slot2avail;
			this.slot1patient = d.slot1patient;
			this.slot1patientid = d.slot1patientid;
			this.slot2patient = d.slot2patient;
			this.slot2patientid = d.slot2patientid;
			
			fi.close();
			oi.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public String getDoctorName() {
		return this.doctorName;
	}

	
	/**
	 * splash screen that shows options when a doctor is signed in
	 */
	public void splashScreen() {
		// while(!quitToMain){
		Hospital.CLS();
		
		System.out.println("Welcome Dr. " + doctorName + "\n\n" + "Services:");
		System.out.println("Press 1. Show my Appointments");
		System.out.println("Press 2. Admit a Patient");
		System.out.println("Press 3. Discharge a Patient");
		System.out.println("Press 4. Generate Discharge Report");
		System.out.println("Press 5. To Quit the system.");

		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

		boolean run = true;
		
		try {
		while (run) {
			int command = Integer.parseInt(sc.readLine());
			String patientID = "";

			switch (command) {
			case 1:
				showAppointments(userID);
				break;
			case 2:
				System.out.println("Enter the ID of the patient you want to admit: ");
				patientID = sc.readLine();
				admit(patientID);
				break;
			case 3:
				System.out.println("Enter the ID of the patient you want to discharge: ");
				patientID = sc.readLine();
				discharge(patientID);
				break;
			case 4:
				System.out.println("Enter the ID of the patient who's discharge report you want to generate: ");
				patientID = sc.readLine();
				generateDischargeReport(patientID);
				break;
			case 5:
				run = false;
				System.out.println("Quitting....");
				break;

			}
		}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static void registerDoctor(String userID) // function to insert new patient records linked to the case switch of
	// administrator class
	{
		// takes input
		Doctor d = new Doctor();
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		
		try {
		d.userID = userID;
		System.out.println("Enter Doctor name:");		
		d.doctorName = sc.readLine();	
		System.out.println("Enter Doctor phone number:");
		d.phoneNumber = sc.readLine();
		System.out.println("Enter Doctor Email Address:");
		d.emailAddress = sc.readLine();
		System.out.println("Enter day for Doctor's 1st slot:");
		d.slot1day = sc.readLine();
		d.slot1avail = true;
		System.out.println("Enter day for Doctor's 2st slot:");
		d.slot2day = sc.readLine();
		d.slot2avail = true;
		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
		d.joindate = formatter.format(currentDate.getTime());
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		try {
			FileOutputStream f = new FileOutputStream(new File("./Doctors/Doctor_" + userID + ".txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(d);

			o.close();
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
	}
	
	public String getSlotAvailibility(boolean slot) {
		if(slot) return "Available";
		else return "Booked";
	}
	
	public String getSlot1() {
		
		return this.slot1day;
	}
	
	public String getSlot2() {
		
		return this.slot2day;
	}

	public void showAppointments(String UserID) {
		openDoctorFile(userID);
		System.out.println("Appointments for Doctor " + doctorName + ":\n" + slot1day + " Status: " + getSlotAvailibility(slot1avail) + " Patient name: " + slot1patient + " Patient ID: " + slot1patientid
				+ "\n" + slot2day + " Status: " + getSlotAvailibility(slot2avail) + " Patient Name: " + slot2patient + " Patient ID: "+ slot2patientid + "\n");
	
	}

	public void admit(String patientUserID) {
		Patient p = new Patient(patientUserID);
		p.admitPatient();
		System.out.println("Admitted patient successfully!");

	} 

	public void discharge(String patientUserID) {
		Patient p = new Patient(patientUserID);
		p.dischargePatient();
		System.out.println("Discharged patient successfully!");
	}
	
	public void setSlot1(String doctorID, String patientName, String patientID) {
		try {
		FileInputStream fi = new FileInputStream("./Doctors/Doctor_" + doctorID + ".txt");
		ObjectInputStream oi = new ObjectInputStream(fi);

		// Read object
		Doctor d = (Doctor) oi.readObject();
		d.slot1avail = false;
		d.slot1patient = patientName;
		d.slot1patientid = patientID;
		
		fi.close();
		oi.close();
		
		FileOutputStream f = new FileOutputStream("./Doctors/Doctor_" + doctorID + ".txt");
		ObjectOutputStream o = new ObjectOutputStream(f);

		// Write updated attributes to file
		o.writeObject(d);

		f.close();
		o.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void setSlot2(String doctorID, String patientName, String patientID) {
		try {
		FileInputStream fi = new FileInputStream("./Doctors/Doctor_" + doctorID + ".txt");
		ObjectInputStream oi = new ObjectInputStream(fi);

		// Read object
		Doctor d = (Doctor) oi.readObject();
		d.slot2avail = false;
		d.slot2patient = patientName;
		d.slot2patientid = patientID;
		
		fi.close();
		oi.close();
		
		FileOutputStream f = new FileOutputStream("./Doctors/Doctor_" + doctorID + ".txt");
		ObjectOutputStream o = new ObjectOutputStream(f);

		// Write updated attributes to file
		o.writeObject(d);

		f.close();
		o.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void showAvailableSlots(String DoctorID) { // each doctor has two available slots in a week
	
		System.out.println("The following are the slots for Dr. " + doctorName + "\n1." + slot1day + " Status: " + getSlotAvailibility(slot1avail) + "\n2."
		+ slot2day + " Status: " + getSlotAvailibility(slot2avail) + "\n");

	}

	public void generateDischargeReport(String patientUserID) {
		openDoctorFile(userID);
		Patient p = new Patient(patientUserID);
		
		String dischargeCondition = "";
		String finalDiagnosis = "";
		String illness = "";
		double charges = 0.0;

		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		try {
		System.out.println("Fill out the details of patient: " + p.getPatientName() + " UserID: " + p.getPatientId());
		System.out.println("Enter patient's illness: ");
		illness = sc.readLine(); 
		p.setPatientIllness(illness);
		System.out.println("Enter patient's condition on discharge: ");
		dischargeCondition = sc.readLine();
		System.out.println("Enter patient's final diagnosis: ");
		finalDiagnosis = sc.readLine();
		System.out.println("Enter charges incurred: ");
		charges = Double.parseDouble(sc.readLine());
		p.setCharges(charges);
		
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Discharge Report:\nPatient Name: " + p.getPatientName() + "\nPatient ID: " + userID
				+ "\nAdmission Date: " + p.getAdmitDate() + "\nDischarge Date: " + p.getDischargeDate()
				+ "\nPrimary Care Doctor: " + doctorName + "\nPatient's Illness: " + illness
				+ "\nCondition on Discharge: " + dischargeCondition + "\nFinal Diagnosis: " + finalDiagnosis
				+ "\nCost incurred: " + charges);
	}

	public String toString() {
		return "Log: " + joindate + "\nUser ID: " + userID + "\nDoctor Name: " + doctorName + "\nPhone Number: "
				+ phoneNumber + "\nEmail Address: " + emailAddress;
	}

	public void showDoctorDetails(String userID) {
		try {
			FileInputStream fi = new FileInputStream("./Doctors/Doctor_" + userID + ".txt");
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read object
			Doctor d = (Doctor) oi.readObject();

			System.out.println(d.toString());

			oi.close();
			fi.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateInfo() {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		try {
			FileInputStream fi = new FileInputStream("./Doctors/Doctor_" + userID + ".txt");
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read object
			Doctor d = (Doctor) oi.readObject();

			System.out.println("Press 1 to update User ID\nPress 2 to update Name\nPress 3 to update Phone number"
					+ "\nPress 4 to update Email Address\nPress 5 to Quit");

			boolean run = true;
			while (run) {
				int command = Integer.parseInt(sc.readLine());
				
				switch (command) {
				case 1:
					System.out.println("Enter new ID:");
					d.userID = sc.readLine();
					break;
				case 2:
					System.out.println("Enter new name:");
					d.doctorName = sc.readLine();
					break;
				case 3:
					System.out.println("Enter new phone number:");
					d.phoneNumber = sc.readLine();
					break;
				case 4:
					System.out.println("Enter new email address:");
					d.emailAddress = sc.readLine();
					break;
				case 5:
					run = false;
					System.out.println("Quitting...");
					break;
				default:
					System.out.println("Invalid Argument. Try Again.");
					break;

				}

				fi.close();
				oi.close();

				FileOutputStream f = new FileOutputStream("./Doctors/Doctor_" + userID + ".txt");
				ObjectOutputStream o = new ObjectOutputStream(f);

				// Write updated attributes to file
				o.writeObject(d);

				f.close();
				o.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}