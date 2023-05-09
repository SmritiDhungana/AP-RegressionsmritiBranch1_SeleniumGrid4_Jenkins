Feature: Tractor Vendor Relationship Process Feature

   # TractorStepDef


  # 74
  @Regression @TractorVendorRelationship-UnitNumber/Report @FailureScreenShot6
  Scenario Outline: (TSET-375) Validate Tractor Vendor Relationship with Unit Number and Validate Report
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors
    Given Navigate to Tractor Vendor Relationship
    And Enter Unit Number <unitNumber> and click on Search
    And Validate the Total Records Returned with database Records <environment> <tableName> Unit Number <unitNumber>
    And Validate the Total Records Returned with database Records DriverThreeSixty <environment1> <tableName1> Unit Number <unitNumber>
    And Click on Report Button and on SEARCH RESULTS
    And Get SEARCH RESULTS Excel Report from Downloads for EBH Tractors
    And Validate the Excel Search result Report with database Records <environment> <tableName> Unit Number <unitNumber>
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | unitNumber | environment  | environment1       | browser  | username     | password      | tableName                      | tableName1                             |
      | "172270"   | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[Equipment]" |

    # 75
  @Regression @TractorVendorRelationship-VendorCode/Report @FailureScreenShot6
  Scenario Outline: (TSET-375) Validate Tractor Vendor Relationship with Vendor Code and Validate Report
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors
    Given Navigate to Tractor Vendor Relationship
    And Enter Vendor Code <vendorCode> and click on Search
    And Validate the Total Records Returned with database Records <environment> <tableName> Vendor Code <vendorCode>
    And Click on Report Button and on SEARCH RESULTS
    And Get SEARCH RESULTS Excel Report from Downloads for Tractor Vendor Relationship VendorCode, Report
    And Validate the Excel Search result Report with database Records <environment> <tableName> Vendor Code <vendorCode>
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | vendorCode | environment  | browser  | username     | password      | tableName                      |
      | "10001"    | "ebhstaging" | "MicrosoftEdge" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" |


    # 76
  @Regression @TractorVendorRelationship-OwnerId/Report @FailureScreenShot6
  Scenario Outline: (TSET-375) Validate Tractor Vendor Relationship with Owner Id and Validate Report
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors
    Given Navigate to Tractor Vendor Relationship
    And Enter Owner Id <ownerId> and click on Search
    And Validate the Total Records Returned with database Records DriverThreeSixty <environment1> <tableName1> Owner Id <ownerId>
    And Click on Report Button and on SEARCH RESULTS
    And Get SEARCH RESULTS Excel Report from Downloads for Tractor Vendor Relationship Owner Id, Report
    And Validate the Excel Search result Report with database Records <environment1> <tableName1> Owner Id <ownerId>
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | ownerId | environment  | environment1       | browser  | username     | password      | tableName1                             |
   #   | "31111" | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[Driver360Staging].[dbo].[Equipment]" |
    #  | "2731" | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[Driver360Staging].[dbo].[Equipment]" |
    #  | "26995" | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[Driver360Staging].[dbo].[Equipment]" |
      | "26995" | "ebhstaging" | "driver360staging" | "MicrosoftEdge" | "SmritiTest" | "Legendary@1" | "[Driver360Staging].[dbo].[Equipment]" |



     # 77
  @Regression @TractorVendorRelationship-Location/Report @FailureScreenShot6
  Scenario Outline: (TSET-375) Validate Tractor Vendor Relationship with Location and Validate Report
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors
    Given Navigate to Tractor Vendor Relationship
    And Enter Location <location> and click on Search
    And Validate the Total Records Returned with database Records <environment> <tableName> Location <location>
    And Click on Report Button and on SEARCH RESULTS
    And Get SEARCH RESULTS Excel Report from Downloads for Tractor Vendor Relationship Location, Report
    And Validate the Excel Search result Report with database Records <environment> <tableName> Location <location>
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | location | environment  | environment  | browser  | username     | password      | tableName                      |
      | "HCI"    | "ebhstaging" | "ebhstaging" | "MicrosoftEdge" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" |


  # 78
  @Regression @TractorVendorRelationshipEDIT @FailureScreenShot6
  Scenario Outline: (TSET-375) Validate Tractor Vendor Relationship with Unit Number, Select the Edit button which has Status = CURRENT
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors
    Given Navigate to Tractor Vendor Relationship
    And Enter Unit Number <unitNumber> which has Accounting Status as CURRENT and click on Search
    And Validate the Total Records Returned with database Records <environment> <tableName> Unit Number <unitNumber>
    And Validate the Total Records Returned with database Records DriverThreeSixty <environment1> <tableName1> Unit Number <unitNumber>
    And Click on View
    And Click on Edit, select Accounting Status as COMPLETED
    And Validate the Total Records Returned with database Records, Use SQL Four <environment> <tableName> <unitNumber> <accountingStatus1>
    And Click on Edit, select Accounting Status as ACCOUNTING HOLD, Select Save
    And Validate the Total Records Returned with database Records, Use SQL four <environment> <tableName> <unitNumber> <accountingStatus2>
    And Revert Back Accounting Status to Original, CURRENT
    And Validate the Total Records Returned with database Records, Use SQL Four <environment> <tableName> <unitNumber> <accountingStatus1>
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | unitNumber | environment  | environment1       | browser  | username     | password      | tableName                      | tableName1                             | accountingStatus1 | accountingStatus2 |
      | "172270"   | "ebhstaging" | "driver360staging" | "MicrosoftEdge" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[Equipment]" | "CURRENT"         | "ACCOUNTING HOLD" |


     #79a
  @Regression @IdentifyUnitNoForTractorVendorRelationshipNEW
  Scenario Outline: Identify Unit No for Testing Tractor Vendor Relationship NEW
    Given SQL query to Identify Active Tractors not in a Relatioship <environment1> <tableName1>
    Examples:
      | environment1       | tableName1                             |
      | "driver360staging" | "[Driver360Staging].[dbo].[Equipment]" |


  # 79b
  @Regression @TractorVendorRelationshipNEW @FailureScreenShot6
  Scenario Outline: (TSET-375) Validate Tractor Vendor Relationship, NEW button
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors
    Given Navigate to Tractor Vendor Relationship
    And Click on New
    And Enter Unit Number <unitNumber> Vendor Code <vendorCode> and Location <location>
    And Select Accounting Status as CURRENT and Click on Save
    And Find this Newly created Record on Tractor Vendor Code Relationship Maintenance Table <unitNumber> <vendorCode> <accountingStatus1>
    And Validate the Total Records Returned with database Records, Use SQL Four <environment> <tableName> <unitNumber> <vendorCode> <accountingStatus1>
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | unitNumber | vendorCode | location | accountingStatus1 | environment  | browser  | username     | password      | tableName                      |
    #  | "55086"    | "10013"    | "AAR"    | "CURRENT"         | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" |
    #  | "55089"    | "10013"    | "AAR"    | "CURRENT"         | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" |
    #  | "55093"    | "10013"    | "AAR"    | "CURRENT"         | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" |
    #  | "55100"    | "10013"    | "AAR"    | "CURRENT"         | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" |
    #  | "55102"    | "10013"    | "AAR"    | "CURRENT"         | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" |
    #  | "55105"    | "10013"    | "AAR"    | "CURRENT"         | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" |
      | "55402"    | "10013"    | "AAR"    | "CURRENT"         | "ebhstaging" | "MicrosoftEdge" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" |

    # Change UnitNo everytime before test from 79a (DON'T TAKE starting from V)



  #80
  @Regression @ActiveTractorsNotInaRelationship @FailureScreenShot6
  Scenario Outline: (TSET-375) Validate Active Tractors not in a Relationship
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors
    Given Navigate to Tractor Vendor Relationship
    And Select Company Code <companyCode> Unit Number <unitNumber> on Active Tractors not in a Relationship table
    And Get the Records Returned
    And Use SQL Seven and Compare to results from SQL One to confirm, what tractors are active or Termination in Process but do not have a relationship  <environment1> <tableName1> <environment> <tableName> <unitNumber>
    And Click on Report Button on Active Tractors not in a Relationship Table
    And Get SEARCH RESULTS Excel Report from Downloads for Active Tractors not in a Relationship
    And Validate the Excel Search result Report with database Records <environment1> <tableName1> <unitNumber>
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | unitNumber | companyCode | environment  | environment1       | browser  | username     | password      | tableName                      | tableName1                             |
    #  | "P186948"  | "WST"       | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[Equipment]" |
      | "55222"    | "EVA"       | "ebhstaging" | "driver360staging" | "MicrosoftEdge" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[Equipment]" |


  # 81
  @Regression @ActiveTractorsNotInaRelationshipNEW @FailureScreenShot6
  Scenario Outline: (TSET-375) Validate Active Tractors not in a Relationship, NEW button
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors
    Given Navigate to Tractor Vendor Relationship
    And Select Company Code <companyCode> and Unit Number <unitNumber> on Active Tractors not in a Relationship table
    And Get the Records Returned
    And Use SQL Seven and compare to results from SQL One to confirm what tractors are active or Termination in Process but do not have a relationship  <environment1> <tableName1> <environment> <tableName> <unitNumber>
    And Click on New on Tractors not in a Relationship table
    And Enter a valid Vendor Code and location, click Cancel and exit the form <vendorCode> <location>
    And Enter a valid Vendor Code and location, click Save and exit the form <vendorCode> <location>
    And Confirm the edited record now appears in the top grid and the original record is removed from the Active Tractors not in a Relationship grid <unitNumber>
    And Confirm the tractor vendor record was created properly in database Records SQL ONE <environment> <tableName> <unitNumber>
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | unitNumber | companyCode | vendorCode | location | environment  | environment1       | browser  | username     | password      | tableName                      | tableName1                             |
    #  | "55084"    | "EVA"       | "10008"    |"AAR"   | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[Equipment]" |
     # | "55091"    | "EVA"       | "10008"    |"AAR"   | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[Equipment]" |
     # | "55095"    | "EVA"       | "10008"    | "AAR"    | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[Equipment]" |
    #  | "55096"    | "EVA"       | "10008"    | "AAR"    | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[Equipment]" |
    #  | "55388"    | "EVA"       | "10008"    | "AAR"    | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[Equipment]" |
      | "55408"    | "EVA"       | "10008"    | "AAR"    | "ebhstaging" | "driver360staging" | "MicrosoftEdge" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[Equipment]" |


















