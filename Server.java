import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
	
	private HashMap<String,String> songs;
	private File file;
	private File logFile;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private FileWriter writer;
	private DateTimeFormatter formatter;
	private Instant instant;
	private Instant instant1;

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
		formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		logFile = new File("server.log");
		writer = new FileWriter(logFile);
		//Set up a server socket on port 22222
		serverSocket = new ServerSocket(22222);
		instant = Instant.now();
		writer.write("\nServer started: " + formatter.format(instant));
		Scanner sc = new Scanner(file);
		while (sc.findWithinHorizon("-",0) != null)
		{
			String line = sc.nextLine();
			Scanner scLine = new Scanner(line);
		}		
	}
	
	public void listen() throws IOException
	{
		try
		{
			String artist;
			//Wait for a connection request from a client and accept it
			clientSocket = serverSocket.accept();
			instant1 = Instant.now();
			writer.write("\nSuccessful connection to client: " + formatter.format(instant1));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//Receive artist name from the client
			artist = in.readLine();
			writer.write("\nArtist name received: " + artist);
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
			//Close the connection
			clientSocket.close();
			instant = Instant.now();
			long period = instant.toEpochMilli()-instant1.toEpochMilli();
			writer.write("\nConnection to client terminated: " + formatter.format(instant) + "\nConnection time: " + period + " ms (~" + period/1000 + " seconds)");
		}
		catch(IOException e)
		{
			instant = Instant.now();
			writer.write("\nConnection error: " + formatter.format(instant));
		}
	}
}
