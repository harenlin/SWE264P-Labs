/**
 * @(#) CheckCourseConflictHandler.java
 *
 * Copyright: Copyright (c) 2024 University of California - Irvine
 *
 */

import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * "Register a student for a course" command event handler.
 */
@SuppressWarnings("deprecation")
public class CheckCourseConflictHandler extends CommandEventHandler {

    /**
     * Construct "Check if a student for a course having conflict" command event handler.
     *
     * @param objDataBase reference to the database object
     * @param iCommandEvCode command event code to receive the commands to process
     * @param iOutputEvCode output event code to send the command processing result
     */
    public CheckCourseConflictHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        super(objDataBase, iCommandEvCode, iOutputEvCode);
    }

    /**
     * Process "Check if a student for a course having conflict" command event.
     *
     * @param param a string parameter for command
     * @return a string result of command processing
     */
    protected String execute(String param) {
		// System.out.println("Param in CONFLICT: " + param);

        // Parse the parameters.
        StringTokenizer objTokenizer = new StringTokenizer(param);
		String valid_bit = objTokenizer.nextToken();
		// Check if invalid 
		if( valid_bit.equals("0") ){ 
			return param;
		}

        String sSID     = objTokenizer.nextToken();
        String sCID     = objTokenizer.nextToken();
        String sSection = objTokenizer.nextToken();

        // Get the student and course records.
        Student objStudent = this.objDataBase.getStudentRecord(sSID);
        Course objCourse = this.objDataBase.getCourseRecord(sCID, sSection);

        // Check if the given course conflicts with any of the courses the student has registered.
        ArrayList vCourse = objStudent.getRegisteredCourses();
        for (int i=0; i<vCourse.size(); i++) {
            if (((Course) vCourse.get(i)).conflicts(objCourse)) {
                return "0 " + "Registration conflicts!";
            }
        }

        // Request validated. Proceed to register.
        this.objDataBase.makeARegistration(sSID, sCID, sSection);
        return param; // "Successful!";

		// -> Will go to CheckClassOverbookedHandler
    }
}
