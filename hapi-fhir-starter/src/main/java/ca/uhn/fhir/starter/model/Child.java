package ca.uhn.fhir.starter.model;

import java.util.ArrayList;
import java.util.List;

import ca.uhn.fhir.model.api.IDatatype;
import ca.uhn.fhir.model.api.ResourceReference;

public class Child extends BaseElement {

	private List<SimpleSetter> mySimpleStters = new ArrayList<SimpleSetter>();

	public String getAnnotationType() {
		return getSingleType();
	}

	public String getCardMaxForChildAnnotation() {
		if (getCardMax().equals("*")) {
			return "Child.MAX_UNLIMITED";
		} else {
			return getCardMax();
		}
	}

	/**
	 * Strips off "[x]"
	 */
	public String getElementNameSimplified() {
		String elementName = getElementName();
		if (elementName.endsWith("[x]")) {
			elementName = elementName.substring(0, elementName.length() - 3);
		}
		return elementName;
	}

	public String getMethodName() {
		String elementName = getElementNameSimplified();
		elementName = elementName.substring(0, 1).toUpperCase() + elementName.substring(1);
		return elementName;
	}

	public String getReferenceType() {
		String retVal;
		if (this.isResourceRef()) {
			retVal = (ResourceReference.class.getSimpleName());
		} else if (this.getType().size() == 1) {
			retVal = getSingleType();
		} else {
			if (this instanceof Extension && ((Extension) this).getChildExtensions().size() > 0) {
				retVal = ((Extension) this).getNameType();
			} else {
				retVal = IDatatype.class.getSimpleName();
			}
		}

		if (this.isRepeatable()) {
			retVal = ("List<" + retVal + ">");
		}

		return retVal;
	}

	public String getReferenceTypeForConstructor() {
		return getReferenceType().replaceAll("^List<", "ArrayList<");
	}

	public List<String> getReferenceTypesForMultiple() {
		ArrayList<String> retVal = new ArrayList<String>();
		for (String next : getType()) {
			retVal.add(next + "Dt");
		}
		return retVal;
	}

	public List<SimpleSetter> getSimpleSetters() {
		return mySimpleStters;
	}

	public String getVariableName() {
		String elementName = getMethodName();
		return "my" + elementName;
	}

	public boolean isBlock() {
		return false;
	}

	public boolean isRepeatable() {
		return "1".equals(getCardMax()) == false;
	}

	protected String getSingleType() {
		String retVal;
		String elemName = this.getType().get(0);
		elemName = elemName.substring(0, 1).toUpperCase() + elemName.substring(1);
		if (this instanceof ResourceBlock) {
			retVal = (elemName);
		} else {
			retVal = (elemName + "Dt");
		}
		return retVal;
	}

}
