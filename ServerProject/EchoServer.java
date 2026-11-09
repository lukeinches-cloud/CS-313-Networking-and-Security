//Simple echo server.


import java.util.Date;
import java.io.*;
import java.net.*;

public class EchoServer {
	static DatagramSocket serverSocket;
	private static final int PORT = 55094;

	public static void main(String[] args) {
		try {
			// Open a UDP datagram socket with a specified port number
		
			int portNumber = PORT;
			serverSocket = new DatagramSocket(portNumber);
			
			//our startup message to show that the server is running
			System.out.println("Time server starts... ");
			
			//An infinite loop that keeps our server running until we decide to terminate it
			while(true){
			// Create a buffer for receiving
			// An empty buffer for that incoming data gets writen to
			byte[] receivedData = new byte[2048];
	
			
			// Create a datagram
			// Wraps the data in a DatagramPacket object
			DatagramPacket receivedDatagram =
					new DatagramPacket(receivedData, receivedData.length);

			// Receive a datagram
			// The programs stops and waits until the client sends a packet then it fills the buffer that we created with the packet as well as where it came from 
			serverSocket.receive(receivedDatagram);

			// Prepare for sending an echo message
			//Gets the address that the packet was sent from
			InetAddress destination = receivedDatagram.getAddress();
			// Sets the port that we are going to send to, to the port that the packet came from
			int clientPortNumber = receivedDatagram.getPort();	
			// Turns our recived data into a readable string
			String message = new String(receivedData, 0, receivedDatagram.getLength());

			// Display received message and client address		 
			System.out.println("The received message is: " + message);
			System.out.println("The client address is: " + destination);
			System.out.println("The client port number is: " + clientPortNumber);
			
			// Create a buffer for sending
			// Creates a byte array from the date the date and time that we will send back
			byte[] data = new Date().toString().getBytes();



			// Create a datagram
			//Creates a datagram packet from the array of date information that we made
			DatagramPacket datagram = 
					new DatagramPacket(data, data.length, destination, clientPortNumber);

			// Send a datagram carrying the echo message
			// Sends our date datagram packet to the client
			serverSocket.send(datagram);
		}	
		} 
		// The catch that we use to handle our errors
		catch (IOException ioEx) {
			ioEx.printStackTrace();
		} 
		finally {
			// Close the socket 
			serverSocket.close();
		}		

	}

}
