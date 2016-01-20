package de.poaceae.SPG.logic;

public class ComparedData implements Interface2Data{

	private Interface2Data signal1Data;
	private Interface2Data signal2Data;

	public ComparedData(Interface2Data i_signal1, Interface2Data i_signal2){
		signal1Data = i_signal1;
		signal2Data = i_signal2;
	};

	public boolean hasData(){
		if (signal1Data.hasData() & signal1Data.hasData())
			return true;
		else
			return false;
	};

	public int getMinPixel(){
		return signal1Data.getMinPixel();

	}

	public int getMaxPixel(){
		return signal1Data.getMaxPixel();
	}

	public float getValue(int pixel){
		float value1;
		float value2;
		float logvalue1;
		float logvalue2;

		value1 = signal1Data.getValue(pixel);
		value2 = signal2Data.getValue(pixel);
		if (value1 == 0 || value2 == 0)
			return 0;
		else {
			logvalue1 = (float) Math.log10( value1);
			logvalue2 = (float) Math.log10( value2);
			return logvalue1 - logvalue2; 
			}
	}

	public float getAverageValue(){
		return 0;
	}

	public int getActualIntegrationTimeIn_ms(){
		return signal1Data.getActualIntegrationTimeIn_ms();
	};

	public int getDataType() {
		// TODO Auto-generated method stub
		return DataManager.isAbsorption;
	};	
}
