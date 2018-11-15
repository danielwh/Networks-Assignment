import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	
	private Socket clientSocket;
	private BufferedReader userIn;
	private BufferedReader serverIn;
	private PrintWriter out;

	public static void main(String[] args) {
		try 
		{
			Client client = new Client();
			client.getSongs();
		} 
		catch (IOException e) 
		{
			System.out.println("Sorry, an error has occured.");
		}
	}
	
	public Client() throws IOException
	{
		clientSocket = new Socket("localhost",22222);
		userIn = new BufferedReader(new InputStreamReader(System.in));
		serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out = new PrintWriter(clientSocket.getOutputStream());
		System.out.println("Connected to server.");
	}
	
	public void getSongs() throws IOException
	{
		System.out.println("Please enter the artist you are interested in:");
		String artist = userIn.readLine();
		out.println(artist);
		String songs = serverIn.readLine();
		System.out.println(songs);
		String quit = "";
		do
		{
			System.out.println("Please enter 'quit' to disconnect from the server.");
			quit = userIn.readLine();
		}while(!quit.equalsIgnoreCase("quit"));
		clientSocket.close();
		System.out.println("Connection terminated.");
	}

}
