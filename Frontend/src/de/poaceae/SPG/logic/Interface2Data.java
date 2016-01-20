package de.poaceae.SPG.logic;

public interface Interface2Data {
	
	boolean hasData();
	
	int getMinPixel();

	int getMaxPixel();
	
	float getValue(int pixel);
	
	public float getAverageValue();
	
	public int getActualIntegrationTimeIn_ms();
	
	public int getDataType();


}
