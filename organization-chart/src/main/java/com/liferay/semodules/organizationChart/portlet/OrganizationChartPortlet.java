/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.semodules.organizationChart.portlet;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.semodules.organizationChart.configuration.OrgChartConfiguration;
import com.liferay.semodules.organizationChart.constants.OrganizationChartPortletKeys;

import java.io.IOException;
import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author mdominguez
 */
@Component(
	configurationPid = OrgChartConfiguration.PID, immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.intranet",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=OrganizationChart",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + OrganizationChartPortletKeys.ORGANIZATIONCHART,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class OrganizationChartPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Listing users");
			}

			List<User> users = UserLocalServiceUtil.getUsers(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			renderRequest.setAttribute("users", users);

			String managerAttr = _orgChartConfiguration.managerAttribute();

			Map<String, String> managers = new HashMap<>();

			for (User user : users) {
				String userJobTitle = user.getJobTitle();

				String trimmedUserJobTitle = userJobTitle.trim();

				ExpandoBridge userExpandoBridge = user.getExpandoBridge();

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Printing user: " + user.getScreenName() + "-" +
							userJobTitle + ";");
				}

				Serializable userManager = userExpandoBridge.getAttribute(
					managerAttr, false);

				if (user.isActive() && !trimmedUserJobTitle.isEmpty() &&
					(userManager != null)) {

					managers.put(user.getUserId() + "", userManager.toString());
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Manager list:" + managers);
			}

			renderRequest.setAttribute("managers", managers);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		super.render(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<Object, Object> properties) {
		_orgChartConfiguration = ConfigurableUtil.createConfigurable(
			OrgChartConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationChartPortlet.class);

	private volatile OrgChartConfiguration _orgChartConfiguration;

}