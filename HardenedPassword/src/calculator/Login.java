package calculator;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Scanner;

import javax.crypto.SecretKey;

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
			QA[i][1] = String.valueOf(0);
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
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception 
	{
		int x, i, j;
		userName = new String();
		pwd = new String();
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
		
		System.out.println("Menu:");
		System.out.println("1. Create account");
		System.out.println("2. Login");
		System.out.println("Enter your Choice:");
		x = in.nextInt();
		
		System.out.println("User Name:");
		userName = in.next();
		System.out.println("Password:");
		pwd = in.next();
		if(x == 1)					//Initialization
		{
			securityQA(in);
			
			if(!f.randVal.exists())
				f.randVal.createNewFile();
			if(!f.instTable.exists())
				f.instTable.createNewFile();
			if(!f.prime.exists())
				f.prime.createNewFile();
			
			f.genRandom();
			OutputStream os = new FileOutputStream(f.randVal);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(f.r);
			os.close();
			f.genPrime();
			os = new FileOutputStream(f.prime);
			oos = new ObjectOutputStream(os);
			oos.writeObject(f.q);
			os.close();
			f.setHpwd();
			f.calcPolynomial();
			f.calcAlphaBeta();
			f.setInstTab();
			writer = new BufferedWriter(new FileWriter(f.instTable));
			for(i = 0; i < f.m; i++)
			{
				for(j = 0; j < 2; j++)
				{
					writer.write(f.instTab[i][j].toString());
					writer.newLine();
				}
			}
			writer.close();
			
			writer = new BufferedWriter(new FileWriter(f.history));
			f.createHFile(QA, writer);	//Setting up history file
			writer.close();
			f.hpwd1 = f.hpwd;
			f.encrypt();				//Encrypt
		}
		else
			if(x == 2)					//Login
			{
				securityQA(in);
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f.randVal));
				f.r = (SecretKey) ois.readObject();
				ois.close();			
				ois = new ObjectInputStream(new FileInputStream(f.prime));
				f.q = (BigInteger) ois.readObject();
				ois.close();
				in = new Scanner(f.instTable);
				while(in.hasNextLine())
				for(i = 0; i < f.m; i++)
				{
					for(j = 0; j < 2; j++)
					{
						f.instTab[i][j] = new BigInteger(in.nextLine());
					}
				}			
				f.xyCalc(QA);			// XY calculation from instruction table using the Alpha or Beta values 
										//we get from QA
				f.generateHPWD();		// Hpwd calculation using the XY values
				f.decrypt();			// Decrypt, display and update
				in = new Scanner(f.history);
				while(in.hasNextLine())
				for(i = 0; i < 10; i++)
				{
					for(j = 0; j < f.m; j++)
					{
						f.Hfile[i][j] = Integer.parseInt(in.nextLine());
					}
				}
				if(f.Hfile[10][1] == 0)	//Check for redundancy match
				{
					f.hpwd = f.hpwd1;
					System.out.println("Login Successful");
				}
				else
					System.out.println("Login Failed");
				f.genRandom();
				OutputStream os = new FileOutputStream(f.randVal);
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(f.r);
				os.close();
				f.genPrime();
				os = new FileOutputStream(f.prime);
				oos = new ObjectOutputStream(os);
				oos.writeObject(f.q);
				os.close();
				f.calcPolynomial();
				f.calcAlphaBeta();
				f.setInstTab();
				writer = new BufferedWriter(new FileWriter(f.instTable));
				for(i = 0; i < f.m; i++)
				{
					for(j = 0; j < 2; j++)
					{
						writer.write(f.instTab[i][j].toString());
						writer.newLine();
					}
				}
				writer.close();
				writer = new BufferedWriter(new FileWriter(f.history));
				f.createHFile(QA, writer);	//Setting up history file
				writer.close();
			}
		in.close();
	}
	
}
