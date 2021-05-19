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

public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userID;
	private String patientName;
	private String phoneNumber;
	private String emailAddress;
	private String patientAddress;
	private int patientAge;
	private String patientGender;
	private double charges;
	private String patientIllness;
	private String joindate;
	private boolean admittedStatus;
	private String admitDate;
	private String dischargeDate;

	private String doctorInchargeID;
	private String doctorInchargeName;
	private String slot;

	// empty Patient constructor
	public Patient() {

	}

	// Patient constructor that takes in userID as the input
	public Patient(String userID) {
		openPatientFile(userID);

	}

	public void openPatientFile(String userID) {
		try {
			FileInputStream fi = new FileInputStream("./Patients/Patient_" + userID + ".txt");
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read object
			Patient p = (Patient) oi.readObject();
			this.patientName = p.patientName;
			this.userID = p.userID;
			this.admitDate = p.admitDate;
			this.dischargeDate = p.dischargeDate;
			this.admittedStatus = p.admittedStatus;
			this.emailAddress = p.emailAddress;
			this.phoneNumber = p.phoneNumber;
			this.joindate = p.joindate;
			this.patientAddress = p.patientAddress;
			this.patientAge = p.patientAge;
			this.patientGender = p.patientGender;
			this.patientIllness = p.patientIllness;
			this.charges = p.charges;

			this.doctorInchargeID = p.doctorInchargeID;
			this.doctorInchargeName = p.doctorInchargeName;
			this.slot = p.slot;

			fi.close();
			oi.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream.");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
		}

	}

	public String getPatientName() {
		return this.patientName;
	}

	public String getPatientId() {
		return this.userID;
	}

	public String getAdmitDate() {
		return this.admitDate;
	}

	public String getDischargeDate() {
		return this.dischargeDate;
	}

	public String getPatientIllness() {
		return this.patientIllness;
	}

	public double getCharges() {
		return this.charges;
	}

	public void setPatientIllness(String patientIllness) {
		try {
			FileInputStream fi = new FileInputStream("./Patients/Patient_" + userID + ".txt");
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read object
			Patient p = (Patient) oi.readObject();

			this.patientIllness = patientIllness;

			fi.close();
			oi.close();

			FileOutputStream f = new FileOutputStream("./Patients/Patient_" + userID + ".txt");
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write updated attributes to file
			o.writeObject(p);

			f.close();
			o.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream.");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
		}

	}

	public void setCharges(double charges) {
		try {
			FileInputStream fi = new FileInputStream("./Patients/Patient_" + userID + ".txt");
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read object
			Patient p = (Patient) oi.readObject();

			this.charges = charges;

			fi.close();
			oi.close();

			FileOutputStream f = new FileOutputStream("./Patients/Patient_" + userID + ".txt");
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write updated attributes to file
			o.writeObject(p);

			f.close();
			o.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream.");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
		}

	}

	/**
	 * splash screen that shows options when a patient is signed in
	 */
	public void splashScreen() {
		Hospital.CLS();

		System.out.println("Welcome " + patientName);

		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		boolean run = true;

		while (run) {
			System.out.println(
					"Press 1 to view your details.\nPress 2 to book an appointment.\nPress 3 to view your appointments\nPress 4 to Quit.");
			try {
				int command = Integer.parseInt(sc.readLine());

				switch (command) {
				case 1:
					showPatientDetails(userID);
					break;
				case 2:
					bookAppointment();
					break;
				case 3:
					viewApp(userID);
					break;
				case 4:
					run = false;
					System.out.println("Quitting.");
					break;
				default:
					System.out.println("Invalid argument. Try Again.");
					break;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	static void registerPatient(String userID) // account creation done by the patient
	{
		// takes input
		Patient p = new Patient();
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

		try {
			p.userID = userID;
			System.out.println("Enter Patient name:");
			p.patientName = sc.readLine();
			System.out.println("Enter Patient phone number:");
			p.phoneNumber = sc.readLine();
			System.out.println("Enter Patient Address:");
			p.patientAddress = sc.readLine();
			System.out.println("Enter Patient Email Address");
			p.emailAddress = sc.readLine();
			System.out.println("Enter Patient Age:");
			p.patientAge = Integer.parseInt(sc.readLine());
			System.out.println("Enter Patient Gender:");
			p.patientGender = sc.readLine();
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
			p.joindate = formatter.format(currentDate.getTime());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			FileOutputStream f = new FileOutputStream(new File("./Patients/Patient_" + userID + ".txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(p);

			o.close();
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
	}

	public void bookAppointment() {
		Scanner sc = new Scanner(System.in);

		openPatientFile(userID);

		System.out.println("Place your preferred Doctor's Id: ");
		String doctorID = sc.nextLine();
		Doctor d = new Doctor(doctorID);
		d.showAvailableSlots(doctorID);

		System.out.println(
				"Press the number corespponding to the available slot of your choice. Press 3 to go back to main splashscreen.");
		boolean run = true;
		while (run) {
			int command = sc.nextInt();
			switch (command) {
			case 1:
				d.setSlot1(doctorID, patientName, userID);
				addAppointmentToFile(doctorID, d.getDoctorName(), patientName, userID, d.getSlot1());
				System.out.println("Appointment made succesfully!");
				break;
			case 2:
				d.setSlot2(doctorID, patientName, userID);
				addAppointmentToFile(doctorID, d.getDoctorName(), patientName, userID, d.getSlot2());
				System.out.println("Appointment made succesfully!");
				break;
			case 3:
				splashScreen();
				break;
			default:
				System.out.println("Invalid Argument. Try Again");
				break;
			}
		}
		sc.close();

	}

	public void addAppointmentToFile(String doctorInchargeID, String doctorInchargeName, String patientName,
			String patientid, String slot) {
		try {
			FileInputStream fi = new FileInputStream("./Patients/Patient_" + userID + ".txt");
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read object
			Patient p = (Patient) oi.readObject();

			p.doctorInchargeID = doctorInchargeID;
			p.doctorInchargeName = doctorInchargeName;
			p.slot = slot;

			fi.close();
			oi.close();

			FileOutputStream f = new FileOutputStream("./Patients/Patient_" + userID + ".txt");
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write updated attributes to file
			o.writeObject(p);

			f.close();
			o.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream.");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
		}

	}

	public void showPatientDetails(String userID) {
		try {
			FileInputStream fi = new FileInputStream("./Patients/Patient_" + userID + ".txt");
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read object
			Patient p = (Patient) oi.readObject();

			System.out.println(p.toString());

			oi.close();
			fi.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
		}
	}

	public void admitPatient() {
		try {
			FileInputStream fi = new FileInputStream("./Patients/Patient_" + userID + ".txt");
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read object
			Patient p = (Patient) oi.readObject();
			this.admittedStatus = p.admittedStatus;

			fi.close();
			oi.close();

			FileOutputStream f = new FileOutputStream("./Patients/Patient_" + userID + ".txt");
			ObjectOutputStream o = new ObjectOutputStream(f);

			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
			p.admitDate = formatter.format(currentDate.getTime());
			p.admittedStatus = true;
			// Write updated attributes to file
			o.writeObject(p);

			f.close();
			o.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream.");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
		}
	}

	public void dischargePatient() {
		try {
			FileInputStream fi = new FileInputStream("./Patients/Patient_" + userID + ".txt");
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read object
			Patient p = (Patient) oi.readObject();
			this.admittedStatus = p.admittedStatus;

			fi.close();
			oi.close();

			FileOutputStream f = new FileOutputStream("./Patients/Patient_" + userID + ".txt");
			ObjectOutputStream o = new ObjectOutputStream(f);

			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
			p.dischargeDate = formatter.format(currentDate.getTime());
			p.admittedStatus = false;
			// Write updated attributes to file
			o.writeObject(p);

			f.close();
			o.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream.");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
		}
	}

	public void updateInfo() { // update info method for administrator to be able to change details and add in
								// details such as patientIllness and charges
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		try {
			FileInputStream fi = new FileInputStream("./Patients/Patient_" + userID + ".txt");
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read object
			Patient p = (Patient) oi.readObject();

			System.out.println(
					"Press 1 to update User ID\nPress 2 to update Name\nPress 3 to update Age\nPress 4 to update Gender\n"
							+ "Press 5 to update Phone numer\nPress 6 to update Email Address\nPress 7 to update Address\n"
							+ "Press 8 to update Patient's Illness\nPress 9 to update Charges\nPress 10 to Quit");

			boolean run = true;

			while (run) {
				int command = Integer.parseInt(sc.readLine());

				switch (command) {
				case 1:
					System.out.println("Enter new ID:");
					p.userID = sc.readLine();
					break;
				case 2:
					System.out.println("Enter new name:");
					p.patientName = sc.readLine();
					break;
				case 3:
					System.out.println("Enter new age:");
					p.patientAge = Integer.parseInt(sc.readLine());
					break;
				case 4:
					System.out.println("Enter updated gender:");
					p.patientGender = sc.readLine();
					break;
				case 5:
					System.out.println("Enter new phone number:");
					p.phoneNumber = sc.readLine();
					break;
				case 6:
					System.out.println("Enter new email address:");
					p.emailAddress = sc.readLine();
					break;
				case 7:
					System.out.println("Enter new address:");
					p.patientAddress = sc.readLine();
					break;
				case 8:
					p.patientIllness = sc.readLine();
					break;
				case 9:
					System.out.println("Enter updated charges:");
					p.charges = Double.parseDouble(sc.readLine());
					break;
				case 10:
					run = false;
					System.out.println("Quitting...");
					break;
				default:
					System.out.println("Invalid Argument. Try Again.");
					break;

				}

				fi.close();
				oi.close();

				FileOutputStream f = new FileOutputStream("./Patients/Patient_" + userID + ".txt");
				ObjectOutputStream o = new ObjectOutputStream(f);

				// Write updated attributes to file
				o.writeObject(p);

				f.close();
				o.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream.");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
		}

	}

	public String toString() { // prints details of the patient
		openPatientFile(userID);

		return "Log: " + joindate + "\nUser ID: " + userID + "\nPatient Name: " + patientName + "\nPhone Number: "
				+ phoneNumber + "\nEmail Address: " + emailAddress + "\nAddress: " + patientAddress + "\nAge: "
				+ patientAge + "\nGender: " + patientGender + "\nPatient Illness: " + patientIllness + "\nCharges: "
				+ charges + "\nAdmission Date: " + admitDate + "\nDischarge Date: " + dischargeDate + "\n";
	}

	public void viewApp(String userID) {
		openPatientFile(userID);
		System.out.println("Your Appointment details are: " + "\nDoctor Incharge: " + doctorInchargeName + "," + " ID: "
				+ doctorInchargeID + "\nDate & Time for your slot: " + slot + "\n");

	}

}
