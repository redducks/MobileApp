package com.pls.sendmail.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *  By convention the Template name is the same as the message template concrete class name.
 * These message template classes must implement this interface and then they may be passed
 * to the MailCriteria.
 * 
 *   Concrete instances should override this root element with their own
 * root element annotation. 
 * 
 *   The root element annotation is here as an example (or reminder) that the template class
 * instances should be annotated for JAXB processing.  The root element on this interface 
 * should let JAXB at least get started if an annotation is missing in a concrete 
 * template class.  That way the developer will at least get a JAXB exception if processing
 * fails.
 * 
 */
@XmlRootElement(name="plsprodocument")
public interface TemplateContent extends MailerContent {

	
}
