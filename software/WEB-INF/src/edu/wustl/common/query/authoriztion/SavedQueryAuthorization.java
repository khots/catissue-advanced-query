/**
 *
 */

package edu.wustl.common.query.authoriztion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import edu.wustl.common.exception.ErrorKey;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.CsmUtility;
import edu.wustl.security.beans.SecurityDataBean;
import edu.wustl.security.exception.SMException;
import edu.wustl.security.manager.ISecurityManager;
import edu.wustl.security.manager.SecurityManagerFactory;
import edu.wustl.security.privilege.PrivilegeManager;
import edu.wustl.security.privilege.PrivilegeUtility;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.dao.ProtectionElementSearchCriteria;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;

/**
 * Class to create and insert protection elements for saved query.
 *
 * @author vijay_pande
 *
 */
public class SavedQueryAuthorization
{

    /**
     * Static constant for dynamic group index.
     */
    private static final int DYNAMIC_GRP_INDEX = 1;

    /**
     * @param protectionObjects
     *            Set of objects for which protection elements has to insert
     * @param csmUserId
     *            CSM user id
     * @param shareQuery
     *            boolean value for whether query is shared or not
     * @param user
     *            logged in user
     * @throws DAOException
     *             DAO Exception
     */
    public void authenticate(Set<ParameterizedQuery> protectionObjects,
            final String csmUserId, boolean shareQuery, User user)
            throws DAOException
    {

        try
        {
            ParameterizedQuery query = protectionObjects.iterator().next();
            PrivilegeManager privilegeManager = PrivilegeManager.getInstance();

            privilegeManager.insertAuthorizationData(getAuthorizationData(
                    query, user, shareQuery), protectionObjects,
                    getDynamicGroups(csmUserId), query.getObjectId());
            if (shareQuery)
            {
                insertProtectionElementForSharedQueries(query);
            }

        } catch (Exception e)
        {
            ErrorKey errorKey = ErrorKey.getErrorKey("db.operation.error");
            throw new DAOException(errorKey, e, e.getMessage());
        }

    }

    /**
     * @param query
     *            Object of ParameterizedQuery
     * @throws CSException
     *             Common Security Exception
     * @throws SMException
     *             Security Manager Exception
     */
    public void insertProtectionElementForSharedQueries(ParameterizedQuery query)
            throws CSException, SMException
    {
        ISecurityManager securityManager = SecurityManagerFactory
                .getSecurityManager();
        ProtectionElement protectionElement = new ProtectionElement();

        List<ProtectionElement> peList;// = new ArrayList<ProtectionElement>();
        PrivilegeUtility privilegeUtility = new PrivilegeUtility();
        protectionElement.setProtectionElementName(query.getObjectId());
        protectionElement.setApplication(privilegeUtility
                .getApplication(securityManager.getAppCtxName()));
        ProtectionElementSearchCriteria searchCriteria = new ProtectionElementSearchCriteria(
                protectionElement);
        peList = privilegeUtility.getUserProvisioningManager().getObjects(
                searchCriteria);
        if (peList != null && !peList.isEmpty())
        {
            protectionElement = peList.get(0);
        }
        privilegeUtility.getUserProvisioningManager().assignProtectionElement(
                Constants.PUBLIC_QUERY_PROTECTION_GROUP,
                protectionElement.getObjectId());

    }

    /**
     * @param csmUserId
     *            CSM user id
     * @return dynamicGroups
     */
    public String[] getDynamicGroups(String csmUserId)
    {
        String[] dynamicGroups = new String[DYNAMIC_GRP_INDEX];
        dynamicGroups[0] = CsmUtility.getUserProtectionGroup(csmUserId);

        return dynamicGroups;
    }

    /**
     * This method returns collection of UserGroupRoleProtectionGroup objects
     * that specifies the user group protection group linkage through a role. It
     * also specifies the groups the protection elements returned by this class
     * should be added to.
     *
     * @param query
     *            object of ParameterizedQuery.
     * @param user
     *            logged in user.
     * @param shareQuery
     *            boolean value for whether query is shared or not.
     * @return authorization Data.
     * @throws SMException
     *             Security Manager Exception
     * @throws CSException
     *             Common Security Exception
     */
    protected List<SecurityDataBean> getAuthorizationData(
            ParameterizedQuery query, User user, boolean shareQuery)
            throws SMException, CSException
    {
        List<SecurityDataBean> authorizationData = new Vector<SecurityDataBean>();
        Set<gov.nih.nci.security.authorization.domainobjects.User> group =
            new HashSet<gov.nih.nci.security.authorization.domainobjects.User>();
        group.add(user);

        String pgName = getUserProtectionGroup(user.getUserId().toString());
        // new String(ManagedQueryCSMUtil.getSavedQueryPGName(query.getId()));
        SecurityDataBean securityDataBean = getSaveQuerySecurityBean(user
                .getUserId().toString(), group, pgName);
        authorizationData.add(securityDataBean);

        return authorizationData;
    }

    /**
     * @param csmUserId
     *            CSM user id
     * @param group
     *            group
     * @param pgName
     *            Protection group name
     * @return securityDataBean
     */
    private SecurityDataBean getSaveQuerySecurityBean(String csmUserId,
            Set<gov.nih.nci.security.authorization.domainobjects.User> group,
            String pgName)
    {
        SecurityDataBean securityDataBean = new SecurityDataBean();
        securityDataBean.setUser(csmUserId);
        securityDataBean.setRoleName(Roles.EXECUTE_QUERY.toString());
        // securityDataBean.setProtectionGroupName(pgName);
        securityDataBean.setProtGrpName(pgName);
        securityDataBean.setGroupName(getUserProtectionGroup(csmUserId));
        securityDataBean.setGroup(group);

        return securityDataBean;
    }

    /**
     * This method returns the user protection group for the given
     * <code>csmUserId</code>.
     *
     * @param csmUserId
     *            CSM user id
     * @return User Protection Group String Value.
     */
    public String getUserProtectionGroup(String csmUserId)
    {
        return "User_" + csmUserId;
    }

    /**
     * It will check weather the given query is shared or not will return true
     * or false accordingly.
     *
     * @param query
     *            object of IParameterizedQuery
     * @return isShared
     * @throws DAOException
     *             DAO Exception
     * @throws SMException
     *             Security Manager Exception
     */
    public boolean checkIsSharedQuery(IParameterizedQuery query)
            throws DAOException, SMException
    {
        CsmUtility csmUtility = new CsmUtility();
        String objectId = ((ParameterizedQuery) query).getObjectId();
        boolean isShared = false;
        try
        {
            isShared = csmUtility.checkIsSharedQuery(objectId);
        } catch (CSObjectNotFoundException e)
        {
            ErrorKey errorKey = ErrorKey.getErrorKey("db.operation.error");
            throw new DAOException(errorKey, e, e.getMessage());
        } catch (CSException e)
        {
            ErrorKey errorKey = ErrorKey.getErrorKey("db.operation.error");
            throw new DAOException(errorKey, e, e.getMessage());
        }
        return isShared;
    }

}
