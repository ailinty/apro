package com.joeysoft.kc868.ui.helper;

import static com.joeysoft.kc868.resource.Messages.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public final class MessageBoxHelper {
	/**
	 * 错误对话框
	 * @param shell
	 * @param message 内容
	 */
	public static void openError(Shell shell, String message){
		openError(shell, null, message);
	}
	
	/**
	 * 错误对话框
	 * @param shell
	 * @param title 标题
	 * @param message 内容
	 */
	public static void openError(Shell shell, String title, String message){
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR|SWT.YES|SWT.ON_TOP);
		messageBox.setText(title==null?message_box_common_error_title:title);
		messageBox.setMessage(message);
		messageBox.open();
	}
	
	/**
	 * 警告对话框
	 * @param shell
	 * @param message 内容
	 */
	public static void openWarning(Shell shell, String message){
		openWarning(shell, null, message);
	}
	
	/**
	 * 警告对话框
	 * @param shell
	 * @param title 标题
	 * @param message 内容
	 */
	public static void openWarning(Shell shell, String title, String message){
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING|SWT.YES|SWT.ON_TOP);
		messageBox.setText(title==null?message_box_common_warning_title:title);
		messageBox.setMessage(message);
		messageBox.open();
	}
	
	/**
	 * 信息对话框
	 * @param shell
	 * @param message 内容
	 */
	public static void openInformation(Shell shell, String message){
		openInformation(shell, null, message);
	}
	
	/**
	 * 信息对话框
	 * @param shell
	 * @param title 标题
	 * @param message 内容
	 */
	public static void openInformation(Shell shell, String title, String message){
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION|SWT.YES|SWT.ON_TOP);
		messageBox.setText(title==null?message_box_common_info_title:title);
		messageBox.setMessage(message);
		messageBox.open();
	}
	
	public static boolean openQuestion(Shell shell, String message){
		return openQuestion(shell, null, message);
	}
	
	public static boolean openQuestion(Shell shell, String title, String message){
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION|SWT.YES|SWT.NO|SWT.ON_TOP);
		messageBox.setText(title==null?message_box_common_info_title:title);
		messageBox.setMessage(message);
		int rest = messageBox.open();
		if(rest == SWT.YES){
			return true;
		}
		return false;
	}
	
	public static MessageBox openDialog(Shell shell,String message){
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION|SWT.ON_TOP);
		messageBox.setText(message_box_common_info_title);
		messageBox.setMessage(message);
		return messageBox;
	}
}
