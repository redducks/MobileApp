package com.pls.sendmail.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.WeakHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.pls.sendmail.util.ResourceHelper;
import com.pls.sendmail.vo.TemplateContent;



/**
 * Mailer transformation processing for turning raw XML into HTML output 
 * was refactored into this class when the Mailer was moved to contentdelivery
 * 
 * @author glawler
 *
 */
public class TransformerProxy {

	
	
	private final static Logger LOG = Logger.getLogger(TransformerProxy.class.getName());

	
    private static Map<String,TransformerCachedValue> m_cachedTransformers = new WeakHashMap<String,TransformerCachedValue>();

	
    
    /**
     * Method to encapsulate the transfomation logic. 
     * 
     * @param templateName matching on of the available templates as enumerated by a resource helper
     * @param XML source as a byte array
     * @return resulting HTML content as a string
     * @throws TransformerException
     */
    public static String transformToHTML(String templateName, TemplateContent xmlContent) throws TransformerException{
    	
		LOG.debug("Entering transformToHTML()" );
		LOG.debug(" template name is " + templateName );
		Transformer transformer = loadMailerTransformer(templateName);
		
		ByteArrayOutputStream resultOut = new ByteArrayOutputStream();
		ByteArrayInputStream xmlInStream = new ByteArrayInputStream(convertObjectToXML(xmlContent));
		
		StreamSource rawXML = new StreamSource(xmlInStream); 
		StreamResult contentHTML = new StreamResult(resultOut);
		
		transformer.transform(rawXML, contentHTML);
		
		String transformedResult = null;
		if(contentHTML.getOutputStream() != null ){
			transformedResult = contentHTML.getOutputStream().toString();
			LOG.debug("Returning the transformed result from transformToHTML()" );
		}
		
		return transformedResult;
    }
    
    

	
	
    /**
     * Returns a transformer by name.
     * <P>The transformer may be loaded:</P>
     * <DIR>
     * <LI>From the cache - if exists in cache already</LI>
     * <LI>From the external file - cached after loaded from the file</LI>
     * </DIR>
     * 
     * Modifications made when this code moved to contentdelivery:
     * - the concept of using a resource helper to provide a mapping to the actual template was added.
     * - This method also tells a cached transformer what it name is when caching the transformer
     * 
     */
    public static Transformer loadMailerTransformer( String templateName ) throws TransformerException {
    	
		LOG.debug("Entering loadMailerTransformer()" );

        // Look up in the cache first
        TransformerCachedValue candidate = (TransformerCachedValue)m_cachedTransformers.get(templateName);


        if ((candidate == null) ){ 
        	
    		LOG.debug("Transformer candidate is not cached, attempting to load the transformer." );

			try {

				byte[] templateBytes = ResourceHelper.getEmailTemplate(templateName);
				
	        	ByteArrayInputStream xsltInStream = new ByteArrayInputStream(templateBytes);

	    		Transformer transformer = TransformerFactory.newInstance().newTransformer(
	    				new StreamSource(xsltInStream));

	    		candidate = new TransformerCachedValue(transformer, templateName);
	            m_cachedTransformers.put(templateName, candidate);
	    		LOG.debug("Added the transformer candidate to the cache." );

			} catch (Exception e) {
				
				String errorMessage = "Unable to load a transformer for template " + templateName;
				LOG.error(errorMessage, e);
				throw new TransformerException(errorMessage, e);
			}

        } 
        
        StringBuffer logMsg = new StringBuffer("");
        logMsg.append("Returning ");
        if(candidate != null && candidate.getName() != null){
        	logMsg.append(candidate.getName());
        }
        logMsg.append(" transformer from loadMailerTransformer()");
		LOG.debug(logMsg.toString());
		
        return candidate.getTransformer();
    }

    
	
    
    /**
     * This is the logic for converting the raw XML data from
     * it's object tree to the bytes of an XML Source.
     * 
     * Currently this is performed the content is set on the XMLMailCriteria object.
     * As an alternative the object tree could be provided and converted at
     * this point, right before the transformation to HTML takes place.
     * 
     * @param xmlContent marked with JAXB annotations
     * @return byte[] containing the XML source marshalled from the object tree
     * @throws Exception
     */
	protected static byte[] convertObjectToXML(TemplateContent xmlContent){

		LOG.debug("Entering convertObjectToXML()" );
		
		ByteArrayOutputStream resultOut = new ByteArrayOutputStream();

			
	    JAXBContext context;
		Marshaller marshaller;
		try {

			context = JAXBContext.newInstance(xmlContent.getClass());
			marshaller = context.createMarshaller();
			LOG.debug(" marshaller was created " );
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(xmlContent, resultOut);

				
			if(resultOut == null ){
				String message = ("Error marshalling TemplateContent to XML.  Marshall result was null.");
				LOG.error(message);
				throw new RuntimeException(message);
			}

			LOG.debug(" marshall complete, returning XML as byte array. " );
		    return resultOut.toByteArray();

		} catch (JAXBException e) {
			String message = ("Error marshalling TemplateContent to XML. JAXB exception while creating XML for message content. ");
			LOG.error(message);
			throw new RuntimeException(message, e);
		}
	}

	
    
    
    /**
     * Value object, to cache a parsed XSLT transformer
     * and the date of transformer modification.
     * 
     * Caching the transformers seems like a really good 
     * feature for the mailer and was maintained when the
     * Mailer moved to contentdevilery.
     * 
     * One change in the caching is that the name of the template
     * is used to identify the transformer, and this same name was 
     * also added to the cache.  It just seems appropriate that a 
     * cached transformer should know what it's name is.
     * 
     */
    static final class TransformerCachedValue {
        /** The transformer. */
        private Transformer transformer;
        private String name;

        public TransformerCachedValue(Transformer value, String templateName) {
            transformer = value;
            name = templateName;
        }

        public Transformer getTransformer() {
            return transformer;
        }

		public String getName() {
			return name;
		}

        
    }
    
	
}
