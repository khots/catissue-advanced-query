
update dyextn_database_properties
set name = 'DEMOGRAPHICS'
where identifier =
(select identifier from dyextn_table_properties
where abstract_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Person'));

update dyextn_database_properties
set name = 'LABS'
where identifier =
(select identifier from dyextn_table_properties
where abstract_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'LaboratoryProcedure'));

update dyextn_database_properties
set name = 'ENCOUNTERS'
where identifier =
(select identifier from dyextn_table_properties
where abstract_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'));

update dyextn_database_properties
set name = 'MEDICATIONS'
where identifier =
(select identifier from dyextn_table_properties
where abstract_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'MedicationOrder'));


update dyextn_database_properties
set name = 'DOCUMENTS'
where identifier =
(select identifier from dyextn_table_properties
where abstract_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Document'));

update dyextn_database_properties
set name = 'ALLERGIES'
where identifier =
(select identifier from dyextn_table_properties
where abstract_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'PatientAllergies'));


update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'LaboratoryProcedure'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Person'))))
);


update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person'))) 
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'MedicationOrder'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Person'))))
);



update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Person'))))
);



update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Person'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'LaboratoryProcedure'))))
);


update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Person'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'MedicationOrder'))))
);



update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person'))) 
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Person'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))))
);

update dyextn_database_properties
set name = 'FACILITY'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'id' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Facility')))  
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'LaboratoryProcedure'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))))
);


update dyextn_database_properties
set name = 'PAN'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'patientAccountNumber' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Encounter')))  
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'LaboratoryProcedure'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))))
);


update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'LaboratoryProcedure'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))))
);

update dyextn_database_properties
set name = 'FACILITY'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'id' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Facility'))) 
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'LaboratoryProcedure'))))
);


update dyextn_database_properties
set name = 'PAN'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'patientAccountNumber' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Encounter')))  
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'LaboratoryProcedure'))))
);

update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'LaboratoryProcedure'))))
);


update dyextn_database_properties
set name = 'FACILITY'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'id' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Facility')))  
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'MedicationOrder'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))))
);


update dyextn_database_properties
set name = 'PAN'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'patientAccountNumber' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Encounter')))  
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'MedicationOrder'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))))
);


update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'MedicationOrder'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))))
);


update dyextn_database_properties
set name = 'FACILITY'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'id' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Facility')))  
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'MedicationOrder'))))
);


update dyextn_database_properties
set name = 'PAN'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'patientAccountNumber' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Encounter')))  
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'MedicationOrder'))))
);


update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'MedicationOrder'))))
);


update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Document'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Person'))))
);


update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Person'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Document'))))
);


update dyextn_database_properties
set name = 'FACILITY'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'id' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Facility')))  
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Document'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))))
);


update dyextn_database_properties
set name = 'PAN'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'patientAccountNumber' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Encounter')))  
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Document'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))))
);


update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and SRC_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Document'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))))
);


update dyextn_database_properties
set name = 'FACILITY'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'id' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Facility')))  
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Document'))))
);


update dyextn_database_properties
set name = 'PAN'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'patientAccountNumber' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Encounter')))  
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Document'))))
);


update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Document'))))
);






insert into dyextn_tagged_value (IDENTIFIER,T_KEY,T_VALUE,ABSTRACT_METADATA_ID) values (default,'PRIMARY_KEY_VALUE','FACILITY',(select identifier from dyextn_abstract_metadata
  where name = 'id' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Facility'))));


update dyextn_database_properties
set name = 'UPI'
where identifier =
(
select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID =
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where primary_attribute_id in 
(select identifier from dyextn_abstract_metadata
where name = 'personUpi' and identifier in 
(select identifier from dyextn_attribute where entiy_id in 
(select identifier from dyextn_abstract_metadata where name='Person')))  
and TGT_CONSTRAINT_KEY_ID=
(select identifier from dyextn_constraint_properties
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Person'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'PatientAllergies'))))
);


