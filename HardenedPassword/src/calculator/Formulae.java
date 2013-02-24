/**
 * Formulae to calculate hardened password parameters
 */
package calculator;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.KeyGenerator;
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
	BigInteger x[][], y[][];	//Result of polynomial evaluation
	Mac g1, g2;
	SecretKey r, rpwd;
	int a[];			//Random coefficients for calculating polynomial
	
	Formulae() throws NoSuchAlgorithmException
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
		x = new BigInteger[m][2];
		y = new BigInteger[m][2];
		r = KeyGenerator.getInstance("HmacSHA1").generateKey();
		a = new int[m];
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
	
	void calcPolynomial() throws NoSuchAlgorithmException	//Calculate the value of y0 and y1
, InvalidKeyException
	{
		int i, j;
		g1 = Mac.getInstance("HmacSHA1");
		g2 = Mac.getInstance("HmacSHA1");
		rpwd = new SecretKeySpec(Login.pwd.getBytes(), "HmacSHA1");
		g1.init(r);
		g2.init(rpwd);
		y[0][0] = y[0][1] = hpwd;
		for(i = 1; i < m; i++)
		{
			x[i][0] = new BigInteger(g1.doFinal(BigInteger.valueOf(2*i).toByteArray())); 
			x[i][1] = new BigInteger(g1.doFinal(BigInteger.valueOf(2*i+1).toByteArray()));
			y[i][0] = y[i][1] = hpwd;
			a[i] = rand.nextInt();
		}
		
		for(i = 0; i < m; i++)
		{
			for(j = 1; j < m; j++)
			{
				y[i][0] = y[i][0].add(BigInteger.valueOf(a[j]).multiply(x[j][0].pow(j)));
				y[i][1] = y[i][1].add(BigInteger.valueOf(a[j]).multiply(x[j][1].pow(j)));
			}
		}
	}
	
	void calcAlphaBeta() 	//Calculates the value of Alpha and Beta
	{
		int i;
		for(i = 0; i < m; i++)
		{
//			y[i][0] = y[i][1] = BigInteger.valueOf(0);
			alpha[i] = y[i][0].add(new BigInteger(g2.doFinal(BigInteger.valueOf(2*i).toByteArray())).mod(q));
			beta[i] = y[i][1].add(new BigInteger(g2.doFinal(BigInteger.valueOf(2*i+1).toByteArray())).mod(q));
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
		
		//genPrime();
		System.out.println("q = " + q);
		
		//setHpwd();
		System.out.println("hpwd = " + hpwd);
		
		calcAlphaBeta();
		System.out.println("g = " + g1 + " " + g2);
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
