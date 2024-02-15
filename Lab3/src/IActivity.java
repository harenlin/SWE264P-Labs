import java.rmi.Remote; 
import java.rmi.RemoteException;  

public interface IActivity extends Remote {  
	public String execute(String param) throws RemoteException;  
}
