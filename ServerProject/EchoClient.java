//Simple echo client.



import java.io.*;
import java.net.*;
import java.net.UnknownHostException;

public class EchoClient {
	static DatagramSocket clientSocket;

	public static void main(String[] args) throws Exception {
		try {
			// Open a UDP datagram socket
			clientSocket = new DatagramSocket();
			//Creates a new IntAddress destination to use later
			InetAddress destination = null;

			// Prepare for transmission
			// Determine server IP address	

			// Approach 1: from the host name
			// Our example is "localhost" or "127.0.0.1"
			//*
			String hostname = "localhost";
			//String hostname = "127.0.0.1";

			try {	
				// Uses turns the name local host into the loop back address 127.0.0.1
				destination = InetAddress.getByName(hostname);
				// Catches the error if we can not turn a string host name into a InetAddress
			} catch (UnknownHostException e) {
				System.out.println("Unable to determine the host by name!");
				System.exit(-1);
			}
			//*/		

			// Approach 2: from the IP address
			/*
			byte[] b = new byte[] {(byte)127,(byte)0,(byte)0,(byte)1};
			try {
	        		destination = InetAddress.getByAddress(b);
			} catch (UnknownHostException e) {
				System.out.println("Unable to determine the host by address!");
				System.exit(-1);
			}
			 */

			// Determine server port number
			// Sets the port number variable to the port that we want to send to
			int serverPortNumber = 55094;

			// Message and its length	
			//Is the string that we are going to send to the server
			String message = "Hey Jarvis, what time is it?";
			//We store the length of the message as an int
			int lengthOfMessage = message.length(); 
			// A byte array is made that is the length of the message
			byte[] data = new byte[lengthOfMessage];
			//Our message string is turned into bytes and stored in our array
			data = message.getBytes();

			// Create a datagram
			// A packet is created from our data array, it's length, it's destination, and the server port number
			DatagramPacket datagram = 
					new DatagramPacket(data, lengthOfMessage, destination, serverPortNumber);
			
			// Creates a buffer to store a recived message
			byte[] receivedData = new byte[2048];
		
			// Send a datagram carrying the message
			//Sends the packet that we created
			clientSocket.send(datagram);

			// Print out the message sent
			//Shows what we sent
			System.out.println(message);

			// Prepare for receiving
			// Create a buffer for receiving
			// byte[] receivedData = new byte[2048];

			// Create a datagram
			// Wraps the recivedData array in a DatagramPacket object
			DatagramPacket receivedDatagram = 	
					new DatagramPacket(receivedData, receivedData.length);

			// Receive a datagram
			// The programs stops and waits until the client sends a packet then it fills the buffer that we created with the packet as well as where it came from 
			clientSocket.receive(receivedDatagram);

			// Display the message in the datagram
			// Turns the datagram packet into a printable string
			String echoMessage = new String(receivedData, 0, receivedDatagram.getLength());
			// Prints the message that we recived
			System.out.println(echoMessage);	

		} 
		// The catch that we use to handle our errors
		catch (IOException ioEx) {
			ioEx.printStackTrace(); 
		} 
		finally {
			// Close the socket 
			clientSocket.close();
		}
	}

}
