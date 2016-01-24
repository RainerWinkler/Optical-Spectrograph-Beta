package de.poaceae.SPG.logic;

import java.awt.Color;
import java.awt.Graphics2D;

import de.poaceae.SPG.MeasurementUnit.FirstSpectrograph;
import de.poaceae.SPG.MeasurementUnit.Interface2MeasurementUnit;
import de.poaceae.SPG.basic.Main;
import de.poaceae.SPG.com.ByteCollector;
import de.poaceae.SPG.com.SendReceive;
import de.poaceae.SPG.file.WriteFile;
import de.poaceae.SPG.frontend.Frontend;
import de.poaceae.SPG.frontend.FrontendHelper;
import de.poaceae.SPG.spectrographBeta.Command;

/**
 * This is the class with the main logic
 * @author Rainer Winkler
 * @version 0.1
 *
 */

public class Logic {
	
	final int lineY_0 = 500;

	private SendReceive sendReceive;

	private ByteCollector byteCollector;

	private RawData rawdata;

	private DataManager dataManager;

	private Interface2Data interface2Data;

	// only tests

	private CollectedData collectedData;

	//private Graphics2D g2jPaneltoDraw;

	private Frontend frontend;

	private FrontendHelper frontendHelper;

	private Interface2MeasurementUnit interface2MeasurementUnit;

	private WriteFile writeFile;

	public Logic(DataManager ir_dataManager){
		dataManager = ir_dataManager;
		interface2MeasurementUnit = new FirstSpectrograph( );
		collectedData = new CollectedData();
		collectedData.initialize();

		writeFile = new WriteFile();
		String message = writeFile.open();
		if (!message.equals(""))
		  {frontend.setMessage(message);};

	}
	
	public void activateFile(){
		writeFile.activate();
	}


	public void setFrontend(Frontend in){
		frontend = in;
		frontendHelper = frontend.returnFrontendHelper();
		frontendHelper.setInterface2MeasurementUnit(interface2MeasurementUnit);
	}

	Command spectrographCommand = new Command();

	public void integrationTimeChanged(String i_textIntegrationTime ){
		spectrographCommand.setIntegrationTime(i_textIntegrationTime);
	};
	
