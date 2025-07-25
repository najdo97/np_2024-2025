/*
* Да се имплементира класа FootballTable за обработка од податоците за повеќе фудбласки натпревари од една лига и прикажување на табелата на освоени поени според резултатите од натпреварите. Во класата да се имплементираат:

public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) - метод за додавање податоци за одигран натпревар помеѓу тимот со име homeTeam (домашен тим) и тимот со име awayTeam (гостински тим), при што homeGoals претставува бројот на постигнати голови од домашниот тим, а awayGoals бројот на постигнати голови од гостинскиот тим.
public void printTable() - метод за печатење на табелата според одиграните (внесените) натпревари. Во табелата се прикажуваат редниот број на тимот во табелата, името (со 15 места порамнето во лево), бројот на одиграни натпревари, бројот на победи, бројот на нерешени натпревари, бројот на освоени поени (сите броеви се печатат со 5 места порамнети во десно). Бројот на освоени поени се пресметува како број_на_победи x 3 + број_на_нерешени x 1. Тимовите се подредени според бројот на освоени поени во опаѓачки редослед, ако имаат ист број на освоени поени според гол разликата (разлика од постигнатите голови и примените голови) во опаѓачки редослед, а ако имаат иста гол разлика, според името.
* */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Partial exam II 2016/2017
 */
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here



//Bournemouth;Man Utd;1;3
//Burnley;Swansea;0;1
//Chelsea;West Ham;3;0
//Crystal Palace;West Brom;4;3
//Everton;Tottenham;0;4
//Hull;Leicester;5;2
//Man City;Sunderland;3;0
//Middlesbrough;Stoke;2;0
//Southampton;Watford;1;0
//Leicester;Arsenal;4;3
//Liverpool;Burnley;2;1
//Man Utd;Southampton;1;5
//Stoke;Man City;4;1
//Sunderland;Middlesbrough;1;4
//Swansea;Hull;0;2
//Tottenham;Crystal Palace;3;0
//Watford;Chelsea;3;4
//West Brom;Everton;5;0
//West Ham;Bournemouth;2;5
//Chelsea;Burnley;3;1
//Crystal Palace;Bournemouth;3;3
//Everton;Stoke;4;0
//Hull;Man Utd;3;3
//Leicester;Swansea;3;3
//Man City;West Ham;0;3
//Southampton;Sunderland;1;5
//Tottenham;Liverpool;4;5
//Watford;Arsenal;2;0
//West Brom;Middlesbrough;4;1
//Arsenal;Southampton;3;2
//Bournemouth;West Brom;3;5
//Burnley;Hull;0;5
//Liverpool;Leicester;3;1
//Man Utd;Man City;2;1
//Middlesbrough;Crystal Palace;3;1
//Stoke;Tottenham;1;3
//Sunderland;Everton;1;5
//Swansea;Chelsea;4;5
//West Ham;Watford;2;2
//Chelsea;Liverpool;1;2
//Crystal Palace;Stoke;4;3
//Everton;Middlesbrough;2;1
//Hull;Arsenal;2;0
//Leicester;Burnley;3;0
//Man City;Bournemouth;4;2
//Southampton;Swansea;4;3
//Tottenham;Sunderland;0;0
//Watford;Man Utd;3;3
//West Brom;West Ham;1;3
//Arsenal;Chelsea;0;0
//Bournemouth;Everton;2;0
//Burnley;Watford;3;0
//Liverpool;Hull;3;0
//Man Utd;Leicester;0;5
//Middlesbrough;Tottenham;4;0
//Stoke;West Brom;5;2
//Sunderland;Crystal Palace;2;2
//Swansea;Man City;3;4
//West Ham;Southampton;1;5
//Burnley;Arsenal;2;2
//Everton;Crystal Palace;0;1
//Hull;Chelsea;3;0
//Leicester;Southampton;1;0
//Man Utd;Stoke;3;1
//Sunderland;West Brom;0;1
//Swansea;Liverpool;5;0
//Tottenham;Man City;2;5
//Watford;Bournemouth;1;0
//West Ham;Middlesbrough;4;0
//Arsenal;Swansea;1;2
//Bournemouth;Hull;2;2
//Chelsea;Leicester;3;3
//Crystal Palace;West Ham;3;5
//Liverpool;Man Utd;3;1
//Man City;Everton;1;5
//Middlesbrough;Watford;0;0
//Southampton;Burnley;5;0
//Stoke;Sunderland;0;2
//West Brom;Tottenham;3;2
//Arsenal;Middlesbrough;4;1
//Bournemouth;Tottenham;4;1
//Burnley;Everton;3;1
//Chelsea;Man Utd;4;2
//Hull;Stoke;4;1
//Leicester;Crystal Palace;5;0
//Liverpool;West Brom;4;2
//Man City;Southampton;0;2
//Swansea;Watford;3;2
//West Ham;Sunderland;3;4
//Crystal Palace;Liverpool;4;5
//Everton;West Ham;4;4
//Man Utd;Burnley;0;2
//Middlesbrough;Bournemouth;2;0
//Southampton;Chelsea;5;4
//Stoke;Swansea;2;1
//Sunderland;Arsenal;3;3
//Tottenham;Leicester;2;5
//Watford;Hull;5;2


//=== TABLE ===
//Team                   P    W    D    L  PTS
// 1. Liverpool          9    8    0    1   24
// 2. Southampton       10    7    0    3   21
// 3. Leicester         10    6    2    2   20
// 4. Hull              10    6    2    2   20
// 5. Chelsea           10    5    2    3   17
// 6. Middlesbrough     10    5    1    4   16
// 7. West Brom          9    5    0    4   15
// 8. West Ham          10    4    2    4   14
// 9. Swansea           10    4    1    5   13
//10. Everton           10    4    1    5   13
//11. Watford           10    3    3    4   12
//12. Man City           9    4    0    5   12
//13. Sunderland        10    3    3    4   12
//14. Bournemouth       10    3    2    5   11
//15. Crystal Palace    10    3    2    5   11
//16. Man Utd           10    3    2    5   11
//17. Tottenham         10    3    1    6   10
//18. Burnley           10    3    1    6   10
//19. Arsenal            9    2    3    4    9
//20. Stoke             10    3    0    7    9