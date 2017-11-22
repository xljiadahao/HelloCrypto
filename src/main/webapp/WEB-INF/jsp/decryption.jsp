<%@page pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Hello Crypto - Decryption</title>
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <script type="text/javascript" src="../js/jquery-1.4.1.min.js"></script>
        <script type="text/javascript" src="../js/validation.js"></script>
        <script language="javascript" type="text/javascript">
            function validateForm() {
                var f1 = $("#encrypt").validate("Encrypted Data Required",$("#msgEncrypt"));
                var f2 = $("#alias").validate("Key Entry Alias Required",$("#msgAlias"));
                var f3 = $("#storepass").validate("Keystore Storepass Required",$("#msgStorepass"));
                var f4 = $("#keypass").validate("Key Entry Keypass Required",$("#msgKeypass"));
                var f5 = $("#keystore").validate("Keystore File Required",$("#msgKeystore"));
                return f1 && f2 && f3 && f4 && f5;
            }
            
            $(function(){
		$("#submitForm").click(function(){
                    var v = validateForm();
                    if(v) {
                        $("#decryptionForm").submit();
                    }
		});
            });
        </script>
    </head>
    <body>
        <div id="wrap">
            <div id="top_content">
                <div id="header">
                    <div id="rightheader">
                        <h1 id="brand" >
                            <a href="#" style="font-family:verdana; color: blue;">微信小程序</a>
                        </h1>
                    </div>
                    <div id="topheader">
                        <h1 id="title" >
                            <a href="#">Hello Crypto</a>
                        </h1>
                    </div>
                    <div id="navigation">
                        <a href="certificate.action"><font size="2">Certificate</font></a>
                        <a href="decryption.action"><font size="2">Decryption</font></a>
                    </div>
                </div>
                
                <div id="content">
                    <p id="whereami"></p>
                    <h1>
                        Let's Decrypt It
                    </h1>
                    <table cellpadding="0" cellspacing="0" border="0" class="form_table">
                        <tr>
                            <td valign="middle" align="right" style="width:150px; font-family:verdana; color:blue;">Lucky Draw &raquo; </td>
                            <td valign="middle" align="left" colspan="2">
                                <marquee style="width: 240px; height: 25px;" scrollamount="2" direction="up" >
                                    <c:forEach items="${participateNames}" var="participant">
                                        <p><font style="font-family:verdana; color:lightskyblue;" size="2" >${participant}</font></p>
                                    </c:forEach>
                                </marquee>
                            </td>
                        </tr>
                        <!-- now maximum 2 lucky draw enabled, further enhancement for multiple lucky draws -->
                        <tr>
                            <td valign="middle" align="right" style="width:150px; font-family:verdana; color:blue;">Encryption Info &raquo; </td>
                            <c:forEach var="secure" items="${secureInfo}" varStatus="s" begin="0" end="1" step="1">
                                <td valign="middle" align="left">
                                    <textarea class="inputgri" readonly style="overflow-x:hidden; width:240px; height:120px;">${secure}</textarea>
                                </td>
                            </c:forEach>
                        </tr>  
                    </table>
                    <form id="decryptionForm" action="decryptionSubmit.action" method="post" enctype="multipart/form-data">
                        <table cellpadding="0" cellspacing="0" border="0" class="form_table">
                            <tr>
                                <td valign="middle" align="right" style="width:150px;">Encrypted Data: </td>
                                <td valign="middle" align="left">
                                    <input id="encrypt" type="text" class="inputgri" style="width:240px;" name="encrypt" />
                                </td>
                                <td>
                                    <span>*</span>
                                    <span id="msgEncrypt" style="color:red"></span>
                                </td>
                            </tr>
                            <tr>
                                <td valign="middle" align="right" style="width:150px;">Key Entry Alias: </td>
                                <td valign="middle" align="left">
                                    <input id="alias" type="text" class="inputgri" style="width:240px;" name="alias" value="${alias}"/>
                                </td>
                                <td>
                                    <span>*</span>
                                    <span id="msgAlias" style="color:red"></span>
                                </td>
                            </tr>
                            <tr>
                                <td valign="middle" align="right" style="width:150px;">Storepass: </td>
                                <td valign="middle" align="left">
                                    <input id="storepass" type="password" class="inputgri" style="width:240px;" name="storepass" />
                                </td>
                                <td>
                                    <span>*</span>
                                    <span id="msgStorepass" style="color:red"></span>
                                </td>
                            </tr>
                            <tr>
                                <td valign="middle" align="right" style="width:150px;">Keypass: </td>
                                <td valign="middle" align="left">
                                    <input id="keypass" type="password" class="inputgri" style="width:240px;" name="keypass" />
                                </td>
                                <td>
                                    <span>*</span>
                                    <span id="msgKeypass" style="color:red;"></span>
                                </td>
                            </tr>
                            <tr>
                                <td valign="middle" align="right" style="width:150px;">Keystore File: </td>
                                <td valign="middle" align="left">
                                    <input id="keystore" type="file" class="inputgri" style="width:240px;" name="file" />
                                </td>
                                <td>
                                    <span>*</span>
                                    <span id="msgKeystore" style="color:red"></span>
                                </td>
                            </tr>
                            <tr>
                                <td valign="middle" align="right" style="width:150px;">Secure Info: </td>
                                <td valign="middle" align="left" colspan="2">
                                    <c:choose>
                                        <c:when test="${decryptSuccess}">
                                            <input id="secureinfo" type="text" readonly class="inputgri" style="width:480px; color:green;" value="${decryptedSecureInfo}" />
                                        </c:when>
                                        <c:otherwise>
                                            <input id="secureinfo" type="text" readonly class="inputgri" style="width:480px; color:red;" value="${decryptedSecureInfo}" />
                                        </c:otherwise>
                                    </c:choose> 
                                </td>
                            </tr>
                            <tr>
                                <td valign="middle" align="right" style="width:150px;">
                                    <p><input id="submitForm" type="button" class="button" value="Decrypt &raquo;" /></p>
                                </td>
                                <td colspan="2"></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <div id="footer">
                <div id="footer_bg">
                    Xu Lei from PayPal
                </div>
            </div>
        </div>
    </body>
</html>
