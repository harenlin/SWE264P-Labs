import java.util.ArrayList;
import java.util.StringTokenizer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("deprecation")
public class RegisterStudentHandler extends UnicastRemoteObject implements IActivity {
	protected DataBase objDataBase;
    
	public RegisterStudentHandler(DataBase objDataBase) throws RemoteException {
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
        if (objStudent == null) {
            return "0 " + "Invalid student ID";
        }
        if (objCourse == null) {
            return "0 " + "Invalid course ID or course section";
        }
		
		return "1 " + param; // -> Will go to CheckCourseConflictHandler
    }
}

