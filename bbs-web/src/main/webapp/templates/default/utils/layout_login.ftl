<#macro layout title>
<!DOCTYPE html>
<html lang="en">
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>CTBB</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <#--<link rel="stylesheet" type="text/css" href="${base}/assets/css/login-style.css" />-->
    <#--<link rel="stylesheet" type="text/css" href="${base}/assets/css/login-style2.css" />-->
    <#--<link rel="stylesheet" type="text/css" href="${base}/assets/css/login-style3.css" />-->
    <#--<link rel="stylesheet" type="text/css" href="${base}/assets/css/login-animate.css" />-->
    <#--<link rel="stylesheet" type="text/css" href="${base}/assets/css/login-demo.css" />-->

    <link rel="stylesheet" href="${base}/assets/vendors/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/assets/css/login.css">
    <script src="${base}/assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/assets/vendors/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${base}/assets/vendors/validate/jquery-validate.js"></script>


</head>
<body class="sign">
    <#nested>

<script type="text/javascript">
    //<!CDATA[
    var bodyBgs = [];
    bodyBgs[0] = "${base}/assets/img/bg/bg-1.jpg";
    bodyBgs[1] = "${base}/assets/img/bg/bg-2.jpg";
    bodyBgs[2] = "${base}/assets/img/bg/bg-3.jpg";
    bodyBgs[3] = "${base}/assets/img/bg/bg-4.jpg";
    bodyBgs[4] = "${base}/assets/img/bg/bg-5.jpg";
    bodyBgs[5] = "${base}/assets/img/bg/bg-6.jpg";
    bodyBgs[6] = "${base}/assets/img/bg/bg-7.jpg";
    bodyBgs[7] = "${base}/assets/img/bg/bg-8.jpg";
    bodyBgs[8] = "${base}/assets/img/bg/bg-9.jpg";
    bodyBgs[9] = "${base}/assets/img/bg/bg-10.jpg";
    bodyBgs[10] = "${base}/assets/img/bg/bg-11.jpg";
    bodyBgs[11] = "${base}/assets/img/bg/bg-12.jpg";
    var randomBgIndex = Math.round(Math.random() * 5);
    //输出随机的背景图
    document.write('<style>body{background:url(' + bodyBgs[randomBgIndex] + ') no-repeat 100% 0}</style>');
    //]]>
</script>
</body>
</html>

</#macro>
