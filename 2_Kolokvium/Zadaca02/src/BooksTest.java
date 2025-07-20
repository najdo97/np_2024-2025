/*
 *
 * Да се напише класа за книга Book во која се чува:
 *
 * наслов
 * категорија
 * цена.
 * Да се имплементира конструктор со следните аргументи Book(String title, String category, float price).
 *
 * Потоа да се напише класа BookCollection во која се чува колекција од книги. Во оваа класа треба да се имплментираат следните методи:
 *
 * public void addBook(Book book) - додавање книга во колекцијата
 * public void printByCategory(String category) - ги печати сите книги од проследената категорија (се споредува стрингот без разлика на мали и големи букви), сортирани според насловот на книгата (ако насловот е ист, се сортираат според цената).
 * public List<Book> getCheapestN(int n) - враќа листа на најевтините N книги (ако има помалку од N книги во колекцијата, ги враќа сите).
 *
 */


import java.util.*;
import java.util.stream.Collectors;


class Book {
    private String title;
    private String category;
    private float price;

    public Book() {
    }

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return  this.title + " (" + this.category + ") " + String.format("%.2f", this.price);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

class BookCollection {
    private Collection<Book> books;

    public BookCollection() {
        this.books = new ArrayList<>();
    }

    public BookCollection(Collection<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void printByCategory(String category) {
        List<Book> pom = books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .sorted(
                        Comparator
                                .comparing(Book::getTitle)
                                .thenComparing(Book::getPrice)
                )
                .collect(Collectors.toCollection(ArrayList::new));

        pom.forEach(System.out::println);

    }

    public List<Book> getCheapestN(int n) {
        return books.stream()
                .sorted(
                        Comparator
                                .comparing(Book::getPrice)
                                .thenComparing(Book::getTitle)
                )
                .limit(n)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}

public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

// Вашиот код овде