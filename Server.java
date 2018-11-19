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
			//On startup, create a server object and wait for a connection request from a client
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
		//Set up a server socket on port 22222
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
		//Wait for a connection request from a client and accept it
		clientSocket = serverSocket.accept();
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		//Receive artist name from the client
		artist = in.readLine();
		if (!songs.containsKey(artist))
		{
			//Return error message if the artists is not contained in the map
			out.println("Sorry, this artist is not contained in our records.");
		}
		else
		{
			//Else return the list of songs held in the map
			out.println(songs.get(artist));
		}
		String quit = "";
		do
		{
			//Wait for message to close connection from client
			quit = in.readLine();
		}while(!quit.equalsIgnoreCase("quit"));
		//Close both sockets
		clientSocket.close();
		serverSocket.close();
	}
	

}
