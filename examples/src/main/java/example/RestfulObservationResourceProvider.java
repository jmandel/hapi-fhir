package example;

import java.util.Collections;
import java.util.List;

import ca.uhn.fhir.model.dstu.resource.Patient;
import ca.uhn.fhir.model.dstu.valueset.AdministrativeGenderCodesEnum;
import ca.uhn.fhir.model.dstu.valueset.IdentifierUseEnum;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.model.primitive.UriDt;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.server.IResourceProvider;

//START SNIPPET: provider
/**
 * All resource providers must implement IResourceProvider
 */
public class RestfulObservationResourceProvider implements IResourceProvider {

	/**
	 * The getResourceType method comes from IResourceProvider, and must
	 * be overridden to indicate what type of resource this provider
	 * supplies.
	 */
	@Override
	public Class<Patient> getResourceType() {
		return Patient.class;
	}
	
	/**
	 * The "@Read" annotation indicates that this method supports the
	 * read operation. It takes one argument, the Resource type being returned. 
	 * 
	 * @param theId
	 *    The read operation takes one parameter, which must be of type
	 *    IdDt and must be annotated with the "@Read.IdParam" annotation.
	 * @return 
	 *    Returns a resource matching this identifier, or null if none exists.
	 */
	@Read()
	public Patient getResourceById(@IdParam IdDt theId) {
		Patient patient = new Patient();
		patient.addIdentifier();
		patient.getIdentifier().get(0).setSystem(new UriDt("urn:hapitest:mrns"));
		patient.getIdentifier().get(0).setValue("00002");
		patient.addName().addFamily("Test");
		patient.getName().get(0).addGiven("PatientOne");
		patient.setGender(AdministrativeGenderCodesEnum.F);
		return patient;
	}

	/**
	 * The "@Search" annotation indicates that this method supports the 
	 * search operation. You may have many different method annotated with 
	 * this annotation, to support many different search criteria. This
	 * example searches by family name.
	 * 
	 * @param theIdentifier
	 *    This operation takes one parameter which is the search criteria. It is
	 *    annotated with the "@Required" annotation. This annotation takes one argument,
	 *    a string containing the name of the search criteria. The datatype here
	 *    is StringDt, but there are other possible parameter types depending on the
	 *    specific search criteria.
	 * @return
	 *    This method returns a list of Patients. This list may contain multiple
	 *    matching resources, or it may also be empty.
	 */
	@Search()
	public List<Patient> getPatient(@RequiredParam(name = Patient.SP_FAMILY) StringDt theFamilyName) {
		Patient patient = new Patient();
		patient.addIdentifier();
		patient.getIdentifier().get(0).setUse(IdentifierUseEnum.OFFICIAL);
		patient.getIdentifier().get(0).setSystem(new UriDt("urn:hapitest:mrns"));
		patient.getIdentifier().get(0).setValue("00001");
		patient.addName();
		patient.getName().get(0).addFamily("Test");
		patient.getName().get(0).addGiven("PatientOne");
		patient.getGender().setText("M");
		return Collections.singletonList(patient);
	}

}
//END SNIPPET: provider
