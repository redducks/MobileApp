package com.pls.sendmail.business.impl;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;

import org.apache.log4j.Logger;

import com.pls.sendmail.business.MailerConstants;
import com.pls.sendmail.business.MailerTask;
import com.pls.sendmail.util.PropertyUtil;



/**
 * Controller, to receive and process mail tasks
 * each mail tasks is added in a queue based on its priority
 * when there are pending tasks, a separate thread is
 * created to process them.
 * The thread is terminated when the queue is empty.
 * This class was moved from eflatbed to contentdelivery with 
 * minimal changes.
 */
public final class MailerTaskManager extends Thread {
    
    private final static Logger LOG = Logger.getLogger(MailerTaskManager.class.getName());

	private static MailerTaskManager m_manager;
    private static Object m_mutex = new Object();
    private static boolean c_initialized = false;
    private static Session c_session = null;
    private LinkedList<MailerTask> m_requests = new LinkedList<MailerTask>();
    public static final String DEBUG_SUBSYSTEM = "Mailer";

    private MailerTaskManager() {
        super("Mailer sender thread");
        setContextClassLoader(Thread.currentThread().getContextClassLoader());
        setDaemon(true);
    }

    /**
     * Called from mailer EJB to initiate sending e-mail
     */
    public static void addTask(MailerTask task) {
        LOG.debug(DEBUG_SUBSYSTEM + "Initiate mailer task " + task.getDescription());
        synchronized (m_mutex) {
            if (getMailSession() == null) {
                LOG.info("Mail server disabled. Unable to send " + task.getDescription());
                return;
            }
            if (m_manager == null || !m_manager.isAlive()) {
                m_manager = new MailerTaskManager();
                m_manager.start();
            }

            m_manager.addTaskToList(task);
        }
    }

    private void addTaskToList(MailerTask task) {
        task.eventScheduled();

        // Try to locate a spot in the queue based on the priority
        ListIterator<MailerTask> it = m_requests.listIterator(m_requests.size());

        while (it.hasPrevious()) {
            MailerTask ct = (MailerTask)it.previous();

            if (ct.getPriority() >= task.getPriority()) {
                it.next();

                break;
            }
        }

        it.add(task);
    }

    private MailerTask getNextTask() {
        synchronized (m_mutex) {
            if (m_requests.isEmpty()) {
                m_manager = null;

                return null;
            }

            return (MailerTask)m_requests.removeFirst();
        }
    }

    /**
     * Invoked, when there are mail tasks in the thread
     */
    public void run() {
        MailerTask task = getNextTask();

        Session s = getMailSession();
        try {
        	Transport t = s.getTransport(MailerConstants.SMTP_PROTOCOL);
        	t.connect();
        	while (task != null) {
        		task.run( s, t );
        		task = getNextTask();
        	}
        	t.close();
        } catch( Throwable t ) {

            LOG.error("General error in the mailer while sending emails ", t );

        	//try to pass the caught object back out to the MailerSessionBean and keep going
//        	try{
//        	    getMailerLocalInterface().handleTaskManagerException(t);
//        	}catch(ServiceCreationException sce){
//        		//oh well, keep going anyway
//                LOG.error("Failed to pass the exception to the Mailer Service Layer");
//        	}
        }
    }

    /**
     * Returns the mail session from the MBean configuration
     */
    protected static Session getMailSession() {
        if (c_initialized) {
            return c_session;
        }

        try {
            cacheMailSession(PropertyUtil.isEnabled(), PropertyUtil.getHost(), PropertyUtil.getFaxSender(), PropertyUtil.getFaxDomain());
            
        } catch (Exception e) {
            LOG.error("The mailer is disabled. Failure, when obtaining mail server session parameters.", e);
        }

        c_initialized = true;

        return c_session;
    }

    private static void cacheMailSession(boolean enabled, String host, String faxDomain, String faxSender ) {
        if (enabled) {
            Properties props = new Properties();
            props.put(MailerConstants.PROP_MAIL_HOST, host);
            props.put(MailerConstants.PROP_TRANS_PROTOCOL, MailerConstants.SMTP_PROTOCOL);
            props.put(MailerConstants.PROP_FAX_DOMAIN, faxDomain );
            props.put(MailerConstants.PROP_FAX_SENDER, faxSender);
            c_session = Session.getInstance(props);
            LOG.info("Mail session enabled, SMTP server on " + host);
        } else {
            c_session = null;
            synchronized (m_mutex) {
                m_manager = null;
            }
            LOG.info("Mail session disabled");
        }
    }

//    /**
//     * Listener for configuration changes from the MBean server.
//     * Mail Server settings may be changed via the JMX Admin Console
//     * 
//     */
//    private static class MailerChangeListener implements NotificationListener {
//        public void handleNotification(Notification notification, Object handback) {
//            if (notification instanceof MailServerChangedEvent) {
//                MailServerChangedEvent m = (MailServerChangedEvent)notification;
//                cacheMailSession(m.getEnabled(), m.getHost(), m.getFaxDomain(), m.getFaxSender() );
//            }
//        }
//    }
//    
//    
//    /**
//     * Get the Mailer so the Task Manager can call back exceptions at runtime
//     */
//	private MailerLocal getMailerLocalInterface() throws ServiceCreationException{
//		
//		if(mailerLocalInterface == null){
//			try {
//				this.mailerLocalInterface = (MailerLocal) MailerSlsbUtil.getLocalHome().create();
//			} catch (Exception e) {
//				throw new ServiceCreationException("Unable to create Mailer Local Home interface ", e);
//			}
//		}
//		return mailerLocalInterface;
//	}
    
    
}
