import java.util.ArrayList;
import java.util.StringTokenizer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// "List courses who registered for a student" command event handler.
@SuppressWarnings("deprecation")
public class ListCoursesRegisteredHandler extends UnicastRemoteObject implements IActivity {
	protected DataBase objDataBase;

	public ListCoursesRegisteredHandler(DataBase objDataBase) throws RemoteException {
		super();
		this.objDataBase = objDataBase;
	}

    public String execute(String param) {
		// Parse the parameters.
        StringTokenizer objTokenizer = new StringTokenizer(param);
        String sSID = objTokenizer.nextToken();

        // Get the list of courses the given student has registered for.
        Student objStudent = this.objDataBase.getStudentRecord(sSID);
        if (objStudent == null) {
            return "Invalid student ID";
        }
        ArrayList vCourse = objStudent.getRegisteredCourses();

        // Construct a list of course information and return it.
        String sReturn = "";
        for (int i=0; i<vCourse.size(); i++) {
            sReturn += (i == 0 ? "" : "\n") + ((Course) vCourse.get(i)).toString();
        }
        return sReturn;
    }
}
