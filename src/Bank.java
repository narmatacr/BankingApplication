import java.util.*;
import java.sql.*;
public class Bank 
{
public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		char op;
		do {
		System.out.println("********************************************");
		System.out.println("Bank Management System");
		System.out.println("********************************************");
		System.out.println("A. Admin");
		System.out.println("B. Officer");
		System.out.println("C. Customer");
		op=sc.next().charAt(0);
		switch(op)
		{
		case 'A':
			admin();
			break;
		case 'B':
			officer();
			break;
		case 'C':
			customer();
			break;
		default:
			System.out.println("Invalid Option!!.Please enter again");
			System.out.println("\n");
			main(args);
		}
		}
		while(op!='A'||op!='B'||op!='C');
	}
static void customer()
{
	Scanner sc=new Scanner(System.in);
	System.out.println("***************************************************");
	System.out.println("Enter your Email and password");
	System.out.println("***************************************************");
	System.out.println();
	String email=sc.next();
	String pwd=sc.next();
	try {
	Class.forName("com.mysql.jdbc.Driver");
	java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
	Statement st= con.createStatement();
	String sql="select * from customer where email='"+email+"'";
	ResultSet rs=st.executeQuery(sql);
	int cnt=0;
	while(rs.next()) {
		cnt++;
		if(rs.getString("pwd").equals(pwd))
		{
			Customer obj=new Customer(email);
			obj.customerMenu();
		}
		else
			{System.out.println("Password incorrect");
			System.out.println();
			 customer();
			}
	}
	if(cnt==0)
	{
		System.out.println("User does not exist");
		System.out.println();
		customer();
	}
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
}
static void officer()
{
	Scanner sc=new Scanner(System.in);
	System.out.println("***************************************************");
	System.out.println("Enter your ID and password");
	System.out.println("***************************************************");
	System.out.println();
	String email=sc.next();
	String pwd=sc.next();
	try {
	Class.forName("com.mysql.jdbc.Driver");
	java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
	Statement st= con.createStatement();
	String sql="select * from officer where email='"+email+"'";
	ResultSet rs=st.executeQuery(sql);
	int cnt=0;
	while(rs.next()) {
		cnt++;
		if(rs.getString("pwd").equals(pwd))
		{
			String name=rs.getString("name");
			Officer obj=new Officer(email,name);
			obj.officerMenu();
		}
		else
			{System.out.println("Password incorrect");
			System.out.println();
			 officer();
			}
	}
	if(cnt==0)
	{
		System.out.println("Email ID does not exist");
		System.out.println();
		officer();
	}
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
}
static void admin()
{
	Scanner sc=new Scanner(System.in);
	System.out.println("***************************************************");
	System.out.println("Enter Admin ID and password");
	System.out.println("***************************************************");
	System.out.println();
	String email=sc.next();
	String pwd=sc.next();
	if(email.equals("admin@gmail.com")&&pwd.equals("123"))
	{
		Admin obj=new Admin();
		obj.adminMenu();
	}
	else
	{
		System.out.println("Invalid credentials");
		admin();
	}
}
}
class Customer
{
	int balance;
	int previousTransaction;
	String cname;
	String acc_no,email;
	Scanner sc=new Scanner(System.in);
	Customer(String email)
	{
		this.email=email;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
			Statement st= con.createStatement();
			String sql="select * from customer where email='"+email+"'";
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				cname=rs.getString("name");
				balance=rs.getInt("amt");
				acc_no=rs.getString("acc_no");
			}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			
	    
	}
	void deposit(int amount)
	{
		if(amount>0)
		{
			balance=balance+amount;
			previousTransaction=amount;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
				Statement st= con.createStatement();
				String sql="update customer set amt="+balance+" where acc_no='"+acc_no+"'";
				st.execute(sql);
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			System.out.println("Successfully deposited!!!");
		}
		else
			System.out.println("Enter a valid amount");
	}
	void withdraw(int amount)
	{
		if(amount>0 && amount<=balance)
		{
			balance=balance-amount;
			previousTransaction=-amount;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
				Statement st= con.createStatement();
				String sql="update customer set amt="+balance+" where acc_no='"+acc_no+"'";
				st.execute(sql);
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			System.out.println("Successfully withdrawn!!!");
		}
		else if(amount<=0)
			System.out.println("Enter a valid amount");
		else
			System.out.println("Your balance is insufficient to withdraw the amount");
	}
	void getPreviousTransaction()
	{
		if(previousTransaction>0)
			System.out.println("Deposited : "+previousTransaction);
		else if(previousTransaction<0)
			System.out.println("Withdrawn : "+Math.abs(previousTransaction));
		else
			System.out.println("No transaction occured");
	}
	void changePassword(String pass)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
			Statement st= con.createStatement();
			String sql="update customer set pwd='"+pass+"' where acc_no='"+acc_no+"'";
			st.execute(sql);
			System.out.println("Password updated!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	void customerMenu()
	{
		char op;
		int n;
		String pass;
		System.out.println("Welcome "+cname);
		System.out.println("Your Account No is "+acc_no);
		System.out.println();
		System.out.println("A. Check balance");
		System.out.println("B. Deposit");
		System.out.println("C. Withdraw");
		System.out.println("D. Previous transaction");
		System.out.println("E. Change password");
		System.out.println("F. Exit");
		do
		{
			System.out.println("********************************************");
			System.out.println("Enter an option");
			System.out.println("********************************************");
			op=sc.next().charAt(0);
			System.out.println();
			switch(op)
			{
			case 'A':
				System.out.println("***************************************************");
				System.out.println("Balance = "+balance);
				System.out.println("***************************************************");
				System.out.println();
				break;
			case 'B':
				System.out.println("***************************************************");
				System.out.println("Enter an amount to deposit:");
				System.out.println("***************************************************");
				n=sc.nextInt();
				deposit(n);
				System.out.println();
				break;
			case 'C':
				System.out.println("***************************************************");
				System.out.println("Enter an amount to withdraw:");
				System.out.println("***************************************************");
				n=sc.nextInt();
				withdraw(n);
				System.out.println();
				break;
			case 'D':
				System.out.println("***************************************************");
				getPreviousTransaction();
				System.out.println("***************************************************");
				System.out.println();
				break;
			case 'E':
				System.out.println("***************************************************");
				System.out.println("Enter new password:");
				System.out.println("***************************************************");
				pass=sc.next();
				changePassword(pass);
				System.out.println();
				break;
			case 'F':
				break;
			default:
				System.out.println("Invalid Option!!.Please enter again");
				System.out.println("\n");
				break;
		 }
		}
		while(op!='F');
		System.out.println("*****Thank you for using our services !!! *****");
		Bank.main(null);
	}
	
}
class Officer
{
	String email;
	String name;
	String cname,ph_no,acc_no,pwd;
	Scanner sc=new Scanner(System.in);
	Officer(String email,String name)
	{
		this.email=email;
		this.name=name;
	}
	void officerMenu()
	{
		char op;
		String cname1,ph_no1,email1,acc_no1,pwd1;
		System.out.println("Welcome "+name);
		System.out.println();
		System.out.println("A. View customers");
		System.out.println("B. Add customer");
		System.out.println("C. Update customer");
		System.out.println("D. Delete customer");
		System.out.println("E. Exit");
		do
		{
			System.out.println("********************************************");
			System.out.println("Enter an option");
			System.out.println("********************************************");
			op=sc.next().charAt(0);
			System.out.println();
			switch(op)
			{
			case 'A':
				viewCustomer();
				break;
			case 'B':
				System.out.println("***************************************************");
				System.out.println("Enter Customer Details");
				System.out.println("***************************************************");
				System.out.println();
				System.out.println("Name : ");
				cname1=sc.next();
				System.out.println("Contact number : ");
				ph_no1=sc.next();
				System.out.println("Email : ");
				email1=sc.next();
				System.out.println("Password : ");
				pwd1=sc.next();
				addCustomer(cname1,ph_no1,email1,pwd1);
				break;
			case 'C':
				System.out.println("***************************************************");
				System.out.println("Update Customer Details");
				System.out.println("***************************************************");
				System.out.println();
				System.out.println("Account number : ");
				acc_no1=sc.next();
				try {
					Class.forName("com.mysql.jdbc.Driver");
					java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
					Statement st= con.createStatement();
					String sql="select * from customer where acc_no='"+acc_no1+"'";
					ResultSet rs=st.executeQuery(sql);
					int cnt=0;
					while(rs.next()) {
						cnt++;
					}
					if(cnt==0)
					{
						System.out.println("Sorry..Account does not exist");
						System.out.println();
						officerMenu();
					}
					else
					{
						System.out.println("Name : ");
						cname1=sc.next();
						System.out.println("Contact number : ");
						ph_no1=sc.next();
						System.out.println("Email : ");
						email1=sc.next();
						System.out.println("Password : ");
						pwd1=sc.next();
						updateCustomer(cname1,ph_no1,email1,pwd1,acc_no1);
					}
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
				
				break;
			case 'D':
				System.out.println("***************************************************");
				System.out.println("Enter Account number to be deleted");
				System.out.println("***************************************************");
				System.out.println();
				acc_no1=sc.next();
				deleteCustomer(acc_no1);
				break;
			case 'E':
				break;
			default:
				System.out.println("Invalid Option!!.Please enter again");
				System.out.println("\n");
				break;	
			}
		}
			while(op!='E');
			System.out.println("*****Thank you for using our services !!! *****");
			Bank.main(null);
	}
	void addCustomer(String cname,String ph_no,String email,String pwd)
	{
		this.cname=cname;
		this.ph_no=ph_no;
		this.email=email;
		this.pwd=pwd;
		double n=Math.random();
		String s=String.valueOf(n);
		s=s.replace(".","");
		while(s.startsWith("0"))
		{
			s=s.substring(1);
		}
		s=s.substring(0,10);
		acc_no=s;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
			Statement st= con.createStatement();
			String sql="select * from customer where email='"+email+"'";
			ResultSet rs=st.executeQuery(sql);
			int cnt=0;
			while(rs.next()) {
				cnt++;
			}
			if(cnt==1)
			{
				System.out.println("Customer with this mail id is already exist");
				System.out.println();
				System.out.println("********************************************");
				System.out.println("Enter a unique mail ID for the user");
				System.out.println("********************************************");
				addCustomer(cname,ph_no,sc.next(),pwd);
			}
			else {
			String sql1="insert into customer values('"+cname+"','"+ph_no+"','"+email+"','"+pwd+"','"+acc_no+"',0)";
			st.executeUpdate(sql1);
			System.out.println("Insertion Successful");
			System.out.println();
			}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
	}
	void updateCustomer(String cname,String ph_no,String email,String pwd,String acc_no)
	{
		this.cname=cname;
		this.ph_no=ph_no;
		this.acc_no=acc_no;
		this.email=email;
		this.pwd=pwd;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
			Statement st1= con.createStatement();
			String sql1="select * from customer where acc_no='"+acc_no+"'";
			ResultSet rs=st1.executeQuery(sql1);
			rs.next();
			if(cname.equals("-"))
				this.cname=rs.getString("name");
			if(ph_no.equals("-"))
				this.ph_no=rs.getString("ph_no");
			if(email.equals("-"))
				this.email=rs.getString("email");
			if(pwd.equals("-"))
				this.pwd=rs.getString("pwd");
			Statement st= con.createStatement();
			String sql="update customer set name='"+this.cname+"',ph_no='"+this.ph_no+"',email='"+this.email+"',pwd='"+this.pwd+"' where acc_no='"+acc_no+"'";
			st.execute(sql);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		System.out.println("Successfully updated!!!");
	}
	void deleteCustomer(String acc_no)
	{
		this.acc_no=acc_no;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
			Statement st= con.createStatement();
			String sql1="delete from customer where acc_no='"+acc_no+"'";
			st.executeUpdate(sql1);
			System.out.println("Deletion Successful");
			System.out.println();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
	}
	void viewCustomer()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
			Statement st= con.createStatement();
			String sql="select * from customer";
			ResultSet rs=st.executeQuery(sql);
			int i=1;
			while(rs.next()) {
				System.out.println(i+".  "+rs.getString("acc_no")+"   "+rs.getString("name")+" "+rs.getString("email")+" "+rs.getString("ph_no"));
				i++;
			}
	        }
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
class Admin
{
	String email;
	String name;
	String pwd;
	Scanner sc=new Scanner(System.in);
	void adminMenu()
	{
		char op;
		String name1,email1,pwd1;
		System.out.println("Welcome Admin");
		System.out.println();
		System.out.println("A. View officers");
		System.out.println("B. Add officer");
		System.out.println("C. Update officer");
		System.out.println("D. Delete officer");
		System.out.println("E. View customers");
		System.out.println("F. Exit");
		do
		{
			System.out.println("********************************************");
			System.out.println("Enter an option");
			System.out.println("********************************************");
			op=sc.next().charAt(0);
			System.out.println();
			switch(op)
			{
			case 'A':
				viewOfficer();
				break;
			case 'B':
				System.out.println("***************************************************");
				System.out.println("Enter Officer Details");
				System.out.println("***************************************************");
				System.out.println();
				System.out.println("Name : ");
				name1=sc.next();
				System.out.println("Email : ");
				email1=sc.next();
				System.out.println("Password : ");
				pwd1=sc.next();
				addOfficer(name1,email1,pwd1);
				break;
			case 'C':
				System.out.println("***************************************************");
				System.out.println("Update Customer Details");
				System.out.println("***************************************************");
				System.out.println();
				System.out.println("Email : ");
				email1=sc.next();
				try {
					Class.forName("com.mysql.jdbc.Driver");
					java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
					Statement st= con.createStatement();
					String sql="select * from officer where email='"+email1+"'";
					ResultSet rs=st.executeQuery(sql);
					int cnt=0;
					while(rs.next()) {
						cnt++;
					}
					if(cnt==0)
					{
						System.out.println("Sorry..Officer with this Email ID does not exist");
						System.out.println();
						adminMenu();
					}
					else
					{
						System.out.println("Name : ");
						name1=sc.next();
						System.out.println("pwd : ");
						pwd1=sc.next();
						updateOfficer(name1,pwd1,email1);
					}
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
				
				break;
			case 'D':
				System.out.println("***************************************************");
				System.out.println("Enter Officer ID to be deleted");
				System.out.println("***************************************************");
				System.out.println();
				email=sc.next();
				deleteOfficer(email);
				break;
			case 'E':
				Officer obj=new Officer("","");
				obj.viewCustomer();
				break;
			case 'F':
				break;
			default:
				System.out.println("Invalid Option!!.Please enter again");
				System.out.println("\n");
				break;	
			}
		}
			while(op!='F');
			System.out.println("*****Thank you for using our services !!! *****");
			Bank.main(null);
	}
	void addOfficer(String name,String email,String pwd)
	{
		this.name=name;
		this.email=email;
		this.pwd=pwd;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
			Statement st= con.createStatement();
			String sql="select * from officer where email='"+email+"'";
			ResultSet rs=st.executeQuery(sql);
			int cnt=0;
			while(rs.next()) {
				cnt++;
			}
			if(cnt==1)
			{
				System.out.println("Officer with this Email ID is already exist");
				System.out.println();
				System.out.println("********************************************");
				System.out.println("Enter Email Id of the Officer");
				System.out.println("********************************************");
				addOfficer(name,sc.next(),pwd);
			}
			else {
			String sql1="insert into officer values('"+name+"','"+email+"','"+pwd+"')";
			st.executeUpdate(sql1);
			System.out.println("Insertion Successful!");
			System.out.println();
			}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
	}
	void updateOfficer(String name,String pwd,String email)
	{
			this.name=name;
			this.pwd=pwd;
			this.email=email;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
				Statement st= con.createStatement();
				String sql="update officer set name='"+name+"',pwd='"+pwd+"' where email='"+email+"'";
				st.execute(sql);
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			System.out.println("Successfully updated!!!");
	}
	void deleteOfficer(String email)
	{
		this.email=email;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
			Statement st= con.createStatement();
			String sql1="delete from officer where email='"+email+"'";
			st.executeUpdate(sql1);
			System.out.println("Deletion Successful");
			System.out.println();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
	}
	void viewOfficer()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","crnarmata");
			Statement st= con.createStatement();
			String sql="select * from officer";
			ResultSet rs=st.executeQuery(sql);
			int i=1;
			while(rs.next()) {
				System.out.println(i+".  "+rs.getString("name")+"   "+rs.getString("email"));
				i++;
			}
	        }
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}