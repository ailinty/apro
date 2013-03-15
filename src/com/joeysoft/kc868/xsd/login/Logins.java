package com.joeysoft.kc868.xsd.login;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

public interface Logins extends EObject{

	EList getLogin();
	
	String getLastLogin();
	
	void setLastLogin(String value);
}
