
import java.io.*;
import java.net.*;


/**
 * Gère la communication avec un client spécifique.
 * Responsable de la réception des messages et de leur diffusion aux autres.
 */
public class GerantDeClient implements Runnable 
{
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String nomClient;


	/**
     * Initialise les flux d'entrée et de sortie pour le socket client.
     */
	public GerantDeClient(Socket socket) 
	{
		this.socket = socket;
		try 
		{
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
		} catch (Exception e) 
		{
			System.out.println("Problème lors de l'initialisation du client");
		}
	}
	
	/**
     * Parcourt la liste des clients pour envoyer le message à tous sauf l'émetteur.
     */
	public void diffuser(String message) 
	{
		for (int i = 0; i < ServeurSimple.lstGerantCli.size(); i++) 
		{
			GerantDeClient gdc = ServeurSimple.lstGerantCli.get(i);

			if (gdc != this) 
				gdc.getOut().println(message);
			
		}
	}


	/**
     * Demande le pseudo à l'utilisateur jusqu'à obtenir une réponse valide (non vide).
     */
	public String demanderNom() 
	{
		String nom = "";
		this.out.println("Entrez votre nom :"); 

		try 
		{
			do 
			{
				nom = this.in.readLine();
			} while (nom != null && nom.trim().equals(""));
		} catch (Exception e) 
		{
			System.out.println(e);
		}
		return nom;
	}


	/**
     * Boucle principale : identifie le client, notifie son arrivée et relaie ses messages.
     * Gère la déconnexion proprement en cas d'erreur ou de départ.
     */
	@Override
	public void run() 
	{
		try 
		{
			this.nomClient = this.demanderNom();
			
			if(this.nomClient == null)
			{ 
				this.socket.close();
				return;
			}

			System.out.println("Un nouveau client est connecté : " + this.nomClient);
			this.out.println("Bienvenue " + this.nomClient);
			
			this.diffuser("SERVEUR: " + this.nomClient + " a rejoint le chat.\n" +
						  "SERVEUR:  Il y a maintenant " + ServeurSimple.lstGerantCli.size() + " clients sur le serveur.");

			String messageRecu;

			while ((messageRecu = this.in.readLine()) != null) 
			{
				System.out.println("[" + this.nomClient + "]: " + messageRecu);	
				String message = this.nomClient + ": " + messageRecu;
				this.diffuser(message);
			}

		} catch (IOException e) {
			System.out.println("Erreur de connexion avec " + this.nomClient);
		} finally 
		{
			fermerConnexion();
		}
	}


	/**
     * Retire le client de la liste active et ferme le socket.
     * Informe les autres participants du départ.
     */
	private void fermerConnexion() 
	{
		try 
		{
			if (this.nomClient != null) 
			{
				ServeurSimple.lstGerantCli.remove(this);
				String messageDepart = "SERVEUR: " + this.nomClient + " a quitté le chat. Clients restants: " + ServeurSimple.lstGerantCli.size();
				System.out.println(messageDepart);
				this.diffuser(messageDepart);
			}
			if (this.socket != null && !this.socket.isClosed()) 
			{
				this.socket.close();
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public PrintWriter getOut() {return this.out;}
}