import java.util.ArrayList;

/*
 * Sanna Balouchi
 * 501154179
 * 
 * 
 *  A Podcast is a type of AudioContent. A Podcast has extra fields such as host (person(s) talking on the podcast) and seasons the podcast has 
 *  season is an ArrayList<Season> that holds all the season titles, episodes titles and episode files
 *  also has variables that keep track of the current episode and current season
 */

public class Podcast extends AudioContent{

	public static final String TYPENAME = "PODCAST";
	
	private String host; private ArrayList<Season> season; private int currentEpisode; int currentSeason;
	
	
	//constructs the podcast with super and constructs the host + season arraylist
	public Podcast(String title, int year, String id, String type, String audioFile, int length, String host, ArrayList<Season> season)
	{
		super(title, year, id, type, audioFile, length);
        this.host = host;
        this.season = season;
		
	}
	
	//returns "PODCAST"
	public String getType()
	{
		return TYPENAME;
	}
	
	//prints the information of the podcast including name of hosts and the number of seasons
	public void printInfo()
	{
		super.printInfo();
		System.out.println("Host: " + host + "\nSeasons: " + season.size());
	}
	
	//sets the episode to be used in play()
	public void selectEpisode(int setEp){
        this.currentEpisode = setEp;
    }

	//sets the season to be used in play()
    public void selectSeason(int setSeason){
        this.currentSeason = setSeason;
    }
    
	//sets the audio file using a specific season from arraylist season and prints the titles + the episode file
	//then plays it to the user
    public void play()
	{
        Season copy = season.get(currentSeason); //gets the season information from arraylist season

        setAudioFile(copy.episodeTitles.get(currentEpisode) + ".\n"
            + copy.episodeFiles.get(currentEpisode));
		super.play();
	}

	//prints a list of episodes from a season
    public void printEpisodes()
	{
        Season copy = season.get(currentSeason);

        for (int i = 0; i < copy.episodeTitles.size(); i++){
			int index = i + 1;
			String x = copy.episodeTitles.get(i);
			System.out.println("Episode " + index + ". " + x);
			System.out.println();
		}
	}
	
	//returns the hosts on the podcast
	public String getHost()
	{
		return host;
	}

	//two podcasts are equal if the titles, id are equal and if the hosts + season arraylist are equal
	public boolean equals(Object other)
	{
		Podcast otherCon = (Podcast) other;
		return super.equals(otherCon) && host.equals(otherCon.host) && season.equals(otherCon.season);
	}
	
}
