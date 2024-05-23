import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * Sanna Balouchi
 * 
 * 
 * 
 * Simulation of audio content in an online store
 * The songs, podcasts, audiobooks listed here can be "downloaded" to your libraryA Playlist contains an array list of AudioContent 
 * (i.e. Song, AudioBooks, Podcasts) from the library
 */

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		private Map<String, Integer> contentFinder = new HashMap<String, Integer>(); //created in makeStore()
		private Map<String, ArrayList<Integer>> artistFinder = new HashMap<String, ArrayList<Integer>>();
		private Map<String, ArrayList<Integer>> genreFinder = new HashMap<String, ArrayList<Integer>>();
		
		public AudioContentStore()
		{

			try {
				contents = makeStore(); //adds all content into the store, also creates general contents map
			} catch (IOException e){
				System.out.println(e.getMessage()); //if file does not exist, it stops the program
				System.exit(1);
			}
			
			genreFinder = makeGenreMap(); //creates the genre map
			artistFinder = makeArtistMap(); //creates the artist map

		}
		
		private ArrayList<AudioContent> makeStore() throws IOException { //makes the store
			File contentStore = new File("store.txt");  // creates file to be scanned
			Scanner store = new Scanner(contentStore); 
			contents = new ArrayList<AudioContent>();
			int index = 0;

			while (store.hasNextLine()){
				String type = store.nextLine();
				
				if (type.equals("SONG")){ //if type is song
					System.out.println("Loading SONG");
					String id = store.nextLine(); //grabs id
					String title = store.nextLine(); //grabs title
					int year = Integer.parseInt(store.nextLine()); //grabs year 
					int length = Integer.parseInt(store.nextLine()); //grabs length
					String artist = store.nextLine(); //grabs artist
					String composer = store.nextLine(); //grabs composer
					Song.Genre genre = Song.Genre.valueOf(store.nextLine()); //grabs genre
					int lines = Integer.parseInt(store.nextLine()); //grabs number of lines in the lyrics
					String lyrics = ""; 

					for (int i = 0; i < lines; i++){
						lyrics += store.nextLine() + "\n";	//grabs all the lyrics
					}
					lyrics = lyrics.trim();//so there isnt any extra lines at the beginning and end

					contentFinder.put(title, index); //adds content to the contents map
					Song newSong = new Song(title, year, id, type, lyrics, length, artist, composer, genre, lyrics); //creates the song
					contents.add(newSong); //adds song to store

				} else if (type.equals("AUDIOBOOK")){ //if type is audiobook
					System.out.println("Loading AUDIOBOOK");
					String id = store.nextLine(); //laods id
					String title = store.nextLine(); //loads title
					int year = Integer.parseInt(store.nextLine()); //loads year
					int length = Integer.parseInt(store.nextLine()); //loads length
					String author = store.nextLine(); //loads author
					String narrator = store.nextLine(); //loads narrator
					int chapters = Integer.parseInt(store.nextLine());//loads number of chapters

					ArrayList<String> chapterTitles = new ArrayList<>(); //chapter title arraylist

					for (int i = 0; i < chapters; i++){ //grabs all the chapter titles into an arrayList
						String chapTitle = store.nextLine();
						chapterTitles.add(chapTitle);
					}

					String file = "";
					ArrayList<String> chapterContent = new ArrayList<>();

					for (int i = 0; i < chapters; i++){ //grabs all the chapter contents into an assorted string
						int num = Integer.parseInt(store.nextLine()); //loads number of lines in chapter
						for (int j = 0; j < num; j++){
							file += store.nextLine() + "\n";
						}
						file = file.trim();
						chapterContent.add(file); //adds the chapter contents into an array list
					}

					contentFinder.put(title, index); //adds audiobook title to contents map
					//creates audiobook
					AudioBook newBook = new AudioBook(title, year, id, type, "", length, author, narrator, chapterTitles, chapterContent);
					contents.add(newBook);//adds audiobook to store
				}

				index++; // so index does not stay at 0
			} store.close(); //closes file

			return contents;

		}
		
		//Podcast Seasons
		
		private ArrayList<Season> makeSeasons()
		{
			ArrayList<Season> seasons = new ArrayList<Season>();
		  Season s1 = new Season();
		  s1.episodeTitles.add("Bay Blanket");
		  s1.episodeTitles.add("You Don't Want to Sleep Here");
		  s1.episodeTitles.add("The Gold Rush");
		  s1.episodeFiles.add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \r\n"
		  		+ "lip-syncing, but some people believe they were used to spread\r\n"
		  		+ "smallpox and decimate entire Indigenous communities. \r\n"
		  		+ "We dive into the history of The Hudson's Bay Company and unpack the\r\n"
		  		+ "very complicated story of the iconic striped blanket.");
		  s1.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeFiles.add("here is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeLengths.add(31);
		  s1.episodeLengths.add(32);
		  s1.episodeLengths.add(45);
		  seasons.add(s1);
		  Season s2 = new Season();
		  s2.episodeTitles.add("Toronto vs Everyone");
		  s2.episodeTitles.add("Water");
		  s2.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s2.episodeFiles.add("Can the foundation of Canada be traced back to Indigenous trade routes?\r\n"
		  		+ "In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\r\n"
		  		+ "and vampires, and discuss some big concerns currently facing Canada's water."); 
		  s2.episodeLengths.add(45);
		  s2.episodeLengths.add(50);
		 
		  seasons.add(s2);
		  return seasons;
		  
		}
		// CREATING MAPS
		private Map<String, ArrayList<Integer>> makeGenreMap(){//creates the genre map; key: genre, value: ArrayList of indices in contents

			Map<String, ArrayList<Integer>> genreMap = new HashMap<>();

			for (AudioContent i : contents){
				if (i.getType().equals("SONG")){

					Song other = (Song) i;
					String genre = other.getGenreName();
					String title = other.getTitle();
					int index = search(title);

					ArrayList<Integer> indices = new ArrayList<>();

					if (!genreMap.containsKey(genre)){
						indices.add(index);
						genreMap.put(genre, indices);

					} else {
						genreMap.get(genre).add(index);
					}
				}
			}

			return genreMap;
		}
		private Map<String, ArrayList<Integer>> makeArtistMap(){ //creates the artist map; key: artist/author, value: ArrayList of indices in contents
			Map<String, ArrayList<Integer>> artistMap = new HashMap<>();
			ArrayList<Integer> indices = new ArrayList<>();

			for (AudioContent i : contents){
				if (i.getType().equals("SONG")){
					Song other = (Song) i;
					String artist = other.getArtist();
					String title = other.getTitle();
					int index = search(title);

					if (!artistMap.containsKey(artist)){
						artistMap.put(artist, new ArrayList<Integer>());
					} 
					artistMap.get(artist).add(index);
					

				} else if (i.getType().equals("AUDIOBOOK")){
					AudioBook other = (AudioBook) i;
					String author = other.getAuthor();
					String title = other.getTitle();
					int index = search(title);

					if (!artistMap.containsKey(author)){
						artistMap.put(author, new ArrayList<Integer>());
					}
					artistMap.get(author).add(index);
				}
			}
			
			return artistMap;
		}

		//PUBLIC STORE METHODS
		public AudioContent getContent(int index) //returns index of get content
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		public void listAll() //lists all contents in store
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		public int search(String title){ //returns index of a title in contents
			if (contentFinder.containsKey(title)){
				int index = contentFinder.get(title);
			    return index;
			} else {
				throw new AudioContentNotFoundException(title);
			}
			
		}
		public ArrayList<Integer> searchA(String artist){ //returns an arraylist of all indexed content under an artist

			if (artistFinder.containsKey(artist)){
				return artistFinder.get(artist);
			} else {
				throw new AudioContentNotFoundException(artist);
			}

		}
		public ArrayList<Integer> searchG(String genre){ //returns an array list of all indexed content under a genre
			if (genreFinder.containsKey(genre)){
				return genreFinder.get(genre);
			} else {
				throw new AudioContentNotFoundException(genre);
			}
		}
		public void printContent(int index){ //prints content for one content
			System.out.print("" + (index+1) + ". ");
			contents.get(index).printInfo();
			System.out.println();
		}
}
