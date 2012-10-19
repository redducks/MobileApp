package com.pls.sendmail.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Loads resources from the EAR at runtime.
 *
 *   Define a convenient name and a mapping to the relative path for each new resource.
 *
 * This was added to simplify the deployment of XSL sheets required for generating PDF
 * documents with Apache FOP.  These resources are not expected to change often, and
 * even if they do, they are included and distributed by the build directly in the EAR.
 *
 */
public class ResourceHelper {

	/**
	 * Enumeration of the key names for resources that are registered in the
	 * static mapping provided by this class.
	 * Allows for convenient naming of resources found in various sub directories
	 */
	enum Resources {
		PLS_LOGO,
		CHECK_BOX_IMAGE,
		W9_PDF_TEMPLATE
	}


	/**
	 * the build copies files from the <service>/resources directory
	 * to the APP-INF/classes directory during the staging task.
	 * Place files in this directory and also register with this Helper.
	 */
	protected static final String RESOURCE_ROOT = "/";

	/**
	 * Place message template files in this directory in the project
	 * all templates held in the same directory.
	 */
	protected static final String MESSAGE_TEMPLATE_ROOT = "/messages/";


	protected static Map<String, String> resourcesMap;

	/**
	 * Map a convenient resource name to it's actual file name, including any
	 * sub directories that occur below the root.
	 */
	static{
		resourcesMap = new HashMap<String, String>();
	}




	public static byte[] getPdfTemplateResource(String mappedResourceName)throws Exception{
		String relativeResourecePath = checkMapFor(mappedResourceName);
		return getResource(relativeResourecePath);
	}

	/**
	 * Accessor for all Mailer transformation templates.
	 *
	 * Refer to these templates by the file name of the template, including the extension.
	 * Mapping is not supported on these resources, the assumption being that these
	 * template files will always be in a single directory.
	 *
	 * @param templateName (file name of the template with extension)
	 * @return bytes of the template
	 * @throws Exception
	 */
	public static byte[] getEmailTemplate(String templateName)throws Exception{
		return getResource( MESSAGE_TEMPLATE_ROOT + templateName);
	}


	/**
	 * Pass the relative path to the resource the runtime path is determined by the
	 * class loader
	 *
	 * @param relative path and name of the resource
	 * @return bytes from the resource
	 * @throws Exception
	 */
	protected static byte[] getResource(String relativeResourcePath)throws Exception{
		byte[] bytes = null;

		if(relativeResourcePath != null && !relativeResourcePath.isEmpty()){

			InputStream inStream = null;
			try{
				inStream = ResourceHelper.class.getResourceAsStream(relativeResourcePath);
				
				if (inStream == null) {
					throw new IOException( "Unable to locate the requested resource from the relative path " );
				}
				
				System.out.println("Test message");

		        int size = inStream.available();
				bytes = new byte[size];

				inStream.read(bytes);

		        return bytes;

			}catch(Exception e){
				throw new InvocationTargetException(e, "Resource Helper unable to load requested resource at relative path " + relativeResourcePath);
			}finally{
				try {
					inStream.close();
				} catch (IOException e) {
					inStream = null;
				}
			}
		}else{
			throw new IllegalArgumentException("Passed a null or empty value for the relative path and resource name to ResourceHelper.getResouce()");
		}
	}

	/**
	 * Checks the Map of resources to look up the path to the named
	 * resource, if its found, the relative path to the resource is returned.
	 * @param resourceName Key for a mapped resource
	 * @return relative path to the mapped resource
	 */
	public static String checkMapFor(String resourceName) throws IllegalArgumentException {
		//first check the map
		if(resourcesMap.containsKey(resourceName)){
			StringBuffer sb = new StringBuffer(RESOURCE_ROOT);
			sb.append( resourcesMap.get(resourceName) );
			return sb.toString();
		}else{
			throw new IllegalArgumentException("The resource named " + resourceName + " is not a valid resource.");
		}
	}


	/**
	 * Look up a required resource that is registerd in the
	 * resource Map and get back the runtime URL for the resource
	 *
	 * implemented to support embedding images in PDF document generation
	 *
	 * @param key name for the resources map
	 * @return URL to the resource at runtime (from the ClassLoader)
	 */
	public static URL getResourceUrl(String mappedName){

		String resource = checkMapFor(mappedName);
		return Thread.currentThread().getContextClassLoader().getResource(resource);
	}

}
