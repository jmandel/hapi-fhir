package ca.uhn.fhir.rest.server.interceptor;

/*
 * #%L
 * HAPI FHIR - Core Library
 * %%
 * Copyright (C) 2014 University Health Network
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.uhn.fhir.model.api.Bundle;
import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.model.api.TagList;
import ca.uhn.fhir.model.base.resource.BaseOperationOutcome;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.method.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;

/**
 * Provides methods to intercept requests and responses. Note that implementations of this interface may wish to use {@link InterceptorAdapter} in order to not need to implement every method.
 * <p>
 * <b>See:</b> See the 
 * <a href="http://jamesagnew.github.io/hapi-fhir/doc_rest_server_interceptor.html">server interceptor documentation</a>
 * for more information on how to use this class.
 * </p>
 */
public interface IServerInterceptor {

	/**
	 * This method is called before any other processing takes place for each incoming request. It may be used to provide alternate handling for some requests, or to screen requests before they are
	 * handled, etc.
	 * <p>
	 * Note that any exceptions thrown by this method will not be trapped by HAPI (they will be passed up to the server)
	 * </p>
	 * 
	 * @param theRequest
	 *            The incoming request
	 * @param theResponse
	 *            The response. Note that interceptors may choose to provide a response (i.e. by calling {@link HttpServletResponse#getWriter()}) but in that case it is important to return
	 *            <code>false</code>
	 * @return Return <code>true</code> if processing should continue normally. This is generally the right thing to do. If your interceptor is providing a response rather than letting HAPI handle the
	 *         response normally, you must return <code>false</code>. In this case, no further processing will occur and no further interceptors will be called.
	 */
	public boolean incomingRequestPreProcessed(HttpServletRequest theRequest, HttpServletResponse theResponse);

	/**
	 * This method is called just before the actual implementing server method is invoked.
	 * <p>
	 * Note about exceptions:
	 * </p>
	 * 
	 * @param theRequestDetails
	 *            A bean containing details about the request that is about to be processed, including details such as the resource type and logical ID (if any) and other
	 *            FHIR-specific aspects of the request which have been pulled out of the {@link HttpServletRequest servlet request}.
	 * @param theRequest
	 *            The incoming request
	 * @param theResponse
	 *            The response. Note that interceptors may choose to provide a response (i.e. by calling {@link HttpServletResponse#getWriter()}) but in that case it is important to return
	 *            <code>false</code>
	 * @return Return <code>true</code> if processing should continue normally. This is generally the right thing to do. If your interceptor is providing a response rather than letting HAPI handle the
	 *         response normally, you must return <code>false</code>. In this case, no further processing will occur and no further interceptors will be called.
	 * @throws AuthenticationException
	 *             This exception may be thrown to indicate that the interceptor has detected an unauthorized access attempt. If thrown, processing will stop and an HTTP 401 will be returned to the
	 *             client.
	 */
	public boolean incomingRequestPostProcessed(RequestDetails theRequestDetails, HttpServletRequest theRequest, HttpServletResponse theResponse) throws AuthenticationException;

	/**
	 * This method is called after the server implementation method has been called, but before any attempt to stream the response back to the client
	 * 
	 * @param theRequestDetails
	 *            A bean containing details about the request that is about to be processed, including details such as the resource type and logical ID (if any) and other
	 *            FHIR-specific aspects of the request which have been pulled out of the {@link HttpServletRequest servlet request}.
	 * @param theResponseObject
	 *            The actual object which is being streamed to the client as a response
	 * @param theRequest
	 *            The incoming request
	 * @param theResponse
	 *            The response. Note that interceptors may choose to provide a response (i.e. by calling {@link HttpServletResponse#getWriter()}) but in that case it is important to return
	 *            <code>false</code>
	 * @return Return <code>true</code> if processing should continue normally. This is generally the right thing to do. If your interceptor is providing a response rather than letting HAPI handle the
	 *         response normally, you must return <code>false</code>. In this case, no further processing will occur and no further interceptors will be called.
	 * @throws AuthenticationException
	 *             This exception may be thrown to indicate that the interceptor has detected an unauthorized access attempt. If thrown, processing will stop and an HTTP 401 will be returned to the
	 *             client.
	 */
	public boolean outgoingResponse(RequestDetails theRequestDetails, TagList theResponseObject, HttpServletRequest theServletRequest, HttpServletResponse theServletResponse)
			throws AuthenticationException;

	/**
	 * This method is called after the server implementation method has been called, but before any attempt to stream the response back to the client
	 * 
	 * @param theRequestDetails
	 *            A bean containing details about the request that is about to be processed, including
	 * @param theResponseObject
	 *            The actual object which is being streamed to the client as a response
	 * @param theRequest
	 *            The incoming request
	 * @param theResponse
	 *            The response. Note that interceptors may choose to provide a response (i.e. by calling {@link HttpServletResponse#getWriter()}) but in that case it is important to return
	 *            <code>false</code>
	 * @return Return <code>true</code> if processing should continue normally. This is generally the right thing to do. If your interceptor is providing a response rather than letting HAPI handle the
	 *         response normally, you must return <code>false</code>. In this case, no further processing will occur and no further interceptors will be called.
	 * @throws AuthenticationException
	 *             This exception may be thrown to indicate that the interceptor has detected an unauthorized access attempt. If thrown, processing will stop and an HTTP 401 will be returned to the
	 *             client.
	 */
	public boolean outgoingResponse(RequestDetails theRequestDetails, Bundle theResponseObject, HttpServletRequest theServletRequest, HttpServletResponse theServletResponse)
			throws AuthenticationException;

