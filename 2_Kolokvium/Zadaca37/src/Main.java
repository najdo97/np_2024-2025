

/*
*Да се имплементира класа Post во која ќе се чуваат информациите за објава на една социјална мрежа. Во класата да се имплементираат следните методи:

Конструктор Post(String username, String postContent)
void addComment (String username, String commentId, String content, String replyToId) - метод за додавање на коментар со ИД commentId и содржина content од корисникот со корисничко име username. Коментарот може да биде директно на самата објава (replyToId=null во таа ситуација) или да биде reply на веќе постоечки коментар/reply. **
void likeComment (String commentId) - метод за лајкнување на коментар.
String toString() - toString репрезентација на една објава во форматот прикажан подолу. Коментарите се листаат во опаѓачки редослед според бројот на лајкови (во вкупниот број на лајкови се сметаат и лајковите на replies на коментарите, како и на replies na replies итн.)
** Решенијата кои ќе овозможат само коментари на објавата ќе бидат оценети со 50% од поените. Истото тоа е рефлектирано во тест примерите (50% од тест примерите се со коментари само на објавата, 50% се со вгнездени коментари и replies)
* */

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class PostTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String postAuthor = sc.nextLine();
        String postContent = sc.nextLine();

        Post p = new Post(postAuthor, postContent);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String testCase = parts[0];

            if (testCase.equals("addComment")) {
                String author = parts[1];
                String id = parts[2];
                String content = parts[3];
                String replyToId = null;
                if (parts.length == 5) {
                    replyToId = parts[4];
                }
                p.addComment(author, id, content, replyToId);
            } else if (testCase.equals("likes")) { //likes;1;2;3;4;1;1;1;1;1 example
                for (int i = 1; i < parts.length; i++) {
                    p.likeComment(parts[i]);
                }
            } else {
                System.out.println(p);
            }

        }
    }
}


//input:
//np.finki
//This is a test post for Advanced programming
//addComment;user1;0;To thousand number positive. Ago where feeling much difference become song.
//addComment;user2;1;Beautiful as rich view position less many. Around account approach city. Between east of tree ball be.
//addComment;user1;2;Television into daughter high have mouth house. Word another official cup thank.
//addComment;user2;3;Card land throughout friend recent bit land. Start but similar develop long his. Baby certainly street tend position piece.
//addComment;user1;4;Just off identify hotel usually growth. Her off scientist white well color. Increase leg degree attention keep.
//likes;1;1;1;2;2;2;2;3;2;3;3;4;4;0;1
//print

//output:
//Post: This is a test post for Advanced programming
//Written by: np.finki
//Comments:
//        Comment: Television into daughter high have mouth house. Word another official cup thank.
//        Written by: user1
//        Likes: 5
//        Comment: Beautiful as rich view position less many. Around account approach city. Between east of tree ball be.
//        Written by: user2
//        Likes: 4
//        Comment: Card land throughout friend recent bit land. Start but similar develop long his. Baby certainly street tend position piece.
//        Written by: user2
//        Likes: 3
//        Comment: Just off identify hotel usually growth. Her off scientist white well color. Increase leg degree attention keep.
//        Written by: user1
//        Likes: 2
//        Comment: To thousand number positive. Ago where feeling much difference become song.
//        Written by: user1
//        Likes: 1