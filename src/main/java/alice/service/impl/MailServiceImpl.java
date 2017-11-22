package alice.service.impl;

import alice.body.SendRandomMail;
import alice.common.constants.Constants;
import alice.common.exception.AliceException;
import alice.dao.ICustomerInfoDao;
import alice.dao.ISendMailRecordDao;
import alice.dao.ISendSpecificMailRecordDao;
import alice.entity.CustomerInfo;
import alice.entity.SendMailRecord;
import alice.entity.SendSpecificMailRecord;
import alice.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qa.mail.MailInfo;
import qa.mail.MailUtil;
import qa.utils.DateFormat;
import qa.utils.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
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
        for(int i=0;i<emailAdress.length;i++){
            String mail = emailAdress[i]; //发送对象的邮箱
//            mail = "523830279@qq.com";
//            if (!mail.contains("@")){
//                mail += "@qq.com";
//            }
            info.setToAddress(mail);
            try {
                MailUtil.sendHtmlMail(info);
                // 发送成功，记录信息
                SendMailRecord sendMailRecord = new SendMailRecord();
                sendMailRecord.setEmail(mail);
                sendMailRecord.setCreateTime(DateFormat.getDate());
                iSendMailRecordDao.save(sendMailRecord);
                log.debug(mail + " 发送成功。");
                FileUtil.pushText("logs/"+logFileName,DateFormat.getDateToString() + " -----> " + mail+" 发送成功。\n");
            } catch (Exception e) {
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
}
