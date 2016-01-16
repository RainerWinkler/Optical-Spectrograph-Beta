package de.poaceae.SPG.spectrographBeta;

public class Command {

	private int g_IntegrationTimeIn_ms;

	public void setIntegrationTime(String i_field_value){
		g_IntegrationTimeIn_ms = Integer.parseInt(i_field_value);	
	}

	//public String startMesurement( ){
	//	return "T";
	//}


	private String addZeros(String a)
	// Code taken from http://bytes.com/topic/java/answers/645327-how-add-leading-zeros-string
	{
		int i=0;
		i=a.length();
		if ( i == 6 )
			return a;
		else
		{
			int j= 6 - i;
			for (int k=0; k<j; k++)
			{
				a="0"+a;
			}
			return a;
		}
	}

	public String startMesurement( ){

		return "J" + addZeros(Integer.toString(g_IntegrationTimeIn_ms));
	}

}
