package alice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MailSmtpInfo {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String smtpAddress;

    @Column(nullable = false)
    private String smtpPort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public void setSmtpAddress(String smtpAddress) {
        this.smtpAddress = smtpAddress;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }
}
