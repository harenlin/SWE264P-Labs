import java.util.ArrayList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// "List all students" command event handler.

@SuppressWarnings("deprecation")
public class ListAllStudentsHandler extends UnicastRemoteObject implements IActivity {
	protected DataBase objDataBase;
    
	public ListAllStudentsHandler(DataBase objDataBase) throws RemoteException {
		super();
		this.objDataBase = objDataBase;
	}

    public String execute(String param) {
		ArrayList vStudent = this.objDataBase.getAllStudentRecords();
        // Construct a list of student information and return it.
        String sReturn = "";
        for (int i=0; i<vStudent.size(); i++) {
            sReturn += (i == 0 ? "" : "\n") + ((Student) vStudent.get(i)).toString();
        }
        return sReturn;
    }
}

