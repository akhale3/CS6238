package calculator;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

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
	
	public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException 
	{
		int x;
		String choice;
		userName = new String();
		pwd = new String();
		Scanner in = new Scanner(System.in);
		{
		QA[0][3] = "How far away from the college are you (in miles)?";	QA[0][2] = "13";
		QA[1][3] = "How many Jelly Beans can fit in a 1000 cc jar?";	QA[1][2] = "240";
		QA[2][3] = "How many hours do you wish to be logged in?";	QA[2][2] = "5";
		QA[3][3] = "What is your weight (in pounds)?";	QA[3][2] = "220";
		QA[4][3] = "How deep do you think is the Pacific Ocean (in miles)?";	QA[4][2] = "7";
		}
		
		Formulae f = new Formulae();
		
		do
		{
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
				f.genPrime();
				f.setHpwd();
				f.calcPolynomial();
				f.setInstTab();
				//set history file
				//encrypt
				f.test();
			}
			else
				if(x == 2)
				{
					securityQA(in);
					f.xyCalc(QA);		// XY calculation from instruction table using the Alpha or Beta values 
										//we get from QA
					f.generateHPWD();		// Hpwd calculation using the XY values
							// decrypt and display and update
					f.test();
				}
			System.out.println("Do you wish to continue (Y/N)?");
			choice = in.next();
		}
		while(choice.compareToIgnoreCase("y") == 0);
		in.close();
	}
	
}
