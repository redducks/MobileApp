package com.pls.sendmail.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.pls.sendmail.business.impl.MailerTaskManager;
import com.pls.sendmail.co.MailCriteria;
import com.pls.sendmail.co.MailerAddress;
import com.pls.sendmail.exceptions.MailerException;
import com.pls.sendmail.vo.SentMessageNotificationVO;

/**
 * Delegate for the Mailer Session Beans
 * 
 */
public class Mailer {

	private final static Logger LOG = Logger.getLogger(Mailer.class.getName());

	private static final Mailer instance = new Mailer();

	private Mailer() {
	}

	public static Mailer instance() {
		return instance;
	}

	/**
	 * Delegate for the session bean. Prepares a mailer task for each message,
	 * then registers the task with the TaskManager that will send the message.
	 * 
	 * The message most likely has not been sent by the time this method ends.
	 * If this method ends without exception, the message has been given to the
	 * task manager and most likely will succeed in being sent.
	 * 
	 * @param messageObject
	 * @return the message that will be sent by the mailer task manager
	 * @throws MailerException
	 */
	public SentMessageNotificationVO sendMail(MailCriteria messageObject)
			throws MailerException {

		try {

			messageObject = validateCriteria(messageObject);

			MailerTask task = MailerTaskFactory
					.getTaskForCriteria(messageObject);

			// this call converts the addresses for use by the mail server
			task.prepareRecipients();

			// this call allows any transformation or other message specific
			// pre-processing to occur
			// before invoking the task manager
			SentMessageNotificationVO sentMessage = task.prepareContent();

			MailerTaskManager.addTask(task);

			return sentMessage;

		} catch (MailerException e) {
			LOG.error("Unable to create and schedule a task for Mail Message "
					+ messageObject.getClass().getName(), e);
			throw new MailerException(
					"Error in the Mailer, message was not sent! ", e);
		}

	}

	public List<SentMessageNotificationVO> sendMail(
			ArrayList<MailCriteria> messageList) throws MailerException {

		List<SentMessageNotificationVO> list = new ArrayList<SentMessageNotificationVO>();
		try {
			if (messageList != null && messageList.size() > 0) {
				for (MailCriteria messageObject : messageList) {
					try {
						list.add(sendMail(messageObject));
					} catch (MailerException e) {
						// issue sending one of the emails, it has already been
						// logged, just continue
					}
				}
			}

			return list;

		} catch (MailerException e) {
			LOG.error("Unable to create and schedule a task for Mail Message",
					e);
			throw new MailerException(
					"Error in the Mailer, message was not sent! ", e);
		}

	}

	/**
	 * encapsulates validation of Criteria data
	 * 
	 * fills in the default sender if no sender is provided
	 * 
	 * @param messageObject
	 * @return the validated object
	 */
	protected MailCriteria validateCriteria(MailCriteria messageObject) {

		if (messageObject == null) {
			throw new MailerException("Criteria object cannot be null");
		}

//		if (messageObject.getSender() == null
//				|| messageObject.getSender().getAddress() == null
//				|| "".equals(messageObject.getSender().getAddress().trim())) {
//
//			UserContext uc = UserContext.getUserContext();
//			Long orgId = uc.getOrgID();
//			if (OrgType.CARRIER.equals(uc.getOrgType())) {
//				orgId = SpecificOrganizations.PLS;
//			}
//			MailerAddress defaultSender = this.getDefaultSender(orgId);
//			if (defaultSender != null) {
//				messageObject.setSender(defaultSender);
//			} else {
//				throw new MailerException(
//						"Message sender not specified and no default sender is available. ");
//			}
//		}

		return messageObject;
	}

	/**
	 * Call back method to allow the TaskManager thread to notify when it
	 * encounters an exception.
	 */
	public void handleTaskManagerException(Throwable t) {
		// TODO determine what to do with these notifications

		// The MailerTaskManager thread used to swallow these exceptions
		// maybe something better can be done from here
		LOG.warn(
				"Received exception from the MailerTaskManager: "
						+ t.getMessage(), t);

	}

	/**
	 * TODO move this type of logic to a Mailer work flow in the future.
	 * 
	 * Checks the Organization Parameters for a default sender and returns the
	 * sender ready for use.
	 * 
	 * @param orgId
	 *            for the message in question
	 * @return InternetAddress of the Org's default sender from Org Params
	 * @throws MailerException
	 */
	public MailerAddress getDefaultSender(Long orgId) throws MailerException {

		MailerAddress sender = new MailerAddress();

//		OrganizationParameterValue nameParam = getOrganizationParameter(orgId,
//				OrganizationParameter.CUSTOMERSERVICE_EMAIL_NAME);
//		OrganizationParameterValue addressParam = getOrganizationParameter(
//				orgId, OrganizationParameter.CUSTOMERSERVICE_EMAIL_ADDRESS);

//		String name = null;
//		String address = null;

//		if (nameParam != null) {
//			name = nameParam.getStringValue();
//		}
//		if (addressParam != null) {
//			address = addressParam.getStringValue();
//		}

		String name=  "PLS Customer Service";
		String address = "customerService@plslogisics.com";
		
		if (address != null && name != null) {
			sender = new MailerAddress(address, name);
		} else if (address != null) {
			sender = new MailerAddress(address);
		} else {
			throw new MailerException(
					"Mailer Error no default sender parameters set for Org Id "
							+ orgId.toString());
		}

		return sender;
	}

	/**
	 * TODO move this type of logic to a Mailer work flow in the future.
	 * 
	 * Helper to invoke the OrganizationParameterService
	 * 
	 * @param orgId
	 * @param parameter
	 *            to retreive
	 * @return OrganizationParameterValue
	 */
//	protected OrganizationParameterValue getOrganizationParameter(Long orgId,
//			OrganizationParameter parameter) {
//		OrgParameterCriteria criteria = new OrgParameterCriteria();
//		criteria.setOrgId(orgId);
//		criteria.setParam(parameter);
//
//		OrgParameterOperationResult result = (OrgParameterOperationResult) ((OrgParameterService) ServiceFactory
//				.getService(OrgParameterService.class))
//				.getOrgParameterValues(criteria);
//		if (result.isSuccess()) {
//			List<OrganizationParameterValue> values = result.getPayLoad();
//			if (values != null && values.size() > 0) {
//				return (OrganizationParameterValue) values.get(0);
//			}
//			return null;
//		} else if (result.hasBusinessError() || result.hasValidationError()) {
//			OperationResultUtil.throwPlsRunExceptionFromBizOrValidError(result);
//		} else if (result.getUnknownError() != null) {
//			OperationResultUtil.throwPlsRunExceptionFromUnkownError(result);
//		}
//		return null;
//	}

}
