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
		String valid_bit = objTokenizer.nextToken();

		// Check if invalid
		if( valid_bit.equals("0") ){
			return param;
		}
        
		String sSID     = objTokenizer.nextToken();
        String sCID     = objTokenizer.nextToken();
        String sSection = objTokenizer.nextToken();

        // Get the student and course records.
        // Student objStudent = this.objDataBase.getStudentRecord(sSID);
        // Course objCourse = this.objDataBase.getCourseRecord(sCID, sSection);
		this.objDataBase.makeARegistration(sSID, sCID, sSection);
		return param;
    }
}

