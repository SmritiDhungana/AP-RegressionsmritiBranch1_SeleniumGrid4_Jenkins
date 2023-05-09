Feature: Tractor Settlement Adjustments Process Feature


    #101
  @Regression  @TractorSettlementAdjustmentsUnitNo @FailureScreenShot7
  Scenario Outline: (TSET-389) Validate Tractor Settlement Adjustments with Unit No
    Given Run Test for <environment> on Browser <browser> for EBH Tractor Settlement Adjustments
    And Enter the url for EBH Tractor Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Tractor Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Tractor Settlement Adjustments
    And Navigate to the Settlements page for Tractor Settlement Adjustments
    And Navigate to Tractor Settlement Adjustments
    And Enter Unit No as <unitNo>
    And Enter Start Date From <startDateFrom> and Start Date To <startDateTo>
    And Validate Company Code, Vendor Code, Vendor Name Description of <unitNo>
    And DESELECT BOTH RECURRING ONLY and INCLUDE COMPLETE and Validate the Records Returned
    And Validate the Records Returned when BOTH RECURRING ONLY and INCLUDE COMPLETE is DESELECTED with Database Record <environment> and <tableName> <tableName1> <tableName2> <unitNo> <startDateFrom> <startDateTo>
    And SELECT RECURRING ONLY and Validate the Records Returned
    And Validate the Records Returned when RECURRING ONLY is SELECTED with Database Record <environment> and <tableName> <tableName1> <tableName2> <unitNo> <startDateFrom> <startDateTo>
    And SELECT INCLUDE COMPLETE and Validate the Records Returned
    And Validate the Records Returned when INCLUDE COMPLETE is SELECTED with Database Record <environment> and <tableName> <tableName1> <tableName2> <unitNo> <startDateFrom> <startDateTo>
    And SELECT BOTH RECURRING ONLY and INCLUDE COMPLETE and Validate the Records Returned
    And Validate the Records Returned when BOTH RECURRING ONLY and INCLUDE COMPLETE is SELECTED with Database Record <environment> and <tableName> <tableName1> <tableName2> <unitNo> <startDateFrom> <startDateTo>
  #  And SELECT EFS and Validate the Records Returned
  #  And Validate the Records Returned when EFS is SELECTED with Database Record <environment> and <tableName> <tableName1> <tableName2> <unitNo> <startDateFrom> <startDateTo>
    Then Close all open Browsers on EBH for Tractor Settlement Adjustments
    Examples:
      | unitNo  | startDateFrom | startDateTo  | environment  | browser  | username     | password      | tableName                           | tableName1                     | tableName2                   |
     # | "11122" | "01/01/2022"  | "11/20/2022" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |
    #  | "11084" | "09/01/2022"  | "11/20/2022" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |
      | "14022" | "01/01/2022"  | "11/20/2022" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |



    #102
  @Regression @TractorSettlementAdjustmentsLocation @FailureScreenShot7
  Scenario Outline: (TSET-389) Validate Tractor Settlement Adjustments with Location
    Given Run Test for <environment> on Browser <browser> for EBH Tractor Settlement Adjustments
    And Enter the url for EBH Tractor Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Tractor Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Tractor Settlement Adjustments
    And Navigate to the Settlements page for Tractor Settlement Adjustments
    And Navigate to Tractor Settlement Adjustments
    And Enter Location as <location>
    And Enter Start Date From <startDateFrom> and Start Date To <startDateTo>
    And Validate Location Name of <location>
    And DESELECT BOTH RECURRING ONLY and INCLUDE COMPLETE and Validate the Records Returned
    And Validate the Records Returned when BOTH RECURRING ONLY and INCLUDE COMPLETE is DESELECTED for Location with Database Record <environment> and <tableName> <tableName1> <tableName2> <location> <startDateFrom> <startDateTo>
    And SELECT RECURRING ONLY and Validate the Records Returned
    And Validate the Records Returned when RECURRING ONLY is SELECTED for Location with Database Record <environment> and <tableName> <tableName1> <tableName2> <location> <startDateFrom> <startDateTo>
    And SELECT INCLUDE COMPLETE and Validate the Records Returned
    And Validate the Records Returned when INCLUDE COMPLETE is SELECTED for Location with Database Record <environment> and <tableName> <tableName1> <tableName2> <location> <startDateFrom> <startDateTo>
    And SELECT BOTH RECURRING ONLY and INCLUDE COMPLETE and Validate the Records Returned
    And Validate the Records Returned when BOTH RECURRING ONLY and INCLUDE COMPLETE is SELECTED for Location with Database Record <environment> and <tableName> <tableName1> <tableName2> <location> <startDateFrom> <startDateTo>
  #  And SELECT EFS and Validate the Records Returned
  #  And Validate the Records Returned when EFS is SELECTED for Location with Database Record <environment> and <tableName> <tableName1> <tableName2> <location> <startDateFrom> <startDateTo>
    Then Close all open Browsers on EBH for Tractor Settlement Adjustments
    Examples:
      | location | startDateFrom | startDateTo  | environment  | browser  | username     | password      | tableName                           | tableName1                     | tableName2                   |
    #  | "AAR"    | "01/01/2022"  | "11/20/2022" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |
      | "BCI"    | "10/30/2022"  | "11/09/2022" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |


  #103
  @Regression @TractorSettlementAdjustmentsScenarioReport @FailureScreenShot7
  Scenario Outline: (TSET-389) Validation of Report on Tractor Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Tractor Settlement Adjustments
    And Enter the url for EBH Tractor Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Tractor Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Tractor Settlement Adjustments
    And Navigate to the Settlements page for Tractor Settlement Adjustments
    And Navigate to Tractor Settlement Adjustments
    And Enter Unit No as <unitNo>
    And SELECT RECURRING ONLY and Validate the Records Returned
    And Click on Report Button for Tractor Settlement Adjustments with Unit No
    And Get Excel Report from Downloads for Tractor Settlement Adjustments with Unit No
    And Validate Excel Report with database Records <environment> <tableName> <tableName1> <tableName2> Unit No <unitNo>
    Then Close all open Browsers on EBH for Tractor Settlement Adjustments
    Examples:
      | unitNo  | environment  | browser  | username     | password      | tableName                           | tableName1                     | tableName2                   |
      | "11122" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |


  #104
  @Regression @TractorSettlementAdjustmentsScenarioNew @FailureScreenShot7
  Scenario Outline: (TSET-389) Validation of New on Tractor Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Tractor Settlement Adjustments
    And Enter the url for EBH Tractor Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Tractor Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Tractor Settlement Adjustments
    And Navigate to the Settlements page for Tractor Settlement Adjustments
    And Navigate to Tractor Settlement Adjustments
    And Select New on Tractor Settlement Adjustments
    And Verify Tractor Settlement Adjustment Detail form is Opened
    And Insert values in all required fields in New <unitNo> <payCode> <status> <frequency> <amount> <orderNumber> <maxNoOfAdjustments> <maxLimit> <endDate> <payToVendorID> <payToVendorPayCode> <lastActivityAmount> <lastActivityDate> <notes>
    And Click on Save on New
    And Get the Newly Created New Record on UI <unitNo> <payCode> <status> <frequency> <amount> <startDateFrom> <endDate>
    And Validate the Newly Created Record with Database Records <environment> <tableName> <tableName1> <tableName2> <unitNo> <payCode> <status> <frequency> <amount> <startDateFrom> <endDate>
    Then Close all open Browsers on EBH for Tractor Settlement Adjustments
    Examples:
      | unitNo  | payCode | status   | frequency | amount | orderNumber | maxNoOfAdjustments | maxLimit | startDateFrom | endDate      | payToVendorID | payToVendorPayCode | lastActivityAmount | lastActivityDate | notes               | environment  | browser  | username     | password      | tableName                           | tableName1                     | tableName2                   |
    #  | "14022" | "AB"    | "ACTIVE" | "S"       | "$2.00"    | "" | "1"                | ""       | "11/07/2022"  | "12/30/2022" | "12/30/2022" | ""            | ""                 | ""                 | ""               | "This is a TEST !!" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |
    #  | "14022" | "ABVVV" | "ACTIVE" | "W"       | "$1.50" |  ""          | "1"                | ""       | "11/30/2022"  | "12/30/2022" | "12/30/2022" | ""            | ""                 | ""                 | ""               | "This is a TEST !!" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |
    #  | "14022" | "AA"    | "ACTIVE" | "W"       | "$1.50" |  ""          | "1"                | ""       | "01/10/2023"  | "03/30/2023" | "12/30/2022" | ""            | ""                 | ""                 | ""               | "This is a TEST !!" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |
      | "14022" | "AA"    | "ACTIVE" | "W"       | "5.55" | ""          | "1"                | ""       | "03/10/2023"  | "04/30/2023" | ""            | ""                 | ""                 | ""               | "This is a TEST !!" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |


      #105
  @Regression @TractorSettlementAdjustmentsScenarioQuickEntry @FailureScreenShot7
  Scenario Outline: (TSET-389) Validation of Quick Entry on Tractor Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Tractor Settlement Adjustments
    And Enter the url for EBH Tractor Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Tractor Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Tractor Settlement Adjustments
    And Navigate to the Settlements page for Tractor Settlement Adjustments
    And Navigate to Tractor Settlement Adjustments
    And Select Quick Entry on Tractor Settlement Adjustments
    And Insert values in all required fields in Quick Entry <unitNo> <payCode> <frequency> <amount> <startDateFrom> <maxLimit> <maxNoOfAdjustments> <orderNumber> <notes> <splits>
    And Click on Save on Quick Entry
    And Get the Newly Created Quick Entry Record on UI <unitNo> <payCode> <status> <frequency> <amount> <startDateFrom>
    And Validate the Newly Created Record with Database Records <environment> <tableName> <tableName1> <tableName2> Unit No <unitNo> <payCode> <status> <frequency> <amount> <startDateFrom> <maxLimit> <maxNoOfAdjustments> <orderNumber> <notes> <splits>
    Then Close all open Browsers on EBH for Tractor Settlement Adjustments
    Examples:
      | unitNo  | payCode | status   | frequency | amount | startDateFrom | maxLimit | maxNoOfAdjustments | orderNumber | notes                         | splits | environment  | browser  | username     | password      | tableName                           | tableName1                     | tableName2                   |
      | "14022" | "AA"    | "ACTIVE" | "S"       | "6.88" | "03/25/2023"  | "1"      | "1"                | ""          | "This is QUICK ENTRY TEST !!" | "1"    | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |


     #106
  @Regression @TractorSettlementAdjustmentsScenarioQuickEdit @FailureScreenShot7
  Scenario Outline: (TSET-389) Validation of Quick Edit on Tractor Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Tractor Settlement Adjustments
    And Enter the url for EBH Tractor Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Tractor Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Tractor Settlement Adjustments
    And Navigate to the Settlements page for Tractor Settlement Adjustments
    And Navigate to Tractor Settlement Adjustments
    And Select Quick Edit on Tractor Settlement Adjustments
    And Enter Unit Number <unitNo>, Pay Code <payCode> and click on Search
    And Get the Total Records Returned on Tractor Settlement Adjustments Quick Edit Table
    And Validate the Records Returned on Tractor Settlement Adjustments Quick Edit Table with Database Record <environment> <tableName> <tableName1> <tableName2> and <unitNo> <payCode> <adjustmentID>
    And Change Start date <startDateEdited> and Amount <amountEdited> on Quick Edit
    And Click on Save
    And Get the Records Returned on Tractor Settlement Adjustments Quick Edit Table, for <unitNo> and <payCode> when Start date <startDateEdited> and Amount <amountEdited> is CHANGED
    And Validate the Records Returned on Tractor Settlement Adjustments Quick Edit Table, when Start date and Amount is CHANGED with Database Record <environment> <tableName> <tableName1> <tableName2> and <unitNo> <payCode> <adjustmentID>
    And Select Quick Edit on Tractor Settlement Adjustments to Revert Back Start Date and Amount to original
    And Enter Unit Number <unitNo>, Pay Code <payCode> and click on Search
    And Change Start date <startDateExisting> and Amount <amountExisting> on Quick Edit
    And Click on Save
    And Get the Records Returned on Tractor Settlement Adjustments Quick Edit Table to Revert Back, for <unitNo> and <payCode> when Start date <startDateExisting> and Amount <amountExisting> is CHANGED
    And Validate the Records Returned on Tractor Settlement Adjustments Quick Edit Table, when Start date and Amount is CHANGED with Database Record <environment> <tableName> <tableName1> <tableName2> and <unitNo> <payCode> <adjustmentID>
    Then Close all open Browsers on EBH for Tractor Settlement Adjustments
    Examples:
      | unitNo  | payCode | adjustmentID | amountExisting | amountEdited | startDateExisting | startDateEdited | environment  | browser  | username     | password      | tableName                           | tableName1                     | tableName2                   |
      | "14022" | "DN"    | "15951095"   | "3.3"          | "5.5"        | "03/30/2023"      | "05/30/2023"    | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |



   #107
  @Regression @TractorSettlementAdjustmentsScenarioEdit @FailureScreenShot7
  Scenario Outline: (TSET-389) Validate on EDIT - ACTIVE to COMPLETE and back to ACTIVE on Tractor Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Tractor Settlement Adjustments
    And Enter the url for EBH Tractor Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Tractor Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Tractor Settlement Adjustments
    And Navigate to the Settlements page for Tractor Settlement Adjustments
    And Navigate to Tractor Settlement Adjustments
    And Enter Unit Number <unitNo> <startDate> <endDate> for Edit
    And Select the Pay Code <payCode>, Status <statusActive> as ACTIVE, Amount <amount> and Start Date <startDate> for Edit
    And Get the Record Returned on Tractor Settlement Adjustments for Edit
    And Validate the Record Returned on Tractor Settlement Adjustments for Edit with Database Record <environment> <tableName> <tableName1> <tableName2> and <unitNo> <payCode> <statusActive> <amount> <startDate>
    And Select Edit Button on Action Column for Edit <adjustmentID>
    And Change ACTIVE status to COMPLETE, Select YES Button and Confirm Status changes to COMPLETE <statusComplete> <endDate>
    And Select Include Complete, Select the Pay Code <payCode>, Amount <amount>, Start Date <startDate> and Select Status as COMPLETE <statusComplete>
    And Get the Records Returned on Tractor Settlement Adjustments Table, when ACTIVE status is changed to COMPLETE
    And Validate the Records Returned on Tractor Settlement Adjustments Table, WHEN ACTIVE TO COMPLETE with Database Record <environment> <tableName> <tableName1> <tableName2> and <unitNo> <payCode> <statusComplete> <amount> <startDate>
    And Select Edit Button on Action Column for COMPLETE
    And Change COMPLETE status to ACTIVE, Select YES Button and Confirm ACTIVE Status was accepted <statusActive>
    And Select Include Complete, Select the Pay Code <payCode>, Amount <amount>, Start Date <startDate> and Status <statusActive> as ACTIVE
    And Get the Records Returned on Tractor Settlement Adjustments Table, when COMPLETE status is changed to ACTIVE
    And Validate the Records Returned on Tractor Settlement Adjustments Table, WHEN COMPLETE TO ACTIVE with Database Record <environment> <tableName> <tableName1> <tableName2> and <unitNo> <payCode> <statusActive> <amount> <startDate>
    Then Close all open Browsers on EBH for Tractor Settlement Adjustments
    Examples:
      | unitNo  | payCode | amount | adjustmentID | startDate    | endDate      | statusComplete | statusActive | environment  | browser  | username     | password      | tableName                           | tableName1                     | tableName2                   |
      | "14022" | "ABVVV" | "1.5"  | "18985574"   | "11/30/2022" | "12/30/2023" | "COMPLETE"     | "ACTIVE"     | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |
    #  | "14002" | "AA" | "5.55"  | "21145997"   | "03/19/2023" | "12/30/2023" | "COMPLETE"     | "ACTIVE"     | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |


    #108
  @Regression @TractorSettlementAdjustments/UnitNo/PayCode/Frequency/OrderNo @FailureScreenShot7
  Scenario Outline: (TSET-389) Validate - UNIT NO, PAY CODE, START DATE FROM, START DATE TO, FREQUENCY, ORDER NUMBER in Tractor Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Tractor Settlement Adjustments
    And Enter the url for EBH Tractor Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Tractor Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Tractor Settlement Adjustments
    And Navigate to the Settlements page for Tractor Settlement Adjustments
    And Navigate to Tractor Settlement Adjustments
    And Enter Unit No as <unitNo>
    And Enter Start Date From <startDateFrom> and Start Date To <startDateTo>
    And Validate Company Code, Vendor Code, Vendor Name Description of <unitNo>
    And DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo>
    And SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo>
    And SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo>
    And SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo>
  #  And SELECT EFS, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo>
    And Enter Unit No, Start Date From, Start Date To, Pay Code <payCode> and Click on Search Button
    And DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode>
    And SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode>
    And SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode>
    And SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode>
   # And SELECT EFS, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode>
    And Enter Unit No, Start Date From, Start Date To, Pay Code, Frequency <frequency> and Click on Search Button
    And DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode> <frequency>
    And SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode> <frequency>
    And SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode> <frequency>
    And SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode> <frequency>
   # And SELECT EFS, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode> <frequency>
    And Enter Unit No, Start Date From, Start Date To, Pay Code, Frequency, Order Number <orderNo> and Click on Search Button
    And DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode> <frequency> <orderNo>
    And SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode> <frequency> <orderNo>
    And SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode> <frequency> <orderNo>
    And SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode> <frequency> <orderNo>
   # And SELECT EFS, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Unit No <unitNo> <startDateFrom> <startDateTo> <payCode> <frequency> <orderNo>
    Then Close all open Browsers on EBH for Tractor Settlement Adjustments
    Examples:
      | unitNo  | startDateFrom | startDateTo  | payCode | frequency | orderNo     | environment  | browser  | username     | password      | tableName                           | tableName1                     | tableName2                   |
      | "14022" | "08/18/2022"  | "11/30/2022" | "AA"    | "W"       | "HCI112789" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |


  #109
  @Regression @TractorSettlementAdjustments/Location/PayCode/Frequency/OrderNo @FailureScreenShot7
  Scenario Outline: (TSET-389) Validation of ADVANDED SEARCH - LOCATION, PAY CODE, START DATE FROM, START DATE TO, FREQUENCY, ORDER NUMBER in Tractor Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Tractor Settlement Adjustments
    And Enter the url for EBH Tractor Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Tractor Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Tractor Settlement Adjustments
    And Navigate to the Settlements page for Tractor Settlement Adjustments
    And Navigate to Tractor Settlement Adjustments
    And Enter Location as <location>
    And Enter Start Date From <startDateFrom> and Start Date To <startDateTo>
    And Validate Location Name of <location>
    And DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo>
    And SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo>
    And SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo>
    And SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo>
  #  And SELECT EFS, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo>
    And Enter Location, Start Date From, Start Date To, Pay Code <payCode> and Click on Search Button
    And DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode>
    And SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode>
    And SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode>
    And SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode>
  #  And SELECT EFS, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode>
    And Enter Location, Start Date From, Start Date To, Pay Code, Frequency <frequency> and Click on Search Button
    And DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode> <frequency>
    And SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode> <frequency>
    And SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode> <frequency>
    And SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode> <frequency>
  #  And SELECT EFS, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode> <frequency>
    And Enter Location, Start Date From, Start Date To, Pay Code, Frequency, Order Number <orderNo> and Click on Search Button
    And DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode> <frequency> <orderNo>
    And SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode> <frequency> <orderNo>
    And SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode> <frequency> <orderNo>
    And SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode> <frequency> <orderNo>
  #  And SELECT EFS, get Records and validate the Records Returned with Database Record <environment> <tableName> <tableName1> <tableName2> and Location <location> <startDateFrom> <startDateTo> <payCode> <frequency> <orderNo>
    Then Close all open Browsers on EBH for Tractor Settlement Adjustments
    Examples:
      | location | startDateFrom | startDateTo  | payCode | frequency | orderNo     | environment  | browser  | username     | password      | tableName                           | tableName1                     | tableName2                   |
     # | "AAR"    | "08/18/2022"  | "11/20/2022" | "CH"    | "S"       | "AGC176105" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |
      | "BCI"    | "11/20/2022"  | "11/30/2022" | "CH"    | "M"       | "HCI112791" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |
    #  | "BCO"    | "08/18/2021"  | "11/20/2022" | "CH"    | "S"       | "AGC176105" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "[EBH].[dbo].[AP_PAY_CODES]" |































