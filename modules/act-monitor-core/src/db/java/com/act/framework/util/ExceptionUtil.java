package com.act.framework.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 关于异常的工具类.
 * 
 */
public class ExceptionUtil {

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex;
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
	
	public static Throwable getRootException (Throwable e){
		if (e.getCause()!=null)
			return getRootException(e.getCause());
		else
			return e;
	}
	
	public static String getExceptionMessage(Throwable e){
		Throwable r = getRootException(e);
		if (r.getMessage()!=null&&r.getMessage().length()>0){
			return r.getMessage();
		}
		else
			return r.toString();
	}
	
	public static RuntimeException toRuntimeException(Exception e) {
		if (e instanceof RuntimeException)
			return (RuntimeException) e;
		else
			return new RuntimeException(e);
	}
}
