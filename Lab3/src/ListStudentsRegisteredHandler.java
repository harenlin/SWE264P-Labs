import java.util.ArrayList;
import java.util.StringTokenizer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// "List students who registered for a course" command event handler.
@SuppressWarnings("deprecation")
public class ListStudentsRegisteredHandler extends UnicastRemoteObject implements IActivity {
	protected DataBase objDataBase;

	public ListStudentsRegisteredHandler(DataBase objDataBase) throws RemoteException {
		super();
		this.objDataBase = objDataBase;
	}

    public String execute(String param) {
        // Parse the parameters.
        StringTokenizer objTokenizer = new StringTokenizer(param);
        String sCID     = objTokenizer.nextToken();
        String sSection = objTokenizer.nextToken();

        // Get the list of students who registered for the given course.
        Course objCourse = this.objDataBase.getCourseRecord(sCID, sSection);
        if (objCourse == null) {
            return "Invalid course ID or course section";
        }
        ArrayList vStudent = objCourse.getRegisteredStudents();

        // Construct a list of student information and return it.
        String sReturn = "";
        for (int i=0; i<vStudent.size(); i++) {
            sReturn += (i == 0 ? "" : "\n") + ((Student) vStudent.get(i)).toString();
        }
        return sReturn;
    }
}
