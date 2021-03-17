package com.liferay.dynamic.data.mapping.form.field.type.internal.userdata.field.form.field;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author mdominguez
 */
@Component(
	immediate = true,
	property = {
		"ddm.form.field.type.description=userdata-field-description",
		"ddm.form.field.type.display.order:Integer=13",
		"ddm.form.field.type.group=customized",
		"ddm.form.field.type.icon=user",
		"ddm.form.field.type.label=userdata-field-label",
		"ddm.form.field.type.name=userdataField"
	},
	service = DDMFormFieldType.class
)
public class UserDataFieldDDMFormFieldType extends BaseDDMFormFieldType {
	
	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return UserDataFieldDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		return _npmResolver.resolveModuleName(
			"dynamic-data-userdata-field-form-field/userdata-field.es");
	}

	@Override
	public String getName() {
		return "userdataField";
	}

	@Override
	public boolean isCustomDDMFormFieldType() {
		return true;
	}

	@Reference
	private NPMResolver _npmResolver;

}