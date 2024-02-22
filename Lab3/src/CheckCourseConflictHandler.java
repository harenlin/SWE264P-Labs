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
        String sSID     = objTokenizer.nextToken();
        String sCID     = objTokenizer.nextToken();
        String sSection = objTokenizer.nextToken();

        // Get the student and course records.
        Student objStudent = this.objDataBase.getStudentRecord(sSID);
        Course objCourse = this.objDataBase.getCourseRecord(sCID, sSection);

        // Check if inputs are valid.
		if (objStudent == null) {
            return "0 " + "Invalid student ID";
        }
        if (objCourse == null) {
            return "0 " + "Invalid course ID or course section";
        }
		
        // Check if the given course conflicts with any of the courses the student has registered.
        ArrayList vCourse = objStudent.getRegisteredCourses();
        for (int i=0; i<vCourse.size(); i++) {
            if (((Course) vCourse.get(i)).conflicts(objCourse)) {
                return "0 " + "Registration conflicts!";
            }
        }

        // Request validated. Proceed to register.
        return "1 " + param;
    }
}

