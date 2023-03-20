Feature: Equipment Console Agent Adjustments Process Feature - Agent Reimbursement, Agent Deduct and Bill Customer


    #43
  @Regression  @IdentifyINVOICE-AgentReimbursement/Deduction @FailureScreenShot4
  Scenario Outline: (EMGR-158) Identify INVOICE, for AGENT REIMBURSEMENT/ AGENT DEDUCTION Process on EQUIPMENT CONSOLE
    Given Locate a Record from Database, Agent Status must have a Status of Agent Review or Corp Review <Environment> <TableName1> <TableName2> <TableName3> <TableName4> <TableName5> <Agent>
    Examples:
      | Agent | Environment | TableName1                              | TableName2                        | TableName3                      | TableName4                   | TableName5                                  |
    #  | "HCI" | "ebhlaunch" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "[Evans].[dbo].[InvoiceRegister]" | "[EBHLaunch].[dbo].[LOCATIONS]" | "[EBHLaunch].[dbo].[ORDERS]" | "[EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO]" |
      | "AAR" | "ebhlaunch" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "[Evans].[dbo].[InvoiceRegister]" | "[EBHLaunch].[dbo].[LOCATIONS]" | "[EBHLaunch].[dbo].[ORDERS]" | "[EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO]" |


    #44
  @Regression  @AgentReimbursement @FailureScreenShot4
  Scenario Outline: (EMGR-158) Verify AGENT REIMBURSEMENT Process on EQUIPMENT CONSOLE
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Equipment Console
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Equipment Console
    And Click on NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface
    And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus>
    And Select Agent <Agent> and ProNo <ProNum>
    And Select Agent Reimbursement on Status <Status>
    And Enter Amount OR # of Days, Effective Date = Todays Date <Amount> <EffectiveDate>
    And Verify Notes Column has the Customer Number, Chassis No and Container No, prefilled in the notes column
    And Enter something into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No <Notes>
    And Verify previously entered data remained the same, Select Go, Select Yes, Main Form appears
    And Query Data in Agent Adjustments Table, There should be no record in this table for this transaction <Environment> <TableName> <TableName1> <EffectiveDate> <OrderNum>
    And Select Corp Status = Corp Review for that same record that has Agent Status = Agent Reimbursement and Corp Status = Corp Review <CorpStatus> <AgentStatus1> <Agent> <ProNum>
    And Select Agent Reimbursement on CropReview
    And The Days, Amount and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date <OfDays>
    And Verify Notes Column has the Customer Number, Chassis No and Container No, prefilled in the Notes Column
    And Enter something into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No <Notes1>
    And Verify previously entered data remained the same, Select Go, Select Yes, then Main Form Appears
    And Verify Agent Status and Corp Status = Agent Reimbursement in Main Form
    And Query Data in Agent_Adjustments SQL Table, There should be one record on the Agent_Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveDate> <OrderNum>
    Then Close all open Browsers for Equipment Console
    Examples:
      | InvoiceNo | AgentStatus   | Agent | ProNum   | OrderNum    | Status               | Amount | EffectiveDate | Notes                 | CorpStatus   | AgentStatus1         | OfDays | Notes1                       | Environment | TableName                              | TableName1                              | Environment1 | Browser  | Username | Password |
    #  | "2210040950DM" | "AgentReview" | "HCI" | "112771" | "HCI112771" | "AgentReimbursement" | "400"  | "12-18-2022"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
   #   | "2210040950DM" | "AgentReview" | "HCI" | "112774" | "HCI112774" | "AgentReimbursement" | "400"  | "12-19-2022"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "2210040950DM" | "AgentReview" | "HCI" | "112739" | "HCI112739" | "AgentReimbursement" | "400"  | "12-30-2022"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "2210040950DM"   | "AgentReview" | "HCI" | "112786" | "HCI112786" | "AgentReimbursement" | "400"  | "01-06-2023"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
     # | "JH230130NSC03" | "AgentReview" | "AAR" | "191231" | "AAR191231" | "AgentReimbursement" | "400"  | "02-02-2023"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "JH230130NSC04" | "AgentReview" | "AAR" | "191231" | "AAR191231" | "AgentReimbursement" | "400"  | "02-02-2023"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "2118143796" | "AgentReview" | "AAR" | "182104" | "AAR182104" | "AgentReimbursement" | "400"  | "02-03-2023"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
     # | "SRECBPA10" | "AgentReview" | "HCI" | "119318" | "HCI119318"  | "AgentReimbursement" | "400"  | "02-10-2023"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "110022"  | "AgentReview" | "QCS" | "112121" | "QCS112121" | "AgentReimbursement" | "400"  | "02-17-2023"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
   #   | "110022"  | "AgentReview" | "QCS" | "112249" | "QCS112249" | "AgentReimbursement" | "400"  | "02-17-2023"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "110022"  | "AgentReview" | "QCS" | "112265" | "QCS112265" | "AgentReimbursement" | "400"  | "02-24-2023"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "100000390761P"  | "AgentReview" | "SAV" | "310800" | "SAV310800" | "AgentReimbursement" | "400"  | "03-03-2023"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
      | "JH230131CSA03"  | "AgentReview" | "AAR" | "191231" | "AAR191231" | "AgentReimbursement" | "400"  | "03-10-2023"  | "Automation Testing," | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |

    #Change InvoiceNo/Agent/ProNum/OrderNum/EffectiveDate everytime before running Test from @IdentifyINVOICEforAgentReimbursement/Deduction


    #45
  @Regression  @IdentifyINVOICE-TestForAgentWithoutRecordOnAgentSettlementInfoTable-AgentReimbursement/Deduction @FailureScreenShot4
  Scenario Outline: (EMGR-158) Identify INVOICE, Test for Agent without a record on the Agent_Settlement_Info Table, AGENT REIMBURSEMENT/AGENT DEDUCTION
    Given Locate a Record to test from Database, Must have a Corp Status of Agent Review or Corp Review <Environment> <TableName1> <TableName2> <TableName3> <TableName4>
    Examples:
      | Environment | TableName1                              | TableName2                        | TableName3                      | TableName4                                  |
      | "ebhlaunch" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "[Evans].[dbo].[InvoiceRegister]" | "[EBHLaunch].[dbo].[LOCATIONS]" | "[EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO]" |


      #46
  @Regression  @AgentReimbursement/TestForAgentWithoutRecordOnAgentSettlementInfoTable @FailureScreenShot4
  Scenario Outline: (EMGR-158) Test for Agent without a record on the Agent_Settlement_Info table, AGENT REIMBURSEMENTS
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Equipment Console
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Equipment Console
    And Click on NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface and Click Refresh
    And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus>
    And Select Agent <Agent> and ProNo <ProNum>
    And Select a record that has a Corp Status = Corp Review or Agent Review <CorpStatus>
    And Select Agent Reimbursement
    And Verify Notes Column has the Customer Number, Chassis No and Container No, Prefilled in the notes column
    And Enter Amount OR Days, Effective Date = Todays Date <Amount> <EffectiveDate>
    And Enter something into the Notes Column <Notes> and Select Go, Select Yes, Select OK
    And Select X to Close Agent Reimbursement Form
    And Conform Equipment Console Main Form is Displayed and Agent Status and Corp Status remained the same
    Then Close all open Browsers for Equipment Console
    Examples:
      | InvoiceNo          | Agent | ProNum   | AgentStatus          | Amount | EffectiveDate | CorpStatus   | Notes                     | Environment1 | Browser  | Username | Password |
      | "JH202204081615DM" | "CTW" | "147915" | "AgentReimbursement" | "300"  | "03-30-2023"  | "CorpReview" | "Automation Testing Done" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |

        #Change EffectiveDate everytime before running Test

    #47
  @Regression  @AgentDeduction @FailureScreenShot4
  Scenario Outline: (EMGR-159) Verify AGENT DEDUCTION Process on EQUIPMENT CONSOLE
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Equipment Console
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Equipment Console
    And Click on NO on alert
    And Enter the Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface
    And Select Agent Status Button for a record that has Agent Status = Agent Review or Corp Review <AgentStatus>
    And Select the Agent <Agent> and ProNo <ProNum>
    And Select Agent Deduct on Agent Review Status <Status>
    And Enter Amount Or # of Days, Effective Date = Todays Date, Splits 1 <Amount> <EffectiveDate>
    And Verify Notes Column has the Customer Number, Chassis No and Container No, Prefilled in the Notes Column
    And Enter Notes into the Notes Column and make sure to enter Comma, Select Ok, Select Go, Select No <Notes>
    And Verify previously entered data remained Same, Select Go, Select Yes, Main Form appears
    And Query Data in Agent Adjustments Table, There should be no Record in this table for this transaction <Environment> <TableName> <TableName1> <EffectiveDate> <OrderNum>
    And Select Corp Status = Corp Review for the Same Record, that has the Agent Status = Agent Deduct and Corp Status = Corp Review <CorpStatus> <AgentStatus1> <Agent> <ProNum>
    And Select Agent Deduct on Corp Review Status <Status>
    And The Days, Amount and Notes Column are filled in with the same information that was previously entered. Enter different Amount, Days or Effective Date <OfDays> <Splits> <EffectiveDate>
    And Verify Notes Column has the Customer Number, Chassis No and Container No, already prefilled in the Notes Column
    And Enter Notes <Notes1> into the Notes Column, make sure to enter a Comma, Select Ok, Select Go, Select No
    And Verify previously entered data remained the Same, Select Go, Select Yes, Main Form Appears
    And Verify Agent Status and Corp Status = Agent Deduct in Main Form
    And Query Data in Agent_Adjustments SQL Table for this transaction <Environment> <TableName> <TableName1> <EffectiveDate> <OrderNum>
    Then Close all open Browsers for Equipment Console
    Examples:
      | InvoiceNo | AgentStatus   | Agent | ProNum   | OrderNum    | Status        | Amount | EffectiveDate | Notes                 | CorpStatus   | AgentStatus1  | OfDays | Notes1                     | Splits | Environment | TableName                              | TableName1                              | Environment1 | Browser  | Username | Password |
    #  | "2210040950DM" | "AgentReview" | "HCI" | "112776" | "HCI112776" | "AgentDeduct" | "800"  | "12-19-2022"  | "Automation Testing," | "CorpReview" | "AgentDeduct" | "4"    | " Automation Testing !!!," | "5"    | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "2210040950DM" | "AgentReview" | "HCI" | "112775" | "HCI112775" | "AgentDeduct" | "800"  | "12-30-2022"  | "Automation Testing," | "CorpReview" | "AgentDeduct" | "4"    | " Automation Testing !!!," | "5"    | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
     # | "2210040950DM" | "AgentReview" | "HCI" | "112791" | "HCI112791" | "AgentDeduct" | "800"  | "01-06-2023"  | "Automation Testing," | "CorpReview" | "AgentDeduct" | "4"    | " Automation Testing !!!," | "5"    | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
     # | "JH230130NSC06" | "AgentReview" | "AAR" | "191231" | "AAR191231" | "AgentDeduct" | "800"  | "02-03-2023"  | "Automation Testing," | "CorpReview" | "AgentDeduct" | "4"    | " Automation Testing !!!," | "5"    | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
     # | "SRECBPA11" | "AgentReview" | "HCI" | "119318" | "HCI119318" | "AgentDeduct" | "800"  | "02-10-2023"  | "Automation Testing," | "CorpReview" | "AgentDeduct" | "4"    | " Automation Testing !!!," | "5"    | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "110022"  | "AgentReview" | "QCS" | "112242" | "QCS112242" | "AgentDeduct" | "800"  | "02-17-2023"  | "Automation Testing," | "CorpReview" | "AgentDeduct" | "4"    | " Automation Testing !!!," | "5"    | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
   #   | "110022"  | "AgentReview" | "QCS" | "112377" | "QCS112377" | "AgentDeduct" | "800"  | "02-24-2023"  | "Automation Testing," | "CorpReview" | "AgentDeduct" | "4"    | " Automation Testing !!!," | "5"    | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "100000390817P"  | "AgentReview" | "SAV" | "311119" | "SAV311119" | "AgentDeduct" | "800"  | "03-03-2023"  | "Automation Testing," | "CorpReview" | "AgentDeduct" | "4"    | " Automation Testing !!!," | "5"    | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
      | "JH230131CSA06"  | "AgentReview" | "AAR" | "191231" | "AAR191231" | "AgentDeduct" | "800"  | "03-10-2023"  | "Automation Testing," | "CorpReview" | "AgentDeduct" | "4"    | " Automation Testing !!!," | "5"    | "ebhlaunch" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |

        #Change InvoiceNo/Agent/ProNum/OrderNum/EffectiveDate everytime before running Test from @IdentifyINVOICEforAgentReimbursement/Deduction


    #48
  @Regression  @AgentDeduction/TestForAgentWithoutRecordOnAgentSettlementInfoTable @FailureScreenShot4
  Scenario Outline: (EMGR-159) Test for Agent without a record on the Agent_Settlement_Info table, AGENT DEDUCTION
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Equipment Console
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Equipment Console
    And Click on NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console and Click Refresh
    And Select a record that has a Corp Status = Corp Review or Agent Review <CorpStatus>
    And Select Agent Deduct
    And Verify Notes Column has the Customer No, Chassis No and Container No, Prefilled in the Notes Column
    And Enter Amount OR No of Days, Effective Date = Todays Date <Amount> <EffectiveDate>
    And Enter some notes into the Notes Column <Notes> and Select Go, Select Yes, Select OK
    And Select X to Close Agent Deduction Form
    And Conform Equipment Console Main Form is Displayed and Agent Status and Corp Status remained the Same
    Then Close all open Browsers for Equipment Console
    Examples:
      | InvoiceNo          | Amount | EffectiveDate | CorpStatus   | Notes                     | Environment1 | Browser  | Username | Password |
      | "JH202204081630CH" | "450"  | "03-30-2023"  | "CorpReview" | "Automation Testing Done" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |

    #Change EffectiveDate everytime before running Test



    #49a
  @Regression  @VerifyTaxAndAdminBillingCodesHaveBeenSetup&IdentifyINVOICE-BillCustomer @FailureScreenShot4
  Scenario Outline: (EMGR-132) Verify TAX and ADMIN BILLING CODES have been setup and Identify INVOICE for BILL CUSTOMER on EQUIPMENT CONSOLE
    Given Locate a Record to Verify TAX and ADMIN BILLING CODES have been setup for Bill Customer from Database SQL-One <Environment> <TableName>
    Then Locate a Record to Identify INVOICE for Bill Customer from Database SQL-Two <Environment> <TableName1> <TableName2> <TableName3> <TableName4> <TableName5> <TableName6>
    Examples:
      | Environment | TableName                           | TableName1                             | TableName2                              | TableName3                                  | TableName4                        | TableName5                   | TableName6                      |
      | "ebhlaunch" | "[EBHLaunch].[dbo].[Billing_Codes]" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "[EBHLaunch].[dbo].[Agent_Settlement_Info]" | "[Evans].[dbo].[InvoiceRegister]" | "[EBHLaunch].[dbo].[Orders]" | "[EBHLaunch].[dbo].[Locations]" |


    #49b
  @Regression  @BillCustomer @FailureScreenShot4
  Scenario Outline: (EMGR-132) BILL CUSTOMER with Different Billing Codes, Multiple Billing Line Items, Tax, Admin Fee's and Change the Bill To on the Form to a different Bill To Number, and Create Order with a Suffix on EQUIPMENT CONSOLE
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Equipment Console
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Equipment Console
    And Click on NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface
    And Select a Record that has a Agent Status and Corp Status <AgentStatus> <CorpStatus> = Corp Review or Agent Review
    And Select the Agent <Agent> and ProNo <ProNum>
    And Select Bill Customer on Corp Review Status <Status>
    And Enter all the information in Bill Customer Form, Select Go, Select Yes <BilledToID> <BillingCode> <NoofDays> <Rate> <NoofDays2> <Rate2> <AdminFee> <Notes> <BillDetails>
    And Verify Bill is created for ProNo <ProNum>
    Then Close all open Browsers for Equipment Console
    Examples:
      | InvoiceNo       | AgentStatus   | CorpStatus    | Agent | ProNum   | Status         | BilledToID | BillingCode | NoofDays | Rate | NoofDays2 | Rate2 | AdminFee | Notes                    | BillDetails   | Environment1 | Browser  | Username | Password |
   #   | "JH2112010906CH" | "AgentReview" | "AgentReview" | "HCI" | "120105" | "BillCustomer" | "FEDBRCA" |  "DCC"     |    "7"   |  "8" |    "6"    |  "6"  |   "18"   | "Automation Testing !!!" | "Testing !!!" |  "eclaunch"  | "chrome" |  "eqpb" |  "taffy"  |
   #   | "JH2109291100DM" | "AgentReview" | "AgentReview" | "AAR" | "192758" | "BillCustomer" | "FEDBRCA"  | "DCC"       | "7"      | "8"  | "6"       | "6"   | "18"     | "Automation Testing !!!" | "Testing !!!" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "JH2109290915CH" | "AgentReview" | "AgentReview" | "ABN" | "124794" | "BillCustomer" | "FEDBRCA"  | "DCC"       | "7"      | "8"  | "6"       | "6"   | "18"     | "Automation Testing !!!" | "Testing !!!" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
   #   | "JH2109291105DM" | "AgentReview" | "AgentReview" | "AAR" | "192758" | "BillCustomer" | "FEDBRCA"  | "DCC"       | "7"      | "8"  | "6"       | "6"   | "18"     | "Automation Testing !!!" | "Testing !!!" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "JH2109291105DM" | "AgentReview" | "AgentReview" | "AAR" | "193564" | "BillCustomer" | "FEDBRCA"  | "DCC"       | "7"      | "8"  | "6"       | "6"   | "18"     | "Automation Testing !!!" | "Testing !!!" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
     # | "JH2111050800DM" | "AgentReview" | "AgentReview" | "AJD" | "113476" | "BillCustomer" | "FEDBRCA"  | "DCC"       | "7"      | "8"  | "6"       | "6"   | "18"     | "Automation Testing !!!" | "Testing !!!" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "JH230130NSC08" | "AgentReview" | "AgentReview" | "AAR" | "191231" | "BillCustomer" | "FEDBRCA"  | "DCC"       | "7"      | "8"  | "6"       | "6"   | "18"     | "Automation Testing !!!" | "Testing !!!" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "JH230130NSC08" | "AgentReview" | "AgentReview" | "AAR" | "191231" | "BillCustomer" | "FEDBRCA"  | "DCC"       | "7"      | "8"  | "6"       | "6"   | "18"     | "Automation Testing !!!" | "Testing !!!" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "JH230130NSC09" | "AgentReview" | "AgentReview" | "AAR" | "191231" | "BillCustomer" | "FEDBRCA"  | "DCC"       | "7"      | "8"  | "6"       | "6"   | "18"     | "Automation Testing !!!" | "Testing !!!" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    #  | "JH230131MSA06" | "AgentReview" | "AgentReview" | "ABC" | "102008" | "BillCustomer" | "FEDBRCA"  | "DCC"       | "7"      | "8"  | "6"       | "6"   | "18"     | "Automation Testing !!!" | "Testing !!!" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
    # | "JH230131CSA12" | "AgentReview" | "AgentReview" | "AAR" | "191231" | "BillCustomer" | "FEDBRCA"  | "DCC"       | "7"      | "8"  | "6"       | "6"   | "18"     | "Automation Testing !!!" | "Testing !!!" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
      | "JH230131CSA14" | "AgentReview" | "AgentReview" | "AAR" | "191231" | "BillCustomer" | "FEDBRCA"  | "DCC"       | "7"      | "8"  | "6"       | "6"   | "18"     | "Automation Testing !!!" | "Testing !!!" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |

            #Change InvoiceNo/Agent/ProNum everytime before running Test from @VerifyTaxAndAdminBillingCodesHaveBeenSetup&IdentifyINVOICE-BillCustomer


    #49c
  @Regression @RetrieveNewOrderPRo
  Scenario Outline: (EMGR-132) Retrieve the NEW ORDER PRO (Order w/Suffix) from Chassis BILL CUSTOMER Record
    Given Verify Order Pro is created in Chassis Bill Customer Record, and Retrieve the NEW ORDER PRO from Database SQL-Three <Environment> <TableName1> <OriginalProNumber>
    Examples:
      | Environment | TableName1                                  | OriginalProNumber |
   #   | "ebhlaunch" | "[Evans].[dbo].[ChassisBillCustomerRecord]" |    "HCI120105%"   |
    #  | "ebhlaunch" | "[Evans].[dbo].[ChassisBillCustomerRecord]" | "AAR192758%"      |
     # | "ebhlaunch" | "[Evans].[dbo].[ChassisBillCustomerRecord]" | "ABN124794%"      |
    #  | "ebhlaunch" | "[Evans].[dbo].[ChassisBillCustomerRecord]" | "AAR192758%"      |
     # | "ebhlaunch" | "[Evans].[dbo].[ChassisBillCustomerRecord]" | "AAR193564%"      |
      | "ebhlaunch" | "[Evans].[dbo].[ChassisBillCustomerRecord]" | "AAR191231%"      |

                #Change OriginalProNumber% everytime before running Test from @BillCustomer


    #49d
  @Regression  @VerifyDataInTables @FailureScreenShot4
  Scenario Outline: (EMGR-132) Verify Data in Tables for BILL CUSTOMER
    Given Verify Data on Orders Table, Enter ORIGINAL PRO and NEW PRO and Retrieve records from Database SQL-Four <Environment> <TableName> <TableName3> <TableName4> <OriginalProNumber> <ProNumberWithSuffix>
    And Verify Data on Order_Billing Table, Enter ORDER ID from NEW PRO NUMBER and Retrieve records from Database SQL-Five <Environment> <TableName> <TableName5> <TableName6> <ProNumberWithSuffix>
    And Verify Data on Order_Refs Table, Enter ORDER ID from ORIGINAL PRO NUMBER and NEW PRO NUMBER and Retrieve records from Database SQL-Six <Environment> <TableName> <TableName7> <OriginalProNumber> <ProNumberWithSuffix>
    And Verify Data on Order_Ops Table, Enter ORDER ID from ORIGINAL PRO NUMBER and NEW PRO NUMBER then Retrieve records from Database SQL-Seven <Environment> <TableName> <TableName8> <OriginalProNumber> <ProNumberWithSuffix>
    And Verify Data on Order_Misc Table, Enter ORDER ID from ORIGINAL PRO NUMBER and NEW PRO NUMBER and then Retrieve records from Database SQL-Eight <Environment> <TableName> <TableName9> <OriginalProNumber> <ProNumberWithSuffix>
    And Verify Data on Order_Action_History Table, Enter ORDER ID from NEW PRO NUMBER, and Retrieve Records from Database SQL-Nine <Environment> <TableName> <TableName10> <ProNumberWithSuffix>
    And Verify Data on Order_Notes Table, Enter ORDER ID from NEW PRO NUMBER and then Retrieve records from Database SQL-Ten <Environment> <TableName> <TableName11> <ProNumberWithSuffix>
    And Verify Data on Order_Billing_Com Table, Enter ORDER ID from NEW PRO NUMBER then Retrieve records from Database SQL-Eleven <Environment> <TableName> <TableName12> <ProNumberWithSuffix>
    And Verify Record was added to Agent_Stl_Trans Table, Enter NEW PRO NUMBER and Retrieve records from Database SQL-Twelve <Environment> <TableName1> <ProNumberWithSuffix>
    And Verify Record was added to the Agent_Settlements Table and Verify Calculations are correct, Enter NEW PRO NUMBER, then Retrieve records from Database SQL-Thirteen <Environment> <TableName2> <ProNumberWithSuffix>
    Examples:
      | OriginalProNumber | ProNumberWithSuffix | Environment | TableName                    | TableName1                            | TableName2                              | TableName3                     | TableName4                                  | TableName5                          | TableName6                          | TableName7                       | TableName8                      | TableName9                       | TableName10                                | TableName11                       | TableName12                              |
    #  | "AAR192758"       | "AAR192758A"        | "ebhlaunch" | "[EBHLaunch].[dbo].[Orders]" | "[EBHLaunch].[dbo].[AGENT_STL_TRANS]" | "[EBHLaunch].[dbo].[AGENT_Settlements]" | "[EBHLaunch].[dbo].[ACCOUNTS]" | "[EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBHLaunch].[dbo].[ORDER_BILLING]" | "[EBHLaunch].[dbo].[BILLING_CODES]" | "[EBHLaunch].[dbo].[ORDER_REFS]" | "[EBHLaunch].[dbo].[ORDER_Ops]" | "[EBHLaunch].[dbo].[Order_Misc]" | "[EBHLaunch].[dbo].[ORDER_Action_History]" | "[EBHLaunch].[dbo].[ORDER_Notes]" | "[EBHLaunch].[dbo].[ORDER_BILLLING_COM]" |
   #   | "ABN124794"       | "ABN124794A"        | "ebhlaunch" | "[EBHLaunch].[dbo].[Orders]" | "[EBHLaunch].[dbo].[AGENT_STL_TRANS]" | "[EBHLaunch].[dbo].[AGENT_Settlements]" | "[EBHLaunch].[dbo].[ACCOUNTS]" | "[EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBHLaunch].[dbo].[ORDER_BILLING]" | "[EBHLaunch].[dbo].[BILLING_CODES]" | "[EBHLaunch].[dbo].[ORDER_REFS]" | "[EBHLaunch].[dbo].[ORDER_Ops]" | "[EBHLaunch].[dbo].[Order_Misc]" | "[EBHLaunch].[dbo].[ORDER_Action_History]" | "[EBHLaunch].[dbo].[ORDER_Notes]" | "[EBHLaunch].[dbo].[ORDER_BILLLING_COM]" |
   #   | "AAR192758"       | "AAR192758A"        | "ebhlaunch" | "[EBHLaunch].[dbo].[Orders]" | "[EBHLaunch].[dbo].[AGENT_STL_TRANS]" | "[EBHLaunch].[dbo].[AGENT_Settlements]" | "[EBHLaunch].[dbo].[ACCOUNTS]" | "[EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBHLaunch].[dbo].[ORDER_BILLING]" | "[EBHLaunch].[dbo].[BILLING_CODES]" | "[EBHLaunch].[dbo].[ORDER_REFS]" | "[EBHLaunch].[dbo].[ORDER_Ops]" | "[EBHLaunch].[dbo].[Order_Misc]" | "[EBHLaunch].[dbo].[ORDER_Action_History]" | "[EBHLaunch].[dbo].[ORDER_Notes]" | "[EBHLaunch].[dbo].[ORDER_BILLLING_COM]" |
    #  | "AAR193564"       | "AAR193564A"        | "ebhlaunch" | "[EBHLaunch].[dbo].[Orders]" | "[EBHLaunch].[dbo].[AGENT_STL_TRANS]" | "[EBHLaunch].[dbo].[AGENT_Settlements]" | "[EBHLaunch].[dbo].[ACCOUNTS]" | "[EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBHLaunch].[dbo].[ORDER_BILLING]" | "[EBHLaunch].[dbo].[BILLING_CODES]" | "[EBHLaunch].[dbo].[ORDER_REFS]" | "[EBHLaunch].[dbo].[ORDER_Ops]" | "[EBHLaunch].[dbo].[Order_Misc]" | "[EBHLaunch].[dbo].[ORDER_Action_History]" | "[EBHLaunch].[dbo].[ORDER_Notes]" | "[EBHLaunch].[dbo].[ORDER_BILLLING_COM]" |
      | "AAR191231"       | "AAR191231A"        | "ebhlaunch" | "[EBHLaunch].[dbo].[Orders]" | "[EBHLaunch].[dbo].[AGENT_STL_TRANS]" | "[EBHLaunch].[dbo].[AGENT_Settlements]" | "[EBHLaunch].[dbo].[ACCOUNTS]" | "[EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBHLaunch].[dbo].[ORDER_BILLING]" | "[EBHLaunch].[dbo].[BILLING_CODES]" | "[EBHLaunch].[dbo].[ORDER_REFS]" | "[EBHLaunch].[dbo].[ORDER_Ops]" | "[EBHLaunch].[dbo].[Order_Misc]" | "[EBHLaunch].[dbo].[ORDER_Action_History]" | "[EBHLaunch].[dbo].[ORDER_Notes]" | "[EBHLaunch].[dbo].[ORDER_BILLLING_COM]" |

 #Change OriginalProNumber/ProNumberWithSuffix everytime before running Test from @RetrieveNewOrderPRo