	private void drawDiagram(Graphics2D g2d,int dataType){

		//final int lineY_0 = 500;
		final int lineY_100 = 400;
		final int lineY_200 = 300;
		final int lineY_300 = 200;
		final int lineY_400 = 100;
		final int lineY_500 = 0;



		final int lineXLambdaLeft = 350;
		final int lineXLambdaRight = 1100;
		final int lineXPixelLeft = 750;
		final int lineXPixelRight = 1500;	
		
		// Legend
		String legend;
		legend = dataManager.returnLegend();
		g2d.drawString(legend, 170 , lineY_500 + 30);		

		// Vertical Lines

		g2d.setColor(Color.black);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_500);		
		g2d.setColor(Color.lightGray);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(800, 400), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(800, 400), lineY_500);

		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(900, 500), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(900, 500), lineY_500);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(1000, 600), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(1000, 600), lineY_500);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(1100, 700), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(1100, 700), lineY_500);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(1200, 800), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(1200, 800), lineY_500);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(1300, 900), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(1300, 900), lineY_500);	
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(1400, 1000), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(1400, 1000), lineY_500);	
		g2d.setColor(Color.black);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(1500, 1100), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(1500, 1100), lineY_500);	

		// Horizontal Lines

		g2d.setColor(Color.black);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_0, 
				frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, lineXLambdaRight), lineY_0);
		g2d.setColor(Color.lightGray);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_100, 
				frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, lineXLambdaRight), lineY_100);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_200, 
				frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, lineXLambdaRight), lineY_200);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_300, 
				frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, lineXLambdaRight), lineY_300);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_400, 
				frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, lineXLambdaRight), lineY_400);
		g2d.setColor(Color.black);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_500, 
				frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, lineXLambdaRight), lineY_500);

		// Vertical Legend

		final int vOffset = 30;
		final int vhOffset = -10;
		
		if ( dataType == DataManager.isIntensity){

		g2d.drawString("0", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_0 - vhOffset);
		g2d.drawString("100", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_100 - vhOffset);
		g2d.drawString("200", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_200 - vhOffset);
		g2d.drawString("300", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_300 - vhOffset);
		g2d.drawString("400", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_400 - vhOffset);
		g2d.drawString("500", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_500 - vhOffset);
		}
		
		if ( dataType == DataManager.isAbsorption){

			g2d.drawString("0", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_0 - vhOffset);
			g2d.drawString("0.5", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_100 - vhOffset);
			g2d.drawString("1", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_200 - vhOffset);
			g2d.drawString("1.5", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_300 - vhOffset);
			g2d.drawString("2", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_400 - vhOffset);
			g2d.drawString("2.5", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_500 - vhOffset);
			}		
		
		// Horizontal Legend

		final int hOffset = -20;
		final int hvOffset = 20;

		g2d.drawString("400 nm", frontendHelper.diagramPixelLambda2PosX(800, 400) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("500 nm", frontendHelper.diagramPixelLambda2PosX(900, 500) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("600 nm", frontendHelper.diagramPixelLambda2PosX(1000, 600) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("700 nm", frontendHelper.diagramPixelLambda2PosX(1100, 700) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("800 nm", frontendHelper.diagramPixelLambda2PosX(1200, 800) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("900 nm", frontendHelper.diagramPixelLambda2PosX(1300, 900) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("1000 nm", frontendHelper.diagramPixelLambda2PosX(1400, 1000) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("1100 nm", frontendHelper.diagramPixelLambda2PosX(1500, 1100) - hvOffset, lineY_0 - hOffset);

		// Spektrum zeichnen

		for (int i = 350; i < 1100; i++){
			int[] RGB;
			double tempLambda;
			tempLambda = (double) i;
			RGB = waveLengthToRGB.waveLengthToRGB(tempLambda);
			g2d.setColor(new Color(RGB[0], RGB[1], RGB[2]));

			g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, tempLambda), lineY_0 + 40, 
					frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, tempLambda), lineY_0 + 80);
		}

		// Mausposition zeichnen
		//g2d.drawLine(c_start + frontend.getMousePosition() / c_divisor, 50, c_start + frontend.getMousePosition() / c_divisor, 680);
		//g2d.drawLine(frontend.getMousePosition(), 50, frontend.getMousePosition(), 680);		
		
		
	}

	public void drawToMainPanel(Graphics2D g2d){

		String message = writeFile.open();
		if (!message.equals(""))
		  {frontend.setMessage(message);};
		  

		// Test File Output
		//WriteFile writeFile;

		//writeFile = new WriteFile();
		//writeFile.appendfile();

		// 1. Spektrograph
		//final int c_divisor = 1;
		//final int c_start = -500 ;

		// 2. Spektrograph
		//final int c_divisor = 2;
		//final int c_start = 0 ;		

		//g2d.drawLine(1, 1, 100, 100);
		//g2d.drawLine(10, 1, 110, 100);
		// Hilfslinien zeichnen
		//g2d.drawLine(c_start + 800 / c_divisor, 0, c_start + 800 / c_divisor, 700);
		//g2d.drawLine(c_start + 900 / c_divisor, 0, c_start + 900 / c_divisor, 700);
		//g2d.drawLine(c_start + 1000 / c_divisor, 0, c_start + 1000 / c_divisor, 800);
		//g2d.drawLine(c_start + 1100 / c_divisor, 0, c_start + 1100 / c_divisor, 700);
		//g2d.drawLine(c_start + 1200 / c_divisor, 0, c_start + 1200 / c_divisor, 700);
		//g2d.drawLine(c_start + 1300 / c_divisor, 0, c_start + 1300 / c_divisor, 700);

		//final int pixelOffset = 100;
/*
		final int lineY_0 = 500;
		final int lineY_100 = 400;
		final int lineY_200 = 300;
		final int lineY_300 = 200;
		final int lineY_400 = 100;
		final int lineY_500 = 0;



		final int lineXLambdaLeft = 350;
		final int lineXLambdaRight = 1100;
		final int lineXPixelLeft = 750;
		final int lineXPixelRight = 1500;	

		// Vertical Lines

		g2d.setColor(Color.black);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_500);		
		g2d.setColor(Color.lightGray);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(800, 400), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(800, 400), lineY_500);

		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(900, 500), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(900, 500), lineY_500);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(1000, 600), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(1000, 600), lineY_500);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(1100, 700), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(1100, 700), lineY_500);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(1200, 800), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(1200, 800), lineY_500);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(1300, 900), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(1300, 900), lineY_500);	
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(1400, 1000), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(1400, 1000), lineY_500);	
		g2d.setColor(Color.black);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(1500, 1100), lineY_0,
				frontendHelper.diagramPixelLambda2PosX(1500, 1100), lineY_500);	

		// Horizontal Lines

		g2d.setColor(Color.black);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_0, 
				frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, lineXLambdaRight), lineY_0);
		g2d.setColor(Color.lightGray);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_100, 
				frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, lineXLambdaRight), lineY_100);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_200, 
				frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, lineXLambdaRight), lineY_200);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_300, 
				frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, lineXLambdaRight), lineY_300);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_400, 
				frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, lineXLambdaRight), lineY_400);
		g2d.setColor(Color.black);
		g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft), lineY_500, 
				frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, lineXLambdaRight), lineY_500);

		// Vertical Legend

		final int vOffset = 30;
		final int vhOffset = -10;

		g2d.drawString("0", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_0 - vhOffset);
		g2d.drawString("100", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_100 - vhOffset);
		g2d.drawString("200", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_200 - vhOffset);
		g2d.drawString("300", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_300 - vhOffset);
		g2d.drawString("400", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_400 - vhOffset);
		g2d.drawString("500", frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, lineXLambdaLeft) - vOffset, lineY_500 - vhOffset);

		// Horizontal Legend

		final int hOffset = -20;
		final int hvOffset = 20;

		g2d.drawString("400 nm", frontendHelper.diagramPixelLambda2PosX(800, 400) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("500 nm", frontendHelper.diagramPixelLambda2PosX(900, 500) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("600 nm", frontendHelper.diagramPixelLambda2PosX(1000, 600) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("700 nm", frontendHelper.diagramPixelLambda2PosX(1100, 700) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("800 nm", frontendHelper.diagramPixelLambda2PosX(1200, 800) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("900 nm", frontendHelper.diagramPixelLambda2PosX(1300, 900) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("1000 nm", frontendHelper.diagramPixelLambda2PosX(1400, 1000) - hvOffset, lineY_0 - hOffset);
		g2d.drawString("1100 nm", frontendHelper.diagramPixelLambda2PosX(1500, 1100) - hvOffset, lineY_0 - hOffset);

		// Spektrum zeichnen

		for (int i = 350; i < 1100; i++){
			int[] RGB;
			double tempLambda;
			tempLambda = (double) i;
			RGB = waveLengthToRGB.waveLengthToRGB(tempLambda);
			g2d.setColor(new Color(RGB[0], RGB[1], RGB[2]));

			g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(lineXPixelLeft, tempLambda), lineY_0 + 40, 
					frontendHelper.diagramPixelLambda2PosX(lineXPixelRight, tempLambda), lineY_0 + 80);
		}

		// Mausposition zeichnen
		//g2d.drawLine(c_start + frontend.getMousePosition() / c_divisor, 50, c_start + frontend.getMousePosition() / c_divisor, 680);
		g2d.drawLine(frontend.getMousePosition(), 50, frontend.getMousePosition(), 680);
		
		*/
		
		// Messkurve zeichnen
		int minPixel = 0;
		int maxPixel = 0;
		int oldPixel = 0;
		int oldPixelValue = 0;
		float oldPixelFloatValue = 0;
		int actualPixel = 0;
		int actualPixelValue = 0;
		float actualPixelFloatValue = 0;
		int typeOfData = 0;

		if (!(rawdata == null))
			collectedData.addRawData(rawdata);

		dataManager.addRawData(rawdata);

		interface2Data = dataManager.returnData2Display();

		

		// Das würde das gleitende Mittel liefern, wird nur für Tests benötigt:
		// interface2Data = collectedData;

		String line;
		line = "" ;
		writeFile.appendLine(line);	
		line = "Pixel" + ";" + "lambda"
		+ ";" + "PixelValue";
		writeFile.appendLine(line);		

		g2d.setColor(Color.BLACK);

		if (!(interface2Data == null)){
			typeOfData = interface2Data.getDataType();
			this.drawDiagram(g2d, typeOfData);
			if (interface2Data.hasData()){
				minPixel = interface2Data.getMinPixel();
				maxPixel = interface2Data.getMaxPixel();
				oldPixel = minPixel;
				oldPixelFloatValue = interface2Data.getValue(oldPixel);
				if (typeOfData == DataManager.isIntensity){
					oldPixelValue = (int) oldPixelFloatValue;}
				if (typeOfData == DataManager.isAbsorption){
					oldPixelValue = (int) ( 200 * oldPixelFloatValue);
				}
				if ( maxPixel > 0 && minPixel >= 0 && maxPixel > minPixel){
					for (int i = minPixel + 1; i <= maxPixel; i++){
						actualPixel = i;
						actualPixelFloatValue = interface2Data.getValue(actualPixel);
						if (typeOfData == DataManager.isIntensity){
							actualPixelValue = (int) actualPixelFloatValue;}
						if (typeOfData == DataManager.isAbsorption){
							actualPixelValue = (int) ( 200 * actualPixelFloatValue);
						}						

						//g2d.drawLine(c_start + oldPixel / c_divisor, oldPixelValue, 
						//		     c_start + actualPixel / c_divisor, actualPixelValue );
						g2d.drawLine(frontendHelper.diagramPixelLambda2PosX(oldPixel, 
								interface2MeasurementUnit.pixel2wavelength(oldPixel)), lineY_0 - oldPixelValue, 
								frontendHelper.diagramPixelLambda2PosX(actualPixel, 
										interface2MeasurementUnit.pixel2wavelength(actualPixel)), lineY_0 - actualPixelValue );						

						oldPixel = actualPixel;
						oldPixelValue = actualPixelValue;
						// String für CSV

						line = String.valueOf(actualPixel) + ";" + String.valueOf(interface2MeasurementUnit.pixel2wavelength(actualPixel))
						+ ";" + String.valueOf(actualPixelFloatValue);
						writeFile.appendLine(line);
					}
				}
			}
		}
		writeFile.close();
	}

	public void setSendReceive( SendReceive in ) {
		sendReceive = in;
	}

	public void setByteCollector( ByteCollector in) {
		byteCollector = in;
	}

	public void setg2jPanelToDraw(Graphics2D in){
		//	g2jPaneltoDraw = in;
	}

	public void sendToSpectrograph(){
		byteCollector.initializeWrite();

		sendReceive.sendeSerialPort(spectrographCommand.startMesurement());  
	}
	public boolean isDataReceived(){
		return byteCollector.allDataIsReveived;
	}

	public void listByteCollector(){
		if ( byteCollector.allDataIsReveived ){
			byteCollector.listContentToConsole();
			rawdata = new RawData(byteCollector);
			rawdata.initialize();
			rawdata.fillRawData();
			this.sendToSpectrograph();
		}
	}

	public void dataIsReceived(){
		this.listByteCollector();
		frontend.repaintMe();
	}

	public void closeApplication(){
		sendReceive.setStopMe();
		while(sendReceive.is_send_possible);
		System.exit(0);
	}

	public double pixelnumber2lambda(int pixelnumber){
		return interface2MeasurementUnit.pixel2wavelength(pixelnumber);
	};


}


