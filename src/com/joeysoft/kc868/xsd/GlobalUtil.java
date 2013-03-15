package com.joeysoft.kc868.xsd;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;

import com.joeysoft.kc868.xsd.global.GlobalFactory;
import com.joeysoft.kc868.xsd.global.GlobalPackage;
import com.joeysoft.kc868.xsd.global.GlobalSetting;

/**
 * Global配置文件工具类
 *
 * @author Joey
 */
public class GlobalUtil {
	/**
	 * 保存全局配置文件
	 * 
	 * @param file
	 * 		文件路径
	 * @param gs
	 * 		文件根元素对象
	 * @throws IOException
	 * 		如果保存出错
	 */
	public static final void save(File file, GlobalSetting gs) {
        // Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();
        
        // Register the default resource factory -- only needed for stand-alone!
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl() {
            public Resource createResource(URI uri) {
                XMLResource xmlResource = (XMLResource) super.createResource(uri);
                return xmlResource;
            }
        });
        
        resourceSet.getPackageRegistry().put(GlobalPackage.eINSTANCE.getNsURI(), GlobalPackage.eINSTANCE);
                
        // Get the URI of the model file.
        URI fileURI = URI.createFileURI(file.getAbsolutePath());

        // Create a resource for this file.
        Resource resource = resourceSet.createResource(fileURI);

        // add the globalsetting to the resoource
        resource.getContents().add(gs);

        // Save the contents of the resource to the file system.
        Map options = new HashMap();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
			resource.save(options);
		} catch (IOException e) {
		}
	}
	
	/**
	 * 载入全局配置文件
	 * 
	 * @param file
	 * 		文件路径
	 * @return
	 * 		根元素对象
	 */
	public static final GlobalSetting load(File file) {
        // Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();

        // Register the default resource factory -- only needed for stand-alone!
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl() {
            public Resource createResource(URI uri) {
                XMLResource xmlResource = (XMLResource) super.createResource(uri);
                return xmlResource;
            }
        });
                
        resourceSet.getPackageRegistry().put(GlobalPackage.eINSTANCE.getNsURI(), GlobalPackage.eINSTANCE);
                
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
	        	return (GlobalSetting) resource.getContents().get(0);
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * @return
	 * 		缺省的全局配置根元素对象
	 */
	public static final GlobalSetting createDefault() {
		GlobalSetting gs = GlobalFactory.eINSTANCE.createGlobalSetting();
		gs.setDBVersion("000");
		return gs;
	}
}