	/**
	 * This method is called after the server implementation method has been called, but before any attempt to stream the response back to the client
	 * 
	 * @param theRequestDetails
	 *            A bean containing details about the request that is about to be processed, including details such as the resource type and logical ID (if any) and other
	 *            FHIR-specific aspects of the request which have been pulled out of the {@link HttpServletRequest servlet request}.
	 * @param theResponseObject
	 *            The actual object which is being streamed to the client as a response
	 * @param theRequest
	 *            The incoming request
	 * @param theResponse
	 *            The response. Note that interceptors may choose to provide a response (i.e. by calling {@link HttpServletResponse#getWriter()}) but in that case it is important to return
	 *            <code>false</code>
	 * @return Return <code>true</code> if processing should continue normally. This is generally the right thing to do. If your interceptor is providing a response rather than letting HAPI handle the
	 *         response normally, you must return <code>false</code>. In this case, no further processing will occur and no further interceptors will be called.
	 * @throws AuthenticationException
	 *             This exception may be thrown to indicate that the interceptor has detected an unauthorized access attempt. If thrown, processing will stop and an HTTP 401 will be returned to the
	 *             client.
	 */
	public boolean outgoingResponse(RequestDetails theRequestDetails, IResource theResponseObject, HttpServletRequest theServletRequest, HttpServletResponse theServletResponse)
			throws AuthenticationException;

	/**
	 * This method is called after the server implementation method has been called, but before any attempt to stream the response back to the client
	 * 
	 * @param theRequestDetails
	 *            A bean containing details about the request that is about to be processed, including details such as the resource type and logical ID (if any) and other
	 *            FHIR-specific aspects of the request which have been pulled out of the {@link HttpServletRequest servlet request}.
	 * @param theResponseObject
	 *            The actual object which is being streamed to the client as a response
	 * @param theRequest
	 *            The incoming request
	 * @param theResponse
	 *            The response. Note that interceptors may choose to provide a response (i.e. by calling {@link HttpServletResponse#getWriter()}) but in that case it is important to return
	 *            <code>false</code>
	 * @return Return <code>true</code> if processing should continue normally. This is generally the right thing to do. If your interceptor is providing a response rather than letting HAPI handle the
	 *         response normally, you must return <code>false</code>. In this case, no further processing will occur and no further interceptors will be called.
	 * @throws AuthenticationException
	 *             This exception may be thrown to indicate that the interceptor has detected an unauthorized access attempt. If thrown, processing will stop and an HTTP 401 will be returned to the
	 *             client.
	 */
	public boolean outgoingResponse(RequestDetails theRequestDetails, HttpServletRequest theServletRequest, HttpServletResponse theServletResponse) throws AuthenticationException;

	/**
	 * This method is called upon any exception being thrown within the server's request processing code. This includes any exceptions thrown within resource provider methods (e.g. {@link Search} and
	 * {@link Read} methods) as well as any runtime exceptions thrown by the server itself. This also includes any {@link AuthenticationException}s thrown.
	 * <p>
	 * Implementations of this method may choose to ignore/log/count/etc exceptions, and return <code>true</code>. In this case, processing will continue, and the server will automatically generate an
	 * {@link BaseOperationOutcome OperationOutcome}. Implementations may also choose to provide their own response to the client. In this case, they should return <code>false</code>, to indicate that
	 * they have handled the request and processing should stop.
	 * </p>
	 * 
	 * @param theRequestDetails
	 *            Contains either <code>null</code>, or a bean containing details about the request that is about to be processed, including details such as the resource type and logical ID (if any) and other
	 *            FHIR-specific aspects of the request which have been pulled out of the {@link HttpServletRequest servlet request}. This parameter may be
	 *            null if the request processing did not successfully parse the incoming request, but will generally not be null.
	 * @param theResponseObject
	 *            The actual object which is being streamed to the client as a response
	 * @param theRequest
	 *            The incoming request
	 * @param theResponse
	 *            The response. Note that interceptors may choose to provide a response (i.e. by calling {@link HttpServletResponse#getWriter()}) but in that case it is important to return
	 *            <code>false</code>
	 * @return Return <code>true</code> if processing should continue normally. This is generally the right thing to do. If your interceptor is providing a response rather than letting HAPI handle the
	 *         response normally, you must return <code>false</code>. In this case, no further processing will occur and no further interceptors will be called.
	 * @throws ServletException
	 *             If this exception is thrown, it will be re-thrown up to the container for handling.
	 * @throws IOException
	 *             If this exception is thrown, it will be re-thrown up to the container for handling.
	 */
	public boolean handleException(RequestDetails theRequestDetails, Throwable theException, HttpServletRequest theServletRequest, HttpServletResponse theServletResponse) throws ServletException,
			IOException;

}
