import java.rmi.Naming;  

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {  
	public static void main(String[] args) {  
		try {
			// Create a buffered reader using system input stream.
			BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				System.out.println("\nStudent Registration System\n");
				System.out.println("1) List all students");
				System.out.println("2) List all courses");
				System.out.println("3) List students who registered for a course");
				System.out.println("4) List courses a student has registered for");
				System.out.println("5) List courses a student has completed");
				System.out.println("6) Register a student for a course");
				System.out.println("x) Exit");
				System.out.println("\nEnter your choice and press return >> ");

				String sChoice = objReader.readLine().trim();

				// Execute command 1: List all students.
				if (sChoice.equals("1")) {
					// System.out.println("1 called");
		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listAllCoursesHandler");
					System.out.println(stub.execute("")); 
					continue;
				}

				// Execute command 2: List all courses.
				if (sChoice.equals("2")) {
					// System.out.println("2 called");
		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listAllStudentsHandler");
					System.out.println(stub.execute("")); 
					continue;
				}

				// Execute command 3: List students registered for a course.
				if (sChoice.equals("3")) {
					System.out.println("\nEnter course ID and press return >> ");
					String sCID = objReader.readLine().trim();
					System.out.println("\nEnter course section and press return >> ");
					String sSection = objReader.readLine().trim();

					System.out.println("3 called");
		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listStudentsRegisteredHandler");
					System.out.println(stub.execute(sCID + " " + sSection)); 
					continue;
				}

				// Execute command 4: List courses a student has registered for.
				if (sChoice.equals("4")) {
					System.out.println("\nEnter student ID and press return >> ");
					String sSID = objReader.readLine().trim();

					System.out.println("4 called");
		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listCoursesRegisteredHandler");
					System.out.println(stub.execute(sSID)); 
					continue;
				}

				// Execute command 5: List courses a student has completed.
				if (sChoice.equals("5")) {
					System.out.println("\nEnter student ID and press return >> ");
					String sSID = objReader.readLine().trim();

					System.out.println("5 called");
		   			IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1999/listCoursesCompletedHandler");
					System.out.println(stub.execute(sSID)); 
					continue;
				}

				// Execute command 6: Register a student for a course.
				if (sChoice.equals("6")) {
					// Get student ID, course ID, and course section from user.
					System.out.println("\nEnter student ID and press return >> ");
					String sSID = objReader.readLine().trim();
					System.out.println("\nEnter course ID and press return >> ");
					String sCID = objReader.readLine().trim();
					System.out.println("\nEnter course section and press return >> ");
					String sSection = objReader.readLine().trim();

					System.out.println("6 called");
					String middleRes = sSID + " " + sCID + " " + sSection;
		   			IActivity stub1 = (IActivity) Naming.lookup("rmi://localhost:1999/registerStudentHandler");
					middleRes = stub1.execute(middleRes);
		   			IActivity stub2 = (IActivity) Naming.lookup("rmi://localhost:1999/checkCourseConflictHandler");
					middleRes = stub2.execute(middleRes);
		   			IActivity stub3 = (IActivity) Naming.lookup("rmi://localhost:1999/checkClassOverbookedHandler");
					System.out.println(stub3.execute(middleRes));

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
