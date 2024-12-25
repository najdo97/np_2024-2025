import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class MP3Player {
    private List<Song> songs;
    private Song current_song;
    private boolean isPaused;
    private boolean isStopped;

    public MP3Player() {
        songs = new ArrayList<>();
        current_song = null;
        isPaused = true;
        this.isStopped = false;
    }

    public MP3Player(List<Song> songs) {
        this.songs = songs;
        this.current_song = this.songs.get(0);
        this.isPaused = true;
        this.isStopped = false;
    }

    public void pressPlay() {
        if (!isPaused) {
            System.out.println("Song is already playing");
            return;
        }
        this.isPaused = false;
        this.isStopped=false;
        int song_number = 0;
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(current_song)) {
                song_number = i;
            }
        }
        System.out.println("Song " + song_number + " is playing");
    }

    public void pressStop() {

        if (this.isPaused && !this.isStopped) {
            current_song = this.songs.get(0);
            System.out.println("Songs are stopped");
            this.isStopped=true;
            return;
        }else if(this.isPaused && this.isStopped){
            current_song = this.songs.get(0);
            System.out.println("Songs are already stopped");
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

        System.out.println("Forward...");
        isPaused = true; //not sure about this

        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(current_song)) {
                if (i == songs.size()-1) {
                    this.current_song = songs.get(0);
                    break;
                } else {
                    this.current_song = songs.get(i + 1);
                    break;
                }
            }
        }
    }

    public void pressREW() {

        System.out.println("Reward...");
        isPaused = true; //not sure about this
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(current_song)) {
                if (i == 0) {
                    current_song = songs.get(songs.size() - 1);
                } else {
                    current_song = songs.get(i - 1);
                }
                break;
            }
        }
    }

    @Override
    public String toString() {
        int song_number = 0;
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(current_song)) {
                song_number = i;
            }
        }

        return "MP3Player{" +
                "currentSong = " + song_number +
                ", songList = " + songs +
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


public class Main {
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