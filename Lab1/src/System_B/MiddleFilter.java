/******************************************************************************************************************
* File:MiddleFilter.java
* Project: Lab 1
* Copyright:
*   Copyright (c) 2020 University of California, Irvine
*   Copyright (c) 2003 Carnegie Mellon University
* Versions:
*   1.1 January 2020 - Revision for SWE 264P: Distributed Software Architecture, Winter 2020, UC Irvine.
*   1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
* This class serves as an example for how to use the FilterRemplate to create a standard filter. This particular
* example is a simple "pass-through" filter that reads data from the filter's input port and writes data out the
* filter's output port.
* Parameters: None
* Internal Methods: None
******************************************************************************************************************/

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MiddleFilter extends FilterFramework
{

	private double prevprevAltitude = Double.MIN_VALUE;
	private double prevAltitude = Double.MIN_VALUE;

	private String outputCSVPath;
	private String[] csvHeaders = {"Time", "Velocity", "Altitude", "Pressure", "Temperature"};

	public MiddleFilter(String outputCSVPath) {
		this.outputCSVPath = outputCSVPath;
	}

	public void appendToCSV(String newData) {
		// Format: yyyy:MM:dd:HH:mm:ss:SS,VVV.vvvvv,AAA.aaaaa,PPP.ppppp,TTT.ttttt
		// We can check if there is "*" right here, before appending into the current csv file.
		boolean isWild = newData.contains("*") ? true : false;
		if( isWild ) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.outputCSVPath, true))) {
				writer.write(newData.replace("*", ""));
				writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}	
		}
    }

	public void instantiateCSV(){
		try {
			if( Files.exists(Paths.get(this.outputCSVPath)) ){
				Files.delete(Paths.get(this.outputCSVPath));
				System.out.println("MiddleFilter's output CSV file overwrited!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		String headerLine = "";
		for (int i = 0; i < this.csvHeaders.length; i++) {
            headerLine += this.csvHeaders[i];
			if( i + 1 < this.csvHeaders.length ) headerLine += ",";
		} appendToCSV(headerLine);
	}

	private double truncateDouble(double value, int decimalPlaces) {
        String pattern = "#." + "#".repeat(decimalPlaces);
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String truncatedValue = decimalFormat.format(value);
		// return truncatedValue;
        return Double.parseDouble(truncatedValue);
	}

	/* 
	public void run()
    {
		int bytesread = 0;					// Number of bytes read from the input file.
		int byteswritten = 0;				// Number of bytes written to the stream.
		byte databyte = 0;					// The byte of data read from the file

		// Next we write a message to the terminal to let the world know we are alive...
		System.out.print( "\n" + this.getName() + "::Middle Reading ");

		while (true)
		{
			// Here we read a byte and write a byte
			try
			{
				databyte = ReadFilterInputPort();
				bytesread++;
				WriteFilterOutputPort(databyte);
				byteswritten++;
			}
			catch (EndOfStreamException e)
			{
				ClosePorts();
				System.out.print( "\n" + this.getName() + "::Middle Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
				break;
			}
		}
   	} */

	private double detectWild(double curValue){
		if( prevprevAltitude == Double.MIN_VALUE && prevAltitude == Double.MIN_VALUE ){
			return curValue;
		} else if ( prevprevAltitude == Double.MIN_VALUE && prevAltitude != Double.MIN_VALUE ) {
			if( Math.abs(prevAltitude - curValue) > 100 ) {
				return prevAltitude;
			} 
		} else if ( prevprevAltitude != Double.MIN_VALUE && prevAltitude != Double.MIN_VALUE ){
			if( Math.abs(prevAltitude - curValue) > 100 ) {
				return (prevprevAltitude + prevAltitude) / 2;
			} 
		} return curValue;
	}

	private int writeInt2Buffer(int val){
		int byteswritten = 0;
		byte[] byteArray = ByteBuffer.allocate(4).putInt(val).array();
		for (int i = 0; i < 4; i++) {
			WriteFilterOutputPort(byteArray[i]);
			byteswritten++;
		} return byteswritten;
	}

	private double writeLong2Buffer(long val){
		int byteswritten = 0;
		byte[] byteArray = ByteBuffer.allocate(8).putLong(val).array();
		for (int i = 0; i < 8; i++) {
			WriteFilterOutputPort(byteArray[i]);
			byteswritten++;
		} return byteswritten;
	}

	public void run()
    {
		Calendar TimeStamp = Calendar.getInstance();
		SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SS");

		int MeasurementLength = 8;		// This is the length of all measurements (including time) in bytes
		int IdLength = 4;				// This is the length of IDs in the byte stream
		byte databyte = 0;				// This is the data byte read from the stream
		int bytesread = 0;				// This is the number of bytes read from the stream

		int byteswritten = 0;			// This is the number of bytes written from the stream

		long measurement;				// This is the word used to store all measurements - conversions are illustrated.
		int id;							// This is the measurement id
		int i;							// This is a loop counter

		this.instantiateCSV();
		String curNewLine = new String();

		// First we announce to the world that we are alive...
		System.out.print( "\n" + this.getName() + "::Middle Reading ");

		while (true)
		{
			try
			{
				id = 0;
				for (i=0; i<IdLength; i++ )
				{
					databyte = ReadFilterInputPort();	// This is where we read the byte from the stream...

					// WriteFilterOutputPort(databyte);
					// byteswritten++;

					id = id | (databyte & 0xFF);		// We append the byte on to ID...
					if (i != IdLength-1)				// If this is not the last byte, then slide the
					{									// previously appended byte to the left by one byte
						id = id << 8;					// to make room for the next byte we append to the ID
					}
					bytesread++;						// Increment the byte count
				}

				measurement = 0;
				for (i=0; i<MeasurementLength; i++ )
				{
					databyte = ReadFilterInputPort();

					// WriteFilterOutputPort(databyte);
					// byteswritten++;

					measurement = measurement | (databyte & 0xFF);	// We append the byte on to measurement...
					if (i != MeasurementLength-1)					// If this is not the last byte, then slide the
					{												// previously appended byte to the left by one byte
						measurement = measurement << 8;				// to make room for the next byte we append to the
																	// measurement
					}
					bytesread++;									// Increment the byte count
				}


				if ( id == 0 )
				{
					// System.out.println("ID 0 Occured");
					TimeStamp.setTimeInMillis(measurement);
					curNewLine += TimeStampFormat.format(TimeStamp.getTime()).toString() + ",";
				}

				if( id == 1 || id == 3 ) // Velocity, Pressure
				{
					curNewLine += truncateDouble(Double.longBitsToDouble(measurement), 5) + ",";
				}

				if( id == 2 ) // Altitude
				{
					// Detect Wild
					double originalAltitude = Double.longBitsToDouble(measurement);
					double modifiedAltitude = detectWild(originalAltitude);

					if( originalAltitude == modifiedAltitude ){ // Not wild
						curNewLine += truncateDouble(originalAltitude, 5) + ",";
					} else if ( originalAltitude != modifiedAltitude ) { // Wild
						curNewLine += truncateDouble(originalAltitude, 5) + "*,";
						System.out.println("Wild Point Detected!");
						id = Integer.MAX_VALUE;
						// curNewLine += truncateDouble(modifiedAltitude, 5) + "*,";
					}

					// measurement = Math.round(modifiedAltitude);
					measurement = Double.doubleToLongBits(modifiedAltitude); // originalAltitude;

					prevprevAltitude = prevAltitude;
					prevAltitude = modifiedAltitude; // originalAltitude;
				}
				
				if ( id == 4 )
				{
					// System.out.print( TimeStampFormat.format(TimeStamp.getTime()) + " ID = " + id + " " + Double.longBitsToDouble(measurement) );
					
					// curNewLine += Double.longBitsToDouble(measurement); //.toString();
					curNewLine += truncateDouble(Double.longBitsToDouble(measurement), 5); //.toString();
					appendToCSV(curNewLine);
					curNewLine = "";
				}

				if ( id == 5 )
				{
					// do nothing
				}
				
				byteswritten += writeInt2Buffer(id);
				byteswritten += writeLong2Buffer(measurement);

			}
			catch (EndOfStreamException e)
			{
				ClosePorts();
				System.out.print( "\n" + this.getName() + "::Middle Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten);
				break;
			}
		} // while
   } // run
}
