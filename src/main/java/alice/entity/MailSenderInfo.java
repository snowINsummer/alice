package alice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class MailSenderInfo {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Integer mailSmtpInfoId;

    @Column(nullable = false)
    private String mailAddress;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMailSmtpInfoId() {
        return mailSmtpInfoId;
    }

    public void setMailSmtpInfoId(Integer mailSmtpInfoId) {
        this.mailSmtpInfoId = mailSmtpInfoId;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
