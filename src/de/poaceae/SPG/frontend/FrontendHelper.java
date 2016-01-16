package de.poaceae.SPG.frontend;

import de.poaceae.SPG.MeasurementUnit.Interface2MeasurementUnit;


public class FrontendHelper {
	
	final private int lambdaOffset = 200;
	final private int pixelOffset = 500;
	
	private int diagramtype = 1; // 1 - auf konstante Wellenlänge skalieren
	
	private Interface2MeasurementUnit interface2MeasurementUnit;
	
	public void setInterface2MeasurementUnit(Interface2MeasurementUnit in){
		interface2MeasurementUnit = in;
	}
	
	private int diagramPixel2PosX (int Pixel){
		int PosX;
		PosX = Pixel - pixelOffset;
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

		if (diagramtype == 1){
			return lambda2PosX(Lambda);
		}
		else
		{
			return diagramPixel2PosX(Pixel);
		}
	
		
	};
	public double diagramPosX2Lambda(int PosX){
		if (diagramtype == 1){
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
