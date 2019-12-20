package com.bpshparis.wa;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class Main {

	private final static int PACKETSIZE = 10000;

	public static void main(String args[]) throws IOException{
		
		f5();

	}

	public static void f6(){

		ExpressionParser parser = new SpelExpressionParser();

		Expression exp = parser.parseExpression("'Hello World'");
		String message = (String) exp.getValue();
		System.out.println(message);//An integer literal

	}
	
	public static void f5() throws IOException {

		Path output = Paths.get("/home/fr054721/wa/WebContent/res/horaires.json");

		List<Horaire> horaires = new ArrayList<Horaire>();

		Horaire mardi = new Horaire();
		mardi.setJour("mardi");
		mardi.setHeures(Arrays.asList("10h", "11h"));
		Horaire jeudi = new Horaire();
		jeudi.setJour("jeudi");
		jeudi.setHeures(Arrays.asList("14h", "15h"));

		horaires.add(mardi);
		horaires.add(jeudi);

		Files.write(output, Tools.toJSON(horaires).getBytes());

	}

	public static void f4() throws IOException {
		
		Properties props = new Properties();
		props.load(new FileInputStream("/home/fr054721/wa/WebContent/res/conf.properties"));
		
		boolean resp = Boolean.parseBoolean(props.getProperty("DEMO"));
		
		if(resp) {
			System.out.println(resp);
		}
		
	}
	
	public static void f3() throws IOException {

		Path path = Paths.get("/home/fr054721/hak/WebContent/res/response.json");
		Logger logger = (Logger) Tools.fromJSON(path.toFile(), new TypeReference<Logger>(){});

		System.out.println(Tools.toJSON(logger.getPositions()));
		
	}
	
	public static void f2() throws IOException {

		Path path = Paths.get("/home/fr054721/hak/resources.json");
		@SuppressWarnings("unchecked")
		Map<String, Resource> resources = (Map<String, Resource>) Tools.fromJSON(path.toFile(), new TypeReference<Map<String, Resource>>(){});
		Resource hak = null;
		
		for(Map.Entry<String, Resource> resource: resources.entrySet()) {
			if(resource.getValue().getService().equalsIgnoreCase("UDPServer")) {
				hak = resource.getValue();
			}
		}		
		
		System.out.println(Tools.toJSON(hak));
		
	}
	
	public static void f1() throws IOException {
		
		List<Logger> loggers = new ArrayList<Logger>();
		Logger logger = new Logger();
		logger.setFlag(900);
		logger.setTid("AC61D447924A");
		logger.setUid("MOBILI");
		loggers.add(logger);
		loggers.add(logger);
		loggers.add(logger);
		
		
		Credential cred = new Credential();
		cred.setIpAddress("193.251.53.223");
		cred.setLoggers(loggers);
		cred.setPacketSize(10000);
		cred.setPort(5100);
		
		Resource resource = new Resource();
		resource.setCredentials(Arrays.asList(cred));
		resource.setService("UDPServer");
		resource.setInstance("hak");
		
		System.out.println(Tools.toJSON(resource));
		
		
	}
	
	public static void f0() {
		DatagramSocket socket = null ;

	    try{
			//Convert the arguments first, to ensure that they are valid
			InetAddress host = InetAddress.getByName("193.251.53.223") ;
			int port = Integer.parseInt("5100") ;

			// Construct the socket
			socket = new DatagramSocket() ;

			// Construct the datagram packet
			// /opt/wks/eclipse/hak/hak/res/request.json

			Path path = Paths.get("/home/fr054721/hak/WebContent/res/request.json");
			byte[] data = Files.readAllBytes(path);

			DatagramPacket packet = new DatagramPacket(data, data.length, host, port) ;

			// Send it
			socket.send(packet) ;

			// Set a receive timeout, 2000 milliseconds
			socket.setSoTimeout(2000) ;

			// Prepare the packet for receive
			packet.setData(new byte[PACKETSIZE]) ;

			// Wait for a response from the server
			socket.receive(packet) ;

			// Print the response
			System.out.println(new String(packet.getData())) ;

	    }
		catch( Exception e ){
			System.out.println( e ) ;
		}
		finally{
			if( socket != null ) socket.close() ;
		}
	}
	
}
