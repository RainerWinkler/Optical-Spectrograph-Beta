/*
 * OpticalSpectrographBetaDetector.c
 *
 * Created: 18.01.2016 20:57:34
 *  Author: rainer
 */ 


// Spectrograph Alpha

// Version 0.1
// Rainer Winkler
// 15.02.2011 21:30
// Messung startet wenn irgendein Zeichen über die serielle Schnittstelle empfangen wird
// Jetzt Ende mit fünf 0XFF in Folge und anschließend ein T
// Die mit T angeforderten Daten werden jetzt in der Klasse RawData im Java-Programm abgelegt.
// Hardware

// Linear CCD Typ Sony ILX554B

// ThetaCLK an PD4 (Ausgang benötigt)
// SHW an PD3 (Ausgang benötigt)
// ThetaROG an PD2 (Ausgang benötigt)
// Trigger für Oszilloskop an PB0

// Extermer Oszillator mit 16 MHz

// Rainer Winkler
// 04.12.2011 21:56
// Nachdem mit I und sechs Ziffern die Integrationszeit gesendet wurde wird jetzt unmittelbar gemessen
//

#include <avr/io.h>
#include <stdlib.h>


// -----------------------------------------------------------------
// Vereinbarungen
// -----------------------------------------------------------------

// delay.h has a different logic after avr-libc 1.6 This causes the old logic to be used:
#define __DELAY_BACKWARD_COMPATIBLE__

#define THETA_CLK_DDR_PORT DDRD
#define THETA_CLK_PORT PORTD
#define THETA_CLK_BYTE 4

#define SHW_DDR_PORT DDRD
#define SHW_PORT PORTD
#define SHW_BYTE 3

#define THETA_ROG_DDR_PORT DDRD
#define THETA_ROG_PORT PORTD
#define THETA_ROG_BYTE 2

#define TRIGGER_DDR_PORT DDRB
#define TRIGGER_PORT PORTB
#define TRIGGER_BYTE 0

#define SETBIT( a, b) { a |= (1 << b); }
#define UNSETBIT( a, b) { a &= ~(1 << b); }

#define NULL 0x00;

// -----------------------------------------------------------------
// Vereinbarungen zum Warten (Aus mikrocontroller.net: 
// http://www.mikrocontroller.net/articles/AVR-GCC-Tutorial#Warteschleifen_.28delay.h.29)
// -----------------------------------------------------------------

#ifndef F_CPU
/* Definiere F_CPU, wenn F_CPU nicht bereits vorher definiert 
   (z.&nbsp;B. durch Übergabe als Parameter zum Compiler innerhalb 
   des Makefiles). Zusätzlich Ausgabe einer Warnung, die auf die
   "nachträgliche" Definition hinweist */
#warning "F_CPU war noch nicht definiert, wird nun mit 3686400 definiert"
#define F_CPU 16000000UL     /* Quarz mit 16.0 Mhz */
#endif
#include <util/delay.h>

// -----------------------------------------------------------------
// Define Functions
// -----------------------------------------------------------------

void putch (unsigned char x)
	{
	while (!(UCSRA & (1<<UDRE)))  /* warten bis Senden moeglich                   */
		{
		}
	UDR = x;                    /* schreibt das Zeichen x auf die Schnittstelle */
	}

void putstring(unsigned char *zeiger)
	{
	while(*zeiger != 0) putch(*zeiger++); // solange keine Endemarke 0
	}


void release_charge_of_ccd (void )
	{

	SETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	UNSETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 
	SETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 

	}

void empty_ccd_array( void )
	{
	unsigned int internal_count;
	internal_count = 0;
while ( internal_count++ < 2087 )
		{
		// Soll für 250ns Bit nicht setzen, macht 4 CPU Zyklen oder 2 Befehle, da jeder 2 CPS Zyklen benötigt
		// da die while Schleife 7 CPU Zyklen benötigt ist das Minimum aber 9 CPU Zyklen
		UNSETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);
		UNSETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);
		UNSETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);
		UNSETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);	
	
		// Soll für 250s Bit setzen, macht 4 CPU Zyklen oder 2 Befehle,
		// da die while Schleife 7 CPU Zyklen benötigt ist das Minimum aber 9 CPU Zyklen
		SETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);
		}
	}

// -----------------------------------------------------------------
// Set Hardwareparameters
// -----------------------------------------------------------------



