/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package edu.nps.example.service.impl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import edu.nps.example.service.base.FooServiceBaseImpl;

/**
 * The implementation of the foo remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link edu.nps.example.service.FooService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author kirk
 * @see edu.nps.example.service.base.FooServiceBaseImpl
 * @see edu.nps.example.service.FooServiceUtil
 */
public class FooServiceImpl extends FooServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link edu.nps.example.service.FooServiceUtil} to access the foo remote service.
	 */
	 public String print(String msg) {
 		System.out.println("JTest message: " + msg);
 		String returnVal = null;
 		try {
 			returnVal = JSONFactoryUtil.createJSONObject("{Message:" + msg + "}").toString();
 		} catch (JSONException e) {
 			e.printStackTrace();
 		}
 		return returnVal;
 	}
 	
}