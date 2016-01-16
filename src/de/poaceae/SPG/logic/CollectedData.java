package de.poaceae.SPG.logic;

import java.util.HashMap;

import de.poaceae.SPG.MeasurementUnit.Interface2MeasurementUnit;

public class CollectedData implements Interface2Data{

	private HashMap<Integer, Long> CollectedData;
	private int countSamples;
	private int minPixel = 0;
	private int maxPixel = 0;
	private int actualIntegrationTimeIn_ms;
	float averageValue = 0;
	boolean hasData = false;
	private RawData rawdata;
	//private Interface2MeasurementUnit interface2MeasurementUnit;

	public CollectedData(){
		CollectedData = new HashMap<Integer, Long>();
		//this.interface2MeasurementUnit = interface2MeasurementUnit;
	}

	void initialize(){
		if (CollectedData != null)
			CollectedData.clear();
		minPixel = 0;
		maxPixel = 0;
		countSamples = 0;
		hasData = false;
	}	

	public int getMinPixel(){
		return minPixel;

	}

	public int getMaxPixel(){
		return maxPixel;
	}

	public int getActualIntegrationTimeIn_ms(){
		return actualIntegrationTimeIn_ms;
	}

	public boolean hasData(){
		return hasData;
	}

	public float getValue(int pixel){
		return CollectedData.get(pixel) / countSamples;
	}	

	private void calculateAverageValue(){
		int pixelNumber;
		int pixelCount = 0;
		float SumOfValues = 0;
		pixelNumber = minPixel;
		while ( pixelNumber <= maxPixel ){
			SumOfValues = SumOfValues + CollectedData.get(pixelNumber);
			pixelNumber++;
			pixelCount++;
		}
		averageValue = SumOfValues / ( pixelCount * countSamples );
	}

	public float getAverageValue(){
		return averageValue;
	}

	public void  addRawData(RawData ir_RawData){
		int pixelNumber;

		rawdata = ir_RawData;
		actualIntegrationTimeIn_ms = rawdata.getactualIntegrationTimeIn_ms();

		minPixel = rawdata.getMinPixel();
		maxPixel = rawdata.getMaxPixel();

		countSamples++;

		pixelNumber = minPixel;


		while ( pixelNumber <= maxPixel ){
			if (hasData){
				CollectedData.put(pixelNumber, ( rawdata.getPixelValue(pixelNumber)  + 
						CollectedData.get(pixelNumber) ) );
			}
			else
			{
				CollectedData.put(pixelNumber, ( (long) rawdata.getPixelValue(pixelNumber) ) );
			}
			pixelNumber++;
		}
		
		calculateAverageValue();
		
		hasData = true;
	}

	public int getDataType() {
		// TODO Auto-generated method stub
		return DataManager.isIntensity;
	};	



}
