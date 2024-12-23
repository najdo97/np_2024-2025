import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class MP3Player {
    private List<Song> songs;
    private Song current_song;
    private boolean isPaused;

    public MP3Player() {
        songs = new ArrayList<>();
        current_song = null;
        isPaused = false;
    }

    public MP3Player(List<Song> songs) {
        this.songs = songs;
        this.current_song = songs.get(0);
        this.isPaused = false;
    }

    public void pressPlay() {
        if (isPaused == false) {
            System.out.println("Song is already playing");
            return;
        }
        this.isPaused = false;
        int song_number = 0;
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(current_song)) {
                song_number = i;
            }
        }
        System.out.println("Song " + song_number + " is playing");
    }

    public void pressStop() {

        if (this.isPaused) {
            current_song = this.songs.get(0);
            this.isPaused = false;
            return;
        }
        this.isPaused = true;
        int song_number = 0;
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(current_song)) {
                song_number = i;
            }
        }
        System.out.println("Song " + song_number + " is paused");
    }

    public void printCurrentSong() {
        System.out.println(this.current_song);
    }

    public void pressFWD() {
        //mozno e da treba da se pauzira tuaka pesnata

        isPaused = true; //not sure about this

        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(current_song)) {
                if (i + 1 == songs.size()) {
                    current_song = songs.get(0);
                } else {
                    current_song = songs.get(i + 1);
                }
            }
        }

    }

    public void pressREW() {

        isPaused = true; //not sure about this
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(current_song)) {
                if (i == 0) {
                    current_song = songs.get(songs.size() - 1);
                } else {
                    current_song = songs.get(i - 1);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "MP3Player{" +
                "currentSong =" + current_song +
                "songList =" + songs +
                '}';
    }
}

class Song {

    private String title;
    private String artist;

    public Song() {
    }

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" + "title=" + title + ", artist=" + artist + '}';
    }
}


public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}

//Vasiot kod ovde