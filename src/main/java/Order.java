import java.time.LocalDateTime;
import java.util.List;

public class Order {

    public List<String> color;
    public String address;
    public String firstName;
    public String lastName;
    public String deliveryDate;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String comment;

    public Order(List<String> color) {
        this.color = color;
        this.address = "ул. Якиманка, 20";
        this.firstName = "Иван";
        this.lastName = "Иванов";
        this.deliveryDate = LocalDateTime.now().toString();
        this.metroStation = "4";
        this.phone = "12345678900";
        this.rentTime = 3;
        this.comment = "какой-то коммент";
    }
}
