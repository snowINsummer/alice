package alice.service;

import alice.body.SendRandomMail;
import alice.common.exception.AliceException;

public interface MailService {
    void sendRandomEmail(SendRandomMail sendRandomMail) throws AliceException;

    void sendSpecificEmail() throws AliceException;

    void test() throws AliceException;
}
