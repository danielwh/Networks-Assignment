import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
	
	private HashMap<String,String> songs;
	private File file;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public static void main(String[] args) {
		try
		{
			Server server = new Server();
			server.listen();
		}
		catch(IOException e)
		{
			System.out.println("An error has occured, please try again.");
		}
	}
	
	public Server() throws IOException 
	{
		songs = new HashMap<>();
		file = new File("Worst100");
		serverSocket = new ServerSocket(22222);
		Scanner sc = new Scanner(file);
		while (sc.findWithinHorizon("-",0) != null)
		{
			String line = sc.nextLine();
			Scanner scLine = new Scanner(line);
		}
		
	}
	
	public void listen() throws IOException
	{
		String artist;
		clientSocket = serverSocket.accept();
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		artist = in.readLine();
		if (!songs.containsKey(artist))
		{		
			out.println("Sorry, this artist is not contained in our records.");
		}
		else
		{
			out.println(songs.get(artist));
		}
		String quit = "";
		do
		{
			quit = in.readLine();
		}while(!quit.equalsIgnoreCase("quit"));
		clientSocket.close();
		serverSocket.close();
	}
	

}
