import java.util.ArrayList;

/*
 * Sanna Balouchi
 * 
 * 
 * A Playlist contains an array list of AudioContent (i.e. Song, AudioBooks, Podcasts) from the library
 */
public class Playlist
{
	private String title;
	private ArrayList<AudioContent> contents; // songs, books, or podcasts or even mixture
	
	public Playlist(String title)
	{
		this.title = title;
		contents = new ArrayList<AudioContent>();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void addContent(AudioContent content)
	{
		contents.add(content);
	}
	
	public ArrayList<AudioContent> getContent()
	{
		return contents;
	}

	//returns the size of the playlist
	public int getContentSize()
	{
		return contents.size();
	}

	//returns an audiocontent at a given position
	//used to compare a song with content in playlist in order to remove the same song
	public AudioContent getConPl(int index)
	{
		return contents.get(index);
	}

	public void setContent(ArrayList<AudioContent> contents)
	{
		this.contents = contents;
	}
	
	/*
	 * Print the information of each audio content object (song, audiobook, podcast)
	 * in the contents array list. Print the index of the audio content object first
	 * followed by ". " then make use of the printInfo() method of each audio content object
	 * Make sure the index starts at 1
	 */
	public void printContents()
	{
		for (int i = 0; i < contents.size(); i++){
			System.out.print((i+1) + ". ");
			contents.get(i).printInfo();
			System.out.println("");
		}	
	}

	// Play all the AudioContent in the contents list
	public void playAll()
	{
		for (int i = 0; i < contents.size(); i++){
			contents.get(i).play();
			System.out.println("");
		}
	}
	
	// Play the specific AudioContent from the contents array list.
	// First make sure the index is in the correct range. 
	public void play(int index)
	{
		if (index < 0 || index > contents.size()){
			System.out.println("Content not found");
		} else {
			contents.get(index).play();
		}
	}
	
	public boolean contains(int index)
	{
		return index >= 0 && index < contents.size();
	}
	
	// Two Playlists are equal if their titles are equal
	public boolean equals(Object other)
	{
		Playlist otherPlaylist = (Playlist) other;
		return title.equals(otherPlaylist.getTitle());
	}
	
	// Given an index of an audio content object in contents array list,
	// remove the audio content object from the array list
	// Hint: use the contains() method above to check if the index is valid
	public void deleteContent(int index)
	{
		if (!contains(index)) return;
		contents.remove(index);
	}
	
	
}
