package com.joeysoft.kc868.xsd;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;

import com.joeysoft.kc868.xsd.login.Login;
import com.joeysoft.kc868.xsd.login.LoginFactory;
import com.joeysoft.kc868.xsd.login.LoginPackage;
import com.joeysoft.kc868.xsd.login.Logins;

/**
 * 登录历史文件工具类
 * 
 * @author JOEY
 *
 */
public class LoginUtil {
	
	/**
	 * 保存登录历史文件
	 * 
	 * @param file
	 * 		文件路径
	 * @param server
	 * 		文件根元素对象
	 * @throws IOException
	 * 		如果保存出错
	 */
	public static final void save(File file, Logins logins) {
		// Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();
        
        // Register the default resource factory -- only needed for stand-alone!
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl() {
            public Resource createResource(URI uri) {
                XMLResource xmlResource = (XMLResource) super.createResource(uri);
                return xmlResource;
            }
        });
        
        resourceSet.getPackageRegistry().put(LoginPackage.eINSTANCE.getNsURI(), LoginPackage.eINSTANCE);
                
        // Get the URI of the model file.
        URI fileURI = URI.createFileURI(file.getAbsolutePath());

        // Create a resource for this file.
        Resource resource = resourceSet.createResource(fileURI);

        // add the globalsetting to the resource
        resource.getContents().add(logins);

        // Save the contents of the resource to the file system.
        Map options = new HashMap();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
			resource.save(options);
		} catch (IOException e) {
		}
	}
	
	/**
	 * 载入登录历史文件
	 * 
	 * @param file
	 * 		文件路径
	 * @return
	 * 		根元素对象
	 */
	public static final Logins load(File file) {
		// Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();

        // Register the default resource factory -- only needed for stand-alone!
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl() {
            public Resource createResource(URI uri) {
                XMLResource xmlResource = (XMLResource) super.createResource(uri);
                return xmlResource;
            }
        });
                
        resourceSet.getPackageRegistry().put(LoginPackage.eINSTANCE.getNsURI(), LoginPackage.eINSTANCE);
                
        // Get the URI of the model file.
        URI fileURI = URI.createFileURI(file.getAbsolutePath());

        // Create a resource for this file.
        Resource resource = resourceSet.createResource(fileURI);

        // Save the contents of the resource to the file system.
        Map options = new HashMap();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
			resource.load(options);
	        if(resource.getContents().isEmpty())
	        	return null;
	        else
	        	return (Logins) resource.getContents().get(0);
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * @return
	 * 		缺省的登录历史文件根元素对象
	 */
	public static final Logins createDefault() {
		Logins logins = LoginFactory.eINSTANCE.createLogins();
		logins.setLastLogin("");
		return logins;
	}
	
	/**
	 * 找寻登录历史
	 * 
	 * @param logins
	 * 		登录历史根元素
	 * @param IP
	 * @return
	 * 		Login对象
	 */
	public static final Login findLogin(Logins logins, String IP) {
		if(logins == null)
			return null;
		
		for(Login login : (List<Login>)logins.getLogin()) {
            if(login.getIP().equals(IP)) 
                return login;
        }
        return null;
	}
}
