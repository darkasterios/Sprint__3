public class CourierWithoutPassword {
    public final String login;

    public CourierWithoutPassword(String login) {
        this.login = login;
    }

    public static CourierWithoutPassword getLoginCourier(Courier courier){
        return new CourierWithoutPassword(courier.login);
    }

}
