/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.schors.evlampia.json.fedex;

import java.util.List;

public class PackageListItem {
    private String trackingNbr;
    private String trackingQualifier;
    private String trackingCarrierCd;
    private String trackingCarrierDesc;
    private String displayTrackingNbr;
    private String shipperCmpnyName;
    private String shipperName;
    private String shipperAddr1;
    private String shipperAddr2;
    private String shipperCity;
    private String shipperStateCD;
    private String shipperZip;
    private String shipperCntryCD;
    private String shipperPhoneNbr;
    private String shippedBy;
    private String recipientCmpnyName;
    private String recipientName;
    private String recipientAddr1;
    private String recipientAddr2;
    private String recipientCity;
    private String recipientStateCD;
    private String recipientZip;
    private String recipientCntryCD;
    private String recipientPhoneNbr;
    private String shippedTo;
    private String keyStatus;
    private String keyStatusCD;
    private String lastScanStatus;
    private String lastScanDateTime;
    private String receivedByNm;
    private String subStatus;
    private String mainStatus;
    private String statusBarCD;
    private String shortStatus;
    private String shortStatusCD;
    private String statusLocationAddr1;
    private String statusLocationAddr2;
    private String statusLocationCity;
    private String statusLocationStateCD;
    private String statusLocationZip;
    private String statusLocationCntryCD;
    private String statusWithDetails;
    private String shipDt;
    private String displayShipDt;
    private String displayShipTm;
    private String displayShipDateTime;
    private String pickupDt;
    private String displayPickupDt;
    private String displayPickupTm;
    private String displayPickupDateTime;
    private String estDeliveryDt;
    private String estDeliveryTm;
    private String displayEstDeliveryDt;
    private String displayEstDeliveryTm;
    private String displayEstDeliveryDateTime;
    private String actDeliveryDt;
    private String displayActDeliveryDt;
    private String displayActDeliveryTm;
    private String displayActDeliveryDateTime;
    private String nickName;
    private String note;
    private List<String> matchedAccountList;
    private String fxfAdvanceETA;
    private String fxfAdvanceReason;
    private String fxfAdvanceStatusCode;
    private String fxfAdvanceStatusDesc;
    private String destLink;
    private String originLink;
    private boolean hasBillOfLadingImage;
    private boolean hasBillPresentment;
    private int signatureRequired;
    private String totalKgsWgt;
    private String displayTotalKgsWgt;
    private String totalLbsWgt;
    private String displayTotalLbsWgt;
    private String displayTotalWgt;
    private String pkgKgsWgt;
    private String displayPkgKgsWgt;
    private String pkgLbsWgt;
    private String displayPkgLbsWgt;
    private String displayPkgWgt;
    private String dimensions;
    private String masterTrackingNbr;
    private String masterQualifier;
    private String masterCarrierCD;
    private String originalOutboundTrackingNbr;
    private String originalOutboundQualifier;
    private String originalOutboundCarrierCD;
    private List<String> invoiceNbrList;
    private List<String> referenceList;
    private List<String> doorTagNbrList;
    private List<String> referenceDescList;
    private List<String> purchaseOrderNbrList;
    private List<String> billofLadingNbrList;
    private List<String> shipperRefList;
    private List<String> rmaList;
    private List<String> deptNbrList;
    private List<String> shipmentIdList;
    private List<String> tcnList;
    private List<String> partnerCarrierNbrList;
    private boolean hasAssociatedShipments;
    private boolean hasAssociatedReturnShipments;
    private int assocShpGrp;
    private int[] drTgGrp;
    private List<AssociationInfo> associationInfoList;
    private String returnReason;
    private String returnRelationship;
    private List<String> skuItemUpcCdList;
    private List<String> receiveQtyList;
    private List<String> itemDescList;
    private List<String> partNbrList;
    private String serviceCD;
    private String serviceDesc;
    private String serviceShortDesc;
    private String packageType;
    private String packaging;
    private String clearanceDetailLink;
    private boolean showClearanceDetailLink;
    private List<String> manufactureCountryCDList;
    private List<String> commodityCDList;
    private List<String> commodityDescList;
    private List<String> cerNbrList;
    private List<String> cerComplaintCDList;
    private List<String> cerComplaintDescList;
    private List<String> cerEventDateList;
    private List<String> displayCerEventDateList;
    private String totalPieces;
    private List<String> specialHandlingServicesList;
    private String shipmentType;
    private String pkgContentDesc1;
    private String pkgContentDesc2;
    private String docAWBNbr;
    private String originalCharges;
    private String transportationCD;
    private String transportationDesc;
    private String dutiesAndTaxesCD;
    private String dutiesAndTaxesDesc;
    private String origPieceCount;
    private String destPieceCount;
    private String goodsClassificationCD;
    private String receipientAddrQty;
    private String deliveryAttempt;
    private String codReturnTrackNbr;
    private List<ScanEvent> scanEventList;
    private String originAddr1;
    private String originAddr2;
    private String originCity;
    private String originStateCD;
    private String originZip;
    private String originCntryCD;
    private String originLocationID;
    private String originTermCity;
    private String originTermStateCD;
    private String destLocationAddr1;
    private String destLocationAddr2;
    private String destLocationCity;
    private String destLocationStateCD;
    private String destLocationZip;
    private String destLocationCntryCD;
    private String destLocationID;
    private String destLocationTermCity;
    private String destLocationTermStateCD;
    private String destAddr1;
    private String destAddr2;
    private String destCity;
    private String destStateCD;
    private String destZip;
    private String destCntryCD;
    private String halAddr1;
    private String halAddr2;
    private String halCity;
    private String halStateCD;
    private String halZipCD;
    private String halCntryCD;
    private String actualDelAddrCity;
    private String actualDelAddrStateCD;
    private String actualDelAddrZipCD;
    private String actualDelAddrCntryCD;
    private String totalTransitMiles;
    private List<String> excepReasonList;
    private List<String> excepActionList;
    private String exceptionReason;
    private String exceptionAction;
    private List<String> statusDetailsList;
    private String trackErrCD;
    private String destTZ;
    private String originTZ;
    private String isMultiStat;
    private List<MultiStat> multiStatList;
    private String maskMessage;
    private String deliveryService;
    private String milestoDestination;
    private String terms;
    private String originUbanizationCode;
    private String originCountryName;
    private boolean isOriginResidential;
    private String halUrbanizationCD;
    private String halCountryName;
    private String actualDelAddrUrbanizationCD;
    private String actualDelAddrCountryName;
    private String destUrbanizationCD;
    private String destCountryName;
    private String delToDesc;
    private String recpShareID;
    private String shprShareID;
    private String defaultCDOType;
    private String mpstype;
    private boolean fxfAdvanceNotice;
    private String rthavailableCD;
    private List<String> excepReasonListNoInit;
    private List<String> excepActionListNoInit;
    private List<String> statusDetailsListNoInit;
    private boolean matched;
    private boolean isSuccessful;
    private List<ErrorListItem> errorList;
    private boolean isCanceled;
    private boolean isPrePickup;
    private boolean isPickup;
    private boolean isInTransit;
    private boolean isInProgress;
    private boolean isDelException;
    private boolean isClearanceDelay;
    private boolean isException;
    private boolean isDelivered;
    private boolean isHAL;
    private boolean isOnSchedule;
    private boolean isDeliveryToday;
    private boolean isSave;
    private boolean isWatch;
    private boolean isHistorical;
    private boolean isTenderedNotification;
    private boolean isDeliveredNotification;
    private boolean isExceptionNotification;
    private boolean isCurrentStatusNotification;
    private boolean isAnticipatedShipDtLabel;
    private boolean isShipPickupDtLabel;
    private boolean isActualPickupLabel;
    private boolean isOrderReceivedLabel;
    private boolean isEstimatedDeliveryDtLabel;
    private boolean isDeliveryDtLabel;
    private boolean isActualDeliveryDtLabel;
    private boolean isOrderCompleteLabel;
    private boolean isOutboundDirection;
    private boolean isInboundDirection;
    private boolean isThirdpartyDirection;
    private boolean isUnknownDirection;
    private boolean isFSM;
    private boolean isReturn;
    private boolean isOriginalOutBound;
    private boolean isChildPackage;
    private boolean isParentPackage;
    private boolean isReclassifiedAsSingleShipment;
    private boolean isDuplicate;
    private boolean isMaskShipper;
    private boolean isHalEligible;
    private boolean isFedexOfficeOnlineOrders;
    private boolean isFedexOfficeInStoreOrders;
    private boolean isMultipleStop;
    private boolean isCustomCritical;
    private boolean isInvalid;
    private boolean isNotFound;
    private boolean isFreight;
    private boolean isSpod;
    private boolean isSignatureAvailable;
    private boolean isMPS;
    private boolean isGMPS;
    private boolean isResidential;
    private boolean isDestResidential;
    private boolean isHALResidential;
    private boolean isActualDelAddrResidential;
    private boolean isReqEstDelDt;
    private boolean isCDOEligible;
    private List<CDOInfo> CDOInfoList;
    private boolean CDOExists;
    private boolean isMtchdByRecShrID;
    private boolean isMtchdByShiprShrID;

    public PackageListItem() {
    }

    public String getTrackingNbr() {
        return trackingNbr;
    }

    public void setTrackingNbr(String trackingNbr) {
        this.trackingNbr = trackingNbr;
    }

    public String getTrackingQualifier() {
        return trackingQualifier;
    }

    public void setTrackingQualifier(String trackingQualifier) {
        this.trackingQualifier = trackingQualifier;
    }

