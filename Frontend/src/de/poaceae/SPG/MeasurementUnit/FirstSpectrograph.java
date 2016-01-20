package de.poaceae.SPG.MeasurementUnit;

public class FirstSpectrograph implements Interface2MeasurementUnit {

	public double pixel2wavelength(int pixelnumber) {
		// TODO Auto-generated method stub
		double lambda;
		lambda = 387.66 + Math.pow((403.77/ ( pixelnumber - 259.947)),(-6.75449));
		return lambda;
	}


	public int wavelength2pixel(double lambda) {
		// TODO Auto-generated method stub
		int pixel;
		pixel = (int) (-403.77 * ( -0.6438 - Math.pow((-387.66 + lambda),0.14804967)));
		return pixel;
	}



	public static int staticRawValue2ProcessedValue(int rawvalue) {
		// TODO Auto-generated method stub
		return ( 600 - rawvalue );

	}


}
