package de.poaceae.SPG.logic;

import de.poaceae.SPG.MeasurementUnit.Interface2MeasurementUnit;

public class DataManager {

	public final int setMeasureSens1 = 1;
	public final int setMeasureSens2 = 2;
	public final int setMeasureDark = 3;
	public final int setMeasureDark2 = 6;
	public final int setMeasureSignal1 = 4;
	public final int setMeasureSignal2 = 5;

	public final int setDisplayRaw = 0;
	public final int setDisplaySens1 = 1;
	public final int setDisplaySens2 = 2;
	public final int setDisplayDark = 3;
	public final int setDisplayDark2 = 9;
	public final int setDisplaySignal1 = 4;
	public final int setDisplayEqSignal1 = 5;
	public final int setDisplaySignal2 = 6;
	public final int setDisplayEqSignal2 = 7;
	public final int setDisplayEqSignal2div1 = 8;
	
	public static int isIntensity = 1;
	public static int isAbsorption = 2;

	private boolean measurementStarted = false;

	private int measureSet;
	private int displaySet;

	private RawData myLastRawData;

	private CollectedData sens1Data;
	private CollectedData sens2Data;
	private CollectedData darkData;
	private CollectedData darkData2;
	private CollectedData signal1Data;
	private EqualizedData eqSignal1Data;
	private CollectedData signal2Data;
	private EqualizedData eqSignal2Data;	
	private ComparedData compSignal2_1;


	public void startMeasurement(){
		measurementStarted = true;
		switch (measureSet){
		case setMeasureSens1:
			sens1Data.initialize();
			break;
		case setMeasureSens2:
			sens2Data.initialize();
			break;		
		case setMeasureDark:
			darkData.initialize();
			break;	
		case setMeasureDark2:
			darkData2.initialize();
			break;				
		case setMeasureSignal1:
			signal1Data.initialize();
			break;	
		case setMeasureSignal2:
			signal2Data.initialize();
			break;				
		}		
	}

	public void stopMeasurement(){
		measurementStarted = false;
	}	

	public void setMeasure(int i_setMeasure){
		measureSet = i_setMeasure;
	}

	public void setDisplay(int i_setDisplay){
		displaySet = i_setDisplay;
	}	

	public DataManager(){

		sens1Data = new CollectedData();
		sens1Data.initialize();
		sens2Data = new CollectedData();
		sens2Data.initialize();	
		darkData = new CollectedData();
		darkData.initialize();	
		darkData2 = new CollectedData();
		darkData2.initialize();			
		signal1Data = new CollectedData();
		signal1Data.initialize();
		eqSignal1Data = new EqualizedData(
				sens1Data, sens2Data, darkData, signal1Data){

		};
		signal2Data = new CollectedData();
		signal2Data.initialize();
		eqSignal2Data = new EqualizedData(
				sens1Data, sens2Data, darkData2, signal2Data){

		};	
		compSignal2_1 = new ComparedData(eqSignal1Data, eqSignal2Data);
	}

	public void  addRawData(RawData ir_RawData){
		myLastRawData = ir_RawData;
		if (measurementStarted == true){
			switch (measureSet){
			case setMeasureSens1:
				sens1Data.addRawData(ir_RawData);
				break;
			case setMeasureSens2:
				sens2Data.addRawData(ir_RawData);
				break;		
			case setMeasureDark:
				darkData.addRawData(ir_RawData);
				break;	
			case setMeasureDark2:
				darkData2.addRawData(ir_RawData);
				break;					
			case setMeasureSignal1:
				signal1Data.addRawData(ir_RawData);
				break;		
			case setMeasureSignal2:
				signal2Data.addRawData(ir_RawData);
				break;					
			}
		}
	};

	public Interface2Data returnData2Display(){

		Interface2Data interface2Data = null;

		switch (displaySet){
		case setDisplayRaw:
			interface2Data = myLastRawData;
			break;
		case setDisplaySens1:
			interface2Data = sens1Data;
			break;
		case setDisplaySens2:
			interface2Data = sens2Data;
			break;
		case setDisplayDark:
			interface2Data = darkData;
			break;
		case setDisplayDark2:
			interface2Data = darkData2;
			break;			
		case setDisplaySignal1:
			interface2Data = signal1Data;
			break;		
		case setDisplayEqSignal1:
			interface2Data = eqSignal1Data;
			break;		
		case setDisplaySignal2:
			interface2Data = signal2Data;
			break;		
		case setDisplayEqSignal2:
			interface2Data = eqSignal2Data;
			break;	
		case setDisplayEqSignal2div1:
			interface2Data = compSignal2_1;
			break;
		}
		return interface2Data;

	}
	
	public String returnLegend(){
		String legend = "";
		switch (displaySet){
		case setDisplayRaw:
			legend = "Raw Data";
			break;
		case setDisplaySens1:
			legend = "Not used";
			break;
		case setDisplaySens2:
			legend = "Not used";
			break;
		case setDisplayDark:
			legend = "Dark Current 1";
			break;
		case setDisplayDark2:
			legend = "Dark Current 2";
			break;			
		case setDisplaySignal1:
			legend = "Signal 1";
			break;		
		case setDisplayEqSignal1:
			legend = "Signal 1 - Dark Current 1";
			break;		
		case setDisplaySignal2:
			legend = "Signal 2";
			break;		
		case setDisplayEqSignal2:
			legend = "Signal 2 - Dark Current 2";
			break;	
		case setDisplayEqSignal2div1:
			legend = "Log Absorption (Signal 1 - Dark Current 1)/(Signal 2 - Dark Current 2)";
			break;
		}
		return legend;
	}

}
