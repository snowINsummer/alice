package alice.service.impl;

import alice.body.SendRandomMail;
import alice.common.constants.Constants;
import alice.common.exception.AliceException;
import alice.dao.*;
import alice.entity.*;
import alice.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qa.mail.MailInfo;
import qa.mail.MailUtil;
import qa.utils.DateFormat;
import qa.utils.FileUtil;
import qa.utils.JSONFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    ICustomerInfoDao iCustomerInfoDao;
    @Autowired
    ISendMailRecordDao iSendMailRecordDao;
    @Autowired
    ISendSpecificMailRecordDao iSendSpecificMailRecordDao;
    @Autowired
    IMailSenderInfoDao iMailSenderInfoDao;
    @Autowired
    IMailSmtpInfoDao iMailSmtpInfoDao;
    @Autowired
    IDailyMailSenderRecordDao iDailyMailSenderRecordDao;

    @Override
    public void sendRandomEmail(SendRandomMail sendRandomMail) throws AliceException {
        String logFileName = DateFormat.getDateString() + ".log";
        List<String> allAdress = new ArrayList<>();
        if (null != sendRandomMail.getTestAdress() && !sendRandomMail.getTestAdress().isEmpty()){
            allAdress.add(sendRandomMail.getTestAdress());
        }else {
            int numberLength = sendRandomMail.getNumberLength();
            int sendTime = sendRandomMail.getSendTime();
            for(int k=0;k<sendTime;k++){
                String number = MailUtil.getRandomNumber(numberLength,9,0);
                number += "@qq.com";
                allAdress.add(number);
            }
        }
        String[] emailAdress = new String[allAdress.size()];
        allAdress.toArray(emailAdress);
//        String str = StringUtils.join(allAdress,";");
        String title = "软装布艺 Casasgs原创设计师品牌";
        String nameDes = "Casasgs原创设计师品牌";
        String content;
        try {
            String classes = this.getClass().getClassLoader().getResource("").getPath();
//            content = FileUtil.getFileText("src/main/resources/Casasgs_2.html");
            content = FileUtil.getFileText(classes+"Casasgs_2.html");
        } catch (IOException e) {
            throw new AliceException(e);
        }
        // 构造MailInfo对象
        MailInfo mailInfo = mailConstructor(sendRandomMail);
        if (null == mailInfo) throw new AliceException("获取发送者邮箱相关信息失败。");
        mailInfo.setSubject(title);
        mailInfo.setContent(content);
        mailInfo.setFormNameDes(nameDes);
        for(int i=0;i<emailAdress.length;i++){
            // TODO 需要调试
            if (sendRandomMail.isNeedChangeSender()){
                mailInfo = mailConstructor(sendRandomMail);
                if (null == mailInfo) throw new AliceException("获取发送者邮箱相关信息失败。");
            }
            String mail = emailAdress[i]; //发送对象的邮箱
//            mail = "523830279@qq.com";
//            if (!mail.contains("@")){
//                mail += "@qq.com";
//            }
            mailInfo.setToAddress(mail);
            try {
                log.debug("Sending mail "+(i+1)+"th");
                MailUtil.sendHtmlMail(mailInfo);
                // 发送成功，记录信息
                SendMailRecord sendMailRecord = new SendMailRecord();
                sendMailRecord.setEmail(mail);
                sendMailRecord.setCreateTime(DateFormat.getDate());
                iSendMailRecordDao.save(sendMailRecord);
                log.debug(mail + " 发送成功。");
                FileUtil.pushText("logs/"+logFileName,DateFormat.getDateToString() + " -----> " + mail+" 发送成功。\n");
            } catch (Exception e) {
                if (e.toString().contains("Connection frequency limited")){
                    // 邮箱发送超过限制，把当天发送邮箱置为无效，跟换邮箱
                    makeSenderInvalid(mailInfo);
                    sendRandomMail.setNeedChangeSender(true);
                }
                FileUtil.pushText("logs/"+logFileName,DateFormat.getDateToString() + " -----> " + mail+" 发送失败！！！" + e.getMessage() + "\n");
                log.debug(mail+" 发送失败！！！" + e.getMessage());
                e.printStackTrace();
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    public void sendSpecificEmail() throws AliceException {
        String logFileName = DateFormat.getDateString() + ".log";
        List<CustomerInfo> customerInfoList = iCustomerInfoDao.findAll();

        //        String str = StringUtils.join(allAdress,";");
        String title = "软装布艺 Casasgs原创设计师品牌";
        String content = null;  // gb2312
        try {
            String classes = this.getClass().getClassLoader().getResource("").getPath();
//            content = FileUtil.getFileText("src/main/resources/Casasgs_2.html");
            content = FileUtil.getFileText(classes+"Casasgs_2.html");
        } catch (IOException e) {
            throw new AliceException(e);
        }
        String nameDes = "Casasgs原创设计师品牌";
        MailInfo info = new MailInfo();
        info.setSubject(title);
        info.setContent(content);
        info.setFormNameDes(nameDes);

        for (CustomerInfo customerInfo : customerInfoList){
            if (customerInfo.getIsActive() != 1){
                continue;
            }
            String mail = customerInfo.getEmail(); //发送对象的邮箱
            if (!mail.contains("@")){
                continue;
            }
            info.setToAddress(mail);

            SendSpecificMailRecord sendSpecificMailRecord = new SendSpecificMailRecord();
            sendSpecificMailRecord.setCustomerInfoId(customerInfo.getId());
            sendSpecificMailRecord.setSendTime(DateFormat.getDate());
            try {
                MailUtil.sendHtmlMail(info);
                // 发送成功，记录信息
                sendSpecificMailRecord.setStatus(Constants.SEND_SUCCESS_TYPE);
                log.debug(mail + " 发送成功。");
                FileUtil.pushText("logs/"+logFileName,DateFormat.getDateToString() + " -----> " + mail+" 发送成功。\n");
            } catch (Exception e) {
                // 发送失败，记录信息
                sendSpecificMailRecord.setStatus(Constants.SEND_FAILURE_TYPE);
                sendSpecificMailRecord.setSendInfo(e.getMessage());
                FileUtil.pushText("logs/"+logFileName,DateFormat.getDateToString() + " -----> " + mail+" 发送失败！！！" + e.getMessage() + "\n");
                log.debug(mail+" 发送失败！！！" + e.getMessage());
                e.printStackTrace();
            }finally {
                iSendSpecificMailRecordDao.save(sendSpecificMailRecord);
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void test() throws AliceException {
//        mailConstructor(sendRandomMail);
    }


    // TODO 调试
    private void makeSenderInvalid(MailInfo mailInfo) {
        String senderAdress = mailInfo.getFormName();
        MailSenderInfo mailSenderInfo = iMailSenderInfoDao.query(senderAdress);
        if (null != mailSenderInfo){
            String today = DateFormat.getDateString();
            iDailyMailSenderRecordDao.update(Constants.DB_DATA_INVALID,mailSenderInfo.getId(),today);
        }
    }

    private MailInfo mailConstructor(SendRandomMail sendRandomMail) throws AliceException {
        MailInfo mailInfo = new MailInfo();
        // 筛选发送者邮箱及相关服务信息
        List<MailSenderInfo> mailSenderInfoList = iMailSenderInfoDao.findAll();
        // 先根据发送者表查询当天记录
        for (MailSenderInfo mailSenderInfo : mailSenderInfoList){
            Integer mailSenderInfoId = mailSenderInfo.getId();
            String today = DateFormat.getDateString();
            DailyMailSenderRecord dailyMailSenderRecord = iDailyMailSenderRecordDao.query(mailSenderInfoId,today);
            if (null == dailyMailSenderRecord){
                // 当天没有记录，新增
                dailyMailSenderRecord = new DailyMailSenderRecord();
                dailyMailSenderRecord.setMailSenderInfoId(mailSenderInfoId);
                dailyMailSenderRecord.setDateString(today);
                dailyMailSenderRecord.setStatus(Constants.DB_DATA_ACTIVE);
                dailyMailSenderRecord.setCreateTime(DateFormat.getDate());
                DailyMailSenderRecord newDailyMailSenderRecord = iDailyMailSenderRecordDao.save(dailyMailSenderRecord);
                log.debug("MailInfo = "+ JSONFormat.getObjectToJson(newDailyMailSenderRecord));
            }else {
                // 判断status，为1有效，为0无效，换邮箱
                Integer status = dailyMailSenderRecord.getStatus();
                if (status == Constants.DB_DATA_INVALID){
                    log.debug("DailyMailSenderRecord = "+ JSONFormat.getObjectToJson(dailyMailSenderRecord));
                    continue;
                }
            }
            // 查询邮箱相关服务器信息
            Integer mailSmtpInfoId = mailSenderInfo.getMailSmtpInfoId();
            MailSmtpInfo mailSmtpInfo = iMailSmtpInfoDao.query(mailSmtpInfoId);
            if (null != mailSmtpInfo){
                mailInfo.setHost(mailSmtpInfo.getSmtpAddress());
                mailInfo.setPort(mailSmtpInfo.getSmtpPort());
                mailInfo.setFormName(mailSenderInfo.getMailAddress());
                mailInfo.setFormPassword(mailSenderInfo.getPassword());
                mailInfo.setReplayAddress(mailSenderInfo.getMailAddress());
                sendRandomMail.setNeedChangeSender(false);
                break;
            }else {
                log.debug("MailSmtpInfo = null");
            }
        }
        log.debug("MailInfo = "+ JSONFormat.getObjectToJson(mailInfo));
        return mailInfo;
    }


    public static void main(String[] args) {
        Date today = DateFormat.getDate();
        System.out.println(today);
    }

}
