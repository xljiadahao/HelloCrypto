<%@page pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Hello Crypto - Certificate</title>
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <script type="text/javascript" src="../js/jquery-1.4.1.min.js"></script>
        <script type="text/javascript" src="../js/validation.js"></script>
        <script language="javascript" type="text/javascript">
            function validateForm() {
                var f1 = $("#name").validate("Full Name Required",$("#msgName"));
                var f2 = $("#ceritificate").validate("Ceritificate File Required",$("#msgCeritificate"));
                return f1 && f2;
            }
            
            $(function(){
                if(${msg != null && !"".equals(msg)}){
                    alert ('${msg}');
                    location.href="certificate.action";
                }
		$("#submitForm").click(function(){
                    var v = validateForm();
                    if(v) {
                        $("#certificateForm").submit();
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
                            <a href="#" style="font-family:verdana; color: blue;">PayPal</a>
                        </h1>
                    </div>
                    <div id="topheader">
                        <h1 id="title" >
                            <a href="#">Hello Crypto</a>
                        </h1>
                    </div>
                    <div id="navigation">
                        <a href="certificate.action"><font size="2">Certificate</font></a>
                        <c:choose>
                            <c:when test="${decryptEnabled}">
                                <a href="decryption.action"><font size="2">Decryption</font></a>
                            </c:when>
                            <c:otherwise><font size="2">Decryption</font></c:otherwise>
                        </c:choose>
                    </div>
                </div>
                
                <div id="content">
                    <p id="whereami"></p>
                    <h1>
                        Upload Certificate
                    </h1>
                    <form id="certificateForm" action="certificateUpload.action" method="post" enctype="multipart/form-data">
                        <table cellpadding="0" cellspacing="0" border="0" class="form_table">
                            <tr>
                                <td valign="middle" align="right">Full Name: </td>
                                <td valign="middle" align="left">
                                    <input id="name" type="text" class="inputgri" style="width:240px;" name="name" />
                                </td>
                                <td>
                                    <span>*</span>
                                    <span id="msgName" style="color:red"></span>
                                </td>
                            </tr>
                            <tr>
                                <td valign="middle" align="right">Certificate: </td>
                                <td valign="middle" align="left">
                                    <input id="ceritificate" type="file" class="inputgri" style="width:240px;" name="file" />
                                </td>
                                <td>
                                    <span>*</span>
                                    <span id="msgCeritificate" style="color:red"></span>
                                </td>
                            </tr>
                        </table>
                        <p>
                            <input id="submitForm" type="button" class="button" value="Upload Certificate &raquo;" />
                        </p>
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
