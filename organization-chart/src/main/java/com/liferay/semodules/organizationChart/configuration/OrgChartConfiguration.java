package com.liferay.semodules.organizationChart.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;
import aQute.bnd.annotation.metatype.Meta.Type;

/**
 * @author mdominguez
 */
@ExtendedObjectClassDefinition(scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE)
@Meta.OCD(id = OrgChartConfiguration.PID, localization = "content/Language", name = "configuration.name.orgchart")

public interface OrgChartConfiguration {
	public static final String PID = "com.liferay.bankingdemo.accounts.portlet.OrgChartConfiguration";
	
	@Meta.AD(deflt = "Manager", required = true, type = Type.String, name = "configuration.name.orgchart.managerattribute", description = "configuration.name.orgchart.managerattribute.description")
	public String managerAttribute();

}
