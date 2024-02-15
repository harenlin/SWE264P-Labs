import java.util.ArrayList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// "List all courses" command event handler.

@SuppressWarnings("deprecation")
public class ListAllCoursesHandler extends UnicastRemoteObject implements IActivity {
	protected DataBase objDataBase;
    
	public ListAllCoursesHandler(DataBase objDataBase) throws RemoteException {
		super();
		this.objDataBase = objDataBase;
	}

    public String execute(String param) {
        ArrayList vCourse = this.objDataBase.getAllCourseRecords();
        // Construct a list of course information and return it.
        String sReturn = "";
        for (int i=0; i<vCourse.size(); i++) {
            sReturn += (i == 0 ? "" : "\n") + ((Course) vCourse.get(i)).toString();
        }
        return sReturn;
    }
}

