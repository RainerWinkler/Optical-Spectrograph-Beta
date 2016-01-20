package de.poaceae.SPG.logic;





import java.util.HashMap;

import de.poaceae.SPG.MeasurementUnit.FirstSpectrograph;
import de.poaceae.SPG.MeasurementUnit.Interface2MeasurementUnit;
import de.poaceae.SPG.com.ByteCollector;
/**
 * This class contains the just read raw data
 * @author Rainer Winkler
 *
 */
public class RawData implements Interface2Data {

	private ByteCollector byteCollector;

	private HashMap<Integer, Integer> rawData;
	private int minPixel = 0;
	private int maxPixel = 0;
	private boolean hasData = false;


	//private Interface2MeasurementUnit interface2MeasurementUnit;

	RawData(ByteCollector in){
		byteCollector = in;		
		//interface2MeasurementUnit = new FirstSpectrograph( );
	}

	void initialize(){
		if (rawData != null)
			rawData.clear();
		minPixel = 0;
		maxPixel = 0;
		hasData = false;
	}

	int byteToInteger(byte b){
		/**
		 Converts byte to integer supposing the byte to be unsigned
		 */
		int tempInteger;
		tempInteger = (byte) b;
		if (tempInteger < 0){
			tempInteger = tempInteger + 256;
		};
		return tempInteger;

	}
	public int getMinPixel(){
		return minPixel;

	}

	public int getMaxPixel(){
		return maxPixel;
	}

	public boolean hasData(){
		return hasData;
	}
	
	public int getActualIntegrationTimeIn_ms(){
		return byteCollector.getactualIntegrationTimeIn_ms();
	}

	public float getValue(int pixel){
		return (float) rawData.get(pixel) ;
	}
	
	int getPixelValue(int pixel){
		return rawData.get(pixel);
	}	

	int getactualIntegrationTimeIn_ms(){
		return byteCollector.getactualIntegrationTimeIn_ms();
	}
	/**
	 * This method reads the raw transfered list of bytes and builds a hashed table with 
	 * integer values for the intensities.
	 */
	void fillRawData(){

		int pixelNumber;
		int pixelValue = 0;
		int convertedPixelValue = 0;
		boolean isFirstTFound;
		boolean isFirstNullFound;
		boolean isAllDataRead;
		byte readByte = 0;
		int stepType = 0;
		final int stepReadLowByte = 1;
		final int stepReadHighByteAndStore = 2;		

		isAllDataRead = false;
		pixelNumber = 1;
		isFirstTFound = false;
		isFirstNullFound = false;

		rawData = new HashMap<Integer, Integer>();

		byteCollector.initializeRead();



		this.initialize();
		minPixel = 1;
		do {
			try {
				readByte = byteCollector.readNextByte();
			} catch (Exception e) {
				isAllDataRead = true;
			}
			if (!isFirstTFound){
				if (readByte == (byte) 'J'){
					isFirstTFound = true;
				};
			}
			else {
				if (!isFirstNullFound)
				{
					if (readByte == (byte) 0x00)
					{
						isFirstNullFound = true;
						stepType = stepReadLowByte;
					};
				}
				else 
				{
					// Now the data is really read
					if (stepType == stepReadLowByte){
						pixelValue = this.byteToInteger(readByte);
						// For next step
						stepType = stepReadHighByteAndStore;
					}
					else if (stepType == stepReadHighByteAndStore){
						if (readByte != (byte) 0xFF){
							pixelValue = pixelValue + this.byteToInteger(readByte) * 256;
							maxPixel = pixelNumber;
							convertedPixelValue = FirstSpectrograph.staticRawValue2ProcessedValue(pixelValue);
							rawData.put(pixelNumber++, convertedPixelValue);
							// For next step
							stepType = stepReadLowByte;
						}
						else {
							isAllDataRead = true;
						};
					}
					else
						assert 1 == 2;

				};
			};
		} while(!isAllDataRead);
		hasData = true;
	}

	@Override
	public float getAverageValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDataType() {
		// TODO Auto-generated method stub
		return DataManager.isIntensity;
	}
}
