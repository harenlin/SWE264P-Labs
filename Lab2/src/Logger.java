/**
 * @(#)Logger.java
 *
 * Copyright: Copyright (c) 2024 University of California - Irvine
 *
 */


import java.util.Observable;
import java.util.Observer;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class represents a client output component which is responsible for displaying text messages
 * onto the console upon receiving show events. Show events are expected to carry a 
 * <code>String</code> object as its parameter that is to be displayed. This component need to
 * subscribe to those events to receive them, which is done at the time of creation.
 *
 * @author Hao-Lun Lin
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class Logger implements Observer {

	private String log_file_path;

	/**
	 * Constructs a client output component. A new client output component subscribes to show events
	 * at the time of creation.
	 */
	public Logger(String log_file_path) {
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

		// Subscribe to SHOW event.
		EventBus.subscribeTo(EventBus.EV_SHOW, this);
	}

	/**
	 * Logger writes texts into the log file.
	 * 
	 * @param param a parameter object of the event. (has been cast to appropriate data type)
	 */
	@SuppressWarnings("unchecked")
	private void writeTxt(String text) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.log_file_path, true))) {
			writer.write(text);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}

	/**
	 * Event handler of this client output component. On receiving a show event, the attached
	 * <code>String</code> object is displayed onto the console.
	 *
	 * @param event an event object. (caution: not to be directly referenced)
	 * @param param a parameter object of the event. (to be cast to appropriate data type)
	 */
	@SuppressWarnings("unchecked")
	public void update(Observable event, Object param) {
		this.writeTxt(param.toString());
	}
}
