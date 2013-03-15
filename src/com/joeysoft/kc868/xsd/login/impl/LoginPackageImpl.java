package com.joeysoft.kc868.xsd.login.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.ecore.xml.type.impl.XMLTypePackageImpl;

import com.joeysoft.kc868.xsd.login.Login;
import com.joeysoft.kc868.xsd.login.LoginFactory;
import com.joeysoft.kc868.xsd.login.LoginPackage;
import com.joeysoft.kc868.xsd.login.Logins;

public class LoginPackageImpl extends EPackageImpl implements LoginPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loginEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loginsEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.ycmsoft.notesIP.xsd.login.LoginPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LoginPackageImpl() {
		super(eNS_URI, LoginFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LoginPackage init() {
		if (isInited) return (LoginPackage)EPackage.Registry.INSTANCE.getEPackage(LoginPackage.eNS_URI);

		// Obtain or create and register package
		LoginPackageImpl theLoginPackage = (LoginPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof LoginPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new LoginPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackageImpl.init();

		// Create package meta-data objects
		theLoginPackage.createPackageContents();

		// Initialize created meta-data
		theLoginPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLoginPackage.freeze();

		return theLoginPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLogin() {
		return loginEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_AutoLogin() {
		return (EAttribute)loginEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_LoginSync() {
		return (EAttribute)loginEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_Password() {
		return (EAttribute)loginEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_IP() {
		return (EAttribute)loginEClass.getEStructuralFeatures().get(3);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_RememberPassword() {
		return (EAttribute)loginEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_User() {
		return (EAttribute)loginEClass.getEStructuralFeatures().get(5);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_Port() {
		return (EAttribute)loginEClass.getEStructuralFeatures().get(6);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLogins() {
		return loginsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLogins_Login() {
		return (EReference)loginsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogins_LastLogin() {
		return (EAttribute)loginsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoginFactory getLoginFactory() {
		return (LoginFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		loginEClass = createEClass(LOGIN);
		createEAttribute(loginEClass, LOGIN__AUTO_LOGIN);
		createEAttribute(loginEClass, LOGIN__LOGIN_SYNC);
		createEAttribute(loginEClass, LOGIN__PASSWORD);
		createEAttribute(loginEClass, LOGIN__USER);
		createEAttribute(loginEClass, LOGIN__IP);
		createEAttribute(loginEClass, LOGIN__PORT);
		createEAttribute(loginEClass, LOGIN__REMEMBER_PASSWORD);

		loginsEClass = createEClass(LOGINS);
		createEReference(loginsEClass, LOGINS__LOGIN);
		createEAttribute(loginsEClass, LOGINS__LAST_LOGIN);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackageImpl theXMLTypePackage = (XMLTypePackageImpl)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(loginEClass, Login.class, "Login", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLogin_AutoLogin(), theXMLTypePackage.getBoolean(), "autoLogin", null, 1, 1, Login.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogin_LoginSync(), theXMLTypePackage.getBoolean(), "loginSync", null, 1, 1, Login.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogin_Password(), theXMLTypePackage.getString(), "password", null, 1, 1, Login.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogin_IP(), theXMLTypePackage.getString(), "IP", null, 1, 1, Login.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogin_User(), theXMLTypePackage.getString(), "user", null, 1, 1, Login.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogin_Port(), theXMLTypePackage.getString(), "port", null, 1, 1, Login.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogin_RememberPassword(), theXMLTypePackage.getBoolean(), "rememberPassword", null, 1, 1, Login.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(loginsEClass, Logins.class, "Logins", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLogins_Login(), this.getLogin(), null, "login", null, 0, -1, Logins.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogins_LastLogin(), theXMLTypePackage.getString(), "lastLogin", null, 1, 1, Logins.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
		addAnnotation
		  (loginEClass, 
		   source, 
		   new String[] {
			 "name", "Login",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getLogin_AutoLogin(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "autoLogin"
		   });		
		addAnnotation
		  (getLogin_LoginSync(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "loginSync"
		   });		
		addAnnotation
		  (getLogin_Password(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "password"
		   });
		addAnnotation
		  (getLogin_IP(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "IP"
		   });	
		addAnnotation
		  (getLogin_RememberPassword(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "rememberPassword"
		   });		
		addAnnotation
		  (getLogin_User(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "user"
		   });	
		addAnnotation
		  (getLogin_Port(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "port"
		   });
		addAnnotation
		  (loginsEClass, 
		   source, 
		   new String[] {
			 "name", "Logins",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getLogins_Login(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Login"
		   });		
		addAnnotation
		  (getLogins_LastLogin(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "lastLogin"
		   });
	}


}
