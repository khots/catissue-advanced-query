package edu.wustl.common.query.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.queryobject.ArithmeticOperator;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConnector;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IQueryEntity;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.Expression;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * Mock to create XQuery Objects.
 * 
 * @author ravindra_jain
 * @created 4th December, 2008
 * 
 */
public class XQueryGeneratorMock 
{
	 public static XQueryEntityManagerMock entityManager = new XQueryEntityManagerMock();
	 
    /**
     * Query for Person: PersonUpi is Not NULL
     * 
     * @param 
     * @return The Condition object.
     */
    public static ICondition createPersonCondition1(EntityInterface personEntity) 
    {
        AttributeInterface attribute = findAttribute(personEntity, "personUpi");
        ICondition condition = QueryObjectFactory.createCondition();
        condition.setRelationalOperator(RelationalOperator.IsNotNull);
        condition.setAttribute(attribute);
        return condition;
    }

    /**
     * Create Rule Person as : PersonUpi is 'Not Null'
     * 						   Default condition on 'ActiveUpiFlag'
     *                         Default condition on 'ResearchOptOut'
     * 
     * @param personEntity The Dynamic Extension Entity Person
     * @return The Rule reference.
     */
    public static IRule createPersonRule1(EntityInterface personEntity) {
        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(createPersonCondition1(personEntity));
        conditions.add(createPersonCondition3(personEntity));
        conditions.add(createPersonCondition4(personEntity));
        
        IRule rule = QueryObjectFactory.createRule(conditions);
        return rule;
    }
    
    /**
     * Query for Person: PersonUpi in values
     * 					 {000000000000000008690923	000000000000000008691120
	 *					  000000000000000008690927  000000000000000008690929}
     * 
     * @param 
     * @return The Condition object.
     */
    public static ICondition createPersonCondition2(EntityInterface personEntity, RelationalOperator relationalOperator) 
    {
    	List<String> values = new ArrayList<String>();
        values.add("000000000000000008690923");
        values.add("000000000000000008690927");
        values.add("000000000000000008691120");
        values.add("000000000000000008690929");
        AttributeInterface attribute = findAttribute(personEntity, "personUpi");

        ICondition condition = QueryObjectFactory.createCondition(attribute, relationalOperator, values);
        return condition;
    }

    
    /**
     * 
     * @param personEntity
     * @return
     */
    public static ICondition createPersonCondition3(EntityInterface personEntity) 
    {
    	 List<String> values = new ArrayList<String>();
         values.add("A");
		 AttributeInterface attribute = findAttribute(personEntity, "activeUpiFlag");
	     
		 ICondition condition = QueryObjectFactory.createCondition(attribute, RelationalOperator.Equals, values);
	     return condition;
    }
    
    
    /**
     * 
     * @param personEntity
     * @return
     */
    public static ICondition createPersonCondition4(EntityInterface personEntity) 
    {
    	 List<String> values = new ArrayList<String>();
         values.add("N");
		 AttributeInterface attribute = findAttribute(personEntity, "researchOptOut");
	     
		 ICondition condition = QueryObjectFactory.createCondition(attribute, RelationalOperator.Equals, values);
	     return condition;
    }
    
    /**
     * Create Rule Person as : PersonUpi in values
     * 					 {000000000000000008690923	000000000000000008691120
	 *					  000000000000000008690927  000000000000000008690929}
     * 
     * @param personEntity The Dynamic Extension Entity Person
     * @return The Rule reference.
     */
    public static IRule createPersonRule2(EntityInterface personEntity, RelationalOperator relationalOperator) {
        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(createPersonCondition2(personEntity, relationalOperator));
        IRule rule = QueryObjectFactory.createRule(conditions);
        return rule;
    }

    /**
     * Create Expression for given Person as: personUpi is Not Null
     * 
     * @param person The Constraint Entity object for Person
     * @return The Expression Object.
     */
    /*public static IExpression createPersonExpression1(IQueryEntity person) {
        IExpression expression = new Expression(person, 1);
        expression.addOperand(createPersonRule1(person.getDynamicExtensionsEntity()));
        return expression;
    }*/

