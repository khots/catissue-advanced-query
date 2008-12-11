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

update dyextn_constraint_properties 
set target_entity_key = 'personUpi'
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Person'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'LaboratoryProcedure'));


update dyextn_constraint_properties 
set target_entity_key = 'personUpi'
where association_id = 
(select identifier from dyextn_association
where identifier in
(select identifier from dyextn_attribute
where entiy_id in  
(select identifier from dyextn_abstract_metadata
where name = 'Person'))
and target_entity_id =
(select identifier from dyextn_abstract_metadata
where name = 'Encounter'));