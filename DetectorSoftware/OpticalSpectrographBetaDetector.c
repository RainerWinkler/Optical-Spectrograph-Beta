/*
 * OpticalSpectrographBetaDetector.c
 *
 * Created: 18.01.2016 20:57:34
 *  Author: rainer
 */ 


// Spectrograph Beta

// Version 0.1
// Rainer Winkler
// 15.02.2011 21:30
// Measurement starts if any character is received about the serial interface
// Now finish with five 0XFF in succession and a final T
// The with T requested data are not stored in class RawData in the Java program.
// Hardware

// Linear CCD type Sony ILX554B

// ThetaCLK on PD4 (output requiredt)
// SHW on PD3 (output required)
// ThetaROG on PD2 (output required)
// Trigger for oscilloscope on an PB0

// external oscillator with 16 MHz

// Rainer Winkler
// 04.12.2011 21:56
// After with I and six digits the integration time is send, measurement starts immediately


#include <avr/io.h>
#include <stdlib.h>


// -----------------------------------------------------------------
// Definitions
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
// Declarations for wait (From mikrocontroller.net: 
// http://www.mikrocontroller.net/articles/AVR-GCC-Tutorial#Warteschleifen_.28delay.h.29)
// -----------------------------------------------------------------

#ifndef F_CPU
/* Define F_CPU, íf F_CPU is not alread defined
   (for instance by returning as parameter to the compiler inside the make file). Raise a warning the that informs about the late definition */
#warning "F_CPU not yet defined, will now be defined with 3686400"
#define F_CPU 16000000UL     /* Quartz with 16.0 Mhz */
#endif
#include <util/delay.h>

// -----------------------------------------------------------------
// Define Functions
// -----------------------------------------------------------------

void putch (unsigned char x)
	{
	while (!(UCSRA & (1<<UDRE)))  /* wait until send is possible                   */
		{
		}
	UDR = x;                    /* write the character x to the serial interface */
	}

void putstring(unsigned char *zeiger)
	{
	while(*zeiger != 0) putch(*zeiger++); // as long as there is no end mark 0
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
		// shall for 250ns do not set bit, makes 4 CPU cycles or 2 statements, because each requires 2 CPS cycles
		// because the while loop requires 7 CPU cycles,. the minimum is 9 CPU cycles
		UNSETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);
		UNSETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);
		UNSETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);
		UNSETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);	
	
		// shall for 250ns do not set bit, makes 4 CPU cycles or 2 statements,
		// because the while loop requires 7 CPU cycles,. the minimum is 9 CPU cycles
		SETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);
		}
	}

// -----------------------------------------------------------------
// Set hardware parameters
// -----------------------------------------------------------------



