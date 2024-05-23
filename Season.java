import java.util.ArrayList;

/*
 * Sanna Balouchi
 * 
 * 
 * 
 *  A Season is a class that is useful to Podcast. It is to hold and access episode titles, episode files, and episode lengths 
 */

public class Season {

    public ArrayList<String> episodeTitles;
	public ArrayList<String> episodeFiles;
    public ArrayList<Integer> episodeLengths;

    //constructs the arraylists that will hold the episode titles, files and lengths

    public Season()
	{
		episodeTitles = new ArrayList<String>();
        episodeFiles = new ArrayList<String>();
        episodeLengths  = new ArrayList<Integer>();
	}
    
}
