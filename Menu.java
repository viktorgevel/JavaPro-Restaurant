import javax.persistence.*;

/**
 * Created by Viktor on 23.05.2016.
 */
@Entity
@Table(name="Menu")
public class Menu {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Price", nullable = true)
    private double price;

    @Column(name = "Weight", nullable = false)
    private double weight;

    @Column(name = "Discount", nullable = true)
    private double discount;

    public Menu() {}

    public Menu(String name, double weight, double price, double discount) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "MenuItem number " + id +
                " [name = " + "\"" + name +
                "\", weight = " + weight +
                ", price = " + price +
                ", discount = " + discount + "]";
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
