/*
 * The MIT License
 *
 * Copyright (c) 2014.  schors (https://github.com/flicus)
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.schors.evlampia;

import org.jdom2.Attribute;
import org.jdom2.JDOMException;
import org.jdom2.output.XMLOutputter;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.convertor.Body;
import org.schors.evlampia.convertor.Head;
import org.schors.evlampia.convertor.Html;
import org.schors.evlampia.convertor.Record;
import org.schors.evlampia.dao.DAOManager;
import org.schors.evlampia.rss.FeedReader;
import org.schors.evlampia.tracker.NamedTrackList;
import org.schors.evlampia.tracker.Track;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    private final static Pattern pattern = Pattern.compile("\\b((?:[a-z][\\w-]+:(?:\\/{1,3}|[a-z0-9%])|www\\d{0,3}[.])(?:[^\\s()<>]+|\\([^\\s()<>]+\\))+(?:\\([^\\s()<>]+\\)|[^`!()\\[\\]{};:'\".,<>?«»“”‘’\\s]))");

    private static final String style = "@font-face {\n" +
            "            font-family: 'Input';\n" +
            "            src: url('fonts/InputMono/InputMono/InputMono-Regular.ttf') format('truetype');\n" +
            "            font-weight: normal;\n" +
            "            font-style: normal\n" +
            "        }\n" +
            "\n" +
            "        .datepick {\n" +
            "            box-shadow: 0 1px 4px rgba(0, 0, 0, .3),\n" +
            "            -23px 0 20px -23px rgba(0, 0, 0, .8),\n" +
            "            23px 0 20px -23px rgba(0, 0, 0, .8),\n" +
            "            0 0 40px rgba(0, 0, 0, .1) inset;\n" +
            "        }\n" +
            "\n" +
            "        .header {\n" +
            "            border-bottom: #246 solid 1pt;\n" +
            "            letter-spacing: 2px;\n" +
            "            margin-left: 20pt;\n" +
            "            letter-spacing: 2px;\n" +
            "            font-size: larger;\n" +
            "            font-weight: bold;\n" +
            "            padding-bottom: 2em;\n" +
            "        }\n" +
            "\n" +
            "        .left {\n" +
            "            color: #6c71c4;\n" +
            "            float: left;\n" +
            "        }\n" +
            "\n" +
            "        .right {\n" +
            "            color: #6c71c4;\n" +
            "            float: right;\n" +
            "        }\n" +
            "\n" +
            "        .clear {\n" +
            "            clear: both;\n" +
            "        }\n" +
            "\n" +
            "        .log {\n" +
            "            padding-top: 2em;\n" +
            "        }\n" +
            "\n" +
            "        .mark {\n" +
            "            color: #aaa;\n" +
            "            text-align: right;\n" +
            "            font-family: monospace;\n" +
            "            letter-spacing: 3px\n" +
            "        }\n" +
            "\n" +
            "        .timestamp {\n" +
            "            font-size: small;\n" +
            "            color: #839496;;\n" +
            "        }\n" +
            "\n" +
            "        .timestamp a {\n" +
            "            text-decoration: none;\n" +
            "            color: #839496;;\n" +
            "        }\n" +
            "\n" +
            "        .nickname {\n" +
            "            color: #b58900;\n" +
            "        }\n" +
            "\n" +
            "        .lampa {\n" +
            "            color: #cb4b16;\n" +
            "        }\n" +
            "\n" +
            "        body {\n" +
            "            font: normal normal normal 14px/1 Input;\n" +
            "            font-size: inherit;\n" +
            "            text-rendering: auto;\n" +
            "            -webkit-font-smoothing: antialiased;\n" +
            "            -moz-osx-font-smoothing: grayscale;\n" +
            "            background-color: #fdf6e3;\n" +
            "            color: #657b83;\n" +
            "        }\n" +
            "\n" +
            "        ::selection {\n" +
            "            background: #FF5E99;\n" +
            "            color: #fff;\n" +
            "            text-shadow: none;\n" +
            "        }\n" +
            "\n" +
            "        p {\n" +
            "            display: block;\n" +
            "            -webkit-margin-before: 0.5em;\n" +
            "            -webkit-margin-after: 0.5em;\n" +
            "            -webkit-margin-start: 0px;\n" +
            "            -webkit-margin-end: 0px;\n" +
            "        }";

    public static String readFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            sb.append(reader.readLine());
        }
        return sb.toString();
    }

    public static void main(String[] args) throws SQLException, IOException, InterruptedException, JDOMException, XMLStreamException {

        Head head = new Head();
        Body body = new Body();
        Html html = new Html(head, body);

        Pattern patternLeft = Pattern.compile("<div\\s+class=\"left\">(.*?)</div>");
        Pattern patternRight = Pattern.compile("<div\\s+class=\"right\">(.*?)</div>");
        Pattern pattern3 = Pattern.compile("<a\\s+id=\"(.+?)\"\\s*href=\".+?\">\\[(.+?)\\]</a></span>\\s*<span\\s+class=\"(.+?)\">(.+?)</span>(.*?)<br/>", Pattern.MULTILINE);
        String file = readFile("C:\\Users\\sskoptsov\\IdeaProjects\\eva\\webroot\\eva\\static\\oldlog.htm");
        Matcher m = patternLeft.matcher(file);
        StringBuilder title = new StringBuilder();
        if (m.find()) {
            body.setLeft(m.group(1));
            title.append(m.group(1));
        }
        m = patternRight.matcher(file);
        if (m.find()) {
            body.setRight(m.group(1));
            title.append(" - ").append(m.group(1));
        }
        head.setTitle(title.toString());

        head.getMeta().add(new Attribute("http-equiv", "Content-Type"));
        head.getMeta().add(new Attribute("content", "text/html; charset=UTF-8"));

        head.getLinks().add("css/jquery.datepick.css");

        head.getScripts().add("js/jquery-1.9.1.js");
        head.getScripts().add("js/jquery.datepick.js");
        head.getScripts().add("js/jquery.datepick-ru.js");
        head.getScripts().add("js/dp.js");

        head.getStyles().add(style);

        m = pattern3.matcher(file);
        while (m.find()) {
            String record = m.group(0);
            System.out.println(record);
            System.out.println(String.format("%s, %s, %s, %s, %s", m.group(1), m.group(2), m.group(3), m.group(4), m.group(5)));
            Record r = new Record(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5));
            body.getRecords().add(r);
        }

        XMLOutputter op = new XMLOutputter();
        FileWriter writer = new FileWriter("C:\\Users\\sskoptsov\\IdeaProjects\\eva\\webroot\\eva\\static\\newlog.html");
        op.output(html.toElement(), writer);


//        SAXBuilder builder = new SAXBuilder(XMLReaders.NONVALIDATING);
//        Document jdom = builder.build(new File("C:\\Users\\sskoptsov\\IdeaProjects\\eva\\webroot\\eva\\static\\oldlog.htm"));
//        jdom.getContent();


        FeedReader reader = new FeedReader(new HashMap<String, MultiUserChat>());
        reader.addFeed("http://proriv.ru/news_rss.xml");
        reader.addFeed("http://shpilenok.livejournal.com/data/rss");
        reader.addFeed("http://1chan.ru/news/rss.xml");

        reader.save();
        reader.load();
//
//        synchronized (Thread.currentThread()) {
//            try {
//                Thread.currentThread().wait();
//            } catch (InterruptedException e) {
//                System.out.println(e);
//            }
//        }

        Map<String, NamedTrackList> list = new HashMap<>();
        NamedTrackList n1 = new NamedTrackList("name1");
        n1.addTrack(new Track("tr11", "trid11"));
        n1.addTrack(new Track("tr12", "trid12"));
        n1.addTrack(new Track("tr13", "trid13"));
        list.put("list1", n1);
        NamedTrackList n2 = new NamedTrackList("name2");
        n1.addTrack(new Track("tr21", "trid21"));
        n1.addTrack(new Track("tr22", "trid22"));
        n1.addTrack(new Track("tr23", "trid23"));
        list.put("list2", n2);
        DAOManager.getInstance().saveTrackList("private", list);
        list.clear();
        DAOManager.getInstance().loadTrackList("private", list);
//
//
//        DAOManager.getInstance().updateTag("tag1");
//        DAOManager.getInstance().updateTag("tag1");
//        DAOManager.getInstance().updateTag("tag1");
//        DAOManager.getInstance().updateTag("tag2");
//        DAOManager.getInstance().updateTag("tag2");
//        DAOManager.getInstance().updateTag("tag3");
//        DAOManager.getInstance().updateTag("tag4");
//
//        List<TagItem> llist = DAOManager.getInstance().getTags();
//
//
//        String te = "int i = 12;\r\nint j = List<String>;";
//
//        String ddd = "hgfkjhf error: kjhgkjhgkjhgjhg\n" +
//                "      geta();\n" +
//                "         ^\n" +
//                "kljmh;lj'oimu'iu\n" +
//                "pi;opi;poj;lkjlkj;lj\n" +
//                "hgfkjhf error: jdhfsdkjhfs\n" +
//                "      int i = 0;\n" +
//                "         ^\n" +
//                "kljmh;lj'oimu'iu\n" +
//                "pi;opi;poj;lkjlkj;lj\n";
//        Pattern p = Pattern.compile("error:\\s*(.*)$\\r?\\n\\s*(.*)$", Pattern.MULTILINE);
//
//        Matcher m = p.matcher(ddd);
//        while (m.find()) {
//            System.out.println(m.group(0));
//            System.out.println(m.group(1));
//            System.out.println(m.group(2));
//        }
//
//        String bb = URLEncoder.encode(te, "UTF-8");
//
//        String cc = URLDecoder.decode(bb, "UTF-8");


//        Instant s = new Instant();
//
//        Configuration cfg = new Configuration();
//        DynDNS ddns = new DynDNS("schors.zapto.org", "flicus@gmail.com", "fghbjhb77");
//        cfg.setDynDns(ddns);
//        DynDNSManager.update(cfg);

//        TokenManager.getInstance().makeNewToken("aaa");
//
//
//        DateTime start = new DateTime(2004, 12, 25, 0, 0, 0, 0);
//        DateTime end = new DateTime();//new DateTime(2006, 1, 1, 0, 0, 0, 0);
//
//        PeriodFormatter pf = new PeriodFormatterBuilder().printZeroRarelyLast().appendYears().appendSuffix(" год", " лет").appendSeparator(", ")//.printZeroRarelyLast()
//                .appendMonths().appendSuffix(" месяц", " месяцев").appendSeparator(", ")
//                .appendDays().appendSuffix(" день", " дней").appendSeparator(", ")
//                .appendHours().appendSuffix(" час", " часов").appendSeparator(", ")
//                .appendMinutes().appendSuffix(" минута", " минут").appendSeparator(", ")
//                .appendSecondsWithOptionalMillis().appendSuffix(" секунда", " секунд").toFormatter();
//
//        System.out.println(new Period(start, end).toString(pf));


        //{"TrackPackagesRequest":{"appType":"wtrk","uniqueKey":"","processingParameters":{"anonymousTransaction":true,"clientId":"WTRK","returnDetailedErrors":true,"returnLocalizedDateTime":false},"trackingInfoList":[{"trackNumberInfo":{"trackingNumber":"799408958110","trackingQualifier":"","trackingCarrier":""}}]}}
        //{"trackPackagesRequest":{"appType":"wtrk","uniqueKey":"","processingParameters":{"anonymousTransaction":true,"clientId":"WTRK","returnDetailedErrors":true,"returnLocalizedDateTime":false},"trackingInfoList":[{"trackNumberInfo":{"trackingNumber":"799408958110","trackingQualifier":"","trackingCarrier":""}}]}}
        //{"TrackPackagesResponse":{"successful":true,"passedLoggedInCheck":false,"errorList":[{"code":"0","message":"Request was successfully processed.","source":null}],"packageList":[{"trackingNbr":"799408958110","trackingQualifier":"2456384000\x7e799408958110\x7eFX","trackingCarrierCd":"FDXE","trackingCarrierDesc":"FedEx Express","displayTrackingNbr":"799408958110","shipperCmpnyName":"","shipperName":"","shipperAddr1":"","shipperAddr2":"","shipperCity":"ANDHERI \x28E\x29","shipperStateCD":"MH","shipperZip":"","shipperCntryCD":"IN","shipperPhoneNbr":"","shippedBy":"","recipientCmpnyName":"","recipientName":"","recipientAddr1":"","recipientAddr2":"","recipientCity":"MOSCOW","recipientStateCD":"","recipientZip":"","recipientCntryCD":"RU","recipientPhoneNbr":"","shippedTo":"","keyStatus":"In transit","keyStatusCD":"IT","lastScanStatus":"","lastScanDateTime":"","receivedByNm":"","subStatus":"MUMBAI, MH","mainStatus":"","statusBarCD":"IT","shortStatus":"","shortStatusCD":"","statusLocationAddr1":"","statusLocationAddr2":"","statusLocationCity":"MUMBAI","statusLocationStateCD":"MH","statusLocationZip":"","statusLocationCntryCD":"IN","statusWithDetails":"In transit\x3b MUMBAI, IN","shipDt":"2013\x2d04\x2d01T19\x3a55\x3a00\x2b05\x3a30","displayShipDt":"4\x2f01\x2f2013","displayShipTm":"7\x3a55 pm","displayShipDateTime":"4\x2f01\x2f2013 7\x3a55 pm","pickupDt":"2013\x2d04\x2d01T19\x3a55\x3a00\x2b05\x3a30","displayPickupDt":"4\x2f01\x2f2013","displayPickupTm":"7\x3a55 pm","displayPickupDateTime":"4\x2f01\x2f2013 7\x3a55 pm","estDeliveryDt":"2013\x2d04\x2d05T18\x3a00\x3a00\x2b04\x3a00","estDeliveryTm":"180000","displayEstDeliveryDt":"4\x2f05\x2f2013","displayEstDeliveryTm":"6\x3a00 pm","displayEstDeliveryDateTime":"4\x2f05\x2f2013 6\x3a00 pm","actDeliveryDt":"","displayActDeliveryDt":"","displayActDeliveryTm":"","displayActDeliveryDateTime":"","nickName":"","note":"","matchedAccountList":[""],"fxfAdvanceETA":"","fxfAdvanceReason":"","fxfAdvanceStatusCode":"","fxfAdvanceStatusDesc":"","destLink":"","originLink":"","hasBillOfLadingImage":false,"hasBillPresentment":false,"signatureRequired":0,"totalKgsWgt":"1.0","displayTotalKgsWgt":"1 kgs","totalLbsWgt":"2.2","displayTotalLbsWgt":"2.2 lbs","displayTotalWgt":"2.2 lbs \x2f 1 kgs","pkgKgsWgt":"1.0","displayPkgKgsWgt":"1 kgs","pkgLbsWgt":"0.5","displayPkgLbsWgt":"0.5 lbs","displayPkgWgt":"0.5 lbs \x2f 1 kgs","dimensions":"","masterTrackingNbr":"","masterQualifier":"","masterCarrierCD":"","originalOutboundTrackingNbr":null,"originalOutboundQualifier":"","originalOutboundCarrierCD":"","invoiceNbrList":[""],"referenceList":[""],"doorTagNbrList":[""],"referenceDescList":[""],"purchaseOrderNbrList":[""],"billofLadingNbrList":[""],"shipperRefList":[""],"rmaList":[""],"deptNbrList":[""],"shipmentIdList":[""],"tcnList":[""],"partnerCarrierNbrList":[""],"hasAssociatedShipments":false,"hasAssociatedReturnShipments":false,"assocShpGrp":0,"drTgGrp":["0"],"associationInfoList":[{"trackingNumberInfo":{"trackingNumber":"","trackingQualifier":"","trackingCarrier":"","processingParameters":null},"associatedType":""}],"returnReason":"","returnRelationship":null,"skuItemUpcCdList":[""],"receiveQtyList":[""],"itemDescList":[""],"partNbrList":[""],"serviceCD":"INTERNATIONAL\x5fPRIORITY","serviceDesc":"FedEx International Priority","serviceShortDesc":"IP","packageType":"","packaging":"FedEx Pak","clearanceDetailLink":"","showClearanceDetailLink":false,"manufactureCountryCDList":[null],"commodityCDList":[""],"commodityDescList":[""],"cerNbrList":[""],"cerComplaintCDList":[""],"cerComplaintDescList":[""],"cerEventDateList":[""],"displayCerEventDateList":[""],"totalPieces":"1","specialHandlingServicesList":["Deliver Weekday"],"shipmentType":"","pkgContentDesc1":"","pkgContentDesc2":"","docAWBNbr":"","originalCharges":"","transportationCD":"","transportationDesc":"","dutiesAndTaxesCD":"","dutiesAndTaxesDesc":"","origPieceCount":"","destPieceCount":"","goodsClassificationCD":"","receipientAddrQty":"0","deliveryAttempt":"0","codReturnTrackNbr":"","scanEventList":[{"date":"2013\x2d04\x2d02","time":"10\x3a33\x3a00","gmtOffset":"\x2b05\x3a30","status":"In transit","statusCD":"IT","scanLocation":"MUMBAI IN","scanDetails":"","scanDetailsHtml":"","rtrnShprTrkNbr":"","isDelException":false,"isClearanceDelay":false,"isException":false,"isDelivered":false},{"date":"2013\x2d04\x2d02","time":"03\x3a40\x3a00","gmtOffset":"\x2b05\x3a30","status":"In transit","statusCD":"IT","scanLocation":"MUMBAI IN","scanDetails":"","scanDetailsHtml":"","rtrnShprTrkNbr":"","isDelException":false,"isClearanceDelay":false,"isException":false,"isDelivered":false},{"date":"2013\x2d04\x2d01","time":"22\x3a08\x3a00","gmtOffset":"\x2b05\x3a30","status":"Left FedEx origin facility","statusCD":"DP","scanLocation":"MUMBAI IN","scanDetails":"","scanDetailsHtml":"","rtrnShprTrkNbr":"","isDelException":false,"isClearanceDelay":false,"isException":false,"isDelivered":false},{"date":"2013\x2d04\x2d01","time":"19\x3a55\x3a00","gmtOffset":"\x2b05\x3a30","status":"Picked up","statusCD":"PU","scanLocation":"MUMBAI IN","scanDetails":"","scanDetailsHtml":"","rtrnShprTrkNbr":"","isDelException":false,"isClearanceDelay":false,"isException":false,"isDelivered":false},{"date":"2013\x2d04\x2d01","time":"08\x3a27\x3a58","gmtOffset":"\x2d05\x3a00","status":"Shipment information sent to FedEx","statusCD":"OC","scanLocation":"","scanDetails":"","scanDetailsHtml":"","rtrnShprTrkNbr":"","isDelException":false,"isClearanceDelay":false,"isException":false,"isDelivered":false}],"originAddr1":"","originAddr2":"","originCity":"MUMBAI","originStateCD":"MH","originZip":"","originCntryCD":"IN","originLocationID":"","originTermCity":"MUMBAI","originTermStateCD":"MH","destLocationAddr1":"","destLocationAddr2":"","destLocationCity":"MOSCOW","destLocationStateCD":"MC","destLocationZip":"","destLocationCntryCD":"RU","destLocationID":"","destLocationTermCity":"MOSCOW","destLocationTermStateCD":"MC","destAddr1":"","destAddr2":"","destCity":"MOSCOW","destStateCD":"","destZip":"","destCntryCD":"RU","halAddr1":"","halAddr2":"","halCity":"","halStateCD":"","halZipCD":"","halCntryCD":"","actualDelAddrCity":"","actualDelAddrStateCD":"","actualDelAddrZipCD":"","actualDelAddrCntryCD":"","totalTransitMiles":"","excepReasonList":[""],"excepActionList":[""],"exceptionReason":"","exceptionAction":"","statusDetailsList":[""],"trackErrCD":"","destTZ":"\x2b04\x3a00","originTZ":"\x2b05\x3a30","isMultiStat":"0","multiStatList":[{"multiPiec":"","multiTm":"","multiDispTm":"","multiSta":""}],"maskMessage":"","deliveryService":"","milestoDestination":"","terms":"","originUbanizationCode":"","originCountryName":"","isOriginResidential":false,"halUrbanizationCD":"","halCountryName":"","actualDelAddrUrbanizationCD":"","actualDelAddrCountryName":"","destUrbanizationCD":"","destCountryName":"","delToDesc":"","recpShareID":"","shprShareID":"","defaultCDOType":"RTH","mpstype":"","fxfAdvanceNotice":true,"rthavailableCD":"","excepReasonListNoInit":[""],"excepActionListNoInit":[""],"statusDetailsListNoInit":[""],"matched":false,"isSuccessful":true,"errorList":[{"code":"","message":"","source":null}],"isCanceled":false,"isPrePickup":false,"isPickup":false,"isInTransit":true,"isInProgress":true,"isDelException":false,"isClearanceDelay":false,"isException":false,"isDelivered":false,"isHAL":false,"isOnSchedule":false,"isDeliveryToday":false,"isSave":false,"isWatch":false,"isHistorical":false,"isTenderedNotification":false,"isDeliveredNotification":true,"isExceptionNotification":true,"isCurrentStatusNotification":false,"isAnticipatedShipDtLabel":false,"isShipPickupDtLabel":true,"isActualPickupLabel":false,"isOrderReceivedLabel":false,"isEstimatedDeliveryDtLabel":true,"isDeliveryDtLabel":false,"isActualDeliveryDtLabel":false,"isOrderCompleteLabel":false,"isOutboundDirection":false,"isInboundDirection":false,"isThirdpartyDirection":false,"isUnknownDirection":false,"isFSM":false,"isReturn":false,"isOriginalOutBound":false,"isChildPackage":false,"isParentPackage":false,"isReclassifiedAsSingleShipment":false,"isDuplicate":false,"isMaskShipper":false,"isHalEligible":false,"isFedexOfficeOnlineOrders":false,"isFedexOfficeInStoreOrders":false,"isMultipleStop":false,"isCustomCritical":false,"isInvalid":false,"isNotFound":false,"isFreight":false,"isSpod":false,"isSignatureAvailable":false,"isMPS":false,"isGMPS":false,"isResidential":false,"isDestResidential":true,"isHALResidential":false,"isActualDelAddrResidential":false,"isReqEstDelDt":false,"isCDOEligible":false,"CDOInfoList":[{"spclInstructDesc":"","delivOptn":"","delivOptnStatus":"","reqApptWdw":"","reqApptDesc":"","rerouteTRKNbr":"","beginTm":"","endTm":""}],"CDOExists":false,"isMtchdByRecShrID":false,"isMtchdByShiprShrID":false}]}}


//        String resp = "{\"TrackPackagesResponse\":{\"successful\":true,\"passedLoggedInCheck\":false,\"errorList\":[{\"code\":\"0\",\"message\":\"Request was successfully processed.\",\"source\":null}],\"packageList\":[{\"trackingNbr\":\"799408958110\",\"trackingQualifier\":\"2456384000\\x7e799408958110\\x7eFX\",\"trackingCarrierCd\":\"FDXE\",\"trackingCarrierDesc\":\"FedEx Express\",\"displayTrackingNbr\":\"799408958110\",\"shipperCmpnyName\":\"\",\"shipperName\":\"\",\"shipperAddr1\":\"\",\"shipperAddr2\":\"\",\"shipperCity\":\"ANDHERI \\x28E\\x29\",\"shipperStateCD\":\"MH\",\"shipperZip\":\"\",\"shipperCntryCD\":\"IN\",\"shipperPhoneNbr\":\"\",\"shippedBy\":\"\",\"recipientCmpnyName\":\"\",\"recipientName\":\"\",\"recipientAddr1\":\"\",\"recipientAddr2\":\"\",\"recipientCity\":\"MOSCOW\",\"recipientStateCD\":\"\",\"recipientZip\":\"\",\"recipientCntryCD\":\"RU\",\"recipientPhoneNbr\":\"\",\"shippedTo\":\"\",\"keyStatus\":\"In transit\",\"keyStatusCD\":\"IT\",\"lastScanStatus\":\"\",\"lastScanDateTime\":\"\",\"receivedByNm\":\"\",\"subStatus\":\"MUMBAI, MH\",\"mainStatus\":\"\",\"statusBarCD\":\"IT\",\"shortStatus\":\"\",\"shortStatusCD\":\"\",\"statusLocationAddr1\":\"\",\"statusLocationAddr2\":\"\",\"statusLocationCity\":\"MUMBAI\",\"statusLocationStateCD\":\"MH\",\"statusLocationZip\":\"\",\"statusLocationCntryCD\":\"IN\",\"statusWithDetails\":\"In transit\\x3b MUMBAI, IN\",\"shipDt\":\"2013\\x2d04\\x2d01T19\\x3a55\\x3a00\\x2b05\\x3a30\",\"displayShipDt\":\"4\\x2f01\\x2f2013\",\"displayShipTm\":\"7\\x3a55 pm\",\"displayShipDateTime\":\"4\\x2f01\\x2f2013 7\\x3a55 pm\",\"pickupDt\":\"2013\\x2d04\\x2d01T19\\x3a55\\x3a00\\x2b05\\x3a30\",\"displayPickupDt\":\"4\\x2f01\\x2f2013\",\"displayPickupTm\":\"7\\x3a55 pm\",\"displayPickupDateTime\":\"4\\x2f01\\x2f2013 7\\x3a55 pm\",\"estDeliveryDt\":\"2013\\x2d04\\x2d05T18\\x3a00\\x3a00\\x2b04\\x3a00\",\"estDeliveryTm\":\"180000\",\"displayEstDeliveryDt\":\"4\\x2f05\\x2f2013\",\"displayEstDeliveryTm\":\"6\\x3a00 pm\",\"displayEstDeliveryDateTime\":\"4\\x2f05\\x2f2013 6\\x3a00 pm\",\"actDeliveryDt\":\"\",\"displayActDeliveryDt\":\"\",\"displayActDeliveryTm\":\"\",\"displayActDeliveryDateTime\":\"\",\"nickName\":\"\",\"note\":\"\",\"matchedAccountList\":[\"\"],\"fxfAdvanceETA\":\"\",\"fxfAdvanceReason\":\"\",\"fxfAdvanceStatusCode\":\"\",\"fxfAdvanceStatusDesc\":\"\",\"destLink\":\"\",\"originLink\":\"\",\"hasBillOfLadingImage\":false,\"hasBillPresentment\":false,\"signatureRequired\":0,\"totalKgsWgt\":\"1.0\",\"displayTotalKgsWgt\":\"1 kgs\",\"totalLbsWgt\":\"2.2\",\"displayTotalLbsWgt\":\"2.2 lbs\",\"displayTotalWgt\":\"2.2 lbs \\x2f 1 kgs\",\"pkgKgsWgt\":\"1.0\",\"displayPkgKgsWgt\":\"1 kgs\",\"pkgLbsWgt\":\"0.5\",\"displayPkgLbsWgt\":\"0.5 lbs\",\"displayPkgWgt\":\"0.5 lbs \\x2f 1 kgs\",\"dimensions\":\"\",\"masterTrackingNbr\":\"\",\"masterQualifier\":\"\",\"masterCarrierCD\":\"\",\"originalOutboundTrackingNbr\":null,\"originalOutboundQualifier\":\"\",\"originalOutboundCarrierCD\":\"\",\"invoiceNbrList\":[\"\"],\"referenceList\":[\"\"],\"doorTagNbrList\":[\"\"],\"referenceDescList\":[\"\"],\"purchaseOrderNbrList\":[\"\"],\"billofLadingNbrList\":[\"\"],\"shipperRefList\":[\"\"],\"rmaList\":[\"\"],\"deptNbrList\":[\"\"],\"shipmentIdList\":[\"\"],\"tcnList\":[\"\"],\"partnerCarrierNbrList\":[\"\"],\"hasAssociatedShipments\":false,\"hasAssociatedReturnShipments\":false,\"assocShpGrp\":0,\"drTgGrp\":[\"0\"],\"associationInfoList\":[{\"trackingNumberInfo\":{\"trackingNumber\":\"\",\"trackingQualifier\":\"\",\"trackingCarrier\":\"\",\"processingParameters\":null},\"associatedType\":\"\"}],\"returnReason\":\"\",\"returnRelationship\":null,\"skuItemUpcCdList\":[\"\"],\"receiveQtyList\":[\"\"],\"itemDescList\":[\"\"],\"partNbrList\":[\"\"],\"serviceCD\":\"INTERNATIONAL\\x5fPRIORITY\",\"serviceDesc\":\"FedEx International Priority\",\"serviceShortDesc\":\"IP\",\"packageType\":\"\",\"packaging\":\"FedEx Pak\",\"clearanceDetailLink\":\"\",\"showClearanceDetailLink\":false,\"manufactureCountryCDList\":[null],\"commodityCDList\":[\"\"],\"commodityDescList\":[\"\"],\"cerNbrList\":[\"\"],\"cerComplaintCDList\":[\"\"],\"cerComplaintDescList\":[\"\"],\"cerEventDateList\":[\"\"],\"displayCerEventDateList\":[\"\"],\"totalPieces\":\"1\",\"specialHandlingServicesList\":[\"Deliver Weekday\"],\"shipmentType\":\"\",\"pkgContentDesc1\":\"\",\"pkgContentDesc2\":\"\",\"docAWBNbr\":\"\",\"originalCharges\":\"\",\"transportationCD\":\"\",\"transportationDesc\":\"\",\"dutiesAndTaxesCD\":\"\",\"dutiesAndTaxesDesc\":\"\",\"origPieceCount\":\"\",\"destPieceCount\":\"\",\"goodsClassificationCD\":\"\",\"receipientAddrQty\":\"0\",\"deliveryAttempt\":\"0\",\"codReturnTrackNbr\":\"\",\"scanEventList\":[{\"date\":\"2013\\x2d04\\x2d02\",\"time\":\"10\\x3a33\\x3a00\",\"gmtOffset\":\"\\x2b05\\x3a30\",\"status\":\"In transit\",\"statusCD\":\"IT\",\"scanLocation\":\"MUMBAI IN\",\"scanDetails\":\"\",\"scanDetailsHtml\":\"\",\"rtrnShprTrkNbr\":\"\",\"isDelException\":false,\"isClearanceDelay\":false,\"isException\":false,\"isDelivered\":false},{\"date\":\"2013\\x2d04\\x2d02\",\"time\":\"03\\x3a40\\x3a00\",\"gmtOffset\":\"\\x2b05\\x3a30\",\"status\":\"In transit\",\"statusCD\":\"IT\",\"scanLocation\":\"MUMBAI IN\",\"scanDetails\":\"\",\"scanDetailsHtml\":\"\",\"rtrnShprTrkNbr\":\"\",\"isDelException\":false,\"isClearanceDelay\":false,\"isException\":false,\"isDelivered\":false},{\"date\":\"2013\\x2d04\\x2d01\",\"time\":\"22\\x3a08\\x3a00\",\"gmtOffset\":\"\\x2b05\\x3a30\",\"status\":\"Left FedEx origin facility\",\"statusCD\":\"DP\",\"scanLocation\":\"MUMBAI IN\",\"scanDetails\":\"\",\"scanDetailsHtml\":\"\",\"rtrnShprTrkNbr\":\"\",\"isDelException\":false,\"isClearanceDelay\":false,\"isException\":false,\"isDelivered\":false},{\"date\":\"2013\\x2d04\\x2d01\",\"time\":\"19\\x3a55\\x3a00\",\"gmtOffset\":\"\\x2b05\\x3a30\",\"status\":\"Picked up\",\"statusCD\":\"PU\",\"scanLocation\":\"MUMBAI IN\",\"scanDetails\":\"\",\"scanDetailsHtml\":\"\",\"rtrnShprTrkNbr\":\"\",\"isDelException\":false,\"isClearanceDelay\":false,\"isException\":false,\"isDelivered\":false},{\"date\":\"2013\\x2d04\\x2d01\",\"time\":\"08\\x3a27\\x3a58\",\"gmtOffset\":\"\\x2d05\\x3a00\",\"status\":\"Shipment information sent to FedEx\",\"statusCD\":\"OC\",\"scanLocation\":\"\",\"scanDetails\":\"\",\"scanDetailsHtml\":\"\",\"rtrnShprTrkNbr\":\"\",\"isDelException\":false,\"isClearanceDelay\":false,\"isException\":false,\"isDelivered\":false}],\"originAddr1\":\"\",\"originAddr2\":\"\",\"originCity\":\"MUMBAI\",\"originStateCD\":\"MH\",\"originZip\":\"\",\"originCntryCD\":\"IN\",\"originLocationID\":\"\",\"originTermCity\":\"MUMBAI\",\"originTermStateCD\":\"MH\",\"destLocationAddr1\":\"\",\"destLocationAddr2\":\"\",\"destLocationCity\":\"MOSCOW\",\"destLocationStateCD\":\"MC\",\"destLocationZip\":\"\",\"destLocationCntryCD\":\"RU\",\"destLocationID\":\"\",\"destLocationTermCity\":\"MOSCOW\",\"destLocationTermStateCD\":\"MC\",\"destAddr1\":\"\",\"destAddr2\":\"\",\"destCity\":\"MOSCOW\",\"destStateCD\":\"\",\"destZip\":\"\",\"destCntryCD\":\"RU\",\"halAddr1\":\"\",\"halAddr2\":\"\",\"halCity\":\"\",\"halStateCD\":\"\",\"halZipCD\":\"\",\"halCntryCD\":\"\",\"actualDelAddrCity\":\"\",\"actualDelAddrStateCD\":\"\",\"actualDelAddrZipCD\":\"\",\"actualDelAddrCntryCD\":\"\",\"totalTransitMiles\":\"\",\"excepReasonList\":[\"\"],\"excepActionList\":[\"\"],\"exceptionReason\":\"\",\"exceptionAction\":\"\",\"statusDetailsList\":[\"\"],\"trackErrCD\":\"\",\"destTZ\":\"\\x2b04\\x3a00\",\"originTZ\":\"\\x2b05\\x3a30\",\"isMultiStat\":\"0\",\"multiStatList\":[{\"multiPiec\":\"\",\"multiTm\":\"\",\"multiDispTm\":\"\",\"multiSta\":\"\"}],\"maskMessage\":\"\",\"deliveryService\":\"\",\"milestoDestination\":\"\",\"terms\":\"\",\"originUbanizationCode\":\"\",\"originCountryName\":\"\",\"isOriginResidential\":false,\"halUrbanizationCD\":\"\",\"halCountryName\":\"\",\"actualDelAddrUrbanizationCD\":\"\",\"actualDelAddrCountryName\":\"\",\"destUrbanizationCD\":\"\",\"destCountryName\":\"\",\"delToDesc\":\"\",\"recpShareID\":\"\",\"shprShareID\":\"\",\"defaultCDOType\":\"RTH\",\"mpstype\":\"\",\"fxfAdvanceNotice\":true,\"rthavailableCD\":\"\",\"excepReasonListNoInit\":[\"\"],\"excepActionListNoInit\":[\"\"],\"statusDetailsListNoInit\":[\"\"],\"matched\":false,\"isSuccessful\":true,\"errorList\":[{\"code\":\"\",\"message\":\"\",\"source\":null}],\"isCanceled\":false,\"isPrePickup\":false,\"isPickup\":false,\"isInTransit\":true,\"isInProgress\":true,\"isDelException\":false,\"isClearanceDelay\":false,\"isException\":false,\"isDelivered\":false,\"isHAL\":false,\"isOnSchedule\":false,\"isDeliveryToday\":false,\"isSave\":false,\"isWatch\":false,\"isHistorical\":false,\"isTenderedNotification\":false,\"isDeliveredNotification\":true,\"isExceptionNotification\":true,\"isCurrentStatusNotification\":false,\"isAnticipatedShipDtLabel\":false,\"isShipPickupDtLabel\":true,\"isActualPickupLabel\":false,\"isOrderReceivedLabel\":false,\"isEstimatedDeliveryDtLabel\":true,\"isDeliveryDtLabel\":false,\"isActualDeliveryDtLabel\":false,\"isOrderCompleteLabel\":false,\"isOutboundDirection\":false,\"isInboundDirection\":false,\"isThirdpartyDirection\":false,\"isUnknownDirection\":false,\"isFSM\":false,\"isReturn\":false,\"isOriginalOutBound\":false,\"isChildPackage\":false,\"isParentPackage\":false,\"isReclassifiedAsSingleShipment\":false,\"isDuplicate\":false,\"isMaskShipper\":false,\"isHalEligible\":false,\"isFedexOfficeOnlineOrders\":false,\"isFedexOfficeInStoreOrders\":false,\"isMultipleStop\":false,\"isCustomCritical\":false,\"isInvalid\":false,\"isNotFound\":false,\"isFreight\":false,\"isSpod\":false,\"isSignatureAvailable\":false,\"isMPS\":false,\"isGMPS\":false,\"isResidential\":false,\"isDestResidential\":true,\"isHALResidential\":false,\"isActualDelAddrResidential\":false,\"isReqEstDelDt\":false,\"isCDOEligible\":false,\"CDOInfoList\":[{\"spclInstructDesc\":\"\",\"delivOptn\":\"\",\"delivOptnStatus\":\"\",\"reqApptWdw\":\"\",\"reqApptDesc\":\"\",\"rerouteTRKNbr\":\"\",\"beginTm\":\"\",\"endTm\":\"\"}],\"CDOExists\":false,\"isMtchdByRecShrID\":false,\"isMtchdByShiprShrID\":false}]}}";
//
//
//        try {
//            URLConnection conn = new URL("https://www.fedex.com/trackingCal/track").openConnection();
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//            conn.setRequestProperty("Cache-Control", "no-cache");
//            conn.setRequestProperty("Pragma", "no-cache");
//
//            conn.setDoOutput(true);
//            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//            out.write("data=");
//            out.write(FedexTracker.getInstanse().makeRequest("799408958110"));
//            out.write("&action=trackpackages&locale=en_US&format=json&version=99");
//            out.write("\r\n");
//            out.flush();
//            out.close();
//            String html = readStreamToString(conn.getInputStream(), "UTF-8");
//            System.out.println(html);
//            FdxResponseWrapper r = FedexTracker.getInstanse().parseResponse(html);
//            if (!r.getTrackPackagesResponse().isSuccessful() && !"0".equals(r.getTrackPackagesResponse().getErrorList().get(0).getCode())) {
//                System.out.println(r.getTrackPackagesResponse().getErrorList().get(0).getMessage());
//            }
//            System.out.println(r.getTrackPackagesResponse().getPackageList().get(0).getMainStatus() + ", " + r.getTrackPackagesResponse().getPackageList().get(0).getSubStatus());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        FdxRequestWrapper fdxRequestWrapper = new FdxRequestWrapper();
//        TrackNumberInfoWrapper trackNumberInfoWrapper = new TrackNumberInfoWrapper();
//        ProcessingParameters processingParameters = new ProcessingParameters();
//        TrackPackagesRequest trackPackagesRequest = new TrackPackagesRequest();
//        TrackNumberInfo trackNumberInfo = new TrackNumberInfo();
//        List<TrackNumberInfoWrapper> trackNumberInfoList = new ArrayList<>();
//        trackNumberInfoList.add(trackNumberInfoWrapper);
//
//        fdxRequestWrapper.setTrackPackagesRequest(trackPackagesRequest);
//
//        trackPackagesRequest.setAppType("wtrk");
//        trackPackagesRequest.setUniqueKey("");
//        trackPackagesRequest.setProcessingParameters(processingParameters);
//        trackPackagesRequest.setTrackingInfoList(trackNumberInfoList);
//
//        processingParameters.setAnonymousTransaction(true);
//        processingParameters.setClientId("WTRK");
//        processingParameters.setReturnDetailedErrors(true);
//        processingParameters.setReturnLocalizedDateTime(false);
//
//
//        trackNumberInfoWrapper.setTrackNumberInfo(trackNumberInfo);
//
//        trackNumberInfo.setTrackingNumber("799408958110");
//        trackNumberInfo.setTrackingCarrier("");
//        trackNumberInfo.setTrackingQualifier("");
//
//        Gson gson = new Gson();
//        String res = gson.toJson(fdxRequestWrapper);
//        System.out.println(res);
//
//        FdxResponseWrapper rr = gson.fromJson(resp, FdxResponseWrapper.class);
//        System.out.println(rr);


//        final TrayIcon trayIcon;
//
//        if (SystemTray.isSupported()) {
//
//            SystemTray tray = SystemTray.getSystemTray();
//            Image image = Toolkit.getDefaultToolkit().getImage("tray.gif");
//
//            MouseListener mouseListener = new MouseListener() {
//
//                public void mouseClicked(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse clicked!");
//                }
//
//                public void mouseEntered(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse entered!");
//                }
//
//                public void mouseExited(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse exited!");
//                }
//
//                public void mousePressed(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse pressed!");
//                }
//
//                public void mouseReleased(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse released!");
//                }
//            };
//
//            ActionListener exitListener = new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    System.out.println("Exiting...");
//                    System.exit(0);
//                }
//            };
//
//            PopupMenu popup = new PopupMenu();
//            MenuItem defaultItem = new MenuItem("Exit");
//            defaultItem.addActionListener(exitListener);
//            popup.add(defaultItem);
//
//            trayIcon = new TrayIcon(image, "Tray Demo", popup);
//
//            ActionListener actionListener = new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    trayIcon.displayMessage("Action Event",
//                            "An Action Event Has Been Performed!",
//                            TrayIcon.MessageType.INFO);
//                }
//            };
//
//            trayIcon.setImageAutoSize(true);
//            trayIcon.addActionListener(actionListener);
//            trayIcon.addMouseListener(mouseListener);
//
//            try {
//                tray.add(trayIcon);
//            } catch (AWTException e) {
//                System.err.println("TrayIcon could not be added.");
//            }
//
//        } else {
//
//            //  System Tray is not supported
//
//        }

//        UUID uuid = UUID.randomUUID();
//
//        long d = System.currentTimeMillis();
//        Random r = new Random(19061977);
//
//
//        String from = "Орда";
//        BASE64Encoder encoder = new BASE64Encoder();
//        BASE64Decoder decoder = new BASE64Decoder();
//
//        String hash = encoder.encode(from.concat(":").concat(uuid.toString()).getBytes("UTF-8"));
//
//        System.out.println(hash);
//        //Орда:22080290-ed77-40a6-b365-856a966eb2f2
//        System.out.println(new String(decoder.decodeBuffer(hash)));
//
//        hash = encoder.encode(String.valueOf(d).concat(":").concat(String.valueOf(r.nextLong())).getBytes());
//        System.out.println(hash);
//        System.out.println(new String(decoder.decodeBuffer(hash)));


        //ConfigurationManager.getInstance().readConfiguration(args[0]);

//        DAOManager.getInstance().updateTag("говно");
//        DAOManager.getInstance().updateTag("падла");
//        List<TagItem> list = DAOManager.getInstance().getTags();


//        String s = "http://yandex.ru/yandsearch?text=regexp%20online&lr=213\n" +
//                "http://stackoverflow.com/questions/2102727/regular-expression-for-url?lq=1\n" +
//                "http://wiki.apache.org/lucene-java/ImproveSearchingSpeed\n" +
//                "http://www.google.ru/ig\n" +
//                "https://www.google.com/\n" +
//                "http://vk.com/audio\n" +
//                "http://192.168.1.35:8080/hello\n" +
//                "Hello.Print()";
//
//        Matcher m = pattern.matcher(s);
//
//        String res = new String(s);
//
//        while (m.find()) {
//            String link = m.group(1);
//            res = res.replace(link, String.format("<a href='%s'>%s</a>", link, link));
//        }
//
//
//        try {
//            WebServer server = new WebServer();
//            EvaExecutors.getInstance().getExecutor().execute(server);
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
    }

    private static String readStreamToString(InputStream in, String encoding)
            throws IOException {
        StringBuffer b = new StringBuffer();
        InputStreamReader r = new InputStreamReader(in, encoding);
        int c;
        while ((c = r.read()) != -1) {
            b.append((char) c);
        }
        return b.toString();
    }
}
