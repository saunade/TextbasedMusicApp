import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
 * Sanna Balouchi
 * 
 * 
 * 
 * Simulation of a Simple Text-based Music App (like Apple Music) 
 */

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		
			AudioContentStore store = new AudioContentStore();

		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");
		

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int from = 0;
				int to = 0;
				
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt())
				{
					from = scanner.nextInt();
				}

				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt())
				{
					to = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				for (int i = from; i <= to; i ++){
					AudioContent content = store.getContent(i);
					try{
						mylibrary.download(content);
					} catch (ContentDownloadedException e){
						System.out.println(e.getMessage());
					}
				}
									
			}

			else if (action.equalsIgnoreCase("DOWNLOADA")) 
			{
				String artist = "";
				ArrayList<Integer> index = new ArrayList<>();

				System.out.print("Artist Name: ");
				if (scanner.hasNext()){ 
					artist = scanner.nextLine(); 
				}

				try {
					index = store.searchA(artist);
				} catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}

				for (int i = 0; i < index.size(); i ++){
					int x = index.get(i);
					AudioContent content = store.getContent(x+1);
					try{
						mylibrary.download(content);
					} catch (ContentDownloadedException e){
						System.out.println(e.getMessage());
					}
				}
			}

			else if (action.equalsIgnoreCase("DOWNLOADG")) 
			{
				String genre = "";
				ArrayList<Integer> index = new ArrayList<>();

				System.out.print("Genre: ");
				if (scanner.hasNext()){ 
					genre = scanner.next(); 
					scanner.nextLine();
				}

				try {
					index = store.searchG(genre);
				} catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}

				for (int i = 0; i < index.size(); i++){
					int x = index.get(i);
					AudioContent content = store.getContent(x+1);
					try {
						mylibrary.download(content);
					} catch (ContentDownloadedException e){
						System.out.println(e.getMessage());
					}
				}
			}
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				// Print error message if the song doesn't exist in the library
				int index = 0;
				
				System.out.print("Song Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				try{
					mylibrary.playSong(index-1);
				} catch (NotInLibraryException e) {
					System.out.println(e.getMessage());
				}
			}
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
			// Print error message if the book doesn't exist in the library
				int index = 0;
				
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				try {
					mylibrary.printAudioBookTOC(index-1);
				} catch (NotInLibraryException e) {
					System.out.println(e.getMessage());
				}
			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				int index = 0;
				int chapter = 0;
				
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt()){ index = scanner.nextInt(); }	

				System.out.print("Chapter: ");
				if (scanner.hasNextInt())
				{
					chapter = scanner.nextInt();
					scanner.nextLine();
				}

				try {
					mylibrary.playAudioBook(index-1, chapter-1);
				} catch (NotInLibraryException e) {
					System.out.println(e.getMessage());
				}
			}
			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				int index = 0;
				int season = 0;
				
				System.out.print("Podcast Number: "); //collects podcast number in library
				if (scanner.hasNextInt()){ index = scanner.nextInt(); }
				
				System.out.print("Season: "); //collects season number
				if (scanner.hasNextInt())
				{
					season = scanner.nextInt();
					scanner.nextLine();
				}
				
				try {
					mylibrary.printPodcastEpisodes(index-1, season-1);
				} catch (NotInLibraryException e) {
					System.out.println(e.getMessage());

				}
			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				int index = 0;
				int season = 0;
				int episode = 0;
				
				System.out.print("Podcast Number: ");
				if (scanner.hasNextInt()){ index = scanner.nextInt(); }
				
				System.out.print("Season: ");
				if (scanner.hasNextInt()){ season = scanner.nextInt(); }

				System.out.print("Episode: ");
				if (scanner.hasNextInt())
				{
					episode = scanner.nextInt();
					scanner.nextLine();
				}

				try {
					mylibrary.playPodcast(index-1, season-1, episode-1);
				} catch (NotInLibraryException e) {
					System.out.println(e.getMessage());
				}
			}
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				String title = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				try { 
					mylibrary.playPlaylist(title);
				} catch (NotInLibraryException e) {
					System.out.println(e.getMessage());

				}
			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				String title = "";
				int index = 0;
				
				System.out.print("Playlist Title: ");
				if (scanner.hasNext()){ title = scanner.next(); }
				
				System.out.print("Content Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}

				try {
					mylibrary.playPlaylist(title, index-1);
				} catch (NotInLibraryException e){
					System.out.println(e.getMessage());

				}
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				int index = 0;
				
				System.out.print("Library Song #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}

				try {
					mylibrary.deleteSong(index-1);
				} catch (NotInLibraryException e) {
					System.out.println(e.getMessage());
				}
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				String title = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next();
					scanner.nextLine();
				}

				try {
					mylibrary.makePlaylist(title);
				} catch (AlreadyExistsException e){
					System.out.println(e.getMessage());
				}
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				String title = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next();
					scanner.nextLine();
				}

				try {
					mylibrary.printPlaylist(title);
				} catch(NotInLibraryException e){
					System.out.println(e.getMessage());
				}
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				String title = "";
				String type = "";
				int index = 0;

				System.out.print("Playlist Title: ");
				if (scanner.hasNext()){ title = scanner.next(); }
					
				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
				if (scanner.hasNext()){ type = scanner.next(); }

				System.out.print("Library Content #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}

				try {
					mylibrary.addContentToPlaylist(type, index-1, title);
				} catch(NotInLibraryException e) {
					System.out.println(e.getMessage());

				}
			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				String title = "";
				int index = 0;

				System.out.print("Playlist Title: ");
				if (scanner.hasNext()){ title = scanner.next(); }
					
				System.out.print("Playlist Content #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}

				try {
					mylibrary.delContentFromPlaylist(index-1, title);
				} catch (NotInLibraryException e) {
					System.out.println(e.getMessage());
				}
			}
			
			else if (action.equalsIgnoreCase("SEARCH")) 
			{
				String title = "";
				int index = -1;

				System.out.print("Title: ");
				if (scanner.hasNext()){ 
					title = scanner.nextLine(); 
				}

				try {
					index = store.search(title);
				} catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				} finally {
					if (index != -1){
						store.printContent(index);
					}
				}
				
			}

			else if (action.equalsIgnoreCase("SEARCHG")) 
			{
				String title = "";
				ArrayList<Integer> index = new ArrayList<>();

				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				if (scanner.hasNext()){ 
					title = scanner.next(); 
					scanner.nextLine();
				}

				try {
					index = store.searchG(title);
				} catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				} finally {
					if (!index.isEmpty()){
						for (int i : index){
							store.printContent(i);
						}
					}
				}
			}

			else if (action.equalsIgnoreCase("SEARCHA")) //doesnt work
			{
				String artist = "";
				ArrayList<Integer> index = new ArrayList<>();

				System.out.print("Arist: ");
				if (scanner.hasNext()){ 
					artist = scanner.nextLine(); 
				}

				try {
					index = store.searchA(artist);
				} catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				} finally {
					if (!index.isEmpty()){
						for (int i : index){
							store.printContent(i);
						}
					}
				}
			}

			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}

			System.out.print("\n>");
		}
	}
}
