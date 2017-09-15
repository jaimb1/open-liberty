/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.ibm.wsspi.request.probe.bci;

/**
 * <p>
 * RequestProbeTransformDescriptor is used to define the various "Request" which
 * the user wants to monitor. Each instance of RequestProbeTransformDescriptor
 * defines a single request.
 * </p>
 * 
 * The following is an example snippet:
 * 
 * 
 * <pre>
 * <code>
 * public class MyOperationTranformDescriptor implements com.ibm.wsspi.request.probe.bci.RequestProbeTransformDescriptor
 * 
 *      private static final String classToInstrument = "com/mypackage/MyClass";
 *      private static final String methodToInstrument = "myMethod";
 *      private static final String descOfMethod = "(Ljava.lang.String)V";
 *      private static final String timedOpsType = "SOME_STATIC_TYPE";
 * 
 *      public boolean isCounter(){
 *      		return false;
 *      }
 *      
 *      public String getClassName()
 *              return classToInstrument;
 *      }
 * 
 *      public String getMethodName() {
 *              return methodToInstrument;
 *      }
 * 
 *      public String getType() {
 *              return timedOpsType;
 *      }
 * 
 *      public String getMethodDesc() {
 *              return descOfMethod;
 *      }
 * 
 *      public String getContextInfo(Object instanceOfThisClass, Object methodArgs) {
 *              String rcString = "";
 * 
 *              //If an instance of 'com.mypackage.MyClass' is useful, then use it and get the correct message for this particular operation
 *              if (instanceOfThisClass != null){
 *                      com.mypackage.MyClass passedInstance = (com.mypackage.MyClass) instanceOfThisClass;
 *                      //Do something here with this object and get the right meta data String and assign to rcString;
 *              }
 * 
 *              //If helpful you can use the argument (String in this case) in your method
 *              //Get the necessary data from arguments and assign to rcString;
 *              if(methodArgs!=null){
 *                      Object[] params = null;
 *                      if (Object[].class.isInstance(methodArgs)) {
 *                              params = (Object[]) methodArgs;
 *                              for (int i = 0; i < params.length; i++) {
 *                                      System.out.println("ARG "+i+" = "+params[i] );
 *                              }
 *                      }
 *              }
 *              return rcString;
 *      }
 * } 
 * 
 * </code>
 * </pre>
 * 
 * @ibm-spi
 */
public interface RequestProbeTransformDescriptor {

	/**
	 * Indicates that events generated by this transform descriptor should be
	 * treated as counters. Counter events will not be associated with request contexts.
	 * Start and end times will not be tracked for counter events.
	 * 
	 * @return Should return true for counters else return false.
	 * 
	 * @ibm-spi
	 */
	public boolean isCounter();
	
	/**
	 * Returns the name of class, which needs to be monitored. ClassName must be
	 * in the form of classname including complete package name. e.g.
	 * com/mypkg/MyClass
	 * 
	 * @return Returns the Name of Class, which needs to be monitored for
	 *         Request Timing, Event Logging, Timed Operations or Monitor.
	 * 
	 * @ibm-spi
	 */
	public String getClassName();

	/**
	 * Returns the name of method which belongs to above class (see {@link
	 * getClassName()}), which needs to be monitored. If method is overridden,
	 * {@link getMethodDesc()} will be used to differentiate it.
	 * 
	 * @return Name of method which need to be monitored for Request
	 *         Timing/Timed Operations/Monitor.
	 * 
	 * @ibm-spi
	 */
	public String getMethodName();

	/**
	 * Returns the Method Descriptor which belongs to above class/method (see
	 * {@link getClassName()} and see {@link getMethodName()}).
	 * 
	 * @return Description of Method to differentiate overloaded method defined
	 *         in {@link getMethodName()}.
	 * 
	 * @ibm-spi
	 */
	public String getMethodDesc();

	/**
	 * Type of Request This would be used as type argument to
	 * getRequestTiming(type, contextInfo) method in RequestTimingService class.
	 * 
	 * @return Type of request.
	 * 
	 * @ibm-spi
	 */
	public String getEventType();

	/**
	 * Get context information of Request Timing/Event Logging/Timed Operations/Monitor.
	 * This would be used as an argument to getRequestTiming(type, contextInfo)
	 * method in RequestTimingService class.
	 * 
	 * @param thisInstance
	 *            thisInstance is the instance of class, which is used for timed
	 *            operation. (Same class defined in getClassName();)
	 * 
	 * @param objArrays
	 *            Actual parameter objects which are passed to method, defined
	 *            by getMethodName()
	 * 
	 * @return Context information to identify the Request Timing/Timed Operations/Monitor
	 *         Instance.
	 * 
	 * @ibm-spi
	 */
	public Object getContextInfo(Object thisInstance, Object objArrays);
	
}
