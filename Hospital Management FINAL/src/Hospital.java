import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Hospital {

	// name of the hospital
	private static String name;
	/**
	 * Contains login details for Administrators, Doctors, Patients, Receptionists
	 * and Accountants
	 */
	private static File credentials = new File("./Credentials/credentials.txt");

	public static int userCount() {
		return new File("./Doctors").listFiles().length
				+ new File("./Patients").listFiles().length
				+ new File("./Administrators").listFiles().length
				+ new File("./Receptionists").listFiles().length
				+ new File("./Accountants").listFiles().length;
	}

	public String getName() {
		return name;
	}

	// Hospital constructor that takes a credentials file and hospital name
	Hospital(String name) {
		Hospital.name = name;
		Hospital.credentials = new File("./Credentials/" + name + "_credentials.txt");
		try {
			credentials.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method creates the first admin account in the credentials file is empty
	 */
	public static void startup() {
		CLS();
		if (userCount() == 0) {
			setCredentials("admin", "Administrator");
			System.out.println("First-time administrator account setup:");
			Administrator.registerAdministrator("0000");
			System.out.println(
					"Upon first startup a default admin " + "account has been created." + "\n" + "User ID: 0000\n"
							+ "Password: admin" + "\n" + "\n" + "Please record these credentials for safekeeping.");
			Wait(3);
		}
		// if(doctorsList does not exist) generate doctorsList
		// if(patientsList does not exist) generate patientsList
		Hospital.logInScreen();
	}

	/**
	 * creates account after checking for a valid userID
	 * 
	 * @param userID      of new Account
	 * @param password    of new Account
	 * @param accountType of new Account
	 */
	public static String setCredentials(String password, String accountType) {
		
		FileWriter fr;
		try {
			fr = new FileWriter(credentials, true);
			BufferedWriter myWriter = new BufferedWriter(fr);
			myWriter.write(String.format("%04d", userCount()) + " " + password + " " + accountType + "\n");
			myWriter.close();
			return userCount() + "";
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * login method which reads username and password
	 * 
	 * @return the type of user associated with the logged in account
	 */
	public static void logInScreen() {
		
		String accountType = "";
		String userID = "";
		String password = "";
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			CLS();
			while (true) {

				// take in input
				try {
					System.out.println("Enter your User ID for " + name + " Hospital Services");
					userID = sc.readLine();
					System.out.println("Enter your password");
					password = sc.readLine();
					accountType = validate(userID + "", password);
					if (accountType == null || accountType.contains("null") || accountType.contains("false"))
						System.out.println("Incorrect User ID or password.");
					else
						break;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}

			switch (accountType) {
			case "Doctor": {
				new Doctor(userID + "").splashScreen();
				break;
			}
			case "Patient": {
				new Patient(userID + "").splashScreen();
				break;
			}
			case "Receptionist": {
				new Receptionist(userID + "").splashScreen();
				break;
			}
			case "Administrator": {
				new Administrator(userID + "").splashScreen();
				break;
			}
			case "Accountant": {
				new Accountant(userID + "").splashScreen();
				break;
			}
			default:
				;
			}
		}
	}

	/**
	 * checks whether userID and password match or not
	 * 
	 * @param userID   to be validated
	 * @param password to be validated
	 * @return user type if correct ID and password, false if incorrect password and
	 *         null if non-existent userID input
	 */
	public static String validate(String userID, String password) {
		String[] line = new String[3];
		Scanner sc;
		try {
			sc = new Scanner(credentials);
			while (sc.hasNextLine()) {
				line = sc.nextLine().split(" ");
				if (line[0].contentEquals(userID)) {
					if (line[1].contentEquals(password)) {
						// return account type if login succeeded
						return line[2];
					}
					return "false";
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * clears screen by pushing 20 empty lines
	 */
	public static void CLS() {
		for (int i = 0; i < 6; i++) {
			System.out.println();
		}
	}

	/**
	 * function that waits so the user may read a message before it is wiped
	 * 
	 * @param i number of seconds to wait
	 */
	public static void Wait(int i) {
		try {
			TimeUnit.SECONDS.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public File edit(File editee, String replaceeLine, String replacer) {
		File temp = new File("temp.txt");
		Scanner sc;
		FileWriter myWriter;
		try {
			sc = new Scanner(editee);
			myWriter = new FileWriter(temp);
			while (sc.hasNextLine()) {
				if (sc.nextLine().contentEquals(replaceeLine)) {
					myWriter.write(replacer);
				} else {
					myWriter.write(sc.nextLine());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	public void editPatientFile() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the ID of the patient file you want to edit: ");
		String id = sc.nextInt() + "";
		Patient p = new Patient(id);
		p.updateInfo();

	}
}