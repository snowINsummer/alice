package alice.controller;

import alice.body.SendRandomMail;
import alice.common.constants.Constants;
import alice.common.entity.ReqData;
import alice.common.entity.RspData;
import alice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import qa.utils.JSONFormat;

/**
 * Created by zhangli on 8/5/2017.
 */

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    /**
     * 随机发送邮件
     * @param reqData
     * @return
     */
    @RequestMapping(value = "/sendRandomEmail", method = RequestMethod.POST)
    public RspData sendRandomEmail(@RequestBody ReqData reqData){
        RspData rspData = new RspData();
        try {
            String json = JSONFormat.getObjectToJson(reqData.getData());
            SendRandomMail sendRandomMail = JSONFormat.fromJson(json, SendRandomMail.class);
            mailService.sendRandomEmail(sendRandomMail);
            rspData.setCode(Constants.CODE_SUCCESS);
            rspData.setMessage(Constants.SEND_SUCCESS);
//        }catch (AliceException e){
//            rspData.setCode(e.getCode());
//            rspData.setMessage(e.getMessage());
        }catch (Exception e){
            rspData.setMessage(e.getMessage());
        }
        return rspData;
    }

    /**
     * 根据客户表发送邮件
     * @return
     */
    @RequestMapping(value = "/sendSpecificEmail", method = RequestMethod.POST)
    public RspData sendSpecificEmail(){
        RspData rspData = new RspData();
        try {
            mailService.sendSpecificEmail();
            rspData.setCode(Constants.CODE_SUCCESS);
            rspData.setMessage(Constants.SEND_SUCCESS);
//        }catch (AliceException e){
//            rspData.setCode(e.getCode());
//            rspData.setMessage(e.getMessage());
        }catch (Exception e){
            rspData.setMessage(e.getMessage());
        }
        return rspData;
    }

}
