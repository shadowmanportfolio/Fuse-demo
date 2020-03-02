alter session set "_ORACLE_SCRIPT"=true;
DROP TABLE ORG_S;
CREATE TABLE ORG_S
(
    ID INTEGER NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    LOCATION VARCHAR(255)
);

DROP TABLE ORG_T;
CREATE TABLE ORG_T
(
    ID INTEGER NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    LOCATION VARCHAR(255)
);
/

CREATE OR REPLACE PROCEDURE GET_TABLES 
(
  p_username IN VARCHAR2,RESULT_CLOB OUT CLOB 
) AS
p_query varchar2(1000);
BEGIN
  p_query := 'select * from all_tables where owner='''||p_username||'''';
  select dbms_xmlgen.getxml(p_query) into RESULT_CLOB from dual;
END GET_TABLES;
/