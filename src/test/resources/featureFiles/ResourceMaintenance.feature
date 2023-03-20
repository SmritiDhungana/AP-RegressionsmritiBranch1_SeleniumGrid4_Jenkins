Feature: Resource Maintenance Process Feature

    #82
  @Regression @ResourceMaintenanceSearch/Report @FailureScreenShot9
  Scenario Outline: (EQ-17) Validation of Search Box and Report on Resource Maintenance
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH Resource Maintenance
    And Login to the Agents Portal with username <username> and password <password> for EBH Resource Maintenance
    And Navigate to the Corporate Page on Main Menu and to the Resources page for EBH Resource Maintenance
    And Navigate to the Resource Maintenance page
    And Enter text to Search Box Code or Locations or Name <code/location/name> and click on Search
    And Get the Total Records Returned on Resource Maintenance Table
    And Validate the Records Returned on Resource Maintenance with Database Record <environment> <tableName> <tableName1> and <code/location/name1>
    And Click on Report Button and Click on Search Results on Resource Maintenance
    And Get SEARCH RESULTS Excel Report from Downloads for Resource Maintenance
    And Validate SEARCH RESULTS Excel Report for Resource Maintenance with Database Record <environment> <tableName> <tableName1> and <code/location/name1>
    Then Close all open Browsers on EBH Resource Maintenance
    Examples:
      | code/location/name | code/location/name1 | environment  | tableName                 | tableName1                              | browser  | username     | password      |
      | "BCO"              | "%BCO%"             | "ebhstaging" | "[EBH].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
   #   | "PETER"            | "%PETER%"           | "ebhstaging" | "[EBH].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
  #    | "59965"            | "%59965%"           | "ebhstaging" | "[EBH].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
  #    | "166854"           | "%166854%"          | "ebhstaging" | "[EBH].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
   # #  | "AAR"              | "%AAR%"             | "ebhstaging" | "[EBH].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
  #    | "JIM"              | "%JIM%"             | "ebhstaging" | "[EBH].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
  #    | "864"              | "%864%"             | "ebhstaging" | "[EBH].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
    #  | "555"              | "%555%"             | "ebhstaging" | "[EBH].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
    #  | "V000001"          | "%V000001%"         | "ebhstaging" | "[EBH].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |


    #83
  @Regression @ResourceMaintenanceView/Edit @FailureScreenShot9
  Scenario Outline: (EQ-17) Validation of View and Edit on Resource Maintenance
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH Resource Maintenance
    And Login to the Agents Portal with username <username> and password <password> for EBH Resource Maintenance
    And Navigate to the Corporate Page on Main Menu and to the Resources page for EBH Resource Maintenance
    And Navigate to the Resource Maintenance page
    And Enter text to Search Box Code or Locations or Name <code/location/name> and click on Search
    And Identify the Code to Edit <code> and Get the Record Returned
    And Validate the Record Returned with Database Record <environment> <tableName> <tableName1> and <code1>
    And Click on View on Resource Maintenance
    And Click on Edit on Resource Maintenance
    And Change ACTIVE Status to INACTIVE and Save, and Click on Search <code>
    And Get the Records Returned and Validate the Records Returned when ACTIVE Status changed to INACTIVE with Database Record <environment> <tableName> <tableName1> and <code1>
    And Click on Edit on Resource Maintenance
    And Revert Back Changes from INACTIVE Status to ACTIVE and Save, and Click on Search <code>
    And Get the Records Returned and Validate the Records Returned when INACTIVE Status changed to ACTIVE  with Database Record <environment> <tableName> <tableName1> and <code1>
    Then Close all open Browsers on EBH Resource Maintenance
    Examples:
      | code/location/name | code     | code1      | environment  | tableName                 | tableName1                              | browser  | username     | password      |
      | "BCO"              | "155155" | "%155155%" | "ebhstaging" | "[ebh].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |


    #84b
  @Regression @ResourceMaintenanceNew @FailureScreenShot9
  Scenario Outline: (EQ-17) Validation of New on Resource Maintenance
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH Resource Maintenance
    And Login to the Agents Portal with username <username> and password <password> for EBH Resource Maintenance
    And Navigate to the Corporate Page on Main Menu and to the Resources page for EBH Resource Maintenance
    And Navigate to the Resource Maintenance page
    And Click on New on Resource Maintenance
    And Enter all the details <type> <company> <code> <homeLocation> <ownerIFTA> <status>
    And Click on Save on Add Resource Information
    And Get the Total Records Returned on Resource Maintenance Table for Newly created Record <type> <code>
    And Validate the Records Returned for NEW on Resource Maintenance with Database Record <environment> <tableName> <tableName1> and <type> <company> <code1> <homeLocation> <ownerIFTA> <status>
    Then Close all open Browsers on EBH Resource Maintenance
    Examples:
      | type  | company | code      | code1       | homeLocation | ownerIFTA              | status   | environment  | tableName                 | tableName1                              | browser  | username     | password      |
     # | "LOC" | "EVA"   | "YNN"     | "%YNN%"     | "AAR"        | "OO - Evans Reports"   | "ACTIVE" | "ebhstaging" | "[ebh].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
     # | "LOC" | "EVA"   | "ABH"     | "%ABH%"     | "AAR"        | "IFTA - Owner Reports" | "ACTIVE" | "ebhstaging" | "[ebh].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
     # | "CAR" | "EVA"   | "V002501" | "%V002501%" | "AAR"        | "IFTA - Owner Reports" | "ACTIVE" | "ebhstaging" | "[ebh].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
     # | "CAR" | "EVA"   | "V002502" | "%V002502%" | "BCO"        | "IFTA - Owner Reports" | "ACTIVE" | "ebhstaging" | "[ebh].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
     # | "TRC" | "EVA"   | "155160"  | "%155160%"  | "BCO"        | "OO - Evans Reports"   | "ACTIVE" | "ebhstaging" | "[ebh].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
     # | "TRC" | "EVA"   | "190000"  | "%190000%"  | "AAR"        | "OO - Evans Reports"   | "ACTIVE" | "ebhstaging" | "[ebh].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
     # | "TRC" | "EVA"   | "190001"  | "%190001%"  | "AAR"        | "OO - Evans Reports"   | "ACTIVE" | "ebhstaging" | "[ebh].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |
      | "TRC" | "EVA"   | "190002"  | "%190002%"  | "AAR"        | "OO - Evans Reports"   | "ACTIVE" | "ebhstaging" | "[ebh].[dbo].[RESOURCES]" | "[EBH].[dbo].[RESOURCES_ALL_LOCATIONS]" | "chrome" | "SmritiTest" | "Legendary@1" |


    #84a
  @Smoke @ValidCodeForTypeLocOnResourceMaintenanceNewForm @FailureScreenShot9
  Scenario Outline: (EQ-17) Identify Valid Code for Type LOC in New Form on Resource Maintenance
    Given Locate a Record from Database for Valid Code for Type LOC in New Form on Resource Maintenance <Environment> <TableName>
      # TRC = three to eight alphanumeric characters. CAR = “V” followed by from five to twelve numbers. LOC = Check the entry against the LOCATIONS table
    Examples:
      | Environment  | TableName                 |
      | "ebhstaging" | "[EBH].[dbo].[LOCATIONS]" |














