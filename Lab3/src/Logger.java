import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("deprecation")
public class Logger extends UnicastRemoteObject implements IActivity {

	private String log_file_path;

	public Logger(String log_file_path) throws RemoteException {
		super();
		// Set log file path.
		this.log_file_path = log_file_path;

		File file = new File(this.log_file_path);
		if( file.exists() ) file.delete();
		try {
			file.createNewFile();
			System.out.println("Log File Created: " + this.log_file_path);
		} catch (IOException e) {
			System.err.println("Error Creating Log File: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public String execute(String text) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.log_file_path, true))) {
			writer.write(text);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
		return "";
	}
}