void main(void)
{

// Set Datadirektions
SETBIT( THETA_CLK_DDR_PORT, THETA_CLK_BYTE);

SETBIT( SHW_DDR_PORT, SHW_BYTE); 

SETBIT( THETA_ROG_DDR_PORT, THETA_ROG_BYTE); 

SETBIT( TRIGGER_DDR_PORT , TRIGGER_BYTE); 

// Set parameters "Serielle Schnittstelle"

 UCSRB |= (1 << TXEN); // Sender ein
 UCSRB |= (1 << RXEN); // Empfänger ein
 
 UCSRC |= (1 << URSEL) | (1 << UCSZ1) | (1 << UCSZ0); // 8 Datenbits und einschalten
 UBRRH = 0;

 //UCSRA |= (1 << U2X);
 //UBRRL = 207; // 9600 Baud bei 16 MHz U2X = 1 (OK)

 //UCSRA |= (1 << U2X);
 //UBRRL = 103; //19200 Baud bei 16 MHz U2X = 1  (OK)

 //UCSRA |= (1 << U2X);
 //UBRRL = 51; //38400 Baud bei 16 MHz U2X = 1  (OK)

 UCSRA |= (1 << U2X);
 UBRRL = 34; //57600 Baud bei 16 MHz U2X = 1 (OK)

 //UCSRA |= (1 << U2X);
 //UBRRL = 16; //115200 Baud bei 16 MHz U2X = 1 (OK)

 // Set Parameters Analog Digital Converter

 ADMUX = (1 << REFS0);
 ADCSR = (1 << ADEN) | (1 << ADPS2) | (1 << ADPS1) | (1 << ADPS0);


// -----------------------------------------------------------------
// Deklarations
// -----------------------------------------------------------------

unsigned int count;
unsigned int adcvalue;
unsigned char zeichen_ein;
//unsigned int integrationTimeIn_ms = 1000;
long int integrationTimeIn_ms = 100;
char s[7];
char conversionError[7];
const unsigned char lf = 10;
const unsigned char cr = 13;

// -----------------------------------------------------------------
// Initialization
// -----------------------------------------------------------------

// Set S/H

UNSETBIT( SHW_PORT, SHW_BYTE); 

// Set Clock

SETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);

// Set ROG

SETBIT( THETA_ROG_PORT, THETA_ROG_BYTE); 

// Am Anfang Array leeren

empty_ccd_array( );
empty_ccd_array( );
empty_ccd_array( );
empty_ccd_array( );
empty_ccd_array( );
empty_ccd_array( );
empty_ccd_array( );
empty_ccd_array( );

