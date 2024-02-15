import java.rmi.Naming;  

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {  
	public static void main(String[] args) {  
		try {
			// Create a buffered reader using system input stream.
			BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));

		   	IActivity loggerStub = (IActivity) Naming.lookup("rmi://localhost:1999/logger");

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
				loggerStub.execute(prompt);

				String sChoice = objReader.readLine().trim();

				// Execute command 1: List all students.
				if (sChoice.equals("1")) {
		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listAllCoursesHandler");
					String res = stub.execute(""); 
					System.out.println(res);
					loggerStub.execute(res);
					continue;
				}

				// Execute command 2: List all courses.
				if (sChoice.equals("2")) {
		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listAllStudentsHandler");
					String res = stub.execute(""); 
					System.out.println(res);
					loggerStub.execute(res);
					continue;
				}

				// Execute command 3: List students registered for a course.
				if (sChoice.equals("3")) {
					System.out.println("\nEnter course ID and press return >> ");
					loggerStub.execute("\nEnter course ID and press return >> ");
					String sCID = objReader.readLine().trim();
					System.out.println("\nEnter course section and press return >> ");
					loggerStub.execute("\nEnter course section and press return >> ");
					String sSection = objReader.readLine().trim();

		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listStudentsRegisteredHandler");
					String res = stub.execute(sCID + " " + sSection); 
					System.out.println(res);
					loggerStub.execute(res);
					continue;
				}

				// Execute command 4: List courses a student has registered for.
				if (sChoice.equals("4")) {
					System.out.println("\nEnter student ID and press return >> ");
					loggerStub.execute("\nEnter student ID and press return >> ");
					String sSID = objReader.readLine().trim();

		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listCoursesRegisteredHandler");
					String res = stub.execute(sSID);
					System.out.println(res);
					loggerStub.execute(res);
					continue;
				}

				// Execute command 5: List courses a student has completed.
				if (sChoice.equals("5")) {
					System.out.println("\nEnter student ID and press return >> ");
					loggerStub.execute("\nEnter student ID and press return >> ");
					String sSID = objReader.readLine().trim();

		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listCoursesCompletedHandler");
					String res = stub.execute(sSID);
					System.out.println(res);
					loggerStub.execute(res);
					continue;
				}

				// Execute command 6: Register a student for a course.
				if (sChoice.equals("6")) {
					// Get student ID, course ID, and course section from user.
					System.out.println("\nEnter student ID and press return >> ");
					loggerStub.execute("\nEnter student ID and press return >> ");
					String sSID = objReader.readLine().trim();
					System.out.println("\nEnter course ID and press return >> ");
					loggerStub.execute("\nEnter course ID and press return >> ");
					String sCID = objReader.readLine().trim();
					System.out.println("\nEnter course section and press return >> ");
					loggerStub.execute("\nEnter course section and press return >> ");
					String sSection = objReader.readLine().trim();

					String middleRes = sSID + " " + sCID + " " + sSection;
		   			IActivity stub1 = (IActivity) Naming.lookup("rmi://localhost:1999/registerStudentHandler");
					middleRes = stub1.execute(middleRes);
		   			IActivity stub2 = (IActivity) Naming.lookup("rmi://localhost:1999/checkCourseConflictHandler");
					middleRes = stub2.execute(middleRes);
		   			IActivity stub3 = (IActivity) Naming.lookup("rmi://localhost:1999/checkClassOverbookedHandler");
					String res = stub3.execute(middleRes);
					System.out.println(res);
					loggerStub.execute(res);
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
