Feature: Equipment Console Tractor Adjustments Process Feature - Tractor Reimbursement, Tractor Deduct and Split Amount

    #85
  @Regression @IdentifyInvoiceAndValidTractorNoForTractorReimbursement/Deduct @FailureScreenShot8
  Scenario Outline: (EMGR-242) Identify INVOICE NUMBER and VALID TRACTOR NUMBER for TRACTOR REIMBURSEMENT/DEDUCT Process on EQUIPMENT CONSOLE
    Given Locate a Record from Database for Tractor Reimbursement, Tractor Deduct <Environment> <TableName> <TableName1> <TableName2> <TableName3> <TableName4> <TableName5> <Location>
    And Locate a Record from Database for Valid Tractor Number for Tractor Reimbursement, Tractor Deduct <Environment> <TableName6> <Location>
    Examples:
      | Location | Environment  | TableName                               | TableName1                       | TableName2                            | TableName3                        | TableName4             | TableName5                | TableName6                     |
      | "AAR"    | "ebhstaging" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBH].[dbo].[Agent_Settlements]" | "[EBH].[dbo].[Orders]" | "[EBH].[dbo].[Locations]" | "[EBH].[dbo].[TRACTOR_VENDOR]" |
     # | "ZPP"    | "ebhstaging" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBH].[dbo].[Agent_Settlements]" | "[EBH].[dbo].[Orders]" | "[EBH].[dbo].[Locations]" | "[EBH].[dbo].[TRACTOR_VENDOR]" |


    #86
  @Regression @TractorReimbursement @FailureScreenShot8
  Scenario Outline: (EMGR-242) Verify TRACTOR REIMBURSEMENT Process on EQUIPMENT CONSOLE
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface for Tractor
    And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus> for Tractor
    And Select Agent <Agent> and ProNo <ProNum> for Tractor
    And Select DriverReimbursement on Status <Status>
    And Enter a Valid Tractor No <TractorNo>
    And Enter Amount or # of Days, Effective Date = Todays Date <Amount> <EffectiveCreatedDate>
    And Verify Notes Column has the Customer Number and Agent, prefilled in the notes column
    And Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No <Notes>
    And Verify previously entered data remained same for Driver Reimbursement, Select Go, Select Yes, Main Form appears
    And Query Data in Tractor Adjustments Table, There should be no record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <OrderNum> <TractorNo> <Username>
    Then Click on Clear Column Filters
    And Select Corp Status = Corp Review for that same record that has Agent Status = DriverReimbursement and Corp Status = Corp Review for Driver Reimbursement <CorpStatus> <AgentStatus1> <Agent> <ProNum>
    And Select DriverReimbursement on CropReview
    And Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date <OfDays>
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column
    And Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No <Notes1>
    And Verify previously entered data remained same for Driver Reimbursement Corp Review, Select Go, Select Yes, Main Form Appears
    And Verify Agent Status and Corp Status = DriverReimbursement in Main Form
    And Query Data in Tractor Adjustments SQL Table, There should be one record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <OrderNum> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo    | AgentStatus   | Agent | ProNum   | OrderNum    | TractorNo | Status                | Amount | EffectiveCreatedDate | Notes                 | CorpStatus   | AgentStatus1          | OfDays | Notes1                       | Environment  | TableName                           | TableName1                     | Environment1 | Browser  | Username | Password |
    #  | "10054642" | "AgentReview" | "AAR" | "193602" | "AAR193602" | "160664"  | "DriverReimbursement" | "220"  | "02-02-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "10054741" | "AgentReview" | "AAR" | "194378" | "AAR194378" | "160664"  | "DriverReimbursement" | "220"  | "02-03-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "2119352925" | "AgentReview" | "AAR" | "194233" | "AAR194233" | "160664"  | "DriverReimbursement" | "220"  | "02-10-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
   #   | "2119375386" | "AgentReview" | "AAR" | "193514" | "AAR193514" | "160664"  | "DriverReimbursement" | "220"  | "02-17-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
  #    | "DCZZ1937637" | "AgentReview" | "AAR" | "192544" | "AAR192544" | "160664"  | "DriverReimbursement" | "220"  | "02-24-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "DCZZ1937637" | "AgentReview" | "AAR" | "192544" | "AAR192544" | "160664"  | "DriverReimbursement" | "220"  | "02-24-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "DZZZ1922009" | "AgentReview" | "AAR" | "191870" | "AAR191870" | "160664"  | "DriverReimbursement" | "220"  | "03-03-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
      | "DZZZ1932748" | "AgentReview" | "AAR" | "192797" | "AAR192797" | "160664"  | "DriverReimbursement" | "220"  | "03-10-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |

    #Change InvoiceNo/Agent/ProNum/OrderNum/TractorNo/EffectiveCreatedDate everytime before running Test from @IdentifyInvoiceAndValidTractorNoForTractorReimbursement/Deduct


    #87
  @Regression  @TractorReimbursement/TractorWithoutRecordOnTractorVendorTable @FailureScreenShot8
  Scenario Outline: (EMGR-242) Test for Tractor without a record on the Tractor_Vendor Table, TRACTOR REIMBURSEMENT
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface for Tractor
    And Select Corp Status = Corp Review for that same record that has Agent Status = DriverReimbursement and Corp Status = Corp Review for Driver Reimbursement <CorpStatus> <AgentStatus1> <Agent> <ProNum>
    And Select DriverReimbursement on CropReview
    And Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered. Enter Effective Date <EffectiveCreatedDate>
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column
    And Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No <Notes1>
    And Verify previously entered data remained same for Driver Reimbursement Corp Review, Select Go, Select Yes, Select Ok
    And Verify Agent Status = DriverReimbursment and Corp Status = CorpReview in Main Form
    And Query Data in Tractor Adjustments Table, There should be no record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <OrderNum> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo  | Agent | ProNum   | OrderNum    | TractorNo | EffectiveCreatedDate | CorpStatus   | AgentStatus1          | Notes1                       | Environment  | TableName                           | TableName1                     | Environment1 | Browser  | Username | Password |
      | "10054642" | "AAR" | "192447" | "AAR192447" | "123456"  | "03-30-2023"         | "CorpReview" | "DriverReimbursement" | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |

    #Change EffectiveCreatedDate everytime before running Test


    #88
  @Regression @TractorReimbursement/TractorNotCurrentlyAssignedToAgentOnDriverReimbursementForm @FailureScreenShot8
  Scenario Outline: (EMGR-242) Test for Tractor NOT currently assigned to the Agent on the DRIVER REIMBURSEMENT Form (Trac_Vend_Acctng_Status <> Current)
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface for Tractor
    And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus> for Tractor
    And Select Agent <Agent> and ProNo <ProNum> for Tractor
    And Select DriverReimbursement on Status <Status>
    And Enter a Valid Tractor No <TractorNo>
    And Enter Amount or # of Days, Effective Date = Todays Date <Amount> <EffectiveCreatedDate>
    And Verify Notes Column has the Customer Number and Agent, prefilled in the notes column
    And Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No <Notes>
    And Verify previously entered data remained same for Driver Reimbursement, Select Go, Select Yes, Main Form appears
    And Query Data in Tractor Adjustments Table, There should be no record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <OrderNum> <TractorNo> <Username>
    And Select Corp Status = Corp Review for that same record that has Agent Status = DriverReimbursement and Corp Status = Corp Review for Driver Reimbursement <CorpStatus> <AgentStatus1> <Agent> <ProNum>
    And Select DriverReimbursement on CropReview
    And Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date <OfDays>
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column
    And Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No <Notes1>
    And Verify previously entered data remained same for Driver Reimbursement Corp Review, Select Go, Select Yes, Select Ok for Tractor NOT currently assigned to the Agent on the DRIVER REIMBURSEMENT Form
    And Verify Agent Status and Corp Status = DriverReimbursement in Main Form
    And Query Data in Tractor Adjustments SQL Table, There should be one record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <OrderNum> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo    | AgentStatus   | Agent | ProNum   | OrderNum    | TractorNo | Status                | Amount | EffectiveCreatedDate | Notes                 | CorpStatus   | AgentStatus1          | OfDays | Notes1                       | Environment  | TableName                           | TableName1                     | Environment1 | Browser  | Username | Password |
     # | "10054642" | "AgentReview" | "AAR" | "193253" | "AAR193253" | "56073"   | "DriverReimbursement" | "110"  | "02-02-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
     # | "10057240" | "AgentReview" | "AAR" | "193575" | "AAR193575" | "56073"   | "DriverReimbursement" | "110"  | "02-03-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
     # | "5257587325" | "AgentReview" | "AAR" | "193323" | "AAR193323" | "56073"   | "DriverReimbursement" | "110"  | "02-17-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "DZZZ1922396" | "AgentReview" | "AAR" | "191382" | "AAR191382" | "56073"   | "DriverReimbursement" | "110"  | "03-03-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
      | "DZZZ1938168" | "AgentReview" | "AAR" | "193313" | "AAR193313" | "56073"   | "DriverReimbursement" | "110"  | "03-10-2023"         | "Automation Testing," | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |

        #Change InvoiceNo/Agent/ProNum/OrderNum/TractorNo-56073  /EffectiveCreatedDate everytime before running Test from @IdentifyInvoiceAndValidTractorNoForTractorReimbursement/Deduct



    #89
  @Regression  @TractorReimbursement/TractorThatDoesNotHaveTractorRecordWithStatusOfCurrent @FailureScreenShot8
  Scenario Outline: (EMGR-242) Test for Tractor that does NOT have a Tractor record with a Status of Current, TRACTOR REIMBURSEMENT (TRAC_VEND_ACCTG_STATUS = ACCOUNTING HOLD)
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface for Tractor
  #  And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus> for Tractor
    And Select Agent <Agent> and ProNo <ProNum> for Tractor
  #  And Select DriverReimbursement on Status <Status>
  #  And Enter a Valid Tractor No <TractorNo>
   # And Enter Amount or # of Days, Effective Date = Todays Date <Amount> <EffectiveCreatedDate>
  #  And Verify Notes Column has the Customer Number and Agent, prefilled in the notes column
  #  And Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No <Notes>
  #  And Verify previously entered data remained same for Driver Reimbursement, Select Go, Select Yes, Main Form appears
  #  And Query Data in Tractor Adjustments Table, There should be no record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <OrderNum> <TractorNo> <Username>
    And Select Corp Status = Corp Review for that same record that has Agent Status = DriverReimbursement and Corp Status = Corp Review for Driver Reimbursement <CorpStatus> <AgentStatus1> <Agent> <ProNum>
    And Select DriverReimbursement on CropReview
    And Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date <OfDays>
    And Change Tractor Number Driver Reimbursement <TractorNo>
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column
    And Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No <Notes1>
    And Verify previously entered data remained same for Driver Reimbursement Corp Review, Select Go, Select Yes, Select Ok for Tractor NOT currently assigned to the Agent on the DRIVER REIMBURSEMENT Form
    And Verify Agent Status and Corp Status = DriverReimbursement in Main Form
    And Query Data in Tractor Adjustments SQL Table, There should be one record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <OrderNum> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo     | Agent | ProNum   | OrderNum    | TractorNo | AgentStatus   | Status                | Amount | EffectiveCreatedDate | CorpStatus   | AgentStatus1          | Notes                 | Notes1                       | OfDays | Environment  | TableName                           | TableName1                     | Environment1 | Browser  | Username | Password |
    #  | "10054642"    | "AAR" | "193305" | "AAR193305" | "57912"   | "AgentReview" | "DriverReimbursement" | "110"  | "02-02-2023"         | "CorpReview" | "DriverReimbursement" | "Automation Testing," | "   Automation Testing !!!," | "3"    | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "10057240" | "AAR" | "193897" | "AAR193897" | "57912"   | "AgentReview" | "DriverReimbursement" | "110"  | "02-03-2023"         | "CorpReview" | "DriverReimbursement" | "Automation Testing," | "   Automation Testing !!!," | "3"    | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
     | "DCZZ1926579" | "AAR" | "191761" | "AAR191761" | "18"   | "AgentReview" | "DriverReimbursement" | "110"  | "03-10-2023"         | "CorpReview" | "DriverReimbursement" | "Automation Testing," | "   Automation Testing !!!," | "3"    | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
   #   | "DCZZ1947550" | "AAR" | "193017" | "AAR193017" | "18"   | "AgentReview" | "DriverReimbursement" | "110"  | "03-09-2023"         | "CorpReview" | "DriverReimbursement" | "Automation Testing," | "   Automation Testing !!!," | "3"    | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |

    #Change InvoiceNo/Agent/ProNum/OrderNum/TractorNo-57912(TractorNo-should be Accounting hold)  /EffectiveCreatedDate everytime before running Test from @IdentifyInvoiceAndValidTractorNoForTractorReimbursement/Deduct
    #55006/ Accounting Hold Status for AAR In Tractor vendor Code Relationship


  #90
  @Regression @TractorDeduct @FailureScreenShot8
  Scenario Outline: (EMGR-243) Verify TRACTOR DEDUCT Process on EQUIPMENT CONSOLE
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Pro Number <Pro/OrderNumber> in Search Criteria on Equipment Console Interface for Tractor
    And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus> for Tractor
    And Select Agent <Agent> and ProNo <ProNum> for Tractor
    And Select DriverDeduct on Status <Status>
    And Enter a Valid Tractor No <TractorNo> for Driver Deduct
    And Enter Amount or # of Days, Effective Date = Todays Date <Amount> <EffectiveCreatedDate> for Driver Deduct
    And Verify Notes Column has the Customer Number and Agent, prefilled in the notes column for Driver Deduct
    And Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No <Notes> for Driver Deduct
    And Verify previously entered data remained same for Driver Deduct, Select Go, Select Yes, Main Form appears
    And Query Data in Tractor Adjustments Table for Driver Deduct, There should be no record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    Then Click on Clear Column Filters
    And Select Corp Status = CorpReview for that same record that has Agent Status = DriverDeduct and Corp Status = CorpReview for Driver Deduct <CorpStatus> <AgentStatus1> <Agent> <ProNum>
    And Select DriverDeduct on CropReview
    And Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date <OfDays> for Driver Deduct
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column for Driver Deduct
    And Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No <Notes1> for Driver Deduct
    And Verify previously entered data remained same for Driver Deduct Corp Review, Select Go, Select Yes, Main Form Appears
    And Verify Agent Status and Corp Status = DriverDeduct in Main Form
    And Query Data in Tractor Adjustments SQL Table for Driver Deduct, There should be one record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo     | AgentStatus   | Agent | ProNum   | Pro/OrderNumber | TractorNo | Status         | Amount | EffectiveCreatedDate | Notes                 | CorpStatus   | AgentStatus1   | OfDays | Notes1                       | Environment  | TableName                           | TableName1                     | Environment1 | Browser  | Username | Password |
    #  | "10054642" | "AgentReview" | "AAR" | "193246" | "AAR193246"     | "160664"  | "DriverDeduct" | "330"  | "02-02-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "2119344964" | "AgentReview" | "AAR" | "193765" | "AAR193765"     | "160664"  | "DriverDeduct" | "330"  | "02-03-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "DCZZ1926579" | "AgentReview" | "AAR" | "192727" | "AAR192727"     | "160664"  | "DriverDeduct" | "330"  | "02-17-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "DZZZ1925256" | "AgentReview" | "AAR" | "192153" | "AAR192153"     | "160664"  | "DriverDeduct" | "330"  | "03-03-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
      | "DZZZ1938488" | "AgentReview" | "AAR" | "192113" | "AAR192113"     | "160664"  | "DriverDeduct" | "330"  | "03-10-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |

        #Change InvoiceNo/Agent/ProNum/OrderNum/TractorNo/EffectiveCreatedDate everytime before running Test from @IdentifyInvoiceAndValidTractorNoForTractorReimbursement/Deduct


    #91
  @Regression @TractorDeduct/TractorWithoutRecordOnTractorVendorTable @FailureScreenShot8
  Scenario Outline: (EMGR-243) Test for Tractor without a record on the Tractor_Vendor Table, TRACTOR DEDUCT
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Pro Number <Pro/OrderNumber> in Search Criteria on Equipment Console Interface for Tractor
    And Select Corp Status = CorpReview for that same record that has Agent Status = DriverDeduct and Corp Status = CorpReview for Driver Deduct <CorpStatus> <AgentStatus1> <Agent> <ProNum>
    And Select DriverDeduct on CropReview
    And Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered. Enter Effective Date <EffectiveCreatedDate> for Driver Deduct
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column for Driver Deduct
    And Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No <Notes1> for Driver Deduct
    And Verify previously entered data remained same for Driver Deduct Corp Review, Select Go, Select Yes, Select Ok
    And Verify Agent Status = DriverDeduct and Corp Status = CorpReview in Main Form
    And Query Data in Tractor Adjustments Table, There should be no record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | Agent | ProNum   | Pro/OrderNumber | TractorNo | EffectiveCreatedDate | CorpStatus   | AgentStatus1   | Notes1                       | Environment  | TableName                           | TableName1                     | Environment1 | Browser  | Username | Password |
      | "AAR" | "192458" | "AAR192458"     | "ABCDEF"  | "03-30-2023"         | "CorpReview" | "DriverDeduct" | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |

        #Change EffectiveCreatedDate everytime before running Test


  #92
  @Regression @TractorDeduct/TractorNotCurrentlyAssignedToAgentOnDriverDeductForm @FailureScreenShot8
  Scenario Outline: (EMGR-243) Test for Tractor NOT currently assigned to the Agent on the DRIVER DEDUCT Form
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface for Tractor
    And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus> for Tractor
    And Select Agent <Agent> and ProNo <ProNum> for Tractor
    And Select DriverDeduct on Status <Status>
    And Enter a Valid Tractor No <TractorNo> for Driver Deduct
    And Enter Amount or # of Days, Effective Date = Todays Date <Amount> <EffectiveCreatedDate> for Driver Deduct
    And Verify Notes Column has the Customer Number and Agent, prefilled in the notes column for Driver Deduct
    And Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No <Notes> for Driver Deduct
    And Verify previously entered data remained same for Driver Deduct, Select Go, Select Yes, Main Form appears
    And Query Data in Tractor Adjustments Table for Driver Deduct, There should be no record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <OrderNumber> <TractorNo> <Username>
    And Select Corp Status = CorpReview for that same record that has Agent Status = DriverDeduct and Corp Status = CorpReview for Driver Deduct <CorpStatus> <AgentStatus1> <Agent> <ProNum>
    And Select DriverDeduct on CropReview
    And Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date <OfDays> for Driver Deduct
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column for Driver Deduct
    And Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No <Notes1> for Driver Deduct
    And Verify previously entered data remained same for Driver Deduct Corp Review, Select Go, Select Yes, Select Ok for Tractor NOT currently assigned to the Agent on the DRIVER DEDUCT Form
    And Verify Agent Status and Corp Status = DriverDeduct in Main Form
    And Query Data in Tractor Adjustments SQL Table for Driver Deduct, There should be one record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <OrderNumber> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo     | AgentStatus   | Agent | ProNum   | OrderNumber | TractorNo | Status         | Amount | EffectiveCreatedDate | Notes                 | CorpStatus   | AgentStatus1   | OfDays | Notes1                       | Environment  | TableName                           | TableName1                     | Environment1 | Browser  | Username | Password |
     # | "10054642" | "AgentReview" | "AAR" | "193960" | "AAR193960" | "56073"   | "DriverDeduct" | "330"  | "02-02-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "2119349482" | "AgentReview" | "AAR" | "194237" | "AAR194237" | "56073"   | "DriverDeduct" | "330"  | "02-03-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "DCZZ1929205" | "AgentReview" | "AAR" | "192781" | "AAR192781" | "56073"   | "DriverDeduct" | "330"  | "02-17-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
      | "DZZZ1925588" | "AgentReview" | "AAR" | "193810" | "AAR193810" | "56073"   | "DriverDeduct" | "330"  | "03-10-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |

        #Change InvoiceNo/Agent/ProNum/OrderNum/TractorNo-56073  /EffectiveCreatedDate everytime before running Test from @IdentifyInvoiceAndValidTractorNoForTractorReimbursement/Deduct




  #93
  @Regression @TractorDeduct/TractorThatDoesNotHaveTractorRecordWithStatusOfCurrent @FailureScreenShot8
  Scenario Outline: (EMGR-243) Test for Tractor that does NOT have a Tractor record with a Status of Current, TRACTOR DEDUCT (TRAC_VEND_ACCTG_STATUS = ACCOUNTING HOLD)
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface for Tractor
   # And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus> for Tractor
    And Select Agent <Agent> and ProNo <ProNum> for Tractor
  #  And Select DriverDeduct on Status <Status>
  #  And Enter a Valid Tractor No <TractorNo> for Driver Deduct
  #  And Enter Amount or # of Days, Effective Date = Todays Date <Amount> <EffectiveCreatedDate> for Driver Deduct
  #  And Verify Notes Column has the Customer Number and Agent, prefilled in the notes column for Driver Deduct
  #  And Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No <Notes> for Driver Deduct
  #  And Verify previously entered data remained same for Driver Deduct, Select Go, Select Yes, Main Form appears
  #  And Query Data in Tractor Adjustments Table for Driver Deduct, There should be no record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <OrderNumber> <TractorNo> <Username>
    And Select Corp Status = CorpReview for that same record that has Agent Status = DriverDeduct and Corp Status = CorpReview for Driver Deduct <CorpStatus> <AgentStatus1> <Agent> <ProNum>
    And Select DriverDeduct on CropReview
    And Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date <OfDays> for Driver Deduct
    And Change Tractor Number Driver Deduct <TractorNo>
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column for Driver Deduct
    And Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No <Notes1> for Driver Deduct
    And Verify previously entered data remained same for Driver Deduct Corp Review, Select Go, Select Yes, Select Ok for Tractor NOT currently assigned to the Agent on the DRIVER DEDUCT Form
    And Verify Agent Status and Corp Status = DriverDeduct in Main Form
    And Query Data in Tractor Adjustments SQL Table for Driver Deduct, There should be one record in Tractor Adjustments table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <OrderNumber> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo     | AgentStatus   | Agent | ProNum   | OrderNumber | TractorNo | Status         | Amount | EffectiveCreatedDate | Notes                 | CorpStatus   | AgentStatus1   | OfDays | Notes1                       | Environment  | TableName                           | TableName1                     | Environment1 | Browser  | Username | Password |
    #  | "DZZZ1942694" | "AgentReview" | "AAR" | "193581" | "AAR193581" | "57912"   | "DriverDeduct" | "330"  | "02-02-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "2119349482" | "AgentReview" | "AAR" | "194238" | "AAR194238" | "57912"   | "DriverDeduct" | "330"  | "02-03-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
     | "DCZZ1929205" | "AgentReview" | "AAR" | "193372" | "AAR193372" | "18"   | "DriverDeduct" | "330"  | "03-10-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "DZZZ1922009" | "AgentReview" | "AAR" | "191869" | "AAR191869" | "18"   | "DriverDeduct" | "330"  | "03-09-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "DZZZ1932748" | "AgentReview" | "AAR" | "191825" | "AAR191825" | "57912"   | "DriverDeduct" | "330"  | "03-09-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
     # | "DZZZ1932748" | "AgentReview" | "AAR" | "191825" | "AAR191825" | "18"   | "DriverDeduct" | "330"  | "03-09-2023"         | "Automation Testing," | "CorpReview" | "DriverDeduct" | "3"    | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |

    #55101
        #Change InvoiceNo/Agent/ProNum/OrderNum/TractorNo-57912(TractorNo-should be Accounting hold)  /EffectiveCreatedDate everytime before running Test from @IdentifyInvoiceAndValidTractorNoForTractorReimbursement/Deduct




  #94
  @Regression @IdentifyInvoiceForSplitAmount @FailureScreenShot8
  Scenario Outline: (EMGR-173) Identify INVOICE NUMBER and TRACTOR NUMBER for SPLIT AMOUNT - Agent and Tractor Deductions and Reimbursements Process on EQUIPMENT CONSOLE
    Given Locate a Record from Database for Valid Invoice Number for Split Amount <Environment> <TableName1> <TableName2> <TableName3> <TableName4> <TableName5> <Agent>
    And Locate a Record from Database for Tractor <Environment> <TableName6> <Agent>
    Examples:
      | Agent | Environment  | TableName1                              | TableName2                        | TableName3                | TableName4             | TableName5                            | TableName6                     |
     | "HCI" | "ebhstaging" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "[Evans].[dbo].[InvoiceRegister]" | "[EBH].[dbo].[Locations]" | "[EBH].[dbo].[Orders]" | "[EBH].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBH].[dbo].[TRACTOR_VENDOR]" |
     # | "LNJ" | "ebhstaging" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "[Evans].[dbo].[InvoiceRegister]" | "[EBH].[dbo].[Locations]" | "[EBH].[dbo].[Orders]" | "[EBH].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBH].[dbo].[TRACTOR_VENDOR]" |



  #95
  @Regression @SplitAmount @FailureScreenShot8
  Scenario Outline: (EMGR-173) Verify SPLIT AMOUNT Process on EQUIPMENT CONSOLE
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface for Split Amount
    And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus> for Split Amount
    And Select Agent <Agent> and ProNo <ProNum> for Split Amount
    And Select SplitAmount on Status <Status>
    And Enter Agent Amount and Driver Amount or # of Days, Effective Date = Todays Date <AgentAmount> <DriverAmount> <EffectiveCreatedDate> for Split Amount
    And Enter a Valid Tractor No <TractorNo> for Split Amount
    And Verify Notes Column has the Customer Number, Chassis No and Container No, prefilled in the notes column for Split Amount
    And Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No <Notes> for Split Amount
    And Verify previously entered data remained same for Split Amount, Select Go, Select Yes, Main Form appears
    And Query Data in Agent_Adjustments Table for Split Amount, There should be no record in this table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    And Query Data in Tractor_Adjustments Table for Split Amount, There should be no record in this table for this transaction <Environment> <TableName2> <TableName3> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    And Select Corp Status = CorpReview for that same record that has Agent Status = SplitAmount and Corp Status = CorpReview for Split Amount <AgentStatus1> <Agent> <ProNum>
    And Select SplitAmount on CropReview
    And The Days, Amount, Tractor and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date or No of Splits <NoOfSplits> for Split Amount
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column for Split Amount
    And Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No <Notes1> for Split Amount CorpReview
    And Verify previously entered data remained same for Split Amount Corp Review, Select Go, Select Yes, Main Form Appears

    And Verify Agent Status and Corp Status = SplitAmount in Main Form
    And Query Data in Agent_Adjustments Table for Split Amount, There should be one record in this table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    And Query Data in Tractor_Adjustments Table for Split Amount, There should be one record in this table for this transaction <Environment> <TableName2> <TableName3> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo       | AgentStatus   | Agent | ProNum   | Pro/OrderNumber | TractorNo | Status        | AgentAmount | DriverAmount | EffectiveCreatedDate | Notes                 | AgentStatus1  | NoOfSplits | Notes1                       | Environment  | TableName                         | TableName1                       | TableName2                          | TableName3                     | Environment1 | Browser  | Username | Password |
    #  | "SR202204043DM" | "AgentReview" | "HCI" | "112739" | "HCI112739"     | "56073"   | "SplitAmount" | "330"       | "220"        | "12-29-2022"         |  "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
     # | "SR202204043DM" | "AgentReview" | "HCI" | "112774" | "HCI112774"     | "56073"   | "SplitAmount" | "330"       | "220"        | "12-30-2022"         |  "Automation Testing," |  "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "SR202204041DM" | "AgentReview" | "HCI" | "112774" | "HCI112774"     | "56073"   | "SplitAmount" | "330"       | "220"        | "12-30-2022"         |  "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "SR202204043DM" | "AgentReview" | "HCI" | "112739" | "HCI112739"     | "56073"   | "SplitAmount" | "330"       | "220"        | "01-04-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
   #   | "SR202204043DM" | "AgentReview" | "HCI" | "112739" | "HCI112739"     | "56073"   | "SplitAmount" | "330"       | "220"        | "01-05-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "SR202204043DM" | "AgentReview" | "HCI" | "112775" | "HCI112775"     | "56073"   | "SplitAmount" | "330"       | "220"        | "01-05-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "SR202204043DM" | "AgentReview" | "HCI" | "113040" | "HCI113040"     | "56073"   | "SplitAmount" | "330"       | "220"        | "01-13-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
     # | "SR202204041DM" | "AgentReview" | "HCI" | "120253" | "HCI120253"     | "56073"   | "SplitAmount" | "330"       | "220"        | "02-02-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "SR202204041DM" | "AgentReview" | "HCI" | "112771" | "HCI112771"     | "56073"   | "SplitAmount" | "330"       | "220"        | "02-03-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "SR202204041DM" | "AgentReview" | "HCI" | "120258" | "HCI120258"     | "56073"   | "SplitAmount" | "330"       | "220"        | "02-10-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "SR202204043DM" | "AgentReview" | "HCI" | "120461" | "HCI120461"     | "56073"   | "SplitAmount" | "330"       | "220"        | "02-17-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
   ##   | "TEST0107221" | "AgentReview" | "HCI" | "122702" | "HCI122702"     | "56073"   | "SplitAmount" | "330"       | "220"        | "02-24-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "JH2110281050CH" | "AgentReview" | "HCI" | "120105" | "HCI120105"     | "56073"   | "SplitAmount" | "330"       | "220"        | "03-03-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
     # | "JH2204051120DM" | "AgentReview" | "HCI" | "120253" | "HCI120253"     | "56073"   | "SplitAmount" | "330"       | "220"        | "03-09-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
      | "JH2204051120DM" | "AgentReview" | "HCI" | "120255" | "HCI120255"     | "56073"   | "SplitAmount" | "330"       | "220"        | "03-10-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |

       # JH2204051120DM 120254 /  120255 / 120256 / 120267 / 120268

        #Change InvoiceNo/Agent/ProNum/OrderNum/TractorNo/EffectiveCreatedDate everytime before running Test from @IdentifyInvoiceForSplitAmount

  #96
  @Regression @IdentifyInvoiceForSplitAmountTestForAgentWithoutRecordOnAgent_Settlement_InfoTable
  Scenario Outline: (EMGR-173) Identify INVOICE NUMBER for SPLIT AMOUNT - Test for Agent without a record on the Agent_Settlement_Info table
    Given Locate a Record from Database for Valid Invoice Number for Split Amount, Test for Agent without a record on the Agent_Settlement_Info table <Environment> <TableName1> <TableName2> <TableName3> <TableName4>
    Examples:
      | Environment  | TableName1                              | TableName2                        | TableName3                | TableName4                            |
      | "ebhstaging" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "[Evans].[dbo].[InvoiceRegister]" | "[EBH].[dbo].[Locations]" | "[EBH].[dbo].[AGENT_SETTLEMENT_INFO]" |


    #97
  @Regression @SplitAmount/TestForAgentWithoutRecordOnAgentSettlementInfoTable @FailureScreenShot8
  Scenario Outline: (EMGR-173) Test for Agent without a record on the Agent_Settlement_Info table, SPLIT AMOUNT
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface for Split Amount
    And Select Corp Status = CorpReview for the same record that has Agent Status = SplitAmount and Corp Status = CorpReview for Split Amount <AgentStatus1> <Agent> <ProNum>
    And Select SplitAmount on CropReview
    And The Days, Amount, Tractor <TractorNo> and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date or No of Splits <EffectiveCreatedDate> for Split Amount
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column for Split Amount
    And Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok <Notes1> for Split Amount CorpReview
    And Verify previously entered data remained same for Split Amount Corp Review, Select Go, Select Yes, Select Ok
    And Verify Agent Status = SplitAmount and Corp Status = CorpReview in Main Form
    And Query Data in Agent_Adjustments Table for Split Amount, There should be no record in this table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo          | Agent | ProNum   | Pro/OrderNumber | TractorNo | AgentStatus1  | EffectiveCreatedDate | Notes1                       | Environment1 | Browser  | Username | Password | Environment  | TableName                         | TableName1                       |
      | "JH202204051120DM" | "CTW" | "147915" | "CTW147915"     | "56073"   | "SplitAmount" | "03-30-2023"         | "   Automation Testing !!!," | "ecstaging"  | "chrome" | "eqpa"   | "spring" | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" |

        #Change EffectiveCreatedDate everytime before running Test



  #98
  @Regression @SplitAmount/TractorWithoutRecordOnTractorVendorTable @FailureScreenShot8
  Scenario Outline: (EMGR-173) Test for Tractor without a record on the Tractor_Vendor Table, SPLIT AMOUNT
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface for Split Amount
    And Select Corp Status = CorpReview for the same record that has Agent Status = SplitAmount and Corp Status = CorpReview for Split Amount <AgentStatus1> <Agent> <ProNum>
    And Select SplitAmount on CropReview
    And The Days, Amount, Tractor <TractorNo> and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date or No of Splits <EffectiveCreatedDate> for Split Amount
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column for Split Amount
    And Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok <Notes1> for Split Amount CorpReview
    And Verify previously entered data remained same for Split Amount Corp Review, Select Go, Select Yes, Select Ok
    And Verify Agent Status = SplitAmount and Corp Status = CorpReview in Main Form
    And Query Data in Tractor_Adjustments Table for Split Amount, There should be one record in this table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo          | Agent | ProNum   | Pro/OrderNumber | TractorNo | EffectiveCreatedDate | AgentStatus1  | Notes1                       | Environment  | TableName                           | TableName1                     | Environment1 | Browser  | Username | Password |
      | "JH202204051120DM" | "HCI" | "112739" | "HCI112739"     | "ABCDEF"  | "03-15-2023"         | "SplitAmount" | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |

        #Change EffectiveCreatedDate everytime before running Test



  #99
  @Regression @SplitAmount/TestforTractorNotCurrentlyAssignedToAgentOnSplitAmountForm @FailureScreenShot8
  Scenario Outline: (EMGR-243) Test for Tractor NOT currently assigned to the Agent on the SPLIT AMOUNT Form
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface for Split Amount
    And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus> for Split Amount
    And Select Agent <Agent> and ProNo <ProNum> for Split Amount
    And Select SplitAmount on Status <Status>
    And Enter Agent Amount and Driver Amount or # of Days, Effective Date = Todays Date <AgentAmount> <DriverAmount> <EffectiveCreatedDate> for Split Amount
    And Enter a Valid Tractor No <TractorNo> for Split Amount
    And Verify Notes Column has the Customer Number, Chassis No and Container No, prefilled in the notes column for Split Amount
    And Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go <Notes> for Split Amount
    And Query Data in Agent_Adjustments Table for Split Amount, There should be no record in this table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    And Query Data in Tractor_Adjustments Table for Split Amount, There should be no record in this table for this transaction <Environment> <TableName2> <TableName3> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    And Select Corp Status = CorpReview for that same record that has Agent Status = SplitAmount and Corp Status = CorpReview for Split Amount <AgentStatus1> <Agent> <ProNum>
    And Select SplitAmount on CropReview
    And The Days, Amount, Tractor and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date or No of Splits <NoOfSplits> for Split Amount
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column for Split Amount
    And Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go <Notes> for SPLIT AMOUNT on CorpReview Split Amount Form
    And Verify Agent Status and Corp Status = SplitAmount in Main Form
    And Query Data in Agent_Adjustments Table for Split Amount, There should be one record in this table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    And Query Data in Tractor_Adjustments Table for Split Amount, There should be one record in this table for this transaction <Environment> <TableName2> <TableName3> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo       | AgentStatus   | Agent | ProNum   | Pro/OrderNumber | TractorNo | Status        | AgentAmount | DriverAmount | EffectiveCreatedDate | Notes                 | AgentStatus1  | NoOfSplits | Notes1                       | Environment  | TableName                         | TableName1                       | TableName2                          | TableName3                     | Environment1 | Browser  | Username | Password |
     # | "SR202204041DM" | "AgentReview" | "HCI" | "112814" | "HCI112814"     | "160664"  | "SplitAmount" | "330"       | "220"        | "02-02-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
     # | "SR202204041DM" | "AgentReview" | "HCI" | "120252" | "HCI120252"     | "160664"  | "SplitAmount" | "330"       | "220"        | "02-03-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "JH2108090821DMA" | "AgentReview" | "HCI" | "112739" | "HCI112739"     | "160664"  | "SplitAmount" | "330"       | "220"        | "02-10-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
     # | "SR202204043DM" | "AgentReview" | "HCI" | "120105" | "HCI120105"     | "160664"  | "SplitAmount" | "330"       | "220"        | "02-17-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "J2108230735CH" | "AgentReview" | "HCI" | "113040" | "HCI113040"     | "160664"  | "SplitAmount" | "330"       | "220"        | "02-22-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
      | "JH2204051120DM" | "AgentReview" | "HCI" | "120256" | "HCI120256"     | "160664"  | "SplitAmount" | "330"       | "220"        | "03-10-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |

            # Change InvoiceNo/Agent/ProNum/OrderNum/TractorNo-160664  /EffectiveCreatedDate everytime before running Test from @IdentifyInvoiceForSplitAmount
            #  160664 active tractor not assigned to agent HCI


  #100
  @Regression @SplitAmount/TractorThatDoesNotHaveTractorRecordWithStatusOfCurrent @FailureScreenShot8
  Scenario Outline: (EMGR-173) Test for Tractor that does NOT have a Tractor record with a Status of Current, SPLIT AMOUNT (TRAC_VEND_ACCTG_STATUS = ACCOUNTING HOLD)
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface for Split Amount
    And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus> for Split Amount
    And Select Agent <Agent> and ProNo <ProNum> for Split Amount
    And Select SplitAmount on Status <Status>
    And Enter Agent Amount and Driver Amount or # of Days, Effective Date = Todays Date <AgentAmount> <DriverAmount> <EffectiveCreatedDate> for Split Amount
    And Enter a Valid Tractor No <TractorNo> for Split Amount
    And Verify Notes Column has the Customer Number, Chassis No and Container No, prefilled in the notes column for Split Amount
    And Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go <Notes> for Split Amount
    And Query Data in Agent_Adjustments Table for Split Amount, There should be no record in this table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    And Query Data in Tractor_Adjustments Table for Split Amount, There should be no record in this table for this transaction <Environment> <TableName2> <TableName3> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    And Select Corp Status = CorpReview for that same record that has Agent Status = SplitAmount and Corp Status = CorpReview for Split Amount <AgentStatus1> <Agent> <ProNum>
    And Select SplitAmount on CropReview
    And The Days, Amount, Tractor and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date or No of Splits <NoOfSplits> for Split Amount
    And Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column for Split Amount
    And Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go <Notes> for SPLIT AMOUNT on CorpReview Split Amount Form
    And Verify Agent Status and Corp Status = SplitAmount in Main Form
    And Query Data in Agent_Adjustments Table for Split Amount, There should be one record in this table for this transaction <Environment> <TableName> <TableName1> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    And Query Data in Tractor_Adjustments Table for Split Amount, There should be one record in this table for this transaction <Environment> <TableName2> <TableName3> <EffectiveCreatedDate> <Pro/OrderNumber> <TractorNo> <Username>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo       | AgentStatus   | Agent | ProNum   | Pro/OrderNumber | TractorNo | Status        | AgentAmount | DriverAmount | EffectiveCreatedDate | Notes                 | AgentStatus1  | NoOfSplits | Notes1                       | Environment  | TableName                         | TableName1                       | TableName2                          | TableName3                     | Environment1 | Browser  | Username | Password |
    #  | "SR202204041DM" | "AgentReview" | "HCI" | "113079" | "HCI113079"     | "57912"   | "SplitAmount" | "330"       | "220"        | "02-02-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "SR202204041DM" | "AgentReview" | "HCI" | "113040" | "HCI113040"     | "57912"   | "SplitAmount" | "330"       | "220"        | "02-03-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "JH2108090821DMA" | "AgentReview" | "HCI" | "112772" | "HCI112772"     | "57912"   | "SplitAmount" | "330"       | "220"        | "02-10-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
      | "JH2108090821DMA" | "AgentReview" | "HCI" | "112773" | "HCI112773"     | "57912"   | "SplitAmount" | "330"       | "220"        | "02-17-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
    #  | "JRB2107261140" | "AgentReview" | "HCI" | "113040" | "HCI113040"     | "57912"   | "SplitAmount" | "330"       | "220"        | "02-22-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |
      | "29042021" | "AgentReview" | "HCI" | "113077" | "HCI113077"     | "57912"   | "SplitAmount" | "330"       | "220"        | "02-24-2023"         | "Automation Testing," | "SplitAmount" | "3"        | "   Automation Testing !!!," | "ebhstaging" | "[EBH].[dbo].[AGENT_ADJUSTMENTS]" | "[EBH].[dbo].[Agent_Pay_Matrix]" | "[EBH].[dbo].[TRACTOR_ADJUSTMENTS]" | "[EBH].[dbo].[TRACTOR_VENDOR]" | "ecstaging"  | "chrome" | "eqpa"   | "spring" |

   #Change InvoiceNo/Agent/ProNum/OrderNum/TractorNo-57912 / 55097 (TractorNo-should be Accounting hold) /EffectiveCreatedDate everytime before running Test from @IdentifyInvoiceForSplitAmount





























