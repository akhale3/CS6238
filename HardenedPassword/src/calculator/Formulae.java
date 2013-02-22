/**
 * Formulae to calculate hardened password parameters
 */
package calculator;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

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
	double instTab[][];	//Instruction Table
	double alpha[];
	double beta[];
	double y[][];		//Result of polynomial evaluation
	
	Formulae()
	{
		rand = new Random();
		hpwd = BigInteger.valueOf(-1);
		m = 5;
//		h = ;
		mean = new double[m];
		sd = new double[m];
		t = new double[m];
		phi = new double[m];
		b = new int[m];
		instTab = new double[m][2];
		alpha = new double[m];
		beta = new double[m];
		y = new double[m][2];
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
	
	void calcPolynomial()	//Calculate the value of y0 and y1
	{
		
	}
	
	void calcAlphaBeta() throws NoSuchAlgorithmException, InvalidKeyException	//Calculates the value of Alpha and Beta
	{
		int i;
		Mac g = Mac.getInstance("SHA256");
		SecretKey r = KeyGenerator.getInstance("SHA256").generateKey();
		g.init(r);
		for(i = 0; i < m; i++)
		{
			alpha[i] = y[i][0] + ByteBuffer.wrap(g.doFinal(BigInteger.valueOf(2*i).toByteArray())).getDouble();
			beta[i] = y[i][1] + ByteBuffer.wrap(g.doFinal(BigInteger.valueOf(2*i+1).toByteArray())).getDouble();
		}
	}
	
	void setInstTab()	//Calculates the values of alpha and beta, and creates the instruction table
	{
		int i, j;
		
		for(i = 0; i < 2; i++)
			for(j = 0; j < m; j++)
			{
				if(i == 0)
					instTab[j][i] = alpha[j];
				else
					instTab[j][i] = beta[j];
			}
	}
	
	
	
}