    public String getTrackingCarrierCd() {
        return trackingCarrierCd;
    }

    public void setTrackingCarrierCd(String trackingCarrierCd) {
        this.trackingCarrierCd = trackingCarrierCd;
    }

    public String getTrackingCarrierDesc() {
        return trackingCarrierDesc;
    }

    public void setTrackingCarrierDesc(String trackingCarrierDesc) {
        this.trackingCarrierDesc = trackingCarrierDesc;
    }

    public String getDisplayTrackingNbr() {
        return displayTrackingNbr;
    }

    public void setDisplayTrackingNbr(String displayTrackingNbr) {
        this.displayTrackingNbr = displayTrackingNbr;
    }

    public String getShipperCmpnyName() {
        return shipperCmpnyName;
    }

    public void setShipperCmpnyName(String shipperCmpnyName) {
        this.shipperCmpnyName = shipperCmpnyName;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperAddr1() {
        return shipperAddr1;
    }

    public void setShipperAddr1(String shipperAddr1) {
        this.shipperAddr1 = shipperAddr1;
    }

    public String getShipperAddr2() {
        return shipperAddr2;
    }

    public void setShipperAddr2(String shipperAddr2) {
        this.shipperAddr2 = shipperAddr2;
    }

    public String getShipperCity() {
        return shipperCity;
    }

    public void setShipperCity(String shipperCity) {
        this.shipperCity = shipperCity;
    }

    public String getShipperStateCD() {
        return shipperStateCD;
    }

    public void setShipperStateCD(String shipperStateCD) {
        this.shipperStateCD = shipperStateCD;
    }

    public String getShipperZip() {
        return shipperZip;
    }

    public void setShipperZip(String shipperZip) {
        this.shipperZip = shipperZip;
    }

    public String getShipperCntryCD() {
        return shipperCntryCD;
    }

    public void setShipperCntryCD(String shipperCntryCD) {
        this.shipperCntryCD = shipperCntryCD;
    }

    public String getShipperPhoneNbr() {
        return shipperPhoneNbr;
    }

    public void setShipperPhoneNbr(String shipperPhoneNbr) {
        this.shipperPhoneNbr = shipperPhoneNbr;
    }

    public String getShippedBy() {
        return shippedBy;
    }

    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
    }

    public String getRecipientCmpnyName() {
        return recipientCmpnyName;
    }

