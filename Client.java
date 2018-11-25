import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class Client {
	
	private Socket clientSocket;
	private BufferedReader userIn;
	private BufferedReader serverIn;
	private PrintWriter out;
	private FileWriter writer;
	private DateTimeFormatter formatter;
	private Instant instant;
	private Instant instant1;
	private File file;

	public static void main(String[] args) {
		try 
		{
			//On start up, create a client object and communicate with the server
			Client client = new Client();
			client.getSongs();
		} 
		catch (ClosedChannelException e)
		{
			System.out.println("Sorry, the server is currently busy or closed.");
		}
		catch (SocketException e)
		{
			System.out.println("Sorry, there was a problem accessing the socket.");
		}
		catch (InterruptedIOException e)
		{
			System.out.println("Sorry, something has interrupted the connection.");
		}
		catch (IOException e) 
		{
			System.out.println("Sorry, an error has occured.");
		}
	}
	
	public Client() throws IOException
	{
		file = new File("client.log");
		writer = new FileWriter(file);
		//Attempt to make a connection to the server on port 22222
		clientSocket = new Socket("localhost",22222);
		instant = Instant.now();
		writer.write("Connection to server established: " + formatter.format(instant));
		userIn = new BufferedReader(new InputStreamReader(System.in));
		serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out = new PrintWriter(clientSocket.getOutputStream());
		System.out.println("Connected to server.");
	}
	
	public void getSongs() throws IOException
	{
		//Prompt the user to enter an artist name
		System.out.println("Please enter the artist you are interested in:");
		String artist = userIn.readLine();
		//Send the name to the server
		out.println(artist);
		instant = Instant.now();
		//Receive the list of songs from the server
		String songs = serverIn.readLine();
		instant1 = Instant.now();
		writer.write("\nServer response received: " + formatter.format(instant1) + "\nResponse length: " + songs.getBytes().length + " bytes\nResponse time: " + (instant1.toEpochMilli()-instant.toEpochMilli()) + " ms");
		//Print the list of songs to the user
		System.out.println(songs);
		String quit = "";
		do
		{
			//Prompt the user to close the connection until they do so
			System.out.println("Please enter 'quit' to disconnect from the server.");
			quit = userIn.readLine();
		}while(!quit.equalsIgnoreCase("quit"));
		//Send the message to the server
		out.println(quit);
		if (clientSocket.isClosed())
		{
			System.out.println("Connection terminated.");
		}
	}
}
