import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by Viktor on 24.05.2016.
 */
public class Application {

    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            emf = Persistence.createEntityManagerFactory("Restaurant");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("Select action:");
                    System.out.println("1: Add a new dish on the menu");
                    System.out.println("2: Choose food for the price");
                    System.out.println("3: Show dishes with discount");
                    System.out.println("4: Select a set of dishes with a weight not exceeding 1 kg");
                    System.out.println("q. Exit");
                    System.out.print("-> ");

                    String choice = in.nextLine();

                    switch (choice) {
                        case "1":
                            addDish(in);
                            break;
                        case "2":
                            selectByPrice(in);
                            break;
                        case "3":
                            selectByDiscount();
                            break;
                        case "4":
                            selectRandom1kgSet();
                            break;
                        case "q":
                            return;
                    }
                }
            } finally {
                in.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;

        }
    }

    public static void addDish(Scanner in) {
        System.out.print("Enter the name of the dish: ");
        String name = in.nextLine();
        System.out.print("Enter the price of the dish: ");
        double price = in.nextDouble();
        System.out.print("Enter the weight of the dish: ");
        double weight = in.nextDouble();
        System.out.print("Enter the discount of the dish: ");
        double discount = in.nextDouble();

        em.getTransaction().begin();
        try {
            Menu menu = new Menu(name, weight, price, discount);
            em.persist(menu);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    public static void selectByPrice(Scanner in) {
        System.out.print("Enter a minimum price: ");
        double minPrice = in.nextDouble();
        System.out.print("Enter a maximum price: ");
        double maxPrice = in.nextDouble();

        Query query = em.createQuery("SELECT d FROM Menu d WHERE price BETWEEN " + minPrice + " and " + maxPrice, Menu.class);
        List<Menu> menuList = query.getResultList();

        for (Menu menu : menuList) {
            System.out.println(menu);
        }
    }

    public static void selectByDiscount() {
        Query query = em.createQuery("SELECT d FROM Menu d WHERE discount > 0 ", Menu.class);
        List<Menu> menuList = query.getResultList();

        for (Menu menu : menuList) {
            System.out.println(menu);
        }
    }

    public static void selectRandom1kgSet() {
        Query query = em.createQuery("SELECT d FROM Menu d", Menu.class);
        List<Menu> menuList = query.getResultList();

        List<Menu> selectedDish = new ArrayList<>();
        int totalWeight = 0;
        Collections.shuffle(menuList);

        for(int i = 0; i < menuList.size(); i++) {
            totalWeight += menuList.get(i).getWeight();
            if(totalWeight >= 1000) {totalWeight -= menuList.get(i).getWeight();break;}
            else {selectedDish.add(menuList.get(i)); continue;}
        }

        System.out.println("Set of dishes (total weight = " + totalWeight + "):");
        for (Menu menu : selectedDish) {
            System.out.println(menu);
        }
    }
}
