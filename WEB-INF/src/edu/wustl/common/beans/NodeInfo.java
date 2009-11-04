package edu.wustl.common.beans;

import java.sql.Timestamp;

/**
 * Common Bean class for Node Info.
 *
 * @author Ravindra Jain
 *
 */
public class NodeInfo
{
    /**
     * Object Instance.
     */
    private Object object;
    /**
     * Member variable to store date of birth.
     */
    private Timestamp dateOfBirth;
    /**
     * Member variable to store the Patient DE Id.
     */
    private Long patientDEId;

    /**
     * Returns the object.
     *
     * @return the obj.
     */
    public Object getObj()
    {
        return object;
    }

    /**
     * Sets the object instance.
     *
     * @param obj
     *            the obj to set
     */
    public void setObj(Object obj)
    {
        this.object = obj;
    }

    /**
     * Returns the Date of Birth.
     *
     * @return the dob.
     */
    public Timestamp getDob()
    {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth.
     *
     * @param dob
     *            the dob to set
     */
    public void setDob(Timestamp dob)
    {
        this.dateOfBirth = dob;
    }

    /**
     * Returns the Patient DE Id.
     *
     * @return The Patient DE Id.
     */
    public Long getPatientDeid()
    {
        return patientDEId;
    }

    /**
     * Sets the patient DE id.
     *
     * @param patientDeid The Patient DE Id.
     */
    public void setPatientDeid(Long patientDeid)
    {
        this.patientDEId = patientDeid;
    }
}

