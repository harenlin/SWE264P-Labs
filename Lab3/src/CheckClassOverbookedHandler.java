import java.util.ArrayList;
import java.util.StringTokenizer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("deprecation")
public class CheckClassOverbookedHandler extends UnicastRemoteObject implements IActivity {
	protected DataBase objDataBase;
    
	public CheckClassOverbookedHandler(DataBase objDataBase) throws RemoteException {
		super();
		this.objDataBase = objDataBase;
	}

    public String execute(String param) {
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