void main(void)
{

// Set data directions
SETBIT( THETA_CLK_DDR_PORT, THETA_CLK_BYTE);

SETBIT( SHW_DDR_PORT, SHW_BYTE); 

SETBIT( THETA_ROG_DDR_PORT, THETA_ROG_BYTE); 

SETBIT( TRIGGER_DDR_PORT , TRIGGER_BYTE); 

// Set parameters "Serial Interface"

 UCSRB |= (1 << TXEN); // Sender on
 UCSRB |= (1 << RXEN); //Receiver on
 
 UCSRC |= (1 << URSEL) | (1 << UCSZ1) | (1 << UCSZ0); // 8 data bits and switch on
 UBRRH = 0;

 //UCSRA |= (1 << U2X);
 //UBRRL = 207; // 9600 Baud at 16 MHz U2X = 1 (OK)

 //UCSRA |= (1 << U2X);
 //UBRRL = 103; //19200 Baud at 16 MHz U2X = 1  (OK)

 //UCSRA |= (1 << U2X);
 //UBRRL = 51; //38400 Baud at 16 MHz U2X = 1  (OK)

 UCSRA |= (1 << U2X);
 UBRRL = 34; //57600 Baud at 16 MHz U2X = 1 (OK)

 //UCSRA |= (1 << U2X);
 //UBRRL = 16; //115200 Baud at 16 MHz U2X = 1 (OK)

 // Set Parameters Analog Digital Converter

 ADMUX = (1 << REFS0);
 ADCSR = (1 << ADEN) | (1 << ADPS2) | (1 << ADPS1) | (1 << ADPS0);


// -----------------------------------------------------------------
// Declarations
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

// empty array at start

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

	// For test, reset trigger:
	UNSETBIT( TRIGGER_PORT , TRIGGER_BYTE); 

	// Wait until any character is received, only then analysis will be done
	while ( ! (UCSRA & (1 << RXC)));
	zeichen_ein = UDR;

	if (zeichen_ein == 0x49) //I
		{
		// Transfer integration time in ms for exposure
		// The next 6 characters are interpretated as integration time in ms. 
		// The time is to be transfered with leading zeros
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
		putstring( s ); // Return integration time
		putch( cr );
		putch( lf );
		integrationTimeIn_ms = strtol( s , conversionError , 10 );

		putch('I'); // Give feedback with I
		ltoa( integrationTimeIn_ms, s, 10 ); // 10 for radix -> decimal system
		putch( cr );
		putch( lf );
		putstring( s ); // Return integration time
		putch( cr );
		putch( lf );
		putstring( conversionError ); // Return integration time
		putch( cr );
		putch( lf );

		putch('E'); // Inform about end with E

		}

	else if (zeichen_ein == 0x4A) //J
		{
		// Transfer integration time in ms for exposure
		// The next 6 characters are interpretated as integration time in ms.
		// The time is to be transfered with leading zeros
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
		putstring( s ); // Return integration time

		integrationTimeIn_ms = strtol( s , conversionError , 10 );

		// Shorted transfer of data for the java programm
		// Required with T
		// Returned is: 
		// 6. A character T is given back
		// 7. character is zero
		// 8. character is low byte of first datapoint
		// 9. character is high byte of first datapoint
		// ...
		// A zero
		// five times 0xFF
		// The last character is T
		unsigned char lowByteADC = 0;
		unsigned char highByteADC = 0;

		// For test, inform about receiving a character by setting trigger:
		SETBIT( TRIGGER_PORT , TRIGGER_BYTE); 

		// Now remove charge, empty array, integrate light and remove charge again.
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

			// start ACD conversion (Dummy, so that period length are equal)
			ADCSR |= (1 << ADSC);
			while(ADCSRA & (1 << ADSC)); //Wait for end of conversion
			//adcvalue = (unsigned int) ADCL + ((unsigned int) ADCH << 8 );

			putch( highByteADC );


			SETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);

			// Start ADC conversion
			ADCSR |= (1 << ADSC);
			while(ADCSRA & (1 << ADSC)); //Wait for end of conversion
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


		} // Final J
	else
		{


		// For test, inform about receiving a character by setting trigger:
		SETBIT( TRIGGER_PORT , TRIGGER_BYTE); 

		// Now remove charge, empty array, integrate light and remove charge again.
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



			// start ACD conversion (Dummy, so that period length are equal)
			ADCSR |= (1 << ADSC);
			while(ADCSRA & (1 << ADSC)); //Wait for end of conversion
			adcvalue = (unsigned int) ADCL + ((unsigned int) ADCH << 8 );




			utoa( count, s, 10 ); // 10 fuer radix -> Dezimalsystem



			putstring( s );
			// measure start of first half
			SETBIT( TRIGGER_PORT , TRIGGER_BYTE); 
			putch(';');

			// measure end of first half 
			UNSETBIT( TRIGGER_PORT , TRIGGER_BYTE); 


			SETBIT( THETA_CLK_PORT, THETA_CLK_BYTE);

			// Start ADC conversion
			ADCSR |= (1 << ADSC);
			while(ADCSRA & (1 << ADSC)); //Wait for end of conversion
			//adcvalue = (unsigned int) ADCL;
			adcvalue = (unsigned int) ADCL + ( ( (unsigned int) ADCH ) << 8 );

			utoa( adcvalue, s , 10 ); // 10 for radix -> Dezimalsystem
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