import java.util.ArrayList;
import java.util.StringTokenizer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("deprecation")
public class CheckCourseConflictHandler extends UnicastRemoteObject implements IActivity {
	protected DataBase objDataBase;
    
	public CheckCourseConflictHandler(DataBase objDataBase) throws RemoteException {
		super();
		this.objDataBase = objDataBase;
	}

    public String execute(String param) {
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
    }
}

