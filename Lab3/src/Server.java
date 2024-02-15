import java.rmi.Naming;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

// Do binding right here!

public class Server { 
	public static void main(String args[]) {
		String studentFileName, courseFileName, logFileName;
		// Check the number of parameters.
		if (args.length == 3) {
			studentFileName = args[0];
			courseFileName = args[1];
			logFileName = args[2];
		} else {
			studentFileName = "./../db/Students.txt";
			courseFileName = "./../db/Courses.txt";
			logFileName = "./log_file.txt";
		}

		// Check if input files exists.
		if (new File(studentFileName).exists() == false) {
			System.err.println("Could not find " + studentFileName);
			System.exit(1);
		}
		if (new File(courseFileName).exists() == false) {
			System.err.println("Could not find " + courseFileName);
			System.exit(1);
		}

		try {
			DataBase db;
			db = new DataBase(studentFileName, courseFileName);
			System.out.println("Database Created!");

			ListAllCoursesHandler listAllCoursesHandler	= new ListAllCoursesHandler(db);
			Naming.rebind("rmi://localhost:1999/listAllCoursesHandler", listAllCoursesHandler);

			ListAllStudentsHandler listAllStudentsHandler = new ListAllStudentsHandler(db);
			Naming.rebind("rmi://localhost:1999/listAllStudentsHandler", listAllStudentsHandler);

			ListStudentsRegisteredHandler listStudentsRegisteredHandler = new ListStudentsRegisteredHandler(db);
			Naming.rebind("rmi://localhost:1999/listStudentsRegisteredHandler", listStudentsRegisteredHandler);

			ListCoursesRegisteredHandler listCoursesRegisteredHandler = new ListCoursesRegisteredHandler(db);
			Naming.rebind("rmi://localhost:1999/listCoursesRegisteredHandler", listCoursesRegisteredHandler);

			ListCoursesCompletedHandler listCoursesCompletedHandler = new ListCoursesCompletedHandler(db);
			Naming.rebind("rmi://localhost:1999/listCoursesCompletedHandler", listCoursesCompletedHandler);

			System.err.println("Server ready");
		} catch (Exception e) { 
			System.err.println("Server exception: " + e.toString()); 
			e.printStackTrace(); 
		} 
	} 
} 
