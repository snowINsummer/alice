package alice.body;

public class SendRandomMail {

    /**
     * 号码长度
     */
    private int numberLength;

    /**
     * 发送次数
     */
    private int sendTime;

    /**
     * 用于测试
     */
    private String testAdress;

    public int getNumberLength() {
        return numberLength;
    }

    public void setNumberLength(int numberLength) {
        this.numberLength = numberLength;
    }

    public int getSendTime() {
        return sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public String getTestAdress() {
        return testAdress;
    }

    public void setTestAdress(String testAdress) {
        this.testAdress = testAdress;
    }
}
