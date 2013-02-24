/**
 * Formulae to calculate hardened password parameters
 */
package calculator;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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
	BigInteger instTab[][];	//Instruction Table
	BigInteger alpha[];
	BigInteger beta[];
	BigInteger y[][];	//Result of polynomial evaluation
	Mac g;
	SecretKey r;
	
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
		instTab = new BigInteger[m][2];
		alpha = new BigInteger[m];
		beta = new BigInteger[m];
		y = new BigInteger[m][2];
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
, InvalidKeySpecException
	{
		int i;
		g = Mac.getInstance("HmacSHA1");
		r = new SecretKeySpec(Login.pwd.getBytes(), "HmacSHA1");
//		PBEKeySpec keySpec = new PBEKeySpec(Login.pwd.toCharArray());
//		r = SecretKeyFactory.getInstance("HmacSHA1").generateSecret(keySpec);
//		r = KeyGenerator.getInstance("HmacSHA1").generateKey();
		g.init(r);
		for(i = 0; i < m; i++)
		{
			y[i][0] = y[i][1] = BigInteger.valueOf(0);
			alpha[i] = y[i][0].add(new BigInteger(g.doFinal(BigInteger.valueOf(2*i).toByteArray())).mod(q));
			beta[i] = y[i][1].add(new BigInteger(g.doFinal(BigInteger.valueOf(2*i+1).toByteArray())).mod(q));
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
	
	void test() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		int i, j;
		
		genPrime();
		System.out.println("q = " + q);
		
		setHpwd();
		System.out.println("hpwd = " + hpwd);
		
		calcAlphaBeta();
		System.out.println("g = " + g);
		System.out.println("r = " + r);
		
		setInstTab();
		for(i = 0; i < m; i++)
		{
			for(j = 0; j < 2; j++)
				System.out.print(instTab[i][j] + " ");
			System.out.println();
		}
	}
	
}
