# TextbasedMusicApp
CPS209 assignment where we created a text-based music app.

Explores exceptions, try&catch, scanners, comparables/comparators,
interfaces, objects, maps, files, and basic java concepts.

Each .java file is there to have a look at the code. It is not to run.

To run the file, download ASSIGNMENT 2 CPS209.zip
then compile and run the MyAudioUI file.

To understand store.txt here is a break down of each line:
line 1: audio type (song, audiobook)
line 2: the ID
line 3: title
line 4: year published
line 5: time length of file (fake)
line 6: artist/author
line 7: composer/narrator

then depending on if it is a song or audio book the next lines differ

SONG:
line 8: genre
line 9: # of lines for lyrics to load
line 10 - n: lyrics
AUDIOBOOK:
line 8: # of chapters
line 9 - n: chapter names
line n: # of lines in that chapter to load for one chapter
line n - m: sentences in chapter
