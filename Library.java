import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * Sanna Balouchi
 * 
 * 
 * 
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
  	private ArrayList<Podcast> 	podcasts;


	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
		podcasts = new ArrayList<Podcast>();
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{
		switch(content.getType()){
			case "SONG": 
				Song songContent = (Song) content; 
				if (!songs.contains(songContent)){
					songs.add(songContent);
					System.out.println("SONG " + content.getTitle() + " Added to Library");
					break;
				} else {
					throw new ContentDownloadedException("Song " + content.getTitle());
				}
				
			case "AUDIOBOOK": 
				AudioBook audioContent = (AudioBook) content; 
				if (!audiobooks.contains(audioContent)){
					audiobooks.add(audioContent); 
					System.out.println("AUDIOBOOK " + content.getTitle() + " Added to Library");
					break;
				} else {
					throw new ContentDownloadedException("AudioBook " + content.getTitle());
				}

			case "PODCAST": 
				Podcast podContent = (Podcast) content; 
				if (!podcasts.contains(podContent)){
					podcasts.add(podContent);
					System.out.println("PODCAST " + content.getTitle() + " Added to Library");
					break;
				} else {
					throw new ContentDownloadedException("Podcast " + content.getTitle());
				}
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		for (int i = 0; i < podcasts.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			podcasts.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". " + playlists.get(i).getTitle());
			System.out.println();	
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> artists = new ArrayList<>();

		for (int i = 0; i < songs.size(); i++){
			Song x = songs.get(i);
			if (!artists.contains(x.getArtist())){
				artists.add(x.getArtist());
			}
		}

		for (int i = 0; i < artists.size(); i++)
		{
			int index = i + 1;
			System.out.print(index + ". ");
			System.out.println(artists.get(i));
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{
		if (index >= 0 && index <= songs.size()){ // check if valid index

			AudioContent songCopy = (AudioContent) songs.get(index);
			
			for (Playlist p : playlists){ // goes through each playlist
				for (int i =0; i < p.getContentSize(); i++){ // goes through each content in playlist
					if (songCopy.equals(p.getConPl(i))){ //if content in playlist equals the song, then delete
						p.deleteContent(i);
					}
				}
			}

			songs.remove(index);

		} else {
			throw new NotInLibraryException();
		}
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b)
		{
			if (a.getYear() < b.getYear()) return -1;
			if (a.getYear() > b.getYear()) return  1;
			return 0;
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator());
	 
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b)
		{
			if (a.getLength() < b.getLength()) return -1;
			if (a.getLength() > b.getLength()) return  1;
			return 0;
		}
	}

	// Sort songs by title 
	public void sortSongsByName() 
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 0 || index > songs.size()) //if invalid index
		{
			throw new NotInLibraryException("Song");
		} else {
			songs.get(index).play(); //gets song from downloaded songs and plays
		}
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public void playPodcast(int index, int season, int episode)
	{
		if (index < 0 || index > podcasts.size())
		{
			throw new NotInLibraryException("Podcast");
		} else {
			podcasts.get(index).selectSeason(season); //sets the season so it is not default
			podcasts.get(index).selectEpisode(episode); //sets the episode so it is not default
			podcasts.get(index).play(); //plays the specific episode
		}
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public void printPodcastEpisodes(int index, int season)
	{
		if(index >= 0 && index <= podcasts.size()){ //if valid index
			podcasts.get(index).selectSeason(season); //selects the season so it is not a former season chosen
			podcasts.get(index).printEpisodes(); //prints episodes in that season
		} else {
			throw new NotInLibraryException("Podcast");
			
		}
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		if (index < 0 || index >= audiobooks.size()) //if invalid index
		{
			throw new NotInLibraryException("AudioBook");
		} else if (audiobooks.get(index).getNumberOfChapters() < chapter || chapter < 0){ //if invalid chapter
			throw new NotInLibraryException("Chapter");
		}
		audiobooks.get(index).selectChapter(chapter); //selects the chapter to play
		audiobooks.get(index).play();
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		if(index >= 0 && index <= audiobooks.size()){ //if valid index
			audiobooks.get(index).printTOC(); //prints table of contents
		} else {
			throw new NotInLibraryException("AudioBook");
		}
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		Playlist newPlaylist = new Playlist(title);
		if (!playlists.contains(newPlaylist)){ //checks if the same playlist object is created (same if titles are equal)
			playlists.add(newPlaylist);
		} else {
			throw new AlreadyExistsException(title);
		}
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		Playlist playlistTitle = new Playlist(title); //creates a playlist object to compare with playlists in arraylist

		if (playlists.contains(playlistTitle)){ //checks if title is valid

			int x = playlists.indexOf(playlistTitle);
			playlists.get(x).printContents();	

		} else {
			throw new NotInLibraryException("Playlist");
		}
		
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		Playlist copyPlaylist = new Playlist(playlistTitle); //creates a playlist object of same name to easily find playlist already created
		int x = playlists.indexOf(copyPlaylist); //gets position of the playlist we want

		if (x!=-1){ //if playlist exists
			playlists.get(x).playAll(); 
		} else {
			throw new NotInLibraryException("Playlist");
		}
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		Playlist copyPlaylist = new Playlist(playlistTitle); 
		int x = playlists.indexOf(copyPlaylist); //finds position of playlist

		if (x!=-1){ //if playlist exists
			System.out.println(playlistTitle); //prints title
			playlists.get(x).play(indexInPL); //plays specific position
		} else {
			throw new NotInLibraryException("Playlist");
		}
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		
		Playlist titlePlaylist = new Playlist(playlistTitle); //creates playlist to find position
		int x = playlists.indexOf(titlePlaylist); //sets position to x 

		if (x != -1){
			switch(type.toUpperCase()){
				case "SONG": //if type is song
					if (index < songs.size() && index >= 0){ //if index is valid
						playlists.get(x).addContent(songs.get(index)); //gets the playlist and adds song chosen from library
						break;
					} else {
						throw new NotInLibraryException("Song");
					}

				case "AUDIOBOOK": //if type is audiobook
					if(index < audiobooks.size() && index >= 0){ //if index is valid
						playlists.get(x).addContent(audiobooks.get(index)); //gets the playlist and adds audiobook chosen from library
						break;
					} else { 
						throw new NotInLibraryException("AudioBook");
					}
				case "PODCAST": //if type is podcast
					if (index < podcasts.size() && index >= 0){ //if index is valid
						playlists.get(x).addContent(podcasts.get(index)); //gets the playlist and adds podcast chosen from library
						break;
					} else { 
						throw new NotInLibraryException("Podcast");
					}
				default: throw new NotInLibraryException("Type"); //if type was not written correctly
			}
		} else {
			throw new NotInLibraryException("Playlist");
		}
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		Playlist titlePlaylist = new Playlist(title);
		int x = playlists.indexOf(titlePlaylist);

		if (x != -1){ //if playlist exists
			playlists.get(x).deleteContent(index); //deletes content in index position
		} else {
		throw new NotInLibraryException("Playlist");
		}
	}
	
}





//****************EXCEPTIONS****************\\

class AudioContentNotFoundException extends RuntimeException{ //if cannot find audiocontent
    
    public AudioContentNotFoundException() {
		super("No matches");
	}
    public AudioContentNotFoundException(String type){ 
        super("No matches for " + type);
    }

}

class ContentDownloadedException extends RuntimeException{ //if audiocontent is already downloaded
    
    public ContentDownloadedException() {
		super("already downloaded");
	}
    public ContentDownloadedException(String type){ 
        super(type + " already downloaded");
    }

}

class NotInLibraryException extends RuntimeException{ //if audiocontent is not able to be found
    
    public NotInLibraryException() {
		super("Song not in Library");
	}
    public NotInLibraryException(String type){ 
        super(type + " Not Found");
    }

}

class AlreadyExistsException extends RuntimeException{ //if playlist already exists
    
    public AlreadyExistsException() {
		super("Already Exists");
	}
    public AlreadyExistsException(String type){ 
        super("Playlist " + type + " Already Exists");
    }

}
