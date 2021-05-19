import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Administrator {

	private String name;
	private String userID;
	private String phoneNumber;
	private String emailAddress;
	private File file;

	/**
	 * splash screen that shows options when an admin user is signed in
	 */
	public void splashScreen() {
		int command;
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			Hospital.CLS();
			System.out.println("Welcome " + name + "\n");
			System.out.println("1. Create a new Account");
			System.out.println("2. Show all Patients");
			System.out.println("3. Show all Doctors");
			System.out.println("4. Sign out");

			while (true) {
				try {
					command = Integer.parseInt(sc.readLine());
					System.out.println(command);
					switch (command) {
					case 1: 
						newAccountScreen();
						break;				
					case 2:
						showAllPatients();
						break;
					case 3:
						showAllDoctors();
						break;
					case 4:
						return;						
					default:
						System.out.println("Invalid argument.");
						break;
					}
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	Administrator() {

	}

	Administrator(String userID) {
		this.file = new File("./Administrators/Administrator_" + userID + ".txt");

		try {
			Scanner sc = new Scanner(file);
			String line[] = {};
			while (true) {
				while (sc.hasNextLine()) {
					line = sc.nextLine().split(":");

					if (line[0].contentEquals("Name"))
						name = line[1];
					else if (line[0].contentEquals("Phone Number"))
						phoneNumber = line[1];
					else if (line[0].contentEquals("Email Address")) {
						emailAddress = line[1];
						return;
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void registerAdministrator(String userID) {
		Administrator a = new Administrator();
		a.userID = userID;
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.println("Enter your name");
			a.name = sc.readLine();
			System.out.println("Enter your phone number");
			a.phoneNumber = sc.readLine();
			System.out.println("Enter your email address:");
			a.emailAddress = sc.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}

		a.file = new File("./Administrators/Administrator_" + a.userID + ".txt");
		try {
			FileWriter fr = new FileWriter(a.file, true);
			BufferedWriter myWriter = new BufferedWriter(fr);
			myWriter.write("Name:" + a.name + "\n");
			myWriter.write("Phone Number:" + a.phoneNumber + "\n");
			myWriter.write("Email Address:" + a.emailAddress + "\n");
			myWriter.close();
			System.out.println("Account registered successfully.");
		} catch (IOException e) {
			System.out.println("Account registration failed.");
			e.printStackTrace();
		}

	}

	/**
	 * method that creates a new account for a user
	 */
	public void newAccountScreen() {

		Scanner sc = new Scanner(System.in);

		int command = 7;
		String accountType = "";
		while (!(command > -1 && command < 6)) {
			Hospital.CLS();
			System.out.println("Enter the number for the type of account you want to create:\n" + "1. Patient\n"
					+ "2. Doctor\n" + "3. Administrator\n" + "4. Receptionist\n" + "5. Accountant\n"
					+ "\n0. Quit Account Creation\n");
			command = sc.nextInt();

			switch (command) {
			case 1: {
				accountType = "Patient";
				break;
			}
			case 2: {
				accountType = "Doctor";
				break;
			}
			case 3: {
				accountType = "Administrator";
				break;
			}
			case 4: {
				accountType = "Receptionist";
				break;
			}
			case 5: {
				accountType = "Accountant";
				break;
			}
			case 0:
				return;
			}
		}

		System.out.println("Enter the password you want to use.");
		BufferedReader sc2 = new BufferedReader(new InputStreamReader(System.in));
		String password = "";
		try {
			password = sc2.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String result = String.format("%04d", Integer.parseInt(Hospital.setCredentials(password, accountType)));
		if (!(result == null)) {
			System.out.println("Account created. Your User ID is " + result);
			switch (command) {
			case 1: {
				Patient.registerPatient(result);
				break;
			}
			case 2: {
				Doctor.registerDoctor(result);
				break;
			}
			case 3: {
				Administrator.registerAdministrator(result);
				break;
			}
			case 4: {
				Receptionist.registerReceptionist(result);
				break;
			}
			case 5: {
				Accountant.registerAccountant(result);
				break;
			}
			}
		} else
			System.out.println("Account creation failed.");

		this.splashScreen();
	}

	public void showAllPatients() {
		//read file name
		File[] total=new File("./Patients").listFiles();
		System.out.println("Patients List:");
		for(int i=0; i<total.length; i++) {
			System.out.println(new Patient(total[i].getName().split("_")[1].substring(0,4)).getPatientName());
		}
	}
	
	public void showAllDoctors() {
		//read file name
		File[] total=new File("./Doctors").listFiles();
		System.out.println("Doctors List:");
		for(int i=0; i<total.length; i++) {
			System.out.println(new Doctor(total[i].getName().split("_")[1].substring(0,4)).getDoctorName());
		}
	}
}