    /**
     * To create IQuery for Person as:
     * [Person Upi is not null]
     * 
     * @param expression The Expression reference created by function
     * @return The corresponding join Graph reference.
     */
    public static IQuery createQuery1() {
        IQuery query = null;
        entityManager = new XQueryEntityManagerMock(); //new EntityManagerMock();
        try {

            query = QueryObjectFactory.createQuery();
            IConstraints constraints = QueryObjectFactory.createConstraints();
            query.setConstraints(constraints);

            // creating Person Expression.
            EntityInterface personEntity = entityManager.getEntityByName(XQueryEntityManagerMock.PERSON);
            IQueryEntity personConstraintEntity = QueryObjectFactory.createQueryEntity(personEntity);
            IExpression personExpression = constraints.addExpression(personConstraintEntity);
            personExpression.addOperand(createPersonRule1(personEntity));

            personExpression.setInView(true);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }
    
    
    /**
     * Query for Person: Condition on Demographics
     * 					 DateOfBirth > someDate
     * @param 
     * @return The Condition object.
     */
    public static ICondition createDemographicsCondition1(EntityInterface demographicsEntity) 
    {
    	List<String> values = new ArrayList<String>();
        // values.add("1900-10-10T11:11:11");
    	// values.add("10/10/1900 11:11:11");
    // Format MM/dd/yyyy HH:mm:ss
    	values.add("10/10/1920");
        AttributeInterface attribute = findAttribute(demographicsEntity, "dateOfBirth");
        ICondition condition = QueryObjectFactory.createCondition(attribute, RelationalOperator.GreaterThan, values);
        
        return condition;
    }

    /**
     * Query for Person: Condition on Demographics
     * 					 DateOfBirth < someDate
     * @param 
     * @return The Condition object.
     */
    public static ICondition createDemographicsCondition2(EntityInterface demographicsEntity) 
    {
    	List<String> values = new ArrayList<String>();
    // Format MM/dd/yyyy HH:mm:ss
    	values.add("10/10/1920");
        AttributeInterface attribute = findAttribute(demographicsEntity, "dateOfBirth");
        ICondition condition = QueryObjectFactory.createCondition(attribute, RelationalOperator.LessThan, values);
        
        return condition;
    }
    
    /**
     * Query for Person: Condition on Demographics
     * 					 Demographics : dateOfBirth between
     * 							1940-01-01	and
	 *    						1950-01-01
     * @param 
     * @return The Condition object.
     */
    public static ICondition createDemographicsCondition3(EntityInterface demographicsEntity) 
    {
    	List<String> values = new ArrayList<String>();
    	values.add("01/01/1940");
    	values.add("01/01/1950");
        AttributeInterface attribute = findAttribute(demographicsEntity, "dateOfBirth");
        ICondition condition = QueryObjectFactory.createCondition(attribute, RelationalOperator.Between, values);
        
        return condition;
    }
    
    /**
     * CONDITION on person & demographics - using STARTS WITH relational operator
     * Query for Person: PersonUpi is NOT NULL &
     * 			 Demographics : socialSecurityNumber starts with '0'
     * 
     * @param 
     * @return The Condition object.
     */
    public static ICondition createDemographicsCondition4(EntityInterface demographicsEntity) 
    {
    	List<String> values = new ArrayList<String>();
    	values.add("0");
        AttributeInterface attribute = findAttribute(demographicsEntity, "socialSecurityNumber");
        ICondition condition = QueryObjectFactory.createCondition(attribute, RelationalOperator.StartsWith, values);
        
        return condition;
    }
    
    
    /**
     * Query for Person: Condition on Demographics
     * 					 Demographics : dateOfBirth between
     * 							1912-01-01	and
	 *    						1912-04-01
     * @param 
     * @return The Condition object.
     */
    public static ICondition createDemographicsCondition5(EntityInterface demographicsEntity) 
    {
    	List<String> values = new ArrayList<String>();
    	// Format MM/dd/yyyy HH:mm:ss
    	values.add("01/25/2009");
        AttributeInterface attribute = findAttribute(demographicsEntity, "effectiveEndTimeStamp");
        ICondition condition = QueryObjectFactory.createCondition(attribute, RelationalOperator.GreaterThan, values);
        
        return condition;
    }
    
    
    /**
     * Query for Person: Condition on Demographics
     * 					 Demographics : dateOfBirth between
     * 							1912-01-01	and
	 *    						1912-04-01
     * @param 
     * @return The Condition object.
     */
    public static ICondition createDemographicsCondition6(EntityInterface demographicsEntity) 
    {
    	// Format MM/dd/yyyy HH:mm:ss
    	List<String> values = new ArrayList<String>();
    	values.add("01/25/1991");
        AttributeInterface attribute = findAttribute(demographicsEntity, "dateOfBirth");
        ICondition condition = QueryObjectFactory.createCondition(attribute, RelationalOperator.LessThan, values);
        
        return condition;
    }
    
    
    /**
     * Query for Person: Condition on Demographics
     * 					 Demographics : dateOfBirth between
     * 							1912-01-01	and
	 *    						1912-04-01
     * @param 
     * @return The Condition object.
     */
    public static ICondition createDemographicsCondition7(EntityInterface demographicsEntity) 
    {
    	// Format MM/dd/yyyy HH:mm:ss
    	List<String> values = new ArrayList<String>();
    	values.add("01/25/2009");
        AttributeInterface attribute = findAttribute(demographicsEntity, "effectiveStartTimeStamp");
        ICondition condition = QueryObjectFactory.createCondition(attribute, RelationalOperator.LessThan, values);
        
        return condition;
    }
    
    
    /**
     * Create Rule on Demographics as : DateOfBirth > someDate
     * 
     * @param personEntity The Dynamic Extension Entity Demographics
     * @return The Rule reference.
     */
    public static IRule createDemographicsRule1(EntityInterface demographicsEntity) {
        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(createDemographicsCondition1(demographicsEntity));
        conditions.add(createDemographicsCondition5(demographicsEntity));
        conditions.add(createDemographicsCondition6(demographicsEntity));
        conditions.add(createDemographicsCondition7(demographicsEntity));
        
        IRule rule = QueryObjectFactory.createRule(conditions);
        return rule;
    }
    
    /**
     * Create Rule on Demographics as : DateOfBirth < someDate
     * 
     * @param personEntity The Dynamic Extension Entity Demographics
     * @return The Rule reference.
     */
    public static IRule createDemographicsRule2(EntityInterface demographicsEntity) {
        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(createDemographicsCondition2(demographicsEntity));
        IRule rule = QueryObjectFactory.createRule(conditions);
        return rule;
    }

    /**
     * Create Rule on Demographics : dateOfBirth between
     * 								1940-01-01	and
	 *    							1950-01-01
     * 
     * @param personEntity The Dynamic Extension Entity Demographics
     * @return The Rule reference.
     */
    public static IRule createDemographicsRule3(EntityInterface demographicsEntity) {
        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(createDemographicsCondition3(demographicsEntity));
        conditions.add(createDemographicsCondition5(demographicsEntity));
        conditions.add(createDemographicsCondition6(demographicsEntity));
        conditions.add(createDemographicsCondition7(demographicsEntity));
        
        IRule rule = QueryObjectFactory.createRule(conditions);
        return rule;
    }
    
    /**
     * RULE on person & demographics - using STARTS WITH relational operator
     * Query for Person: PersonUpi is NOT NULL &
     * 			 Demographics : socialSecurityNumber starts with '0'
     * 
     * @param personEntity The Dynamic Extension Entity Demographics
     * @return The Rule reference.
     */
    public static IRule createDemographicsRule4(EntityInterface demographicsEntity) {
        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(createDemographicsCondition4(demographicsEntity));
        IRule rule = QueryObjectFactory.createRule(conditions);
        return rule;
    }
    
    /**
     * Create Expression for given Person as: personUpi is Not Null
     * 
     * @param person The Constraint Entity object for Person
     * @return The Expression Object.
     */
   /* public static IExpression createDemographicsExpression1(IQueryEntity demographics) {
        IExpression expression = new Expression(demographics, 2);
        expression.addOperand(createDemographicsRule1(demographics.getDynamicExtensionsEntity()));
        return expression;
    }*/

    
    /**
     * Query for Lab Procedure: 
     * 				 			Accession number is NOT NULL
     * @param 
     * @return The Condition object.
     */
    public static ICondition createLabProcedureCondition1(EntityInterface labProcedureEntity) 
    {
    	 AttributeInterface attribute = findAttribute(labProcedureEntity, "accessionNumber");
         ICondition condition = QueryObjectFactory.createCondition();
         condition.setRelationalOperator(RelationalOperator.IsNotNull);
         condition.setAttribute(attribute);
         return condition;
    }
    
    /**
     * Create Rule on Lab as : Accession number is NOT NULL
     * 
     * @param personEntity The Dynamic Extension Entity Lab
     * @return The Rule reference.
     */
    public static IRule createLabProcedureRule1(EntityInterface labProcedureEntity) {
        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(createLabProcedureCondition1(labProcedureEntity));
        IRule rule = QueryObjectFactory.createRule(conditions);
        return rule;
    }
    
    /**
     * To create IQuery for Person & Demographics as:
     * [Person Upi is not null]
     * 
     * @param expression The Expression reference created by function
     * @return The corresponding join Graph reference.
     */
    public static IQuery createQuery2() {
        IQuery query = null;
        entityManager = new XQueryEntityManagerMock(); //new EntityManagerMock();
        try 
        {
            query = QueryObjectFactory.createParameterizedQuery();
            IConstraints constraints = QueryObjectFactory.createConstraints();
            query.setConstraints(constraints);
            IJoinGraph joinGraph = constraints.getJoinGraph();
            IConnector<LogicalOperator> andConnector = QueryObjectFactory.createLogicalConnector(LogicalOperator.And);
            
            // creating Person Expression.
            EntityInterface personEntity = entityManager.getEntityByName(XQueryEntityManagerMock.PERSON);
            IQueryEntity personConstraintEntity = QueryObjectFactory.createQueryEntity(personEntity);
            IExpression personExpression = constraints.addExpression(personConstraintEntity);
            personExpression.addOperand(createPersonRule1(personEntity));

            // creating Demographics Expression.
            EntityInterface demographicsEntity = entityManager.getEntityByName(XQueryEntityManagerMock.DEMOGRAPHICS);
            IQueryEntity demographicsConstraintEntity = QueryObjectFactory.createQueryEntity(demographicsEntity);
            IExpression demographicsExpression = constraints.addExpression(demographicsConstraintEntity);
            personExpression.addOperand(andConnector, demographicsExpression);
            demographicsExpression.addOperand(createDemographicsRule1(demographicsEntity));

            // Adding association to Join graph.
            AssociationInterface personAndDemographicsAssociation = getAssociationFrom(entityManager.getAssociations(
                    personEntity.getId(), demographicsEntity.getId()), XQueryEntityManagerMock.DEMOGRAPHICS);
            IIntraModelAssociation association = QueryObjectFactory
                    .createIntraModelAssociation(personAndDemographicsAssociation);
            joinGraph.putAssociation(personExpression, demographicsExpression,
                    association);
            
            // Populate Output Attribute List
            updateQueryWithSelectedAttributes((IParameterizedQuery)query);
            
            // // creating output tree.
            personExpression.setInView(true);
            demographicsExpression.setInView(true);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }
    
    
    /**
     * Query for Race: Race/id equals 3452
     * 
     * @param 
     * @return The Condition object.
     */
    public static ICondition createRaceCondition1(EntityInterface raceEntity) 
    {
        List<String> values = new ArrayList<String>();
        values.add("3452");
        AttributeInterface attribute = findAttribute(raceEntity, "id");
        ICondition condition = QueryObjectFactory.createCondition(attribute, RelationalOperator.Equals, values);
        return condition;
    }

    /**
     * Create Rule Race: Race/id equals 3452
     * 
     * @param personEntity The Dynamic Extension Entity Person
     * @return The Rule reference.
     */
    public static IRule createRaceRule1(EntityInterface raceEntity) {
        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(createRaceCondition1(raceEntity));
        IRule rule = QueryObjectFactory.createRule(conditions);
        return rule;
    }

    /**
     * Create Expression for given Race as: Race/id equals 3452
     * 
     * @param person The Constraint Entity object for Race
     * @return The Expression Object.
     */
    public static IExpression creatRaceExpression1(IQueryEntity race) {
        IExpression expression = new Expression(race, 1);
        expression.addOperand(createRaceRule1(race.getDynamicExtensionsEntity()));
        return expression;
    }
    
    
    /**
     * Query for Gender: gender/id equals 1987
     * 
     * @param 
     * @return The Condition object.
     */
    public static ICondition createGenderCondition1(EntityInterface genderEntity) 
    {
        List<String> values = new ArrayList<String>();
        values.add("1987");
        AttributeInterface attribute = findAttribute(genderEntity, "id");
        ICondition condition = QueryObjectFactory.createCondition(attribute, RelationalOperator.Equals, values);
        return condition;
    }

    /**
     * Create Rule Gender: gender/id equals 1687
     * 
     * @param personEntity The Dynamic Extension Entity Person
     * @return The Rule reference.
     */
    public static IRule createGenderRule1(EntityInterface genderEntity) {
        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(createGenderCondition1(genderEntity));
        IRule rule = QueryObjectFactory.createRule(conditions);
        return rule;
    }

    /**
     * Create Expression for given Gender: gender/id equals 1687
     * 
     * @param person The Constraint Entity object for Race
     * @return The Expression Object.
     */
    public static IExpression createGenderExpression1(IQueryEntity gender) {
        IExpression expression = new Expression(gender, 1);
        expression.addOperand(createGenderRule1(gender.getDynamicExtensionsEntity()));
        return expression;
    }
    
    
    /**
     * To create IQuery for:
     * Query on person & demographics & race & gender
     * Query for Person: PersonUpi is Not NULL
     * 					 DateOfBirth > someDate
     * 					 RaceId = 3452
     * 					 Gender = 1987
     * @param expression The Expression reference created by function
     * @return The corresponding join Graph reference.
     */
    public static IQuery createQuery3() {
        IQuery query = null;
        entityManager = new XQueryEntityManagerMock(); //new EntityManagerMock();
        try 
        {
            query = QueryObjectFactory.createQuery();
            IConstraints constraints = QueryObjectFactory.createConstraints();
            query.setConstraints(constraints);
            IJoinGraph joinGraph = constraints.getJoinGraph();
            IConnector<LogicalOperator> andConnector = QueryObjectFactory.createLogicalConnector(LogicalOperator.And);

            // creating Person Expression.
            EntityInterface personEntity = entityManager.getEntityByName(XQueryEntityManagerMock.PERSON);
            IQueryEntity personConstraintEntity = QueryObjectFactory.createQueryEntity(personEntity);
            IExpression personExpression = constraints.addExpression(personConstraintEntity);
            personExpression.addOperand(createPersonRule1(personEntity));

            // creating Demographics Expression.
            EntityInterface demographicsEntity = entityManager.getEntityByName(XQueryEntityManagerMock.DEMOGRAPHICS);
            IQueryEntity demographicsConstraintEntity = QueryObjectFactory.createQueryEntity(demographicsEntity);
            IExpression demographicsExpression = constraints.addExpression(demographicsConstraintEntity);
            personExpression.addOperand(andConnector, demographicsExpression);
            demographicsExpression.addOperand(createDemographicsRule1(demographicsEntity));
            
            // creating Race Expression.
            EntityInterface raceEntity = entityManager.getEntityByName(XQueryEntityManagerMock.RACE);
            IQueryEntity raceConstraintEntity = QueryObjectFactory.createQueryEntity(raceEntity);
            IExpression raceExpression = constraints.addExpression(raceConstraintEntity);
            demographicsExpression.addOperand(andConnector, raceExpression);
            raceExpression.addOperand(createRaceRule1(raceEntity));
            
            // creating Gender Expression.
            EntityInterface genderEntity = entityManager.getEntityByName(XQueryEntityManagerMock.GENDER);
            IQueryEntity genderConstraintEntity = QueryObjectFactory.createQueryEntity(genderEntity);
            IExpression genderExpression = constraints.addExpression(genderConstraintEntity);
            demographicsExpression.addOperand(andConnector, genderExpression);
            genderExpression.addOperand(createGenderRule1(genderEntity));
            
            // Adding association to Join graph.
            AssociationInterface personAndDemographicsAssociation = getAssociationFrom(entityManager.getAssociations(
                    personEntity.getId(), demographicsEntity.getId()), XQueryEntityManagerMock.DEMOGRAPHICS);
            IIntraModelAssociation association1 = QueryObjectFactory
                    .createIntraModelAssociation(personAndDemographicsAssociation);
            joinGraph.putAssociation(personExpression, demographicsExpression,
            		association1);

            
            AssociationInterface demographicsAndRaceAssociation = getAssociationFrom(entityManager.getAssociations(
                    demographicsEntity.getId(), raceEntity.getId()), XQueryEntityManagerMock.RACE);
            IIntraModelAssociation association2 = QueryObjectFactory
                    .createIntraModelAssociation(demographicsAndRaceAssociation);
            joinGraph.putAssociation(demographicsExpression, raceExpression,
            		association2);
            
            
            AssociationInterface demographicsAndGenderAssociation = getAssociationFrom(entityManager.getAssociations(
                    demographicsEntity.getId(), genderEntity.getId()), XQueryEntityManagerMock.GENDER);
            IIntraModelAssociation association3 = QueryObjectFactory
                    .createIntraModelAssociation(demographicsAndGenderAssociation);
            joinGraph.putAssociation(demographicsExpression, genderExpression,
            		association3);
            
            // // creating output tree.
            personExpression.setInView(true);
            demographicsExpression.setInView(true);
            raceExpression.setInView(true);
            genderExpression.setInView(true);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }
    
    
    /**
     * To create IQuery for:
     * Query on person & demographics & race & gender
     * Query for Person: PersonUpi is Not NULL
     * 					 DateOfBirth > someDate
     * 					 RaceId = 3452
     * @param expression The Expression reference created by function
     * @return The corresponding join Graph reference.
     */
    public static IQuery createQuery4() {
        IQuery query = null;
        entityManager = new XQueryEntityManagerMock(); //new EntityManagerMock();
        try 
        {
            query = QueryObjectFactory.createQuery();
            IConstraints constraints = QueryObjectFactory.createConstraints();
            query.setConstraints(constraints);
            IJoinGraph joinGraph = constraints.getJoinGraph();
            IConnector<LogicalOperator> andConnector = QueryObjectFactory.createLogicalConnector(LogicalOperator.And);

            // creating Person Expression.
            EntityInterface personEntity = entityManager.getEntityByName(XQueryEntityManagerMock.PERSON);
            IQueryEntity personConstraintEntity = QueryObjectFactory.createQueryEntity(personEntity);
            IExpression personExpression = constraints.addExpression(personConstraintEntity);
            personExpression.addOperand(createPersonRule1(personEntity));

            // creating Demographics Expression.
            EntityInterface demographicsEntity = entityManager.getEntityByName(XQueryEntityManagerMock.DEMOGRAPHICS);
            IQueryEntity demographicsConstraintEntity = QueryObjectFactory.createQueryEntity(demographicsEntity);
            IExpression demographicsExpression = constraints.addExpression(demographicsConstraintEntity);
            personExpression.addOperand(andConnector, demographicsExpression);
            demographicsExpression.addOperand(createDemographicsRule1(demographicsEntity));
            
            AssociationInterface personAndDemographicsAssociation = getAssociationFrom(entityManager.getAssociations(
                    personEntity.getId(), demographicsEntity.getId()), XQueryEntityManagerMock.DEMOGRAPHICS);
            IIntraModelAssociation association1 = QueryObjectFactory
                    .createIntraModelAssociation(personAndDemographicsAssociation);
            joinGraph.putAssociation(personExpression, demographicsExpression,
            		association1);
            
            // creating Race Expression.
//            EntityInterface raceEntity = entityManager.getEntityByName(XQueryEntityManagerMock.RACE);
//            IQueryEntity raceConstraintEntity = QueryObjectFactory.createQueryEntity(raceEntity);
//            IExpression raceExpression = constraints.addExpression(raceConstraintEntity);
//            demographicsExpression.addOperand(andConnector, raceExpression);
//            raceExpression.addOperand(createRaceRule1(raceEntity));
//
//            
//            AssociationInterface demographicsAndRaceAssociation = getAssociationFrom(entityManager.getAssociations(
//                    XQueryEntityManagerMock.DEMOGRAPHICS, XQueryEntityManagerMock.RACE), XQueryEntityManagerMock.RACE);
//            IIntraModelAssociation association2 = QueryObjectFactory
//                    .createIntraModelAssociation(demographicsAndRaceAssociation);
//            joinGraph.putAssociation(demographicsExpression, raceExpression,
//            		association2);
            
            // creating Gender Expression.
            EntityInterface genderEntity = entityManager.getEntityByName(XQueryEntityManagerMock.GENDER);
            IQueryEntity genderConstraintEntity = QueryObjectFactory.createQueryEntity(genderEntity);
            IExpression genderExpression = constraints.addExpression(genderConstraintEntity);
            demographicsExpression.addOperand(andConnector, genderExpression);
            genderExpression.addOperand(createGenderRule1(genderEntity));
            
            // Adding association to Join graph.
            AssociationInterface demographicsAndGenderAssociation = getAssociationFrom(entityManager.getAssociations(
                    demographicsEntity.getId(), genderEntity.getId()), XQueryEntityManagerMock.GENDER);
            IIntraModelAssociation association3 = QueryObjectFactory
                    .createIntraModelAssociation(demographicsAndGenderAssociation);
            joinGraph.putAssociation(demographicsExpression, genderExpression,
            		association3);
            
            // // creating output tree.
         // raceExpression.setInView(true);
            personExpression.setInView(true);
            demographicsExpression.setInView(true);
            genderExpression.setInView(true);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }
    
    /**
     * To create IQuery for Person as:
     * [Person Upi is not null]
     * DateOfBirth < someDate
     * 
     * @return The corresponding IQuery
     */
    public static IQuery createQuery5() {
        IQuery query = null;
        entityManager = new XQueryEntityManagerMock(); //new EntityManagerMock();
        try 
        {
            query = QueryObjectFactory.createQuery();
            IConstraints constraints = QueryObjectFactory.createConstraints();
            query.setConstraints(constraints);
            IJoinGraph joinGraph = constraints.getJoinGraph();
            IConnector<LogicalOperator> andConnector = QueryObjectFactory.createLogicalConnector(LogicalOperator.And);

            // creating Person Expression.
            EntityInterface personEntity = entityManager.getEntityByName(XQueryEntityManagerMock.PERSON);
            IQueryEntity personConstraintEntity = QueryObjectFactory.createQueryEntity(personEntity);
            IExpression personExpression = constraints.addExpression(personConstraintEntity);
            personExpression.addOperand(createPersonRule1(personEntity));

            // creating Demographics Expression.
            EntityInterface demographicsEntity = entityManager.getEntityByName(XQueryEntityManagerMock.DEMOGRAPHICS);
            IQueryEntity demographicsConstraintEntity = QueryObjectFactory.createQueryEntity(demographicsEntity);
            IExpression demographicsExpression = constraints.addExpression(demographicsConstraintEntity);
            personExpression.addOperand(andConnector, demographicsExpression);
            demographicsExpression.addOperand(createDemographicsRule2(demographicsEntity));

            // Adding association to Join graph.
            AssociationInterface personAndDemographicsAssociation = getAssociationFrom(entityManager.getAssociations(
                    personEntity.getId(), demographicsEntity.getId()), XQueryEntityManagerMock.DEMOGRAPHICS);
            IIntraModelAssociation association = QueryObjectFactory
                    .createIntraModelAssociation(personAndDemographicsAssociation);
            joinGraph.putAssociation(personExpression, demographicsExpression,
                    association);

            // // creating output tree.
            personExpression.setInView(true);
            demographicsExpression.setInView(true);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }
    
    
    /**
     * To create IQuery for Person as:
     * Query on person - using IN relational operator
     * Query for Person: PersonUpi in values
     * 					 {000000000000000008690923	000000000000000008691120
	 *					  000000000000000008690927  000000000000000008690929}
     * 
     * @return The corresponding IQuery
     */
    public static IQuery createQuery6() {
        IQuery query = null;
        entityManager = new XQueryEntityManagerMock();
        try 
        {
            query = QueryObjectFactory.createQuery();
            IConstraints constraints = QueryObjectFactory.createConstraints();
            query.setConstraints(constraints);

            // creating Person Expression.
            EntityInterface personEntity = entityManager.getEntityByName(XQueryEntityManagerMock.PERSON);
            IQueryEntity personConstraintEntity = QueryObjectFactory.createQueryEntity(personEntity);
            IExpression personExpression = constraints.addExpression(personConstraintEntity);
            personExpression.addOperand(createPersonRule2(personEntity, RelationalOperator.In));

            // // creating output tree.
            personExpression.setInView(true);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }
    
    
    /**
     * To create IQuery for Person as:
     * Query on person - using NOT IN relational operator
     * Query for Person: PersonUpi NOT IN values
     * 					 {000000000000000008690923	000000000000000008691120
	 *					  000000000000000008690927  000000000000000008690929}
     * 
     * @return The corresponding IQuery
     */
    public static IQuery createQuery7() {
        IQuery query = null;
        entityManager = new XQueryEntityManagerMock();
        try 
        {
            query = QueryObjectFactory.createQuery();
            IConstraints constraints = QueryObjectFactory.createConstraints();
            query.setConstraints(constraints);

            // creating Person Expression.
            EntityInterface personEntity = entityManager.getEntityByName(XQueryEntityManagerMock.PERSON);
            IQueryEntity personConstraintEntity = QueryObjectFactory.createQueryEntity(personEntity);
            IExpression personExpression = constraints.addExpression(personConstraintEntity);
            personExpression.addOperand(createPersonRule2(personEntity, RelationalOperator.NotIn));

            // // creating output tree.
            personExpression.setInView(true);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }
    
    
    /**
     * To create IQuery for Person as:
     * Query on person - using CONTAINS relational operator
     * Query for Person: PersonUpi CONTAINS values
     * 					 {000000000000000008690923}
     * 
     * @return The corresponding IQuery
     */
    public static IQuery createQuery8() {
        IQuery query = null;
        entityManager = new XQueryEntityManagerMock();
        try 
        {
            query = QueryObjectFactory.createQuery();
            IConstraints constraints = QueryObjectFactory.createConstraints();
            query.setConstraints(constraints);

            // creating Person Expression.
            EntityInterface personEntity = entityManager.getEntityByName(XQueryEntityManagerMock.PERSON);
            IQueryEntity personConstraintEntity = QueryObjectFactory.createQueryEntity(personEntity);
            IExpression personExpression = constraints.addExpression(personConstraintEntity);
            personExpression.addOperand(createPersonRule2(personEntity, RelationalOperator.Contains));

            // // creating output tree.
            personExpression.setInView(true);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }
    
    
    /**
     * To create IQuery for Query as:
     * Query on person & demographics - using BETWEEN relational operator
     * Query for Person: PersonUpi is NOT NULL &
     * 			 Demographics : dateOfBirth between
     * 							1912-01-01	and
	 *    						1912-04-01
     * 
     * @return The corresponding IQuery
     */
    public static IQuery createQuery9() {
        IQuery query = null;
        entityManager = new XQueryEntityManagerMock();
        try 
        {
            query = QueryObjectFactory.createQuery();
            IConstraints constraints = QueryObjectFactory.createConstraints();
            query.setConstraints(constraints);
            IJoinGraph joinGraph = constraints.getJoinGraph();
            IConnector<LogicalOperator> andConnector = QueryObjectFactory.createLogicalConnector(LogicalOperator.And);

            // creating Person Expression.
            EntityInterface personEntity = entityManager.getEntityByName(XQueryEntityManagerMock.PERSON);
            IQueryEntity personConstraintEntity = QueryObjectFactory.createQueryEntity(personEntity);
            IExpression personExpression = constraints.addExpression(personConstraintEntity);
            personExpression.addOperand(createPersonRule1(personEntity));

            // creating Demographics Expression.
            EntityInterface demographicsEntity = entityManager.getEntityByName(XQueryEntityManagerMock.DEMOGRAPHICS);
            IQueryEntity demographicsConstraintEntity = QueryObjectFactory.createQueryEntity(demographicsEntity);
            IExpression demographicsExpression = constraints.addExpression(demographicsConstraintEntity);
            personExpression.addOperand(andConnector, demographicsExpression);
            demographicsExpression.addOperand(createDemographicsRule3(demographicsEntity));

            // Adding association to Join graph.
            AssociationInterface personAndDemographicsAssociation = getAssociationFrom(entityManager.getAssociations(
                    personEntity.getId(), demographicsEntity.getId()), XQueryEntityManagerMock.DEMOGRAPHICS);
            IIntraModelAssociation association = QueryObjectFactory
                    .createIntraModelAssociation(personAndDemographicsAssociation);
            joinGraph.putAssociation(personExpression, demographicsExpression,
                    association);

            // // creating output tree.
            personExpression.setInView(true);
            demographicsExpression.setInView(true);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }
    
    
    /**
     * To create IQuery for Query as:
     * Query on person & demographics - using STARTS WITH relational operator
     * Query for Person: PersonUpi is NOT NULL &
     * 			 Demographics : socialSecurityNumber starts with '0'
     * 
     * @return The corresponding IQuery
     */
    public static IQuery createQuery10() {
        IQuery query = null;
        entityManager = new XQueryEntityManagerMock();
        try 
        {
            query = QueryObjectFactory.createQuery();
            IConstraints constraints = QueryObjectFactory.createConstraints();
            query.setConstraints(constraints);
            IJoinGraph joinGraph = constraints.getJoinGraph();
            IConnector<LogicalOperator> andConnector = QueryObjectFactory.createLogicalConnector(LogicalOperator.And);

            // creating Person Expression.
            EntityInterface personEntity = entityManager.getEntityByName(XQueryEntityManagerMock.PERSON);
            IQueryEntity personConstraintEntity = QueryObjectFactory.createQueryEntity(personEntity);
            IExpression personExpression = constraints.addExpression(personConstraintEntity);
            personExpression.addOperand(createPersonRule1(personEntity));

            // creating Demographics Expression.
            EntityInterface demographicsEntity = entityManager.getEntityByName(XQueryEntityManagerMock.DEMOGRAPHICS);
            IQueryEntity demographicsConstraintEntity = QueryObjectFactory.createQueryEntity(demographicsEntity);
            IExpression demographicsExpression = constraints.addExpression(demographicsConstraintEntity);
            personExpression.addOperand(andConnector, demographicsExpression);
            demographicsExpression.addOperand(createDemographicsRule4(demographicsEntity));

            // Adding association to Join graph.
            AssociationInterface personAndDemographicsAssociation = getAssociationFrom(entityManager.getAssociations(
                    personEntity.getId(), demographicsEntity.getId()), XQueryEntityManagerMock.DEMOGRAPHICS);
            IIntraModelAssociation association = QueryObjectFactory
                    .createIntraModelAssociation(personAndDemographicsAssociation);
            joinGraph.putAssociation(personExpression, demographicsExpression,
                    association);

            // // creating output tree.
            personExpression.setInView(true);
            demographicsExpression.setInView(true);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }
    
    
    /**
     * To create IQuery for Query as:
     * Query on Person & Lab
     * Query for Person: PersonUpi is NOT NULL &
     * 			 Lab : accessionNumber is NOT NULL
     * 
     * @return The corresponding IQuery
     */
    public static IQuery createQuery11() {
        IQuery query = null;
        entityManager = new XQueryEntityManagerMock();
        try 
        {
            query = QueryObjectFactory.createQuery();
            IConstraints constraints = QueryObjectFactory.createConstraints();
            query.setConstraints(constraints);
            IJoinGraph joinGraph = constraints.getJoinGraph();
            IConnector<LogicalOperator> andConnector = QueryObjectFactory.createLogicalConnector(LogicalOperator.And);

            // creating Person Expression.
            EntityInterface personEntity = entityManager.getEntityByName(XQueryEntityManagerMock.PERSON);
            IQueryEntity personConstraintEntity = QueryObjectFactory.createQueryEntity(personEntity);
            IExpression personExpression = constraints.addExpression(personConstraintEntity);
            personExpression.addOperand(createPersonRule1(personEntity));

            // creating Lab Expression.
            EntityInterface labProcedureEntity = entityManager.getEntityByName(XQueryEntityManagerMock.LABORATORY_PROCEDURE);
            IQueryEntity labProcedureConstraintEntity = QueryObjectFactory.createQueryEntity(labProcedureEntity);
            IExpression labProcedureExpression = constraints.addExpression(labProcedureConstraintEntity);
            personExpression.addOperand(andConnector, labProcedureExpression);
            labProcedureExpression.addOperand(createLabProcedureRule1(labProcedureEntity));

            // Adding association to Join graph.
            AssociationInterface personAndLabProcedureAssociation = getAssociationFrom(entityManager.getAssociations(
                    personEntity.getId(), labProcedureEntity.getId()), XQueryEntityManagerMock.LABORATORY_PROCEDURE);
            IIntraModelAssociation association = QueryObjectFactory
                    .createIntraModelAssociation(personAndLabProcedureAssociation);
            joinGraph.putAssociation(personExpression, labProcedureExpression,
                    association);

            // // creating output tree.
            personExpression.setInView(true);
            labProcedureExpression.setInView(true);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }
    

    /**
     * To search attribute in the Entity.
     * 
     * @param entity The Dynamic Extension Entity
     * @param attributeName The name of the attribute to search.
     * @return The corresponding attribute.
     */
    private static AttributeInterface findAttribute(EntityInterface entity, String attributeName) {
        Collection<AttributeInterface> attributes = entity.getEntityAttributesForQuery();
        for (AttributeInterface attribute : attributes) {
            if (attribute.getName().equals(attributeName))
                return attribute;
        }
        return null;
    }
    
    /**
     * To set all Expressions in the view.
     * 
     * @param constraints The reference to constraints object.
     */
    private static void setAllExpressionInView(IConstraints constraints) {
        for(IExpression expression : constraints) {
            expression.setInView(true);
        }
    }

    public static AssociationInterface getAssociationFrom(Collection<AssociationInterface> associations,
            String targetEntityName) {
        for (Iterator<AssociationInterface> iter = associations.iterator(); iter.hasNext();) {
            AssociationInterface theAssociation = iter.next();
            if (theAssociation == null || theAssociation.getTargetEntity() == null) {
                System.out.println("FFOOOOO");
            }
            if (theAssociation.getTargetEntity().getName().equals(targetEntityName)) {
                return theAssociation;
            }
        }
        return null;
    }

    /**
     * To instantiate Logical connector for OR operator.
     * 
     * @return reference to logical connector contining 'OR' logical operator.
     */
    private static IConnector<LogicalOperator> getOrConnector() {
        return QueryObjectFactory.createLogicalConnector(LogicalOperator.Or);
    }

    /**
     * To instantiate Logical connector for AND operator.
     * 
     * @return reference to logical connector contining 'AND' logical operator.
     */
    private static IConnector<LogicalOperator> getAndConnector() {
        return QueryObjectFactory.createLogicalConnector(LogicalOperator.And);
    }

    private static IConnector<ArithmeticOperator> conn(ArithmeticOperator oper) {
        return QueryObjectFactory.createArithmeticConnector(oper);
    }

    private static IQueryEntity newEntity(String name) {
       //  entityManager = new EntityManagerMock();
    	 entityManager = new XQueryEntityManagerMock();
        try {
            EntityInterface entity = entityManager.getEntityByName(name);
            return QueryObjectFactory.createQueryEntity(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /**
	 * 
	 * @param query
	 * @throws QueryModuleException
	 */
	private static void updateQueryWithSelectedAttributes(IParameterizedQuery query)throws QueryModuleException
	{
		List<IOutputAttribute> selectedOutputAttributeList = new ArrayList<IOutputAttribute>();
		IParameterizedQuery parameterizedQuery = (IParameterizedQuery) query;
		IConstraints constraints = parameterizedQuery.getConstraints();
		IExpression rootExpression;
		try
		{
			rootExpression = constraints.getRootExpression();

			Collection<AttributeInterface> allAttributes = rootExpression.getQueryEntity()
					.getDynamicExtensionsEntity().getPrimarykeyAttributeCollectionInSameEntity();
			for (AttributeInterface attributeInterface : allAttributes)
			{
					IOutputAttribute createOutputAttribute = QueryObjectFactory
							.createOutputAttribute(rootExpression, attributeInterface);
					selectedOutputAttributeList.add(createOutputAttribute);
			}
			parameterizedQuery.setOutputAttributeList(selectedOutputAttributeList);
		}
		catch (MultipleRootsException e)
		{
			QueryModuleException queryModuleException = new QueryModuleException(e.getMessage(),QueryModuleError.MULTIPLE_ROOT);
			throw queryModuleException;
		}
	}
    
}
