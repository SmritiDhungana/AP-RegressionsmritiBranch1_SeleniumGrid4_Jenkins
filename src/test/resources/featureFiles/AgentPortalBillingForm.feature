Feature: Agent Portal Billing Form Features

   #50
  @Regression @CreateOrders/BookLoad @FailureScreenShot10
  Scenario Outline: Book a Load through Agents Portal in Staging Environment
    Given run test for <environment> on browser <browser> in Agents Portal
    And enter the url of Agents Portal
   # And login on the agents portal with username <username> and password <password>
    And login on the agents portal with username <username> and password <password> FOR HCI ONLY
    And navigate to the bookload page
    And enter Bill To as <billto> From as <from> and To as <to>
    And in References Tab enter the Container as <container> Chassis as <chassis> and Empty Container as <emptycontainer>
    And in References Tab enter the ReferenceOne as <referenceOne> ReferenceTwo as <referenceTwo> and RefOne as <refOne>
    And in Financial Information - Customer Charges Tab enter the Freight Charges as <FreightCharges>
    And in Financial Information - Customer Charges Tab enter the Fuel Surcharges Quantity as <FuelSurchargesQuantity> and Rate as <FuelSurchargesRate> <FuelSurchargesTotal>
    And in Financial Information - Customer Charges Tab enter the Daily Chassis Charges Quantity as <DailyChassisChargesQuantity> and Rate as <DailyChassisChargesRate>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorOne> from the TractorOne Field, Freight Charges <FCOne>, Fuel Surcharges <FSOne> and Daily Chassis Charges <DCCOne>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorTwo> from the TractorTwo Field, Freight Charges <FCTwo>, Fuel Surcharges <FSTwo> and Daily Chassis Charges <DCCTwo>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorThree> from the TractorThree Field, Freight Charges <FCThree>, Fuel Surcharges <FSThree> and Daily Chassis Charges <DCCThree>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFour> from the TractorFour Field, Freight Charges <FCFour>, Fuel Surcharges <FSFour> and Daily Chassis Charges <DCCFour>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFive> from the TractorFive Field, Freight Charges <FCFive>, Fuel Surcharges <FSFive> and Daily Chassis Charges <DCCFive>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorSix> from the TractorSix Field, Freight Charges <FCSix>, Fuel Surcharges <FSSix> and Daily Chassis Charges <DCCSix>
    And in Operations Information - enter the Actual Date as <ActualDate> and Time as <ActualTime>
    And in Operations Information - enter the Delivery Appointment Date as <DADate> and Time as <DATime>
    Then click on Book Load and validate if the Booking Number got generated on BookLoad
    Then close all open browsers
    Examples:
      | environment | browser  | username | password    | billto    | from       | to         | container | chassis | emptycontainer | referenceOne | referenceTwo | refOne  | FreightCharges | FuelSurchargesTotal | ActualDate   | ActualTime | DADate       | DATime  | FuelSurchargesQuantity | FuelSurchargesRate | DailyChassisChargesQuantity | DailyChassisChargesRate | TractorOne               | FCOne | FSOne | DCCOne | TractorTwo | FCTwo | FSTwo | DCCTwo | TractorThree | FCThree | FSThree | DCCThree | TractorFour | FCFour | FSFour | DCCFour | TractorFive            | FCFive | FSFive | DCCFive | TractorSix | FCSix | FSSix | DCCSix |
      | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "03/10/2023" | "08:00"    | "03/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "180776-DAVID BLACKBURN" | "14"  | "11"  | "3"    | "931115-"  | "15"  | "10"  | "3"    | "181198-"    | "15"    | "10"    | "3"      | "176623-"   | "13"   | "10"   | "2"     | "931124-CECILIO GOMEZ" | "14"   | "11"   | "3"     | "931115-"  | "15"  | "10"  | "3"    |


    #51
  @Regression @CreateOrders/BookLoadWithBillingDoc @FailureScreenShot10
  Scenario Outline: Book a Load with Billing Document through Agents Portal in Staging Environment
    Given run test for <environment> on browser <browser> in Agents Portal
    And enter the url of Agents Portal
   # And login on the agents portal with username <username> and password <password>
    And login on the agents portal with username <username> and password <password> FOR HCI ONLY
    And navigate to the bookload page
    And enter Bill To as <billto> From as <from> and To as <to>
    And in References Tab enter the Container as <container> Chassis as <chassis> and Empty Container as <emptycontainer>
    And in References Tab enter the ReferenceOne as <referenceOne> ReferenceTwo as <referenceTwo> and RefOne as <refOne>
    And in Financial Information - Customer Charges Tab enter the Freight Charges as <FreightCharges>
    And in Financial Information - Customer Charges Tab enter the Fuel Surcharges Quantity as <FuelSurchargesQuantity> and Rate as <FuelSurchargesRate> <FuelSurchargesTotal>
    And in Financial Information - Customer Charges Tab enter the Daily Chassis Charges Quantity as <DailyChassisChargesQuantity> and Rate as <DailyChassisChargesRate>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorOne> from the TractorOne Field, Freight Charges <FCOne>, Fuel Surcharges <FSOne> and Daily Chassis Charges <DCCOne>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorTwo> from the TractorTwo Field, Freight Charges <FCTwo>, Fuel Surcharges <FSTwo> and Daily Chassis Charges <DCCTwo>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorThree> from the TractorThree Field, Freight Charges <FCThree>, Fuel Surcharges <FSThree> and Daily Chassis Charges <DCCThree>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFour> from the TractorFour Field, Freight Charges <FCFour>, Fuel Surcharges <FSFour> and Daily Chassis Charges <DCCFour>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFive> from the TractorFive Field, Freight Charges <FCFive>, Fuel Surcharges <FSFive> and Daily Chassis Charges <DCCFive>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorSix> from the TractorSix Field, Freight Charges <FCSix>, Fuel Surcharges <FSSix> and Daily Chassis Charges <DCCSix>
    And in Operations Information - enter the Actual Date as <ActualDate> and Time as <ActualTime>
    And in Operations Information - enter the Delivery Appointment Date as <DADate> and Time as <DATime>
    Then click on Book Load
    Then Click on Scan Documents on Bill Created
    Then close all open browsers
    Examples:
      | environment | browser  | username | password    | billto    | from       | to         | container | chassis | emptycontainer | referenceOne | referenceTwo | refOne  | FreightCharges | FuelSurchargesTotal | ActualDate   | ActualTime | DADate       | DATime  | FuelSurchargesQuantity | FuelSurchargesRate | DailyChassisChargesQuantity | DailyChassisChargesRate | TractorOne               | FCOne | FSOne | DCCOne | TractorTwo | FCTwo | FSTwo | DCCTwo | TractorThree | FCThree | FSThree | DCCThree | TractorFour | FCFour | FSFour | DCCFour | TractorFive            | FCFive | FSFive | DCCFive | TractorSix | FCSix | FSSix | DCCSix |
      | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "03/10/2023" | "08:00"    | "03/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "180776-DAVID BLACKBURN" | "14"  | "11"  | "3"    | "931115-"  | "15"  | "10"  | "3"    | "181198-"    | "15"    | "10"    | "3"      | "176623-"   | "13"   | "10"   | "2"     | "931124-CECILIO GOMEZ" | "14"   | "11"   | "3"     | "931115-"  | "15"  | "10"  | "3"    |


  #52
  @Regression @CreateOrder @CreateOrders/BCPD @FailureScreenShot10
  Scenario Outline: Successfully Created BCPD Scenario through Agents Portal
    Given run test for <environment> on browser <browser> in Agents Portal
    And enter the url of Agents Portal
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter Bill To as <billto> From as <from> and To as <to>
    And in References Tab enter the Container as <container> Chassis as <chassis> and Empty Container as <emptycontainer>
    And in References Tab enter the ReferenceOne as <referenceOne> ReferenceTwo as <referenceTwo> and RefOne as <refOne>
    And in Financial Information - Customer Charges Tab enter the Freight Charges as <FreightCharges>
    And in Financial Information - Customer Charges Tab enter the Fuel Surcharges Quantity as <FuelSurchargesQuantity> and Rate as <FuelSurchargesRate> <FuelSurchargesTotal>
    And in Financial Information - Customer Charges Tab enter the Daily Chassis Charges Quantity as <DailyChassisChargesQuantity> and Rate as <DailyChassisChargesRate>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorOne> from the TractorOne Field, Freight Charges <FCOne>, Fuel Surcharges <FSOne> and Daily Chassis Charges <DCCOne>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorTwo> from the TractorTwo Field, Freight Charges <FCTwo>, Fuel Surcharges <FSTwo> and Daily Chassis Charges <DCCTwo>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorThree> from the TractorThree Field, Freight Charges <FCThree>, Fuel Surcharges <FSThree> and Daily Chassis Charges <DCCThree>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFour> from the TractorFour Field, Freight Charges <FCFour>, Fuel Surcharges <FSFour> and Daily Chassis Charges <DCCFour>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFive> from the TractorFive Field, Freight Charges <FCFive>, Fuel Surcharges <FSFive> and Daily Chassis Charges <DCCFive>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorSix> from the TractorSix Field, Freight Charges <FCSix>, Fuel Surcharges <FSSix> and Daily Chassis Charges <DCCSix>
    And in Operations Information - enter the Actual Date as <ActualDate> and Time as <ActualTime>
    And in Operations Information - enter the Delivery Appointment Date as <DeliveryDate> and Time as <DATime>
    Then click on BCPD and validate if the Booking Number got generated
    Then close all open browsers
    Examples:
      | environment | browser         | username | password    | billto    | from       | to         | container | chassis | emptycontainer | referenceOne | referenceTwo | refOne  | FreightCharges | FuelSurchargesTotal | ActualDate   | ActualTime | DeliveryDate | DATime  | FuelSurchargesQuantity | FuelSurchargesRate | DailyChassisChargesQuantity | DailyChassisChargesRate | TractorOne           | FCOne | FSOne | DCCOne | TractorTwo               | FCTwo | FSTwo | DCCTwo | TractorThree | FCThree | FSThree | DCCThree | TractorFour | FCFour | FSFour | DCCFour | TractorFive | FCFive | FSFive | DCCFive | TractorSix | FCSix | FSSix | DCCSix |
     # | "staging"   | "chrome" | "HCI"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/02/2023" | "08:00"    | "02/02/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "WCB"                | "14"  | "11"  | "3"    | "WCB"                    | "15"  | "10"  | "3"    | "WCB"        | "15"    | "10"    | "3"      | "WCB"       | "13"   | "10"   | "2"     | "WCB"       | "14"   | "11"   | "3"     | "WCB"      | "15"  | "10"  | "3"    |
      | "staging"   | "MicrosoftEdge" | "AIL"    | "legendary" | "WEIOMNE" | "AIL06902" | "UP14DCIL" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "03/10/2023" | "08:00"    | "03/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "15749-RAJAE MORRAR" | "14"  | "11"  | "3"    | "15997-BOGOLJUB ZUJOVIC" | "15"  | "10"  | "3"    | "186254-"    | "15"    | "10"    | "3"      | "171020-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "931235-"  | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | 500"                | "02/02/2023" | "08:00"    | "02/02/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "WCB"                | "14"  | "11"  | "3"    | "57763"                  | "15"  | "10"  | "3"    | "WCB"        | "15"    | "10"    | "3"      | "WCB"       | "13"   | "10"   | "2"     | "WCB"       | "14"   | "11"   | "3"     | "57763"    | "15"  | "10"  | "3"    |
      # | "staging"   | "chrome" | "AIL"    | "legendary" | "WEIOMNE"  | "AIL06902" | "UP14DCIL" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/08/2023" | "08:00"    | "02/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "15749-RAJAE MORRAR" | "14"  | "11"  | "3"    | "15997-BOGOLJUB ZUJOVIC" | "15"  | "10"  | "3"    | "186254-"    | "15"    | "10"    | "3"      | "171020-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "931235-"  | "15"  | "10"  | "3"    |
     # | "staging"   | "chrome" | "AIL"    | "legendary" | "AMEWECA"  | "CP10BEIL" | "AIL09998" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/08/2023" | "08:00"    | "02/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "15749-RAJAE MORRAR" | "14"  | "11"  | "3"    | "15997-BOGOLJUB ZUJOVIC" | "15"  | "10"  | "3"    | "186254-"    | "15"    | "10"    | "3"      | "171020-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "931235-"  | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "AIL"    | "legendary" | "HUBEDI"   | "AIL07164" | "AIL00333" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/08/2023" | "08:00"    | "02/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "15749-RAJAE MORRAR" | "14"  | "11"  | "3"    | "15997-BOGOLJUB ZUJOVIC" | "15"  | "10"  | "3"    | "186254-"    | "15"    | "10"    | "3"      | "171020-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "931235-"  | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "AIL"    | "legendary" | "MILVPIAP" | "AIL10097" | "CO64FBIL" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/08/2023" | "08:00"    | "02/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "185288-"            | "14"  | "11"  | "3"    | "55661"                  | "15"  | "10"  | "3"    | "57338"      | "15"    | "10"    | "3"      | "185145-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "188563-"  | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "AIL"    | "legendary" | "STROMNE"  | "AIL10740" | "AIL03783" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/08/2023" | "08:00"    | "02/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "185288-"            | "14"  | "11"  | "3"    | "55661"                  | "15"  | "10"  | "3"    | "57338"      | "15"    | "10"    | "3"      | "185145-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "188563-"  | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "AIL"    | "legendary" | "STROMNE"  | "AIL01197" | "AIL07987" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/08/2023" | "08:00"    | "02/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "185288-"            | "14"  | "11"  | "3"    | "185145-"                | "15"  | "10"  | "3"    | "57338"      | "15"    | "10"    | "3"      | "55661"     | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "188563-"  | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "AIL"    | "legendary" | "WEIOMNE" | "AIL06902" | "UP14DCIL" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/08/2023" | "08:00"    | "02/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "15749-RAJAE MORRAR" | "14"  | "11"  | "3"    | "15997-BOGOLJUB ZUJOVIC" | "15"  | "10"  | "3"    | "186254-"    | "15"    | "10"    | "3"      | "171020-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "931235-"  | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "AIL"    | "legendary" | "WEIOMNE" | "AIL06902" | "UP14DCIL" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/08/2023" | "08:00"    | "02/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "185288-"            | "14"  | "11"  | "3"    | "55661"                  | "15"  | "10"  | "3"    | "57338"      | "15"    | "10"    | "3"      | "185145-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "188563-"  | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "AIL"    | "legendary" | "HUBEDI"  | "AIL07164" | "AIL00333" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/08/2023" | "08:00"    | "02/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "15749-RAJAE MORRAR" | "14"  | "11"  | "3"    | "15997-BOGOLJUB ZUJOVIC" | "15"  | "10"  | "3"    | "186254-"    | "15"    | "10"    | "3"      | "171020-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "931235-"  | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "AIL"    | "legendary" | "HUBEDI"  | "AIL07164" | "AIL00333" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/08/2023" | "08:00"    | "02/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "185288-"            | "14"  | "11"  | "3"    | "55661"                  | "15"  | "10"  | "3"    | "57338"      | "15"    | "10"    | "3"      | "185145-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "188563-"  | "15"  | "10"  | "3"    |

   #53
  @Regression @CreateOrders/BCPDWithBillingDoc @FailureScreenShot10 @CreateOrder
  Scenario Outline: Successfully Created BCPD Scenario with Billing Document through Agents Portal
    Given run test for <environment> on browser <browser> in Agents Portal
    And enter the url of Agents Portal
   # And login on the agents portal with username <username> and password <password>
    And login on the agents portal with username <username> and password <password> FOR HCI ONLY
    And navigate to the bookload page
    And enter Bill To as <billto> From as <from> and To as <to>
    And in References Tab enter the Container as <container> Chassis as <chassis> and Empty Container as <emptycontainer>
    And in References Tab enter the ReferenceOne as <referenceOne> ReferenceTwo as <referenceTwo> and RefOne as <refOne>
    And in Financial Information - Customer Charges Tab enter the Freight Charges as <FreightCharges>
    And in Financial Information - Customer Charges Tab enter the Fuel Surcharges Quantity as <FuelSurchargesQuantity> and Rate as <FuelSurchargesRate> <FuelSurchargesTotal>
    And in Financial Information - Customer Charges Tab enter the Daily Chassis Charges Quantity as <DailyChassisChargesQuantity> and Rate as <DailyChassisChargesRate>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorOne> from the TractorOne Field, Freight Charges <FCOne>, Fuel Surcharges <FSOne> and Daily Chassis Charges <DCCOne>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorTwo> from the TractorTwo Field, Freight Charges <FCTwo>, Fuel Surcharges <FSTwo> and Daily Chassis Charges <DCCTwo>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorThree> from the TractorThree Field, Freight Charges <FCThree>, Fuel Surcharges <FSThree> and Daily Chassis Charges <DCCThree>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFour> from the TractorFour Field, Freight Charges <FCFour>, Fuel Surcharges <FSFour> and Daily Chassis Charges <DCCFour>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFive> from the TractorFive Field, Freight Charges <FCFive>, Fuel Surcharges <FSFive> and Daily Chassis Charges <DCCFive>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorSix> from the TractorSix Field, Freight Charges <FCSix>, Fuel Surcharges <FSSix> and Daily Chassis Charges <DCCSix>
    And in Operations Information - enter the Actual Date as <ActualDate> and Time as <ActualTime>
    And in Operations Information - enter the Delivery Appointment Date as <DeliveryDate> and Time as <DATime>
    Then click on BCPD
    Then Click on Scan Documents on Bill Created
    Then close all open browsers
    Examples:
      | environment | browser         | username | password    | billto    | from       | to         | container | chassis | emptycontainer | referenceOne | referenceTwo | refOne  | FreightCharges | FuelSurchargesTotal | ActualDate   | ActualTime | DeliveryDate | DATime  | FuelSurchargesQuantity | FuelSurchargesRate | DailyChassisChargesQuantity | DailyChassisChargesRate | TractorOne               | FCOne | FSOne | DCCOne | TractorTwo | FCTwo | FSTwo | DCCTwo | TractorThree | FCThree | FSThree | DCCThree | TractorFour | FCFour | FSFour | DCCFour | TractorFive            | FCFive | FSFive | DCCFive | TractorSix | FCSix | FSSix | DCCSix |
      | "staging"   | "MicrosoftEdge" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "03/10/2023" | "08:00"    | "03/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "180776-DAVID BLACKBURN" | "14"  | "11"  | "3"    | "931115-"  | "15"  | "10"  | "3"    | "181198-"    | "15"    | "10"    | "3"      | "176623-"   | "13"   | "10"   | "2"     | "931124-CECILIO GOMEZ" | "14"   | "11"   | "3"     | "931115-"  | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/02/2023" | "08:00"    | "02/02/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "WCB"      | "14"  | "11"  | "3"    | "WCB"      | "15"  | "10"  | "3"    | "WCB"        | "15"    | "10"    | "3"      | "WCB"       | "13"   | "10"   | "2"     | "WCB"       | "14"   | "11"   | "3"     | "WCB"      | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "ail"    | "legendary" | "WEIOMNE" | "AIL06902" | "UP14DCIL" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/08/2023" | "08:00"    | "02/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "15749-RAJAE MORRAR" | "14"  | "11"  | "3"    | "15997-BOGOLJUB ZUJOVIC" | "15"  | "10"  | "3"    | "186254-"    | "15"    | "10"    | "3"      | "171020-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "931235-"  | "15"  | "10"  | "3"    |

    #54
  @Regression @CreateOrder @CreateOrdersPDOnly @FailureScreenShot10
  Scenario Outline: Successfully Created PD Only Scenario through Agents Portal
    Given run test for <environment> on browser <browser> in Agents Portal
    And enter the url of Agents Portal
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter Bill To as <billto> From as <from> and To as <to>
    And in References Tab enter the Container as <container> Chassis as <chassis> and Empty Container as <emptycontainer>
    And in References Tab enter the ReferenceOne as <referenceOne> ReferenceTwo as <referenceTwo> and RefOne as <refOne>
    And in Financial Information - Customer Charges Tab enter the Freight Charges as <FreightCharges>
    And in Financial Information - Customer Charges Tab enter the Fuel Surcharges Quantity as <FuelSurchargesQuantity> and Rate as <FuelSurchargesRate> <FuelSurchargesTotal>
    And in Financial Information - Customer Charges Tab enter the Daily Chassis Charges Quantity as <DailyChassisChargesQuantity> and Rate as <DailyChassisChargesRate>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorOne> from the TractorOne Field, Freight Charges <FCOne>, Fuel Surcharges <FSOne> and Daily Chassis Charges <DCCOne>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorTwo> from the TractorTwo Field, Freight Charges <FCTwo>, Fuel Surcharges <FSTwo> and Daily Chassis Charges <DCCTwo>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorThree> from the TractorThree Field, Freight Charges <FCThree>, Fuel Surcharges <FSThree> and Daily Chassis Charges <DCCThree>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFour> from the TractorFour Field, Freight Charges <FCFour>, Fuel Surcharges <FSFour> and Daily Chassis Charges <DCCFour>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFive> from the TractorFive Field, Freight Charges <FCFive>, Fuel Surcharges <FSFive> and Daily Chassis Charges <DCCFive>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorSix> from the TractorSix Field, Freight Charges <FCSix>, Fuel Surcharges <FSSix> and Daily Chassis Charges <DCCSix>
    And in Operations Information - enter the Actual Date as <ActualDate> and Time as <ActualTime>
    And in Operations Information - enter the Delivery Appointment Date as <DeliveryDate> and Time as <DATime>
    Then click on PDOnly and validate if the Booking Number got generated
    Then close all open browsers
    Examples:
      | environment | browser  | username | password    | billto    | from       | to         | container | chassis | emptycontainer | referenceOne | referenceTwo | refOne  | FreightCharges | FuelSurchargesTotal | ActualDate   | ActualTime | DeliveryDate | DATime  | FuelSurchargesQuantity | FuelSurchargesRate | DailyChassisChargesQuantity | DailyChassisChargesRate | TractorOne           | FCOne | FSOne | DCCOne | TractorTwo               | FCTwo | FSTwo | DCCTwo | TractorThree | FCThree | FSThree | DCCThree | TractorFour | FCFour | FSFour | DCCFour | TractorFive | FCFive | FSFive | DCCFive | TractorSix | FCSix | FSSix | DCCSix |
    #  | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/02/2023" | "08:00"    | "02/02/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "WCB"                | "14"  | "11"  | "3"    | "WCB"                    | "15"  | "10"  | "3"    | "WCB"        | "15"    | "10"    | "3"      | "WCB"       | "13"   | "10"   | "2"     | "WCB"       | "14"   | "11"   | "3"     | "WCB"      | "15"  | "10"  | "3"    |
      | "staging"   | "chrome" | "ail"    | "legendary" | "WEIOMNE" | "AIL06902" | "UP14DCIL" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "03/10/2023" | "08:00"    | "03/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "15749-RAJAE MORRAR" | "14"  | "11"  | "3"    | "15997-BOGOLJUB ZUJOVIC" | "15"  | "10"  | "3"    | "186254-"    | "15"    | "10"    | "3"      | "171020-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "931235-"  | "15"  | "10"  | "3"    |

    #55
  @Regression @CreateOrders/PDOnlyWithBillingDoc @FailureScreenShot10 @CreateOrder
  Scenario Outline: Successfully Created PD Only Scenario with Billing Document through Agents Portal
    Given run test for <environment> on browser <browser> in Agents Portal
    And enter the url of Agents Portal
  #  And login on the agents portal with username <username> and password <password>
    And login on the agents portal with username <username> and password <password> FOR HCI ONLY
    And navigate to the bookload page
    And enter Bill To as <billto> From as <from> and To as <to>
    And in References Tab enter the Container as <container> Chassis as <chassis> and Empty Container as <emptycontainer>
    And in References Tab enter the ReferenceOne as <referenceOne> ReferenceTwo as <referenceTwo> and RefOne as <refOne>
    And in Financial Information - Customer Charges Tab enter the Freight Charges as <FreightCharges>
    And in Financial Information - Customer Charges Tab enter the Fuel Surcharges Quantity as <FuelSurchargesQuantity> and Rate as <FuelSurchargesRate> <FuelSurchargesTotal>
    And in Financial Information - Customer Charges Tab enter the Daily Chassis Charges Quantity as <DailyChassisChargesQuantity> and Rate as <DailyChassisChargesRate>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorOne> from the TractorOne Field, Freight Charges <FCOne>, Fuel Surcharges <FSOne> and Daily Chassis Charges <DCCOne>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorTwo> from the TractorTwo Field, Freight Charges <FCTwo>, Fuel Surcharges <FSTwo> and Daily Chassis Charges <DCCTwo>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorThree> from the TractorThree Field, Freight Charges <FCThree>, Fuel Surcharges <FSThree> and Daily Chassis Charges <DCCThree>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFour> from the TractorFour Field, Freight Charges <FCFour>, Fuel Surcharges <FSFour> and Daily Chassis Charges <DCCFour>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFive> from the TractorFive Field, Freight Charges <FCFive>, Fuel Surcharges <FSFive> and Daily Chassis Charges <DCCFive>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorSix> from the TractorSix Field, Freight Charges <FCSix>, Fuel Surcharges <FSSix> and Daily Chassis Charges <DCCSix>
    And in Operations Information - enter the Actual Date as <ActualDate> and Time as <ActualTime>
    And in Operations Information - enter the Delivery Appointment Date as <DADate> and Time as <DATime>
    Then click on PDOnly
    Then Click on Scan Documents on Bill Created
    Then close all open browsers
    Examples:
      | environment | browser  | username | password    | billto    | from       | to         | container | chassis | emptycontainer | referenceOne | referenceTwo | refOne  | FreightCharges | FuelSurchargesTotal | ActualDate   | ActualTime | DADate       | DATime  | FuelSurchargesQuantity | FuelSurchargesRate | DailyChassisChargesQuantity | DailyChassisChargesRate | TractorOne               | FCOne | FSOne | DCCOne | TractorTwo | FCTwo | FSTwo | DCCTwo | TractorThree | FCThree | FSThree | DCCThree | TractorFour | FCFour | FSFour | DCCFour | TractorFive            | FCFive | FSFive | DCCFive | TractorSix | FCSix | FSSix | DCCSix |
      | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "03/10/2023" | "08:00"    | "03/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "180776-DAVID BLACKBURN" | "14"  | "11"  | "3"    | "931115-"  | "15"  | "10"  | "3"    | "181198-"    | "15"    | "10"    | "3"      | "176623-"   | "13"   | "10"   | "2"     | "931124-CECILIO GOMEZ" | "14"   | "11"   | "3"     | "931115-"  | "15"  | "10"  | "3"    |

   ##   | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/02/2023" | "08:00"    | "02/02/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "WCB"      | "14"  | "11"  | "3"    | "WCB"      | "15"  | "10"  | "3"    | "WCB"        | "15"    | "10"    | "3"      | "WCB"       | "13"   | "10"   | "2"     | "WCB"       | "14"   | "11"   | "3"     | "WCB"      | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "hci"    | "legendary" | "TEST"    | "HCI00697" | "HCI01709" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/02/2023" | "08:00"    | "02/02/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "180776-DAVID BLACKBURN" | "14"  | "11"  | "3"    | "57763-TEST TRACTOR VENDOR" | "15"  | "10"  | "3"    | "181198-"    | "15"    | "10"    | "3"      | "176623-"   | "13"   | "10"   | "2"     | "931124-CECILIO GOMEZ" | "14"   | "11"   | "3"     | "931115-"  | "15"  | "10"  | "3"    |
    #  | "staging"   | "chrome" | "ail"    | "legendary" | "WEIOMNE" | "AIL06902" | "UP14DCIL" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/08/2023" | "08:00"    | "02/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "15749-RAJAE MORRAR" | "14"  | "11"  | "3"    | "15997-BOGOLJUB ZUJOVIC" | "15"  | "10"  | "3"    | "186254-"    | "15"    | "10"    | "3"      | "171020-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "931235-"  | "15"  | "10"  | "3"    |



 #56
  @Regression @CreateOrdersBCOnly @FailureScreenShot10
  Scenario Outline: Successfully Created BC Only Scenario through Agents Portal
    Given run test for <environment> on browser <browser> in Agents Portal
    And enter the url of Agents Portal
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter Bill To as <billto> From as <from> and To as <to>
    And in References Tab enter the Container as <container> Chassis as <chassis> and Empty Container as <emptycontainer>
    And in References Tab enter the ReferenceOne as <referenceOne> ReferenceTwo as <referenceTwo> and RefOne as <refOne>
    And in Financial Information - Customer Charges Tab enter the Freight Charges as <FreightCharges>
    And in Financial Information - Customer Charges Tab enter the Fuel Surcharges Quantity as <FuelSurchargesQuantity> and Rate as <FuelSurchargesRate> <FuelSurchargesTotal>
    And in Financial Information - Customer Charges Tab enter the Daily Chassis Charges Quantity as <DailyChassisChargesQuantity> and Rate as <DailyChassisChargesRate>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorOne> from the TractorOne Field, Freight Charges <FCOne>, Fuel Surcharges <FSOne> and Daily Chassis Charges <DCCOne>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorTwo> from the TractorTwo Field, Freight Charges <FCTwo>, Fuel Surcharges <FSTwo> and Daily Chassis Charges <DCCTwo>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorThree> from the TractorThree Field, Freight Charges <FCThree>, Fuel Surcharges <FSThree> and Daily Chassis Charges <DCCThree>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFour> from the TractorFour Field, Freight Charges <FCFour>, Fuel Surcharges <FSFour> and Daily Chassis Charges <DCCFour>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFive> from the TractorFive Field, Freight Charges <FCFive>, Fuel Surcharges <FSFive> and Daily Chassis Charges <DCCFive>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorSix> from the TractorSix Field, Freight Charges <FCSix>, Fuel Surcharges <FSSix> and Daily Chassis Charges <DCCSix>
    And in Operations Information - enter the Actual Date as <ActualDate> and Time as <ActualTime>
    And in Operations Information - enter the Delivery Appointment Date as <DeliveryDate> and Time as <DATime>
    Then click on BCOnly and validate if the Booking Number got generated
    Then close all open browsers
    Examples:
      | environment | browser         | username | password    | billto    | from       | to         | container | chassis | emptycontainer | referenceOne | referenceTwo | refOne  | FreightCharges | FuelSurchargesTotal | ActualDate   | ActualTime | DeliveryDate | DATime  | FuelSurchargesQuantity | FuelSurchargesRate | DailyChassisChargesQuantity | DailyChassisChargesRate | TractorOne           | FCOne | FSOne | DCCOne | TractorTwo               | FCTwo | FSTwo | DCCTwo | TractorThree | FCThree | FSThree | DCCThree | TractorFour | FCFour | FSFour | DCCFour | TractorFive | FCFive | FSFive | DCCFive | TractorSix | FCSix | FSSix | DCCSix |
    #  | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "02/02/2023" | "08:00"    | "02/02/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "WCB"                | "14"  | "11"  | "3"    | "WCB"                    | "15"  | "10"  | "3"    | "WCB"        | "15"    | "10"    | "3"      | "WCB"       | "13"   | "10"   | "2"     | "WCB"       | "14"   | "11"   | "3"     | "WCB"      | "15"  | "10"  | "3"    |
      | "staging"   | "MicrosoftEdge" | "ail"    | "legendary" | "WEIOMNE" | "AIL06902" | "UP14DCIL" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "03/10/2023" | "08:00"    | "03/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "15749-RAJAE MORRAR" | "14"  | "11"  | "3"    | "15997-BOGOLJUB ZUJOVIC" | "15"  | "10"  | "3"    | "186254-"    | "15"    | "10"    | "3"      | "171020-"   | "13"   | "10"   | "2"     | "172475-"   | "14"   | "11"   | "3"     | "931235-"  | "15"  | "10"  | "3"    |


    #57
  @Regression @CreateOrders/BCOnlyWithBillingDoc @FailureScreenShot10
  Scenario Outline: Successfully Created BC Only Scenario with Billing Document through Agents Portal
    Given run test for <environment> on browser <browser> in Agents Portal
    And enter the url of Agents Portal
  #  And login on the agents portal with username <username> and password <password>
    And login on the agents portal with username <username> and password <password> FOR HCI ONLY
    And navigate to the bookload page
    And enter Bill To as <billto> From as <from> and To as <to>
    And in References Tab enter the Container as <container> Chassis as <chassis> and Empty Container as <emptycontainer>
    And in References Tab enter the ReferenceOne as <referenceOne> ReferenceTwo as <referenceTwo> and RefOne as <refOne>
    And in Financial Information - Customer Charges Tab enter the Freight Charges as <FreightCharges>
    And in Financial Information - Customer Charges Tab enter the Fuel Surcharges Quantity as <FuelSurchargesQuantity> and Rate as <FuelSurchargesRate> <FuelSurchargesTotal>
    And in Financial Information - Customer Charges Tab enter the Daily Chassis Charges Quantity as <DailyChassisChargesQuantity> and Rate as <DailyChassisChargesRate>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorOne> from the TractorOne Field, Freight Charges <FCOne>, Fuel Surcharges <FSOne> and Daily Chassis Charges <DCCOne>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorTwo> from the TractorTwo Field, Freight Charges <FCTwo>, Fuel Surcharges <FSTwo> and Daily Chassis Charges <DCCTwo>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorThree> from the TractorThree Field, Freight Charges <FCThree>, Fuel Surcharges <FSThree> and Daily Chassis Charges <DCCThree>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFour> from the TractorFour Field, Freight Charges <FCFour>, Fuel Surcharges <FSFour> and Daily Chassis Charges <DCCFour>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorFive> from the TractorFive Field, Freight Charges <FCFive>, Fuel Surcharges <FSFive> and Daily Chassis Charges <DCCFive>
    And in Financial Information - Independent Contractor Pay Tab pass value <TractorSix> from the TractorSix Field, Freight Charges <FCSix>, Fuel Surcharges <FSSix> and Daily Chassis Charges <DCCSix>
    And in Operations Information - enter the Actual Date as <ActualDate> and Time as <ActualTime>
    And in Operations Information - enter the Delivery Appointment Date as <DADate> and Time as <DATime>
    Then click on BCOnly
    Then Click on Scan Documents on Bill Created
    Then close all open browsers
    Examples:
      | environment | browser         | username | password    | billto    | from       | to         | container | chassis | emptycontainer | referenceOne | referenceTwo | refOne  | FreightCharges | FuelSurchargesTotal | ActualDate   | ActualTime | DADate       | DATime  | FuelSurchargesQuantity | FuelSurchargesRate | DailyChassisChargesQuantity | DailyChassisChargesRate | TractorOne               | FCOne | FSOne | DCCOne | TractorTwo | FCTwo | FSTwo | DCCTwo | TractorThree | FCThree | FSThree | DCCThree | TractorFour | FCFour | FSFour | DCCFour | TractorFive            | FCFive | FSFive | DCCFive | TractorSix | FCSix | FSSix | DCCSix |
      | "staging"   | "MicrosoftEdge" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "500"               | "03/10/2023" | "08:00"    | "03/10/2023" | "12:00" | "1"                    | "100"              | "1"                         | "10"                    | "180776-DAVID BLACKBURN" | "14"  | "11"  | "3"    | "931115-"  | "15"  | "10"  | "3"    | "181198-"    | "15"    | "10"    | "3"      | "176623-"   | "13"   | "10"   | "2"     | "931124-CECILIO GOMEZ" | "14"   | "11"   | "3"     | "931115-"  | "15"  | "10"  | "3"    |














  @Regressions @BookLoad
  Scenario: Book a Load through Agents Portal in Staging Environment
    Given run test for "staging" on browser "chrome"
    And enter the url
    And login on the agents portal with username "hcic" and password "legendary"
    And navigate to the bookload page
    And enter billto as "ICARBGA" from as "HCI02147" and to as "HCI02257"
    And in references tab enter container as "fsd" chasis as "asf" and empty container as "xbc"
    And in references tab enter referenceOne as "bxc" referenceTwo as "bbn" and referenceThree as "nbvvc"
    And in financial information - customer charges tab enter freight charges as "1000"
    And in financial information - independent contractor pay tab enter freight charges as "100"
    And in financial information - customer charges tab enter fuel charges quantity as "1" and rate as "100"
    And in financial information - independent contractor pay tab enter fuel surcharges as "10"
    And in financial information - customer charges tab enter daily chasis charges quantity as "1" and rate as "100"
    And in financial information - independent contractor pay tab enter daily chasis charges as "10"
    And in operations information - pickup appointment enter date as "08/25/2021" and time as "10:00"
    And in operations information - actual enter date as "08/26/2021" and time as "10:00"
    Then click on book load and validate if the booking number got generated
    Then retrieve the load with loadnumber and validate the fields
    Then close all open browsers

    #44
  @Regressions @BookLoadScenarioOutLine
  Scenario Outline: Book a Load through Agents Portal in Staging Environment
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
  # And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
   # And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
   # And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
   # And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
   # And in financial information - customer charges tab enter fuel charges quantity as <CustomerChargesFuelChargesQuantity> and rate as <CustomerChargesFuelChargesRate>
  #  And in financial information - independent contractor pay tab enter fuel surcharges as <IndependentContractorFuelSurCharges>
  #  And in financial information - customer charges tab enter daily chasis charges quantity as <CustomerChargesDailyChasisChargesQuantity> and rate as <CustomerChargesDailyChasisChargesRate>
  #  And in financial information - independent contractor pay tab enter daily chasis charges as <IndependentContractorDailyChasisCharges>
  #  And in operations information - pickup appointment enter date as <PickUpAppointmentDate> and time as <PickUpAppointmentTime>
  #  And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
  #  Then click on book load
  #  And click yes on alert
  #  And validate if the booking number got generated
  #  Then click on book load and validate if the booking number got generated
   # Then retrieve the load with loadnumber and validate the fields
    Then close all open browsers
    Examples:
      | environment | browser  | username | password | billto    | from        | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | CustomerChargesFuelChargesQuantity | CustomerChargesFuelChargesRate | IndependentContractorFuelSurCharges | CustomerChargesDailyChasisChargesQuantity | CustomerChargesDailyChasisChargesRate | IndependentContractorDailyChasisCharges | PickUpAppointmentDate | PickUpAppointmentTime | ActualDate   | ActualTime |
    # | "staging"   | "chrome"  | "hcic"   | "legendary" | "ICARBGA" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "1"                                | "100"                          | "10"                                | "1"                                       | "100"                                 | "10"                                    | "09/29/2021"          | "10:00"               | "09/30/2021" | "10:00"    |
    #  | "launch"   | "chrome"  | "pss"   | "taffy" | "ICARBGA" | "PSS00741 " | "PSS00742" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "1"                                | "100"                          | "10"                                | "1"                                       | "100"                                 | "10"                                    | "01/25/2022"          | "03:00"               | "01/26/2022" | "03:00"    |
      | "launch"    | "chrome" | "pss"    | "taffy"  | "ICARBGA" | "WCH00741 " | "WCH00742" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "1"                                | "100"                          | "10"                                | "1"                                       | "100"                                 | "10"                                    | "01/25/2022"          | "03:00"               | "01/26/2022" | "03:00"    |


  @Regressions @SuccessfullyCreatedBCPD
  Scenario Outline: Successfully Created BCPD Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on bill customer pay driver and validate if the booking number got generated
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |

  @Regressions @SuccessfullyCreatedPD
  Scenario Outline: Successfully Created PD Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on pay driver only and validate if the booking number got generated
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "ICARBGA" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |

  @Regressions @SuccessfullyCreatedBC
  Scenario Outline: Successfully Created BC Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on bill customer only and validate if the booking number got generated
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "ICARBGA" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |


  @Regressions @ActualDateAndTimeBCPDErrorHandling
  Scenario Outline: Actual Date And Time BCPD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    Then click on bill customer pay driver and validate if it shows actual date and time error
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" |

  @Regressions @ActualDateAndTimeBCErrorHandling
  Scenario Outline: Actual Date And Time BC Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    Then click on bill customer only and validate if it shows actual date and time error
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" |


  @Regressions @TractorOneChargeBCPDErrorHandling
  Scenario Outline: Tractor One Charge BCPD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on bill customer pay driver and validate if it shows the tractor one charge error
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |

  @Regressions @TractorOneChargePDErrorHandling
  Scenario Outline: Tractor One Charge PD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on pay driver only and validate if it shows the tractor one charge error
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |


  @Regressions @TractorOneIdBCPDErrorHandling
  Scenario Outline: Tractor One Id BCPD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on bill customer pay driver and validate if it shows the tractor one id error
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "09/26/2021" | "10:00"    |

  @Regressions @TractorOneIdPDErrorHandling
  Scenario Outline: Tractor One Id PD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on pay driver only and validate if it shows the tractor one id error
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "09/26/2021" | "10:00"    |


  @Regressions @AcceptRiskWhenAcceptingOrderBCPDErrorHandling
  Scenario Outline: Accept Risk When Accepting Order BCPD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on bill customer pay driver and validate if accept risk when accepting order alert
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto  | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "MJCBT" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |


  @Regressions @AcceptRiskWhenAcceptingOrderBCPDErrorHandling
  Scenario Outline: Accept Risk When Accepting Order BCPD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on pay driver only and validate if accept risk when accepting order alert
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto  | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "MJCBT" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |



#  need to automate with correct data
  @Regressions @CashOnlyBCPDErrorHandling
  Scenario Outline: Cash Only BCPD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on bill customer pay driver and validate if accept risk when accepting order alert
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto  | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "MJCBT" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |


  @databaseconnect
  Scenario Outline: Connect to database
    Given Connect to <environment> and <tableName>
    Examples:
      | environment | tableName                                  |
      | "launch"    | "[EBHLaunch].[dbo].[ACCOUNTS_CLASS_CODES]" |
