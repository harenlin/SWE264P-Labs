/**
 * @(#) CheckClassOverbookedHandler.java
 *
 * Copyright: Copyright (c) 2024 University of California - Irvine
 *
 */

import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * "Check if a course is overbooked" command event handler.
 */
@SuppressWarnings("deprecation")
public class CheckClassOverbookedHandler extends CommandEventHandler {

    /**
     * Construct "Check if a course is overbooked" command event handler.
     *
     * @param objDataBase reference to the database object
     * @param iCommandEvCode command event code to receive the commands to process
     * @param iOutputEvCode output event code to send the command processing result
     */
    public CheckClassOverbookedHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        super(objDataBase, iCommandEvCode, iOutputEvCode);
    }

    /**
     * Process "Check if a course is overbooked" command event.
     *
     * @param param a string parameter for command
     * @return a string result of command processing
     */
    protected String execute(String param) {
		// System.out.println("Param in OVERBOOK: " + param);

        // Parse the parameters.
        StringTokenizer objTokenizer = new StringTokenizer(param);
		String valid_bit = objTokenizer.nextToken();
		// Check if invalid 
		if( valid_bit.equals("0") ){ 
			return param.substring(2);
		}
        String sSID     = objTokenizer.nextToken();
        String sCID     = objTokenizer.nextToken();
        String sSection = objTokenizer.nextToken();

        // Get the student and course records.
        Student objStudent = this.objDataBase.getStudentRecord(sSID);
        Course objCourse = this.objDataBase.getCourseRecord(sCID, sSection);
        /* if (objStudent == null) {
            return ""; // "Invalid student ID";
        }
        if (objCourse == null) {
            return ""; // "Invalid course ID or course section";
        } */

        String response = "Successful!";
		if( objCourse != null && objCourse.getRegisteredStudents().size() > 3 ) {
			response += " - [Caution] Course Overbooked!";
		}
		return response;	
    }
}
