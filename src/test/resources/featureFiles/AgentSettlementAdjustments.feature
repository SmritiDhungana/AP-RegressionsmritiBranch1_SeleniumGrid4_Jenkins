Feature: Agent Settlement Adjustments Process Feature


    #30
  @Regression  @AgentSettlementAdjustmentsVendorId @FailureScreenShot2
  Scenario Outline: Validate Agent Settlement Adjustments with VendorID
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor ID as <VendorID> and click
    And Validate VendorID, Vendor Code Description of <VendorID>
    And Click on Recurring Only and Validate Total Records Returned and Total Record
    And Validate Total Records Returned on Recurring Only with Database Record <environment> and <tableName> <VendorID>
    And Validate Pay Code contains ES and ME
    And Click on EFS and Validate total records Returned
    And Validate Total Records Returned on EFS with Database Record <environment> and <tableName> <VendorID>
    And Click on Include Complete and Validate total records Returned
    And Validate Total Records Returned on Include Complete with Database Record <environment> and <tableName> <VendorID>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | environment | browser  | username         | password      | VendorID | tableName                                      |
      | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "9859"   | "[EBHLaunch].[dbo].[usp_GetVendorCodeDetails]" |
  #    | "ebhprod"   | "chrome" | "SMRITIDHUNGANA" | "Smr1tiD@2022" | "99999"  | "[EBH].[dbo].[usp_GetVendorCodeDetails]" |


    #31
  @Regression @AgentSettlementAdjustmentsAgentCode @FailureScreenShot2
  Scenario Outline: Validate Agent Settlement Adjustments with AgentCode
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Agent Code as <AgentCode> and click
    And Validate VendorID, Vendor Code Description of <AgentCode>
    And Click on Recurring Only and Validate Total Records Returned and Total Record
    And Validate Total Records Returned for Agent Code on Recurring Only with Database Record <environment> and <tableName> <VendorID> <AgentCode>
    And Validate Pay Code contains ES and ME
    And Click on EFS and Validate total records Returned
    And Validate Total Records Returned for Agent Code on EFS with Database Record <environment> and <tableName> <VendorID> <AgentCode>
    And Click on Include Complete and Validate total records Returned
    And Validate Total Records Returned for Agent Code on Include Complete with Database Record <environment> and <tableName> <VendorID> <AgentCode>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorID | AgentCode | environment | browser  | username         | password      | tableName                                      |
      | "10008"  | "YNN"     | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetVendorCodeDetails]" |
   #   | "99999"  | "HCI"     | "ebhprod"   | "chrome" | "SMRITIDHUNGANA" | "Smr1tiD@2022" | "[EBH].[dbo].[usp_GetVendorCodeDetails]" |



    #32
  @Regression @ScenarioActiveCompleteActive @FailureScreenShot2
  Scenario Outline: (SET-2295) Validate on EDIT - ACTIVE to COMPLETE and back to ACTIVE on Agent Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode> <startDate> <endDate> and Click on Search
    And Select the Pay Code <PayCode> Start Date <startDate> and Status <StatusActive> <amount>
    And Select the First Edit Button on Action Column <adjustmentID>
    And Change ACTIVE status to COMPLETE, Select YES Button, Confirm Status changes to COMPLETE <StatusComplete> <endDate>
    And Select Include Complete
    And Select the Pay Code <PayCode> Start Date <startDate> and Status <StatusComplete> <amount>
    And Get the Records Returned on Agent Settlement Adjustments Table, when ACTIVE status changed to COMPLETE
    And Validate the Records Returned, WHEN ACTIVE TO COMPLETE with Database Record <environment> <tableName> and <VendorCode> <PayCode> <StatusComplete> <startDate> <amount>
    And Select the First Edit Button on Action Column for COMPLETE <adjustmentID>
    And Change COMPLETE status to ACTIVE, Select YES Button, Confirm ACTIVE Status was accepted <StatusActive>
    And Select the Pay Code <PayCode> Start Date <startDate> and Status <StatusActive> <amount>
    And Get the Records Returned on Agent Settlement Adjustments Table after Revert Back
    And Validate the Records Returned, WHEN COMPLETE TO ACTIVE with Database Record <environment> <tableName> and <VendorCode> <PayCode> <StatusActive> <startDate> <amount>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | PayCode | amount | startDate    | endDate      | StatusComplete | StatusActive | adjustmentID | environment | browser  | username         | password      | tableName                               |
    #  | "10000"    | "AB"    | "4.44" | "12/07/2022" | "03/30/2023" | "COMPLETE"     | "ACTIVE"     | "10448"      | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
      | "10000"    | "AB"    | "4.44" | "12/07/2022" | "03/30/2023" | "COMPLETE"     | "ACTIVE"     | "10448"      | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |



    #33
  @Regression @ScenarioOnNewDATE @FailureScreenShot2
  Scenario Outline: (SET-2295) Validate on NEW - START DATE and END DATE on Agent Settlement Adjustments Detail
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode> and Click on Search
    And Select the New Button, Enter required fields <PayCode> <Status> <Frequency> <Amount> <ApplytoAgent>
    And Enter an End Date that is after the Start Date and not in the past, Select the Save button <StartDate> <EndDate>
    And Select Include Complete and Search the Newly created record <PayCode> <Status> <Amount> <Frequency> <ApplytoAgent> <StartDate>
    And Validate the Records Returned on selecting NEW BUTTON with Database Record <environment> <tableName> and <VendorCode> <PayCode> <Status> <Amount> <Frequency> <ApplytoAgent> <StartDate>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | PayCode | Status   | Frequency | Amount | ApplytoAgent | StartDate    | EndDate      | environment | browser  | username         | password      | tableName                               |
    #  | "10000"    | "AB"    | "ACTIVE" | "S"       | "3.33" | "BAL"        | "03/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
      | "10000"    | "AA"    | "ACTIVE" | "S"       | "1.33" | "BAL"        | "03/10/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |


    #34
  @Regression @ScenarioOnNew/ApplyToAgent/VendorCode @FailureScreenShot2
  Scenario Outline: (SET-2295) Validate on NEW - Verify APPLY TO AGENT field is not Blank or Empty and drop-down list values are associated with the searched Vendor Code
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code as <VendorCode> and Click on Search
    And Select the New Button, Enter required fields <PayCode> <Status> <Frequency> <Amount> and Apply To Agent <ApplytoAgent>
    And Enter an End Date that is after the Start Date and not in the past, Select the Save button <StartDate1> <EndDate>
    And Select Include Complete and Search the Newly created record for Apply to Agent <PayCode> <Status> <Amount> <Frequency> <ApplytoAgent> <StartDate>
    And Validate the Records Returned on selecting NEW BUTTON with Database Record for Apply to Agent <environment> <tableName> and <VendorCode> <PayCode> <Status> <Amount> <Frequency> <ApplytoAgent> <StartDate1>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | PayCode | Status   | Frequency | Amount | ApplytoAgent | StartDate    | StartDate1   | EndDate      | environment | browser  | username         | password      | tableName                               |
     # | "9803"    | "AB"    | "ACTIVE" | "S"       | "3.33" | "AGA"        |"2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
     # | "59688"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "QCC"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "10000"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "DBO"        |"2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    # | "164549"   | "AB"    | "ACTIVE" | "S"       | "3.33" | "WUN"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "21157"    | "AB"    | "ACTIVE" | "S"       | "3.33" | "GDE"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "30046"    | "AB"    | "ACTIVE" | "S"       | "3.33" | "ZMA"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    # | "20184"    | "AB"    | "ACTIVE" | "S"       | "3.33" | "MNH"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "19208"    | "AB"    | "ACTIVE" | "S"       | "3.33" | "JJN"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "250105"    | "AB"    | "ACTIVE" | "S"       | "3.33" | "DTL"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
   #   | "10532"    | "AB"    | "ACTIVE" | "S"       | "3.33" | "ZRB"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "21068"    | "AB"    | "ACTIVE" | "S"       | "3.33" | "DVC"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
     # | "14245"    | "AB"    | "ACTIVE" | "S"       | "3.33" | "MJC"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "10384"    | "AB"    | "ACTIVE" | "S"       | "3.33" | "ARCX"       | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "10079"    | "AB"    | "ACTIVE" | "S"       | "3.33" | "MCE"       | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "8112"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "KBI"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "10512"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "ZWG"        | "2023-03-09" | "03/09/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
      | "14325"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "EMA"        | "2023-03-10" | "03/10/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |

    #Enter new VendorCode/ApplyToAgent/StartDate/EndDate everytime before running test

     #35
  @Regression @ScenarioOnNew/ApplyToAgent/AgentCode @FailureScreenShot2
  Scenario Outline: (SET-2295) Validate on NEW - Verify APPLY TO AGENT field is not Blank or Empty and drop-down list values are associated with the searched Agent Code
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Agent Code as <AgentCode> and click on Search
    And Select the New Button, Enter required fields <PayCode> <Status> <Frequency> <Amount> and Apply To Agent <ApplytoAgent>
    And Enter an End Date that is after the Start Date and not in the past, Select the Save button <StartDate1> <EndDate>
    And Select Include Complete and Search the Newly created record <PayCode> <Status> <Amount> <Frequency> <ApplytoAgent> <StartDate>
    And Validate the Records Returned on selecting NEW BUTTON with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode> <Status> <Amount> <Frequency> <ApplytoAgent> <StartDate1>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | AgentCode | PayCode | Status   | Frequency | Amount | ApplytoAgent | StartDate    | StartDate1   | EndDate      | environment | browser  | username         | password      | tableName                               |
    #  | "BCO"    | "AA"    | "ACTIVE" | "W"       | "3.33" | "BCO"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
     # | "HCI"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "HCI"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhstaging" | "chrome" | "SMRITITEST"     | "Legendary@1" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "DBI"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "DBI"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhstaging" | "chrome" | "SMRITITEST"     | "Legendary@1" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "HCI"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "HCI"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch"  | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
     # | "DBI"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "DBI"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch"  | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "ABC"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "ABC"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "ZSV"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "ZSV"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
     # | "XHN"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "XHN"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    # | "CCI"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "CCI"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "AKT"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "AKT"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    ##  | "ABH"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "ABH"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
