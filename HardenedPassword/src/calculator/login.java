package calculator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class login 
{
	static String QA[][] = new String[5][4];

public static void main(String[] args) throws IOException 
{
	int x,y, qa1,qa2;
	String UserName = new String();
	String Pwd = new String();
	Scanner in = new Scanner(System.in);
	{
	QA[0][3] = "How far away from the college are you (in km)?";
	QA[1][3] = "How many Jelly Beans can fit in 100cc jar?";
	QA[2][3] = "How many hours do you wish to be logged in?";
	QA[3][3] = "What is your weight in kg?";
	QA[4][3] = "How deep do you think is pacific ocean (in km)";
	}
	
	Formulae F = new Formulae();
	
	System.out.println("What do you want to do:");
	System.out.println("1. Create new user account");
	System.out.println("2. Already existing user, enter credentials");
	System.out.println("\n\n Enter your Choice:");
	x = in.nextInt();
	
	System.out.println("Enter the User Name:");
	UserName = in.next();
	System.out.println("Enter the Password:");
	Pwd = in.next();
	
	if(x==1)
	{
		for (int i=0; i< 5; i++)
		{
			System.out.println(QA[i][3]);
			QA[i][1] = in.next();
			QA[i][2] = QA[i][1];
			qa1 = Integer.valueOf(QA[i][1]);
			qa2 = Integer.valueOf(QA[i][2]);
			if( qa1 > qa2) { QA[i][0] = "B";}
			else if( qa1 < qa2) { QA[i][0] = "A";}
			else if( qa1 == qa2) { QA[i][0] = "AB";}
			
		}
		F.genPrime();
		F.setHpwd();
		F.calcPolynomial();
		F.setInstTab();
		//set history file
		//encrypt
	}
	
	else if(x==2)
		{
		System.out.println("choose to answer some security questions:\n 1. YES,\t2. NO");
		y = in.nextInt(); qa1= 0; qa2 = 0;
		switch (y)
		  {
		  case 1: 
			  		for (int i=0; i< 5; i++)
			  		{
			  			System.out.println(QA[i][3]);
			  			QA[i][1] = in.next();
			  			qa1 = Integer.valueOf(QA[i][1]);
			  			qa2 = Integer.valueOf(QA[i][2]);
			  			if( qa1 > qa2) { QA[i][0] = "B";}
			  			else if( qa1 < qa2) { QA[i][0] = "A";}
			  			else if( qa1 == qa2) { QA[i][0] = "AB";}
			  		}
			  		break;
		  case 2:
			  		for (int i=0; i< 5; i++)
			  		{
					QA[i][0] = "AB";
			  		}
			  		break;
		  default: break;
		  }
				// XY calculation from instruction table using the Alpha or Beta values 
							//we get from QA
				// Hpwd calculation using the XY values
				// decrypt and display and update
		}
	in.close();
}

}
