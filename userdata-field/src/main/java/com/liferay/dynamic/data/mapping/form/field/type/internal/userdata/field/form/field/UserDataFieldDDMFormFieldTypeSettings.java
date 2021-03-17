package com.liferay.dynamic.data.mapping.form.field.type.internal.userdata.field.form.field;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;

@DDMForm
@DDMFormLayout(
		paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.TABBED_MODE,
		value = {
				@DDMFormLayoutPage(
						title = "%basic",
						value = {
								@DDMFormLayoutRow(
										{
											@DDMFormLayoutColumn(
													size = 12,
													value = {
															"label", "userDataField", "required", "tip"
													}
											)
										}
								)
						}
						
				),
				@DDMFormLayoutPage(
						title = "%advanced",
						value = {
								@DDMFormLayoutRow(
										{
											@DDMFormLayoutColumn(
													size = 12,
													value = {
															"dataType", "name", "showLabel", 
															"repeatable", "type", "validation",
															"visibilityExpression"
													}
											)
										}
								)
						}
						
				)
		}
)

public interface UserDataFieldDDMFormFieldTypeSettings extends DefaultDDMFormFieldTypeSettings {
	@DDMFormField(
			label = "%user-data-field",
			predefinedValue = "name",
			optionLabels = {
					"%full-name", 
					"%first-name",
					"%middle-name",
					"%last-name",
					"%date-of-birth",
					"%email",
					"%tax-id",
					"%phone",
					"%address",
					"%street1",
					"%street2",
					"%street3",
					"%city",
					"%country-id",
					"%country-name",
					"%region-id",
					"%region-code",
					"%policy-number",
					"%postal-code"
			},
			optionValues = {
					"getFullName", 
					"getFirstName",
					"getMiddleName",
					"getLastName",
					"getDateOfBirth",
					"getEmailAddress",
					"getTaxId",
					"getPhones",
					"getAddresses",
					"getStreet1",
					"getStreet2",
					"getStreet3",
					"getCity",
					"getCountryId",
					"getCountryName",
					"getRegionId",
					"getRegionCode",
					"getPolicyNumber",
					"getPostalCode"
			},
			required = true,
			type = "select"
	)
	public String userDataField();
}