#ABH showing 5 on apply to agent dropdown
    #  | "AEP"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "AEP"        | "2023-02-25" | "02/25/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
#AEP showing 3 on apply to agent dropdown
    #  | "PHL"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "PHL"        | "2023-03-09" | "03/09/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "CNJ"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "CNJ"        | "2023-03-10" | "03/10/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
      | "CHT"     | "AB"    | "ACTIVE" | "S"       | "3.33" | "CHT"        | "2023-03-19" | "03/19/2023" | "05/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |

    #Enter new AgentCode/ApplyToAgent/StartDate/EndDate everytime before running test

  #36
  @Regression @ScenarioOnNewMaxLimitandTotaltoDate @FailureScreenShot2
  Scenario Outline: (SET-2295) Validate on NEW - MAX LIMIT and TOTAL TO DATE on Agent Settlement Adjustments Detail
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode> and Click on Search
    And Select the New Button, Enter the required fields <PayCode> <Status> <Frequency> <Amount> <ApplytoAgent>
    And Enter an End Date that is after the Start Date and not in the past <StartDate> <EndDate>
    And Enter the Max Limit to a value Less Than current Total to Date, Select the Save button <MaxLimit1> <TotalToDate>
    And Select Include Complete and search for the newly created record <PayCode> <Status> <Amount> <MaxLimit1> <TotalToDate> <StartDate>
    And Validate the Records Returned when Max Limit is LESS THAN with Database Record <environment> <tableName> and <VendorCode> <PayCode> <ApplytoAgent> <Frequency> <Amount> <MaxLimit1> <StartDate> <TotalToDate>
    And Select the First Edit Button on Action Column on Include Complete
    And Change the Max Limit to a value = Current Total to Date, Select the Save button <MaxLimit2>
    And Search for the newly created record <PayCode> <Status1> <Amount> <MaxLimit2> <TotalToDate> <StartDate>
    And Validate the Records Returned when Max Limit is EQUALS TO with Database Record <environment> <tableName> and <VendorCode> <PayCode> <ApplytoAgent> <Frequency> <Amount> <MaxLimit2> <TotalToDate> <StartDate>
    And Select the First Edit Button on Action Column on Include Complete again
    And Change the Max Limit to a Value Greater Than > current Total to Date, Select the Save button <MaxLimit3> <Status>
    And Select Include Complete and search for the newly created record <PayCode> <Status> <Amount> <MaxLimit3> <TotalToDate> <StartDate>
    And Validate the Records Returned when Max Limit is GREATER THAN with Database Record <environment> <tableName> and <VendorCode> <PayCode> <ApplytoAgent> <Frequency> <Amount> <MaxLimit3> <TotalToDate> <StartDate>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | StartDate    | EndDate      | PayCode | Status   | Status1    | Frequency | Amount  | ApplytoAgent | MaxLimit1 | TotalToDate | MaxLimit2 | MaxLimit3 | environment | browser  | username         | password      | tableName                               |
    #  | "10000"    | "02/27/2023" | "05/30/2023" | "AD"    | "ACTIVE" | "COMPLETE" | "W"       | "20.00" | "BAL"        | "1.50"    | "2.50"      | "2.50"    | "4.50"    | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
      | "6257"     | "03/10/2023" | "05/30/2023" | "AD"    | "ACTIVE" | "COMPLETE" | "W"       | "20.00" | "AAR"        | "1.50"    | "2.50"      | "2.50"    | "4.50"    | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |



   @MaxEdit @FailureScreenShot2
  Scenario Outline: (SET-2295) Validate on NEW - MAX LIMIT and TOTAL TO DATE on Agent Settlement Adjustments Detail
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode> and Click on Search
    And Select Include Complete and search for the newly created record <PayCode> <Status1> <Amount> <MaxLimit3> <TotalToDate> <StartDate>
    And Select the First Edit Button on Action Column on Include Complete again
    And Change the Max Limit to a Value Greater Than > current Total to Date, Select the Save button <MaxLimit3> <Status>
  #  And Search for the newly created record <PayCode> <Status> <Amount> <MaxLimit3> <TotalToDate> <StartDate>
     And Select Include Complete and search for the newly created record <PayCode> <Status> <Amount> <MaxLimit3> <TotalToDate> <StartDate>
    And Validate the Records Returned when Max Limit is GREATER THAN with Database Record <environment> <tableName> and <VendorCode> <PayCode> <ApplytoAgent> <Frequency> <Amount> <MaxLimit3> <TotalToDate> <StartDate>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | StartDate    | EndDate      | PayCode | Status   | Status1    | Frequency | Amount  | ApplytoAgent | MaxLimit1 | TotalToDate | MaxLimit2 | MaxLimit3 | environment | browser  | username         | password      | tableName                               |
      | "10000"    | "02/27/2023" | "05/30/2023" | "AD"    | "ACTIVE" | "COMPLETE" | "W"       | "20.00" | "BAL"        | "1.50"    | "2.50"      | "2.50"    | "4.50"    | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "6257"     | "02/27/2023" | "05/30/2023" | "AD"    | "ACTIVE" | "COMPLETE" | "W"       | "20.00" | "AAR"        | "1.50"    | "2.50"      | "2.50"    | "4.50"    | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |









       #37
  @Regression @AgentSettlementAdjustmentsVendorCodePayCodeOrderNo  @FailureScreenShot2
  Scenario Outline: (SET-2295) Validate - VENDOR CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO in Agent Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode>, Start Date From <startDateFrom>, Start Date To <startDateTo> and Click on Search Button
    And Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <startDateFrom> <startDateTo>
    And Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <startDateFrom> <startDateTo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <startDateFrom> <startDateTo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <startDateFrom> <startDateTo>
    And Enter Vendor Code, Pay Code <PayCode>, Start Date From, Start Date To and Click on Search Button
    And Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode1> <startDateFrom> <startDateTo>
    And Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode1> <startDateFrom> <startDateTo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode1> <startDateFrom> <startDateTo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode1> <startDateFrom> <startDateTo>
    And Enter Vendor Code, Pay Code and Order Number <OrderNo>, Start Date From, Start Date To and Click on Search Button
    And Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode1> <OrderNo> <startDateFrom> <startDateTo>
    And Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode1> <OrderNo> <startDateFrom> <startDateTo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode1> <OrderNo> <startDateFrom> <startDateTo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode1> <OrderNo> <startDateFrom> <startDateTo>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | startDateFrom | startDateTo  | PayCode                          | PayCode1 | OrderNo     | environment | browser  | username         | password      | tableName                               |
      | "10008"    | "04/10/2022"  | "03/30/2023" | "DM - PER DIEM/M & R DEDUCTIONS" | "DM"     | "YNN123456" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
   #   | "99999"    | "CH - CHASSIS USAGE DEDUCTION" | "CH"     | "HCI113974" | "ebhprod"   | "chrome" | "SMRITIDHUNGANA" | "Smr1tiD@2022" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" |


    #38
  @Regression @AgentSettlementAdjustmentsAgentCodePayCodeOrderNo  @FailureScreenShot2
  Scenario Outline: (SET-2295) Validate - AGENT CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO in Agent Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Agent Code <AgentCode>, Start Date From <startDateFrom>, Start Date To <startDateTo> and Click on Search Button
    And Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <startDateFrom> <startDateTo>
    And Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <startDateFrom> <startDateTo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <startDateFrom> <startDateTo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <startDateFrom> <startDateTo>
    And Enter Agent Code, Pay Code <PayCode>, Start Date From, Start Date To and Click on Search Button
    And Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1> <startDateFrom> <startDateTo>
    And Get Records when RECURRING ONLY is SELECTED, Validate Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1> <startDateFrom> <startDateTo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1> <startDateFrom> <startDateTo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1> <startDateFrom> <startDateTo>
    And Enter Agent Code, Pay Code and Order Number <OrderNo>, Start Date From, Start Date To and Click on Search Button
    And Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1> <OrderNo> <startDateFrom> <startDateTo>
    And Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1> <OrderNo> <startDateFrom> <startDateTo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1> <OrderNo> <startDateFrom> <startDateTo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1> <OrderNo> <startDateFrom> <startDateTo>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | AgentCode | startDateFrom | startDateTo  | PayCode                          | PayCode1 | OrderNo     | environment | browser  | username         | password      | tableName                               |
      | "YNN"     | "04/10/2022"  | "03/30/2023" | "DM - PER DIEM/M & R DEDUCTIONS" | "DM"     | "YNN123456" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
   #   | "HCI"     | "CH - CHASSIS USAGE DEDUCTION" | "CH"     | "HCI113974" | "ebhprod"   | "chrome" | "SMRITIDHUNGANA" | "Smr1tiD@2022" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" |


    #39
  @Regression @ScenarioViewButton @FailureScreenShot2
  Scenario Outline: (SET-2295) Validate records on VIEW - on Agent Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode> and Click on Search
    And Select the First View Button on Action Column
    And Confirm the company code, vendor code, all locations and vendor name are displayed in header in blue font
    And Select the 'X' on form
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | environment | browser  | username         | password      |
      | "10000"    | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" |
  #    | "99999"    | "ebhprod"   | "chrome" | "SMRITIDHUNGANA" | "Smr1tiD@2022" |


     #40
  @Regression @ScenarioDeleteButton @FailureScreenShot2
  Scenario Outline: (SET-2295) Validate records on DELETE - on Agent Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode> and Click on Search
    And Click on Status, Inactive <Status>
    And Validate the Records Returned for Delete with Database Record <environment> <tableName> and <VendorCode> <Status> <ApplyToAgent> <PayCode>
  #  And Select the First Delete Button on Action Column
  #  And Validate the Records Returned for Delete on Hold with Database Record <environment> <tableName> and <VendorCode> <Status1> <ApplyToAgent>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | Status     | ApplyToAgent | PayCode | environment | browser  | username         | password      | tableName                               |
      | "10000"    | "INACTIVE" | "WCC"        | "AA"    | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
  #    | "99999"    | "INACTIVE" | "BCI"        |  ""             | "ebhprod"   | "chrome" | "SMRITIDHUNGANA" | "Smr1tiD@2022" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" |


    #41
  @Regression @ScenarioReport @FailureScreenShot2
  Scenario Outline: (SET-2295) Validate records on REPORT - on Agent Settlement Adjustments
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode>, Start Date From <startDateFrom>, Start Date To <startDateTo> and Click on Search Button
    And Select the Report button
    And Get Excel Report from Downloads
    And Validate Excel Report with Database Record <environment> <tableName> and <VendorCode> <tableName1>
    Then Close all open Browsers for Agent Settlement Adjustments
    Examples:
      | VendorCode | startDateFrom | startDateTo  | environment | browser  | username         | password      | tableName                               | tableName1                                       |
      | "10008"    | "04/10/2022"  | "03/30/2023" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "[EBHLaunch].[dbo].[usp_GetAgentRecordForExcel]" |
  #    | "99999"    | "ebhprod"   | "chrome" | "SMRITIDHUNGANA" | "Smr1tiD@2022" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[usp_GetAgentRecordForExcel]" |


     #42
  @Regression @ScenarioQuickEntry @FailureScreenShot2
  Scenario Outline: (SET-2295) Validate QUICK ENTRY on Agent Settlement Adjustments Detail
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode> and Click on Search
    And Select Quick Entry
    And Enter the required fields <PayCode> <Amount> <ApplytoAgent> <StartDate> <OrderNo> <Notes>
    And Select Save, Click Yes on Conformation
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | PayCode | Amount | ApplytoAgent | StartDate    | OrderNo     | Notes              | environment | browser  | username         | password      |
      | "10008"    | "AA"    | "3.03" | "YNN"        | "01/18/2023" | "YNN128901" | "AB45678901234XYZ" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" |
   #   | "99999"    | "CH"    | "2.00" | "HCI"        | "09/25/2022" | "HCI113974" | "AB45678901234XYZ" | "ebhprod"   | "chrome" | "SMRITIDHUNGANA" | "Smr1tiD@2022" |