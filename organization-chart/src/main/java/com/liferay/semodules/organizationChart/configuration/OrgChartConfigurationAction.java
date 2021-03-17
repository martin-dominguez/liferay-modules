package com.liferay.semodules.organizationChart.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.semodules.organizationChart.constants.OrganizationChartPortletKeys;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author mdominguez
 */
@Component(
	configurationPid =  OrgChartConfiguration.PID,
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "javax.portlet.name=" + OrganizationChartPortletKeys.ORGANIZATIONCHART,
	service = ConfigurationAction.class
)

public class OrgChartConfigurationAction extends DefaultConfigurationAction {
	
	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		
		httpServletRequest.setAttribute(
				OrgChartConfiguration.class.getName(),
				_orgChartConfiguration);
		
		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}
	
	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {
		
		String managerAttribute = ParamUtil.getString(actionRequest, "managerAttribute");
		setPreference(actionRequest, "managerAttribute", managerAttribute);
		
		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<Object, Object> properties) {
		_orgChartConfiguration = ConfigurableUtil.createConfigurable(
				OrgChartConfiguration.class, properties);
	}
	
	private volatile OrgChartConfiguration _orgChartConfiguration;

}
