import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.time.Instant;
import java.time.LocalDateTime;
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
	private LocalDateTime datetime;

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
		writer = new FileWriter("client.log", true);
		formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		//Attempt to make a connection to the server on port 22222
		clientSocket = new Socket("localhost",22222);
		datetime = LocalDateTime.now();
		writer.append("\r\nConnection to server established: " + formatter.format(datetime));
		writer.flush();
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
		out.flush();
		instant = Instant.now();
		//Receive the list of songs from the server
		String songs = serverIn.readLine();
		instant1 = Instant.now();
		datetime = LocalDateTime.now();
		writer.append("\r\nServer response received: " + formatter.format(datetime) + "\r\nResponse length: " + songs.getBytes().length + " bytes\r\nResponse time: " + (instant1.toEpochMilli()-instant.toEpochMilli()) + " ms");
		writer.flush();
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
		out.flush();
		clientSocket.close();
		System.out.println("Connection terminated.");
	}
}
