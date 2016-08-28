package example.com.androidcontact;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;


@Table
public class AppContact extends SugarRecord implements Comparable<AppContact> {

    private Long id;
    private int rating;
    private String name;
    @Unique
    private String number;

    public String getNumber() {
        return number;
    }

    public AppContact() {
    }

    public String getName() {
        return name;
    }

    public AppContact(String name, String number, int rating) {
        this.name = name;
        this.number = number;
        this.rating = rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int compareTo(AppContact s) {
        return s.getRating() - this.rating;
    }
}
