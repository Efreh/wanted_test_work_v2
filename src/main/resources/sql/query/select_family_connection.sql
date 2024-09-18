SELECT allPeople2.name AS name_surname,
       allPeople2.birthDate AS birthday,
       fml_connection.contactRelationship AS family_connection
FROM hppersongeneric AS allPeople
JOIN hprelatedperson AS fml_connection ON (allPeople.sysId = fml_connection.HPPersonGenericSysId)
JOIN hppersongeneric AS allPeople2 ON (fml_connection.HPRelatedPersonSysId = allPeople2.sysId)
WHERE allPeople.personId = 'test';