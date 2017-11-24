package alice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PageViewStatistics {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private Integer dailyPageviews;

    @Column(nullable = false)
    private Integer dailyVisitor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDailyPageviews() {
        return dailyPageviews;
    }

    public void setDailyPageviews(Integer dailyPageviews) {
        this.dailyPageviews = dailyPageviews;
    }

    public Integer getDailyVisitor() {
        return dailyVisitor;
    }

    public void setDailyVisitor(Integer dailyVisitor) {
        this.dailyVisitor = dailyVisitor;
    }
}
