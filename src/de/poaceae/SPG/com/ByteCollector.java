package de.poaceae.SPG.com;

import java.util.ArrayList;

import de.poaceae.SPG.logic.Logic;

/** 
 * The first class to collect the data that is read from the spectrograph
 * 
 * @author Rainers Konto
 *
 */
public class ByteCollector {
	final int maxLength = 100000;
	private byte[] completeData;
	private int completeDataWritePointer;
	private int completeDataEndPointer;	
	private int completeDataReadPointer;
	private int countNumberOfFoundFF;
	private boolean FiveFFFound;
	/**
	 * The used integration time in ms as has been send from the spectrograph
	 * back to the client
	 */
	private int actualIntegrationTimeIn_ms;
	private int countCharRead;
	private String actualIntegrationTimeIn_ms_string;
	public boolean allDataIsReveived;
	private Logic logic;

	public int getactualIntegrationTimeIn_ms(){
		return actualIntegrationTimeIn_ms;
	}

	public ByteCollector(Logic in){
		logic = in;
		completeData = new byte[maxLength];
		initializeWrite();
	}

	public void initializeWrite(){
		completeDataWritePointer = 0;
		completeDataEndPointer = 0;
		countNumberOfFoundFF = 0;
		FiveFFFound = false;
		actualIntegrationTimeIn_ms = 0;
		countCharRead = 0;
		actualIntegrationTimeIn_ms_string = "";
		allDataIsReveived = false;
	}
/** 
 * Main collection of data
 * 
 * In the first steps the integration time in ms is read.
 * The data is transfered in separate parts.
 * At the end five FF are expected.
 * 
 * Errors may occur if six FF are transfered.
 * 
 * @param data a byte array with the collected data
 * @param num the length of the colleceted data
 */
	public void collect(byte[] data, int num){

		if (completeDataWritePointer < maxLength){
			if (num > 0){
				for (int i = 0; i < num; i++){
					// Begin Anfang auswerten
					countCharRead++;
					if ( countCharRead < 7 ){
						actualIntegrationTimeIn_ms_string = 
							actualIntegrationTimeIn_ms_string + (char) data[i]; }

					if ( countCharRead == 7 ){
						// Das sollte ein J sein
						//System.out.println("Char 7F ");

						actualIntegrationTimeIn_ms = Integer.parseInt(actualIntegrationTimeIn_ms_string);
						
						countNumberOfFoundFF = 0;
						
						System.out.print("Zurückgemeldete IntegrationTime ");
						System.out.println(actualIntegrationTimeIn_ms_string);
					}	

					// Ende Anfang auswerten
					completeData[completeDataWritePointer++] = data[i];
					
/*					if ( data[i] == (byte)0xFF	)
						 System.out.println("Found FF");
					if ( data[i] == (byte)0x54	)
						 System.out.println("Found T");*/
					
					if ( data[i] == (byte)0xFF &
							countNumberOfFoundFF >= 4 	) 
					{ System.out.println("All Received");
					allDataIsReveived = true;}	
					else
					{
						if (data[i] == (byte)0xFF ){ //-128 steht für 0xFF
							countNumberOfFoundFF++;
						}
						else
							countNumberOfFoundFF = 0;
					}

				}
			}
		}
		completeDataEndPointer = completeDataWritePointer;
		if (allDataIsReveived){
			logic.dataIsReceived();
		}
	}

	public void listContentToConsole(){
		//	System.out.print(new String( completeData, 0, completeDataWritePointer));
	}

	public void initializeRead(){
		completeDataReadPointer = 0;
	}

	public byte readNextByte() throws Exception{
		/**
		TestDoku
		 */
		if (completeDataReadPointer >= completeDataEndPointer){
			throw new Exception("No more data");
		}

		return (byte) completeData[completeDataReadPointer++];
	}


}