    public void setRecipientCmpnyName(String recipientCmpnyName) {
        this.recipientCmpnyName = recipientCmpnyName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientAddr1() {
        return recipientAddr1;
    }

    public void setRecipientAddr1(String recipientAddr1) {
        this.recipientAddr1 = recipientAddr1;
    }

    public String getRecipientAddr2() {
        return recipientAddr2;
    }

    public void setRecipientAddr2(String recipientAddr2) {
        this.recipientAddr2 = recipientAddr2;
    }

    public String getRecipientCity() {
        return recipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        this.recipientCity = recipientCity;
    }

    public String getRecipientStateCD() {
        return recipientStateCD;
    }

    public void setRecipientStateCD(String recipientStateCD) {
        this.recipientStateCD = recipientStateCD;
    }

    public String getRecipientZip() {
        return recipientZip;
    }

    public void setRecipientZip(String recipientZip) {
        this.recipientZip = recipientZip;
    }

    public String getRecipientCntryCD() {
        return recipientCntryCD;
    }

    public void setRecipientCntryCD(String recipientCntryCD) {
        this.recipientCntryCD = recipientCntryCD;
    }

    public String getRecipientPhoneNbr() {
        return recipientPhoneNbr;
    }

    public void setRecipientPhoneNbr(String recipientPhoneNbr) {
        this.recipientPhoneNbr = recipientPhoneNbr;
    }

    public String getShippedTo() {
        return shippedTo;
    }

    public void setShippedTo(String shippedTo) {
        this.shippedTo = shippedTo;
    }

    public String getKeyStatus() {
        return keyStatus;
    }

    public void setKeyStatus(String keyStatus) {
        this.keyStatus = keyStatus;
    }

    public String getKeyStatusCD() {
        return keyStatusCD;
    }

    public void setKeyStatusCD(String keyStatusCD) {
        this.keyStatusCD = keyStatusCD;
    }

    public String getLastScanStatus() {
        return lastScanStatus;
    }

    public void setLastScanStatus(String lastScanStatus) {
        this.lastScanStatus = lastScanStatus;
    }

    public String getLastScanDateTime() {
        return lastScanDateTime;
    }

    public void setLastScanDateTime(String lastScanDateTime) {
        this.lastScanDateTime = lastScanDateTime;
    }

    public String getReceivedByNm() {
        return receivedByNm;
    }

    public void setReceivedByNm(String receivedByNm) {
        this.receivedByNm = receivedByNm;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public String getMainStatus() {
        return mainStatus;
    }

    public void setMainStatus(String mainStatus) {
        this.mainStatus = mainStatus;
    }

    public String getStatusBarCD() {
        return statusBarCD;
    }

    public void setStatusBarCD(String statusBarCD) {
        this.statusBarCD = statusBarCD;
    }

    public String getShortStatus() {
        return shortStatus;
    }

    public void setShortStatus(String shortStatus) {
        this.shortStatus = shortStatus;
    }

    public String getShortStatusCD() {
        return shortStatusCD;
    }

    public void setShortStatusCD(String shortStatusCD) {
        this.shortStatusCD = shortStatusCD;
    }

    public String getStatusLocationAddr1() {
        return statusLocationAddr1;
    }

    public void setStatusLocationAddr1(String statusLocationAddr1) {
        this.statusLocationAddr1 = statusLocationAddr1;
    }

    public String getStatusLocationAddr2() {
        return statusLocationAddr2;
    }

    public void setStatusLocationAddr2(String statusLocationAddr2) {
        this.statusLocationAddr2 = statusLocationAddr2;
    }

    public String getStatusLocationCity() {
        return statusLocationCity;
    }

    public void setStatusLocationCity(String statusLocationCity) {
        this.statusLocationCity = statusLocationCity;
    }

    public String getStatusLocationStateCD() {
        return statusLocationStateCD;
    }

    public void setStatusLocationStateCD(String statusLocationStateCD) {
        this.statusLocationStateCD = statusLocationStateCD;
    }

    public String getStatusLocationZip() {
        return statusLocationZip;
    }

    public void setStatusLocationZip(String statusLocationZip) {
        this.statusLocationZip = statusLocationZip;
    }

    public String getStatusLocationCntryCD() {
        return statusLocationCntryCD;
    }

    public void setStatusLocationCntryCD(String statusLocationCntryCD) {
        this.statusLocationCntryCD = statusLocationCntryCD;
    }

    public String getStatusWithDetails() {
        return statusWithDetails;
    }

    public void setStatusWithDetails(String statusWithDetails) {
        this.statusWithDetails = statusWithDetails;
    }

    public String getShipDt() {
        return shipDt;
    }

    public void setShipDt(String shipDt) {
        this.shipDt = shipDt;
    }

    public String getDisplayShipDt() {
        return displayShipDt;
    }

    public void setDisplayShipDt(String displayShipDt) {
        this.displayShipDt = displayShipDt;
    }

    public String getDisplayShipTm() {
        return displayShipTm;
    }

    public void setDisplayShipTm(String displayShipTm) {
        this.displayShipTm = displayShipTm;
    }

    public String getDisplayShipDateTime() {
        return displayShipDateTime;
    }

    public void setDisplayShipDateTime(String displayShipDateTime) {
        this.displayShipDateTime = displayShipDateTime;
    }

    public String getPickupDt() {
        return pickupDt;
    }

    public void setPickupDt(String pickupDt) {
        this.pickupDt = pickupDt;
    }

    public String getDisplayPickupDt() {
        return displayPickupDt;
    }

    public void setDisplayPickupDt(String displayPickupDt) {
        this.displayPickupDt = displayPickupDt;
    }

    public String getDisplayPickupTm() {
        return displayPickupTm;
    }

    public void setDisplayPickupTm(String displayPickupTm) {
        this.displayPickupTm = displayPickupTm;
    }

    public String getDisplayPickupDateTime() {
        return displayPickupDateTime;
    }

    public void setDisplayPickupDateTime(String displayPickupDateTime) {
        this.displayPickupDateTime = displayPickupDateTime;
    }

    public String getEstDeliveryDt() {
        return estDeliveryDt;
    }

    public void setEstDeliveryDt(String estDeliveryDt) {
        this.estDeliveryDt = estDeliveryDt;
    }

    public String getEstDeliveryTm() {
        return estDeliveryTm;
    }

    public void setEstDeliveryTm(String estDeliveryTm) {
        this.estDeliveryTm = estDeliveryTm;
    }

    public String getDisplayEstDeliveryDt() {
        return displayEstDeliveryDt;
    }

    public void setDisplayEstDeliveryDt(String displayEstDeliveryDt) {
        this.displayEstDeliveryDt = displayEstDeliveryDt;
    }

    public String getDisplayEstDeliveryTm() {
        return displayEstDeliveryTm;
    }

    public void setDisplayEstDeliveryTm(String displayEstDeliveryTm) {
        this.displayEstDeliveryTm = displayEstDeliveryTm;
    }

    public String getDisplayEstDeliveryDateTime() {
        return displayEstDeliveryDateTime;
    }

    public void setDisplayEstDeliveryDateTime(String displayEstDeliveryDateTime) {
        this.displayEstDeliveryDateTime = displayEstDeliveryDateTime;
    }

    public String getActDeliveryDt() {
        return actDeliveryDt;
    }

    public void setActDeliveryDt(String actDeliveryDt) {
        this.actDeliveryDt = actDeliveryDt;
    }

    public String getDisplayActDeliveryDt() {
        return displayActDeliveryDt;
    }

    public void setDisplayActDeliveryDt(String displayActDeliveryDt) {
        this.displayActDeliveryDt = displayActDeliveryDt;
    }

    public String getDisplayActDeliveryTm() {
        return displayActDeliveryTm;
    }

    public void setDisplayActDeliveryTm(String displayActDeliveryTm) {
        this.displayActDeliveryTm = displayActDeliveryTm;
    }

    public String getDisplayActDeliveryDateTime() {
        return displayActDeliveryDateTime;
    }

    public void setDisplayActDeliveryDateTime(String displayActDeliveryDateTime) {
        this.displayActDeliveryDateTime = displayActDeliveryDateTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<String> getMatchedAccountList() {
        return matchedAccountList;
    }

    public void setMatchedAccountList(List<String> matchedAccountList) {
        this.matchedAccountList = matchedAccountList;
    }

    public String getFxfAdvanceETA() {
        return fxfAdvanceETA;
    }

    public void setFxfAdvanceETA(String fxfAdvanceETA) {
        this.fxfAdvanceETA = fxfAdvanceETA;
    }

    public String getFxfAdvanceReason() {
        return fxfAdvanceReason;
    }

    public void setFxfAdvanceReason(String fxfAdvanceReason) {
        this.fxfAdvanceReason = fxfAdvanceReason;
    }

    public String getFxfAdvanceStatusCode() {
        return fxfAdvanceStatusCode;
    }

    public void setFxfAdvanceStatusCode(String fxfAdvanceStatusCode) {
        this.fxfAdvanceStatusCode = fxfAdvanceStatusCode;
    }

    public String getFxfAdvanceStatusDesc() {
        return fxfAdvanceStatusDesc;
    }

    public void setFxfAdvanceStatusDesc(String fxfAdvanceStatusDesc) {
        this.fxfAdvanceStatusDesc = fxfAdvanceStatusDesc;
    }

    public String getDestLink() {
        return destLink;
    }

    public void setDestLink(String destLink) {
        this.destLink = destLink;
    }

    public String getOriginLink() {
        return originLink;
    }

    public void setOriginLink(String originLink) {
        this.originLink = originLink;
    }

    public boolean isHasBillOfLadingImage() {
        return hasBillOfLadingImage;
    }

    public void setHasBillOfLadingImage(boolean hasBillOfLadingImage) {
        this.hasBillOfLadingImage = hasBillOfLadingImage;
    }

    public boolean isHasBillPresentment() {
        return hasBillPresentment;
    }

    public void setHasBillPresentment(boolean hasBillPresentment) {
        this.hasBillPresentment = hasBillPresentment;
    }

    public int getSignatureRequired() {
        return signatureRequired;
    }

    public void setSignatureRequired(int signatureRequired) {
        this.signatureRequired = signatureRequired;
    }

    public String getTotalKgsWgt() {
        return totalKgsWgt;
    }

    public void setTotalKgsWgt(String totalKgsWgt) {
        this.totalKgsWgt = totalKgsWgt;
    }

    public String getDisplayTotalKgsWgt() {
        return displayTotalKgsWgt;
    }

    public void setDisplayTotalKgsWgt(String displayTotalKgsWgt) {
        this.displayTotalKgsWgt = displayTotalKgsWgt;
    }

    public String getTotalLbsWgt() {
        return totalLbsWgt;
    }

    public void setTotalLbsWgt(String totalLbsWgt) {
        this.totalLbsWgt = totalLbsWgt;
    }

    public String getDisplayTotalLbsWgt() {
        return displayTotalLbsWgt;
    }

    public void setDisplayTotalLbsWgt(String displayTotalLbsWgt) {
        this.displayTotalLbsWgt = displayTotalLbsWgt;
    }

    public String getDisplayTotalWgt() {
        return displayTotalWgt;
    }

    public void setDisplayTotalWgt(String displayTotalWgt) {
        this.displayTotalWgt = displayTotalWgt;
    }

    public String getPkgKgsWgt() {
        return pkgKgsWgt;
    }

    public void setPkgKgsWgt(String pkgKgsWgt) {
        this.pkgKgsWgt = pkgKgsWgt;
    }

    public String getDisplayPkgKgsWgt() {
        return displayPkgKgsWgt;
    }

    public void setDisplayPkgKgsWgt(String displayPkgKgsWgt) {
        this.displayPkgKgsWgt = displayPkgKgsWgt;
    }

    public String getPkgLbsWgt() {
        return pkgLbsWgt;
    }

    public void setPkgLbsWgt(String pkgLbsWgt) {
        this.pkgLbsWgt = pkgLbsWgt;
    }

    public String getDisplayPkgLbsWgt() {
        return displayPkgLbsWgt;
    }

    public void setDisplayPkgLbsWgt(String displayPkgLbsWgt) {
        this.displayPkgLbsWgt = displayPkgLbsWgt;
    }

    public String getDisplayPkgWgt() {
        return displayPkgWgt;
    }

    public void setDisplayPkgWgt(String displayPkgWgt) {
        this.displayPkgWgt = displayPkgWgt;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getMasterTrackingNbr() {
        return masterTrackingNbr;
    }

    public void setMasterTrackingNbr(String masterTrackingNbr) {
        this.masterTrackingNbr = masterTrackingNbr;
    }

    public String getMasterQualifier() {
        return masterQualifier;
    }

    public void setMasterQualifier(String masterQualifier) {
        this.masterQualifier = masterQualifier;
    }

    public String getMasterCarrierCD() {
        return masterCarrierCD;
    }

    public void setMasterCarrierCD(String masterCarrierCD) {
        this.masterCarrierCD = masterCarrierCD;
    }

    public String getOriginalOutboundTrackingNbr() {
        return originalOutboundTrackingNbr;
    }

    public void setOriginalOutboundTrackingNbr(String originalOutboundTrackingNbr) {
        this.originalOutboundTrackingNbr = originalOutboundTrackingNbr;
    }

    public String getOriginalOutboundQualifier() {
        return originalOutboundQualifier;
    }

    public void setOriginalOutboundQualifier(String originalOutboundQualifier) {
        this.originalOutboundQualifier = originalOutboundQualifier;
    }

    public String getOriginalOutboundCarrierCD() {
        return originalOutboundCarrierCD;
    }

    public void setOriginalOutboundCarrierCD(String originalOutboundCarrierCD) {
        this.originalOutboundCarrierCD = originalOutboundCarrierCD;
    }

    public List<String> getInvoiceNbrList() {
        return invoiceNbrList;
    }

    public void setInvoiceNbrList(List<String> invoiceNbrList) {
        this.invoiceNbrList = invoiceNbrList;
    }

    public List<String> getReferenceList() {
        return referenceList;
    }

    public void setReferenceList(List<String> referenceList) {
        this.referenceList = referenceList;
    }

    public List<String> getDoorTagNbrList() {
        return doorTagNbrList;
    }

    public void setDoorTagNbrList(List<String> doorTagNbrList) {
        this.doorTagNbrList = doorTagNbrList;
    }

    public List<String> getReferenceDescList() {
        return referenceDescList;
    }

    public void setReferenceDescList(List<String> referenceDescList) {
        this.referenceDescList = referenceDescList;
    }

    public List<String> getPurchaseOrderNbrList() {
        return purchaseOrderNbrList;
    }

    public void setPurchaseOrderNbrList(List<String> purchaseOrderNbrList) {
        this.purchaseOrderNbrList = purchaseOrderNbrList;
    }

    public List<String> getBillofLadingNbrList() {
        return billofLadingNbrList;
    }

    public void setBillofLadingNbrList(List<String> billofLadingNbrList) {
        this.billofLadingNbrList = billofLadingNbrList;
    }

    public List<String> getShipperRefList() {
        return shipperRefList;
    }

    public void setShipperRefList(List<String> shipperRefList) {
        this.shipperRefList = shipperRefList;
    }

    public List<String> getRmaList() {
        return rmaList;
    }

    public void setRmaList(List<String> rmaList) {
        this.rmaList = rmaList;
    }

    public List<String> getDeptNbrList() {
        return deptNbrList;
    }

    public void setDeptNbrList(List<String> deptNbrList) {
        this.deptNbrList = deptNbrList;
    }

    public List<String> getShipmentIdList() {
        return shipmentIdList;
    }

    public void setShipmentIdList(List<String> shipmentIdList) {
        this.shipmentIdList = shipmentIdList;
    }

    public List<String> getTcnList() {
        return tcnList;
    }

    public void setTcnList(List<String> tcnList) {
        this.tcnList = tcnList;
    }

    public List<String> getPartnerCarrierNbrList() {
        return partnerCarrierNbrList;
    }

    public void setPartnerCarrierNbrList(List<String> partnerCarrierNbrList) {
        this.partnerCarrierNbrList = partnerCarrierNbrList;
    }

    public boolean isHasAssociatedShipments() {
        return hasAssociatedShipments;
    }

    public void setHasAssociatedShipments(boolean hasAssociatedShipments) {
        this.hasAssociatedShipments = hasAssociatedShipments;
    }

    public boolean isHasAssociatedReturnShipments() {
        return hasAssociatedReturnShipments;
    }

    public void setHasAssociatedReturnShipments(boolean hasAssociatedReturnShipments) {
        this.hasAssociatedReturnShipments = hasAssociatedReturnShipments;
    }

    public int getAssocShpGrp() {
        return assocShpGrp;
    }

    public void setAssocShpGrp(int assocShpGrp) {
        this.assocShpGrp = assocShpGrp;
    }

    public int[] getDrTgGrp() {
        return drTgGrp;
    }

    public void setDrTgGrp(int[] drTgGrp) {
        this.drTgGrp = drTgGrp;
    }

    public List<AssociationInfo> getAssociationInfoList() {
        return associationInfoList;
    }

    public void setAssociationInfoList(List<AssociationInfo> associationInfoList) {
        this.associationInfoList = associationInfoList;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getReturnRelationship() {
        return returnRelationship;
    }

    public void setReturnRelationship(String returnRelationship) {
        this.returnRelationship = returnRelationship;
    }

    public List<String> getSkuItemUpcCdList() {
        return skuItemUpcCdList;
    }

    public void setSkuItemUpcCdList(List<String> skuItemUpcCdList) {
        this.skuItemUpcCdList = skuItemUpcCdList;
    }

    public List<String> getReceiveQtyList() {
        return receiveQtyList;
    }

    public void setReceiveQtyList(List<String> receiveQtyList) {
        this.receiveQtyList = receiveQtyList;
    }

    public List<String> getItemDescList() {
        return itemDescList;
    }

    public void setItemDescList(List<String> itemDescList) {
        this.itemDescList = itemDescList;
    }

    public List<String> getPartNbrList() {
        return partNbrList;
    }

    public void setPartNbrList(List<String> partNbrList) {
        this.partNbrList = partNbrList;
    }

    public String getServiceCD() {
        return serviceCD;
    }

    public void setServiceCD(String serviceCD) {
        this.serviceCD = serviceCD;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getServiceShortDesc() {
        return serviceShortDesc;
    }

    public void setServiceShortDesc(String serviceShortDesc) {
        this.serviceShortDesc = serviceShortDesc;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getClearanceDetailLink() {
        return clearanceDetailLink;
    }

    public void setClearanceDetailLink(String clearanceDetailLink) {
        this.clearanceDetailLink = clearanceDetailLink;
    }

    public boolean isShowClearanceDetailLink() {
        return showClearanceDetailLink;
    }

    public void setShowClearanceDetailLink(boolean showClearanceDetailLink) {
        this.showClearanceDetailLink = showClearanceDetailLink;
    }

    public List<String> getManufactureCountryCDList() {
        return manufactureCountryCDList;
    }

    public void setManufactureCountryCDList(List<String> manufactureCountryCDList) {
        this.manufactureCountryCDList = manufactureCountryCDList;
    }

    public List<String> getCommodityCDList() {
        return commodityCDList;
    }

    public void setCommodityCDList(List<String> commodityCDList) {
        this.commodityCDList = commodityCDList;
    }

    public List<String> getCommodityDescList() {
        return commodityDescList;
    }

    public void setCommodityDescList(List<String> commodityDescList) {
        this.commodityDescList = commodityDescList;
    }

    public List<String> getCerNbrList() {
        return cerNbrList;
    }

    public void setCerNbrList(List<String> cerNbrList) {
        this.cerNbrList = cerNbrList;
    }

    public List<String> getCerComplaintCDList() {
        return cerComplaintCDList;
    }

    public void setCerComplaintCDList(List<String> cerComplaintCDList) {
        this.cerComplaintCDList = cerComplaintCDList;
    }

    public List<String> getCerComplaintDescList() {
        return cerComplaintDescList;
    }

    public void setCerComplaintDescList(List<String> cerComplaintDescList) {
        this.cerComplaintDescList = cerComplaintDescList;
    }

    public List<String> getCerEventDateList() {
        return cerEventDateList;
    }

    public void setCerEventDateList(List<String> cerEventDateList) {
        this.cerEventDateList = cerEventDateList;
    }

    public List<String> getDisplayCerEventDateList() {
        return displayCerEventDateList;
    }

    public void setDisplayCerEventDateList(List<String> displayCerEventDateList) {
        this.displayCerEventDateList = displayCerEventDateList;
    }

    public String getTotalPieces() {
        return totalPieces;
    }

    public void setTotalPieces(String totalPieces) {
        this.totalPieces = totalPieces;
    }

    public List<String> getSpecialHandlingServicesList() {
        return specialHandlingServicesList;
    }

    public void setSpecialHandlingServicesList(List<String> specialHandlingServicesList) {
        this.specialHandlingServicesList = specialHandlingServicesList;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getPkgContentDesc1() {
        return pkgContentDesc1;
    }

    public void setPkgContentDesc1(String pkgContentDesc1) {
        this.pkgContentDesc1 = pkgContentDesc1;
    }

    public String getPkgContentDesc2() {
        return pkgContentDesc2;
    }

    public void setPkgContentDesc2(String pkgContentDesc2) {
        this.pkgContentDesc2 = pkgContentDesc2;
    }

    public String getDocAWBNbr() {
        return docAWBNbr;
    }

    public void setDocAWBNbr(String docAWBNbr) {
        this.docAWBNbr = docAWBNbr;
    }

    public String getOriginalCharges() {
        return originalCharges;
    }

    public void setOriginalCharges(String originalCharges) {
        this.originalCharges = originalCharges;
    }

    public String getTransportationCD() {
        return transportationCD;
    }

    public void setTransportationCD(String transportationCD) {
        this.transportationCD = transportationCD;
    }

    public String getTransportationDesc() {
        return transportationDesc;
    }

    public void setTransportationDesc(String transportationDesc) {
        this.transportationDesc = transportationDesc;
    }

    public String getDutiesAndTaxesCD() {
        return dutiesAndTaxesCD;
    }

    public void setDutiesAndTaxesCD(String dutiesAndTaxesCD) {
        this.dutiesAndTaxesCD = dutiesAndTaxesCD;
    }

    public String getDutiesAndTaxesDesc() {
        return dutiesAndTaxesDesc;
    }

    public void setDutiesAndTaxesDesc(String dutiesAndTaxesDesc) {
        this.dutiesAndTaxesDesc = dutiesAndTaxesDesc;
    }

    public String getOrigPieceCount() {
        return origPieceCount;
    }

    public void setOrigPieceCount(String origPieceCount) {
        this.origPieceCount = origPieceCount;
    }

    public String getDestPieceCount() {
        return destPieceCount;
    }

    public void setDestPieceCount(String destPieceCount) {
        this.destPieceCount = destPieceCount;
    }

    public String getGoodsClassificationCD() {
        return goodsClassificationCD;
    }

    public void setGoodsClassificationCD(String goodsClassificationCD) {
        this.goodsClassificationCD = goodsClassificationCD;
    }

    public String getReceipientAddrQty() {
        return receipientAddrQty;
    }

    public void setReceipientAddrQty(String receipientAddrQty) {
        this.receipientAddrQty = receipientAddrQty;
    }

    public String getDeliveryAttempt() {
        return deliveryAttempt;
    }

    public void setDeliveryAttempt(String deliveryAttempt) {
        this.deliveryAttempt = deliveryAttempt;
    }

    public String getCodReturnTrackNbr() {
        return codReturnTrackNbr;
    }

    public void setCodReturnTrackNbr(String codReturnTrackNbr) {
        this.codReturnTrackNbr = codReturnTrackNbr;
    }

    public List<ScanEvent> getScanEventList() {
        return scanEventList;
    }

    public void setScanEventList(List<ScanEvent> scanEventList) {
        this.scanEventList = scanEventList;
    }

    public String getOriginAddr1() {
        return originAddr1;
    }

    public void setOriginAddr1(String originAddr1) {
        this.originAddr1 = originAddr1;
    }

    public String getOriginAddr2() {
        return originAddr2;
    }

    public void setOriginAddr2(String originAddr2) {
        this.originAddr2 = originAddr2;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getOriginStateCD() {
        return originStateCD;
    }

    public void setOriginStateCD(String originStateCD) {
        this.originStateCD = originStateCD;
    }

    public String getOriginZip() {
        return originZip;
    }

    public void setOriginZip(String originZip) {
        this.originZip = originZip;
    }

    public String getOriginCntryCD() {
        return originCntryCD;
    }

    public void setOriginCntryCD(String originCntryCD) {
        this.originCntryCD = originCntryCD;
    }

    public String getOriginLocationID() {
        return originLocationID;
    }

    public void setOriginLocationID(String originLocationID) {
        this.originLocationID = originLocationID;
    }

    public String getOriginTermCity() {
        return originTermCity;
    }

    public void setOriginTermCity(String originTermCity) {
        this.originTermCity = originTermCity;
    }

    public String getOriginTermStateCD() {
        return originTermStateCD;
    }

    public void setOriginTermStateCD(String originTermStateCD) {
        this.originTermStateCD = originTermStateCD;
    }

    public String getDestLocationAddr1() {
        return destLocationAddr1;
    }

    public void setDestLocationAddr1(String destLocationAddr1) {
        this.destLocationAddr1 = destLocationAddr1;
    }

    public String getDestLocationAddr2() {
        return destLocationAddr2;
    }

    public void setDestLocationAddr2(String destLocationAddr2) {
        this.destLocationAddr2 = destLocationAddr2;
    }

    public String getDestLocationCity() {
        return destLocationCity;
    }

    public void setDestLocationCity(String destLocationCity) {
        this.destLocationCity = destLocationCity;
    }

    public String getDestLocationStateCD() {
        return destLocationStateCD;
    }

    public void setDestLocationStateCD(String destLocationStateCD) {
        this.destLocationStateCD = destLocationStateCD;
    }

    public String getDestLocationZip() {
        return destLocationZip;
    }

    public void setDestLocationZip(String destLocationZip) {
        this.destLocationZip = destLocationZip;
    }

    public String getDestLocationCntryCD() {
        return destLocationCntryCD;
    }

    public void setDestLocationCntryCD(String destLocationCntryCD) {
        this.destLocationCntryCD = destLocationCntryCD;
    }

    public String getDestLocationID() {
        return destLocationID;
    }

    public void setDestLocationID(String destLocationID) {
        this.destLocationID = destLocationID;
    }

    public String getDestLocationTermCity() {
        return destLocationTermCity;
    }

    public void setDestLocationTermCity(String destLocationTermCity) {
        this.destLocationTermCity = destLocationTermCity;
    }

    public String getDestLocationTermStateCD() {
        return destLocationTermStateCD;
    }

    public void setDestLocationTermStateCD(String destLocationTermStateCD) {
        this.destLocationTermStateCD = destLocationTermStateCD;
    }

    public String getDestAddr1() {
        return destAddr1;
    }

    public void setDestAddr1(String destAddr1) {
        this.destAddr1 = destAddr1;
    }

    public String getDestAddr2() {
        return destAddr2;
    }

    public void setDestAddr2(String destAddr2) {
        this.destAddr2 = destAddr2;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public String getDestStateCD() {
        return destStateCD;
    }

    public void setDestStateCD(String destStateCD) {
        this.destStateCD = destStateCD;
    }

    public String getDestZip() {
        return destZip;
    }

    public void setDestZip(String destZip) {
        this.destZip = destZip;
    }

    public String getDestCntryCD() {
        return destCntryCD;
    }

    public void setDestCntryCD(String destCntryCD) {
        this.destCntryCD = destCntryCD;
    }

    public String getHalAddr1() {
        return halAddr1;
    }

    public void setHalAddr1(String halAddr1) {
        this.halAddr1 = halAddr1;
    }

    public String getHalAddr2() {
        return halAddr2;
    }

    public void setHalAddr2(String halAddr2) {
        this.halAddr2 = halAddr2;
    }

    public String getHalCity() {
        return halCity;
    }

    public void setHalCity(String halCity) {
        this.halCity = halCity;
    }

    public String getHalStateCD() {
        return halStateCD;
    }

    public void setHalStateCD(String halStateCD) {
        this.halStateCD = halStateCD;
    }

    public String getHalZipCD() {
        return halZipCD;
    }

    public void setHalZipCD(String halZipCD) {
        this.halZipCD = halZipCD;
    }

    public String getHalCntryCD() {
        return halCntryCD;
    }

    public void setHalCntryCD(String halCntryCD) {
        this.halCntryCD = halCntryCD;
    }

    public String getActualDelAddrCity() {
        return actualDelAddrCity;
    }

    public void setActualDelAddrCity(String actualDelAddrCity) {
        this.actualDelAddrCity = actualDelAddrCity;
    }

    public String getActualDelAddrStateCD() {
        return actualDelAddrStateCD;
    }

    public void setActualDelAddrStateCD(String actualDelAddrStateCD) {
        this.actualDelAddrStateCD = actualDelAddrStateCD;
    }

    public String getActualDelAddrZipCD() {
        return actualDelAddrZipCD;
    }

    public void setActualDelAddrZipCD(String actualDelAddrZipCD) {
        this.actualDelAddrZipCD = actualDelAddrZipCD;
    }

    public String getActualDelAddrCntryCD() {
        return actualDelAddrCntryCD;
    }

    public void setActualDelAddrCntryCD(String actualDelAddrCntryCD) {
        this.actualDelAddrCntryCD = actualDelAddrCntryCD;
    }

    public String getTotalTransitMiles() {
        return totalTransitMiles;
    }

    public void setTotalTransitMiles(String totalTransitMiles) {
        this.totalTransitMiles = totalTransitMiles;
    }

    public List<String> getExcepReasonList() {
        return excepReasonList;
    }

    public void setExcepReasonList(List<String> excepReasonList) {
        this.excepReasonList = excepReasonList;
    }

    public List<String> getExcepActionList() {
        return excepActionList;
    }

    public void setExcepActionList(List<String> excepActionList) {
        this.excepActionList = excepActionList;
    }

    public String getExceptionReason() {
        return exceptionReason;
    }

    public void setExceptionReason(String exceptionReason) {
        this.exceptionReason = exceptionReason;
    }

    public String getExceptionAction() {
        return exceptionAction;
    }

    public void setExceptionAction(String exceptionAction) {
        this.exceptionAction = exceptionAction;
    }

    public List<String> getStatusDetailsList() {
        return statusDetailsList;
    }

    public void setStatusDetailsList(List<String> statusDetailsList) {
        this.statusDetailsList = statusDetailsList;
    }

    public String getTrackErrCD() {
        return trackErrCD;
    }

    public void setTrackErrCD(String trackErrCD) {
        this.trackErrCD = trackErrCD;
    }

    public String getDestTZ() {
        return destTZ;
    }

    public void setDestTZ(String destTZ) {
        this.destTZ = destTZ;
    }

    public String getOriginTZ() {
        return originTZ;
    }

    public void setOriginTZ(String originTZ) {
        this.originTZ = originTZ;
    }

    public String getMultiStat() {
        return isMultiStat;
    }

    public void setMultiStat(String multiStat) {
        isMultiStat = multiStat;
    }

    public List<MultiStat> getMultiStatList() {
        return multiStatList;
    }

    public void setMultiStatList(List<MultiStat> multiStatList) {
        this.multiStatList = multiStatList;
    }

    public String getMaskMessage() {
        return maskMessage;
    }

    public void setMaskMessage(String maskMessage) {
        this.maskMessage = maskMessage;
    }

    public String getDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(String deliveryService) {
        this.deliveryService = deliveryService;
    }

    public String getMilestoDestination() {
        return milestoDestination;
    }

    public void setMilestoDestination(String milestoDestination) {
        this.milestoDestination = milestoDestination;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getOriginUbanizationCode() {
        return originUbanizationCode;
    }

    public void setOriginUbanizationCode(String originUbanizationCode) {
        this.originUbanizationCode = originUbanizationCode;
    }

    public String getOriginCountryName() {
        return originCountryName;
    }

    public void setOriginCountryName(String originCountryName) {
        this.originCountryName = originCountryName;
    }

    public boolean isOriginResidential() {
        return isOriginResidential;
    }

    public void setOriginResidential(boolean originResidential) {
        isOriginResidential = originResidential;
    }

    public String getHalUrbanizationCD() {
        return halUrbanizationCD;
    }

    public void setHalUrbanizationCD(String halUrbanizationCD) {
        this.halUrbanizationCD = halUrbanizationCD;
    }

    public String getHalCountryName() {
        return halCountryName;
    }

    public void setHalCountryName(String halCountryName) {
        this.halCountryName = halCountryName;
    }

    public String getActualDelAddrUrbanizationCD() {
        return actualDelAddrUrbanizationCD;
    }

    public void setActualDelAddrUrbanizationCD(String actualDelAddrUrbanizationCD) {
        this.actualDelAddrUrbanizationCD = actualDelAddrUrbanizationCD;
    }

    public String getActualDelAddrCountryName() {
        return actualDelAddrCountryName;
    }

    public void setActualDelAddrCountryName(String actualDelAddrCountryName) {
        this.actualDelAddrCountryName = actualDelAddrCountryName;
    }

    public String getDestUrbanizationCD() {
        return destUrbanizationCD;
    }

    public void setDestUrbanizationCD(String destUrbanizationCD) {
        this.destUrbanizationCD = destUrbanizationCD;
    }

    public String getDestCountryName() {
        return destCountryName;
    }

    public void setDestCountryName(String destCountryName) {
        this.destCountryName = destCountryName;
    }

    public String getDelToDesc() {
        return delToDesc;
    }

    public void setDelToDesc(String delToDesc) {
        this.delToDesc = delToDesc;
    }

    public String getRecpShareID() {
        return recpShareID;
    }

    public void setRecpShareID(String recpShareID) {
        this.recpShareID = recpShareID;
    }

    public String getShprShareID() {
        return shprShareID;
    }

    public void setShprShareID(String shprShareID) {
        this.shprShareID = shprShareID;
    }

    public String getDefaultCDOType() {
        return defaultCDOType;
    }

    public void setDefaultCDOType(String defaultCDOType) {
        this.defaultCDOType = defaultCDOType;
    }

    public String getMpstype() {
        return mpstype;
    }

    public void setMpstype(String mpstype) {
        this.mpstype = mpstype;
    }

    public boolean isFxfAdvanceNotice() {
        return fxfAdvanceNotice;
    }

    public void setFxfAdvanceNotice(boolean fxfAdvanceNotice) {
        this.fxfAdvanceNotice = fxfAdvanceNotice;
    }

    public String getRthavailableCD() {
        return rthavailableCD;
    }

    public void setRthavailableCD(String rthavailableCD) {
        this.rthavailableCD = rthavailableCD;
    }

    public List<String> getExcepReasonListNoInit() {
        return excepReasonListNoInit;
    }

    public void setExcepReasonListNoInit(List<String> excepReasonListNoInit) {
        this.excepReasonListNoInit = excepReasonListNoInit;
    }

    public List<String> getExcepActionListNoInit() {
        return excepActionListNoInit;
    }

    public void setExcepActionListNoInit(List<String> excepActionListNoInit) {
        this.excepActionListNoInit = excepActionListNoInit;
    }

    public List<String> getStatusDetailsListNoInit() {
        return statusDetailsListNoInit;
    }

    public void setStatusDetailsListNoInit(List<String> statusDetailsListNoInit) {
        this.statusDetailsListNoInit = statusDetailsListNoInit;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public List<ErrorListItem> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ErrorListItem> errorList) {
        this.errorList = errorList;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public boolean isPrePickup() {
        return isPrePickup;
    }

    public void setPrePickup(boolean prePickup) {
        isPrePickup = prePickup;
    }

    public boolean isPickup() {
        return isPickup;
    }

    public void setPickup(boolean pickup) {
        isPickup = pickup;
    }

    public boolean isInTransit() {
        return isInTransit;
    }

    public void setInTransit(boolean inTransit) {
        isInTransit = inTransit;
    }

    public boolean isInProgress() {
        return isInProgress;
    }

    public void setInProgress(boolean inProgress) {
        isInProgress = inProgress;
    }

    public boolean isDelException() {
        return isDelException;
    }

    public void setDelException(boolean delException) {
        isDelException = delException;
    }

    public boolean isClearanceDelay() {
        return isClearanceDelay;
    }

    public void setClearanceDelay(boolean clearanceDelay) {
        isClearanceDelay = clearanceDelay;
    }

    public boolean isException() {
        return isException;
    }

    public void setException(boolean exception) {
        isException = exception;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public boolean isHAL() {
        return isHAL;
    }

    public void setHAL(boolean HAL) {
        isHAL = HAL;
    }

    public boolean isOnSchedule() {
        return isOnSchedule;
    }

    public void setOnSchedule(boolean onSchedule) {
        isOnSchedule = onSchedule;
    }

    public boolean isDeliveryToday() {
        return isDeliveryToday;
    }

    public void setDeliveryToday(boolean deliveryToday) {
        isDeliveryToday = deliveryToday;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    public boolean isWatch() {
        return isWatch;
    }

    public void setWatch(boolean watch) {
        isWatch = watch;
    }

    public boolean isHistorical() {
        return isHistorical;
    }

    public void setHistorical(boolean historical) {
        isHistorical = historical;
    }

    public boolean isTenderedNotification() {
        return isTenderedNotification;
    }

    public void setTenderedNotification(boolean tenderedNotification) {
        isTenderedNotification = tenderedNotification;
    }

    public boolean isDeliveredNotification() {
        return isDeliveredNotification;
    }

    public void setDeliveredNotification(boolean deliveredNotification) {
        isDeliveredNotification = deliveredNotification;
    }

    public boolean isExceptionNotification() {
        return isExceptionNotification;
    }

    public void setExceptionNotification(boolean exceptionNotification) {
        isExceptionNotification = exceptionNotification;
    }

    public boolean isCurrentStatusNotification() {
        return isCurrentStatusNotification;
    }

    public void setCurrentStatusNotification(boolean currentStatusNotification) {
        isCurrentStatusNotification = currentStatusNotification;
    }

    public boolean isAnticipatedShipDtLabel() {
        return isAnticipatedShipDtLabel;
    }

    public void setAnticipatedShipDtLabel(boolean anticipatedShipDtLabel) {
        isAnticipatedShipDtLabel = anticipatedShipDtLabel;
    }

    public boolean isShipPickupDtLabel() {
        return isShipPickupDtLabel;
    }

    public void setShipPickupDtLabel(boolean shipPickupDtLabel) {
        isShipPickupDtLabel = shipPickupDtLabel;
    }

    public boolean isActualPickupLabel() {
        return isActualPickupLabel;
    }

    public void setActualPickupLabel(boolean actualPickupLabel) {
        isActualPickupLabel = actualPickupLabel;
    }

    public boolean isOrderReceivedLabel() {
        return isOrderReceivedLabel;
    }

    public void setOrderReceivedLabel(boolean orderReceivedLabel) {
        isOrderReceivedLabel = orderReceivedLabel;
    }

    public boolean isEstimatedDeliveryDtLabel() {
        return isEstimatedDeliveryDtLabel;
    }

    public void setEstimatedDeliveryDtLabel(boolean estimatedDeliveryDtLabel) {
        isEstimatedDeliveryDtLabel = estimatedDeliveryDtLabel;
    }

    public boolean isDeliveryDtLabel() {
        return isDeliveryDtLabel;
    }

    public void setDeliveryDtLabel(boolean deliveryDtLabel) {
        isDeliveryDtLabel = deliveryDtLabel;
    }

    public boolean isActualDeliveryDtLabel() {
        return isActualDeliveryDtLabel;
    }

    public void setActualDeliveryDtLabel(boolean actualDeliveryDtLabel) {
        isActualDeliveryDtLabel = actualDeliveryDtLabel;
    }

    public boolean isOrderCompleteLabel() {
        return isOrderCompleteLabel;
    }

    public void setOrderCompleteLabel(boolean orderCompleteLabel) {
        isOrderCompleteLabel = orderCompleteLabel;
    }

    public boolean isOutboundDirection() {
        return isOutboundDirection;
    }

    public void setOutboundDirection(boolean outboundDirection) {
        isOutboundDirection = outboundDirection;
    }

    public boolean isInboundDirection() {
        return isInboundDirection;
    }

    public void setInboundDirection(boolean inboundDirection) {
        isInboundDirection = inboundDirection;
    }

    public boolean isThirdpartyDirection() {
        return isThirdpartyDirection;
    }

    public void setThirdpartyDirection(boolean thirdpartyDirection) {
        isThirdpartyDirection = thirdpartyDirection;
    }

    public boolean isUnknownDirection() {
        return isUnknownDirection;
    }

    public void setUnknownDirection(boolean unknownDirection) {
        isUnknownDirection = unknownDirection;
    }

    public boolean isFSM() {
        return isFSM;
    }

    public void setFSM(boolean FSM) {
        isFSM = FSM;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    public boolean isOriginalOutBound() {
        return isOriginalOutBound;
    }

    public void setOriginalOutBound(boolean originalOutBound) {
        isOriginalOutBound = originalOutBound;
    }

    public boolean isChildPackage() {
        return isChildPackage;
    }

    public void setChildPackage(boolean childPackage) {
        isChildPackage = childPackage;
    }

    public boolean isParentPackage() {
        return isParentPackage;
    }

    public void setParentPackage(boolean parentPackage) {
        isParentPackage = parentPackage;
    }

    public boolean isReclassifiedAsSingleShipment() {
        return isReclassifiedAsSingleShipment;
    }

    public void setReclassifiedAsSingleShipment(boolean reclassifiedAsSingleShipment) {
        isReclassifiedAsSingleShipment = reclassifiedAsSingleShipment;
    }

    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }

    public boolean isMaskShipper() {
        return isMaskShipper;
    }

    public void setMaskShipper(boolean maskShipper) {
        isMaskShipper = maskShipper;
    }

    public boolean isHalEligible() {
        return isHalEligible;
    }

    public void setHalEligible(boolean halEligible) {
        isHalEligible = halEligible;
    }

    public boolean isFedexOfficeOnlineOrders() {
        return isFedexOfficeOnlineOrders;
    }

    public void setFedexOfficeOnlineOrders(boolean fedexOfficeOnlineOrders) {
        isFedexOfficeOnlineOrders = fedexOfficeOnlineOrders;
    }

    public boolean isFedexOfficeInStoreOrders() {
        return isFedexOfficeInStoreOrders;
    }

    public void setFedexOfficeInStoreOrders(boolean fedexOfficeInStoreOrders) {
        isFedexOfficeInStoreOrders = fedexOfficeInStoreOrders;
    }

    public boolean isMultipleStop() {
        return isMultipleStop;
    }

    public void setMultipleStop(boolean multipleStop) {
        isMultipleStop = multipleStop;
    }

    public boolean isCustomCritical() {
        return isCustomCritical;
    }

    public void setCustomCritical(boolean customCritical) {
        isCustomCritical = customCritical;
    }

    public boolean isInvalid() {
        return isInvalid;
    }

    public void setInvalid(boolean invalid) {
        isInvalid = invalid;
    }

    public boolean isNotFound() {
        return isNotFound;
    }

    public void setNotFound(boolean notFound) {
        isNotFound = notFound;
    }

    public boolean isFreight() {
        return isFreight;
    }

    public void setFreight(boolean freight) {
        isFreight = freight;
    }

    public boolean isSpod() {
        return isSpod;
    }

    public void setSpod(boolean spod) {
        isSpod = spod;
    }

    public boolean isSignatureAvailable() {
        return isSignatureAvailable;
    }

    public void setSignatureAvailable(boolean signatureAvailable) {
        isSignatureAvailable = signatureAvailable;
    }

    public boolean isMPS() {
        return isMPS;
    }

    public void setMPS(boolean MPS) {
        isMPS = MPS;
    }

    public boolean isGMPS() {
        return isGMPS;
    }

    public void setGMPS(boolean GMPS) {
        isGMPS = GMPS;
    }

    public boolean isResidential() {
        return isResidential;
    }

    public void setResidential(boolean residential) {
        isResidential = residential;
    }

    public boolean isDestResidential() {
        return isDestResidential;
    }

    public void setDestResidential(boolean destResidential) {
        isDestResidential = destResidential;
    }

    public boolean isHALResidential() {
        return isHALResidential;
    }

    public void setHALResidential(boolean HALResidential) {
        isHALResidential = HALResidential;
    }

    public boolean isActualDelAddrResidential() {
        return isActualDelAddrResidential;
    }

    public void setActualDelAddrResidential(boolean actualDelAddrResidential) {
        isActualDelAddrResidential = actualDelAddrResidential;
    }

    public boolean isReqEstDelDt() {
        return isReqEstDelDt;
    }

    public void setReqEstDelDt(boolean reqEstDelDt) {
        isReqEstDelDt = reqEstDelDt;
    }

    public boolean isCDOEligible() {
        return isCDOEligible;
    }

    public void setCDOEligible(boolean CDOEligible) {
        isCDOEligible = CDOEligible;
    }

    public List<CDOInfo> getCDOInfoList() {
        return CDOInfoList;
    }

    public void setCDOInfoList(List<CDOInfo> CDOInfoList) {
        this.CDOInfoList = CDOInfoList;
    }

    public boolean isCDOExists() {
        return CDOExists;
    }

    public void setCDOExists(boolean CDOExists) {
        this.CDOExists = CDOExists;
    }

    public boolean isMtchdByRecShrID() {
        return isMtchdByRecShrID;
    }

    public void setMtchdByRecShrID(boolean mtchdByRecShrID) {
        isMtchdByRecShrID = mtchdByRecShrID;
    }

    public boolean isMtchdByShiprShrID() {
        return isMtchdByShiprShrID;
    }

    public void setMtchdByShiprShrID(boolean mtchdByShiprShrID) {
        isMtchdByShiprShrID = mtchdByShiprShrID;
    }

    @Override
    public String toString() {
        return "PackageListItem{" +
                "trackingNbr='" + trackingNbr + '\'' +
                ", trackingQualifier='" + trackingQualifier + '\'' +
                ", trackingCarrierCd='" + trackingCarrierCd + '\'' +
                ", trackingCarrierDesc='" + trackingCarrierDesc + '\'' +
                ", displayTrackingNbr='" + displayTrackingNbr + '\'' +
                ", shipperCmpnyName='" + shipperCmpnyName + '\'' +
                ", shipperName='" + shipperName + '\'' +
                ", shipperAddr1='" + shipperAddr1 + '\'' +
                ", shipperAddr2='" + shipperAddr2 + '\'' +
                ", shipperCity='" + shipperCity + '\'' +
                ", shipperStateCD='" + shipperStateCD + '\'' +
                ", shipperZip='" + shipperZip + '\'' +
                ", shipperCntryCD='" + shipperCntryCD + '\'' +
                ", shipperPhoneNbr='" + shipperPhoneNbr + '\'' +
                ", shippedBy='" + shippedBy + '\'' +
                ", recipientCmpnyName='" + recipientCmpnyName + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", recipientAddr1='" + recipientAddr1 + '\'' +
                ", recipientAddr2='" + recipientAddr2 + '\'' +
                ", recipientCity='" + recipientCity + '\'' +
                ", recipientStateCD='" + recipientStateCD + '\'' +
                ", recipientZip='" + recipientZip + '\'' +
                ", recipientCntryCD='" + recipientCntryCD + '\'' +
                ", recipientPhoneNbr='" + recipientPhoneNbr + '\'' +
                ", shippedTo='" + shippedTo + '\'' +
                ", keyStatus='" + keyStatus + '\'' +
                ", keyStatusCD='" + keyStatusCD + '\'' +
                ", lastScanStatus='" + lastScanStatus + '\'' +
                ", lastScanDateTime='" + lastScanDateTime + '\'' +
                ", receivedByNm='" + receivedByNm + '\'' +
                ", subStatus='" + subStatus + '\'' +
                ", mainStatus='" + mainStatus + '\'' +
                ", statusBarCD='" + statusBarCD + '\'' +
                ", shortStatus='" + shortStatus + '\'' +
                ", shortStatusCD='" + shortStatusCD + '\'' +
                ", statusLocationAddr1='" + statusLocationAddr1 + '\'' +
                ", statusLocationAddr2='" + statusLocationAddr2 + '\'' +
                ", statusLocationCity='" + statusLocationCity + '\'' +
                ", statusLocationStateCD='" + statusLocationStateCD + '\'' +
                ", statusLocationZip='" + statusLocationZip + '\'' +
                ", statusLocationCntryCD='" + statusLocationCntryCD + '\'' +
                ", statusWithDetails='" + statusWithDetails + '\'' +
                ", shipDt='" + shipDt + '\'' +
                ", displayShipDt='" + displayShipDt + '\'' +
                ", displayShipTm='" + displayShipTm + '\'' +
                ", displayShipDateTime='" + displayShipDateTime + '\'' +
                ", pickupDt='" + pickupDt + '\'' +
                ", displayPickupDt='" + displayPickupDt + '\'' +
                ", displayPickupTm='" + displayPickupTm + '\'' +
                ", displayPickupDateTime='" + displayPickupDateTime + '\'' +
                ", estDeliveryDt='" + estDeliveryDt + '\'' +
                ", estDeliveryTm='" + estDeliveryTm + '\'' +
                ", displayEstDeliveryDt='" + displayEstDeliveryDt + '\'' +
                ", displayEstDeliveryTm='" + displayEstDeliveryTm + '\'' +
                ", displayEstDeliveryDateTime='" + displayEstDeliveryDateTime + '\'' +
                ", actDeliveryDt='" + actDeliveryDt + '\'' +
                ", displayActDeliveryDt='" + displayActDeliveryDt + '\'' +
                ", displayActDeliveryTm='" + displayActDeliveryTm + '\'' +
                ", displayActDeliveryDateTime='" + displayActDeliveryDateTime + '\'' +
                ", nickName='" + nickName + '\'' +
                ", note='" + note + '\'' +
                ", matchedAccountList=" + matchedAccountList +
                ", fxfAdvanceETA='" + fxfAdvanceETA + '\'' +
                ", fxfAdvanceReason='" + fxfAdvanceReason + '\'' +
                ", fxfAdvanceStatusCode='" + fxfAdvanceStatusCode + '\'' +
                ", fxfAdvanceStatusDesc='" + fxfAdvanceStatusDesc + '\'' +
                ", destLink='" + destLink + '\'' +
                ", originLink='" + originLink + '\'' +
                ", hasBillOfLadingImage=" + hasBillOfLadingImage +
                ", hasBillPresentment=" + hasBillPresentment +
                ", signatureRequired=" + signatureRequired +
                ", totalKgsWgt='" + totalKgsWgt + '\'' +
                ", displayTotalKgsWgt='" + displayTotalKgsWgt + '\'' +
                ", totalLbsWgt='" + totalLbsWgt + '\'' +
                ", displayTotalLbsWgt='" + displayTotalLbsWgt + '\'' +
                ", displayTotalWgt='" + displayTotalWgt + '\'' +
                ", pkgKgsWgt='" + pkgKgsWgt + '\'' +
                ", displayPkgKgsWgt='" + displayPkgKgsWgt + '\'' +
                ", pkgLbsWgt='" + pkgLbsWgt + '\'' +
                ", displayPkgLbsWgt='" + displayPkgLbsWgt + '\'' +
                ", displayPkgWgt='" + displayPkgWgt + '\'' +
                ", dimensions='" + dimensions + '\'' +
                ", masterTrackingNbr='" + masterTrackingNbr + '\'' +
                ", masterQualifier='" + masterQualifier + '\'' +
                ", masterCarrierCD='" + masterCarrierCD + '\'' +
                ", originalOutboundTrackingNbr='" + originalOutboundTrackingNbr + '\'' +
                ", originalOutboundQualifier='" + originalOutboundQualifier + '\'' +
                ", originalOutboundCarrierCD='" + originalOutboundCarrierCD + '\'' +
                ", invoiceNbrList=" + invoiceNbrList +
                ", referenceList=" + referenceList +
                ", doorTagNbrList=" + doorTagNbrList +
                ", referenceDescList=" + referenceDescList +
                ", purchaseOrderNbrList=" + purchaseOrderNbrList +
                ", billofLadingNbrList=" + billofLadingNbrList +
                ", shipperRefList=" + shipperRefList +
                ", rmaList=" + rmaList +
                ", deptNbrList=" + deptNbrList +
                ", shipmentIdList=" + shipmentIdList +
                ", tcnList=" + tcnList +
                ", partnerCarrierNbrList=" + partnerCarrierNbrList +
                ", hasAssociatedShipments=" + hasAssociatedShipments +
                ", hasAssociatedReturnShipments=" + hasAssociatedReturnShipments +
                ", assocShpGrp=" + assocShpGrp +
                ", drTgGrp=" + drTgGrp +
                ", associationInfoList=" + associationInfoList +
                ", returnReason='" + returnReason + '\'' +
                ", returnRelationship='" + returnRelationship + '\'' +
                ", skuItemUpcCdList=" + skuItemUpcCdList +
                ", receiveQtyList=" + receiveQtyList +
                ", itemDescList=" + itemDescList +
                ", partNbrList=" + partNbrList +
                ", serviceCD='" + serviceCD + '\'' +
                ", serviceDesc='" + serviceDesc + '\'' +
                ", serviceShortDesc='" + serviceShortDesc + '\'' +
                ", packageType='" + packageType + '\'' +
                ", packaging='" + packaging + '\'' +
                ", clearanceDetailLink='" + clearanceDetailLink + '\'' +
                ", showClearanceDetailLink=" + showClearanceDetailLink +
                ", manufactureCountryCDList=" + manufactureCountryCDList +
                ", commodityCDList=" + commodityCDList +
                ", commodityDescList=" + commodityDescList +
                ", cerNbrList=" + cerNbrList +
                ", cerComplaintCDList=" + cerComplaintCDList +
                ", cerComplaintDescList=" + cerComplaintDescList +
                ", cerEventDateList=" + cerEventDateList +
                ", displayCerEventDateList=" + displayCerEventDateList +
                ", totalPieces='" + totalPieces + '\'' +
                ", specialHandlingServicesList=" + specialHandlingServicesList +
                ", shipmentType='" + shipmentType + '\'' +
                ", pkgContentDesc1='" + pkgContentDesc1 + '\'' +
                ", pkgContentDesc2='" + pkgContentDesc2 + '\'' +
                ", docAWBNbr='" + docAWBNbr + '\'' +
                ", originalCharges='" + originalCharges + '\'' +
                ", transportationCD='" + transportationCD + '\'' +
                ", transportationDesc='" + transportationDesc + '\'' +
                ", dutiesAndTaxesCD='" + dutiesAndTaxesCD + '\'' +
                ", dutiesAndTaxesDesc='" + dutiesAndTaxesDesc + '\'' +
                ", origPieceCount='" + origPieceCount + '\'' +
                ", destPieceCount='" + destPieceCount + '\'' +
                ", goodsClassificationCD='" + goodsClassificationCD + '\'' +
                ", receipientAddrQty='" + receipientAddrQty + '\'' +
                ", deliveryAttempt='" + deliveryAttempt + '\'' +
                ", codReturnTrackNbr='" + codReturnTrackNbr + '\'' +
                ", scanEventList=" + scanEventList +
                ", originAddr1='" + originAddr1 + '\'' +
                ", originAddr2='" + originAddr2 + '\'' +
                ", originCity='" + originCity + '\'' +
                ", originStateCD='" + originStateCD + '\'' +
                ", originZip='" + originZip + '\'' +
                ", originCntryCD='" + originCntryCD + '\'' +
                ", originLocationID='" + originLocationID + '\'' +
                ", originTermCity='" + originTermCity + '\'' +
                ", originTermStateCD='" + originTermStateCD + '\'' +
                ", destLocationAddr1='" + destLocationAddr1 + '\'' +
                ", destLocationAddr2='" + destLocationAddr2 + '\'' +
                ", destLocationCity='" + destLocationCity + '\'' +
                ", destLocationStateCD='" + destLocationStateCD + '\'' +
                ", destLocationZip='" + destLocationZip + '\'' +
                ", destLocationCntryCD='" + destLocationCntryCD + '\'' +
                ", destLocationID='" + destLocationID + '\'' +
                ", destLocationTermCity='" + destLocationTermCity + '\'' +
                ", destLocationTermStateCD='" + destLocationTermStateCD + '\'' +
                ", destAddr1='" + destAddr1 + '\'' +
                ", destAddr2='" + destAddr2 + '\'' +
                ", destCity='" + destCity + '\'' +
                ", destStateCD='" + destStateCD + '\'' +
                ", destZip='" + destZip + '\'' +
                ", destCntryCD='" + destCntryCD + '\'' +
                ", halAddr1='" + halAddr1 + '\'' +
                ", halAddr2='" + halAddr2 + '\'' +
                ", halCity='" + halCity + '\'' +
                ", halStateCD='" + halStateCD + '\'' +
                ", halZipCD='" + halZipCD + '\'' +
                ", halCntryCD='" + halCntryCD + '\'' +
                ", actualDelAddrCity='" + actualDelAddrCity + '\'' +
                ", actualDelAddrStateCD='" + actualDelAddrStateCD + '\'' +
                ", actualDelAddrZipCD='" + actualDelAddrZipCD + '\'' +
                ", actualDelAddrCntryCD='" + actualDelAddrCntryCD + '\'' +
                ", totalTransitMiles='" + totalTransitMiles + '\'' +
                ", excepReasonList=" + excepReasonList +
                ", excepActionList=" + excepActionList +
                ", exceptionReason='" + exceptionReason + '\'' +
                ", exceptionAction='" + exceptionAction + '\'' +
                ", statusDetailsList=" + statusDetailsList +
                ", trackErrCD='" + trackErrCD + '\'' +
                ", destTZ='" + destTZ + '\'' +
                ", originTZ='" + originTZ + '\'' +
                ", isMultiStat='" + isMultiStat + '\'' +
                ", multiStatList=" + multiStatList +
                ", maskMessage='" + maskMessage + '\'' +
                ", deliveryService='" + deliveryService + '\'' +
                ", milestoDestination='" + milestoDestination + '\'' +
                ", terms='" + terms + '\'' +
                ", originUbanizationCode='" + originUbanizationCode + '\'' +
                ", originCountryName='" + originCountryName + '\'' +
                ", isOriginResidential=" + isOriginResidential +
                ", halUrbanizationCD='" + halUrbanizationCD + '\'' +
                ", halCountryName='" + halCountryName + '\'' +
                ", actualDelAddrUrbanizationCD='" + actualDelAddrUrbanizationCD + '\'' +
                ", actualDelAddrCountryName='" + actualDelAddrCountryName + '\'' +
                ", destUrbanizationCD='" + destUrbanizationCD + '\'' +
                ", destCountryName='" + destCountryName + '\'' +
                ", delToDesc='" + delToDesc + '\'' +
                ", recpShareID='" + recpShareID + '\'' +
                ", shprShareID='" + shprShareID + '\'' +
                ", defaultCDOType='" + defaultCDOType + '\'' +
                ", mpstype='" + mpstype + '\'' +
                ", fxfAdvanceNotice=" + fxfAdvanceNotice +
                ", rthavailableCD='" + rthavailableCD + '\'' +
                ", excepReasonListNoInit=" + excepReasonListNoInit +
                ", excepActionListNoInit=" + excepActionListNoInit +
                ", statusDetailsListNoInit=" + statusDetailsListNoInit +
                ", matched=" + matched +
                ", isSuccessful=" + isSuccessful +
                ", errorList=" + errorList +
                ", isCanceled=" + isCanceled +
                ", isPrePickup=" + isPrePickup +
                ", isPickup=" + isPickup +
                ", isInTransit=" + isInTransit +
                ", isInProgress=" + isInProgress +
                ", isDelException=" + isDelException +
                ", isClearanceDelay=" + isClearanceDelay +
                ", isException=" + isException +
                ", isDelivered=" + isDelivered +
                ", isHAL=" + isHAL +
                ", isOnSchedule=" + isOnSchedule +
                ", isDeliveryToday=" + isDeliveryToday +
                ", isSave=" + isSave +
                ", isWatch=" + isWatch +
                ", isHistorical=" + isHistorical +
                ", isTenderedNotification=" + isTenderedNotification +
                ", isDeliveredNotification=" + isDeliveredNotification +
                ", isExceptionNotification=" + isExceptionNotification +
                ", isCurrentStatusNotification=" + isCurrentStatusNotification +
                ", isAnticipatedShipDtLabel=" + isAnticipatedShipDtLabel +
                ", isShipPickupDtLabel=" + isShipPickupDtLabel +
                ", isActualPickupLabel=" + isActualPickupLabel +
                ", isOrderReceivedLabel=" + isOrderReceivedLabel +
                ", isEstimatedDeliveryDtLabel=" + isEstimatedDeliveryDtLabel +
                ", isDeliveryDtLabel=" + isDeliveryDtLabel +
                ", isActualDeliveryDtLabel=" + isActualDeliveryDtLabel +
                ", isOrderCompleteLabel=" + isOrderCompleteLabel +
                ", isOutboundDirection=" + isOutboundDirection +
                ", isInboundDirection=" + isInboundDirection +
                ", isThirdpartyDirection=" + isThirdpartyDirection +
                ", isUnknownDirection=" + isUnknownDirection +
                ", isFSM=" + isFSM +
                ", isReturn=" + isReturn +
                ", isOriginalOutBound=" + isOriginalOutBound +
                ", isChildPackage=" + isChildPackage +
                ", isParentPackage=" + isParentPackage +
                ", isReclassifiedAsSingleShipment=" + isReclassifiedAsSingleShipment +
                ", isDuplicate=" + isDuplicate +
                ", isMaskShipper=" + isMaskShipper +
                ", isHalEligible=" + isHalEligible +
                ", isFedexOfficeOnlineOrders=" + isFedexOfficeOnlineOrders +
                ", isFedexOfficeInStoreOrders=" + isFedexOfficeInStoreOrders +
                ", isMultipleStop=" + isMultipleStop +
                ", isCustomCritical=" + isCustomCritical +
                ", isInvalid=" + isInvalid +
                ", isNotFound=" + isNotFound +
                ", isFreight=" + isFreight +
                ", isSpod=" + isSpod +
                ", isSignatureAvailable=" + isSignatureAvailable +
                ", isMPS=" + isMPS +
                ", isGMPS=" + isGMPS +
                ", isResidential=" + isResidential +
                ", isDestResidential=" + isDestResidential +
                ", isHALResidential=" + isHALResidential +
                ", isActualDelAddrResidential=" + isActualDelAddrResidential +
                ", isReqEstDelDt=" + isReqEstDelDt +
                ", isCDOEligible=" + isCDOEligible +
                ", CDOInfoList=" + CDOInfoList +
                ", CDOExists=" + CDOExists +
                ", isMtchdByRecShrID=" + isMtchdByRecShrID +
                ", isMtchdByShiprShrID=" + isMtchdByShiprShrID +
                '}';
    }
}
