package arhangel.dim.entity;

/**
 *
 */
public enum Category {

    FOOD("FOOD"),
    ATM("ATM"),
    MEDICINE("MEDICINE"),
    PHONE_INTERNET("PHONE_INTERNET"),
    OTHER("OTHER");

    private String category;

    Category(String category) {
        this.category = category;
    }

}
