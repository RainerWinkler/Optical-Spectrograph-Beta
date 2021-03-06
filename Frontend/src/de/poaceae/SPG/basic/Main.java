

/** 
 * This is the main part of the Sprectrograph Beta client
 * @version 0.1
 * @author Rainer Winkler
 * Nov available on Github https://github.com/RainerWinkler/SpectrographBetaClient
 * 
 */

package de.poaceae.SPG.basic;

import de.poaceae.SPG.MeasurementUnit.FirstSpectrograph;
import de.poaceae.SPG.MeasurementUnit.Interface2MeasurementUnit;
import de.poaceae.SPG.com.ByteCollector;
import de.poaceae.SPG.com.SendReceive;
import de.poaceae.SPG.frontend.Frontend;
import de.poaceae.SPG.logic.DataManager;
import de.poaceae.SPG.logic.Logic;

public class Main {



	private static Runnable runnable2;

	/**
	 * The main program
	 */	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Instanciate logic




		//
		//Interface2MeasurementUnit interface2MeasurementUnit = new FirstSpectrograph( );
		DataManager dataManager = new DataManager();
		Logic logic = new Logic(dataManager);
		
		//start_thread_to_communicate(logic);

		//((SendReceive) runnable).sendeSerialPort("H");  

		// Start frontend
		Frontend frontend = new Frontend();
		frontend.startFrontend( logic, dataManager );
		logic.setFrontend(frontend);

	}

	public static void start_thread_to_communicate(Logic logic, String newPortName) {
		// New ByteCollector instance
		ByteCollector byteCollector = new ByteCollector(logic);

		// Start thread for communication
		Runnable runnable = new SendReceive(newPortName);
		( (SendReceive) runnable ).setByteCollector(byteCollector);

		new Thread(runnable).start();

		// Give instance of SendenEmpfangen to logic layer
		logic.setSendReceive((SendReceive) runnable);

		logic.setByteCollector(byteCollector);

		// Testnachricht
		//while ((boolean) runnable).is_send_possible == false;

		//while (!((SendReceive) runnable ).is_send_possible );
		
		runnable2 = runnable;
		
	}

	protected void finalize() throws Throwable{
		((SendReceive) runnable2 ).setStopMe();
	}


}
