
import java.net.*;
import java.util.ArrayList;

public class ServeurSimple 
{
	public static ArrayList<GerantDeClient> lstGerantCli = new ArrayList<>();

	public ServeurSimple(int port) 
	{
		try 
		{
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Serveur démarré sur le port " + port + ", en attente de clients...");

			while (true) 
			{
				Socket s = serverSocket.accept();

				GerantDeClient gdc = new GerantDeClient(s);

				ServeurSimple.lstGerantCli.add(gdc);

				Thread tgdc = new Thread(gdc);
				tgdc.start();
			}

		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) 
	{
		new ServeurSimple(6000); 
	}
}