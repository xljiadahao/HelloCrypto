/**
 * Author:  xulei
 * Created: Nov 06, 2016
 * Updated on Nov 15 2017 for multi-tenant by xulei
 */

CREATE DATABASE hellocrypto;

USE hellocrypto;

-- ---------------------------------------------------------------
-- Table certificate
-- store the certificate or public key for each client
-- ---------------------------------------------------------------
CREATE TABLE `certificate` (
`ID` int(11) NOT NULL AUTO_INCREMENT,
`NAME` varchar(50) NOT NULL,
`PUB_KEY_FINGERPRINT` varchar(100) NOT NULL UNIQUE,
`CERTIFICATE_BINARY` blob NOT NULL,
`TYPE` varchar(10) NOT NULL,
`TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------
-- Table group
-- store the group information with admin functionality
-- ---------------------------------------------------------------
CREATE TABLE `group` (
`IDENTIFIER` varchar(10) NOT NULL UNIQUE,
`ORG_NAME` varchar(50) NOT NULL,
`ACTIVITY_NAME` varchar(50) NOT NULL,
`MAX_COUNT` int DEFAULT NULL,
`IS_ACTIVATED` tinyint(1) NOT NULL,
`TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`IDENTIFIER`),
CHECK(`IS_ACTIVATED` in(0,1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------
-- Table group_certificate_mapping
-- store the relationship for the client under specific group
-- ---------------------------------------------------------------
CREATE TABLE `group_certificate_mapping` (
`GROUP_ID` varchar(10) NOT NULL,
`CERTIFICATE_ID` int(11) NOT NULL,
constraint GROUP_IDENTIFIER_FK foreign key(`GROUP_ID`) 
references `group`(`IDENTIFIER`) on delete cascade,
constraint CERTIFICATE_ID_FK foreign key(`CERTIFICATE_ID`) 
references `certificate`(`ID`) on delete cascade,
CONSTRAINT COMPOSITE_PK PRIMARY KEY(`GROUP_ID`,`CERTIFICATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
