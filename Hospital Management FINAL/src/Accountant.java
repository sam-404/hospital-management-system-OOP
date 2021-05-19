import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Accountant {

	private String userID;
	private String name;
	private String phoneNumber;
	private File file;

	Accountant() {

	}

	/**
	 * splash screen that shows options when a accountant is signed in
	 */
	public void splashScreen() {
		System.out.println("Welcome, " + name + "\n\n");
		System.out.println("1. Notifications");
		System.out.println("2. Sign out");

		Scanner sc = new Scanner(System.in);
		int command = sc.nextInt();
		System.out.println(command);
		switch (command) {
		case 1:
			displayNotifications();
			break;
		case 2:
			return;
		}
	}

	private void displayNotifications() {
		Hospital.CLS();

		try {
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				System.out.println(myReader.nextLine());
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	Accountant(String userID) {
		this.file = new File("./Accountants/Accountant_" + userID + ".txt");
		try {
			Scanner sc = new Scanner(file);
			String line[];
			while (true) {
				line = sc.nextLine().split(" ");
				if (line[0].contentEquals("Name:"))
					name = line[1];
				else if (line[0].contentEquals("Phone")) {
					phoneNumber = line[2];
					break;
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void registerAccountant(String userID) {
		Accountant a = new Accountant();
		a.userID = userID;
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.println("Enter your name");
			a.name = sc.readLine();
			System.out.println("Enter your phone number");
			a.phoneNumber = sc.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}

		a.file = new File("./Accountants/Accountant_" + a.userID + ".txt");
		try {
			FileWriter fr = new FileWriter(a.file, true);
			BufferedWriter myWriter = new BufferedWriter(fr);
			myWriter.write("Name: " + a.name + "\n");
			myWriter.write("Phone Number: " + a.phoneNumber + "\n");
			myWriter.close();
			System.out.println("Account registered successfully.");
		} catch (IOException e) {
			System.out.println("Account registration failed.");
			e.printStackTrace();
		}

	}
}