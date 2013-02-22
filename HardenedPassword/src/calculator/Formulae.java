/**
 * Formulae to calculate hardened password parameters
 */
package calculator;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author Anish
 *
 */

public class Formulae {

	BigInteger q;		//Large Prime Number
	BigInteger hpwd;	//Hardened Password
	Random rand;		//Random Number
	double mean[];		//Mean
	double sd[];		//Standard Deviation
	double t[];			//Threshold
	double phi[];		//Distinguishing Feature
	int b[];			//Feature Descriptor
	int h;				//Size of History File
	int m;				//Number of questions
	int instTab[][];	//Instruction Table
	double alpha;
	double beta;
	double y[][];		//Result of polynomial evaluation
	
	Formulae()
	{
		rand = new Random();
		hpwd = BigInteger.valueOf(-1);
		m = 5;
//		h = ;
	}
	
	void genPrime()	//Returns a 160 bit random prime number
	{
		q = BigInteger.probablePrime(160, rand);
	}
	
	void setHpwd()	//Sets hardened password during initialization
	{
		hpwd = hpwd.add(q);
	}
	
	void calcMeanSD()	//Calculates mean and standard deviation
	{
//		int i;
		
	}
	
	void isDistinguishing()	//Determines if the features are distinguishing or otherwise
	{
//		int k;
	}
	
	void calcPolynomial()
	{
		
	}
	
	void setInstTab()	//Calculates the values of alpha and beta, and creates the instruction table
	{
		int i, j;
		
		for(i = 0; i < m; i++)
			for(j = 0; j < 2; j++)
			{
				
			}
	}
	
	
	
}
