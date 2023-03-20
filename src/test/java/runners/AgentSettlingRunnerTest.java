package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/featureFiles"},
        glue = {"stepDefinitions"},
        monochrome = true,
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "html:target/cucumber-reports/cucumber.html",
                "rerun:target/cucumber-reports/rerun.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        tags = "@AARAgentManagementFile"
)


public class AgentSettlingRunnerTest {

}



/// AgentSettling.feature
// 1   @OWTBFContinue
// 2   @OWTBFReset
// 3   @SettleYourAgent&ViewEpicorExcelReportsContinue
// 4   @SettleYourAgent&ViewEpicorExcelReportsReset
// 5   @SettlementOrderDetailsContinue
// 6   @SettlementOrderDetailsReset
// 7   @AgentsYoureSettlingAddedRemoved
// 8   @AdjustmentsDetailsAndAdjustmentsOnHold
// 9   @MyActiveAgentsNotSettled
// 10  @AgentSettlementFlagMaintenance
// 11  @DBAgentSettlements&AgentStlTrans
// 12  @DBOrderDocs&TfDocDataD4

/// AgentSettlementInquiry.feature
// 13 @AgentSettlementInquiryAgent
// 14 @AgentSettlementInquiryVendor
// 15 @AgentSettlementInquiryPayCodes

/// AgentsCurrentlyBeingSettled.feature
// 16 @AgentsCurrentlyBeingSettledVendorCode
// 17 @AgentsCurrentlyBeingSettledAgentCode

/// AgentCommission.feature
// 18 @AgentCommissionMaintenance
// 19 @AgentCommissionCalculation

/// BasicFileMaintenance.feature
// 20 @AccountFile
// 21 @LocationFile

/// @AARAgentManagementFile.feature
// 22 @AARAgentManagementFile
// 23 @AARReassignment
// 24 @AARReassignmentAgentThatSharesVendorCodeWithOtherAgents
// 25 @AARAgentManagementWeeklyCutoffDay/Time

/// AgentSettlementAdjustments.feature
// 26 @AgentSettlementAdjustmentsVendorId
// 27 @AgentSettlementAdjustmentsAgentCode
// 28 @ScenarioActiveCompleteActive
// 29 @ScenarioOnNewDATE
// 30 @ScenarioOnNewMaxLimitandTotaltoDate
// 31 @AgentSettlementAdjustmentsVendorCodePayCodeOrderNo
// 32 @AgentSettlementAdjustmentsAgentCodePayCodeOrderNo
// 33 @ScenarioViewButton
// 34 @ScenarioDeleteButton
// 35 @ScenarioReport
// 36 @ScenarioQuickEntry

/// EquipmentConsole.feature
// 37  @IdentifyINVOICE-AgentReimbursement/Deduction
// 38  @AgentReimbursement
// 39  @IdentifyINVOICE-TestForAgentWithoutRecordOnAgentSettlementInfoTable-AgentReimbursement/Deduction
// 40  @AgentReimbursement/TestForAgentWithoutRecordOnAgentSettlementInfoTable
// 41  @AgentDeduction
// 42  @AgentDeduction/TestForAgentWithoutRecordOnAgentSettlementInfoTable
// 43a @VerifyTaxAndAdminBillingCodesHaveBeenSetup&IdentifyINVOICE-BillCustomer
// 43b @BillCustomer
// 43c @RetrieveNewOrderPRo
// 43d @VerifyDataInTables

/// Billing.feature
// 44 @BookLoadScenarioOutLine

/// BCPDTractorValidation.feature
// 45a  @BCPDCreateBillPositive
// 45b  @BCPDCreateBillNeg
// 45ab @BCPDOrderNumOnDB
// 46a  @PDOnlyCreateBillPositive
// 46b  @PDOnlyCreateBillNeg
// 46ab @PDOnlyOrderNumOnDB

/// FuelPurchaseMaintenance.feature
// 47 @FPMValidationTractorID
// 48 @FPMValidationTractorIDEarliestDate
// 49 @FPMValidationTractorIDLatestDate
// 50 @FPMValidationIFTA
// 51 @FPMValidationIFTAEarliestDate
// 52 @FPMValidationIFTALatestDate
// 53 @FPMScenarioNew
// 54 @FPMScenarioEdit
// 55 @FPMScenarioReportTractorID
// 56 @FPMScenarioReportIFTA

/// AARAgentManagementFile.feature
// 57 @TractorAARLogin
// 58 @TractorAARLoginNoOneIsCurrentlySettling
// 59 @TractorAARLoginAnotherAARisSettlingTheAgentVerified
// 60 @TractorAARLoginAnotherAARisSettlingTheAgentNOTVerified

/// TractorVendorRelationship.feature
// 61 @TractorVendorRelationshipUnitNumber/Report
// 62 @TractorVendorRelationshipVendorCode/Report
// 63 @TractorVendorRelationshipOwnerId/Report
// 64 @TractorVendorRelationship-Location/Report
// 65 @TractorVendorRelationshipEDIT
// 66 @TractorVendorRelationshipNEW
// 67 @ActiveTractorsNotInaRelationship
// 68 @ActiveTractorsNotInaRelationshipNEW

/// TractorSettlementAdjustmentsStepDef.feature
// 69  @TractorSettlementAdjustmentsUnitNo
// 70 @TractorSettlementAdjustmentsLocation
// 71 @TractorSettlementAdjustmentsScenarioReport
// 72 @TractorSettlementAdjustmentsScenarioNew
// 73 @TractorSettlementAdjustmentsScenarioQuickEntry
// 74 @TractorSettlementAdjustmentsScenarioQuickEdit
// 75 @TractorSettlementAdjustmentsScenarioEdit
// 76 @TSAScenarioAdvancedSearchUnitNo
// 77 @TSAScenarioAdvancedSearchLocation
// 78 @TSAScenarioAdvancedSearchEdit
// 79 @TSAScenarioAdvancedSearchReport

/// ResourceMaintenance.feature
// 80 @ResourceMaintenanceSearch/Report
// 81 @ResourceMaintenanceView/Edit
// 82 @ResourceMaintenanceNew

/// EquipmentConsoleTractorAdjustments.feature
// 83 @IdentifyInvoiceAndValidTractorNoForTractorReimbursement/Deduct
// 84 @TractorReimbursement
// 85 @TractorReimbursement/TractorWithoutRecordOnTractorVendorTable
// 86 @TractorReimbursement/TractorNotCurrentlyAssignedToAgentOnDriverReimbursementForm
// 87 @TractorReimbursement/TractorThatDoesNotHaveTractorRecordWithStatusOfCurrent
// 88 @TractorDeduct
// 89 @TractorDeduct/TractorWithoutRecordOnTractorVendorTable
// 90 @TractorDeduct/TractorNotCurrentlyAssignedToAgentOnDriverDeductForm
// 91 @TractorDeduct/TractorThatDoesNotHaveTractorRecordWithStatusOfCurrent
// 92 @IdentifyInvoiceForSplitAmount
// 93 @SplitAmount
// 94 @IdentifyInvoiceForSplitAmountTestForAgentWithoutRecordOnAgentSettlementInfoTable
// 95 @SplitAmount/TestForAgentWithoutRecordOnAgentSettlementInfoTable
// 96 @SplitAmount/TractorWithoutRecordOnTractorVendorTable
// 97 @SplitAmount/TestforTractorNotCurrentlyAssignedToAgentOnSplitAmountForm
// 98 @SplitAmount/TractorThatDoesNotHaveTractorRecordWithStatusOfCurrent
