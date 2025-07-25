/*
* Да се имплементира класа Names со следните методи:

public void addName(String name) - додавање на име
public void printN(int n) - ги печати сите имиња кои се појавуваат n или повеќе пати, подредени лексикографски според името, на крајот на зборот во загради се печати бројот на појавувања, а по него на крај бројот на уникатни букви во зборот (не се прави разлика на големи и мали)
public String findName(int len, int x) - го враќа името кое со наоѓа на позиција x (почнува од 0) во листата од уникатни имиња подредени лексикографски, по бришење на сите имиња со големина поголема или еднаква на len. Позицијата x може да биде поголема од бројот на останати имиња, во тој случај се продожува со броење од почетокот на листата. Пример за листа со 3 имиња A, B, C, ако x = 7, се добива B. A0, B1, C2, A3, B4, C5, A6, B7.
* */

import java.util.*;

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde

//input:
//440
//Alyssa
//Brianna
//Sarah
//Emma
//William
//Nathan
//Mia
//Michael
//John
//Ryan
//James
//David
//Mia
//Hannah
//Chloe
//Matthew
//Nicholas
//Jonathan
//Mia
//Anthony
//Jacob
//Emma
//Ethan
//Andrew
//Joshua
//Grace
//Emma
//Emma
//Elizabeth
//Daniel
//Tyler
//Natalie
//Sophia
//Tyler
//Sarah
//Ashley
//Isabella
//William
//Nicholas
//Joseph
//Alyssa
//Ashley
//Samantha
//Alexander
//Jonathan
//Nathan
//Sarah
//Sarah
//Nicholas
//Joseph
//Joseph
//James
//Brianna
//Mia
//Mia
//Andrew
//Emily
//Matthew
//Sarah
//Sarah
//Tyler
//Tyler
//Tyler
//Samantha
//Samantha
//Sarah
//Olivia
//Tyler
//Olivia
//Natalie
//Joseph
//Matthew
//Christopher
//David
//Michael
//Alexis
//Sophia
//Mia
//Samantha
//James
//David
//Mia
//Tyler
//Grace
//Natalie
//Brianna
//James
//Joshua
//David
//Chloe
//Matthew
//Nathan
//Christopher
//Ethan
//Hannah
//Sophia
//John
//Isabella
//Chloe
//Ethan
//Christopher
//Samantha
//Jonathan
//Matthew
//James
//Andrew
//Tyler
//Isabella
//Matthew
//Tyler
//John
//Elizabeth
//Michael
//Mia
//Emily
//Tyler
//Anthony
//Hannah
//James
//Grace
//Ryan
//Sarah
//Natalie
//Elizabeth
//Christopher
//Joshua
//Brianna
//Elizabeth
//Christopher
//Madison
//Hannah
//Grace
//Ava
//Emily
//John
//Matthew
//Mia
//Grace
//Olivia
//Alexis
//Nicholas
//Joseph
//Alexis
//Abigail
//Daniel
//Nicholas
//Nathan
//Abigail
//Jonathan
//Samantha
//Ryan
//Ava
//Nathan
//Alexis
//Samantha
//James
//David
//Alexander
//Olivia
//Chloe
//Sarah
//Grace
//Alexander
//Matthew
//Christopher
//Michael
//Sophia
//Emma
//Joseph
//Brianna
//Andrew
//Joseph
//Emma
//Sophia
//Alyssa
//Alexander
//Madison
//Samantha
//Abigail
//Matthew
//Joshua
//Emma
//Emily
//Nicholas
//Nathan
//Michael
//Sophia
//Mia
//Sophia
//Sarah
//Emily
//Brianna
//Michael
//Hannah
//Elizabeth
//Nicholas
//Jonathan
//Chloe
//James
//Ethan
//David
//Samantha
//Grace
//Natalie
//Sophia
//Jacob
//Chloe
//William
//Ethan
//William
//Abigail
//Michael
//William
//Christopher
//Daniel
//Alyssa
//Jonathan
//William
//Emma
//John
//Alyssa
//Natalie
//Mia
//Chloe
//Brianna
//Tyler
//Natalie
//Jonathan
//Sarah
//William
//Ryan
//Mia
//Abigail
//Andrew
//Emma
//Andrew
//William
//Olivia
//Hannah
//Alexander
//Joshua
//Joseph
//Nicholas
//William
//Nathan
//Matthew
//Christopher
//Isabella
//Sophia
//Hannah
//Elizabeth
//Hannah
//Ryan
//Tyler
//Jonathan
//Christopher
//Jacob
//Alexis
//Chloe
//Elizabeth
//Grace
//Natalie
//Madison
//Jacob
//Elizabeth
//Mia
//Brianna
//Nicholas
//James
//Michael
//Emily
//Tyler
//John
//Christopher
//Alexander
//Ava
//Tyler
//Tyler
//Samantha
//Olivia
//Hannah
//Christopher
//Anthony
//John
//Emma
//Joseph
//Ryan
//Grace
//Grace
//Nicholas
//John
//Tyler
//Ryan
//Andrew
//Anthony
//Jonathan
//Jacob
//Nicholas
//Michael
//Emma
//Samantha
//Nathan
//Chloe
//Natalie
//Emily
//Tyler
//Daniel
//Alyssa
//Samantha
//Ashley
//Tyler
//Joshua
//Elizabeth
//Andrew
//James
//Matthew
//Michael
//Ethan
//Ryan
//Alexander
//Joshua
//Emily
//Emma
//Jonathan
//Abigail
//Emily
//Alyssa
//Nicholas
//John
//Samantha
//William
//Jonathan
//David
//Chloe
//Daniel
//Ethan
//Abigail
//Abigail
//Joseph
//Emma
//Anthony
//Natalie
//Michael
//Nathan
//James
//Jacob
//Daniel
//Christopher
//Joseph
//Elizabeth
//Christopher
//Christopher
//Emily
//Joseph
//Samantha
//Jacob
//Samantha
//Alexander
//Alyssa
//Christopher
//Matthew
//Alyssa
//Ava
//Tyler
//Nicholas
//Matthew
//Ashley
//Elizabeth
//Matthew
//Grace
//Andrew
//Nathan
//Andrew
//Natalie
//William
//Ava
//Jonathan
//Emily
//Alexis
//Natalie
//Emma
//Samantha
//Brianna
//Daniel
//Joshua
//Daniel
//Michael
//Jonathan
//Joshua
//Michael
//Natalie
//Alyssa
//Joseph
//Chloe
//Grace
//Jonathan
//Joshua
//William
//Joseph
//James
//Alexander
//Sophia
//Daniel
//Jacob
//Michael
//Anthony
//Daniel
//Alexis
//Emma
//Mia
//Nathan
//Abigail
//Michael
//Tyler
//Natalie
//Daniel
//William
//Daniel
//Brianna
//Andrew
//Ava
//Michael
//Grace
//John
//Olivia
//Madison
//Emma
//Christopher
//Tyler
//Anthony
//Jonathan
//Hannah
//Brianna
//Elizabeth
//Abigail
//William
//William
//Michael
//Chloe
//Brianna
//6
//7
//144

//output

//===== PRINT NAMES APPEARING AT LEAST 6 TIMES =====
//Abigail (10) 5
//Alexander (9) 7
//Alexis (7) 6
//Alyssa (10) 4
//Andrew (11) 6
//Anthony (7) 6
//Ava (6) 2
//Brianna (12) 5
//Chloe (12) 5
//Christopher (16) 9
//Daniel (12) 6
//David (7) 4
//Elizabeth (12) 8
//Emily (11) 5
//Emma (16) 3
//Ethan (7) 5
//Grace (13) 5
//Hannah (10) 3
//Jacob (8) 5
//James (12) 5
//John (10) 4
//Jonathan (15) 6
//Joseph (14) 6
//Joshua (10) 6
//Matthew (14) 6
//Mia (14) 3
//Michael (17) 7
//Natalie (14) 6
//Nathan (11) 4
//Nicholas (13) 8
//Olivia (7) 5
//Ryan (8) 4
//Samantha (16) 6
//Sarah (11) 4
//Sophia (10) 6
//Tyler (21) 5
//William (15) 5
//===== FIND NAME =====
//Nathan