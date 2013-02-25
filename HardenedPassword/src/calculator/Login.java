package calculator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Login 
{
	static String QA[][] = new String[5][4];
	static String userName;
	static String pwd;

	/**
	 * @param in
	 * @return
	 * @throws NumberFormatException
	 */
	private static void securityQA(Scanner in) throws NumberFormatException {
		int y;
		int qa1;
		int qa2;
		int i;
		for (i = 0; i < 5; i++)
		{
			System.out.println(QA[i][3]);
			System.out.println("Do you wish to answer this question?\n1. YES\t2. NO");
			y = in.nextInt();
			qa1 = qa2 = 0;
			switch(y)
			{
			  case 1:		System.out.print("Answer: ");
				  			QA[i][1] = in.next();
				  			qa1 = Integer.valueOf(QA[i][1]);
				  			qa2 = Integer.valueOf(QA[i][2]);
				  			if(qa1 > qa2)
				  				QA[i][0] = "B";
				  			else
				  				if(qa1 < qa2)
				  					QA[i][0] = "A";
				  				else
				  					if(qa1 == qa2)
				  						QA[i][0] = "AB";
				  		break;
				  		
			  case 2:	QA[i][0] = "AB";
				  		break;
				  		
			  default:	break;
			  }
		}
	}
	
	public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException 
	{
		int x;
		String choice;
		userName = new String();
		pwd = new String();
		FileReader fr;
		FileWriter fw;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		Scanner in = new Scanner(System.in);
		{
		QA[0][3] = "How far away from the college are you (in miles)?";	QA[0][2] = "13";
		QA[1][3] = "How many Jelly Beans can fit in a 1000 cc jar?";	QA[1][2] = "240";
		QA[2][3] = "How many hours do you wish to be logged in?";	QA[2][2] = "5";
		QA[3][3] = "What is your weight (in pounds)?";	QA[3][2] = "220";
		QA[4][3] = "How deep do you think is the Pacific Ocean (in miles)?";	QA[4][2] = "7";
		}
		
		Formulae f = new Formulae();
		
//		do
//		{
			System.out.println("Menu:");
			System.out.println("1. Create account");
			System.out.println("2. Login");
			System.out.println("Enter your Choice:");
			x = in.nextInt();
			
			System.out.println("User Name:");
			userName = in.next();
			System.out.println("Password:");
			pwd = in.next();
			if(x==1)
			{
				securityQA(in);
				
				if(!f.randVal.exists())
					f.randVal.createNewFile();
				if(!f.instTable.exists())
					f.instTable.createNewFile();
				if(!f.history.exists())
					f.history.createNewFile();
				
				f.genRandom();
				OutputStream os = new FileOutputStream(f.randVal);
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(f.r);
				os.close();
				f.genPrime();
				f.setHpwd();
				f.calcPolynomial();
				f.setInstTab();
				os = new FileOutputStream(f.instTable);
				oos = new ObjectOutputStream(os);
				oos.writeObject(f.instTab);
				os.close();
				//set history file
				//encrypt
				f.test();
			}
			else
				if(x == 2)
				{
					securityQA(in);
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f.randVal));
					f.r = (SecretKey) ois.readObject();
					ois.close();
//					try
//					{
//						fr = new FileReader(f.randVal);
//						reader = new BufferedReader(fr);
//					}
//					catch(Exception e)
//					{
//						e.printStackTrace();
//					}
//					f.r = new SecretKeySpec(reader.readLine().getBytes(), 0, reader.readLine().getBytes().length, "HmacSHA1");
					ois = new ObjectInputStream(new FileInputStream(f.instTable));
					f.instTab = (BigInteger[][]) ois.readObject();
					ois.close();
					f.xyCalc(QA);		// XY calculation from instruction table using the Alpha or Beta values 
										//we get from QA
					f.generateHPWD();		// Hpwd calculation using the XY values
							// decrypt and display and update
					f.test();
				}
//			System.out.println("Do you wish to continue (Y/N)?");
//			choice = in.next();
//		}
//		while(choice.compareToIgnoreCase("y") == 0);
		in.close();
	}
	
}
