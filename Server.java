import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
	
	private HashMap<String,String> songs;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private FileWriter writer;
	private DateTimeFormatter formatter;
	private LocalDateTime datetime;
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
		formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		writer = new FileWriter("server.log", true);
		//Set up a server socket on port 22222
		serverSocket = new ServerSocket(22222);
		datetime = LocalDateTime.now();
		writer.append("\r\nServer started: " + formatter.format(datetime));
		writer.flush();
		songs.put("Debby Boone", "You Light Up My Life");
//		Scanner sc = new Scanner("Worst100.txt");
//		while (sc.findWithinHorizon("-",0) != null)
//		{
//			String line = sc.nextLine();
//			Scanner scLine = new Scanner(line);
//		}		
	}
	
	public void listen() throws IOException
	{
		try
		{
			String artist;
			//Wait for a connection request from a client and accept it
			clientSocket = serverSocket.accept();
			instant1 = Instant.now();
			datetime = LocalDateTime.now();
			writer.append("\r\nSuccessful connection to client: " + formatter.format(datetime));
			writer.flush();
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//Receive artist name from the client
			artist = in.readLine();
			writer.append("\r\nArtist name received: " + artist);
			writer.flush();
			if (!songs.containsKey(artist))
			{
				//Return error message if the artists is not contained in the map
				out.println("Sorry, this artist is not contained in our records.");
				out.flush();
			}
			else
			{
				//Else return the list of songs held in the map
				out.println(songs.get(artist));
				out.flush();
			}
			String quit = "";
			do
			{
				//Wait for message to close connection from client
				quit = in.readLine();
			}while(!quit.equalsIgnoreCase("quit"));
			//Close the connection
			clientSocket.close();
			serverSocket.close();
			instant = Instant.now();
			datetime = LocalDateTime.now();
			long period = instant.toEpochMilli()-instant1.toEpochMilli();
			writer.append("\r\nConnection to client terminated: " + formatter.format(datetime) + "\r\nConnection time: " + period + " ms (~" + period/1000 + " seconds)");
			writer.flush();
		}
		catch(IOException e)
		{
			datetime = LocalDateTime.now();;
			writer.append("\r\nConnection error: " + formatter.format(datetime));
			writer.flush();
		}
	}
}
