package com.joeysoft.kc868.xsd.global;

import org.eclipse.emf.ecore.EFactory;

import com.joeysoft.kc868.xsd.global.impl.GlobalFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.joeysoft.kc868.xsd.global.GlobalPackage
 * @generated
 */
public interface GlobalFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GlobalFactory eINSTANCE = new GlobalFactoryImpl();

	/**
	 * Returns a new object of class '<em>Setting</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Setting</em>'.
	 * @generated
	 */
	GlobalSetting createGlobalSetting();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	GlobalPackage getGlobalPackage();

} //GlobalFactory
