package de.poaceae.SPG.frontend;

import de.poaceae.SPG.MeasurementUnit.Interface2MeasurementUnit;


public class FrontendHelper {
	
	final private int lambdaOffset = 200;
	final private int pixelOffset = 500;
	final private int displayWaveLength = 1; // 1 - Scale for correct wavelength
	final private int displayAllPxel = 2; // 2 - Display all pixel
	
	private int diagramtype = displayWaveLength;              
	
	private Interface2MeasurementUnit interface2MeasurementUnit;

	
	public void setInterface2MeasurementUnit(Interface2MeasurementUnit in){
		interface2MeasurementUnit = in;
	}
	
	public void displayWaveLength () {
		diagramtype = displayWaveLength;
	};
	
	public void displayAllPixel () {
		diagramtype = displayAllPxel;
	};
	
	private int diagramPixel2PosX (int Pixel){
		int PosX;
		PosX = Pixel - pixelOffset;
		//PosX = (Pixel - 0)/4;
		return PosX;
		
	};
	
	private int Pixel2PosX (int Pixel){
		// Display all pixel
		int PosX;
		
		PosX = (Pixel - 0)/2;
		return PosX;
		
	};	
	
	private int diagramPosX2Pixel (int PosX){
		int Pixel;
		Pixel = PosX + pixelOffset;
		return Pixel;
	}
	
	private int lambda2PosX (double lambda){
		return (int)lambda - lambdaOffset;
	};
	
	private double PosX2lambda (int PosX){
		return PosX + lambdaOffset;
	};	
	
	public int diagramPixelLambda2PosX(int Pixel, double Lambda){

		if (diagramtype == displayWaveLength){
			return lambda2PosX(Lambda);
		}
		if (diagramtype == displayAllPxel){
			return Pixel2PosX(Pixel);
		}
		else
		{
			return diagramPixel2PosX(Pixel);
		}
	
		
	};
	public double diagramPosX2Lambda(int PosX){
		if (diagramtype == displayWaveLength){
			return PosX2lambda(PosX);
		}
		if (diagramtype == displayAllPxel){
			return PosX2lambda(PosX);
		}
		else
		{
			return interface2MeasurementUnit.pixel2wavelength(diagramPosX2Pixel(PosX));
		}
	}
	
	public int Lambda2Pixel(double Lambda){
		return interface2MeasurementUnit.wavelength2pixel(Lambda);
	}

}
