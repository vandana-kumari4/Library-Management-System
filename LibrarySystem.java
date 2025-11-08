import java.io.*;
import java.util.*;

class Book implements Serializable {
    int id;
    String title;
    boolean available = true;

    Book(int id, String title) {
        this.id = id;
        this.title = title;
    }
}

public class LibrarySystem {
    static final String FILE_NAME = "books.dat";
    static ArrayList<Book> books = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        load();
        while (true) {
            System.out.println("\n1. Add Book  2. Borrow  3. Return  4. View  5. Save  6. Exit");
            System.out.print("Choose: ");
            int c = sc.nextInt(); 
            sc.nextLine();

            switch (c) {
                case 1 -> add();
                case 2 -> borrow();
                case 3 -> ret();
                case 4 -> view();
                case 5 -> save();
                case 6 -> { save(); return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void add() {
        System.out.print("ID: "); 
        int id = sc.nextInt(); 
        sc.nextLine();

        System.out.print("Title: "); 
        String t = sc.nextLine();

        books.add(new Book(id, t));
        System.out.println("Book added!");
    }

    static void borrow() {
        System.out.print("Enter Book ID: "); 
        int id = sc.nextInt();

        for (Book b : books) {
            if (b.id == id && b.available) {
                b.available = false;
                System.out.println("Borrowed successfully!");
                return;
            }
        }

        System.out.println("Book not available!");
    }

    static void ret() {
        System.out.print("Book ID: "); 
        int id = sc.nextInt();

        for (Book b : books) {
            if (b.id == id) {
                b.available = true;
                System.out.println("Returned!");
                return;
            }
        }

        System.out.println("Book not found!");
    }

    static void view() {
        if (books.isEmpty()) {
            System.out.println("No books available!");
            return;
        }

        for (Book b : books)
            System.out.println(b.id + " | " + b.title + " | " + (b.available ? "Available" : "Not Available"));
    }

    static void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(books);
            System.out.println("Saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            books = (ArrayList<Book>) in.readObject();
        } catch (Exception ignored) {}
    }
}
