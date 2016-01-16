// Version 0.1
// Rainer Winkler
// 08.02.2011 06:02

package de.poaceae.SPG.com;




import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;



/**
 * Contains the logic to call the RXTX module
 * As described in http://www.mikrocontroller.net/articles/Serielle_Schnittstelle_unter_Java
 */

public class SendReceive implements Runnable {

	/**
	 * @param args
	 */
	/**
	public static void main(String[] args)
	{
		Runnable runnable = new SendenEmpfangen();
		new Thread(runnable).start();
		System.out.println("main finished");
	}
	 */
	/**
	 * 
	 */

	CommPortIdentifier serialPortId;
	Enumeration enumComm;
	SerialPort serialPort;
	OutputStream outputStream;
	InputStream inputStream;

	Boolean serialPortGeoeffnet = false;


	int baudrate = 57600;
	int dataBits = SerialPort.DATABITS_8;
	int stopBits = SerialPort.STOPBITS_1;
	int parity = SerialPort.PARITY_NONE;
	String portName = "COM1";

	int secondsRuntime = 20;

	// Own attributes

	boolean stopMe = false;
	public boolean is_send_possible = false;

	ByteCollector byteCollector;

	public void setStopMe(){
		stopMe = true;
	}

	public void setByteCollector(ByteCollector in){
		byteCollector = in;
	}

	public void EinfachSenden()
	{
		System.out.println("Konstruktor: EinfachSenden");
	}

	public void run()
	{
		Integer secondsRemaining = secondsRuntime;
		if (oeffneSerialPort(portName) != true)
			return;

		is_send_possible = true;
		/*
        // Testnachricht
        sendeSerialPort("K");  
		 */      

		/*
	while (secondsRemaining > 0) {
		System.out.println("Sekunden verbleiben: " + secondsRemaining.toString() );
		secondsRemaining--;
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) { }

		}
		 */
		while( !stopMe ){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		schliesseSerialPort();

		is_send_possible = false;
	}

	boolean oeffneSerialPort(String portName)
	{
		Boolean foundPort = false;
		if (serialPortGeoeffnet != false) {
			System.out.println("Serialport bereits geöffnet");
			return false;
		}
		System.out.println("Öffne Serialport");
		enumComm = CommPortIdentifier.getPortIdentifiers();
		while(enumComm.hasMoreElements()) {
			serialPortId = (CommPortIdentifier) enumComm.nextElement();
			if (portName.contentEquals(serialPortId.getName())) {
				foundPort = true;
				break;
			}
		}
		if (foundPort != true) {
			System.out.println("Serialport nicht gefunden: " + portName);
			return false;
		}
		try {
			serialPort = (SerialPort) serialPortId.open("Öffnen und Senden", 500);
		} catch (PortInUseException e) {
			System.out.println("Port belegt");
		}
		try {
			outputStream = serialPort.getOutputStream();
		} catch (IOException e) {
			System.out.println("Keinen Zugriff auf OutputStream");
		}

		try {
			inputStream = serialPort.getInputStream();
		} catch (IOException e) {
			System.out.println("Keinen Zugriff auf InputStream");
		}
		try {
			serialPort.addEventListener(new serialPortEventListener());
		} catch (TooManyListenersException e) {
			System.out.println("TooManyListenersException für Serialport");
		}
		serialPort.notifyOnDataAvailable(true);

		try {
			serialPort.setSerialPortParams(baudrate, dataBits, stopBits, parity);
		} catch(UnsupportedCommOperationException e) {
			System.out.println("Konnte Schnittstellen-Paramter nicht setzen");
		}

		serialPortGeoeffnet = true;
		return true;
	}

	void schliesseSerialPort()
	{
		if ( serialPortGeoeffnet == true) {
			System.out.println("Schließe Serialport");
			serialPort.close();
			serialPortGeoeffnet = false;
		} else {
			System.out.println("Serialport bereits geschlossen");
		}
	}

	public void sendeSerialPort(String nachricht)
	{
		System.out.println("Sende: " + nachricht);
		if (serialPortGeoeffnet != true)
			return;
		try {
			outputStream.write(nachricht.getBytes());
		} catch (IOException e) {
			System.out.println("Fehler beim Senden");
		}
	}

	void serialPortDatenVerfuegbar() {
		try {
			byte[] data = new byte[150];
			int num;
			while(inputStream.available() > 0) {
				num = inputStream.read(data, 0, data.length);
				//System.out.println("Empfange: "+ new String(data, 0, num));
				//System.out.print(new String(data, 0, num));
				byteCollector.collect(data, num);
			}
		} catch (IOException e) {
			System.out.println("Fehler beim Lesen empfangener Daten");
		}
	}

	class serialPortEventListener implements SerialPortEventListener {
		public void serialEvent(SerialPortEvent event) {
			//System.out.println("serialPortEventlistener");
			switch (event.getEventType()) {
			case SerialPortEvent.DATA_AVAILABLE:
				serialPortDatenVerfuegbar();
				break;
			case SerialPortEvent.BI:
			case SerialPortEvent.CD:
			case SerialPortEvent.CTS:
			case SerialPortEvent.DSR:
			case SerialPortEvent.FE:
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			case SerialPortEvent.PE:
			case SerialPortEvent.RI:
			default:
			}
		}
	}

}

