import java.util.ArrayList;
import java.util.StringTokenizer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// "List courses a student has completed" command event handler.
@SuppressWarnings("deprecation")
public class ListCoursesCompletedHandler extends UnicastRemoteObject implements IActivity {
	protected DataBase objDataBase;

	public ListCoursesCompletedHandler(DataBase objDataBase) throws RemoteException {
		super();
		this.objDataBase = objDataBase;
	}

    public String execute(String param) {
    	// Parse the parameters.
        StringTokenizer objTokenizer = new StringTokenizer(param);
        String sSID = objTokenizer.nextToken();

        // Get the list of courses the given student has completed.
        Student objStudent = this.objDataBase.getStudentRecord(sSID);
        if (objStudent == null) {
            return "Invalid student ID";
        }
        ArrayList vCourseID = objStudent.getCompletedCourses();

        // Construct a list of course information and return it.
        String sReturn = "";
        for (int i=0; i<vCourseID.size(); i++) {
            String sCID = (String) vCourseID.get(i);
            String sName = this.objDataBase.getCourseName(sCID);
            sReturn += (i == 0 ? "" : "\n") + sCID + " " + (sName == null ? "Unknown" : sName);
        }
        return sReturn;
	}
}
