package com.pls.sendmail.vo;

/**
 * Content type for HTML formated content.
 * 
 * Use this if you already have HTML, such as in the case of
 * a re-send operation.
 * 
 * @author glawler
 *
 */
public class HTMLContent extends TextContent {

	private static final long serialVersionUID = 3207476098824107657L;

	public HTMLContent(String htmlContent, String subjectLine) {
		super(htmlContent, subjectLine);
	}

}
