/*
* Да се имплементира класа за именик PhoneBook со следните методи:

void addContact(String name, String number) - додава нов контакт во именикот. Ако се обидеме да додадеме контакт со веќе постоечки број, треба да се фрли исклучок од класа DuplicateNumberException со порака Duplicate number: [number]. Комплексноста на овој метод не треба да надминува $O(log N)$ за $N$ контакти.
void contactsByNumber(String number) - ги печати сите контакти кои во бројот го содржат бројот пренесен како аргумент во методот (минимална должина на бројот [number] е 3). Комплексноста на овој метод не треба да надминува $O(log N)$ за $N$ контакти.
void contactsByName(String name) - ги печати сите контакти кои даденото име. Комплексноста на овој метод треба да биде $O(1)$.
Во двата методи контактите се печатат сортирани лексикографски според името, а оние со исто име потоа според бројот.
* */

import java.util.Scanner;



public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}

// Вашиот код овде

//Input:
//100
//Joseph:076419335
//Grace:071100596
//Daniel:070722123
//Anthony:070644513
//John:070498738
//Hannah:07063258
//Anthony:077819546
//Matthew:075493469
//Sophia:075131379
//Chloe:077707915
//Elizabeth:071763836
//Ava:071552885
//Madison:077172002
//Ethan:071395488
//Chloe:070229419
//Samantha:07520725
//Daniel:071907118
//Abigail:076360771
//David:070540071
//Matthew:078313851
//Mia:071132061
//Jacob:078151372
//Madison:078139784
//David:070776005
//Natalie:078406279
//Natalie:070766008
//James:070489255
//Anthony:070300785
//Anthony:071573836
//Ava:076114610
//Daniel:071916884
//Nathan:077662244
//Ryan:078190890
//John:077664248
//James:070957979
//Nicholas:078752598
//Andrew:076626965
//Daniel:071342655
//Brianna:071215193
//David:071249755
//Andrew:07536276
//Ava:076710189
//Daniel:075369220
//Andrew:077989868
//Emily:071455248
//Ashley:071380478
//Nicholas:077356364
//Hannah:077334152
//Grace:071881593
//Emma:077563062
//Nicholas:071415843
//Joseph:077191965
//Ava:071919844
//Matthew:075433499
//William:077229828
//Andrew:070358282
//Anthony:077381439
//Olivia:071538194
//Christopher:075579670
//Daniel:077631059
//Elizabeth:078493212
//Anthony:07543526
//Joshua:07149634
//Mia:070504798
//James:071609153
//William:078484106
//Mia:076833318
//Tyler:077672223
//Joseph:0765826
//Tyler:0784753
//Daniel:075364408
//Jacob:075672081
//Samantha:078808979
//Elizabeth:070588340
//Jacob:078114686
//Jacob:071593656
//Jonathan:077540283
//Michael:077500506
//Ethan:070215469
//William:075445896
//Joseph:070818626
//Ryan:077754827
//Alexander:076579509
//Madison:071184318
//Madison:075801283
//Grace:075386503
//Alexander:070503555
//Ava:077926314
//David:070600549
//Madison:075596220
//Sophia:075433746
//Sophia:071450599
//Abigail:070502823
//Matthew:076902452
//Madison:075580671
//Hannah:077859313
//Mia:076294366
//William:078451083
//Christopher:077104541
//Emily:071865027
//NAME:Joseph
//NUM:0771
//NAME:Matthew
//NUM:10774
//NAME:Anthony
//NUM:658
//NAME:Andrew
//NUM:52741
//NAME:Mia
//NUM:12367
//NAME:Joseph
//NUM:9530
//NAME:Andrew
//NUM:4391
//NAME:Joseph
//NUM:1331
//NAME:Grace
//NUM:16884
//NAME:Nathan
//NUM:25120
//NAME:Olivia
//NUM:35397
//NAME:Grace
//NUM:90987
//NAME:Natalie
//NUM:215
//NAME:Samantha
//NUM:77229
//NAME:Andrew
//NUM:755
//NAME:Nathan
//NUM:5596
//NAME:Emma

//output:
//NAME:Joseph
//Joseph 070818626
//Joseph 076419335
//Joseph 0765826
//Joseph 077191965
//NUM:0771
//Abigail 076360771
//Christopher 077104541
//Joseph 077191965
//Madison 077172002
//NAME:Matthew
//Matthew 075433499
//Matthew 075493469
//Matthew 076902452
//Matthew 078313851
//NUM:10774
//NOT FOUND
//NAME:Anthony
//Anthony 070300785
//Anthony 070644513
//Anthony 071573836
//Anthony 07543526
//Anthony 077381439
//Anthony 077819546
//NUM:658
//Joseph 0765826
//NAME:Andrew
//Andrew 070358282
//Andrew 07536276
//Andrew 076626965
//Andrew 077989868
//NUM:52741
//NOT FOUND
//NAME:Mia
//Mia 070504798
//Mia 071132061
//Mia 076294366
//Mia 076833318
//NUM:12367
//NOT FOUND
//NAME:Joseph
//Joseph 070818626
//Joseph 076419335
//Joseph 0765826
//Joseph 077191965
//NUM:9530
//NOT FOUND
//NAME:Andrew
//Andrew 070358282
//Andrew 07536276
//Andrew 076626965
//Andrew 077989868
//NUM:4391
//NOT FOUND
//NAME:Joseph
//Joseph 070818626
//Joseph 076419335
//Joseph 0765826
//Joseph 077191965
//NUM:1331
//NOT FOUND
//NAME:Grace
//Grace 071100596
//Grace 071881593
//Grace 075386503
//NUM:16884
//Daniel 071916884
//NAME:Nathan
//Nathan 077662244
//NUM:25120
//NOT FOUND
//NAME:Olivia
//Olivia 071538194
//NUM:35397
//NOT FOUND
//NAME:Grace
//Grace 071100596
//Grace 071881593
//Grace 075386503
//NUM:90987
//NOT FOUND
//NAME:Natalie
//Natalie 070766008
//Natalie 078406279
//NUM:215
//Brianna 071215193
//Ethan 070215469
//NAME:Samantha
//Samantha 07520725
//Samantha 078808979
//NUM:77229
//William 077229828
//NAME:Andrew
//Andrew 070358282
//Andrew 07536276
//Andrew 076626965
//Andrew 077989868
//NUM:755
//Christopher 075579670
//David 071249755
//Madison 075580671
//Madison 075596220
//NAME:Nathan
//Nathan 077662244
//NUM:5596
//Madison 075596220
//NAME:Emma
//Emma 077563062

