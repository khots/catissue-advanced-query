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
set name = 'personUpi'
where identifier =
(select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID=
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where TGT_CONSTRAINT_KEY_ID=
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
where name = 'LaboratoryProcedure')))));


update dyextn_database_properties
set name = 'personUpi'
where identifier =
(select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID=
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where TGT_CONSTRAINT_KEY_ID=
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
where name = 'Encounter')))));

update dyextn_database_properties
set name = 'personUpi'
where identifier =
(select identifier from dyextn_column_properties
where CNSTR_KEY_PROP_ID=
(select identifier from DYEXTN_CONSTRAINTKEY_PROP
where TGT_CONSTRAINT_KEY_ID=
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
where name = 'MedicationOrder')))));
