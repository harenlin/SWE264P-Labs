import java.rmi.Naming;  

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Client {  

	public static String log_file_path;

	public static void initLog(String log_path) {
		log_file_path = log_path;
		File file = new File(log_file_path);
		if( file.exists() ) file.delete();
		try {
			file.createNewFile();
			System.out.println("Log File Created: " + log_file_path);
		} catch (IOException e) {
			System.err.println("Error Creating Log File: " + e.getMessage());
		}
	}

	public static void insertLog(String text) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(log_file_path, true))) {
			writer.write(text);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}

	public static void main(String[] args) {  

		initLog(args[0]);

		try {
			// Create a buffered reader using system input stream.
			BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				String prompt = "\nStudent Registration System\n\n"
					+ "1) List all students\n"
					+ "2) List all courses\n"
					+ "3) List students who registered for a course\n"
					+ "4) List courses a student has registered for\n"
					+ "5) List courses a student has completed\n"
					+ "6) Register a student for a course\n"
					+ "x) Exit\n"
					+ "\nEnter your choice and press return >> \n";
				System.out.println(prompt);
				insertLog(prompt);

				String sChoice = objReader.readLine().trim();

				// Execute command 1: List all students.
				if (sChoice.equals("1")) {
		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listAllCoursesHandler");
					String res = stub.execute(""); 
					System.out.println(res);
					insertLog(res);
					continue;
				}

				// Execute command 2: List all courses.
				if (sChoice.equals("2")) {
		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listAllStudentsHandler");
					String res = stub.execute(""); 
					System.out.println(res);
					insertLog(res);
					continue;
				}

				// Execute command 3: List students registered for a course.
				if (sChoice.equals("3")) {
					System.out.println("\nEnter course ID and press return >> ");
					insertLog("\nEnter course ID and press return >> ");
					String sCID = objReader.readLine().trim();
					System.out.println("\nEnter course section and press return >> ");
					insertLog("\nEnter course section and press return >> ");
					String sSection = objReader.readLine().trim();

		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listStudentsRegisteredHandler");
					String res = stub.execute(sCID + " " + sSection); 
					System.out.println(res);
					insertLog(res);
					continue;
				}

				// Execute command 4: List courses a student has registered for.
				if (sChoice.equals("4")) {
					System.out.println("\nEnter student ID and press return >> ");
					insertLog("\nEnter student ID and press return >> ");
					String sSID = objReader.readLine().trim();

		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listCoursesRegisteredHandler");
					String res = stub.execute(sSID);
					System.out.println(res);
					insertLog(res);
					continue;
				}

				// Execute command 5: List courses a student has completed.
				if (sChoice.equals("5")) {
					System.out.println("\nEnter student ID and press return >> ");
					insertLog("\nEnter student ID and press return >> ");
					String sSID = objReader.readLine().trim();

		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listCoursesCompletedHandler");
					String res = stub.execute(sSID);
					System.out.println(res);
					insertLog(res);
					continue;
				}

				// Execute command 6: Register a student for a course.
				if (sChoice.equals("6")) {
					// Get student ID, course ID, and course section from user.
					System.out.println("\nEnter student ID and press return >> ");
					insertLog("\nEnter student ID and press return >> ");
					String sSID = objReader.readLine().trim();
					System.out.println("\nEnter course ID and press return >> ");
					insertLog("\nEnter course ID and press return >> ");
					String sCID = objReader.readLine().trim();
					System.out.println("\nEnter course section and press return >> ");
					insertLog("\nEnter course section and press return >> ");
					String sSection = objReader.readLine().trim();

					String middleRes = sSID + " " + sCID + " " + sSection;
		   			IActivity stub1 = (IActivity) Naming.lookup("rmi://localhost:1999/checkCourseConflictHandler");
					middleRes = stub1.execute(middleRes);
		   			IActivity stub2 = (IActivity) Naming.lookup("rmi://localhost:1999/registerStudentHandler");
					middleRes = stub2.execute(middleRes);
		   			IActivity stub3 = (IActivity) Naming.lookup("rmi://localhost:1999/checkClassOverbookedHandler");
					String res = stub3.execute(middleRes);
					System.out.println(res);
					insertLog(res);
					continue;
				}

				// Terminate this client.
				if (sChoice.equalsIgnoreCase("X")) {
					break;
				}
			}

			// Clean up the resources.
			objReader.close();
		}
		catch (Exception e) {
			// Dump the exception information for debugging.
		    System.err.println("Client exception: " + e.toString()); 
			e.printStackTrace();
			System.exit(1);
		}
	} 
}
