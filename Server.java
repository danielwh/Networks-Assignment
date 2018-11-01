import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class Server {
	
	private HashMap<String,String> songs;
	private File file;

	public static void main(String[] args) {
		try
		{
			Server server = new Server();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("An error has occured, please try again.");
		}
	}
	
	public Server() throws FileNotFoundException
	{
		songs = new HashMap<>();
		file = new File("Worst100");
		Scanner sc = new Scanner(file);
		while (sc.findWithinHorizon("-",0) != null)
		{
			String line = sc.nextLine();
			Scanner scLine = new Scanner(line);
		}
	}

}
