package alice.common.operation;

public class DailyPageviewsAndVisitorAndSendMail {

    private String dateStr;
    private Integer dailyVisitor;
    private Integer dailyPageviews;
    private Integer sendMail;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Integer getDailyVisitor() {
        return dailyVisitor;
    }

    public void setDailyVisitor(Integer dailyVisitor) {
        this.dailyVisitor = dailyVisitor;
    }

    public Integer getDailyPageviews() {
        return dailyPageviews;
    }

    public void setDailyPageviews(Integer dailyPageviews) {
        this.dailyPageviews = dailyPageviews;
    }

    public Integer getSendMail() {
        return sendMail;
    }

    public void setSendMail(Integer sendMail) {
        this.sendMail = sendMail;
    }
}
