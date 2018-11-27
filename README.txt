This is a Java implementation of a client-server network system, consisting of two programs: server.java and client.java.

These may be run either by loading them into a java supportive IDE and running them from there or directly through the terminal using the usual compilation and execution commands.
If using a GNU compatible OS (such as Unix-like systems (eg. Linux), macOS, or newer versions of Windows), they may also be run using the enclosed bash scripts.
In both cases, the server program should be run before the client. On successful startup, the server program will print a message to the console; the client should only be started once this message is received.
If the client is started before the server, it will attempt to connect to the (inactive) server, fail, print an error, and terminate. This won't affect the functionality of the system, but you will need to restart the client once the server is running in order to use it.

Once both the server and client are running and a successful connection has been made, you will be prompted to enter the name of an artist through the console.
Once you have done so, a list of songs (or an error if the artist cannot be found) will be printed and you will prompted to terminate the connection by entering 'quit' until you do so.

This system is not designed to handle multiple client requests simultaeneously. Once the 'quit' command has been received, the server closes its sockets and terminates, so you will need to restart the server if you wish to make another request.

This system is designed to work specifically with the 'Worst100.txt' input file. Please ensure that this file is not moved, altered, or renamed, as this may impact the functionality of the system.

The log files generated during testing are enclosed. These may be deleted as wanted, as the system will generate new log files automatically if they are not already present.

If an error occurs (except an artist not being contained in the server), the program will print an error message and then terminate, and so will need to be restarted in order for you to use the system.
If an error occurs on the server, the client will also need to be terminated and restarted.