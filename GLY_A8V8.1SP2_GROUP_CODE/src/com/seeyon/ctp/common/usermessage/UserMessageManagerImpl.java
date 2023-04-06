package com.seeyon.ctp.common.usermessage;

import com.seeyon.ctp.common.SystemEnvironment;
import com.seeyon.ctp.common.constants.ApplicationCategoryEnum;
import com.seeyon.ctp.common.constants.Constants;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.log.CtpLogFactory;
import com.seeyon.ctp.common.po.usermessage.Ent_UserMessage;
import com.seeyon.ctp.common.po.usermessage.UserHistoryMessage;
import com.seeyon.ctp.common.usermessage.Constants.UserMessage_TYPE;
import com.seeyon.ctp.common.usermessage.api.UserMessageApi;
import com.seeyon.ctp.login.online.OnlineUser;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.Strings;
import com.seeyon.ctp.util.annotation.AjaxAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class UserMessageManagerImpl implements UserMessageManager {
    private static Log log = CtpLogFactory.getLog(UserMessageManagerImpl.class);
    private UserMessageApi userMessageApi;

    public UserMessageManagerImpl() {
    }

    public UserMessageApi getUserMessageApi() {
        return this.userMessageApi;
    }

    public void setUserMessageApi(UserMessageApi userMessageApi) {
        this.userMessageApi = userMessageApi;
    }

    public void initMessageState() {
        this.userMessageApi.initMessageState();
    }

    public void deleteReadMessage() {
        this.userMessageApi.deleteReadMessage();
    }

    public List<UserHistoryMessage> getAllSystemMessages(long userInternalID, String condition, String textfield, String textfield1) throws BusinessException {
        return this.userMessageApi.getAllSystemMessages(userInternalID, condition, textfield, textfield1);
    }

    public List<UserHistoryMessage> getAllSystemMessages(long userInternalID, String condition, String textfield, String textfield1, Boolean isPage) throws BusinessException {
        return this.userMessageApi.getAllSystemMessages(userInternalID, condition, textfield, textfield1, isPage);
    }

    public List<UserHistoryMessage> getAllSystemMessages(long userInternalID, String condition, String textfield, String textfield1, Boolean isPage, String readType) throws BusinessException {
        return this.userMessageApi.getAllSystemMessages(userInternalID, condition, textfield, textfield1, isPage, readType);
    }

    public String toJSLink(UserHistoryMessage message) {
        StringBuilder sb = new StringBuilder();
        sb.append(message.getLinkType());
        if (!Strings.isNotBlank(message.getLinkParam0())) {
            sb.append("|").append(message.getLinkParam0());
        }

        if (!Strings.isNotBlank(message.getLinkParam1())) {
            sb.append("|").append(message.getLinkParam1());
        }

        if (!Strings.isNotBlank(message.getLinkParam2())) {
            sb.append("|").append(message.getLinkParam2());
        }

        if (!Strings.isNotBlank(message.getLinkParam3())) {
            sb.append("|").append(message.getLinkParam3());
        }

        if (!Strings.isNotBlank(message.getLinkParam4())) {
            sb.append("|").append(message.getLinkParam4());
        }

        if (!Strings.isNotBlank(message.getLinkParam5())) {
            sb.append("|").append(message.getLinkParam5());
        }

        if (!Strings.isNotBlank(message.getLinkParam6())) {
            sb.append("|").append(message.getLinkParam6());
        }

        if (!Strings.isNotBlank(message.getLinkParam7())) {
            sb.append("|").append(message.getLinkParam7());
        }

        if (!Strings.isNotBlank(message.getLinkParam8())) {
            sb.append("|").append(message.getLinkParam8());
        }

        if (!Strings.isNotBlank(message.getLinkParam9())) {
            sb.append("|").append(message.getLinkParam9());
        }

        return sb.toString();
    }

    public void saveMessage(Ent_UserMessage msg) throws BusinessException {
        this.userMessageApi.saveMessage(msg);
    }

    public void sendSystemMessage(MessageContent content, ApplicationCategoryEnum messageCategroy, long senderId, MessageReceiver receiver, Object... messageFilterArgs) throws BusinessException {
        Collection<MessageReceiver> mreceivers = new ArrayList(1);
        mreceivers.add(receiver);
        this.sendSystemMessage(content, messageCategroy, senderId, (Collection)mreceivers, messageFilterArgs);
    }

    public void sendSystemMessage(MessageContent content, ApplicationCategoryEnum messageCategroy, long senderId, Collection<MessageReceiver> receivers, Object... messageFilterArgs) throws BusinessException {
        this.sendSystemMessage(content, messageCategroy.key(), senderId, receivers, messageFilterArgs);
    }

    public void sendSystemMessage(MessageContent content, int messageCategroy, long senderId, Date creationDate, Collection<MessageReceiver> receivers, Object... messageFilterArgs) throws BusinessException {
        if (receivers != null && !receivers.isEmpty()) {
            UserMessage userMessage = new UserMessage(senderId, messageCategroy, UserMessage_TYPE.SYSTEM, content, 0L, creationDate, receivers, messageFilterArgs);
            List<Object[]> keys = content.getKeys();
            for (int i = 0; i < keys.size(); i++) {
                String cancelMessage = keys.get(i)[0].toString();
                if(!cancelMessage.equals("news.cancel.publish") && !cancelMessage.equals("news.publishEdit") ){
                    this.sendMessage(userMessage);
                }
            }
        }
    }

    public void sendSystemMessage(MessageContent content, int messageCategroy, long senderId, Collection<MessageReceiver> receivers, Object... messageFilterArgs) throws BusinessException {
        this.sendSystemMessage(content, messageCategroy, senderId, new Date(), receivers, messageFilterArgs);
    }

    public void sendMessage(UserMessage userMessage) throws BusinessException {
        this.userMessageApi.sendMessage(userMessage);
    }

    public int getWaitingParseQLength() {
        return this.userMessageApi.getWaitingParseQLength();
    }

    public int getWaitingSaveQLength() {
        return this.userMessageApi.getWaitingSaveQLength();
    }

    public boolean isCachedUserMessage(long userInternalID) {
        return MessageState.getInstance().isUserCached(userInternalID);
    }

    public int getCachedUserCount() {
        return MessageState.getInstance().getCachedUserCount();
    }

    public void removeAllMessages(long userInternalID) throws BusinessException {
        this.userMessageApi.removeAllMessages(userInternalID);
    }

    public List<Ent_UserMessage> getUnresolvedMessagesForMB(long userInternalID) throws BusinessException {
        return this.userMessageApi.getUnresolvedMessagesForMB(userInternalID);
    }

    public void removeMessage(String startTime, String endTime) throws BusinessException {
        this.userMessageApi.removeMessage(startTime, endTime);
    }

    public void removeMessage(String condition, Long longfield) throws BusinessException {
        this.userMessageApi.removeMessage(condition, longfield);
    }

    public String getNewMessagesAndOnlineSize() {
        return this.userMessageApi.getNewMessagesAndOnlineSize();
    }

    public int getCommentZishu() {
        return this.userMessageApi.getCommentZishu();
    }

    public String getUserOnlineMessage() throws BusinessException {
        return this.userMessageApi.getUserOnlineMessage();
    }

    public Map<String, String> getMessageLinkTypes() {
        return this.userMessageApi.getMessageLinkTypes();
    }

    public void savePatchHistory(List<UserHistoryMessage> ms) {
        this.userMessageApi.savePatchHistory(ms);
    }

    @Transactional(
        propagation = Propagation.SUPPORTS,
        rollbackFor = {BusinessException.class}
    )
    public void updateSystemMessageStateByUserAndReference(long userInternalID, long referenceId) {
        this.userMessageApi.updateSystemMessageStateByUserAndReference(userInternalID, referenceId);
    }

    public void processPcAndMobileOnlineAtSameTime(Long userId, String pLang, String userAgentFrom, Map<Constants.login_sign, OnlineUser.LoginInfo> loginInfoMapper, String loginType) {
        this.userMessageApi.processPcAndMobileOnlineAtSameTime(userId, pLang, userAgentFrom, loginInfoMapper, loginType);
    }

    public UserHistoryMessage getUserHistoryMsgById(Long id) throws BusinessException {
        return this.userMessageApi.getUserHistoryMsgById(id);
    }

    public int getNotReadSystemMessageCount(long userInternalID) throws BusinessException {
        return this.userMessageApi.getNotReadSystemMessageCount(userInternalID);
    }

    public void deleteUserMessageForM3(List<Long> ids) throws BusinessException {
        this.userMessageApi.deleteUserMessageForM3(ids);
    }

    public void startAyncEngine() {
        if (SystemEnvironment.isDistributedMode() && !SystemEnvironment.isMessageService()) {
            log.warn("微服务模式下非消息服务，忽略该startAyncEngine方法调用");
        } else {
            this.userMessageApi.startAyncEngine();
        }
    }

    public void stopAyncEngine() {
        if (SystemEnvironment.isDistributedMode() && !SystemEnvironment.isMessageService()) {
            log.warn("微服务模式下非消息服务，忽略该stopAyncEngine方法调用");
        } else {
            this.userMessageApi.stopAyncEngine();
        }
    }

    public List<UserHistoryMessage> getSystemHistoryMessages(FlipInfo fi, Map<String, String> params) throws BusinessException {
        return this.userMessageApi.getSystemHistoryMessages(fi, params);
    }

    public void updateSystemMessageStateByUser(long userId) throws BusinessException {
        this.userMessageApi.updateSystemMessageStateByUser(userId);
    }

    public void updateSystemMessageCountByUser(long userId) throws BusinessException {
        this.userMessageApi.updateSystemMessageCountByUser(userId);
    }

    public void updateSystemMessageStateByCategory(long userId, int messageCategory) throws BusinessException {
        this.userMessageApi.updateSystemMessageStateByCategory(userId, messageCategory);
    }

    public void updateSystemMessageCountByCategory(long userId, int messageCategory) throws BusinessException {
        this.userMessageApi.updateSystemMessageCountByCategory(userId, messageCategory);
    }

    @AjaxAccess
    public void updateSystemMessageState(long id) throws BusinessException {
        this.userMessageApi.updateSystemMessageState(id);
    }

    public void deleteSystemMessageByUser(long userId) throws BusinessException {
        this.userMessageApi.deleteSystemMessageByUser(userId);
    }

    public void deleteSystemMessageByCategory(long userId, int messageCategory) throws BusinessException {
        this.userMessageApi.deleteSystemMessageByCategory(userId, messageCategory);
    }
}



