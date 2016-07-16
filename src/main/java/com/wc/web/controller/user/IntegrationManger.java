package com.wc.web.controller.user;

import com.wc.base.service.SettingService;
import com.wc.user.bean.IntegrationLog;
import com.wc.user.service.IntegrationLogService;
import com.wc.user.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 积分管理
 * Created by ruby on 2016-04-01.
 */
@Component
public class IntegrationManger {

    public static final String VERIFY_HOUSE_HOLD = "通过业主审核";

    @Autowired
    private IntegrationLogService integrationLogService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private SettingService settingService;

    /**
     * 注册
     *
     * @param userId
     */
    public void verify(long userId) {
        int integration = settingService.getInt("verifyHouseHold", 0);
        IntegrationLog log = new IntegrationLog();
        log.setTitle(VERIFY_HOUSE_HOLD);
        log.setCreateTime(new Date());
        log.setIntegration(integration);
        log.setType(IntegrationLog.VERIFY);
        log.setUserId(userId);
        integrationLogService.add(log);
        walletService.addIntegration(userId, integration);
    }


}