while(1)
	{

	// Für Test, Trigger zurücksetzen:
	UNSETBIT( TRIGGER_PORT , TRIGGER_BYTE); 

	// Warte bis irgendein Zeichen empfangen wird, erst dann wird ausgewertet
	while ( ! (UCSRA & (1 << RXC)));
	zeichen_ein = UDR;

	if (zeichen_ein == 0x49) //I
		{
		// Übermittle Integrationszeit in ms für die Belichtung
		// Die nächsten 6 Zeichen werden als Integrationszeit in ms interpretiert
		// Die Zeit ist mit führenden Nullen zu übergeben
		while ( ! (UCSRA & (1 << RXC)));
		s[0] = UDR;
		while ( ! (UCSRA & (1 << RXC)));
		s[1] = UDR;
		while ( ! (UCSRA & (1 << RXC)));
		s[2] = UDR;
		while ( ! (UCSRA & (1 << RXC)));
		s[3] = UDR;
		while ( ! (UCSRA & (1 << RXC)));
		s[4] = UDR;
		while ( ! (UCSRA & (1 << RXC)));
		s[5] = UDR;
		s[6] = 0;
		putstring( s ); // Gebe Integrationszeit zurück
		putch( cr );
		putch( lf );
		integrationTimeIn_ms = strtol( s , conversionError , 10 );

		putch('I'); // Quittiere Empfang mit I
		ltoa( integrationTimeIn_ms, s, 10 ); // 10 fuer radix -> Dezimalsystem
		putch( cr );
		putch( lf );
		putstring( s ); // Gebe Integrationszeit zurück
		putch( cr );
		putch( lf );
		putstring( conversionError ); // Gebe Integrationszeit zurück
		putch( cr );
		putch( lf );

		putch('E'); // Melde Ende mit E

		}

	else if (zeichen_ein == 0x4A) //J
		{

		// Übermittle Integrationszeit in ms für die Belichtung
		// Die nächsten 6 Zeichen werden als Integrationszeit in ms interpretiert
		// Die Zeit ist mit führenden Nullen zu übergeben
		while ( ! (UCSRA & (1 << RXC)));
		s[0] = UDR;
		while ( ! (UCSRA & (1 << RXC)));
		s[1] = UDR;
		while ( ! (UCSRA & (1 << RXC)));
		s[2] = UDR;
		while ( ! (UCSRA & (1 << RXC)));
		s[3] = UDR;
		while ( ! (UCSRA & (1 << RXC)));
		s[4] = UDR;
		while ( ! (UCSRA & (1 << RXC)));
		s[5] = UDR;
		s[6] = 0;
		putstring( s ); // Gebe Integrationszeit zurück

		integrationTimeIn_ms = strtol( s , conversionError , 10 );

		// Verkürzte Übergabe der Daten für das Java Programm
		// Angefordert mit T
		// Rückgabe: 
		// 6. Ein Zeichen T wird zurückgegeben
		// 7. Zeichen ist Null
		// 8. Zeichen ist das LowByte des ersten Datenpunktes
		// 9. Zeichen ist das HighByte des ersten Datenpunktes
		// ...
		// Eine Null
		// Fünfmal 0xFF
		// Das letzte Zeichen ist T
		unsigned char lowByteADC = 0;
		unsigned char highByteADC = 0;

		// Für Test, Empfang des Zeichens mitteilen indem Trigger gesetzt wird:
		SETBIT( TRIGGER_PORT , TRIGGER_BYTE); 

		// Jetzt Ladung entleeren, Array leeren Strahlung integrieren und Ladung erneut entleeren
		release_charge_of_ccd ( );
		empty_ccd_array( );
		//_delay_ms(1000);
		_delay_ms(integrationTimeIn_ms);
		release_charge_of_ccd( );


		const unsigned char lf = 10;
		const unsigned char cr = 13;

		// Trigger First part
		//SETBIT( TRIGGER_PORT , TRIGGER_BYTE); 
		//UNSETBIT( TRIGGER_PORT , TRIGGER_BYTE); 

		count = 0;

		// Trigger First part
		//SETBIT( TRIGGER_PORT , TRIGGER_BYTE); 
		//UNSETBIT( TRIGGER_PORT , TRIGGER_BYTE); 


		putch('J');
		highByteADC = 0;

		// Read out of CCD 
		while ( count++ < 2087 )
			{
			UNSETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);

			// Starte ADC Wandlung (Dummy, damit Periodenlängen gleich sind
			ADCSR |= (1 << ADSC);
			while(ADCSRA & (1 << ADSC)); //Warte auf Ende der Wandlung
			//adcvalue = (unsigned int) ADCL + ((unsigned int) ADCH << 8 );

			putch( highByteADC );


			SETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);

			// Starte ADC Wandlung
			ADCSR |= (1 << ADSC);
			while(ADCSRA & (1 << ADSC)); //Warte auf Ende der Wandlung
			//adcvalue = (unsigned int) ADCL + ( ( (unsigned int) ADCH ) << 8 );
			lowByteADC = (unsigned char) ADCL;
			highByteADC = (unsigned char) ADCH;
			

			putch( lowByteADC );
			
			};

		putch( highByteADC );
		putch( 0x00 );
		putch( 0xFF );
		putch( 0xFF );
		putch( 0xFF );
		putch( 0xFF );
		putch( 0xFF );


		} // Ende J
	else
		{


		// Für Test, Empfang des Zeichens mitteilen indem Trigger gesetzt wird:
		SETBIT( TRIGGER_PORT , TRIGGER_BYTE); 

		// Jetzt Ladung entleeren, Array leeren Strahlung integrieren und Ladung erneut entleeren
		release_charge_of_ccd ( );
		empty_ccd_array( );
		//_delay_ms(1000);
		_delay_ms(integrationTimeIn_ms);
		release_charge_of_ccd( );



		// Trigger First part
		//SETBIT( TRIGGER_PORT , TRIGGER_BYTE); 
		//UNSETBIT( TRIGGER_PORT , TRIGGER_BYTE); 

		count = 0;

		// Trigger First part
		//SETBIT( TRIGGER_PORT , TRIGGER_BYTE); 
		//UNSETBIT( TRIGGER_PORT , TRIGGER_BYTE); 

 		s[0] = 0x53;//S
		s[1] = 0x54;//T
		s[2] = 0x41;//A
		s[3] = 0x52;//R
    	s[4] = cr;
		s[5] = lf;
		s[6] = 0;
		putstring( s );

		// Read out of CCD 
		while ( count++ < 2087 )
		//while ( count++ < 10 )  
			{
			UNSETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);



			// Starte ADC Wandlung (Dummy, damit Periodenlängen gleich sind
			ADCSR |= (1 << ADSC);
			while(ADCSRA & (1 << ADSC)); //Warte auf Ende der Wandlung
			adcvalue = (unsigned int) ADCL + ((unsigned int) ADCH << 8 );




			utoa( count, s, 10 ); // 10 fuer radix -> Dezimalsystem



			putstring( s );
			// Messe Anfang der ersten Hälfte
			SETBIT( TRIGGER_PORT , TRIGGER_BYTE); 
			putch(';');

			// Messe Ende der ersten Hälfte
			UNSETBIT( TRIGGER_PORT , TRIGGER_BYTE); 


			SETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);

			// Starte ADC Wandlung
			ADCSR |= (1 << ADSC);
			while(ADCSRA & (1 << ADSC)); //Warte auf Ende der Wandlung
			//adcvalue = (unsigned int) ADCL;
			adcvalue = (unsigned int) ADCL + ( ( (unsigned int) ADCH ) << 8 );

			utoa( adcvalue, s , 10 ); // 10 fuer radix -> Dezimalsystem
			putstring( s );
			putch( cr );
			putch( lf );

			};
		s[0] = 0x45;//E
		s[1] = 0x4E;//N
		s[2] = 0x44;//D
		s[3] = 0x45;//E
		s[4] = cr;
		s[5] = lf;
		s[6] = 0;
		putstring( s );
		}
	}
}