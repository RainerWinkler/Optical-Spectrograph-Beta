package de.poaceae.SPG.logic;

public class EqualizedData implements Interface2Data{

	boolean hasData = false;


	private Interface2Data sens1Data;
	private Interface2Data sens2Data;
	private Interface2Data darkCurrentData;
	private Interface2Data signal1Data;

	public EqualizedData(CollectedData i_sens1Data, 
			CollectedData i_sens2Data, 
			CollectedData i_darkCurrentData,
			CollectedData i_signal1Data){
		sens1Data = (Interface2Data) i_sens1Data;
		sens2Data = (Interface2Data) i_sens2Data;
		darkCurrentData = (Interface2Data) i_darkCurrentData;
		signal1Data = (Interface2Data) i_signal1Data;
	}

	public int getMinPixel(){
		return signal1Data.getMinPixel();

	}

	public int getMaxPixel(){
		return signal1Data.getMaxPixel();
	}

	public int getActualIntegrationTimeIn_ms(){
		return signal1Data.getActualIntegrationTimeIn_ms();
	}

	//public boolean hasData(){
	//	if (sens1Data.hasData() & sens2Data.hasData() & darkCurrentData.hasData() & signal1Data.hasData())
	//		return true;
	//	else
	//		return false;
	//
	//}

	// Rainer Winkler 30.05.2014 Ignoriere Sens1 und Sens2 Daten
	public boolean hasData(){
		if (darkCurrentData.hasData() & signal1Data.hasData())
			return true;
		else
			return false;

	}


	// public float getValue(int pixel){
	// 	return 700 + ( signal1Data.getValue(pixel) - darkCurrentData.getValue(pixel) ) * 
	// 	( sens2Data.getAverageValue() - sens1Data.getAverageValue()) / (
	// 	( sens2Data.getValue(pixel) - sens1Data.getValue(pixel)) ) ;
	// }

	// public float getValue(int pixel){
	// 	return 600 + ( signal1Data.getValue(pixel) - darkCurrentData.getValue(pixel) ) * 
	// 	( sens1Data.getAverageValue()) / (
	// 	( sens1Data.getValue(pixel)) ) ;
	// }	


	// Rainer Winkler 30.05.2014 Ignoriere Sens1 und Sens2 Daten
	public float getValue(int pixel){
		//return 600 + ( signal1Data.getValue(pixel) - darkCurrentData.getValue(pixel) ); //* 
		return signal1Data.getValue(pixel) - darkCurrentData.getValue(pixel) ; //* 
		//( sens1Data.getAverageValue()) / (
		//( sens1Data.getValue(pixel)) ) ;
	}	


	@Override
	public float getAverageValue() {
		return signal1Data.getAverageValue() - darkCurrentData.getAverageValue();
	}

	public int getDataType() {
		// TODO Auto-generated method stub
		return DataManager.isIntensity;
	};		

}
